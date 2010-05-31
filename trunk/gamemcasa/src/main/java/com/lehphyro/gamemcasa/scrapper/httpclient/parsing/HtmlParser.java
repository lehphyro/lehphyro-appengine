package com.lehphyro.gamemcasa.scrapper.httpclient.parsing;

import java.io.*;

import org.htmlcleaner.*;

public class HtmlParser {

	private static final CleanerProperties CLEANER_PROPERTIES = new CleanerProperties();
	static {
		CLEANER_PROPERTIES.setOmitComments(true);
		CLEANER_PROPERTIES.setOmitXmlDeclaration(true);
		CLEANER_PROPERTIES.setPruneTags("script,style");
	}

	private HtmlCleaner cleaner;

	public HtmlParser() {
		cleaner = new HtmlCleaner(CLEANER_PROPERTIES);
	}
	
	public HtmlNode parse(String s) throws IOException {
		return parse(new StringReader(s));
	}

	public HtmlNode parse(Reader reader) throws IOException {
		return new HtmlNode(cleaner.clean(reader));
	}

}
