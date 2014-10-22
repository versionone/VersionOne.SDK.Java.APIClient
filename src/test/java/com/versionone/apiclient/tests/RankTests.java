package com.versionone.apiclient.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.DB;
import com.versionone.apiclient.Rank;

public class RankTests {
	
    private static Rank rank(Object o) {
        return new Rank(o);
    }

    @Test
    public void compare() {
        Assert.assertTrue(rank("5").compareTo(rank("5")) == 0);
        Assert.assertTrue(rank("5").compareTo(rank("4")) > 0);
        Assert.assertTrue(rank("5").compareTo(rank("6")) < 0);
        Assert.assertEquals(rank("5"), rank("5"));
        Assert.assertFalse(rank("5").equals(rank("4")));
        Assert.assertFalse(rank("5").equals(rank("6")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void transientDontCompare1() {
        rank("5+").compareTo(rank("5-"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void transientDontCompare2() {
        rank("5+").compareTo(rank("5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void transientDontCompare3() {
        rank("5").compareTo(rank("5+"));
    }

    @Test
    public void constructor() {
        final Rank expected = rank("5");

        Assert.assertEquals(expected, rank(5));
        Assert.assertEquals(expected, rank(expected));
        Assert.assertEquals(rank(Integer.MAX_VALUE), rank(DB.Null));
        Assert.assertEquals(rank(Integer.MAX_VALUE), rank(null));
        Assert.assertFalse(expected.equals(new DB.Int()));
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("5", rank(5).toString());
        Assert.assertEquals("5+", rank(5).after().toString());
        Assert.assertEquals("5-", rank(5).before().toString());
    }

    @Test
    public void offsetTest() {
        Assert.assertTrue(rank("5+").isAfter());
        Assert.assertFalse(rank("5+").isBefore());
        Assert.assertFalse(rank("5-").isAfter());
        Assert.assertTrue(rank("5-").isBefore());
        Assert.assertFalse(rank("5").isAfter());
        Assert.assertFalse(rank("5").isBefore());
    }
}
