package com.google.code.luar.syntax.tree;

public class StringLiteralNode extends ExpressionNode {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}