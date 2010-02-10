package com.lehphyro.btracs.impl;

import java.io.*;
import java.util.*;

import org.junit.*;

import com.lehphyro.btracs.*;

public class BTracsScraperDefaultImplIntegrationTest {

	@Test
	public void test() throws Exception {
		BTracsScrapper scraper = new BTracsScraperDefaultImpl();
		List<WebSite> sites = scraper.getWebSitesClosedMostOfTheTime();
		
		for (WebSite site : sites) {
			System.out.printf("%s%n", site);
		}
		
		BTracsFeeder feeder = new BTracsFeederDefaultImpl();
		feeder.feed(sites, new PrintWriter(System.out));
	}

}
