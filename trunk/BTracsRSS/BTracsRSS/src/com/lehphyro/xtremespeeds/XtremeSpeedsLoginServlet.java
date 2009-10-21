package com.lehphyro.xtremespeeds;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.appengine.repackaged.org.apache.commons.logging.*;
import com.google.inject.*;

public class XtremeSpeedsLoginServlet extends HttpServlet {

	private static final long serialVersionUID = -6811462642444170647L;

	private static final Log log = LogFactory.getLog(XtremeSpeedsLoginServlet.class);

	private Injector injector;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		injector = Guice.createInjector(new XtremeSpeedsModule());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			XtremeSpeedsLoginExecutor executor = injector.getInstance(XtremeSpeedsLoginExecutor.class);
			executor.login();
		} catch (Throwable t) {
			log.error("Error loging to xtremespeeds", t);
		}
	}

}
