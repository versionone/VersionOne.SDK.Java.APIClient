package com.versionone.apiclient;

/**
 * Represents the API Version
 * @author jerry
 *
 */
public class Version implements Comparable<Version>{

	private int _major = 1;
	private int _minor = 0;
	private int _build = 0;
	private int _revision = 0;

	public int getMajor() {return _major;}
	public int getMinor() {return _minor;}
	public int getBuild() {return _build;}
	public int getRevision() {return _revision;}

	/**
	 * Create 1.0.0.0 version
	 */
	public Version() {
	}

	/**
	 * Parse a string to create version
	 * Expected format {major}.{minor}.{build}.{revision}
	 * @param value - string to parse
	 */
	public Version(String value) {
		if(value != null && value.length() > 0) {
			String[] tokens = value.split("\\.");
			_major    = (1 <= tokens.length) ? Integer.parseInt(tokens[0]) : 0;
			_minor    = (2 <= tokens.length) ? Integer.parseInt(tokens[1]) : 0;
			_build    = (3 <= tokens.length) ? Integer.parseInt(tokens[2]) : 0;
			_revision = (4 <= tokens.length) ? Integer.parseInt(tokens[3]) : 0;
		}
	}

	/**
	 * Determine if two versions are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Version) {
			Version o = (Version)obj;
			return _major == o._major &&
			       _minor == o._minor &&
			       _build == o._build &&
			       _revision == o._revision;
		}
		return false;
	}

	/**
	 * Convert to string
	 */
	@Override
	public String toString() {
		return _major + "." + _minor + "." + _build + "." + _revision;
	}
	
    public int compareTo(Version other) {
		if(_major != other._major){
			return _major - other._major;
		}
		if(_minor != other._minor){
			return _minor - other._minor;
		}
		if(_build != other._build){
			return _build - other._build;
		}
		if(_revision != other._revision){
			return _revision - other._revision;
		}
		return 0;
	}
}
