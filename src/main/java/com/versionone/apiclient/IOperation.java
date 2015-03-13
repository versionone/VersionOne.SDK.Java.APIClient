package com.versionone.apiclient;

import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;

/**
 * Methods required by VersionOne Operation objects 
 */
public interface IOperation {

	/**
	 * Get the token for this operation
	 * 
	 * @return operation token
	 */
	String getToken();

	/**
	 * Get the operation name
	 * 
	 * @return name of operation
	 */
	String getName();

	/**
	 * Get the type of asset for this operation 
	 * 
	 * @return IAssetType for this operation
	 * @throws MetaException - if the asset does not support this operation
	 */
	IAssetType getAssetType() throws MetaException;

	/**
	 * Get the definition for the attribute used to validate this operation
	 * 
	 * @return IAttributeDefinition of attribute used to validate this operation
	 * @throws MetaException if there's a problem getting the information
	 */
	IAttributeDefinition getValidatorAttribute() throws MetaException;
}