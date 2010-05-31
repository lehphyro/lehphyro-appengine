package com.lehphyro.gamemcasa.scrapper.httpclient.parsing;

import java.util.*;

public class TooManyNodesFoundException extends HtmlParsingException {

	private static final long serialVersionUID = 3867928047884233591L;

	private int size;

	protected TooManyNodesFoundException(String message, Object... args) {
		super(message, args);
	}

	public static TooManyNodesFoundException byName(String name, int size) {
		TooManyNodesFoundException exception = new TooManyNodesFoundException("Too many nodes (%d) found with name [%s]", size, name);
		exception.setName(name);
		exception.setSize(size);
		return exception;
	}

	public static TooManyNodesFoundException byAttributes(Map<String, String> attributes, int size) {
		TooManyNodesFoundException exception = new TooManyNodesFoundException("Too many nodes (%d) found with attributes [%s]", size, attributes);
		exception.setAttributes(attributes);
		exception.setSize(size);
		return exception;
	}
	
	public static TooManyNodesFoundException byXPath(String expression, int size) {
		TooManyNodesFoundException exception = new TooManyNodesFoundException("Too many nodes (%d) found with xpath [%s]", size, expression);
		exception.setXPath(expression);
		exception.setSize(size);
		return exception;
	}

	public int getSize() {
		return size;
	}
	
	protected void setSize(int size) {
		this.size = size;
	}
}
