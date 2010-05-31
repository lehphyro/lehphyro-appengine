package com.lehphyro.gamemcasa.scrapper.httpclient.page;

import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.junit.*;

public class MyGamesPageIntegrationTest {

	@Test
	public void test() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HomePage homePage = new HomePage(httpClient);
		LoginPage loginPage = homePage.access();
		homePage = loginPage.login("", "");
		MyGamesPage myGamesPage = homePage.myGames();
		System.out.println(myGamesPage.listGames());
	}

}
