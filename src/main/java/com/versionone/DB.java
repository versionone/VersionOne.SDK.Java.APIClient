package com.versionone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Base class for data types used in APIClient.  Internal types are wrapped
 * to allow for null values.
 *
 * @author jerry
 */
public abstract class DB {

	private static final String NullConversionMessage = "Cannot convert from NULL to non-NULL";
	private static final String _UnknownConversionMessage = "Unsupported conversion from %s";

	private static String UnknownConversionMessage(Object o) {
		return String.format(_UnknownConversionMessage, o == null ? "null" : o.getClass().getName());
	}

	private boolean notNull = false;

	protected DB() { }

	/**
	 * Get the value of the data.
	 *
	 * @return
	 */
	public abstract Object getValue();

	/**
	 * Compare this object to another and return true if they are equal
	 */
	@Override
	public abstract boolean equals(Object obj);

	/**
	 * Return the hashCode for the object
	 */
	@Override
	public int hashCode() {
		return isNull() ? Null.hashCode() : getValue().hashCode();
	}

	/**
	 * return the String representation of this object
	 */
	@Override
	public String toString() {
		return isNull() ? Null.toString() : getValue().toString();
	}

	/**
	 * Is the value null?
	 *
	 * @return true if the value is null, false otherwise
	 */
	public boolean isNull() {
		return !notNull;
	}

	void setNotNull(boolean value) {
		notNull = value;
	}

	public static final NullObject Null = new NullObject();

	/**
	 * Represents a NULL DB object
	 *
	 * @author jerry
	 */
	public static class NullObject extends DB {

		private NullObject() { }

		/**
		 * Return true if the object is null, the object is an instance of NullObject
		 * or if the other object is a DB, then true if the other object is null.
		 */
		@Override
		public boolean equals(Object obj) {
			if (null == obj) {
				return true;
            }

			if (obj instanceof NullObject) {
				return true;
            }

			if (obj instanceof DB) {
				DB other = (DB) obj;
				return (null == other.getValue()) && other.isNull();
			}

			return false;
		}

		/**
		 * Returns null
		 */
		@Override
		public Object getValue() {
			return null;
		}

		/**
		 * returns 0
		 */
		@Override
		public int hashCode() {
			return 0;
		}

		/**
		 * returns the empty string
		 */
		@Override
		public String toString() {
			return "";
		}
	}

	/**
	 * DB Class for Integers
	 *
	 * @author jerry
	 */
	public static class Int extends DB {

		private Integer value = null;

		/**
		 * Create a NULL integer
		 */
		public Int() { }

		/**
		 * Create from an int
		 *
		 * @param value - the desired value
		 */
		public Int(int value) {
			setValue(value);
		}

		/**
		 * Supports creation from several types of object
		 * Object is | value set by calling
		 * String   |  Integer.valueOf(value)
		 * Number   |  value.intValue()
		 * <p/>
		 * Anything  else causes the value to be null
		 *
		 * @param value - Set value based on
		 */
		public Int(Object value) {
			if ((value != null) && !(value instanceof NullObject)) {
				if (value instanceof String) {
					String s = (String) value;

					if (!(s == null || s.length() == 0)) {
						setValue(Integer.valueOf(s));
					}
				} else if (value instanceof Number) {
					setValue(((Number) value).intValue());
				}
			}
		}

		/**
		 * If this instance is not null, return an Integer object otherwise return null
		 */
		@Override
		public Integer getValue() {
			return isNull() ? null : value;
		}

		/**
		 * Static method for comparing two Int values
		 *
		 * @param a - Int value
		 * @param b - Int value
		 * @return
		 */
		public static boolean compare(Int a, Int b) {
			return a.isNull() ? b.isNull() : b.isNull() && a.value == b.value;
		}

