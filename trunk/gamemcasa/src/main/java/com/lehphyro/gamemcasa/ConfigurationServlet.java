package com.lehphyro.gamemcasa;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.http.*;
import org.slf4j.*;

import com.lehphyro.gamemcasa.credentials.*;

import static org.apache.commons.lang.StringUtils.*;

public class ConfigurationServlet extends HttpServlet {

	private static final long serialVersionUID = -4171848545909488633L;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			if (!isBlank(username) && !isBlank(password)) {
				new CredentialsStore().store(username, password);
				response.sendRedirect("/config/success.html");
			} else {
				response.sendError(HttpStatus.SC_BAD_REQUEST);
			}
		} catch (Throwable t) {
			logger.error("Error saving configuration", t);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
