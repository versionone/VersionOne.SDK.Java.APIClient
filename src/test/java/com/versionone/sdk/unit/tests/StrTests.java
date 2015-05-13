package com.versionone.sdk.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.DB;

public class StrTests {
	
	private void ConstructAndTest (DB expected, Object o)
	{
		DB.Str b = new DB.Str(o);
		Assert.assertEquals(expected, b);
	}

	private void ConstructAndTest (DB expected, DB.Str o)
	{
		Assert.assertEquals(expected, o);
	}

	private void ConstructAndTest(String expected, DB.Str str) {
		Assert.assertEquals(expected, str.toString());
	}

	private void ConstructAndTest(String expected, String s) {
		DB.Str actual = new DB.Str(s);
		Assert.assertEquals(expected, actual.toString());
	}
	
	@Test public void ObjectNull () {ConstructAndTest(DB.Null, null);}
	@Test public void ObjectDBNull () {ConstructAndTest(DB.Null, DB.Null);}
	@Test public void ObjectEmptyString () {ConstructAndTest(DB.Null, "");}
	@Test public void ObjectStrNull () {ConstructAndTest(DB.Null, new DB.Str());}
	@Test public void ObjectStrNotNull () {ConstructAndTest("bbb", new DB.Str("bbb"));}
	@Test public void ObjectString () {ConstructAndTest("aaa", "aaa");}
//	@Test public void ObjectINullable () {ConstructAndTest("ccc", new System.Data.SqlTypes.SqlString("ccc"));}
//	@Test public void ObjectINullableNull () {ConstructAndTest(DB.Null, new System.Data.SqlTypes.SqlString());}

	@Test public void IsNull ()
	{
		Assert.assertTrue("Null", new DB.Str().isNull());
		Assert.assertFalse("Not Null", new DB.Str("ddd").isNull());
	}

	@Test public void ToStringTest ()
	{
		Assert.assertEquals("Not Null", "eee", new DB.Str("eee").toString());
		Assert.assertEquals("Null", "", new DB.Str().toString());
	}

//	@Test public void ImplicitFromDBNull ()
//	{
//		DB.Str a = new DB.Str();
//		DB.Str b = DB.Null;
//		Assert.assertEquals(a, b);
//	}
//
//	@Test public void ImplicitFromString ()
//	{
//		DB.Str a = new DB.Str("fff");
//		DB.Str b = "fff";
//		Assert.assertEquals(a, b);
//	}
//
//	@Test public void ImplicitFromNullString ()
//	{
//		DB.Str a = new DB.Str();
//		DB.Str b = (string) null;
//		Assert.assertEquals(a, b);
//	}
//
//	@Test public void ImplicitFromEmptyString ()
//	{
//		DB.Str a = new DB.Str();
//		DB.Str b = string.Empty;
//		Assert.assertEquals(a, b);
//	}
//
//	@Test public void ImplicitToString ()
//	{
//		string b = new DB.Str("ggg");
//		Assert.assertEquals("ggg", b);
//	}
//
//	@Test public void ImplicitToStringNull ()
//	{
//		string b = new DB.Str();
//		Assert.assertEquals(string.Empty, b);
//	}

	@Test public void Equality ()
	{
		Assert.assertTrue("1 == 1", new DB.Str("1").equals(new DB.Str("1")));
		Assert.assertFalse("2 == 3", new DB.Str("2").equals(new DB.Str("3")));
		Assert.assertTrue("Null == Null", new DB.Str().equals(new DB.Str()));
		Assert.assertFalse("4 == Null", new DB.Str("4").equals(new DB.Str()));
		Assert.assertFalse("Null == 5", new DB.Str().equals(new DB.Str("5")));
	}

//	@Test public void Inequality ()
//	{
//		Assert.assertFalse(new DB.Str("6") != new DB.Str("6"), "6 != 6");
//		Assert.assertTrue(new DB.Str("7") != new DB.Str("8"), "7 != 8");
//		Assert.assertFalse(new DB.Str() != new DB.Str(), "Null != Null");
//		Assert.assertTrue(new DB.Str("9") != new DB.Str(), "9 != Null");
//		Assert.assertTrue(new DB.Str() != new DB.Str("10"), "Null != 10");
//	}

	@Test public void Trims ()
	{
		Assert.assertEquals("Leading", new DB.Str("xyz"), new DB.Str("   xyz"));
		Assert.assertEquals("Trailing", new DB.Str("xyz"), new DB.Str("xyz   "));
		Assert.assertEquals("Leading and Trailing", new DB.Str("xyz"), new DB.Str("   xyz   "));
		Assert.assertEquals("All Blank", new DB.Str(), new DB.Str("    "));
	}

}