		/**
		 * Compares this instance to another Int or Integer object
		 */
		@Override
		public boolean equals(Object obj) {
			if ((null == obj) || (obj instanceof NullObject)) {
				return this.isNull();
			}
			if (obj instanceof Int) {
				Int other = (Int) obj;

                if (other.isNull()) {
					return this.isNull();
				}

                return !isNull() && other.value.equals(value);
			}

			if (obj instanceof Integer) {
				if (isNull()) {
					return false;
                } else {
					Integer other = (Integer) obj;
					return this.value.equals(other);
				}
			}
			return false;
		}

		/**
		 * Return the int value of this object
		 *
		 * @return int value of this object
		 * @throws NullPointerException if instance is null
		 */
		public int intValue() {
			if (isNull()) {
				throw new NullPointerException(NullConversionMessage);
            } else {
				return value.intValue();
            }
		}

		/**
		 * Return the long value of this object
		 *
		 * @return long value of this object
		 * @throws NullPointerException if instance is null
		 */
		public long longValue() {
			if (isNull()) {
				throw new NullPointerException(NullConversionMessage);
            } else {
				return value.longValue();
            }
		}

		/**
		 * Set the value of this instance.
		 *
		 * @param i
		 */
		private void setValue(int i) {
			value = i;
			setNotNull(true);
		}
	}

	/**
	 * DB type for Boolean values
	 *
	 * @author jerry
	 */
	public static class Bit extends DB {
		private Boolean value = null;

		/**
		 * Default Constructor
		 */
		public Bit() { }

		/**
		 * Construct from a Boolean value
		 *
		 * @param value - boolean used to set value
		 */
		public Bit(boolean value) {
			setValue(value);
		}

		/**
		 * Construct from an Object
		 * Object Type | Value set by
		 * Boolean  | value.booleanValue
		 * String   | 0 or false - value is false
		 * | 1 or true  - value is true
		 * | Boolean.parseBoolean(value)
		 * Anything else creates a NULL instance
		 *
		 * @param value - object used to set value
		 */
		public Bit(Object value) {
			if ((value != null) && !(value instanceof NullObject)) {
				if (value instanceof String) {
					String s = (String) value;

					if (s != null && s.length() != 0) {
						if (s == "0" || s.toLowerCase(Locale.getDefault()) == "false") {
							setValue(false);
						} else if (s == "1" || s.toLowerCase(Locale.getDefault()) == "true") {
							setValue(true);
						} else {
							setValue(Boolean.parseBoolean(s));
						}
					}
				} else if (value instanceof Boolean) {
					setValue(((Boolean) value).booleanValue());
				}
			}
		}

		/**
		 * Create from an integer.  Value is determined by (value != 0)
		 *
		 * @param value - integer used to set value
		 */
		public Bit(int value) {
			setValue(0 != value);
		}

		/**
		 * Returns the Boolean value or null if the instance is null
		 */
		public Boolean getValue() {
			return isNull() ? null : value;
		}

		/**
		 * Returns the value as a boolean
		 *
		 * @return - boolean value
		 * @throws NullPointerException if the instance is null
		 */
		public boolean booleanValue() {
			if (isNull()) {
				throw new NullPointerException(NullConversionMessage);
			} else {
				return value.booleanValue();
            }
		}

		/**
		 * Compare this instance to another DB.Bit or Boolean object
		 */
		@Override
		public boolean equals(Object obj) {
			if ((null == obj) || (obj instanceof NullObject)) {
				return this.isNull();
			}

			if (obj instanceof Bit) {
				Bit other = (Bit) obj;

				if (other.isNull()) {
					return this.isNull();
				}

				return !isNull() && other.value.equals(value);
			}

			if (obj instanceof Boolean) {
				if (isNull()) {
					return false;
                } else {
					Boolean other = (Boolean) obj;
					return this.value.equals(other);
				}
			}

			return false;
		}

		// internal use by constructors
		private void setValue(boolean value) {
			this.value = value;
			setNotNull(true);
		}
	}

