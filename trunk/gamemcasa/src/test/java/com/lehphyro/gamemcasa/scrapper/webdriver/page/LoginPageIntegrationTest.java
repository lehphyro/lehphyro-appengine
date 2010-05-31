package com.lehphyro.gamemcasa.scrapper.webdriver.page;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.*;

import com.lehphyro.gamemcasa.*;

import static org.junit.Assert.*;

public class LoginPageIntegrationTest {

	@Test
	public void testLogin() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		HomePage home = new HomePage(driver);
		
		LoginPage login = home.access();
		home = login.login(Credentials.USERNAME.getValue(), Credentials.PASSWORD.getValue());
		
		assertEquals("Ol�, Leandro de Oliveira Aparecido.\nVoc� est� logado.", home.getLoggedInMessage());
	}
}
