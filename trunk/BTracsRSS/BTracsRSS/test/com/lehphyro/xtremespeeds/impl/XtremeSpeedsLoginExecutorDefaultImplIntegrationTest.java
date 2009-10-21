package com.lehphyro.xtremespeeds.impl;

import org.junit.*;

import com.google.inject.*;
import com.lehphyro.xtremespeeds.*;

public class XtremeSpeedsLoginExecutorDefaultImplIntegrationTest {

	@Test
	public void test() throws Exception {
		Injector injector = Guice.createInjector(new XtremeSpeedsModule());
		XtremeSpeedsLoginExecutor executor = injector.getInstance(XtremeSpeedsLoginExecutor.class);
		executor.login();
	}

}
