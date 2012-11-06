package com.versionone.apiclient;

class Monad extends Tuple {
	private Object value;

	public Object getValue() {
		return (value == Null) ? null : value;
	}

	public Monad(Object value) {
		this.value = value == null ? Null : value;
	}

	public static boolean compare(Monad m1, Monad m2) {
		if (null != m1)
			return m1.equals(m2);
		return null == m2;
	}

	@Override
	public boolean equals(Object obj) {
		boolean rc = false;

		if (null != obj && obj instanceof Monad) {
			Monad other = (Monad) obj;
			rc = this.value.equals(other.value);
		}

        return rc;
	}

	@Override
	public Object get(int i) {
		if (i != 0) {
			throw new java.lang.IndexOutOfBoundsException();
        }

		return getValue();
	}

	@Override
	public int hashCode() {
		return HashCode.Hash(getValue());
	}

	@Override
	public Object[] toArray() {
		return new Object[] { getValue() };
	}

	@Override
	public int getSize() {
		return 1;
	}
}