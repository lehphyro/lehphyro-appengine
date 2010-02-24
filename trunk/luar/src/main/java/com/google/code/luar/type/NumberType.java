package com.google.code.luar.type;

public class NumberType extends AbstractType {
	
	private Number value;

	public byte getType() {
		return NUMBER;
	}

	public Object getValue() {
		return value;
	}

	protected boolean isLegalValue(Object value) {
		if (value == null) {
			return true;
		}
		return (value instanceof Number);
	}

	protected void doSetValue(Object value) {
		this.value = (Number)value;
	}

}