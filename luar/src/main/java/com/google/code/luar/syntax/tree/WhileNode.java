package com.google.code.luar.syntax.tree;

import com.google.code.luar.syntax.*;

public class WhileNode extends StatementNode {

	private ExpressionNode conditionPart;
	
	private BlockNode bodyPart;

	public BlockNode getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(BlockNode bodyPart) {
		this.bodyPart = bodyPart;
	}

	public ExpressionNode getConditionPart() {
		return conditionPart;
	}

	public void setConditionPart(ExpressionNode conditionPart) {
		this.conditionPart = conditionPart;
	}
	
	public boolean addToken(Token token) {
		return false;
	}
}