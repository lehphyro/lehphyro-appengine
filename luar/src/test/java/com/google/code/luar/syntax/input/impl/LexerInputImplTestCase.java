package com.google.code.luar.syntax.input.impl;

import java.io.*;

import com.google.code.luar.syntax.input.*;

import junit.framework.*;

public class LexerInputImplTestCase extends TestCase {
	
	private String inputString;
	
	public LexerInputImplTestCase() {
		inputString = "Test input with something to feed the lexer input";
	}

	public void testReadForLookAhead() {
		try {
			LexerInput input = new LexerInputImpl(new StringReader(inputString), 10, 3);
			
			StringBuffer sb = new StringBuffer(inputString.length());
			while ((input.readForLookAhead(1)) != LexerInput.EOF) {
				sb.append(input.read());
			}
			assertEquals("Must be the inputString", inputString, sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testClose() {
		LexerInput input = new LexerInputImpl(new StringReader(inputString));
		input.close();
		try {
			input.readForLookAhead(1);
			fail("Must throw exception");
		} catch (IOException e) {
			// Success!
		}
	}

}