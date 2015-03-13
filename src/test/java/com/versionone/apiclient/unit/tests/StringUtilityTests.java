package com.versionone.apiclient.unit.tests;

import junit.framework.Assert;

import org.junit.Test;

import com.versionone.utils.V1Util;

public class StringUtilityTests {

    @Test
    public void IsNullOrEmptyTest(){
        Assert.assertTrue(V1Util.isNullOrEmpty(null));
        Assert.assertTrue(V1Util.isNullOrEmpty(""));
        Assert.assertFalse(V1Util.isNullOrEmpty("This is a string."));
        Assert.assertFalse(V1Util.isNullOrEmpty("toast"));
    }

}
