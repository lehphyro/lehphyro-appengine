package com.google.code.luar.syntax.tree;

import java.util.*;

import com.google.code.luar.syntax.*;

public class FunctionDefinitionNode extends StatementNode {

	private VariableNode variable;
	
	private BlockNode body;
	
	private List<ExpressionNode> arguments;

	public FunctionDefinitionNode() {
		arguments = new ArrayList<ExpressionNode>();
	}
	
	public BlockNode getBody() {
		return body;
	}

	public void setBody(BlockNode body) {
		this.body = body;
	}

	public VariableNode getVariable() {
		return variable;
	}

	public void setVariable(VariableNode variable) {
		this.variable = variable;
	}

	public List<ExpressionNode> getArguments() {
		return Collections.unmodifiableList(arguments);
	}
	
	public void addArgument(ExpressionNode expression) {
		arguments.add(expression);
	}
	
	public boolean addToken(Token token) {
		return false;
	}
}