package com.versionone.sdk.unit.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={
		DBNullTests.class,
		DBTests.class,
		DateTimeTests.class,
		BitTests.class,
		IntTests.class,
		StrTests.class,
		RealTests.class
		})

public class DBTestSuite {}
