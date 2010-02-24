package com.google.code.luar.syntax;

public class InvalidStringException extends InvalidTokenException {

	private static final long serialVersionUID = -5901151748294224491L;

	public InvalidStringException(String value,
	                              int lineNumber,
	                              int columnNumber) {
		super(value, lineNumber, columnNumber);
	}
}