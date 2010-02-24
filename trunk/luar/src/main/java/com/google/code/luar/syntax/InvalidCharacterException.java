package com.google.code.luar.syntax;

public class InvalidCharacterException extends InvalidTokenException {

	private static final long serialVersionUID = -4943458453095982473L;

	public InvalidCharacterException(char character,
	                                 int lineNumber,
	                                 int columnNumber) {
		super(Character.toString(character),
			  lineNumber,
			  columnNumber);
	}

}