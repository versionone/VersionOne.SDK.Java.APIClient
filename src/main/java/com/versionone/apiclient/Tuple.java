package com.versionone.apiclient;

/**
 * Base class
 * @author jerry
 *
 */
abstract class Tuple {
	
	protected static final Object Null = new Object();

	public abstract int getSize();
	public abstract Object get(int i);
	public abstract Object[] toArray();

	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object obj);

	public static Tuple FromArray(Object[] array, int startIndex, int length) {		
		switch (length)
		{
			case 1:
				return new Monad(array[startIndex]);
			case 2:
				return new Pair(array[startIndex],array[startIndex+1]);
			case 3:
				return new Triple(array[startIndex],array[startIndex+1],array[startIndex+2]);
			case 4:
				return new Quadruple(array[startIndex], array[startIndex+1], array[startIndex+2], array[startIndex+3]);
			default: {
				Object[] elements = new Object[length];
				for(int i = 0; i < length; ++i) {
					elements[i] = array[i+startIndex];
				}
				return new NTuple(elements);				
			}
		}
	}

	public static Tuple create(Object o1, Object o2) {
		if ( o1 instanceof Tuple) {
			return grow((Tuple)o1, o2);
		}
		return new Pair(o1,o2);
	}

	public static Tuple grow(Tuple tuple, Object x) {
		switch (tuple.getSize()) {
			case 1:
				return new Pair(tuple.get(0), x);
			case 2:
				return new Triple(tuple.get(0), tuple.get(1), x);
			case 3:
				return new Quadruple(tuple.get(0), tuple.get(1), tuple.get(2), x);
			default:
				return new NTuple(tuple.toArray(), x);
		}
	}

	public static Tuple shrink(Tuple tuple) {
		switch(tuple.getSize()) {
			case 1:
				return null;
			case 2:
				return new Monad(tuple.get(0));
			case 3:
				return new Pair(tuple.get(0), tuple.get(1));
			case 4:
				return new Triple(tuple.get(0), tuple.get(1), tuple.get(2));
			default:
				return ((NTuple)tuple).shrink();
		}
	}
}