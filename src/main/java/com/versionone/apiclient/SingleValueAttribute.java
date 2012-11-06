package com.versionone.apiclient;

/**
 * A the value for a scalar attribute
 *
 * @author jerry
 *
 */
class SingleValueAttribute extends Attribute
{
    /**
     * Holds original value (incl. null). Also holds null if value isn't been read.
     */
	private Object _value;
    /**
     * Holds new (changed) value (incl. null). Also holds null if value isn't been modified.
     */
	private Object _newValue;
    /**
     * True if {@link #_newValue} holds new value. False if this attribute isn't been modified.
     * This field was introduced to differ these states.  
     */
	private boolean _hasChanged;

    /**
	 * Create from definition and asset
	 * @param def - attribute definition
	 * @param asset - asset
	 */
	SingleValueAttribute(IAttributeDefinition def, Asset asset)
	{
		super(def, asset);
	}

	/**
	 * Return the value before any modificaions
	 */
	@Override
	public Object getOriginalValue(){return _value;}

	/**
	 * Return the modified value or null if it's not been modified
	 */
	@Override
	public Object getNewValue(){
        return hasChanged() ? _newValue : null;
    }

    /**
	 * Return an array of 1 containing the value before any modification
     * or null if it's not been set.
	 */
	@Override
	public Object[] getOriginalValues()	{
        return wrap(getOriginalValue());
	}

    /**
	 * Return an array of 1 containing the modified value
     * or null if it's not been set.
	 */
    @Override
	public Object[] getNewValues() {
        return wrap(getNewValue());
    }

    private static Object[] wrap(final Object value) {
        if (value == null)
            return null;
        return new Object[] {value};
    }

	@Override
	public Object[] getAddedValues(){ return getNewValues(); }

	@Override
	public Object[] getRemovedValues() {
			if(hasChanged())
				return getOriginalValues();
			return null;
	}

	@Override
	public boolean hasChanged(){ return _hasChanged; }

	@Override
	void setValue(Object value) throws APIException
	{
		checkReadOnly();
		try {
			value = getDefinition().coerce(value);
		} catch (V1Exception e) {
			throw new APIException("Error converting data", e);
		}
		checkNull(value);
		_newValue = value;
        _hasChanged = true;
	}

	@Override
	void forceValue(Object value) throws APIException
	{
		try {
			value = getDefinition().coerce(value);
		} catch (V1Exception e) {
			throw new APIException("Error converting data", e);
		}
		checkNull(value);
		_newValue = value;
        _hasChanged = true;
	}

	@Override
	void addValue(Object value)
	{
		throw new RuntimeException("Cannot assign multiple values to a single-value attribute: " + getDefinition().getToken());
	}

	@Override
	void removeValue(Object value)
	{
		throw new RuntimeException("Cannot remove values from a single-value attribute: " + getDefinition().getToken());
	}

	@Override
	public void acceptChanges()
	{
		if (hasChanged())
		{
			_value = _newValue;
			_newValue = null;
            _hasChanged = false;
		}
	}

	@Override
	public void rejectChanges()
	{
		_newValue = null;
        _hasChanged = false;
	}

	@Override
	void loadValue(Object value) throws APIException
	{
		if (_value != null)
			throw new RuntimeException("Cannot load multiple values into a single-value attribute: " + getDefinition().getToken());
		try {
			_value = getDefinition().coerce(value);
		} catch (V1Exception e) {
			throw new APIException("Error converting data", e);
		}
	}

	@Override
	public String toString() {
		StringBuffer rc = new StringBuffer(128);
		rc.append("{value=").append(_value);
        if (hasChanged())
		    rc.append(", newValue=").append(_newValue);
		rc.append("}");
		return rc.toString();
	}
}
