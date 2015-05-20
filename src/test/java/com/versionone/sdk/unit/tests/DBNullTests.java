package com.versionone.sdk.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.DB;


public class DBNullTests {
	
	@Test
	public void SameAsDBNullValue ()
	{
		Assert.assertSame(DB.Null, DB.Null);
	}

	@Test
	public void SameHashCode ()
	{
		Assert.assertEquals(DB.Null.hashCode(), DB.Null.hashCode());
	}

	@Test
	public void Equality()
	{
		Assert.assertTrue(DB.Null.equals(null));
		Assert.assertTrue(DB.Null.equals(DB.Null));
	}

	@Test
	public void GetValue() {
		Assert.assertEquals(null, DB.Null.getValue());
		Assert.assertNull(DB.Null.getValue());
	}
	
	@Test
	public void String() {
		Assert.assertEquals("", DB.Null.toString());
	}
}
