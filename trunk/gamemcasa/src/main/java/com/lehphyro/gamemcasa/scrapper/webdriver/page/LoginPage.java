package com.lehphyro.gamemcasa.scrapper.webdriver.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class LoginPage extends Page {

	@FindBy(id = "ctl00_ContentPlaceHolder1_txtEmail")
	private WebElement emailInput;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtSenha")
	private WebElement passwordInput;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_btnEntrar")
	private WebElement submitButton;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public HomePage login(String username, String password) {
		emailInput.sendKeys(username);
		passwordInput.sendKeys(password);
		submitButton.click();

		return new HomePage(getDriver());
	}
}
