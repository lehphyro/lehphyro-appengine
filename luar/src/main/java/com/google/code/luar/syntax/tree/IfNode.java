package com.google.code.luar.syntax.tree;

import java.util.*;

import com.google.code.luar.syntax.*;

public class IfNode extends StatementNode {

	private List<ExpressionNode> conditionParts;
	
	private BlockNode elsePart;

	public IfNode() {
		conditionParts = new ArrayList<ExpressionNode>();
	}
	
	public List<ExpressionNode> getConditionParts() {
		return Collections.unmodifiableList(conditionParts);
	}
	
	public BlockNode getElsePart() {
		return elsePart;
	}

	public boolean addToken(Token token) {
		// TODO implement
		return false;
	}
}