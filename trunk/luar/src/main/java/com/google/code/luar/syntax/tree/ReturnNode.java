package com.google.code.luar.syntax.tree;

import java.util.*;

import com.google.code.luar.syntax.*;

public class ReturnNode extends StatementNode {

	private List<ExpressionNode> expressions;
	
	public ReturnNode() {
		expressions = new ArrayList<ExpressionNode>();
	}

	public List<ExpressionNode> getExpressions() {
		return Collections.unmodifiableList(expressions);
	}
	
	public void addExpression(ExpressionNode expression) {
		expressions.add(expression);
	}
	
	public boolean addToken(Token token) {
		return false;
	}
}