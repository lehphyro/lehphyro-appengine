package com.google.code.luar.syntax;

public class InvalidCommentException extends InvalidTokenException {

	private static final long serialVersionUID = -102273344393626440L;

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