package com.versionone.apiclient.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.DB;
import com.versionone.DB.Bit;


public class BitTests {
	
	private void ConstructAndTest (DB expected, Object o) {
		DB.Bit b = new DB.Bit(o);
		Assert.assertEquals(expected, b);
	}
	
	private void ConstructAndTest(boolean expected, Bit bit) {
		Assert.assertEquals(expected, bit.booleanValue());	
	}

	private void ConstructAndTest(boolean expected, int o) {
		DB.Bit b = new DB.Bit(o);
		Assert.assertEquals(expected, b.booleanValue());
	}

	private void ConstructAndTest(boolean expected, Object o) {
		DB.Bit b = new DB.Bit(o);
		Assert.assertEquals(expected, b.booleanValue());
	}
	
	@Test public void ObjectNull () {ConstructAndTest(DB.Null, null);}
	@Test public void ObjectEmptyString () {ConstructAndTest(DB.Null, "");}
	@Test public void ObjectDBNull () {ConstructAndTest(DB.Null, DB.Null);}
	@Test public void ObjectBitNull () {ConstructAndTest(DB.Null, new DB.Bit());}
	@Test public void ObjectBitFalse () {ConstructAndTest(false, new DB.Bit(false));}	
	@Test public void ObjectBitTrue () {ConstructAndTest(true, new DB.Bit(true));}
	
	@Test public void ObjectBit0 () {ConstructAndTest(false, 0);}
	@Test public void ObjectBit1 () {ConstructAndTest(true, 1);}
	@Test public void ObjectChar0 () {ConstructAndTest(false, "0");}
	@Test public void ObjectChar1 () {ConstructAndTest(true, "1");}
	@Test public void ObjectFalse () {ConstructAndTest(false, false);}
	@Test public void ObjectTrue () {ConstructAndTest(true, true);}
	@Test public void ObjectStringFalse () {ConstructAndTest(false, "false");}
	@Test public void ObjectStringTrue () {ConstructAndTest(true, "true");}
//	@Test public void ObjectINullableNull () {ConstructAndTest(DB.Null, new System.Data.SqlTypes.SqlBoolean());}

	@Test public void IsNull ()
	{
		Assert.assertTrue("Null", new DB.Bit().isNull());
		Assert.assertFalse("False Not Null", new DB.Bit(false).isNull());
		Assert.assertFalse("True Not Null", new DB.Bit(true).isNull());
	}

	@Test public void ToBoolean ()
	{
		Assert.assertEquals(true, new DB.Bit(true).booleanValue());
	}
	
	@Test(expected=NullPointerException.class)
	public void ToBooleanNull ()
	{
		@SuppressWarnings("unused")
		boolean b = new DB.Bit().booleanValue();
	}

	@Test public void ToStringTest ()
	{
		Assert.assertEquals("True", "true", new DB.Bit(true).toString());
		Assert.assertEquals("False", "false", new DB.Bit(false).toString());
		Assert.assertEquals("Null", "", new DB.Bit().toString());
	}

	@Test public void ImplicitFromDBNull ()
	{
		DB.Bit a = new DB.Bit();
		DB b = DB.Null;
		Assert.assertEquals(a, b);
	}

//	@Test public void ImplicitFromBoolean ()
//	{
//		DB.Bit a = new DB.Bit(true);
//		boolean b = true;
//		Assert.assertEquals(a, b);
//	}
//
//	@Test public void ExplicitFromString ()
//	{
//		DB.Bit a = new DB.Bit(false);
//		DB.Bit b = (DB.Bit) "false";
//		Assert.assertEquals(a, b);
//	}
//
//	@Test public void ExplicitFromNullString ()
//	{
//		DB.Bit a = new DB.Bit();
//		DB.Bit b = (DB.Bit) (String) null;
//		Assert.assertEquals(a, b);
//	}
//
//	@Test public void ExplicitFromEmptyString ()
//	{
//		DB.Bit a = new DB.Bit();
//		DB.Bit b = (DB.Bit) String.Empty;
//		Assert.assertEquals(a, b);
//	}
//
//	[ExpectedException(typeof(FormatException))]
//	@Test public void ExplicitFromInvalidString ()
//	{
//		DB.Bit a = (DB.Bit) "x";
//	}

//	@Test public void ExplicitToBoolean ()
//	{
//		bool b = (bool) new DB.Bit(true);
//		Assert.assertEquals(true, b);
//	}
//
//	[ExpectedException(typeof(InvalidCastException))]
//	@Test public void ExplicitToBooleanNull ()
//	{
//		bool a = (bool) new DB.Bit();
//	}
//
//	@Test public void ImplicitToString ()
//	{
//		String b = new DB.Bit(false);
//		Assert.assertEquals("False", b);
//	}
//
//	@Test public void ImplicitToStringNull ()
//	{
//		String b = new DB.Bit();
//		Assert.assertEquals(String.Empty, b);
//	}

	@Test public void Equality ()
	{
		Assert.assertTrue("true == true", new DB.Bit(true).equals(new DB.Bit(true)));
		Assert.assertFalse("true == false", new DB.Bit(true).equals(new DB.Bit(false)));
		Assert.assertTrue("Null == Null", new DB.Bit().equals(new DB.Bit()));
		Assert.assertFalse("true == Null", new DB.Bit(true).equals(new DB.Bit()));
		Assert.assertFalse("Null == false", new DB.Bit().equals(new DB.Bit(false)));
		Assert.assertTrue(new DB.Bit(true).equals(true));
		Assert.assertTrue(new DB.Bit(false).equals(false));
		Assert.assertTrue(new DB.Bit().equals(null));
	}

//	@Test public void Inequality ()
//	{
//		Assert.assertFalse(new DB.Bit(true) != new DB.Bit(true), "true != true");
//		Assert.assertTrue(new DB.Bit(false) != new DB.Bit(true), "false != true");
//		Assert.assertFalse(new DB.Bit() != new DB.Bit(), "Null != Null");
//		Assert.assertTrue(new DB.Bit(true) != new DB.Bit(), "true != Null");
//		Assert.assertTrue(new DB.Bit() != new DB.Bit(false), "Null != false");
//	}

}