	/**
	 * DB type for Strings
	 *
	 * @author jerry
	 */
	public static class Str extends DB {

		private String value = null;

		/**
		 * Default Constructor
		 */
		public Str() { }

		/**
		 * Set value based on an Object.  Only suppported type is String
		 *
		 * @param value - Object to use for setting value
		 */
		public Str(Object value) {
			if ((value != null) && !(value instanceof NullObject)) {
				setValue(value.toString().trim());

				if (this.value.length() == 0) {
					this.value = null;
					this.setNotNull(false);
				}
			}
		}

		private void setValue(String value) {
			this.value = value;
			this.setNotNull(true);
		}

		/**
		 * Returns the String, or NULL if the instance is null
		 */
		public String getValue() {
            return isNull() ? null : value;
		}

		/**
		 * Compare this instance to another Db.Str or String
		 */
		@Override
		public boolean equals(Object obj) {
			if ((null == obj) || (obj instanceof NullObject)) {
				return this.isNull();
			}

			if (obj instanceof Str) {
				Str other = (Str) obj;

                if (other.isNull()) {
					return this.isNull();
				}

                return !isNull() && other.value.equals(value);
			}

			if (obj instanceof String) {
				if (isNull()) {
					return false;
                } else {
					String other = (String) obj;
					return this.value.equals(other);
				}
			}

			return false;
		}
	}

	/**
	 * DB Type for Dates
	 *
	 * @author jerry
	 */
	public static class DateTime extends DB implements Comparable<DateTime> {

        public static final SimpleDateFormat DAY_N_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        private static final SimpleDateFormat TO_STRING_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		private Date value = null;

        public static DateTime now() {
			return new DateTime(new Date());
		}

		/**
		 * Default Construction (creates a null object)
		 */
		public DateTime() { }

		/**
		 * Create and set value from a java.util.Date instance
		 *
		 * @param value - value set from this object
		 */
		public DateTime(Date value) {
			setDate(value);
		}

		/**
		 * Create and set value from an Object
		 * If Object is | Value set by
		 * String   | Use a SimpleDateFormat object to parse string
		 * | Expected format is yyyy-MM-dd'T'HH:mm:ss in UTC
		 * Date     | Set the date (same as DateTime(Date value)
		 *
		 * @param value - instance used to set value
		 */
		public DateTime(Object value) {
			if ((value != null) && (!(value instanceof NullObject))) {
				if (value instanceof String) {
					String strValue = (String) value;

					if (!strValue.equals("")) {
						final SimpleDateFormat format;

						if (strValue.contains("T")) {
							format = DAY_N_TIME_FORMAT;
						} else {
							format = DAY_FORMAT;
						}

						try {
							setDate(format.parse(strValue));
						} catch (ParseException e) {
							throw new RuntimeException("Cannot Parse Value", e);
						}
					}
				} else if (value instanceof Date) {
					setDate((Date) value);
				} else if (value instanceof DateTime) {
					DateTime other = (DateTime) value;

					if (!other.isNull()) {
						setDate(other.getValue());
                    }
				} else {
					throw new RuntimeException(UnknownConversionMessage(value));
                }
			}
		}

		/**
		 * Get the Date object or null if this instance is null
		 */
		@Override
		public Date getValue() {
			return isNull() ? null : value;
		}

		/**
		 * Compare this instance to another instance of Db.DateTime or another Date object
		 */
		@Override
		public boolean equals(Object obj) {
			if ((obj == null) || (obj instanceof NullObject)) {
				return this.isNull();
			}

			if (obj instanceof DateTime) {
				DateTime other = (DateTime) obj;

                if (this.isNull()) {
					return other.isNull();
				}

                if (other.isNull()){
					return false;
				}

                return Math.abs(other.value.getTime() - value.getTime()) < 1000;
			}
			return (obj instanceof Date) && !isNull() && obj.equals(value);
		}

