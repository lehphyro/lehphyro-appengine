package com.google.code.luar.syntax.lexer.impl;

import java.io.*;

import com.google.code.luar.syntax.*;
import com.google.code.luar.syntax.input.*;
import com.google.code.luar.syntax.input.impl.*;
import com.google.code.luar.syntax.lexer.*;
import com.google.code.luar.type.*;

/**
 * Lexical analyzer.
 */
public class LexerImpl implements Lexer {

	private LexerInput input;
	
	private int currentLine;
	
	private int currentColumn;
	
	private StringBuffer buffer;

	public LexerImpl() {
		this((LexerInput)null);
	}
	
	public LexerImpl(String input) {
		this(new LexerInputImpl(new StringReader(input)));
	}
	
	public LexerImpl(Reader reader) {
		this(new LexerInputImpl(reader));
	}
	
	public LexerImpl(LexerInput input) {
		this.input = input;
		this.currentLine = 1;
		this.currentColumn = 1;
		buffer = new StringBuffer();
	}

	public void setInput(String input) {
		setReader(new StringReader(input));
	}
	
	public void setReader(Reader reader) {
		this.input = new LexerInputImpl(reader);
	}
	
	public Token nextToken() {
		try {
			Token token = new Token(Token.UNKNOWN);
			
			while (token.getType() == Token.UNKNOWN ||
				   token.getType() == Token.SKIP) {
				prepareBuffer();
				switch (lookAhead(1)) {
					case '(':
						doLeftParenthesis(token);
						break;
					case ')':
						doRightParenthesis(token);
						break;
					case ']':
						doRightBrack(token);
						break;
					case '{':
						doLeftCurly(token);
						break;
					case '}':
						doRightCurly(token);
						break;
					case ':':
						doColon(token);
						break;
					case ',':
						doComma(token);
						break;
					case ';':
						doSemicolon(token);
						break;
					case '\t':
					case '\n':
					case '\u000c':
					case '\r':
					case ' ':
						doWhiteSpace(token);
						break;
					case '\'':
						doStringLiteral(token, StringType.SINGLE_QUOTE_DELIMITER);
						break;
					case '"':
						doStringLiteral(token, StringType.DOUBLE_QUOTE_DELIMITER);
						break;
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						doNumber(token);
						break;
					case '+':
						doPlus(token);
						break;
					case '*':
						doMultiply(token);
						break;
					case '/':
						doDivide(token);
						break;
					case '^':
						doPlus(token);
						break;
					case '#':
						doComment(token);
						break;
					default:
						if (lookAhead(1) == '=' && lookAhead(2) == '=') {
							doEqual(token);
						} else if (lookAhead(1) == '~' && lookAhead(2) == '=') {
							doNotEqual(token);
						} else if (lookAhead(1) == '>' && lookAhead(2) == '=') {
							doGreaterOrEqual(token);
						} else if (lookAhead(1) == '<' && lookAhead(2) == '=') {
							doLessOrEqual(token);
						} else if (lookAhead(1) == '-' && lookAhead(2) == '-') {
							doComment(token);
						} else if (lookAhead(1) == '[' && lookAhead(2) == '[') {
							doStringLiteral(token, StringType.DOUBLE_SQUARE_BRACKETS);
						} else if (lookAhead(1) == '>') {
							doGreater(token);
						} else if (lookAhead(1) == '<') {
							doLess(token);
						} else if (lookAhead(1) == '-') {
							doMinus(token);
						} else if (lookAhead(1) == '[') {
							doLeftBrack(token);
						} else if (lookAhead(1) == '=') {
							doAssign(token);
						} else if (lookAhead(1) == '.' && lookAhead(2) == '.' && lookAhead(3) == '.') {
							doTripleDots(token);
						} else if (lookAhead(1) == '.' && lookAhead(2) == '.') {
							doDoubleDots(token);
						} else if (lookAhead(1) == '.') {
							doDot(token);
						} else if (Character.isJavaIdentifierStart(lookAhead(1))) {
							doIdentifier(token);
						} else {
							if (isEOF(lookAhead(1))) { // EOF
								doEOF(token);
							} else {
								throw new InvalidCharacterException(lookAhead(1), getLine(), getColumn());
							}
						}
				}
			}
			
			return token;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void close() {
		input.close();
	}
	
	protected void doIdentifier(Token token) throws IOException {
		int column = getColumn();
		boolean keepReading = true;
		while (keepReading) {
			if (Character.isJavaIdentifierPart(lookAhead(1))) {
				buffer.append(readNext());
			} else {
				keepReading = false;
			}
		}
		String value = buffer.toString();
		Byte typeFound = (Byte)Token.literals.get(value);
		byte type;
		if (typeFound == null) {
			type = Token.IDENTIFIER;
		} else {
			type = typeFound.byteValue();
		}
		setTokenAttributes(type, value, column, token);
	}

	protected void doComment(Token token) throws IOException {
		// Read the first two '--'
		readNext(); readNext();
		
		// Verify if it´s a multi-line comment
		if (lookAhead(1) == '[' && lookAhead(2) == '[') {
			try {
				readBracketedString();
			} catch (InvalidStringException e) {
				throw new InvalidCommentException(null, getLine(), getColumn());
			}
		} else { // Single-line comment
			while (!isNewLine(lookAhead(1)) && !isEOF(lookAhead(1))) {
				readNext(); // Just read until line-break or EOF
			}
		}
		token.setType(Token.SKIP);
	}

	protected void doNumber(Token token) throws IOException {
		int column = getColumn();
		
		while (isNumericValue(lookAhead(1), lookAhead(2), lookAhead(3))) {
			buffer.append(readNext());
		}
		try {
			Double value = Double.valueOf(buffer.toString());
			setTokenAttributes(Token.NUMBER_LITERAL, value.toString(), column, token);
		} catch (NumberFormatException e) {
			throw new InvalidNumberException(buffer.toString(), getLine(), getColumn());
		}
	}
	
	protected boolean isNumericValue(char character1,
	                                 char character2,
	                                 char character3) {
		if (Character.isDigit(character1)) {
			return true;
		} else if (character1 == '.' && Character.isDigit(character2)) {
			return true;
		} else if (character1 == 'e' || character1 == 'E') {
			if (Character.isDigit(character2)) {
				return true;
			} else if (character2 == '-' && Character.isDigit(character3)) {
				return true;
			}
		}
		return false;
	}

	protected void doStringLiteral(Token token, byte delimiter) throws IOException {
		int column = getColumn();
		
		switch (delimiter) {
			case StringType.SINGLE_QUOTE_DELIMITER:
				doStringLiteralSingleDelimiter('\'');
				break;
			case StringType.DOUBLE_QUOTE_DELIMITER:
				doStringLiteralSingleDelimiter('"');
				break;
			case StringType.DOUBLE_SQUARE_BRACKETS:
				doStringLiteralDoubleBrackets();
				break;
			default:
				throw new IllegalArgumentException("Unknown delimiter type for string literal: " + delimiter);
		}
		
		setTokenAttributes(Token.STRING_LITERAL, buffer.toString(), column, token);
	}
	
	protected void doStringLiteralSingleDelimiter(char delimiter) throws IOException {
		// Read the first delimiter
		readNext();
		
		char read = lookAhead(1);
		while (read != delimiter &&
			  !isNewLine(read) &&
			  !isEOF(read)) {
			buffer.append(readNext());
			read = lookAhead(1);
		}
		// Read second delimiter
		read = readNext();
		if (read != delimiter) {
			throw new InvalidStringException(buffer.toString(), getLine(), getColumn());
		}
		
		processStringLiteral();
	}
	
	protected void doStringLiteralDoubleBrackets() throws IOException {
		readBracketedString();
		processStringLiteral();
	}
	
	protected void processStringLiteral() {
		int start, end = "\\newline".length();

		while ((start = buffer.indexOf("\\newline")) >= 0) {
			buffer.replace(start, start + end, "\\n");
		}
	}

	protected void doWhiteSpace(Token token) throws IOException {
		while (isWhiteSpace(lookAhead(1))) {
			readNext(); // Just read while white space
		}
		token.setType(Token.SKIP);
	}

	protected void doLess(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.LESS, null, column, token);
	}

	protected void doGreater(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.GREATER, null, column, token);
	}

