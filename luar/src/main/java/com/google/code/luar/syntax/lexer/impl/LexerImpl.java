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

	private final LexerInput input;

	private int currentLine;
	private int currentColumn;
	private StringBuffer buffer;

	private Token.Type currentTokenType;
    private String currentTokenValue;
    private int currentTokenColumn;
    private int currentTokenLine;
    
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
		this.buffer = new StringBuffer();
	}

	public Token nextToken() {
		try {
			while (currentTokenType == null || currentTokenType == Token.Type.SKIP) {
				prepareBuffer();
				switch (lookAhead(1)) {
					case '(':
						doLeftParenthesis();
						break;
					case ')':
						doRightParenthesis();
						break;
					case ']':
						doRightBracket();
						break;
					case '{':
						doLeftCurly();
						break;
					case '}':
						doRightCurly();
						break;
					case ':':
						doColon();
						break;
					case ',':
						doComma();
						break;
					case ';':
						doSemicolon();
						break;
					case '\t':
					case '\n':
					case '\u000c':
					case '\r':
					case ' ':
						doWhiteSpace();
						break;
					case '\'':
						doStringLiteral(StringType.SINGLE_QUOTE_DELIMITER);
						break;
					case '"':
						doStringLiteral(StringType.DOUBLE_QUOTE_DELIMITER);
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
						doNumber();
						break;
					case '+':
						doPlus();
						break;
					case '*':
						doMultiply();
						break;
					case '/':
						doDivide();
						break;
					case '^':
						doPlus();
						break;
					case '#':
						doComment();
						break;
					default:
						if (lookAhead(1) == '=' && lookAhead(2) == '=') {
							doEqual();
						} else if (lookAhead(1) == '~' && lookAhead(2) == '=') {
							doNotEqual();
						} else if (lookAhead(1) == '>' && lookAhead(2) == '=') {
							doGreaterOrEqual();
						} else if (lookAhead(1) == '<' && lookAhead(2) == '=') {
							doLessOrEqual();
						} else if (lookAhead(1) == '-' && lookAhead(2) == '-') {
							doComment();
						} else if (lookAhead(1) == '[' && lookAhead(2) == '[') {
							doStringLiteral(StringType.DOUBLE_SQUARE_BRACKETS);
						} else if (lookAhead(1) == '>') {
							doGreater();
						} else if (lookAhead(1) == '<') {
							doLess();
						} else if (lookAhead(1) == '-') {
							doMinus();
						} else if (lookAhead(1) == '[') {
							doLeftBracket();
						} else if (lookAhead(1) == '=') {
							doAssign();
						} else if (lookAhead(1) == '.' && lookAhead(2) == '.' && lookAhead(3) == '.') {
							doTripleDots();
						} else if (lookAhead(1) == '.' && lookAhead(2) == '.') {
							doDoubleDots();
						} else if (lookAhead(1) == '.') {
							doDot();
						} else if (Character.isJavaIdentifierStart(lookAhead(1))) {
							doIdentifier();
						} else if (isEOF(lookAhead(1))) {
							doEOF();
						} else {
							throw new InvalidCharacterException(lookAhead(1), currentLine, currentColumn);
						}
				}
			}
			
			Token token = new Token(currentTokenType, currentTokenValue, currentTokenLine, currentTokenColumn);
			resetTokenAttributes();
			
			return token;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void close() {
		input.close();
	}
	
	protected void doIdentifier() throws IOException {
		int column = currentColumn;
		boolean keepReading = true;
		while (keepReading) {
			if (Character.isJavaIdentifierPart(lookAhead(1))) {
				buffer.append(readNext());
			} else {
				keepReading = false;
			}
		}
		String value = buffer.toString();
		Token.Type typeFound = (Token.Type)Token.LITERALS.get(value);
		if (typeFound == null) {
			typeFound = Token.Type.IDENTIFIER;
		}
		setTokenAttributes(typeFound, value, column);
	}

	protected void doComment() throws IOException {
		// Read the first two '--'
		readNext(); readNext();
		
		// Verify if it´s a multi-line comment
		if (lookAhead(1) == '[' && lookAhead(2) == '[') {
			try {
				readBracketedString();
			} catch (InvalidStringException e) {
				throw new InvalidCommentException(null, currentLine, currentColumn);
			}
		} else { // Single-line comment
			while (!isNewLine(lookAhead(1)) && !isEOF(lookAhead(1))) {
				readNext(); // Just read until line-break or EOF
			}
		}
		setTokenAttributes(Token.Type.SKIP, null, currentColumn);
	}

	protected void doNumber() throws IOException {
		int column = currentColumn;
		
		while (isNumericValue(lookAhead(1), lookAhead(2), lookAhead(3))) {
			buffer.append(readNext());
		}
		try {
			Double value = Double.valueOf(buffer.toString());
			setTokenAttributes(Token.Type.NUMBER_LITERAL, value.toString(), column);
		} catch (NumberFormatException e) {
			throw new InvalidNumberException(buffer.toString(), currentLine, currentColumn);
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

	protected void doStringLiteral(byte delimiter) throws IOException {
		int line = currentLine;
		int column = currentColumn;
		
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
		
		setTokenAttributes(Token.Type.STRING_LITERAL, buffer.toString(), line, column);
	}
	
	protected void doStringLiteralSingleDelimiter(char delimiter) throws IOException {
		// Read the first delimiter
		readNext();
		
		char read = lookAhead(1);
		while (read != delimiter && !isNewLine(read) && !isEOF(read)) {
			if (read == '\\' && lookAhead(2) == delimiter) { // Append escaped delimiter
				readNext();
				buffer.append(readNext());
			} else {
				buffer.append(readNext());
				read = lookAhead(1);
			}
		}
		// Read second delimiter
		read = readNext();
		if (read != delimiter) {
			throw new InvalidStringException(buffer.toString(), currentLine, currentColumn);
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

	protected void doWhiteSpace() throws IOException {
		while (isWhiteSpace(lookAhead(1))) {
			readNext(); // Just read while white space
		}
		setTokenAttributes(Token.Type.SKIP, null, currentColumn);
	}

	protected void doLess() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.LESS, null, column);
	}

	protected void doGreater() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.GREATER, null, column);
	}

	protected void doEqual() throws IOException {
		int column = currentColumn;
		readNext(); readNext();
		setTokenAttributes(Token.Type.EQUAL, null, column);
	}

	protected void doNotEqual() throws IOException {
		int column = currentColumn;
		readNext(); readNext();
		setTokenAttributes(Token.Type.NOT_EQUAL, null, column);
	}

	protected void doLessOrEqual() throws IOException {
		int column = currentColumn;
		readNext(); readNext();
		setTokenAttributes(Token.Type.LESS_OR_EQUAL, null, column);
	}

	protected void doGreaterOrEqual() throws IOException {
		int column = currentColumn;
		readNext(); readNext();
		setTokenAttributes(Token.Type.GREATER_OR_EQUAL, null, column);
	}

	protected void doPlus() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.PLUS, null, column);
	}

	protected void doMinus() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.MINUS, null, column);
	}

	protected void doMultiply() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.MULTIPLY, null,column);
	}

	protected void doDivide() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.DIVIDE, null, column);
	}

	protected void doAssign() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.ASSIGN, null, column);
	}

	protected void doComma() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.COMMA, null, column);
	}

	protected void doColon() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.COLON, null, column);
	}
	
	protected void doSemicolon() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.SEMICOLON, null, column);
	}

	protected void doRightCurly() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.RIGHT_CURLY, null, column);
	}

	protected void doLeftCurly() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.LEFT_CURLY, null, column);
	}

	protected void doLeftBracket() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.LEFT_BRACKET, null, column);
	}

	protected void doRightBracket() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.RIGHT_BRACKET, null, column);
	}

	protected void doLeftParenthesis() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.LEFT_PARENTHESIS, null, column);
	}
	
	protected void doRightParenthesis() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.RIGHT_PARENTHESIS, null, column);
	}
	
	protected void doTripleDots() throws IOException {
		int column = currentColumn;
		readNext();	readNext();	readNext();
		setTokenAttributes(Token.Type.TRIPLE_DOTS, null, column);
	}
	
	protected void doDoubleDots() throws IOException {
		int column = currentColumn;
		readNext();	readNext();
		setTokenAttributes(Token.Type.DOUBLE_DOTS, null, column);
	}
	
	protected void doDot() throws IOException {
		int column = currentColumn;
		readNext();
		setTokenAttributes(Token.Type.DOT, null, column);
	}

	protected void doEOF() throws IOException {
		setTokenAttributes(Token.Type.EOF, null, currentColumn);
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
		if (isNewLine(lookAhead(1))) {
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
					throw new InvalidStringException(buffer.toString(), currentLine, currentColumn);
				}
			}
		}
		// Pass the last bracket
		readNext();
	}

	protected void setTokenAttributes(Token.Type type, String value, int column) {
		setTokenAttributes(type, value, currentLine, column);
	}

	protected void setTokenAttributes(Token.Type type, String value, int line, int column) {
		currentTokenType = type;
		currentTokenValue = value;
		currentTokenLine = line;
		currentTokenColumn = column;
	}

	protected void resetTokenAttributes() {
		setTokenAttributes(null, null, 0);
	}
	
	protected boolean isWhiteSpace(char character) {
		return character == ' ' ||
			   character == '\r' ||
			   character == '\t' ||
			   character == '\u000c' ||
			   isNewLine(character);
	}
	
	protected boolean isNewLine(char character) {
		return character == '\n';
	}
	
	protected boolean isEOF(char character) {
		return character == LexerInput.EOF;
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

	protected void prepareBuffer() {
		buffer.delete(0, buffer.length());
	}
	
}