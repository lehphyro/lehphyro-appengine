package com.lehphyro.gamemcasa.scrapper.webdriver;

import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.*;

import com.lehphyro.gamemcasa.*;
import com.lehphyro.gamemcasa.configuration.*;
import com.lehphyro.gamemcasa.scrapper.*;
import com.lehphyro.gamemcasa.scrapper.webdriver.page.*;

public class WebDriverScrapper implements Scrapper {

	@Override
	public List<Game> getNextGames() {
		Map<String, Object> config = new ConfigurationStore().read();

		WebDriver driver = new HtmlUnitDriver(false);
		
		HomePage homePage = new HomePage(driver);
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
