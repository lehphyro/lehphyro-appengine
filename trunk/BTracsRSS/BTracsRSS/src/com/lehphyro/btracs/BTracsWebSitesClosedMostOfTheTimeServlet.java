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
			BTracsScrapper scrapper = new BTracsScraperDefaultImpl();
			List<WebSite> sites = scrapper.getWebSitesClosedMostOfTheTime();
			BTracsFeeder feeder = new BTracsFeederDefaultImpl();
			feeder.feed(sites, response.getWriter());
		} catch (Throwable e) {
			logger.log(Level.SEVERE, "Error generating feed", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