		/**
		 * return the long value of this date
		 *
		 * @return
		 * @see java.util.Date#getTime()
		 */
		public long toLong() {
			return isNull() ? 0 : value.getTime();
		}

		private void setDate(Date value) {
			this.value = value;
			setNotNull(value != null);
		}

		/**
		 * @return data in yyyy-MM-dd HH:mm:ss format
		 */
		@Override
		public String toString() {
			return getValue() != null ? TO_STRING_FORMAT.format(getValue()) : "";
		}

		/**
		 * Subtracts the specified date from this instance.
		 *
		 * @param date specified date
		 * @return number of days between specified date and date from this instance
		 */
		public Duration subtract(DateTime date) {
			if (isNull() || date.isNull()) {
				throw new IllegalStateException("Date has null value");
			}

			Calendar date1 = Calendar.getInstance();
			date1.setTime(getValue());

			Calendar date2 = Calendar.getInstance();
			date2.setTime(date.getValue());

			return new Duration(Math.abs(DayCounter.daysUntil(date2, date1)) + " Days");
		}

        /**
         * Gets the Date component of this instance. If isNull() is true, returns itself.
         * @return new object with same date but 0:00:00 time.
         */
        public DateTime getDate() {
            if (isNull()){
                return this;
            }
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(getValue());

            // remove time from date
            calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
            calendar.set(GregorianCalendar.MINUTE, 0);
            calendar.set(GregorianCalendar.SECOND, 0);
            calendar.set(GregorianCalendar.MILLISECOND, 0);

            return new DateTime(calendar.getTime());
        }

        public DateTime add(int field, int value) {
            if (isNull()){
                return this;
            }

            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(getValue());
            calendar.add(field, value);
            return new DateTime(calendar.getTime());
        }

        /**
         * Converts time in local timezone to UTC time.
         * @param date to be converted
         * @return new Date object representing the UTC time.
         */
        public static Date convertUtcToLocal(Date date) {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            final TimeZone local = TimeZone.getDefault();
            calendar.add(Calendar.MILLISECOND, local.getOffset(date.getTime()+ local.getRawOffset()));
            return calendar.getTime();
        }

        /**
         * Converts this DateTime object to UTC.
         * @return new object representing UTC time.
         */
        public DateTime convertUtcToLocal() {
            return new DateTime(convertUtcToLocal(getValue()));
        }

        /**
         * Converts time in UTC timezone to local time.
         * @param date to be converted
         * @return new Date object representing the local time.
         */
        public static Date convertLocalToUtc(Date date) {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            final TimeZone local = TimeZone.getDefault();
            calendar.add(Calendar.MILLISECOND, -local.getOffset(date.getTime()));
            return calendar.getTime();
        }

        /**
         * Converts this DateTime object to UTC.
         * @return new object representing UTC time.
         */
        public DateTime convertLocalToUtc() {
            return new DateTime(convertLocalToUtc(getValue()));
        }

        public int compareTo(DateTime another) {
            long thisVal = this.getValue().getTime();
            long anotherVal = another.getValue().getTime();
            return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
        }

        private static class DayCounter {
			public static int daysUntil(Calendar fromDate, Calendar toDate) {
				return daysSinceEpoch(toDate) - daysSinceEpoch(fromDate);
			}

			public static int daysSinceEpoch(Calendar day) {
				int year = day.get(Calendar.YEAR);
				int month = day.get(Calendar.MONTH);
				int daysThisYear = cumulDaysToMonth[month]
				                                    + day.get(Calendar.DAY_OF_MONTH) - 1;
				if ((month > 1) && isLeapYear(year)) {
					daysThisYear++;
				}

				return daysToYear(year) + daysThisYear;
			}

