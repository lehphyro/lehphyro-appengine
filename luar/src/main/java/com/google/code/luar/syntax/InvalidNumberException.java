package com.google.code.luar.syntax;

public class InvalidNumberException extends InvalidTokenException {

	public InvalidNumberException(String value,
	                              int lineNumber,
	                              int columnNumber) {
		super(value, lineNumber, columnNumber);
	}
}