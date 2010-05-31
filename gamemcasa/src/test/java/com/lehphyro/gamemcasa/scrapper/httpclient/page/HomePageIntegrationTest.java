package com.lehphyro.gamemcasa.scrapper.httpclient.page;

import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.junit.*;

import static org.junit.Assert.*;

public class HomePageIntegrationTest {

	@Test
	public void test() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HomePage homePage = new HomePage(httpClient);
		assertNotNull(homePage.access());
	}
}
