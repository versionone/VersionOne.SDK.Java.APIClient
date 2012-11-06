package com.versionone.apiclient;


class Quadruple extends Tuple {

	private Object _first;
	private Object _second;
	private Object _third;
	private Object _fourth;

	public Object getFirst()  {return _first == Null? null: _first;}
	public Object getSecond() {return _second == Null? null: _second;}
	public Object getThird()  {return _third == Null? null: _third;}
	public Object getFourth() {return _fourth == Null? null: _fourth;}
	
	public Quadruple(Object first, Object second, Object third, Object fourth) {
		_first = first == null? Null: first;
		_second = second == null? Null: second;
		_third = third == null? Null: third;
		_fourth = fourth == null? Null: fourth;
	}

	public static boolean compare(Quadruple q1, Quadruple q2)
	{
		if(null != q1)
			return q1.equals(q2);
		return null == q2;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean rc = false;
		if(null != obj && obj instanceof Quadruple) {
			Quadruple other = (Quadruple) obj;
			rc = this._first.equals(other._first) && this._second.equals(other._second) && this._third.equals(other._third) && this._fourth.equals(other._fourth);			
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
			case 3:
				return getFourth();
			default:
				throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public int hashCode() {
		return HashCode.Hash(this.toArray());
	}

	@Override
	public Object[] toArray() {
		return new Object[] {getFirst(), getSecond(), getThird(), getFourth()};
	}

}