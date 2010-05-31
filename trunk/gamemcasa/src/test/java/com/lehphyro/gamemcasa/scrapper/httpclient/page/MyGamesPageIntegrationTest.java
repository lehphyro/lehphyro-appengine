package com.lehphyro.gamemcasa.scrapper.httpclient.page;

import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.junit.*;

import com.lehphyro.gamemcasa.*;

public class MyGamesPageIntegrationTest {

	@Test
	public void test() throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HomePage homePage = new HomePage(httpClient);
		LoginPage loginPage = homePage.access();
		homePage = loginPage.login(Credentials.USERNAME.getValue(), Credentials.PASSWORD.getValue());
		MyGamesPage myGamesPage = homePage.myGames();
		System.out.println(myGamesPage.listGames());
	}

}
