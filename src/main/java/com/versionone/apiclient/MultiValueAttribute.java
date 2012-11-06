package com.versionone.apiclient;

import java.util.ArrayList;

import com.versionone.DB;

/**
 * Represents an Attribute that can contain multiple value
 * @author jerry
 *
 */
@SuppressWarnings("unchecked")
class MultiValueAttribute extends Attribute
{
	private ArrayList _values = new ArrayList();
	private ArrayList _addedvalues = new ArrayList();
	private ArrayList _removedvalues = new ArrayList();
	private ArrayList _newvalues;

	MultiValueAttribute(IAttributeDefinition def, Asset asset)
	{
		super(def, asset);
	}

	@Override
	public Object getOriginalValue() throws APIException {
		if (_values == null)
			return null;
		if (_values.size() == 0) {
			try {
				return getDefinition().coerce(DB.Null);
			} catch (V1Exception e) {
				throw new APIException("Error converting data", e);
			}
		}
		if (_values.size() == 1)
			return _values.get(0);
		throw new RuntimeException("Attribute contains multiple values: " + getDefinition().getToken());
	}

	@Override
	public Object getNewValue() throws APIException {
		if (_newvalues == null)
			return null;
		if (_newvalues.size() == 0) {
			try {
				return getDefinition().coerce(DB.Null);
			} catch (V1Exception e) {
				throw new APIException("Error converting data", e);
			}
		}
		if (_newvalues.size() == 1)
			return _newvalues.get(0);
		throw new RuntimeException("Attribute contains multiple values: " + getDefinition().getToken());
	}

	@Override
	public Object[] getOriginalValues() {return _values == null ? null : _values.toArray();}

	@Override
	public Object[] getNewValues() {return _newvalues == null ? null : _newvalues.toArray();}

	@Override
	public Object[] getAddedValues() {return _addedvalues == null ? null : _addedvalues.toArray();}

	@Override
	public Object[] getRemovedValues() {return _removedvalues == null ? null : _removedvalues.toArray();}

	@Override
	public boolean hasChanged()
	{
		return ( (_newvalues != null) && (_newvalues.size() > 0) ) ||
		       ( (_removedvalues != null) && (_removedvalues.size() > 0));
	}


	@Override
	void setValue(Object value)
	{
		throw new RuntimeException("Cannot set value on a multi-value attribute: " + getDefinition().getToken());
	}

	@Override
	void forceValue(Object value)
	{
		throw new RuntimeException("Cannot force value on a multi-value attribute: " + getDefinition().getToken());
	}

	@Override
	void addValue(Object value) throws APIException
	{
		checkReadOnly();
		try {
			value = getDefinition().coerce(value);
		} catch (V1Exception e) {
			throw new APIException("Error converting data", e);
		}
		checkNull(value);

		if (_newvalues == null)
			if (_values != null)
				_newvalues = new ArrayList(_values);
			else
				_newvalues = new ArrayList();
		_newvalues.add(value);

		if (_addedvalues == null)
			_addedvalues = new ArrayList();
		_addedvalues.add(value);
	}

	@Override
	void removeValue(Object value) throws APIException
	{
		checkReadOnly();
		try {
			value = getDefinition().coerce(value);
		} catch (V1Exception e) {
			throw new APIException("Error Converting Data", e);
		}

		if (_newvalues == null)
			if (_values != null)
				_newvalues = new ArrayList(_values);
			else
				_newvalues = new ArrayList();
		_newvalues.remove(value);

		if (_removedvalues == null)
			_removedvalues = new ArrayList();

		_removedvalues.add(value);
	}

	@Override
	public void acceptChanges()
	{
		if(hasChanged())
		{
			_values = _newvalues;
			_newvalues = _addedvalues = _removedvalues = null;
		}
	}

	@Override
	public void rejectChanges()
	{
		_newvalues = _addedvalues = _removedvalues = null;
	}

	@Override
	void loadValue(Object value) throws APIException
	{
		try {
			_values.add(getDefinition().coerce(value));
		} catch (V1Exception e) {
			throw new APIException("Error converting data", e);
		}
	}
}
