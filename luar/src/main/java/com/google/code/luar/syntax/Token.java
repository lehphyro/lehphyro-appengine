package com.google.code.luar.syntax;

import java.util.*;

import com.google.common.collect.*;

public class Token {
	
	public static enum Type {
		// Keywords
		AND, BREAK, DO, ELSE, ELSEIF,
		END, FALSE, FOR, FUNCTION, IF,
		IN, LOCAL, NIL, NOT, OR,
		REPEAT, RETURN, THEN, TRUE, UNTIL, WHILE,
		
		// Other types
		PLUS, MINUS, MULTIPLY, DIVIDE, PERCENT, POW, SHARP,
		EQUAL, NOT_EQUAL, LESS_OR_EQUAL, GREATER_OR_EQUAL, LESS, GREATER, ASSIGN,
		LEFT_PARENTHESIS, RIGHT_PARENTHESIS, LEFT_CURLY, RIGHT_CURLY, LEFT_BRACKET, RIGHT_BRACKET,
		SEMICOLON, COLON, COMMA, DOT, DOUBLE_DOTS, TRIPLE_DOTS,
		
		// Types
		IDENTIFIER, STRING_LITERAL, NUMBER_LITERAL, EOF, SKIP,
	}

	public static final Map<String, Type> LITERALS = ImmutableMap.<String, Type>builder()
		.put("and", Type.AND).put("break", Type.BREAK).put("do", Type.DO).put("else", Type.ELSE).put("elseif", Type.ELSEIF)
		.put("end", Type.END).put("false", Type.FALSE).put("for", Type.FOR).put("function", Type.FUNCTION).put("if", Type.IF)
		.put("in", Type.IN).put("local", Type.LOCAL).put("nil", Type.NIL).put("not", Type.NOT).put("or", Type.OR).put("repeat", Type.REPEAT)
		.put("return", Type.RETURN).put("then", Type.THEN).put("true", Type.TRUE).put("until", Type.UNTIL).put("while", Type.WHILE)
		
		.put("+", Type.PLUS).put("-", Type.MINUS).put("*", Type.MULTIPLY).put("/", Type.DIVIDE).put("%", Type.PERCENT).put("^", Type.POW)
		.put("#", Type.SHARP).put("==", Type.EQUAL).put("~=", Type.NOT_EQUAL).put("<=", Type.LESS_OR_EQUAL).put(">=", Type.GREATER_OR_EQUAL)
		.put("<", Type.LESS).put(">", Type.GREATER).put("=", Type.ASSIGN).put("(", Type.LEFT_PARENTHESIS).put(")", Type.RIGHT_PARENTHESIS)
		.put("{", Type.LEFT_CURLY).put("}", Type.RIGHT_CURLY).put("[", Type.LEFT_BRACKET).put("]", Type.RIGHT_BRACKET)
		.put(";", Type.SEMICOLON).put(":", Type.COLON).put(",", Type.COMMA).put(".", Type.DOT).put("..", Type.DOUBLE_DOTS)
		.put("...", Type.TRIPLE_DOTS)
		.build();
	
	private final Type type;
	private final String value;
	private final int line;
	private final int column;
	
	public Token(Type type, String value, int line, int column) {
		this.type = type;
		this.value = value;
		this.line = line;
		this.column = column;
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public int getColumn() {
		return column;
	}

	public int getLine() {
		return line;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + column;
		result = PRIME * result + line;
		result = PRIME * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Token))
			return false;
		final Token other = (Token)obj;
		if (column != other.column)
			return false;
		if (line != other.line)
			return false;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		if (getValue() != null) {
			sb.append('"');
			sb.append(value);
			sb.append("\", ");
		}
		sb.append(type);
		sb.append(", line=");
		sb.append(line);
		sb.append(", column=");
		sb.append(column);
		sb.append("]");
		
		return sb.toString();
	}
}