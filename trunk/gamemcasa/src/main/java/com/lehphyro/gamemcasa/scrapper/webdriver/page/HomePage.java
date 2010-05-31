package com.lehphyro.gamemcasa.scrapper.webdriver.page;

import java.net.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class HomePage extends Page {

	public static final URI HOME_URI = URI.create("http://www.gamemcasa.com.br");

	@FindBy(id = "ctl00_MenuSuperior1_lnkAcessar")
	private WebElement acessarButton;
	
	@FindBy(id = "ctl00_MenuSuperior1_lblMensLogado")
	private WebElement loggedInMessage;
	
	public HomePage(WebDriver driver) {
		super(driver);
		driver.get(HOME_URI.toString());
	}

	public LoginPage access() {
		acessarButton.click();
		return new LoginPage(getDriver());
	}
	
	public MyGamesPage myGames() {
		return new MyGamesPage(getDriver());
	}

	public String getLoggedInMessage() {
		return loggedInMessage.getText();
	}
}
