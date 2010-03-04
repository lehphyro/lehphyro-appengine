package com.lehphyro.eatj;

import java.io.*;
import java.util.logging.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class EatJServerPingServlet extends HttpServlet {

	private static final long serialVersionUID = -8177433004553822796L;
	
	private static final Logger logger = Logger.getLogger(EatJServerPingServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			EatJPinger pinger = new EatJPinger();
			if (!pinger.ping()) {
				EatJMailer mailer = new EatJMailer();
				mailer.mailServerDown();
			}
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Error pinging eatj", t);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
