package com.versionone.javasdk.unit.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.versionone.Duration;

public class DurationTests {

	@Test
	public void DefaultUnitsToDays ()
	{
		Duration d = new Duration();
		assertEquals(Duration.Unit.Days, d.getUnits());
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void CannotHaveNegativeDuration ()
	{
		Duration d = new Duration(-3, Duration.Unit.Days);
		assertNull(d);
	}

	@Test public void DaysToString ()
	{
		Duration d = new Duration(5, Duration.Unit.Days);
		assertEquals("5 Days", d.toString());
	}

	@Test public void WeeksToString ()
	{
		Duration d = new Duration(5, Duration.Unit.Weeks);
		assertEquals("5 Weeks", d.toString());
	}

	@Test public void MonthsToString ()
	{
		Duration d = new Duration(5, Duration.Unit.Months);
		assertEquals("5 Months", d.toString());
	}

	@Test public void ParseDays ()
	{
		Duration d = Duration.parse("10 Days");
		assertEquals("Amount", 10, d.getAmount());
		assertEquals("Units", Duration.Unit.Days, d.getUnits());
	}

	@Test public void ParseWeeks ()
	{
		Duration d = Duration.parse("10 Weeks");
		assertEquals("Amount", 10, d.getAmount());
		assertEquals("Units", Duration.Unit.Weeks, d.getUnits());
	}

	@Test public void ParseMonths ()
	{
		Duration d = Duration.parse("10 Months");
		assertEquals("Amount", 10, d.getAmount());
		assertEquals("Units", Duration.Unit.Months, d.getUnits());
	}

	
	@Test(expected=IllegalArgumentException.class) 
	public void ParseBadAmount ()
	{
		@SuppressWarnings("unused")
		Duration d = Duration.parse("1x0 Months");
	}

	@Test(expected=IllegalArgumentException.class)
	public void ParseBadUnit ()
	{
		@SuppressWarnings("unused")
		Duration d = Duration.parse("10 Moons");
	}

	@Test public void ParseNull ()
	{
		Duration d = Duration.parse(null);
		assertEquals(d.getAmount(),0);
		assertEquals(d.getUnits(),Duration.Unit.Days);
	}

	@Test public void Equality ()
	{
		Duration d1 = new Duration(12, Duration.Unit.Weeks);
		Duration d2 = new Duration(12, Duration.Unit.Weeks);
		assertEquals(d1, d2);
	}

	@Test public void EqualOpTrue ()
	{
		Duration d1 = new Duration(12, Duration.Unit.Weeks);
		Duration d2 = new Duration(12, Duration.Unit.Weeks);
		assertTrue(Duration.compare(d1, d2));
	}

	@Test public void EqualOpFalse ()
	{
		Duration d1 = new Duration(12, Duration.Unit.Weeks);
		Duration d2 = new Duration(13, Duration.Unit.Weeks);
		assertFalse(Duration.compare(d1, d2));
	}

	@Test public void NotEqualOpTrue ()
	{
		Duration d1 = new Duration(12, Duration.Unit.Weeks);
		Duration d2 = new Duration(13, Duration.Unit.Weeks);
		assertFalse(Duration.compare(d1, d2));
	}

	@Test public void NotEqualOpFalse ()
	{
		Duration d1 = new Duration(12, Duration.Unit.Weeks);
		Duration d2 = new Duration(12, Duration.Unit.Weeks);
		assertTrue(Duration.compare(d1, d2));
	}

	@Test public void ToStringZero ()
	{
		assertEquals("0 Days", new Duration(0, Duration.Unit.Weeks).toString());
	}

	@Test public void FromStringZero ()
	{
		assertEquals(new Duration(0, Duration.Unit.Weeks), Duration.parse("0"));
	}

	@Test public void DaysConversionWeeksToDays()
	{
		Duration d1 = new Duration(12,Duration.Unit.Weeks);
		assertEquals(84, d1.getDays());
	}

	@Test public void DaysConversionMonthToDays()
	{
		Duration d1 = new Duration(1,Duration.Unit.Months);
		assertEquals(30, d1.getDays());
	}

	@Test public void DaysConversionMonthsToDays()
	{
		Duration d1 = new Duration(2,Duration.Unit.Months);
		assertEquals(61, d1.getDays());
	}

	@Test public void DaysConversionSemiYearToDays()
	{
		Duration d1 = new Duration(6,Duration.Unit.Months);
		assertEquals(183, d1.getDays());
	}

	@Test public void DaysConversionYearToDays()
	{
		Duration d1 = new Duration(12,Duration.Unit.Months);
		assertEquals(365, d1.getDays());
	}

	@Test public void DaysConversionLucky13MonthsToDays()
	{
		Duration d1 = new Duration(13,Duration.Unit.Months);
		assertEquals(395, d1.getDays());
	}
	
	@Test public void DaysPropertyToDays()
	{
		Duration d = new Duration(5,Duration.Unit.Days);
		assertEquals(5,d.getDays());
	}
		
	@Test
	public void testEqualsObject() {
		Duration d1 = new Duration(12, Duration.Unit.Weeks);
		Duration d2 = new Duration(12, Duration.Unit.Weeks);		
		assertTrue(d1.equals(d2));
		assertFalse(d1 == d2);
		
		Duration d3 = new Duration(12, Duration.Unit.Weeks);
		Duration d4 = d3;
		assertTrue(d3.equals(d4));
		assertTrue(d3 == d4);
	}	
}
