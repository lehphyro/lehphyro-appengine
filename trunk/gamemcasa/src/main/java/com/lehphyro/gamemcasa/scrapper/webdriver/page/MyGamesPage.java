package com.lehphyro.gamemcasa.scrapper.webdriver.page;

import java.net.*;
import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import static org.apache.commons.lang.StringUtils.*;

public class MyGamesPage extends Page {

	public static final URI PAGE_URI = URI.create("http://www.gamemcasa.com.br/Forms/MeusGames01.aspx");

	@FindBy(id = "ctl00_ContentPlaceHolder1_gvGamesNaLista")
	private WebElement myGamesTable;
	
	public MyGamesPage(WebDriver driver) {
		super(driver);
	}

	public Map<String, String> listGames() {
		getDriver().navigate().to(PAGE_URI.toString());
		
		List<WebElement> links = myGamesTable.findElements(By.tagName("a"));
		List<WebElement> avails = myGamesTable.findElements(By.tagName("span"));
		
		Map<String, String> gamesMap = new HashMap<String, String>(links.size());
		
		for (int i = 0; i < links.size(); i++) {
			WebElement link = links.get(i);
			WebElement avail = avails.get(i);
			
			String gameName = link.getText();
			String gameAvail = avail.getText();
			
			gamesMap.put(trim(gameName), trim(gameAvail));
		}
		
		return gamesMap;
	}
}
