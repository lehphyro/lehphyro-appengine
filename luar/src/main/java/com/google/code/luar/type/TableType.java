package com.google.code.luar.type;

import java.util.*;

public class TableType extends AbstractType {

	private Map value;
	
	public byte getType() {
		return TABLE;
	}

	public Object getValue() {
		return value;
	}

	protected boolean isLegalValue(Object value) {
		if (value == null) {
			return true;
		}
		return (value instanceof Map);
	}

	protected void doSetValue(Object value) {
		this.value = (Map)value;
	}

}