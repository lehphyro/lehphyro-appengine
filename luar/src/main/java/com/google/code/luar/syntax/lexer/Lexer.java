package com.google.code.luar.syntax.lexer;

import com.google.code.luar.syntax.*;

public interface Lexer {

	Token nextToken();
	
	void close();
}