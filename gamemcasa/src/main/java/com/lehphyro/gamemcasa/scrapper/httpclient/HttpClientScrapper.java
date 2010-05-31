package com.lehphyro.gamemcasa.scrapper.httpclient;

import java.util.*;

import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.apache.http.params.*;

import com.lehphyro.gamemcasa.*;
import com.lehphyro.gamemcasa.scrapper.*;
import com.lehphyro.gamemcasa.scrapper.httpclient.page.*;

public class HttpClientScrapper implements Scrapper {

	@Override
	public List<Game> getNextGames() throws Exception {
		HttpClient httpClient = new DefaultHttpClient(new GAEConnectionManager(), new BasicHttpParams());

		HomePage homePage = new HomePage(httpClient);
		LoginPage loginPage = homePage.access();
		homePage = loginPage.login(Credentials.USERNAME.getValue(), Credentials.PASSWORD.getValue());
		MyGamesPage myGamesPage = homePage.myGames();
		Map<String, String> myGames = myGamesPage.listGames();

		GameParser parser = new GameParser();
		List<Game> games = new ArrayList<Game>(myGames.size());
		for (Map.Entry<String, String> myGame : myGames.entrySet()) {
			games.add(parser.parse(myGame.getKey(), myGame.getValue()));
		}
		
		return games;
	}

}
