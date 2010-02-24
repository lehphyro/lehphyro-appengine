package com.google.code.luar.syntax.lexer.impl;

import java.io.*;

import com.google.code.luar.syntax.*;
import com.google.code.luar.syntax.lexer.*;
import junit.framework.*;

public class LexerImplTestCase extends TestCase {

	public void testNextToken() {
		InputStream is = LexerImplTestCase.class.getResourceAsStream("hello.lua");
		Lexer lexer = new LexerImpl(new InputStreamReader(is));
		assertEquals(new Token(Token.IDENTIFIER, "io", 3, 1), lexer.nextToken());
		assertEquals(new Token(Token.DOT, null, 3, 3), lexer.nextToken());
		assertEquals(new Token(Token.IDENTIFIER, "write", 3, 4), lexer.nextToken());
		assertEquals(new Token(Token.LEFT_PARENTHESIS, null, 3, 9), lexer.nextToken());
		assertEquals(new Token(Token.STRING_LITERAL, "Hello world, from ", 3, 10), lexer.nextToken());
		assertEquals(new Token(Token.COMMA, null, 3, 30), lexer.nextToken());
		assertEquals(new Token(Token.IDENTIFIER, "_VERSION", 3, 31), lexer.nextToken());
		assertEquals(new Token(Token.COMMA, null, 3, 39), lexer.nextToken());
		assertEquals(new Token(Token.STRING_LITERAL, "!\\n", 3, 40), lexer.nextToken());
		assertEquals(new Token(Token.RIGHT_PARENTHESIS, null, 3, 45), lexer.nextToken());
	}
}