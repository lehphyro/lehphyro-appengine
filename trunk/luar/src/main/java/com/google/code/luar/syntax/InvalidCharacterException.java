package com.google.code.luar.syntax;

public class InvalidCharacterException extends InvalidTokenException {

	public InvalidCharacterException(char character,
	                                 int lineNumber,
	                                 int columnNumber) {
		super(Character.toString(character),
			  lineNumber,
			  columnNumber);
	}

}