	protected void doEqual(Token token) throws IOException {
		int column = getColumn();
		readNext(); readNext();
		setTokenAttributes(Token.EQUAL, null,column, token);
	}

	protected void doNotEqual(Token token) throws IOException {
		int column = getColumn();
		readNext(); readNext();
		setTokenAttributes(Token.NOT_EQUAL, null, column, token);
	}

	protected void doLessOrEqual(Token token) throws IOException {
		int column = getColumn();
		readNext(); readNext();
		setTokenAttributes(Token.LESS_OR_EQUAL, null, column, token);
	}

	protected void doGreaterOrEqual(Token token) throws IOException {
		int column = getColumn();
		readNext(); readNext();
		setTokenAttributes(Token.GREATER_OR_EQUAL, null, column, token);
	}

	protected void doPlus(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.PLUS, null, column, token);
	}

	protected void doMinus(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.MINUS, null, column, token);
	}

	protected void doMultiply(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.MULTIPLY, null,column, token);
	}

	protected void doDivide(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.DIVIDE, null, column, token);
	}

	protected void doAssign(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.ASSIGN, null, column, token);
	}

	protected void doComma(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.COMMA, null, column, token);
	}

	protected void doColon(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.COLON, null, column, token);
	}
	
	protected void doSemicolon(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.SEMICOLON, null, column, token);
	}

	protected void doRightCurly(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.RIGHT_CURLY, null, column, token);
	}

	protected void doLeftCurly(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.LEFT_CURLY, null, column, token);
	}

	protected void doLeftBrack(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.LEFT_BRACK, null, column, token);
	}

	protected void doRightBrack(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.RIGHT_BRACK, null, column, token);
	}

	protected void doLeftParenthesis(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.LEFT_PARENTHESIS, null, column, token);
	}
	
	protected void doRightParenthesis(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.RIGHT_PARENTHESIS, null, column, token);
	}
	
	protected void doTripleDots(Token token) throws IOException {
		int column = getColumn();
		readNext();	readNext();	readNext();
		setTokenAttributes(Token.TRIPLE_DOTS, null, column, token);
	}
	
	protected void doDoubleDots(Token token) throws IOException {
		int column = getColumn();
		readNext();	readNext();
		setTokenAttributes(Token.DOUBLE_DOTS, null, column, token);
	}
	
	protected void doDot(Token token) throws IOException {
		int column = getColumn();
		readNext();
		setTokenAttributes(Token.DOT, null, column, token);
	}

	protected void doEOF(Token token) throws IOException {
		token.setType(Token.EOF);
	}

	protected char lookAhead(int length) throws IOException {
		return input.readForLookAhead(length);
	}
	
	protected char readNext() throws IOException {
		char read = input.read();
		updateColumn(read);
		updateLine(read);
		
		return read;
	}
	
	protected void readBracketedString() throws IOException {
		int opened = 1;
		int closed = 0;
		
		// Pass the first two [[
		readNext(); readNext();
		
		// If next is newline, does not include
		if (lookAhead(1) == '\n') {
			readNext();
		}
		
		char read;
		while (opened != closed) {
			read = readNext();
			
			if (read == '[' && lookAhead(1) == '[') {
				opened++;
			} else if (read == ']' && lookAhead(1) == ']') {
				closed++;
			}
			if (opened != closed) {
				buffer.append(read);
				if (isEOF(read)) {
					throw new InvalidStringException(buffer.toString(), getLine(), getColumn());
				}
			}
		}
		// Pass the last bracket
		readNext();
	}

	protected boolean isWhiteSpace(char character) {
		return character == ' ' ||
			   character == '\t' ||
			   character == '\u000c' ||
			   isNewLine(character);
	}
	
	protected boolean isNewLine(char character) {
		return character == '\n' ||
			   character == '\r';
	}
	
	protected boolean isEOF(char character) {
		return character == LexerInput.EOF;
	}
	
	protected void setTokenAttributes(byte type,
	                                  String value,
	                                  int column,
	                                  Token token) {
		token.setType(type);
		token.setValue(value);
		token.setColumn(column);
		token.setLine(getLine());
	}
	
	protected void updateLine(char read) {
		if (isNewLine(read)) {
			currentLine++;
		}
	}
	
	protected void updateColumn(char read) {
		if (isNewLine(read)) {
			currentColumn = 1;
		} else {
			currentColumn++;
		}
	}

	protected int getLine() {
		return currentLine;
	}

	protected int getColumn() {
		return currentColumn;
	}

	protected void prepareBuffer() {
		buffer.delete(0, buffer.length());
	}
	
}