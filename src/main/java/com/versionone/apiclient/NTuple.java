package com.versionone.apiclient;



class NTuple extends Tuple {
	private Object[] _elements;
	
	public NTuple(Object[] elements, Object add) {
		_elements = new Object[elements.length + 1];
		for (int i = 0; i < elements.length; ++i)
			_elements[i] = (elements[i] == null)? Null: elements[i];
		_elements[elements.length] = (add == null)? Null: add;		
	}

	public NTuple(Object[] elements)
	{
		_elements = elements;
		for (int i = 0; i < _elements.length; ++i)
			if (_elements[i] == null)
				_elements[i] = Null;
	}
	
	NTuple shrink() {
		int newLength = _elements.length - 1;
		Object[] elements = new Object[newLength];
		for(int i = 0; i < newLength; ++i){
			elements[i] = _elements[i];
		}
		return new NTuple(elements);		
	}


	public static boolean compareTo(NTuple t1, NTuple t2)
	{
		if(null == t1)
			return null == t2;
		if(null == t2)
			return false;
		if(t1._elements.length != t2._elements.length)
			return false;
		for(int i = 0; i < t1._elements.length; ++i)
			if (! t1._elements[i].equals( t2._elements[i] ))
				return false;
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean rc = false;
		if(null != obj && obj instanceof NTuple) {
			rc = compareTo(this, (NTuple)obj);
		}
		return rc;
	}


	@Override
	public Object get(int i) {
		return (_elements[i] == Null)? null: _elements[i];
	}

	@Override
	public int getSize() {
		return _elements.length;
	}


	@Override
	public int hashCode() {
		return HashCode.Hash(_elements);
	}


	@Override
	public Object[] toArray() {
		Object [] elements = _elements.clone();
		for (int i = 0; i < elements.length; ++i)
			if (elements[i] == Null)
				elements[i] = null;
		return elements;
	}

}