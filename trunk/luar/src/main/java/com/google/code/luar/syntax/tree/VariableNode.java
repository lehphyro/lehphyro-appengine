package com.google.code.luar.syntax.tree;

public class VariableNode extends ExpressionNode {
	
	// Types of variables
	public static final byte GLOBAL = 0;
	public static final byte LOCAL = 1;
	public static final byte TABLE = 2;
	
	private byte type;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
}