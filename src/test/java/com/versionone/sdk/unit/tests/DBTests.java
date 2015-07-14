package com.versionone.sdk.unit.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.versionone.DB;

public class DBTests {

    @Test
    public void testGetDate() {
        final DB.DateTime dateWithTime = new DB.DateTime("2000-01-01T01:40:50.000");
        final DB.DateTime dateWithoutTime = new DB.DateTime("2000-01-01");
        assertEquals(dateWithoutTime, dateWithTime.getDate());
    }
}
