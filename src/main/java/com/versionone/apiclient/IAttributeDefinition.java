package com.versionone.apiclient;

/**
 * Defines methods for the definition of a VersionOne Attribute
 * @author jerry
 *
 */
public interface IAttributeDefinition {

	/**
	 * Supported Attribute types
	 * @author jerry
	 *
	 */
	public enum AttributeType {
		Boolean,
		Numeric,
		Date,
		Duration,
		Text,
		LongText,
		LocalizerTag,
		Password,
		AssetType,
		Relation,
		Opaque,
		State,
		Rank,
		Blob,
		LongInt,
	}

	/**
	 * Supported Aggregate types
	 */
	public enum Aggregate
	{
		Sum,
		Min,
		Max,
		Count,
		And,
		Or
	}

	/**
	 * Attempt to convert the value into this type
	 * @param value - value to coerce
	 * @return same value in this type
	 * @throws V1Exception - if the value cannot be converted
	 */
	Object coerce(Object value) throws V1Exception;

	/**
	 * Get asset type information to this attribute
	 * @return IAssetType for this attribute
	 * @throws MetaException
	 */
	IAssetType getAssetType() throws MetaException;

	/**
	 * Get attribute name
	 * @return string containing name
	 */
	String getName();

	/**
	 * get attribute display name
	 * @return
	 */
	String getDisplayName();

	/**
	 * Get attribute token
	 * @return String containing token
	 */
	String getToken();

	/**
	 * Get type of attribute
	 * @return AttributeType for this instance
	 */
	AttributeType getAttributeType();

	/**
	 * Get base attribute information
	 * @return IAttributeDefinition for base
	 * @throws MetaException if there is no base
	 */
	IAttributeDefinition getBase() throws MetaException;

	/**
	 * Is this attribute read-only
	 * @return true if read-only, false otherwise
	 */
	boolean isReadOnly();

	/**
	 * Is this a required attribute
	 * @return true if attribute is required, false otherwise
	 */
	boolean isRequired();

	/**
	 * Does this attribute support multiple values
	 * @return true if attribute supports multiple-values, false otherwise
	 */
	boolean isMultiValue();

	/**
	 * Get type information for related asset if this attribute is a relation
	 * @return IAssetType for related asset
	 * @throws MetaException - if we cannot get the Asset Type information
	 */
	IAssetType getRelatedAsset() throws MetaException;


	/**
	 * Downcast current object
	 *
	 * @param assetType IAssetType for asset defined in token
	 * @return Downcasted object
	 */
	IAttributeDefinition downcast(IAssetType assetType);

	/**
	 * Add filter to object
	 *
	 * @param filter filter for adding
	 * @return object with filter
         * @throws APIException if filter has incorrect statement
	 */
	IAttributeDefinition filter(IFilterTerm filter) throws APIException;

	/**
	 * Join current token with new
	 *
	 * @param joined Object with token for joining
	 * @return new object with joined tokens
	 */
	IAttributeDefinition join(IAttributeDefinition joined);

	/**
	 * Aggregate objects
	 *
	 * @param aggregate rule for Aggregating
	 * @return agregated object
	 */
	IAttributeDefinition aggregate(Aggregate aggregate);
}