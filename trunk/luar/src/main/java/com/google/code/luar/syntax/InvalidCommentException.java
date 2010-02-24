package com.google.code.luar.syntax;

public class InvalidCommentException extends InvalidTokenException {

	public InvalidCommentException(String value,
	                              int lineNumber,
	                              int columnNumber) {
		super(value, lineNumber, columnNumber);
	}
	
	public String getMessage() {
		String msg = "invalid comment ";
        msg += " at line: " + getLineNumber();
        msg += " column: " + getColumnNumber();
        
        return msg;
	}
}