			public static boolean isLeapYear(int year) {
				return (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
			}

			static int daysToYear(int year) {
				return (365 * year) + numLeapsToYear(year);
			}

			static int numLeapsToYear(int year) {
				int num4y = (year - 1) / 4;
				int num100y = (year - 1) / 100;
				int num400y = (year - 1) / 400;
				return num4y - num100y + num400y;
			}

			private static final int[] cumulDaysToMonth = { 0, 31, 59, 90, 120,
				151, 181, 212, 243, 273, 304, 334 };
		}

	}

	/**
	 * DB type for Float
	 *
	 * @author jerry
	 */
	public static class Real extends DB {

		private Double _value = null;

		/**
		 * Create a null real
		 */
		public Real() {
		}

		/**
		 * Create from an float
		 *
		 * @param value - the desired value
		 */
		public Real(double value) {
			setValue(value);
		}

		/**
		 * Construct and set value based on an object
		 * If Object is | Value set by
		 * String    | Float.parseFloat(value)
		 * Number    | value.floatValue()
		 *
		 * @param value - instance used to set value
		 */
		public Real(Object value) {
			if ((value != null) && (!(value instanceof NullObject))) {
				if (value instanceof String) {
					String strValue = (String) value;
					if (0 != strValue.length()) {
//						setValue(0);
//} else {
						setValue(Float.parseFloat((String) value));
					}
				} else if (value instanceof Number) {
					setValue(((Number) value).floatValue());
				} else
					throw new RuntimeException(UnknownConversionMessage(value));
			}
		}

		/**
		 * Return the Double value of this object or null if this instance is null
		 */
		public Double getValue() {
            return isNull() ? null : _value;
		}

		/**
		 * Return the float as basic type
		 *
		 * @return
		 */
		public float getFloatValue() {
			if (isNull())
				throw new NullPointerException(NullConversionMessage);
			else
				return _value.floatValue();
		}

		/**
		 * Compare this instance to another DB.Real or another Float
		 */
		@Override
		public boolean equals(Object obj) {
			if ((obj == null) || (obj instanceof NullObject)) {
				return this.isNull();
			}
			if (obj instanceof Real) {
				Real other = (Real) obj;
				if (other.isNull()) {
					return this.isNull();
				}
				return !isNull() && other._value.equals(_value);
			}
			if (obj instanceof Double) {
				if (isNull())
					return false;
				return _value.equals(obj);
			}
			return false;
		}

		private void setValue(double value) {
			_value = new Double(value);
			setNotNull(true);
		}
	}

	/**
	 * DB type for Long
	 *
	 * @author jerry
	 */
	public static class BigInt extends DB {

		private Long _value = null;

		/**
		 * Create a null instance
		 */
		public BigInt() {
		}

		/**
		 * Create an instance and set value from another object
		 * If Object is | Value set based on
		 * String    | Long.parseLong(value)
		 * Number    | value.longValue()
		 *
		 * @param value - instance used to set value
		 */
		public BigInt(Object value) {
			if ((value != null) && !(value instanceof NullObject)) {
				if (value instanceof String) {
					String s = (String) value;
					if (s.length() != 0) {
						setValue(Long.parseLong(s));
					}
				} else if (value instanceof Number) {
					setValue(((Number) value).longValue());
				} else
					throw new RuntimeException(UnknownConversionMessage(value));
			}
		}

		/**
		 * return the Long value or null if the instance is null
		 */
		public Long getValue() {
            return isNull() ? null : _value;
		}

		/**
		 * Compare this instance to another DB.BigInt or another Long
		 */
		@Override
		public boolean equals(Object obj) {
			if( (obj == null) || (obj instanceof NullObject) ){
				return this.isNull();
			}
			if (obj instanceof BigInt) {
				BigInt other = (BigInt) obj;
				if (other.isNull()) {
					return this.isNull();
				}
				return !isNull() && other._value.equals(_value);
			}
			if (obj instanceof Long) {
				if(isNull())
					return false;
				return _value.equals((Long)obj);
			}

			return false;
		}

		private void setValue(long value) {
			_value = new Long(value);
			setNotNull(true);
		}
	}
}