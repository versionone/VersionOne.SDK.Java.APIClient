package com.versionone.tests;

import com.versionone.apiclient.tests.*;
import com.versionone.util.TestDelegate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {DurationTester.class
        , TextBuilderTester.class
        , OidTester.class
        , VersionTester.class
        , DBTestSuite.class
        , MetaTestSuite.class
        , LocalizerTester.class
        , QueryTestSuite.class
        , XmlApiWriterTester.class
        , ServicesTester.class
//		, DataExamplesTester.class
        , TestDelegate.class
        , FileAPIConnectorTester.class
        , MimeTypeTester.class
        , RankTester.class
        , V1ConfigurationTester.class
        , CookiesManagerTester.class
        , MultiValueAttributeTester.class
        , V1APIConnectorTest.class
        , CookiesManagerTester.class
        , RequiredFieldValidatorTester.class})
public class AllAPIClientTests { }
