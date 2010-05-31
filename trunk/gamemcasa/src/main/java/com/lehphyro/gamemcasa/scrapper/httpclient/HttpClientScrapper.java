package com.lehphyro.gamemcasa.scrapper.httpclient;

import java.util.*;

import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.apache.http.params.*;

import com.lehphyro.gamemcasa.*;
import com.lehphyro.gamemcasa.configuration.*;
import com.lehphyro.gamemcasa.scrapper.*;
import com.lehphyro.gamemcasa.scrapper.httpclient.page.*;

public class HttpClientScrapper implements Scrapper {

	@Override
	public List<Game> getNextGames() throws Exception {
		Map<String, Object> config = new ConfigurationStore().read();

		HttpClient httpClient = new DefaultHttpClient(new GAEConnectionManager(), new BasicHttpParams());

		HomePage homePage = new HomePage(httpClient);
		LoginPage loginPage = homePage.access();
		homePage = loginPage.login((String)config.get("gamemcasa_username"), (String)config.get("gamemcasa_password"));
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
