package com.versionone.tests;

import com.versionone.DB;
import com.versionone.Duration;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeTester {
	private Date _now;
	private Date _notnow;
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Before
	public void Setup () {
		_now = Calendar.getInstance().getTime();
		_notnow = new Date(_now.getTime() + 1001);
	}

	private void ConstructAndTest (DB expected, Object o) throws Exception {
		DB.DateTime b = new DB.DateTime(o);
		Assert.assertEquals(expected, b);
	}

	private void ConstructAndTest(Date expected, DB.DateTime o) {
		Assert.assertEquals(expected, o.getValue());
	}

	private void ConstructAndTest(Date d1, Date d2) {
		@SuppressWarnings("unused")
		DB.DateTime dt1 = new DB.DateTime(d1);
		@SuppressWarnings("unused")
		DB.DateTime dt2 = new DB.DateTime(d2);
		Assert.assertEquals(d1, d2);
	}

	@Test
	public void ObjectNull () throws Exception {ConstructAndTest(DB.Null, null);}

	@Test
	public void ObjectEmptyString () throws Exception {ConstructAndTest(DB.Null, "");}

	@Test
	public void ObjectDBNull () throws Exception {ConstructAndTest(DB.Null, DB.Null);}

	@Test
	public void ObjectDBDateTimeNull () throws Exception {ConstructAndTest(DB.Null, new DB.DateTime());}

	@Test
	public void ObjectDBDateTimeNotNull () {ConstructAndTest(_now, new DB.DateTime(_now));}

	@Test
	public void ObjectSysDateTime () {ConstructAndTest(_now, _now);}

	// the date we are trying to get here is February 03, 2001 04:05:06 AM
	@Test
	public void ObjectString () throws Exception {
		GregorianCalendar calendar = new GregorianCalendar(2001, 1, 3, 4, 5, 6);
//        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date expectedDate = calendar.getTime();
		String actualAsString = "2001-02-03T04:05:06";
		ConstructAndTest(expectedDate, actualAsString);
	}

	private void ConstructAndTest(Date date, String string) throws Exception {
		DB.DateTime testMe = new DB.DateTime(string);
		Assert.assertEquals(date, testMe.getValue());

	}

	@Test
	public void IsNull () {
		Assert.assertTrue("Null", new DB.DateTime().isNull());
		Assert.assertFalse("Not Null", new DB.DateTime(_now).isNull());
	}

	@Test
	public void ToDateTime () {
		Assert.assertEquals(_now, new DB.DateTime(_now).getValue());
	}

	@Test
	public void ToDateTimeNull () {
		Date d = (Date) new DB.DateTime().getValue();
		Assert.assertNull(d);
	}

	@Test
	public void ToStringTest () {
		Assert.assertEquals("Not Null", format.format(_now), new DB.DateTime(_now).toString());
		Assert.assertEquals("Null", "", new DB.DateTime().toString());
	}

	@Test
	public void ImplicitFromDBNull () {
		DB.DateTime a = new DB.DateTime();
		DB.DateTime b = null;
		Assert.assertTrue(a.equals(b));
	}

	@Test
	public void testFromStringNoTime() {
		DB.DateTime testMe = new DB.DateTime("2007-10-29");
		Assert.assertNotNull(testMe);

		GregorianCalendar calendar = new GregorianCalendar(2007, 9, 29, 0, 0, 0);
//        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date expectedDate = calendar.getTime();
		Assert.assertEquals(expectedDate, testMe.getValue());
	}

	@Test
	public void ExplicitToDateTime () {
		Date b = new DB.DateTime(_now).getValue();
		Assert.assertEquals(_now, b);
	}

	@Test
	public void ImplicitToString () {
		String b = new DB.DateTime(_now).toString();
		Assert.assertEquals(format.format(_now), b);
	}

	@Test
	public void ImplicitToStringNull () {
		String b = new DB.DateTime().toString();
		Assert.assertEquals("", b);
	}

	@Test
	public void Equality () {
		Assert.assertTrue("now == now", new DB.DateTime(_now).equals(new DB.DateTime(_now)));
		Assert.assertFalse("now == not now", new DB.DateTime(_now).equals(new DB.DateTime(_notnow)));
		Assert.assertTrue("Null == Null", new DB.DateTime().equals(new DB.DateTime()));
		Assert.assertFalse("now == null", new DB.DateTime(_now).equals(new DB.DateTime()));
		Assert.assertFalse("null == now", new DB.DateTime().equals(new DB.DateTime(_now)));
	}

    private final int MONTH_LENGTH[] = {31,28,31,30,31,30,31,31,30,31,30,31};
    private final int LEAP_MONTH_LENGTH[] = {31,29,31,30,31,30,31,31,30,31,30,31};
	@Test
	public void Subtract() {
	    DB.DateTime date1 = new DB.DateTime(_now);
	    DB.DateTime date2;
	    Duration duration;
	    final int numberOfDays = 10;
	    final int numberOfMonth = 2;

	    final String exceptionMessage = "Date has null value";
	    GregorianCalendar calendar = new GregorianCalendar();

	    calendar.setTime(_now);
	    calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);
	    date2 = new DB.DateTime(calendar.getTime());
	    duration = date1.subtract(date2);
	    Assert.assertEquals("10 Days between dates", numberOfDays, duration.getDays());

	    calendar.add(Calendar.DAY_OF_MONTH, numberOfDays * 10);
	    date2 = new DB.DateTime(calendar.getTime());
	    duration = date1.subtract(date2);
	    Assert.assertEquals("many days between dates", (numberOfDays + numberOfDays * 10), duration.getDays());

	    calendar.setTime(_now);
	    int summOfDays = 0;
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
	    for (int i = 0; i < numberOfMonth; i++) {
	        int month = calendar.get(Calendar.MONTH);
	        int year = calendar.get(Calendar.YEAR);
	        int numberOfDay = getMonthLength(calendar.isLeapYear(year),  month);
	        summOfDays += numberOfDay;
	        // this need for case if current date has days in month more than in next month.
	        // for example: 30 Jan 2010, but in Feb only 28 days.
	        summOfDays -= getDeltaInDays(calendar, day, month, year);
	        calendar.add(Calendar.MONTH, 1);
	    }
	    date2 = new DB.DateTime(calendar.getTime());
	    duration = date1.subtract(date2);
	    Assert.assertEquals(numberOfMonth+" month between dates", summOfDays, duration.getDays());

        date2 = new DB.DateTime(null);
        try{
            date1.subtract(date2);
            fail("No IllegalStateException when one of the date has value null");
        } catch (IllegalStateException ex) {
            Assert.assertEquals("Exception message", exceptionMessage, ex.getMessage());
        }
	}

	private int getDeltaInDays(GregorianCalendar calendar, int day, int month, int year) {
		int daysInNextMonth = 0;
		if (month + 1 > 11) {
			daysInNextMonth = getMonthLength(calendar.isLeapYear(year + 1), 0);
		} else {
			daysInNextMonth = getMonthLength(calendar.isLeapYear(year), month + 1);
		}
		return day > daysInNextMonth ? day - daysInNextMonth : 0;
	}

	private int getMonthLength(boolean isLeap, int month) {
		return isLeap ? LEAP_MONTH_LENGTH[month] : MONTH_LENGTH[month];
	}

}
