package com.versionone.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={DBNullTester.class
		, DateTimeTester.class
		, BitTester.class
		, IntTester.class
		, StrTester.class
		, RealTester.class
		})
public class DBTestSuite {

}
