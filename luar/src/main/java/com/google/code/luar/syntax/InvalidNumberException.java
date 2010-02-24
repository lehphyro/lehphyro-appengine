package com.google.code.luar.syntax;

public class InvalidNumberException extends InvalidTokenException {

	private static final long serialVersionUID = 6813261629607046012L;

	public InvalidNumberException(String value,
	                              int lineNumber,
	                              int columnNumber) {
		super(value, lineNumber, columnNumber);
	}
}