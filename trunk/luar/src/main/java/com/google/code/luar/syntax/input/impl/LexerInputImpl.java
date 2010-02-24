package com.google.code.luar.syntax.input.impl;

import java.io.*;

import com.google.code.luar.syntax.input.*;

public class LexerInputImpl implements LexerInput {
	
	private static final int DEFAULT_BUFFER_SIZE = 64;
	
	private static final int MAX_LOOK_AHEAD = 5;
	
	private int position;
	
	private Reader reader;
	
	private char[] buffer;
	
	private int maxLookAhead;
	
	private boolean initialized;
	
	private boolean closed;
	
	private boolean reachedEOF;
	
	public LexerInputImpl(Reader reader) {
		this(reader, DEFAULT_BUFFER_SIZE, MAX_LOOK_AHEAD);
	}
	
	public LexerInputImpl(Reader reader, int bufferSize, int maxLookAhead) {
		this.reader = reader;
		this.maxLookAhead = maxLookAhead;
		buffer = new char[bufferSize];
	}

	public char readForLookAhead(int length) throws IOException {
		if (check()) {
			if (length > maxLookAhead) {
				throw new IllegalArgumentException("maxLookAhead violation: " + length);
			}
			
			return buffer[position + length - 1];
		} else {
			return EOF;
		}
	}

	public char read() throws IOException {
		if (check()) {
			if (position + maxLookAhead == buffer.length) {
				System.arraycopy(buffer, position, buffer, 0, maxLookAhead);
				load(maxLookAhead, buffer.length - maxLookAhead);
				position = 0;
			}
			
			return buffer[position++];
		} else {
			return EOF;
		}
	}

	public void close() {
		try {
			closed = true;
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected boolean check() throws IOException {
		if (reachedEOF) {
			return false;
		} else if (closed) {
			throw new IOException("Input was closed");
		} else if (!initialized) {
			load(0, buffer.length);
			initialized = true;
		}

		return true;
	}
	
	protected void load(int start, int length) throws IOException {
		int read = reader.read(buffer, start, length);
		if (read < 0) {
			reachedEOF = true;
		} else {
			for (int i = start + read; i < buffer.length; i++) {
				buffer[i] = EOF;
			}
		}
	}

}