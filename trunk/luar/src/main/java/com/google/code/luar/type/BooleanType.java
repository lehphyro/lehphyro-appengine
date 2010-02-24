package com.google.code.luar.type;

public class BooleanType extends AbstractType {

	private Boolean value;
	
	public byte getType() {
		return BOOLEAN;
	}

	public Object getValue() {
		return value;
	}

	protected boolean isLegalValue(Object value) {
		return (value instanceof Boolean);
	}

	protected void doSetValue(Object value) {
		this.value = (Boolean)value;
	}

}