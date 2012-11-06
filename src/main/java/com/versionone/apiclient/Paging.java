package com.versionone.apiclient;

/**
 * Provides support for paging parameter in query.
 * 
 * @author jerry
 *
 */
public class Paging
{
	private int _pagesize;
	public int getPageSize()  {return _pagesize;} 
	public void setPageSize(int value) { _pagesize = value;}
	
	private int _start;
	public int getStart() { return _start;}
	public void setStart(int value) { _start = value;}

	/**
	 * Create with default paging - 0 to Max Integer
	 */
	public Paging() {
		this(0, Integer.MAX_VALUE);
	}

	/**
	 * Create with a specific start element and page size
	 * 
	 * @param start
	 * @param pagesize
	 */
	public Paging(int start, int pagesize)
	{
		_start = start;
		_pagesize = pagesize;
	}
	
	/**
	 * Get the paging token
	 * @return
	 */
	public String getToken() { return String.format("%d,%d", getPageSize(), getStart()); }
	
	/**
	 * Return object as a string (the token)
	 */
	@Override
	public String toString()
	{
		return getToken();
	}
}