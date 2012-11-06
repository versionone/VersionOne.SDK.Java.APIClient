/*(c) Copyright 2008, VersionOne, Inc. All rights reserved. (c)*/
package com.versionone.apiclient;

import com.versionone.DB;

/**
 * Rank class used to order Assets. Rank has a number and offset. In comparison
 * may act only Ranks with offset == 0. By methods {@link #before()},
 * {@link #after()} appropriate correlation Ranks can be created.
 */
public class Rank extends Number implements Comparable<Rank> {
    private final int _rankNumber;
    private final int _offset;

    private Rank(int rankNumber, int offset) {
        _rankNumber = rankNumber;
        _offset = offset;
    }

    /**
     * Creates the Rank equivalent to specified object.
     * Object can be instance of:
     * <ul>
     * <li> {@link com.versionone.apiclient.Rank} - the same Rank
     * <li> {@code null}, {@link com.versionone.DB.NullObject} -
     * the highest Rank
     * <li> {@link String} - must contain integer value with optional
     * end by + or - sign (ex. 5+, 3-).
     * Integer value used for Rank number, sign - for offset.
     * <li> {@link Number} - Rank with number equivalent to specified value
     * <li> otherwise - Rank equivalent to 0
     * </ul>
     *
     * @param o object to get Rank for
     */
    public Rank(Object o) {
        if (o instanceof Rank) {
            Rank other = (Rank) o;
            _rankNumber = other._rankNumber;
            _offset = other._offset;
        } else if (o == DB.Null || o == null) {
            _rankNumber = Integer.MAX_VALUE;
            _offset = 0;
        } else if (o instanceof String) {

            String s = (String) o;
            if (s.endsWith("+")) {
                _rankNumber = Integer.parseInt(s.substring(0, s.length() - 1));
                _offset = 1;
            } else if (s.endsWith("-")) {
                _rankNumber = Integer.parseInt(s.substring(0, s.length() - 1));
                _offset = -1;
            } else {
                _rankNumber = Integer.parseInt(s);
                _offset = 0;
            }
        } else if (o instanceof Number) {
            _rankNumber = ((Number) o).intValue();
            _offset = 0;
        } else {
            _rankNumber = 0;
            _offset = 0;
        }
    }

    /**
     * Creates Rank with equivalent number and lower offset.
     *
     * @return created Rank
     */
    public Rank before() {
        return new Rank(_rankNumber, _offset - 1);
    }

    /**
     * Creates Rank with equivalent number and higher offset.
     *
     * @return created Rank
     */
    public Rank after() {
        return new Rank(_rankNumber, _offset + 1);
    }

    /**
     * Determines if Rank has negative offset
     *
     * @return true if offset is negative; false otherwise
     */
    public boolean isBefore() {
        return _offset < 0;
    }

    /**
     * Determines if Rank has positive offset
     *
     * @return true if offset is positive; false otherwise
     */
    public boolean isAfter() {
        return _offset > 0;
    }

    /**
     * Returns a string representation of this Rank. This string consist of
     * integer value and optional + or - sign character. Integer value got
     * from Rank number and appropriate sign from offset.
     * If <code>(offset == 0)</code> sign character not added.
     *
     * @return a string representation of this Rank
     */
    @Override
    public String toString() {
        return _rankNumber + (isAfter() ? "+" : isBefore() ? "-" : "");
    }

    /**
     * @return a value of Rank number as {@code int}.
     */
    public int intValue() {
        return _rankNumber;
    }

    /**
     * @return a value of Rank number as {@code long}.
     */
    public long longValue() {
        return _rankNumber;
    }

    /**
     * @return a value of Rank number as {@code float}.
     */
    public float floatValue() {
        return _rankNumber;
    }

    /**
     * @return a value of Rank number as {@code double}.
     */
    public double doubleValue() {
        return _rankNumber;
    }

    /**
     * Compares this object with the specified object. The result is
     * <code>true</code> if and only if the argument is not
     * <code>null</code> and the <code>Rank</code> object that has
     * the same Rank number.
     * <p/>
     * Note: Rank objects with offset which isn't 0 cannot be compared.
     *
     * (<code>{@link #isAfter()} == true</code> or
     * <code>{@link #isBefore()} == true)</code>
     *
     * @param obj object to compare with.
     * @return <code>true</code> if objects equals, <code>false</code> -
     *         otherwise.
     *
     * @throws IllegalArgumentException if <code>obj</code> or <code>this</code> has offset
     *                                  not equal 0.
     */
    @Override
    public boolean equals(Object obj) throws IllegalArgumentException {
        try {
            return (obj instanceof Rank) && compareTo((Rank) obj) == 0;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns a hash code for this <code>Rank</code>.
     *
     * @return a hash code value for this Rank, equals to its number.
     */
    @Override
    public int hashCode() {
        return _rankNumber;
    }

    /**
     * Compares this Rank with the specified Rank.
     * <p/>
     * Note: Rank objects with not 0 offset cannot be compared.
     * (<code>{@link #isAfter()} == true</code> or
     * <code>{@link #isBefore()} == true)</code>
     *
     * @param other Rank to compare with.
     * @return the value <code>0</code> if this <code>Rank</code> is
     *         equal to the argument <code>Rank</code>; a value less than
     *         <code>0</code> if this <code>Rank</code> number is numerically
     *         less than the argument <code>Rank</code> number; and the value
     *         greater than <code>0</code> if this <code>Rank</code> number is
     *         numerically greater than the argument <code>Rank</code> number
     *         (signed comparison).
     * @throws IllegalArgumentException if <code>obj</code> or <code>this</code> has offset
     *                                  not equal 0.
     */
    public int compareTo(Rank other) throws IllegalArgumentException {
        assert other != null;
        if (isAfter() || isBefore() || other.isBefore() || other.isAfter()) {
            throw new IllegalArgumentException("Transient ranks are not comparable");
        }
        return _rankNumber - other._rankNumber;
    }
}