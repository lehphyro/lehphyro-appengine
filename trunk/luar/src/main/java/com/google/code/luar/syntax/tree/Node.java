package com.google.code.luar.syntax.tree;

import com.google.code.luar.syntax.*;

/**
 * Represents a syntax node of a lua program.
 */
public interface Node {
	/**
	 * Parent node of this node.
	 * 
	 * @param parent
	 */
	void setParent(Node parent);
	
	Node getParent();

	/**
	 * Pass a new token to be processed by the node.
	 * 
	 * @param token Token to be incorporated to the node structure.
	 * @return True if the node is complete.
	 */
	boolean addToken(Token token);
}