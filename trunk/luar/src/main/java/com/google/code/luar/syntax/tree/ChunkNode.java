package com.google.code.luar.syntax.tree;

import java.util.*;

import com.google.code.luar.syntax.*;

public class ChunkNode extends AbstractNode {

	private List<StatementNode> statements;
	
	private Node lastNode;
	
	public ChunkNode() {
		statements = new ArrayList<StatementNode>();
	}
	
	public void setParent(Node parent) {
		throw new IllegalArgumentException("A chunk cannot have a parent");
	}
	
	public Node getParent() {
		return null;
	}
	
	public List<StatementNode> getStatements() {
		return Collections.unmodifiableList(statements);
	}
	
	public void addStatement(StatementNode statement) {
		statements.add(statement);
	}
	
	public boolean addToken(Token token) {
		if (lastNode == null) {
			lastNode = newNode(token);
		}
		if (lastNode.addToken(token) == false) {
			statements.add((StatementNode)lastNode);
			lastNode = null;
		}
		return true;
	}
	
	protected Node newNode(Token token) {
		switch (token.getType()) {
			case Token.IF:
				return new IfNode();
			default:
				throw new IllegalArgumentException("Token not supported: " + token);
		}
	}
}