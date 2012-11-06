package com.versionone.apiclient;


class Pair extends Tuple {
	private Object _first;
	private Object _second;

	public Object getFirst() {return _first == Null? null: _first;}
	public Object getSecond() {return _second == Null? null: _second;}

	public Pair(Object first, Object second) {
		_first = first == null? Null: first;
		_second = second == null? Null: second;
	}

	public static boolean compare(Pair p1, Pair p2)
	{
		if(null != p1)
			return p1.equals(p2);
		return null == p2;
	}
		
	@Override
	public boolean equals(Object obj) {
		boolean rc = false;
		if (null != obj && obj instanceof Pair) {
			Pair other = (Pair) obj;
			rc = this._first.equals(other._first) && this._second.equals(other._second);
		}
		return rc;
	}

	@Override
	public Object get(int i) {
		switch (i)
		{
			case 0:
				return getFirst();
			case 1:
				return getSecond();
			default:
				throw new java.lang.IndexOutOfBoundsException();
		}
	}

	@Override
	public int hashCode() {
		return HashCode.Hash(_first, _second);
	}

	@Override
	public Object[] toArray() {
		return new Object[] {getFirst(), getSecond()};
	}
	
	@Override
	public int getSize() {
		return 2;
	}
}