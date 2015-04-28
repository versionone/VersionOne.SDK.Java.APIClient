package com.versionone.apiclient.interfaces;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.OidException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.services.QueryResult;

/**
 * Methods implemented by a class providing VersionOne services.
 */
public interface IServices {

	/**
	 * Get type information for an asset specified in a token
	 *  
	 * @param token - token to process
	 * @return IAssetType based on token
	 * @throws MetaException if the token is invalid
	 */
	IAssetType getAssetType(String token) throws MetaException;

	/**
	 * Get type information for an attribute specified in a token
	 * 
	 * @param token - token to process
	 * @return IAttributeDefinition based on token
	 * @throws MetaException if the token is invalid
	 */
	IAttributeDefinition getAttributeDefinition(String token) throws MetaException;

	/**
	 * Get type information for an operation specified in a token
	 * 
	 * @param token - token to process
	 * @return IOperation based on token
	 * @throws MetaException if the token is invalid
	 */
	IOperation getOperation(String token) throws MetaException;

	/**
	 * Get a Object Identifier for the specified token
	 * 
	 * @param token - token to process
	 * @return Object Identifier
	 * @throws OidException if the token is invalid
	 */
	Oid getOid(String token) throws OidException;

	/**
	 * Execute a Query and return the results
	 * 
	 * @param query - Query to execute
	 * @return QueryResult of the query
     * @throws ConnectionException - if connection to VersionOne down
     * @throws OidException - if there is problem related to Object Identifiers
     * @throws APIException if there is a problem executing the query
	 */
	QueryResult retrieve(Query query) throws ConnectionException, APIException, OidException;

	/**
	 * Persist changes to an asset without comment
	 * 
	 * @param asset - asset that changed
     * @throws ConnectionException - if connection to VersionOne down
     * @throws APIException - if the save fails
	 */
	void save(Asset asset) throws APIException, ConnectionException;

	/**
	 * Persist changes to as asset with comment
	 * 
	 * @param asset - asset that changed
	 * @param comment - comment
     * @throws ConnectionException - if connection to VersionOne down
     * @throws APIException - if the save fails
	 */
	void save(Asset asset, String comment) throws APIException, ConnectionException;

	/**
	 * Save multiple assets without comment
	 * 
	 * @param assetList - array of assets to save
	 * @throws V1Exception - if the save fails
	 */
	void save(Asset[] assetList) throws V1Exception;

	/**
	 * Determine the OID of the currently logged in user
	 * 
	 * @return Object Identifier for current user
     * @throws ConnectionException - if connection to VersionOne down
     * @throws OidException - if there is problem related to Object Identifiers 
     * @throws APIException - if there is any other error
	 */
	Oid getLoggedIn() throws APIException, ConnectionException, OidException;

	/**
	 * Create a new Asset 
	 * 
	 * @param assetType - type of asset to create
	 * @param context - context under which to create the asset
	 * @return New Asset
	 * @throws V1Exception - when the create fails
	 */
	Asset createNew(IAssetType assetType, Oid context) throws V1Exception;

	/**
	 * Execute an operation
	 * 
	 * @param op - Operation to execute
	 * @param oid - OID of object on which method is executed
	 * @return Oid result of operation
	 * @throws APIException - when the operation fails
	 */
	Oid executeOperation(IOperation op, Oid oid) throws APIException;
	
	/**
	 * Get Meta Information
	 * 
	 * @return IMetaModel
	 */
	IMetaModel getMeta();
	
	/**
	 * Get V1connector 
	 * 
	 * @return V1Connector
	 */
	
	V1Connector getV1Connector();
	
	/**
	 * 
	 * @param query the query string
	 * @return String
	 */
    String executePassThroughQuery(String query);

}
