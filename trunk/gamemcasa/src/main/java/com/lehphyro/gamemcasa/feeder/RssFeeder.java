package com.lehphyro.gamemcasa.feeder;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.*;

import com.lehphyro.gamemcasa.*;
import com.lehphyro.gamemcasa.scrapper.page.*;

import de.nava.informa.core.*;
import de.nava.informa.exporters.*;
import de.nava.informa.impl.basic.*;

public class RssFeeder implements Feeder {

	private static final String RSS_TITLE = "My Games";

	@Override
	public void feed(List<Game> games, Writer writer) throws IOException {
		ChannelBuilder builder = new ChannelBuilder();
		ChannelIF channel = builder.createChannel(RSS_TITLE);
		
		Collections.sort(games, new Game.AvailabilityComparator());
		for (Game game : games) {
			builder.createItem(channel, game.getName(), game.getAvailability().getName(), HomePage.HOME_URI.toURL());
		}
		ChannelExporterIF exporter = new RSS_2_0_Exporter(writer, CharEncoding.UTF_8);
		exporter.write(channel);
	}

}
