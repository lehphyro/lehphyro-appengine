package com.google.code.luar.type;

public class NilType extends AbstractType {

	public byte getType() {
		return NIL;
	}

	public Object getValue() {
		return null;
	}

	protected boolean isLegalValue(Object value) {
		return false;
	}

	protected void doSetValue(Object value) {
		throw new IllegalArgumentException("Cannot set value for nil type");
	}

}