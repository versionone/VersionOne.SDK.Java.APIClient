package com.versionone.tests;

import com.versionone.util.StringUtility;
import junit.framework.Assert;
import org.junit.Test;

public class StringUtilityTester {

    @Test
    public void IsNullOrEmptyTest(){
        Assert.assertTrue(StringUtility.IsNullOrEmpty(null));
        Assert.assertTrue(StringUtility.IsNullOrEmpty(""));
        Assert.assertFalse(StringUtility.IsNullOrEmpty("This is a string."));
        Assert.assertFalse(StringUtility.IsNullOrEmpty("toast"));
    }

}
