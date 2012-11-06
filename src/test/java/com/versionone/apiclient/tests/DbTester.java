/*(c) Copyright 2008, VersionOne, Inc. All rights reserved. (c)*/
package com.versionone.apiclient.tests;

import com.versionone.DB;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DbTester {

    @Test
    public void testGetDate() {
        final DB.DateTime dateWithTime = new DB.DateTime("2000-01-01T01:40:50");
        final DB.DateTime dateWithoutTime = new DB.DateTime("2000-01-01");
        assertEquals(dateWithoutTime, dateWithTime.getDate());
    }
}
