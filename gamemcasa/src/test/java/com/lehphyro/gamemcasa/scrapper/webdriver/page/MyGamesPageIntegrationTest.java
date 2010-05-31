package com.lehphyro.gamemcasa.scrapper.webdriver.page;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.*;

import com.lehphyro.gamemcasa.*;

public class MyGamesPageIntegrationTest {

	@Test
	public void testListGames() throws Exception {
		WebDriver driver = new HtmlUnitDriver(false);
		
		HomePage home = new HomePage(driver);
		LoginPage login = home.access();
		home = login.login(Credentials.USERNAME.getValue(), Credentials.PASSWORD.getValue());
		
		MyGamesPage myGames = home.myGames();
		System.out.println(myGames.listGames());
	}
}
