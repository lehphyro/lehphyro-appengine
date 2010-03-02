package com.lehphyro.gamemcasa.scrapper;

import com.lehphyro.gamemcasa.*;

import static org.apache.commons.lang.StringUtils.*;

public class GameParser {

	private static final String PLAYSTATION_ID = "(PlayStation 3)";

	public Game parse(String name, String availability) {
		String realName = remove(name, PLAYSTATION_ID);
		Availability realAvailability = Availability.identify(availability);
		
		return new Game(realName, realAvailability);
	}

}
