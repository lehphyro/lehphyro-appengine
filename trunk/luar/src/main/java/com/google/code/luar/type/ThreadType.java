package com.google.code.luar.type;

public class ThreadType extends AbstractType {

	public byte getType() {
		return THREAD;
	}

	public Object getValue() {
		return null;
	}

	protected boolean isLegalValue(Object value) {
		return false;
	}

	protected void doSetValue(Object value) {
	}

}