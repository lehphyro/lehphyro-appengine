package com.google.code.luar.syntax.tree;

import java.util.*;

import com.google.code.luar.syntax.*;

public class ReturnNode extends StatementNode {

	private List expressions;
	
	public ReturnNode() {
		expressions = new ArrayList();
	}

	public List getExpressions() {
		return Collections.unmodifiableList(expressions);
	}
	
	public void addExpression(ExpressionNode expression) {
		expressions.add(expression);
	}
	
	public boolean addToken(Token token) {
		return false;
	}
}