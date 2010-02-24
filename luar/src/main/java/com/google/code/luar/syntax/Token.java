package com.google.code.luar.syntax;

import java.util.*;

public class Token {
	// Reserved words
	public static final byte AND = 0;
	public static final byte BREAK = 1;
	public static final byte DO = 2;
	public static final byte ELSE = 3;
	public static final byte ELSEIF = 4;
	public static final byte END = 5;
	public static final byte FALSE = 6;
	public static final byte FOR = 7;
	public static final byte FUNCTION = 8;
	public static final byte IF = 9;
	public static final byte IN = 10;
	public static final byte LOCAL = 11;
	public static final byte NIL = 12;
	public static final byte NOT = 13;
	public static final byte OR = 14;
	public static final byte REPEAT = 15;
	public static final byte RETURN = 16;
	public static final byte THEN = 17;
	public static final byte TRUE = 18;
	public static final byte UNTIL = 19;
	public static final byte WHILE = 20;
	
	// Other literal signs
	public static final byte PLUS = 21;
	public static final byte MINUS = 22;
	public static final byte MULTIPLY = 23;
	public static final byte DIVIDE = 24;
	public static final byte POW = 25;
	public static final byte ASSIGN = 26;
	public static final byte NOT_EQUAL = 27;
	public static final byte LESS_OR_EQUAL = 28;
	public static final byte GREATER_OR_EQUAL = 29;
	public static final byte LESS = 30;
	public static final byte GREATER = 31;
	public static final byte EQUAL = 32;
	public static final byte LEFT_PARENTHESIS = 33;
	public static final byte RIGHT_PARENTHESIS = 34;
	public static final byte LEFT_CURLY = 35;
	public static final byte RIGHT_CURLY = 36;
	public static final byte LEFT_BRACK = 37;
	public static final byte RIGHT_BRACK = 38;
	public static final byte SEMICOLON = 39;
	public static final byte COLON = 40;
	public static final byte COMMA = 41;
	public static final byte DOT = 42;
	public static final byte DOUBLE_DOTS = 43;
	public static final byte TRIPLE_DOTS = 44;
	
	// Types
	public static final byte UNKNOWN = 100;
	public static final byte IDENTIFIER = 101;
	public static final byte STRING_LITERAL = 102;
	public static final byte NUMBER_LITERAL = 103;
	public static final byte EOF = 126;
	public static final byte SKIP = 127;
	
	public static final Map literals;
	static {
		literals = new HashMap(TRIPLE_DOTS);
		literals.put("and", new Byte(AND));
		literals.put("break", new Byte(BREAK));
		literals.put("do", new Byte(DO));
		literals.put("else", new Byte(ELSE));
		literals.put("elseif", new Byte(ELSEIF));
		literals.put("end", new Byte(END));
		literals.put("false", new Byte(FALSE));
		literals.put("for", new Byte(FOR));
		literals.put("function", new Byte(FUNCTION));
		literals.put("if", new Byte(IF));
		literals.put("in", new Byte(IN));
		literals.put("local", new Byte(LOCAL));
		literals.put("nil", new Byte(NIL));
		literals.put("not", new Byte(NOT));
		literals.put("or", new Byte(OR));
		literals.put("repeat", new Byte(REPEAT));
		literals.put("return", new Byte(RETURN));
		literals.put("then", new Byte(THEN));
		literals.put("true", new Byte(TRUE));
		literals.put("until", new Byte(UNTIL));
		literals.put("while", new Byte(WHILE));
		
		literals.put("+", new Byte(PLUS));
		literals.put("-", new Byte(MINUS));
		literals.put("*", new Byte(MULTIPLY));
		literals.put("/", new Byte(DIVIDE));
		literals.put("^", new Byte(POW));
		literals.put("=", new Byte(ASSIGN));
		literals.put("~=", new Byte(NOT_EQUAL));
		literals.put("<=", new Byte(LESS_OR_EQUAL));
		literals.put(">=", new Byte(GREATER_OR_EQUAL));
		literals.put("<", new Byte(LESS));
		literals.put(">", new Byte(GREATER));
		literals.put("==", new Byte(EQUAL));
		literals.put("(", new Byte(LEFT_PARENTHESIS));
		literals.put(")", new Byte(RIGHT_PARENTHESIS));
		literals.put("{", new Byte(LEFT_CURLY));
		literals.put("}", new Byte(RIGHT_CURLY));
		literals.put("[", new Byte(LEFT_BRACK));
		literals.put("]", new Byte(RIGHT_BRACK));
		literals.put(";", new Byte(SEMICOLON));
		literals.put(":", new Byte(COLON));
		literals.put(",", new Byte(COMMA));
		literals.put(".", new Byte(DOT));
		literals.put("..", new Byte(DOUBLE_DOTS));
		literals.put("...", new Byte(TRIPLE_DOTS));
	}
	
// Token attributes
	private byte type;
	private String value;
	private int line;
	private int column;
	
	public Token() {
		this(UNKNOWN);
	}
	
	public Token(byte type) {
		this(type, null);
	}
	
	public Token(byte type, String value) {
		this(type, value, 0, 0);
	}
	
	public Token(byte type, String value, int line, int column) {
		this.type = type;
		this.value = value;
		this.line = line;
		this.column = column;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
	
	public String getTypeName(byte type) {
		if (type == UNKNOWN) {
			return "unknown";
		} else if (type == IDENTIFIER) {
			return "identifier";
		} else if (type == STRING_LITERAL) {
			return "string literal";
		} else if (type == NUMBER_LITERAL) {
			return "number literal";
		} else if (type == EOF) {
			return "EOF";
		}
		
		Iterator it = literals.entrySet().iterator();
		Map.Entry entry;
		while (it.hasNext()) {
			entry = (Map.Entry)it.next();
			if (((Byte)entry.getValue()).byteValue() == type) {
				return (String)entry.getKey();
			}
		}
		throw new IllegalStateException("Token with unknown type: " + type);
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
			sb.append(getValue());
			sb.append("\", ");
		}
		sb.append(getTypeName(getType()));
		sb.append(", line=");
		sb.append(getLine());
		sb.append(", column=");
		sb.append(getColumn());
		sb.append("]");
		
		return sb.toString();
	}
}