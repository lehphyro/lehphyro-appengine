package com.google.code.luar.syntax.input;

import java.io.*;

public interface LexerInput {

	char EOF = (char)-1;

	char read() throws IOException;

	char readForLookAhead(int length) throws IOException;

	void close();
}