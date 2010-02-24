package com.google.code.luar.type;

public class UserDataType extends AbstractType {

	private Object value;
	
	public byte getType() {
		return USER_DATA;
	}

	public Object getValue() {
		return value;
	}

	protected boolean isLegalValue(Object value) {
		return true;
	}

	protected void doSetValue(Object value) {
		this.value = value;
	}

}