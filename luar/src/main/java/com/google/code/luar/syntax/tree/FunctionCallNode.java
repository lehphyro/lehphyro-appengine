package com.google.code.luar.syntax.tree;

import java.util.*;

public class FunctionCallNode extends ExpressionNode {

	// Types of call
	public static final byte STATEMENT_CALL = 0;
	public static final byte MIDDLE_OF_EXPRESSION = 1;
	public static final byte END_OF_EXPRESSION = 2;
	
	private byte type;
	
	private ExpressionNode expression;
	
	private List arguments;
	
	public FunctionCallNode() {
		arguments = new ArrayList();
	}

	public ExpressionNode getExpression() {
		return expression;
	}

	public void setExpression(ExpressionNode expression) {
		this.expression = expression;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public List getArguments() {
		return Collections.unmodifiableList(arguments);
	}
	
	public void addArgument(ExpressionNode expression) {
		arguments.add(expression);
	}
}