package com.lehphyro.btracs;

import com.google.inject.*;
import com.lehphyro.btracs.impl.*;

public class BTracsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(BTracsScraper.class).to(BTracsScraperDefaultImpl.class);
		bind(BTracsFeeder.class).to(BTracsFeederDefaultImpl.class);
	}

}
