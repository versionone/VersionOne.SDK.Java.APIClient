package com.versionone;

import com.versionone.apiclient.HashCode;

/**
 * Class representing some duration of time
 * @author jerry
 *
 */
public class Duration {

	/**
	 * Valid units for Duration
	 * @author jerry
	 *
	 */
	public enum Unit {
		Days, Weeks, Months
	}

	private int _amount;
	private Unit _units;

	/**
	 * Default constructor
	 * sets duration to 0 days
	 */
	public Duration() {
		this(0, Unit.Days);
	}

	/**
	 * Create from amount and unit
	 * @param amount - amount value for this instance
	 * @param units  - unit value for this instance
	 */
	public Duration(int amount, Unit units) {
		_units = units;
		setAmount(amount);
	}
	
	/**
	 * Create instance from a String 
	 * Expected format is "%d {Unit}" for example "3 Days" (without the quotes)
	 * 
	 * @param value - string value used to set amount and unit
	 * @throws IllegalArgumentException if thre is an error in parsing the value
	 */
	public Duration(String value) {
		this(0, Unit.Days);
		if ((value == null || value.length() == 0)) {
			return;
		}
		try {
			String[] parts = value.split(" ");
			setAmount(Integer.parseInt(parts[0]));
			if (_amount != 0)
				_units = Unit.valueOf(parts[1]);
		} catch (Exception e) {
			throw new IllegalArgumentException("Not a valid Duration: " + value, e);
		}
	}
	
	/**
	 * return the amount specified by this Duration
	 * @return integer 
	 */
	public int getAmount() {
		return _amount;
	}

	/**
	 * return the units specified by this Duration
	 * @return Unit
	 */
	public Unit getUnits() {
		return _units;
	}

	/**
	 * Get amount of this Duration in days 
	 * @return
	 */
	public int getDays() {
		switch (_units) {
		case Days:
			return _amount;
		case Weeks:
			return _amount * 7;
		case Months:
			if (_amount <= 1)
				return _amount * 30;
			else if (_amount < 12)
				return (_amount * 61) / 2;
			else
				return (_amount * 365) / 12;
		default:
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Compare two Duration instances
	 * @param a - one instance
	 * @param b - the other instance
	 * @return true if they are equal, false otherwise
	 */
	public static boolean compare(Duration a, Duration b) {
		if (null == a || null == b)
			return (null == a) && (null == b);
		return a.equals(b);
	}

	/**
	 * Create a Duration from a String
	 * 
	 * @param value - string used to create
	 * @return an instance of Duration with value set from string
	 * @see #Duration(String)
	 */
	public static Duration parse(String value) {
		return new Duration(value);
	}

	/**
	 * Convert this instance to it's string representation (%d {Unit}) 
	 */
	@Override
	public String toString() {
		return _amount + " " + _units.toString();
	}

	/**
	 * Compare this instance to another Duration
	 * @return true if they are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		boolean rc = false;
		if ((obj != null) && (obj instanceof Duration)) {
			Duration other = (Duration) obj;
			rc = (_amount == other.getAmount()) && (_units == other.getUnits());
		}
		return rc;
	}

	/**
	 * Get the hash code for this instance
	 */
	@Override
	public int hashCode() {
		return HashCode.Hash(_amount, _units);
	}
	
	private void setAmount(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount must be non-negative");
		}
		_amount = amount;
		if (amount == 0)
			_units = Unit.Days;
	}	
}
