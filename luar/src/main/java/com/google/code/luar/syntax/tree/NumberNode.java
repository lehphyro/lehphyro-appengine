package com.google.code.luar.syntax.tree;

public class NumberNode extends ExpressionNode {

	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}