package com.versionone.javasdk.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.V1Configuration;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.interfaces.IV1Configuration;

public class ConfigurationTests {
    public static final String TEST_DATA = "testdata/TestData.xml";

    private static void RunTest(String testName, boolean exepectedTracking,
                                IV1Configuration.TrackingLevel exepectedStoryLevel,
                                IV1Configuration.TrackingLevel expectedDefectLevel) throws ConnectionException, APIException {
        V1Configuration testSubject = new V1Configuration(new ResponseConnector.XMLResponseConnector(
                TEST_DATA, "config.v1/" + testName, "V1ConfigurationTester"));
        Assert.assertEquals(exepectedTracking, testSubject.isEffortTracking());
        Assert.assertEquals(exepectedStoryLevel, testSubject.getStoryTrackingLevel());
        Assert.assertEquals(expectedDefectLevel, testSubject.getDefectTrackingLevel());
    }

    @Test
    public void TrueOffOff() throws ConnectionException, APIException {
        RunTest("TrueOffOff", true, IV1Configuration.TrackingLevel.Off, IV1Configuration.TrackingLevel.Off);
    }

    @Test
    public void TrueOnOn() throws ConnectionException, APIException {
        RunTest("TrueOnOn", true, IV1Configuration.TrackingLevel.On, IV1Configuration.TrackingLevel.On);
    }

    @Test
    public void TrueOffOn() throws ConnectionException, APIException {
        RunTest("TrueOffOn", true, IV1Configuration.TrackingLevel.Off, IV1Configuration.TrackingLevel.On);
    }

    @Test
    public void TrueOnOff() throws ConnectionException, APIException {
        RunTest("TrueOnOff", true, IV1Configuration.TrackingLevel.On, IV1Configuration.TrackingLevel.Off);
    }

    @Test
    public void FalseOffOff() throws ConnectionException, APIException {
        RunTest("FalseOffOff", false, IV1Configuration.TrackingLevel.Off, IV1Configuration.TrackingLevel.Off);
    }

    @Test
    public void FalseOnOn() throws ConnectionException, APIException {
        RunTest("FalseOnOn", false, IV1Configuration.TrackingLevel.On, IV1Configuration.TrackingLevel.On);
    }

    @Test
    public void FalseOffOn() throws ConnectionException, APIException {
        RunTest("FalseOffOn", false, IV1Configuration.TrackingLevel.Off, IV1Configuration.TrackingLevel.On);
    }

    @Test
    public void FalseOnOff() throws ConnectionException, APIException {
        RunTest("FalseOnOff", false, IV1Configuration.TrackingLevel.On, IV1Configuration.TrackingLevel.Off);
    }
}
