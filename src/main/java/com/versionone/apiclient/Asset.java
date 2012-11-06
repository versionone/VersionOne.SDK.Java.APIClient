package com.versionone.apiclient;

import com.versionone.Oid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents one Asset in the VersionOne system.
 * Assets are Projects, Stories, Tasks, etc
 *
 * @author jerry
 *
 */
public class Asset {

	private Oid oid = Oid.Null;
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();
	private Map<String, Asset> newAssets = new HashMap<String, Asset>();
	private IAssetType assetType;
	private List<Asset> children = new ArrayList<Asset>();

	/**
	 * Get type information
	 */
	public IAssetType getAssetType(){
        return assetType;
    }

	/**
	 * Get Asset Object Identifier
	 */
	public Oid getOid() {
        return oid;
    }

	/**
	 * This method is used by Service after a new asset has been created.
	 * the result of create returns the oid
	 *
	 * @param value
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
	 */
	public Map<String, Attribute> getAttributes(){
        return attributes;
    }

	/**
	 * Used to add new relationships
	 * get new assets
	 */
	public Map<String, Asset> getNewAssets() {
        return newAssets;
    }

	/**
	 * Get child assets
	 */
	public List<Asset> getChildren() {
        return children;
    }

	/**
	 * Create from Object Id
	 * @param oid
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
	 * @param assetType
	 */
	public Asset(IAssetType assetType) {
		this.assetType = assetType;
	}

	/**
	 * set an attribute value
	 * @param attributeDefinition attribute definition
	 * @param value attribute value
	 */
	public void setAttributeValue(IAttributeDefinition attributeDefinition, Object value) throws APIException {
		ensureAttribute(attributeDefinition).setValue(value);
	}

	/**
	 * force an attribute to a value
	 *
	 * @param attributeDefinition attribute definition
	 * @param value value
	 * @throws Exception
	 */
	void forceAttributeValue(IAttributeDefinition attributeDefinition, Object value) throws APIException {
		ensureAttribute(attributeDefinition).forceValue(value);
	}

	/**
	 * add an attribute value
	 *
	 * @param attributeDefinition
	 * @param value
	 * @throws Exception
	 */
	public void addAttributeValue(IAttributeDefinition attributeDefinition, Object value) throws APIException {
		ensureAttribute(attributeDefinition).addValue(value);
	}

	/**
	 * remove an attribute value
	 * @param attributeDefinition
	 * @param value
	 * @throws Exception
	 */
	public void removeAttributeValue(IAttributeDefinition attributeDefinition, Object value) throws APIException {
		ensureAttribute(attributeDefinition).removeValue(value);
	}

    /**
     * Clear an attribute from cache based on definition.
     *
     * @param attributeDefinition definition of attribute to clear;
     *                  if null, all attributes will be cleared from cache.
     */
    public void clearAttributeCache(IAttributeDefinition attributeDefinition) {
        if (attributeDefinition == null) {
            attributes.clear();
        } else {
            attributes.remove(resolveAttributeDefinition(attributeDefinition).getToken());
        }
	}

	/**
	 * get an attribute based on definition
	 * @param attributeDefinition
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
	 * accept changes to asset
	 */
	public void acceptChanges() {
		for(Attribute attribute : attributes.values()) {
			attribute.acceptChanges();
        }
	}

	/**
	 * reject changes to this asset
	 */
	public void rejectChanges() {
		for(Attribute attribute : attributes.values()) {
			attribute.rejectChanges();
        }
	}

	/**
	 * determine if something changed
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
