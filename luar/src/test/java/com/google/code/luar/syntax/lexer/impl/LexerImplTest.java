package com.google.code.luar.syntax.lexer.impl;

import java.io.*;

import org.junit.Test;

import com.google.code.luar.syntax.*;
import com.google.code.luar.syntax.lexer.*;

import static org.junit.Assert.*;

public class LexerImplTest {

	@Test
	public void testNextToken() {
		InputStream is = LexerImplTest.class.getResourceAsStream("hello.lua");
		Lexer lexer = new LexerImpl(new InputStreamReader(is));
		assertEquals(new Token(Token.Type.IDENTIFIER, "io", 3, 1), lexer.nextToken());
		assertEquals(new Token(Token.Type.DOT, null, 3, 3), lexer.nextToken());
		assertEquals(new Token(Token.Type.IDENTIFIER, "write", 3, 4), lexer.nextToken());
		assertEquals(new Token(Token.Type.LEFT_PARENTHESIS, null, 3, 9), lexer.nextToken());
		assertEquals(new Token(Token.Type.STRING_LITERAL, "Hello world, from ", 3, 10), lexer.nextToken());
		assertEquals(new Token(Token.Type.COMMA, null, 3, 30), lexer.nextToken());
		assertEquals(new Token(Token.Type.IDENTIFIER, "_VERSION", 3, 31), lexer.nextToken());
		assertEquals(new Token(Token.Type.COMMA, null, 3, 39), lexer.nextToken());
		assertEquals(new Token(Token.Type.STRING_LITERAL, "!\\n", 3, 40), lexer.nextToken());
		assertEquals(new Token(Token.Type.RIGHT_PARENTHESIS, null, 3, 45), lexer.nextToken());
	}
}