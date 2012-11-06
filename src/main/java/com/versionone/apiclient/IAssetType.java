package com.versionone.apiclient;

/**
 * Interface defined by VersionOne AssetType
 * @author jerry
 *
 */
public interface IAssetType {

	/**
	 * Determine if this Asset Type is the same as or derived from another type
	 * @param targetType - type to check against
	 * @return true if this type is the same as or derived from targetType
	 * @throws MetaException if we cannot retrieve meta information for targetType
	 */
	boolean isA(IAssetType targetType) throws MetaException;

	/**
	 * Get the definition of the specified attribute
	 * @param name of attribute
	 * @return IAttributeDefinition for name
	 * @throws MetaException if the Attribute is invalid
	 */
	IAttributeDefinition getAttributeDefinition(String name) throws MetaException;

	/**
	 * Return the token for this Asset Type
	 * @return AssetType token
	 */
	String getToken();

	/**
	 * Get the base asset for this type
	 * @return IAssetType of base
	 * @throws MetaException - if base cannot be determined
	 */
	IAssetType getBase() throws MetaException;

	/**
	 * Get the display name for this asset type
	 * @return Display Name
	 */
	String getDisplayName();

	/**
	 * Get attribute on which this asset is sorted by default.
	 * @return IAttributeDefinition for default sort attribute
	 * @throws MetaException if we cannot obtain this information.
	 */
	IAttributeDefinition getDefaultOrderBy() throws MetaException;

	/**
	 * @param name name of operation
	 * @return operation
	 * @throws MetaException if an error occurs finding the attribute
	 */
	IOperation getOperation(String name) throws MetaException;

	/**
	 * @return short name attribute
	 * @throws MetaException if an error occurs finding the attribute
	 */
	IAttributeDefinition getShortNameAttribute() throws MetaException;

	/**
	 * @return attribute name
	 * @throws MetaException if an error occurs finding the attribute
	 */
	IAttributeDefinition getNameAttribute() throws MetaException;

	/**
	 * @return description attribute
	 * @throws MetaException if an error occurs finding the attribute
	 */
	IAttributeDefinition getDescriptionAttribute() throws MetaException;
}