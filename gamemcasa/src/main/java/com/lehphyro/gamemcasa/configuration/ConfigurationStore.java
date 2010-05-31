package com.lehphyro.gamemcasa.configuration;

import java.util.*;

import com.google.appengine.api.datastore.*;

public class ConfigurationStore {

	private static final String KIND = "configuration";
	private static final int ID = 1;
	
	public void save(String username, String password) {
		Query query = new Query(KIND);
		query.addFilter("id", Query.FilterOperator.EQUAL, ID);

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		
		Entity entity = datastoreService.prepare(query).asSingleEntity();
		if (entity == null) {
			entity = new Entity(KIND);
		}
		entity.setProperty("id", ID);
		entity.setProperty("gamemcasa_username", username);
		entity.setProperty("gamemcasa_password", password);
		
		datastoreService.put(entity);
	}

	public Map<String, Object> read() {
		Query query = new Query(KIND);
		query.addFilter("id", Query.FilterOperator.EQUAL, ID);

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		
		Entity entity = datastoreService.prepare(query).asSingleEntity();
		if (entity == null) {
			throw new IllegalStateException("Configuracoes nao encontradas com ID: " + ID);
		}
		
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("id", entity.getProperty("id"));
		config.put("gamemcasa_username", entity.getProperty("gamemcasa_username"));
		config.put("gamemcasa_password", entity.getProperty("gamemcasa_password"));
		
		return config;
	}
}
