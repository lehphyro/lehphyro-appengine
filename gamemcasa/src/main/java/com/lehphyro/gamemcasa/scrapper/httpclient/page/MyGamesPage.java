package com.lehphyro.gamemcasa.scrapper.httpclient.page;

import java.util.*;

import com.lehphyro.gamemcasa.scrapper.httpclient.parsing.*;

import static org.apache.commons.lang.StringUtils.*;

public class MyGamesPage {

	private final HtmlNode page;

	public MyGamesPage(HtmlNode page) {
		this.page = page;
	}

	public Map<String, String> listGames() throws HtmlParsingException {
		HtmlNode table = page.findNodeById("ctl00_ContentPlaceHolder1_gvGamesNaLista");
		List<HtmlNode> links = table.findNodesByTagName("a");
		List<HtmlNode> spans = table.findNodesByTagName("span");

		Map<String, String> gamesMap = new HashMap<String, String>(links.size());
		
		for (int i = 0; i < links.size(); i++) {
			HtmlNode link = links.get(i);
			HtmlNode span = spans.get(i);
			
			String gameName = link.getTextContent();
			String gameAvail = span.getTextContent();
			
			gamesMap.put(trim(gameName), trim(gameAvail));
		}

		return gamesMap;
	}

}
