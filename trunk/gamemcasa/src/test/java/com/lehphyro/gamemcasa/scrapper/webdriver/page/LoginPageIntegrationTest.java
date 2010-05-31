package com.lehphyro.gamemcasa.scrapper.webdriver.page;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.*;

import com.lehphyro.gamemcasa.scrapper.webdriver.*;

import static org.junit.Assert.*;

public class LoginPageIntegrationTest {

	@Test
	public void testLogin() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		HomePage home = new HomePage(driver);
		
		LoginPage login = home.access();
		home = login.login(WebDriverScrapper.USERNAME, WebDriverScrapper.PASSWORD);
		
		assertEquals("Olá, Leandro de Oliveira Aparecido.\nVocê está logado.", home.getLoggedInMessage());
	}
}
