package com.lehphyro.xtremespeeds;

import com.google.inject.*;
import com.lehphyro.xtremespeeds.impl.*;

public class XtremeSpeedsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(XtremeSpeedsLoginExecutor.class).to(XtremeSpeedsLoginExecutorDefaultImpl.class);
	}

}
