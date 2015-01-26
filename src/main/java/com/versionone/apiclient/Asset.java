package com.versionone.apiclient;

import com.versionone.Oid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents one Asset in the VersionOne system.
 * Assets are Projects, Stories, Tasks, etc
 */
public class Asset {

	private Oid oid = Oid.Null;
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();
	private Map<String, Asset> newAssets = new HashMap<String, Asset>();
	private IAssetType assetType;
	private List<Asset> children = new ArrayList<Asset>();

	/**
	 * Get type information
	 * 
	 * @return IAssetType
	 */
	public IAssetType getAssetType(){
        return assetType;
    }

	/**
	 * Get Asset Object Identifier
	 * 
	 * @return  Oid
	 */
	public Oid getOid() {
        return oid;
    }

	/**
	 * This method is used by Service after a new asset has been created.
	 * the result of create returns the oid
	 *
	 * @param value - Oid
	 * @throws OidException - when the new oid is a different type of asset
	 */
	public void setOid(Oid value) throws OidException {
		if(value.getAssetType() != assetType) {
			throw new OidException("Cannot change this asset's AssetType", value.getToken());
        }
		oid = value;
	}

	/**
	 * Get asset attributes
	 * 
	 * @return  Map data
	 */
	public Map<String, Attribute> getAttributes(){
        return attributes;
    }

	/**
	 * Used to add new relationships and get new assets
	 * 
	 * @return  Map
	 */
	public Map<String, Asset> getNewAssets() {
        return newAssets;
    }

	/**
	 * Get child assets
	 * 
	 * @return List
	 */
	public List<Asset> getChildren() {
        return children;
    }

	/**
	 * Create from Object Id
	 * 
	 * @param oid - Oid data
	 */
	public Asset(Oid oid) {
		if (oid.isNull()) {
			throw new java.lang.IllegalArgumentException("Cannot initialize Asset with NULL Oid");
        }
		this.oid = oid;
		assetType = oid.getAssetType();
	}

	/**
	 * Create based on type
	 *
	 * @param assetType - IAssetType
	 */
	public Asset(IAssetType assetType) {
		this.assetType = assetType;
	}

	/**
	 * Set an attribute value
	 * 
	 * @param attributeDefinition attribute definition
	 * @param value attribute value
	 * @throws APIException - APIException throws
	 */
	public void setAttributeValue(IAttributeDefinition attributeDefinition, Object value) throws APIException {
		ensureAttribute(attributeDefinition).setValue(value);
	}

	/**
	 * Force an attribute to a value
	 *
	 * @param attributeDefinition attribute definition
	 * @param value value
	 * @throws Exception
	 */
	void forceAttributeValue(IAttributeDefinition attributeDefinition, Object value) throws APIException {
		ensureAttribute(attributeDefinition).forceValue(value);
	}

	/**
	 * Add an attribute value
	 *
	 * @param attributeDefinition - IAttributeDefinition
	 * @param value - Object
	 * @throws APIException -  exception throws
	 */
	public void addAttributeValue(IAttributeDefinition attributeDefinition, Object value) throws APIException {
		ensureAttribute(attributeDefinition).addValue(value);
	}

	/**
	 * Remove an attribute value
	 * 
	 * @param attributeDefinition - IAttributeDefinition
	 * @param value - Object
	 * @throws APIException - APIException throws
	 */
	public void removeAttributeValue(IAttributeDefinition attributeDefinition, Object value) throws APIException {
		ensureAttribute(attributeDefinition).removeValue(value);
	}

    /**
     * Clear an attribute from cache based on definition.
     *
     * @param attributeDefinition definition of attribute to clear; if null, all attributes will be cleared from cache.
     */
    public void clearAttributeCache(IAttributeDefinition attributeDefinition) {
        if (attributeDefinition == null) {
            attributes.clear();
        } else {
            attributes.remove(resolveAttributeDefinition(attributeDefinition).getToken());
        }
	}

	/**
	 * Get an attribute based on definition
	 * 
	 * @param attributeDefinition - IAttributeDefinition
	 * @throws MetaException - MetaException
	 * @return Attribute - Attribute
	 */
	public Attribute getAttribute(IAttributeDefinition attributeDefinition) throws MetaException {
		return attributes.get(resolveAttributeDefinition(attributeDefinition).getToken());
	}

	private IAttributeDefinition resolveAttributeDefinition(IAttributeDefinition attributeDefinition) throws MetaException	{
		if (getAssetType().isA(attributeDefinition.getAssetType())) {
			return getAssetType().getAttributeDefinition(attributeDefinition.getName());
        }
		return attributeDefinition;
	}

	/**
	 * Accept changes to asset
	 */
	public void acceptChanges() {
		for(Attribute attribute : attributes.values()) {
			attribute.acceptChanges();
        }
	}

	/**
	 * Reject changes to this asset
	 */
	public void rejectChanges() {
		for(Attribute attribute : attributes.values()) {
			attribute.rejectChanges();
        }
	}

	/**
	 * Determine if something changed
	 * 
	 * @return boolean - return boolean value
	 */
	public boolean hasChanged() {
		for(Attribute attribute : attributes.values()) {
			if(attribute.hasChanged()) {
				return true;
            }
        }
		return false;
	}

	public void loadAttributeValue(IAttributeDefinition attributeDefinition, Object value) throws APIException 	{
		ensureAttribute(attributeDefinition).loadValue(value);
	}

	public Attribute ensureAttribute (IAttributeDefinition attributeDefinition) throws APIException	{
		try {
            attributeDefinition = resolveAttributeDefinition(attributeDefinition);
		} catch (MetaException e) {
			throw new APIException("Cannot resolve Attribute", attributeDefinition.getToken(), e);
		}
		Attribute attribute = attributes.get(attributeDefinition.getToken());

		if (attribute == null) {
			attribute = attributeDefinition.isMultiValue() ? new MultiValueAttribute(attributeDefinition, this) : new SingleValueAttribute(attributeDefinition, this);
			attributes.put(attributeDefinition.getToken(), attribute);
		}
		return attribute;
	}
}
