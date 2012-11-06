package com.versionone.apiclient;


class Triple extends Tuple {

	private Object _first;
	private Object _second;
	private Object _third;

	public Object getFirst()  {return _first == Null? null: _first;}
	public Object getSecond() {return _second == Null? null: _second;}
	public Object getThird()  {return _third == Null? null: _third;}
	
	public Triple(Object first, Object second, Object third) {
		_first = first == null? Null: first;
		_second = second == null? Null: second;
		_third = third == null? Null: third;
	}

	public static boolean compare(Triple t1, Triple t2)
	{
		if(null != t1)
			return t1.equals(t2);
		return t2 == null;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		boolean rc = false;
		if(null != obj && obj instanceof Triple)
		{
			Triple other = (Triple) obj;
			rc = this._first.equals(other._first) && this._second.equals(other._second) && this._third.equals(other._third);
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
			case 2:
				return getThird();
			default:
				throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public int getSize() {
		return 3;
	}

	@Override
	public int hashCode() {
		return HashCode.Hash(_first, _second, _third);
	}

	@Override
	public Object[] toArray() {
		return new Object[]{getFirst(), getSecond(), getThird()};
	}

}