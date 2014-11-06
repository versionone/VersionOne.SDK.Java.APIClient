package com.versionone.apiclient.unit.tests;

import com.versionone.util.StringUtility;
import junit.framework.Assert;
import org.junit.Test;

public class StringUtilityTests {

    @Test
    public void IsNullOrEmptyTest(){
        Assert.assertTrue(StringUtility.IsNullOrEmpty(null));
        Assert.assertTrue(StringUtility.IsNullOrEmpty(""));
        Assert.assertFalse(StringUtility.IsNullOrEmpty("This is a string."));
        Assert.assertFalse(StringUtility.IsNullOrEmpty("toast"));
    }

}
