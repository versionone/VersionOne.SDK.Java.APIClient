package com.versionone.javasdk.unit.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={
		AssetTypeTests.class,
		AttributeDefinitionTests.class,
		MetaModelTests.class,
		OperationTests.class
		})
public class MetaTestSuite {

}
