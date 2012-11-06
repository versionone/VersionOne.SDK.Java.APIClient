package com.versionone.apiclient;

/**
 * Methods required by VersionOne Operation objects 
 * @author jerry
 *
 */
public interface IOperation {

	/**
	 * Get the token for this operation
	 * @return operation token
	 */
	String getToken();

	/**
	 * Get the operation name
	 * @return name of operation
	 */
	String getName();

	/**
	 * Get the type of asset for this operation 
	 * @return IAssetType for this operation
	 * @throws MetaException - if the asset does not support this operation
	 */
	IAssetType getAssetType() throws MetaException;

	/**
	 * Get the definition for the attribute used to validate this operation
	 * @return IAttributeDefinition of attribute used to validate this operation
	 * @throws V1Exception if there's a problem getting the information
	 */
	IAttributeDefinition getValidatorAttribute() throws MetaException;
}