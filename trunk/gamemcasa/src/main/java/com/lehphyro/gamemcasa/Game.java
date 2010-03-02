package com.lehphyro.gamemcasa;

import java.util.*;

public class Game {

	private final String name;
	private final Availability availability;

	public Game(String name, Availability availability) {
		this.name = name;
		this.availability = availability;
	}
	
	public static class AvailabilityComparator implements Comparator<Game> {
		@Override
		public int compare(Game game1, Game game2) {
			if (game1.availability == null) {
				return -1;
			} else if (game2.availability == null) {
				return 1;
			}
			
			return game2.availability.getWeight() - game1.availability.getWeight();
		}
	}

	public String getName() {
		return name;
	}

	public Availability getAvailability() {
		return availability;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((availability == null) ? 0 : availability.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (availability == null) {
			if (other.availability != null)
				return false;
		} else if (!availability.equals(other.availability))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("availability=%s, name=%s", availability, name);
	}

}
