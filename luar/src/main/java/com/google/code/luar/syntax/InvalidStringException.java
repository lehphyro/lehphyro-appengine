package com.google.code.luar.syntax;

public class InvalidStringException extends InvalidTokenException {

	public InvalidStringException(String value,
	                              int lineNumber,
	                              int columnNumber) {
		super(value, lineNumber, columnNumber);
	}
}