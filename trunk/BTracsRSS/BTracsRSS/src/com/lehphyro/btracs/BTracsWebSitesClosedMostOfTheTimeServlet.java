package com.lehphyro.btracs;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.logging.*;

import com.google.inject.*;

public class BTracsWebSitesClosedMostOfTheTimeServlet extends HttpServlet {

	private static final long serialVersionUID = 3483149536624901826L;
	
	private static final Log log = LogFactory.getLog(BTracsWebSitesClosedMostOfTheTimeServlet.class);

	private Injector injector;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		injector = Guice.createInjector(new BTracsModule());
		super.init(config);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		
		try {
			BTracsScraper scraper = injector.getInstance(BTracsScraper.class);
			List<WebSite> sites = scraper.getWebSitesClosedMostOfTheTime();
			BTracsFeeder feeder = injector.getInstance(BTracsFeeder.class);
			feeder.feed("Web Sites Closed Most of the Time", sites, response.getWriter());
		} catch (Throwable e) {
			log.error("Error generating feed", e);
		}
	}
}
