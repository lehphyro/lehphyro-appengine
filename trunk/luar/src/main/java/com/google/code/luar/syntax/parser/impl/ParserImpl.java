package com.google.code.luar.parser.impl;

import com.google.code.luar.syntax.*;
import com.google.code.luar.syntax.lexer.*;
import com.google.code.luar.syntax.parser.*;
import com.google.code.luar.syntax.tree.*;

public class ParserImpl implements Parser {

	private Lexer lexer;
	
	private ChunkNode syntaxTree;
	
	public ParserImpl(Lexer lexer) {
		this.lexer = lexer;
	}
	
	public ChunkNode parse() {
		syntaxTree = new ChunkNode();
		Token token;
		try {
			while ((token = lexer.nextToken()).getType() != Token.EOF) {
				syntaxTree.addToken(token);
			}
		} finally {
			lexer.close();
		}
		
		return syntaxTree;
	}
	
}