package com.lehphyro.gamemcasa.scrapper.webdriver;

import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.*;

import com.lehphyro.gamemcasa.*;
import com.lehphyro.gamemcasa.scrapper.*;
import com.lehphyro.gamemcasa.scrapper.webdriver.page.*;

public class WebDriverScrapper implements Scrapper {

	public static final String USERNAME = "lehphyro@gmail.com";
	public static final String PASSWORD = "teste00";

	@Override
	public List<Game> getNextGames() {
		WebDriver driver = new HtmlUnitDriver(false);
		
		HomePage homePage = new HomePage(driver);
		LoginPage loginPage = homePage.access();
		homePage = loginPage.login(WebDriverScrapper.USERNAME, WebDriverScrapper.PASSWORD);
		
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
