package com.lehphyro.gamemcasa;

public enum Credentials {

	USERNAME("lehphyro@gmail.com"),
	PASSWORD("teste00"),
	;
	
	private final String value;
	
	private Credentials(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
