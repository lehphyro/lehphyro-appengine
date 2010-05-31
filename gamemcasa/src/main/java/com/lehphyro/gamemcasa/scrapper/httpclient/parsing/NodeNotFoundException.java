package com.lehphyro.gamemcasa.scrapper.httpclient.parsing;

import java.util.*;

public class NodeNotFoundException extends HtmlParsingException {

	private static final long serialVersionUID = 8892642859136672230L;

	protected NodeNotFoundException(String message, Object... args) {
		super(message, args);
	}
	
	public static NodeNotFoundException byId(String id) {
		NodeNotFoundException exception = new NodeNotFoundException("Node not found with id [%s]", id);
		exception.setId(id);
		return exception;
	}
	
	public static NodeNotFoundException byName(String name) {
		NodeNotFoundException exception = new NodeNotFoundException("Node not found with tag name [%s]", name);
		exception.setName(name);
		return exception;
	}
	
	public static NodeNotFoundException byAttributes(Map<String, String> attributes) {
		NodeNotFoundException exception = new NodeNotFoundException("Node not found with attributes [%s]", attributes);
		exception.setAttributes(attributes);
		return exception;
	}
	
	public static NodeNotFoundException byXPath(String expression) {
		NodeNotFoundException exception = new NodeNotFoundException("Node not found with xpath [%s]", expression);
		exception.setXPath(expression);
		return exception;
	}
}
