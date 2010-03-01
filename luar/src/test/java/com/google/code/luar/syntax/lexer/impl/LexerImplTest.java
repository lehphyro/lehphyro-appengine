package com.google.code.luar.syntax.lexer.impl;

import java.io.*;

import org.junit.*;

import com.google.code.luar.syntax.*;
import com.google.code.luar.syntax.lexer.*;

import static org.junit.Assert.*;

import static com.google.code.luar.syntax.Token.Type.*;

public class LexerImplTest {

	@Test
	public void testHello() {
		Lexer lexer = getLexer("hello.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "io", 3, 1), lexer.nextToken());
			assertEquals(new Token(DOT, 3, 3), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "write", 3, 4), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 3, 9), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "Hello world, from ", 3, 10), lexer.nextToken());
			assertEquals(new Token(COMMA, 3, 30), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "_VERSION", 3, 31), lexer.nextToken());
			assertEquals(new Token(COMMA, 3, 39), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "!\\n", 3, 40), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 3, 45), lexer.nextToken());
			
			assertEquals(new Token(EOF, 4, 1), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testTypes() {
		Lexer lexer = getLexer("types.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "a", 3, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 3, 2), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 3, 3), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "b", 4, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 4, 2), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "abc", 4, 3), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "c", 5, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 5, 2), lexer.nextToken());
			assertEquals(new Token(LEFT_CURLY, 5, 3), lexer.nextToken());
			assertEquals(new Token(RIGHT_CURLY, 5, 4), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "d", 6, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 6, 2), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "print", 6, 3), lexer.nextToken());

			assertPrint("a", 8, lexer);
			assertPrint("b", 9, lexer);
			assertPrint("c", 10, lexer);
			assertPrint("d", 11, lexer);
			
			assertEquals(new Token(EOF, 12, 1), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testStrings() {
		Lexer lexer = getLexer("strings.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "a", 1, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 1, 2), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "single 'quoted' string and double \"quoted\" string inside", 1, 3), lexer.nextToken());

			assertEquals(new Token(IDENTIFIER, "b", 2, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 2, 2), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "single 'quoted' string and double \"quoted\" string inside", 2, 3), lexer.nextToken());

			assertEquals(new Token(IDENTIFIER, "c", 3, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 3, 2), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, " multiple line\r\nwith 'single'\r\nand \"double\" quoted strings inside.", 3, 4), lexer.nextToken());

			assertEquals(new Token(IDENTIFIER, "print", 7, 1), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 7, 6), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 7, 7), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 7, 8), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testAssignments() {
		Lexer lexer = getLexer("assignments.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "a", 1, 1), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 2), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "b", 1, 3), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 4), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "c", 1, 5), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 6), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "d", 1, 7), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 8), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "e", 1, 9), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 1, 11), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 1, 13), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 14), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "2", 1, 16), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 17), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "three", 1, 19), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 26), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "four", 1, 28), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 34), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "5", 1, 36), lexer.nextToken());
			
			assertEquals(new Token(IDENTIFIER, "print", 3, 1), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 3, 6), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 3, 7), lexer.nextToken());
			assertEquals(new Token(COMMA, 3, 8), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "b", 3, 9), lexer.nextToken());
			assertEquals(new Token(COMMA, 3, 10), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "c", 3, 11), lexer.nextToken());
			assertEquals(new Token(COMMA, 3, 12), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "d", 3, 13), lexer.nextToken());
			assertEquals(new Token(COMMA, 3, 14), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "e", 3, 15), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 3, 16), lexer.nextToken());
			
			assertEquals(new Token(EOF, 3, 17), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testNumbers() {
		Lexer lexer = getLexer("numbers.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "a", 1, 1), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 2), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "b", 1, 3), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 4), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "c", 1, 5), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 6), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "d", 1, 7), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 8), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "e", 1, 9), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 1, 11), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 1, 13), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 14), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1.123", 1, 16), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 21), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1E9", 1, 23), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 26), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "-123", 1, 28), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 32), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, ".0008", 1, 34), lexer.nextToken());
			
			assertEquals(new Token(IDENTIFIER, "print", 2, 1), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 2, 6), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "a=", 2, 7), lexer.nextToken());
			assertEquals(new Token(DOUBLE_DOTS, 2, 11), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 2, 13), lexer.nextToken());
			assertEquals(new Token(COMMA, 2, 14), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "b=", 2, 16), lexer.nextToken());
			assertEquals(new Token(DOUBLE_DOTS, 2, 20), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "b", 2, 22), lexer.nextToken());
			assertEquals(new Token(COMMA, 2, 23), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testTables() {
		Lexer lexer = getLexer("tables.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "address", 1, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 1, 8), lexer.nextToken());
			assertEquals(new Token(LEFT_CURLY, 1, 9), lexer.nextToken());
			assertEquals(new Token(RIGHT_CURLY, 1, 10), lexer.nextToken());
			
			assertEquals(new Token(IDENTIFIER, "address", 2, 1), lexer.nextToken());
			assertEquals(new Token(DOT, 2, 8), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "StreetNumber", 2, 9), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 2, 21), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "360", 2, 22), lexer.nextToken());

			assertEquals(new Token(IDENTIFIER, "address", 3, 1), lexer.nextToken());
			assertEquals(new Token(DOT, 3, 8), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "AptNumber", 3, 9), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 3, 18), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "2a", 3, 19), lexer.nextToken());
			
			assertEquals(new Token(IDENTIFIER, "print", 5, 1), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 5, 6), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "address", 5, 7), lexer.nextToken());
			assertEquals(new Token(DOT, 5, 14), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "StreetNumber", 5, 15), lexer.nextToken());
			assertEquals(new Token(COMMA, 5, 27), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "address", 5, 29), lexer.nextToken());
			assertEquals(new Token(LEFT_BRACKET, 5, 36), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "AptNumber", 5, 37), lexer.nextToken());
			assertEquals(new Token(RIGHT_BRACKET, 5, 48), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 5, 49), lexer.nextToken());
			
			assertEquals(new Token(EOF, 7, 1), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testIf() {
		Lexer lexer = getLexer("if.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "a", 1, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 1, 2), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 1, 3), lexer.nextToken());
			
			assertEquals(new Token(IF, 2, 1), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 2, 4), lexer.nextToken());
			assertEquals(new Token(EQUAL, 2, 5), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 2, 7), lexer.nextToken());
			assertEquals(new Token(THEN, 2, 9), lexer.nextToken());
			
			assertEquals(new Token(IDENTIFIER, "print", 3, 5), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 3, 11), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "a is one", 3, 12), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 3, 22), lexer.nextToken());
			
			assertEquals(new Token(END, 4, 1), lexer.nextToken());
			
			assertEquals(new Token(EOF, 4, 4), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testConditionalAssignment() {
		Lexer lexer = getLexer("conditional_assignment.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "a", 1, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 1, 2), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 1, 3), lexer.nextToken());
			
			assertEquals(new Token(IDENTIFIER, "b", 2, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 2, 2), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 2, 3), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 2, 4), lexer.nextToken());
			assertEquals(new Token(EQUAL, 2, 5), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 2, 7), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 2, 8), lexer.nextToken());
			
			assertEquals(new Token(AND, 2, 10), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "one", 2, 14), lexer.nextToken());
			assertEquals(new Token(OR, 2, 20), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "not one", 2, 23), lexer.nextToken());
			
			assertEquals(new Token(EOF, 3, 1), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testWhile() {
		Lexer lexer = getLexer("while.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "a", 1, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 1, 2), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 1, 3), lexer.nextToken());
			
			assertEquals(new Token(WHILE, 2, 1), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 2, 7), lexer.nextToken());
			assertEquals(new Token(NOT_EQUAL, 2, 8), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "5", 2, 10), lexer.nextToken());
			assertEquals(new Token(DO, 2, 12), lexer.nextToken());
			
			assertEquals(new Token(IDENTIFIER, "a", 3, 5), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 3, 6), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 3, 7), lexer.nextToken());
			assertEquals(new Token(PLUS, 3, 8), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 3, 9), lexer.nextToken());
			
			assertEquals(new Token(IDENTIFIER, "io", 4, 5), lexer.nextToken());
			assertEquals(new Token(DOT, 4, 7), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "write", 4, 8), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 4, 13), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 4, 14), lexer.nextToken());
			assertEquals(new Token(DOUBLE_DOTS, 4, 15), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, " ", 4, 17), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 4, 20), lexer.nextToken());
			
			assertEquals(new Token(END, 5, 1), lexer.nextToken());
			assertEquals(new Token(EOF, 5, 4), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testFor() {
		Lexer lexer = getLexer("for.lua");
		try {
			assertEquals(new Token(FOR, 1, 1), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 1, 5), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 1, 6), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 1, 7), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 8), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "4", 1, 9), lexer.nextToken());
			assertEquals(new Token(DO, 1, 11), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "io", 1, 14), lexer.nextToken());
			assertEquals(new Token(DOT, 1, 16), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "write", 1, 17), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 1, 22), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 1, 23), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 1, 24), lexer.nextToken());
			assertEquals(new Token(END, 1, 26), lexer.nextToken());
			assertEquals(new Token(EOF, 1, 29), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testForIteration() {
		Lexer lexer = getLexer("for_iteration.lua");
		try {
			assertEquals(new Token(FOR, 1, 1), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "key", 1, 5), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 8), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "value", 1, 9), lexer.nextToken());
			assertEquals(new Token(IN, 1, 15), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "pairs", 1, 18), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 1, 23), lexer.nextToken());
			assertEquals(new Token(LEFT_CURLY, 1, 24), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 1, 25), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 26), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "2", 1, 27), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 28), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "3", 1, 29), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 30), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "4", 1, 31), lexer.nextToken());
			assertEquals(new Token(RIGHT_CURLY, 1, 32), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 1, 33), lexer.nextToken());
			assertEquals(new Token(DO, 1, 35), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "print", 1, 38), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 1, 43), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "key", 1, 44), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 47), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "value", 1, 49), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 1, 54), lexer.nextToken());
			assertEquals(new Token(END, 1, 56), lexer.nextToken());
			assertEquals(new Token(EOF, 1, 59), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	@Test
	public void testPrintTables() {
		Lexer lexer = getLexer("print_table.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "a", 1, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 1, 2), lexer.nextToken());
			assertEquals(new Token(LEFT_CURLY, 1, 3), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1", 1, 4), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 5), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "2", 1, 6), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 7), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "3", 1, 8), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 9), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "4", 1, 10), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 11), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "five", 1, 12), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 18), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "elephant", 1, 19), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 29), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "mouse", 1, 31), lexer.nextToken());
			assertEquals(new Token(RIGHT_CURLY, 1, 38), lexer.nextToken());
			
			assertEquals(new Token(FOR, 3, 1), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "i", 3, 5), lexer.nextToken());
			assertEquals(new Token(COMMA, 3, 6), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "v", 3, 7), lexer.nextToken());
			assertEquals(new Token(IN, 3, 9), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "pairs", 3, 12), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 3, 17), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "a", 3, 18), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 3, 19), lexer.nextToken());
			assertEquals(new Token(DO, 3, 21), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "print", 3, 24), lexer.nextToken());
			assertEquals(new Token(LEFT_PARENTHESIS, 3, 29), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "i", 3, 30), lexer.nextToken());
			assertEquals(new Token(COMMA, 3, 31), lexer.nextToken());
			assertEquals(new Token(IDENTIFIER, "v", 3, 32), lexer.nextToken());
			assertEquals(new Token(RIGHT_PARENTHESIS, 3, 33), lexer.nextToken());
			assertEquals(new Token(END, 3, 35), lexer.nextToken());
			
			assertEquals(new Token(EOF, 3, 38), lexer.nextToken());
		} finally {
			lexer.close();
		}
	}
	
	private void assertPrint(String varName, int line, Lexer lexer) {
		assertEquals(new Token(IDENTIFIER, "print", line, 1), lexer.nextToken());
		assertEquals(new Token(LEFT_PARENTHESIS, line, 6), lexer.nextToken());
		assertEquals(new Token(IDENTIFIER, "type", line, 7), lexer.nextToken());
		assertEquals(new Token(LEFT_PARENTHESIS, line, 11), lexer.nextToken());
		assertEquals(new Token(IDENTIFIER, varName, line, 12), lexer.nextToken());
		assertEquals(new Token(RIGHT_PARENTHESIS, line, 13), lexer.nextToken());
		assertEquals(new Token(RIGHT_PARENTHESIS, line, 14), lexer.nextToken());
	}
	
	protected Lexer getLexer(String inputName) {
		InputStream is = LexerImplTest.class.getResourceAsStream(inputName);
		return  new LexerImpl(new InputStreamReader(is));
	}
}