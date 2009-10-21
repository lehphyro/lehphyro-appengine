package com.lehphyro.btracs.impl;

import java.io.*;
import java.util.*;

import org.junit.*;

import com.lehphyro.btracs.*;

public class BTracsScraperDefaultImplIntegrationTest {

	@Test
	public void test() throws Exception {
		BTracsScraper scraper = new BTracsScraperDefaultImpl();
		List<WebSite> sites = scraper.getWebSitesClosedMostOfTheTime();
		
		for (WebSite site : sites) {
			System.out.printf("%s%n", site);
		}
		
		BTracsFeeder feeder = new BTracsFeederDefaultImpl();
		feeder.feed("Web Sites Closed Most of the Time", sites, new PrintWriter(System.out));
	}

}
