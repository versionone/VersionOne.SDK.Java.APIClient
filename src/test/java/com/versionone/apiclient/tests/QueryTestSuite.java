package com.versionone.apiclient.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(value=Suite.class)
@SuiteClasses(value={QueryURLBuilderTester.class
		, QueryFilterTester.class
		, PagingTester.class
		})
public class QueryTestSuite {

}
