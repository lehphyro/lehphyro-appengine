package com.lehphyro.gamemcasa.scrapper.page;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.*;

public class HomePageIntegrationTest {

	@Test(expected = NoSuchElementException.class)
	public void testNotLoggedIn() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		
		HomePage page = new HomePage(driver);
		page.getLoggedInMessage();
	}
}
