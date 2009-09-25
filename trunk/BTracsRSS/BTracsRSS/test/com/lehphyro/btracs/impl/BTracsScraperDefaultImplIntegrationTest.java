package com.lehphyro.btracs.impl;

import java.io.*;
import java.util.*;

import org.junit.*;

import com.google.inject.*;
import com.lehphyro.btracs.*;

public class BTracsScraperDefaultImplIntegrationTest {

	@Test
	public void test() throws Exception {
		Injector injector = Guice.createInjector(new BTracsModule());
		BTracsScraper scraper = injector.getInstance(BTracsScraper.class);
		List<WebSite> sites = scraper.getWebSitesClosedMostOfTheTime();
		
		for (WebSite site : sites) {
			System.out.printf("%s%n", site);
		}
		
		BTracsFeeder feeder = injector.getInstance(BTracsFeeder.class);
		feeder.feed("Web Sites Closed Most of the Time", sites, new PrintWriter(System.out));
	}

}
