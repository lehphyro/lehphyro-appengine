package com.google.code.luar.syntax.tree;

public class IfNodePart {

	private ExpressionNode conditionPart;
	
	private BlockNode thenPart;

	public ExpressionNode getConditionPart() {
		return conditionPart;
	}

	public BlockNode getThenPart() {
		return thenPart;
	}
}