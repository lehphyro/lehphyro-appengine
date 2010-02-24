package com.google.code.luar.type;

public abstract class AbstractType implements Type {

	public boolean isBoolean() {
		return getType() == BOOLEAN;
	}

	public boolean isFunction() {
		return getType() == FUNCTION;
	}

	public boolean isNil() {
		return getType() == NIL;
	}

	public boolean isNumber() {
		return getType() == NUMBER;
	}

	public boolean isString() {
		return getType() == STRING;
	}

	public boolean isTable() {
		return getType() == TABLE;
	}

	public boolean isThread() {
		return getType() == THREAD;
	}

	public boolean isUserData() {
		return getType() == USER_DATA;
	}
	
	public boolean isFalse() {
		return (isNil() || (isBoolean() && getValueAsBoolean() == false));
	}
	
	public boolean getValueAsBoolean() {
		if (!isBoolean()) {
			throw new IllegalArgumentException("Cannot type as boolean. Type is " + getType());
		}
		return ((Boolean)getValue()).booleanValue();
	}

	public void setValue(Object value) {
		if (isLegalValue(value)) {
			doSetValue(value);
		} else {
			throw new IllegalArgumentException("Illegal value for type '" + getType() + "': " + value);
		}
	}

	protected abstract boolean isLegalValue(Object value);

	protected abstract void doSetValue(Object value);
	
}