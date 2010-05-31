package com.lehphyro.gamemcasa.scrapper.httpclient.parsing;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.*;
import org.htmlcleaner.*;

public class HtmlNode {

	public static final String ID_ATTRIBUTE = "id";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String VALUE_ATTRIBUTE = "value";
	public static final String TYPE_ATTRIBUTE = "type";
	public static final String HREF_ATTRIBUTE = "href";
	public static final String CLASS_ATTRIBUTE = "class";
	public static final String STYLE_ATTRIBUTE = "style";
	public static final String ACTION_ATTRIBUTE = "action";

	private TagNode tag;
	
	HtmlNode(TagNode tag) {
		this.tag = tag;
	}

	public String getTagName() {
		return tag.getName();
	}

	public String getTextContent() {
		return tag.getText().toString();
	}

	public String getAttribute(String name) {
		return tag.getAttributeByName(name);
	}
	
	public String getId() {
		return getAttribute(ID_ATTRIBUTE);
	}
	
	public String getType() {
		return getAttribute(TYPE_ATTRIBUTE);
	}

	public String getValue() {
		return getAttribute(VALUE_ATTRIBUTE);
	}
	
	public String getStyleClass() {
		return getAttribute(CLASS_ATTRIBUTE);
	}
	
	public String getStyle() {
		return getAttribute(STYLE_ATTRIBUTE);
	}
	
	public String getAction() {
		return getAttribute(ACTION_ATTRIBUTE);
	}
	
	public String getHref() {
		return getAttribute(HREF_ATTRIBUTE);
	}
	
	@SuppressWarnings("unchecked")
	public List<HtmlNode> getChildren() {
		List<TagNode> aux = tag.getChildTagList();
		List<HtmlNode> children = new ArrayList<HtmlNode>(aux.size());
		for (TagNode node : aux) {
			children.add((HtmlNode)getTagAsHtmlNodeIfNecessary(node));
		}
		return children;
	}
	
	public HtmlNode findNodeById(String id) throws NodeNotFoundException {
		TagNode child = tag.findElementByAttValue(ID_ATTRIBUTE, id, true, true);
		if (child == null) {
			throw NodeNotFoundException.byId(id);
		}
		return new HtmlNode(child);
	}
	
	public HtmlNode findNodeByTagName(String name) throws NodeNotFoundException, TooManyNodesFoundException {
		List<HtmlNode> nodes = findNodesByTagName(name);
		if (nodes.isEmpty()) {
			throw NodeNotFoundException.byName(name);
		} else if (nodes.size() > 1) {
			throw TooManyNodesFoundException.byName(name, nodes.size());
		}
		return nodes.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<HtmlNode> findNodesByTagName(String name) {
		List<TagNode> nodes = tag.getElementListByName(name, true);
		List<HtmlNode> htmlNodes = new ArrayList<HtmlNode>(nodes.size());
		for (TagNode node : nodes) {
			htmlNodes.add(new HtmlNode(node));
		}
		return htmlNodes;
	}
	
	public HtmlNode findNodeByAttributes(String... attributes) throws NodeNotFoundException, TooManyNodesFoundException {
		List<HtmlNode> nodes = findNodesByAttributes(attributes);
		if (nodes.isEmpty()) {
			throw NodeNotFoundException.byAttributes(toMap(attributes));
		} else if (nodes.size() > 1) {
			throw TooManyNodesFoundException.byAttributes(toMap(attributes), nodes.size());
		}
		
		return nodes.get(0);
	}
	
	public List<HtmlNode> findNodesByAttributes(String... attributes) {
		Map<String, String> attributeMap = toMap(attributes);
		List<HtmlNode> nodes = new ArrayList<HtmlNode>();
		findNodesByAttributesRecursively(tag, attributeMap, nodes);
		return nodes;
	}
	
	protected void findNodesByAttributesRecursively(TagNode node, Map<String, String> attributeMap, List<HtmlNode> nodes) {
        for (Object child : node.getChildren()) {
            if (child instanceof TagNode) {
                TagNode currNode = (TagNode)child;

                boolean satisfy = true;
                for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
                	if (!StringUtils.equals(entry.getValue(), currNode.getAttributeByName(entry.getKey()))) {
                		satisfy = false;
                		break;
                	}
                }
                
                if (satisfy) {
                	nodes.add(new HtmlNode(currNode));
                }

                findNodesByAttributesRecursively(currNode, attributeMap, nodes);
            }
        }
	}

