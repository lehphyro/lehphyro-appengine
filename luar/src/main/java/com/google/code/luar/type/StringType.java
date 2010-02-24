package com.google.code.luar.type;

public class StringType extends AbstractType {
	// Types of delimiters
	public static final byte SINGLE_QUOTE_DELIMITER = 0;
	public static final byte DOUBLE_QUOTE_DELIMITER = 1;
	public static final byte DOUBLE_SQUARE_BRACKETS = 2;

	private String value;
	
	public byte getType() {
		return STRING;
	}

	public Object getValue() {
		return value;
	}

	protected boolean isLegalValue(Object value) {
		if (value == null) {
			return true;
		}
		return (value instanceof String);
	}

	protected void doSetValue(Object value) {
		this.value = (String)value;
	}

}