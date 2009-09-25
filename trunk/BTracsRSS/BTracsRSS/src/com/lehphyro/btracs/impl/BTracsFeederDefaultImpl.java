package com.lehphyro.btracs.impl;

import java.io.*;
import java.net.*;
import java.util.*;

import com.lehphyro.btracs.*;

import de.nava.informa.core.*;
import de.nava.informa.exporters.*;
import de.nava.informa.impl.basic.*;

public class BTracsFeederDefaultImpl implements BTracsFeeder {

	@Override
	public void feed(String title, List<WebSite> sites, Writer writer) throws IOException {
		ChannelBuilder builder = new ChannelBuilder();
		ChannelIF channel = builder.createChannel(title);

		for (WebSite site : sites) {
			String description = String.format("Category: %s - Language: %s - Ranking: %s", site.getCategory(), site.getLanguage(), site.getRanking());
			builder.createItem(channel, site.getName(), description, new URL(site.getUrl()));
		}
		ChannelExporterIF exporter = new RSS_2_0_Exporter(writer, "UTF-8");
		exporter.write(channel);
	}

}
