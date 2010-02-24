package com.google.code.luar.syntax.tree;

public abstract class AbstractNode implements Node {

	private Node parent;
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getParent() {
		return parent;
	}
}