    /**
     * Executa expressão XPath no node.<br>
     * <em>
     *  Exemplos:
     * </em>
     * <code>
     * <ul>
     *      <li>//div//a</li>
     *      <li>//div//a[@id][@class]</li>
     *      <li>/body/*[1]/@type</li>
     *      <li>//div[3]//a[@id][@href='r/n4']</li>
     *      <li>//div[last() >= 4]//./div[position() = last()])[position() > 22]//li[2]//a</li>
     *      <li>//div[2]/@*[2]</li>
     *      <li>data(//div//a[@id][@class])</li>
     *      <li>//p/last()</li>
     *      <li>//body//div[3][@class]//span[12.2<position()]/@id</li>
     *      <li>data(//a['v' < @id])</li>
     * </ul>
     * </code>
     * @param expression
     * @return Elemento resultante.
     * @throws HarvestException Se nenhum ou mais de um node for encontrado.
     */
	@SuppressWarnings("unchecked")
	public <T> T findNodeByXPath(String expression) throws NodeNotFoundException, TooManyNodesFoundException {
		try {
			Object[] result = tag.evaluateXPath(expression);
			if (result.length == 0) {
				throw NodeNotFoundException.byXPath(expression);
			} else if (result.length > 1) {
				throw TooManyNodesFoundException.byXPath(expression, result.length);
			}
			return (T)getTagAsHtmlNodeIfNecessary(result[0]);
		} catch (XPatherException e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * Executa expressão XPath no node.<br>
     * <em>
     *  Exemplos:
     * </em>
     * <code>
     * <ul>
     *      <li>//div//a</li>
     *      <li>//div//a[@id][@class]</li>
     *      <li>/body/*[1]/@type</li>
     *      <li>//div[3]//a[@id][@href='r/n4']</li>
     *      <li>//div[last() >= 4]//./div[position() = last()])[position() > 22]//li[2]//a</li>
     *      <li>//div[2]/@*[2]</li>
     *      <li>data(//div//a[@id][@class])</li>
     *      <li>//p/last()</li>
     *      <li>//body//div[3][@class]//span[12.2<position()]/@id</li>
     *      <li>data(//a['v' < @id])</li>
     * </ul>
     * </code>
     * @param expression
     * @return Elemento resultante.
     * @throws HarvestException Se nenhum ou mais de um node for encontrado.
     */
	@SuppressWarnings("unchecked")
	public <T> List<T> findNodesByXPath(String expression) {
		try {
			Object[] result = tag.evaluateXPath(expression);
			List<T> list = new ArrayList<T>(result.length);
			for (Object item : result) {
				list.add((T)getTagAsHtmlNodeIfNecessary(item));
			}
			return list;
		} catch (XPatherException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean existsById(String id) {
		return tag.findElementByAttValue(ID_ATTRIBUTE, id, true, true) != null;
	}

	public String dump(int abbreviationSize) {
		StringWriter writer = new StringWriter();
		try {
			dump(writer, abbreviationSize);
		} catch (IOException ignored) {
			// Nunca deve acontecer
		}
		return writer.toString();
	}

	public void dump(Writer writer, int abbreviationSize) throws IOException {
		dumpRecursively(tag, writer, 0, abbreviationSize);
	}
	
	@SuppressWarnings("unchecked")
	protected void dumpRecursively(TagNode node, Writer writer, int level, int abbreviationSize) throws IOException {
		writer.append(StringUtils.repeat(" ", level));
		writer.append(node.getName()).append(" - ").append(node.getAttributes().toString());

		String textContent = node.getText().toString();
		if (!StringUtils.isBlank(textContent)) {
			textContent = StringUtils.replaceChars(textContent, "\r\n", "||");
			writer.append(" - ").append(StringUtils.abbreviate(textContent, abbreviationSize));
		}
		
		List<TagNode> children = node.getChildTagList();
		if (!children.isEmpty()) {
			level++;
			for (TagNode child : children) {
				writer.append('\n');
				dumpRecursively(child, writer, level, abbreviationSize);
			}
		}
	}
	
	protected Object getTagAsHtmlNodeIfNecessary(Object obj) {
		if (obj instanceof TagNode) {
			return new HtmlNode((TagNode)obj);
		}
		return obj;
	}
	
	protected Map<String, String> toMap(String... values) {
		if (values.length == 0) {
			return Collections.emptyMap();
		}
		if (values.length % 2 != 0) {
			throw new IllegalArgumentException("Quantidade de valores deve ser multiplo de 2");
		}
		
		Map<String, String> map = new HashMap<String, String>(values.length);
		for (int i = 0; i < values.length; i += 2) {
			map.put(values[i], values[i + 1]);
		}
		
		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof HtmlNode))
			return false;
		HtmlNode other = (HtmlNode) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else {
			if (!tag.getName().equals(other.tag.getName())) {
				return false;
			} else {
				Map<String, Object> attributes = tag.getAttributes();
				Map<String, Object> otherAttributes = other.tag.getAttributes();
				if (!attributes.equals(otherAttributes)) {
					return false;
				}
				
				List<HtmlNode> children = getChildren();
				List<HtmlNode> otherChildren = other.getChildren();
				
				if (children.size() != otherChildren.size()) {
					return false;
				}

				for (HtmlNode node : children) {
					if (!otherChildren.contains(node)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("name=%s, attributes=%s, text=%s", getTagName(), tag.getAttributes(), getTextContent());
	}
}
