package com.google.code.luar.syntax.lexer.impl;

import java.io.*;

import org.junit.*;

import com.google.code.luar.syntax.*;
import com.google.code.luar.syntax.lexer.*;

import static org.junit.Assert.*;

import static com.google.code.luar.syntax.Token.Type.*;

public class LexerImplTest {

	@Test @Ignore
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
		} finally {
			lexer.close();
		}
	}
	
	@Test @Ignore
	public void testTypes() {
		Lexer lexer = getLexer("types.lua");
		try {
			assertEquals(new Token(IDENTIFIER, "a", 3, 1), lexer.nextToken());
			assertEquals(new Token(ASSIGN, 3, 2), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "1.0", 3, 3), lexer.nextToken());
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
		} finally {
			lexer.close();
		}
	}
	
	@Test @Ignore
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
			assertEquals(new Token(NUMBER_LITERAL, "1.0", 1, 13), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 14), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "2.0", 1, 16), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 17), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "three", 1, 19), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 26), lexer.nextToken());
			assertEquals(new Token(STRING_LITERAL, "four", 1, 28), lexer.nextToken());
			assertEquals(new Token(COMMA, 1, 34), lexer.nextToken());
			assertEquals(new Token(NUMBER_LITERAL, "5.0", 1, 36), lexer.nextToken());
			
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