package com.lehphyro.xtremespeeds.impl;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.io.*;
import org.apache.commons.lang.*;

import com.google.appengine.repackaged.org.apache.commons.logging.*;
import com.lehphyro.xtremespeeds.*;

public class XtremeSpeedsLoginExecutorDefaultImpl implements XtremeSpeedsLoginExecutor {

	private static final String LOGIN_URL = "http://xtremespeeds.net/takelogin.php";
	private static final String USERNAME = "lehphyro";
	private static final String PASSWORD = "teste00";
	private static final String SUCCESSFUL_LOGIN_MESSAGE = "You have succesfully logged in...";

	private static final Log log = LogFactory.getLog(XtremeSpeedsLoginExecutorDefaultImpl.class);

	@Override
	public void login() throws Exception {
		URL url = new URL(LOGIN_URL);
		String parameters = "username=" + USERNAME + "&password=" + PASSWORD + "&logout=yes";
		
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		
		connection.setDoInput(true);
		connection.setDoOutput(true);

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Host", "xtremespeeds.net");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2");
		connection.setRequestProperty("Referer", "http://xtremespeeds.net/");
		connection.setRequestProperty("Cookie", "ts_username=lehphyro; _csoot=1255609128502; _csuid=4a75b79f41eae293; poll_voted_218=218; TSSE_Session=50eds6nm9jh3vg9kt8sain0016; ts_psf=100");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", Integer.toString(parameters.getBytes().length));

		BufferedReader reader = null;
		try {
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(parameters);
			writer.close();
			
			int responseCode = connection.getResponseCode();
			String responseMessage = connection.getResponseMessage();
			StringBuilder pageBody = new StringBuilder();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				log.info("HTTP OK");
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), CharEncoding.ISO_8859_1));
				String line; boolean loggedIn = false;
				while ((line = reader.readLine()) != null) {
					if (line.contains(SUCCESSFUL_LOGIN_MESSAGE)) {
						loggedIn = true;
					}
					pageBody.append(line).append('\n');
					log.info(line);
				}
				if (!loggedIn) {
					notifyLoginFailed(responseCode, responseMessage, pageBody.toString());
				}
			} else {
				log.info("HTTP " + connection.getResponseCode() +  ": " + connection.getResponseMessage());
				notifyLoginFailed(responseCode, responseMessage, null);
			}
		} finally {
			IOUtils.closeQuietly(reader);
			connection.disconnect();
		}
	}

	protected void notifyLoginFailed(int responseCode, String responseMessage, String pageBody) throws Exception {
		Session session = Session.getDefaultInstance(new Properties(), null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("lehphyro@gmail.com", "XtremeSpeedsLoginExecutorDefaultImpl"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress("lehphyro@gmail.com", "Leandro"));
		msg.setSubject("Login automático em XtremeSpeeds falhou");
		
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP: ").append(responseCode).append(" - ").append(responseMessage).append("\n\n");
		if (pageBody != null) {
			sb.append(pageBody);
		}
		msg.setText(sb.toString());
		Transport.send(msg);
	}
}
