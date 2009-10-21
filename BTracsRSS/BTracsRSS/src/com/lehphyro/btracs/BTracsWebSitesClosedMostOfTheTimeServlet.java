package com.lehphyro.btracs;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.servlet.http.*;

import com.lehphyro.btracs.impl.*;

public class BTracsWebSitesClosedMostOfTheTimeServlet extends HttpServlet {

	private static final long serialVersionUID = 3483149536624901826L;
	
	private static final Logger logger = Logger.getLogger(BTracsWebSitesClosedMostOfTheTimeServlet.class.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		
		try {
			BTracsScraper scraper = new BTracsScraperDefaultImpl();
			List<WebSite> sites = scraper.getWebSitesClosedMostOfTheTime();
			BTracsFeeder feeder = new BTracsFeederDefaultImpl();
			feeder.feed("Web Sites Closed Most of the Time", sites, response.getWriter());
		} catch (Throwable e) {
			logger.log(Level.SEVERE, "Error generating feed", e);
		}
	}
}
