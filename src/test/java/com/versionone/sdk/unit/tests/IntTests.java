package com.versionone.sdk.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.DB;

public class IntTests {

	private void ConstructAndTest (DB expected, Object o)
	{
		DB.Int b = new DB.Int(o);
		Assert.assertEquals(expected, b);
	}

	private void ConstructAndTest (int expected, DB.Int o)
	{
		Assert.assertEquals(expected, (Object)o.getValue());
	}
	
	private void ConstructAndTest(int expected, int o) {
		DB.Int actual = new DB.Int(o);
		Assert.assertEquals(expected, actual.intValue());
	}
	
	private void ConstructAndTest(int expected, double d) {
		DB.Int actual = new DB.Int(d);
		Assert.assertEquals(expected, actual.intValue());		
	}
	
	private void ConstructAndTest(int expected, String s) {
		DB.Int actual = new DB.Int(s);
		Assert.assertEquals(expected, actual.intValue());		
	}

	@Test public void ObjectNull () {ConstructAndTest(DB.Null, null);}
	@Test public void ObjectEmptyString () {ConstructAndTest(DB.Null, "");}
	@Test public void ObjectDBNull () {ConstructAndTest(DB.Null, DB.Null);}
	@Test public void ObjectIntNull () {ConstructAndTest(DB.Null, new DB.Int());}
	@Test public void ObjectIntNotNull () {ConstructAndTest(18, new DB.Int(18));}
	@Test public void ObjectInt () {ConstructAndTest(19, 19);}
	@Test public void ObjectDouble () {ConstructAndTest(20, 20.0);}
	@Test public void ObjectString () {ConstructAndTest(21, "21");}
	//@Test public void ObjectINullable () {ConstructAndTest(22, new System.Data.SqlTypes.SqlInt32(22));}
	//@Test public void ObjectINullableNull () {ConstructAndTest(DB.Null, new System.Data.SqlTypes.SqlInt32());}

	@Test public void IsNull ()
	{
		Assert.assertTrue("Null", new DB.Int().isNull());
		Assert.assertFalse("Not Null", new DB.Int(11).isNull());
	}

	@Test public void ToInt32 ()
	{
		Assert.assertEquals(12, new DB.Int(12).longValue());
	}

	@Test(expected=NullPointerException.class) 
	public void ToInt32Null ()
	{
		@SuppressWarnings("unused")
		int i = new DB.Int().intValue();
	}

	@Test public void toStringTest ()
	{
		Assert.assertEquals("Not Null", "13", new DB.Int(13).toString());
		Assert.assertEquals("Null", "", new DB.Int().toString());
	}
	
	@Test public void Equality ()
	{
		Assert.assertTrue("1 == 1", new DB.Int(1).equals(new DB.Int(1)));
		Assert.assertFalse("2 == 3", new DB.Int(2).equals(new DB.Int(3)));
		Assert.assertTrue("Null == Null", new DB.Int().equals(new DB.Int()));
		Assert.assertFalse("4 == Null", new DB.Int(4).equals(new DB.Int()));
		Assert.assertFalse("Null == 5", new DB.Int().equals(new DB.Int(5)));
	}	
}
