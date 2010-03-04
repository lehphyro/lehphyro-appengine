package com.lehphyro.eatj;

import java.io.*;
import java.net.*;

public class EatJPinger {

	private static final URI PING_URI = URI.create("http://lehphyro.s218.eatj.com/gamemcasa/ping.html");

	public boolean ping() throws IOException {
		URL pingUrl = new URL(PING_URI.toString());
		HttpURLConnection connection = (HttpURLConnection)pingUrl.openConnection();
		connection.setInstanceFollowRedirects(false);
		connection.getInputStream(); // Connect
		return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
	}
	
}
