package com.versionone.sdk.unit.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {
		DurationTests.class,
        TextBuilderTests.class,
        OidTests.class,
        VersionTests.class,
        DBTestSuite.class,
        MetaTestSuite.class,
        LocalizerTests.class,
        QueryTestSuite.class,
        XmlApiWriterTests.class,
        ServicesTests.class,
//        TestDelegate.class,
        FileAPIConnectorTests.class,
        MimeTypeTests.class,
        RankTests.class,
        ConfigurationTests.class,
        CookiesManagerTests.class,
        MultiValueAttributeTests.class,
        RequiredFieldValidatorTests.class,
        CredentialsTests.class,
        StringUtilityTests.class,
		ServicesConstructorWhenPreLoadMetaIsTrue.class,
		ServicesConstructorWhenPreLoadMetaIsFalse.class
})

public class APIClientUnitTestSuite { }
