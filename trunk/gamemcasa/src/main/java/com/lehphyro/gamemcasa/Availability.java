package com.lehphyro.gamemcasa;

public enum Availability {

	NOW("Tá na mão", 1000),
	SOON("Chega logo", 100),
	LATER("Demora um pouco", 10),
	UNAVAILABLE("Indisponível", 0),
	;
	
	private String name;
	private int weight;
	
	private Availability(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}
	
	public static Availability identify(String name) {
		for (Availability availability : values()) {
			if (availability.name.equals(name)) {
				return availability;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
	
	public int getWeight() {
		return weight;
	}
}
