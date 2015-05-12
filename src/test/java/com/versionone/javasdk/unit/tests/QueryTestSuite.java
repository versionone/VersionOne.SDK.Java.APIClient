package com.versionone.javasdk.unit.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(value=Suite.class)
@SuiteClasses(value={
		QueryURLBuilderTests.class,
		QueryFilterTests.class,
		PagingTests.class,
		})
public class QueryTestSuite {

}
