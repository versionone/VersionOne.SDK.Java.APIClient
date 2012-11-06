package com.versionone.apiclient;

/**
 * Methods required by objects providing VersionOne MetaModel data
 *  
 * @author jerry
 *
 */
public interface IMetaModel {

	/**
	 * Get type information on the asset specified in the token
	 * @param token - token of desired asset  
	 * @return IAssetType for asset defined in token
	 * @throws MetaException if an error occurs while locating the asset
	 */
	IAssetType getAssetType(String token) throws MetaException;

	/**
	 * Get the definition of an attribute specified in the token
	 * @param token - token of desired attribute
	 * @return IAttributeDefinition for attribute specified
	 * @throws MetaException if an error occurs finding the attribute
	 */
	IAttributeDefinition getAttributeDefinition(String token) throws MetaException;

	/**
	 * Get information on an operation specified in the token
	 * @param token - token of desired operation 
	 * @return IOperation based on token
	 * @throws MetaException if as error occurs finding the operation information
	 */
	IOperation getOperation(String token) throws MetaException;
}