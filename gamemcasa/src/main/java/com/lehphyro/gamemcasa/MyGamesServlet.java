package com.lehphyro.gamemcasa;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.slf4j.*;

import com.lehphyro.gamemcasa.feeder.*;
import com.lehphyro.gamemcasa.scrapper.*;

public class MyGamesServlet extends HttpServlet {

	private static final long serialVersionUID = -7925373156686577332L;

	private static final Logger logger = LoggerFactory.getLogger(MyGamesServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		response.setCharacterEncoding(CharEncoding.UTF_8);
		
		try {
			Scrapper scrapper = new WebDriverScrapper();
			List<Game> games = scrapper.getNextGames();
			
			Feeder feeder = new RssFeeder();
			feeder.feed(games, response.getWriter());
		} catch (Throwable t) {
			logger.error("Error generating feed", t);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
