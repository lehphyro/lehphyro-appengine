package com.google.code.luar.syntax.tree;

public class NumericForNode extends ForNode {

	private VariableNode variable;
	
	private ExpressionNode initExpression;
	
	private ExpressionNode limitExpression;
	
	private ExpressionNode stepExpression;

	public ExpressionNode getInitExpression() {
		return initExpression;
	}

	public void setInitExpression(ExpressionNode initExpression) {
		this.initExpression = initExpression;
	}

	public ExpressionNode getLimitExpression() {
		return limitExpression;
	}

	public void setLimitExpression(ExpressionNode limitExpression) {
		this.limitExpression = limitExpression;
	}

	public ExpressionNode getStepExpression() {
		return stepExpression;
	}

	public void setStepExpression(ExpressionNode stepExpression) {
		this.stepExpression = stepExpression;
	}

	public VariableNode getVariable() {
		return variable;
	}

	public void setVariable(VariableNode variable) {
		this.variable = variable;
	}
}