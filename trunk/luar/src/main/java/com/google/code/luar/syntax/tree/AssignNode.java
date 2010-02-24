package com.google.code.luar.syntax.tree;

import java.util.*;

import com.google.code.luar.syntax.*;

public class AssignNode extends StatementNode {

	private List<VariableNode> variables;
	
	private List<ExpressionNode> expressions;
	
	public AssignNode() {
		variables = new ArrayList<VariableNode>();
		expressions = new ArrayList<ExpressionNode>();
	}

	public List<ExpressionNode> getExpressions() {
		return Collections.unmodifiableList(expressions);
	}

	public void addExpression(ExpressionNode expression) {
		expressions.add(expression);
	}

	public List<VariableNode> getVariables() {
		return variables;
	}

	public void addVariable(VariableNode variable) {
		variables.add(variable);
	}
	
	public boolean addToken(Token token) {
		return false;
	}
}