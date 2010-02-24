package com.google.code.luar.type;

/**
 * Defines a lua type.
 */
public interface Type {
	byte NIL = 0;
	byte NUMBER = 1;
	byte STRING = 2;
	byte TABLE = 3;
	byte FUNCTION = 4;
	byte BOOLEAN = 5;
	byte THREAD = 6;
	byte USER_DATA = 7;
	
	byte getType();
	void setValue(Object value);
	Object getValue();
	
	boolean isNil();
	boolean isNumber();
	boolean isString();
	boolean isTable();
	boolean isFunction();
	boolean isBoolean();
	boolean isThread();
	boolean isUserData();
	
	boolean isFalse();
	boolean getValueAsBoolean();
}