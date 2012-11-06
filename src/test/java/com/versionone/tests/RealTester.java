package com.versionone.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.DB;

public class RealTester {
	private void ConstructAndTest (DB expected, Object o)
	{
		DB.Real b = new DB.Real(o);
		Assert.assertEquals(expected, b);
	}

	private void ConstructAndTest (float expected, DB.Real o)
	{
		Assert.assertEquals(expected, o.getFloatValue(), 0);
	}

	private void ConstructAndTest(DB expected, DB.Real o)
	{
		Assert.assertEquals(expected, o.getValue());
	}
	
	private void ConstructAndTest(float expected, float o) {
		DB.Real actual = new DB.Real(o);
		Assert.assertEquals(expected, actual.getFloatValue(), 0);
	}
	
	private void ConstructAndTest(float expected, double d) {
		DB.Real actual = new DB.Real(d);
		Assert.assertEquals(expected, actual.getFloatValue(), 0);	
	}
	
	private void ConstructAndTest(float expected, String s) {
		DB.Real actual = new DB.Real(s);
		Assert.assertEquals(expected, actual.getFloatValue(), 0);		
	}

	@Test public void ObjectNull () {ConstructAndTest(DB.Null, (Object)null);}
	@Test public void ObjectEmptyString () {ConstructAndTest(DB.Null, "");}
	@Test public void ObjectDBNull () {ConstructAndTest(DB.Null, DB.Null);}
	@Test public void ObjectIntNull () {ConstructAndTest(DB.Null, new DB.Real());}
	@Test public void ObjectIntNotNull () {ConstructAndTest(18, new DB.Real(18));}
	@Test public void ObjectInt () {ConstructAndTest(19, 19);}
	@Test public void ObjectDouble () {ConstructAndTest(20, 20.0);}
	@Test public void ObjectString () {ConstructAndTest(21, "21");}
	//@Test public void ObjectINullable () {ConstructAndTest(22, new System.Data.SqlTypes.SqlInt32(22));}
	//@Test public void ObjectINullableNull () {ConstructAndTest(DB.Null, new System.Data.SqlTypes.SqlInt32());}

	@Test public void IsNull ()
	{
		Assert.assertTrue("Null", new DB.Real().isNull());
		Assert.assertFalse("Not Null", new DB.Real(11).isNull());
	}

	@Test public void ToInt32 ()
	{
		Assert.assertEquals(12, new DB.Real(12).getFloatValue(), 0);
	}

	@Test(expected=NullPointerException.class) 
	public void ToInt32Null ()
	{
		@SuppressWarnings("unused")
		float i = new DB.Real().getFloatValue();
	}

	@Test public void toStringTest ()
	{
		Assert.assertEquals("Not Null", "13.0", new DB.Real(13).toString());
		Assert.assertEquals("Null", "", new DB.Real().toString());
	}
	
	@Test public void Equality ()
	{
		Assert.assertTrue("1 == 1", new DB.Real(1).equals(new DB.Real(1)));
		Assert.assertFalse("2 == 3", new DB.Real(2).equals(new DB.Real(3)));
		Assert.assertTrue("Null == Null", new DB.Real().equals(new DB.Real()));
		Assert.assertFalse("4 == Null", new DB.Real(4).equals(new DB.Real()));
		Assert.assertFalse("Null == 5", new DB.Real().equals(new DB.Real(5)));
	}
}
