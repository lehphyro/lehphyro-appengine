package com.google.code.luar.syntax.tree;

import java.util.*;

import com.google.code.luar.syntax.*;

public class AssignNode extends StatementNode {

	private List variables;
	
	private List expressions;
	
	public AssignNode() {
		variables = new ArrayList();
		expressions = new ArrayList();
	}

	public List getExpressions() {
		return Collections.unmodifiableList(expressions);
	}

	public void addExpression(ExpressionNode expression) {
		expressions.add(expression);
	}

	public List getVariables() {
		return variables;
	}

	public void addVariable(VariableNode variable) {
		variables.add(variable);
	}
	
	public boolean addToken(Token token) {
		return false;
	}
}