package com.lehphyro.xtremespeeds.impl;

import org.junit.*;

import com.lehphyro.xtremespeeds.*;

public class XtremeSpeedsLoginExecutorDefaultImplIntegrationTest {

	@Test
	public void test() throws Exception {
		XtremeSpeedsLoginExecutor executor = new XtremeSpeedsLoginExecutorDefaultImpl();
		executor.login();
	}

}
