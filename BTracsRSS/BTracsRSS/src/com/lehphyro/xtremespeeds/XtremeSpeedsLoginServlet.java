package com.lehphyro.xtremespeeds;

import java.io.*;
import java.util.logging.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.inject.*;

public class XtremeSpeedsLoginServlet extends HttpServlet {

	private static final long serialVersionUID = -6811462642444170647L;

	private static final Logger logger = Logger.getLogger(XtremeSpeedsLoginServlet.class.getName());

	private Injector injector;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		injector = Guice.createInjector(new XtremeSpeedsModule());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		try {
			XtremeSpeedsLoginExecutor executor = injector.getInstance(XtremeSpeedsLoginExecutor.class);
			executor.login();
			response.getWriter().write("OK");
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Error loging to xtremespeeds", t);
		}
	}

}
