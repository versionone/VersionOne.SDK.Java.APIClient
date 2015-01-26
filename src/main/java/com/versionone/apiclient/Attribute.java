package com.versionone.apiclient;

import com.versionone.DB;
import com.versionone.Oid;
import com.versionone.apiclient.IAttributeDefinition.AttributeType;

/**
 * Base class for an attribute
 */
public abstract class Attribute {

	private IAttributeDefinition _def;
	private Asset _asset;

	protected Attribute(IAttributeDefinition def, Asset asset) {
		_def = def;
		_asset = asset;
	}

	/**
	 * Get the Asset that owns the attribute
	 * 
	 * @return Asset
	 */
	public Asset getAsset() {
		return _asset;
	}

	/**
	 * Get the definition of the attribute
	 * 
	 * @return IAttributeDefinition
	 */
	public IAttributeDefinition getDefinition() {
		return _def;
	}

	/**
	 * Get the original value of the attribute
	 * 
	 * @return Object - Object
	 * @throws APIException - APIException
	 */
	public abstract Object getOriginalValue() throws APIException;

	/**
	 * Get the new value of the attribute
	 * 
	 * @return Object
	 * @throws APIException - APIException
	 */
	public abstract Object getNewValue() throws APIException;

	/**
	 * Get the current value of the attribute
	 * 
	 * @return Object
	 * @throws APIException - APIException
	 */
	public Object getValue() throws APIException {
		if (hasChanged())
			return getNewValue();
		return getOriginalValue();
	}

	/**
	 * Get the original values
	 * 
	 * @return array of Object
	 */
	public abstract Object[] getOriginalValues();

	/**
	 * Get the new Valued
	 * 
	 * @return array of Object
	 */
	public abstract Object[] getNewValues();

	/**
	 * Get values that were added
	 * 
	 * @return array of Object
	 */
	public abstract Object[] getAddedValues();

	/**
	 * Get the values that were removed
	 * 
	 * @return array of Object
	 */
	public abstract Object[] getRemovedValues();

	/**
	 * Get the current values of the attribute
	 * 
	 * @return array of Object
	 */
	public Object[] getValues() {
		if (hasChanged())
			return getNewValues();
		return getOriginalValues();

	}

	/**
	 * Has this attribute changed
	 * 
	 * @return boolean
	 */
	public abstract boolean hasChanged();

	/**
	 * Accept the changes to the attribute
	 */
	public abstract void acceptChanges();

	/**
	 * Reject the changes to this attribute
	 */
	public abstract void rejectChanges();

	protected void checkReadOnly() {
		if (_def.isReadOnly())
			throw new RuntimeException(
					"Cannot assign new value to a read-only attribute: "
							+ getDefinition().getToken());
	}

	protected void checkNull(Object value) {
		if (_def.isRequired()
				|| (_def.isMultiValue() && _def.getAttributeType() == AttributeType.Relation))
			if (value == DB.Null
					|| (value instanceof Oid && ((Oid) value).isNull()))
				throw new RuntimeException("Value required: "
						+ getDefinition().getToken());
	}

	abstract void loadValue(Object value) throws APIException;

	abstract void setValue(Object value) throws APIException;

	abstract void forceValue(Object value) throws APIException;

	abstract void addValue(Object value) throws APIException;

	abstract void removeValue(Object value) throws APIException;
}
