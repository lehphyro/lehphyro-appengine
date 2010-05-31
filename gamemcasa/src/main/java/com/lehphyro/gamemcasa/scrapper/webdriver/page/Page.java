package com.lehphyro.gamemcasa.scrapper.webdriver.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public abstract class Page {

	private final WebDriver driver;

	public Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	protected WebDriver getDriver() {
		return driver;
	}
}
