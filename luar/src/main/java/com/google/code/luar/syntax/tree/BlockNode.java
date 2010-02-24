package com.google.code.luar.syntax.tree;

/**
 * A block is a list of statement like a chunk but it has a
 * parent.
 */
public class BlockNode extends ChunkNode {

	private Node parent;
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getParent() {
		return parent;
	}
}