package com.lehphyro.gamemcasa.credentials;

import java.util.*;

import org.slf4j.*;

import com.google.appengine.api.datastore.*;
import com.google.common.collect.*;

public class CredentialsStore {
	
	private static final Logger logger = LoggerFactory.getLogger(CredentialsStore.class);
	
	private static final String KIND = "userpass";

	public void store(String username, String password) {
		logger.info("Salvando novos usuario e senha [{}]", username);

		Key key = KeyFactory.createKey(KIND, 0);

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Entity entity;
		
		try {
			entity = datastoreService.get(key);
		} catch (EntityNotFoundException e) {
			entity = null;
		}
		
		if (entity == null) {
			entity = new Entity(KIND);
		}
		entity.setProperty("username", username);
		entity.setProperty("password", password);
		
		datastoreService.put(entity);
	}

	public Map<String, String> read() {
		Key key = KeyFactory.createKey(KIND, 0);
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		try {
			Entity entity = datastoreService.get(key);
			String username = (String)entity.getProperty("username");
			String password = (String)entity.getProperty("password");
			
			return ImmutableMap.of(username, password);
		} catch (EntityNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
