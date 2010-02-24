package com.google.code.luar.syntax;

public abstract class InvalidTokenException extends ParserException {

	private static final long serialVersionUID = -2735353101295034723L;

	private String value;
	
	private int lineNumber;
	
	private int columnNumber;

	public InvalidTokenException(String value,
	                             int lineNumber,
	                             int columnNumber) {
		this.value = value;
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}
	
	protected int getColumnNumber() {
		return columnNumber;
	}
	
	protected int getLineNumber() {
		return lineNumber;
	}

	public String getMessage() {
		String msg = "invalid token: \'";
        msg += value;
        msg += "\' at line: " + getLineNumber();
        msg += " column: " + getColumnNumber();
        
        return msg;
	}
}