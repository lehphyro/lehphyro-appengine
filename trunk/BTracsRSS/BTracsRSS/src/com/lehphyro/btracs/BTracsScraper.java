package com.lehphyro.btracs;

import com.google.appengine.repackaged.com.google.common.collect.*;

public interface BTracsScraper {

	ImmutableList<WebSite> getWebSitesClosedMostOfTheTime() throws Exception;

}
