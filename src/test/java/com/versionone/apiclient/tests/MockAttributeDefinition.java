package com.versionone.apiclient.tests;

import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IFilterTerm;
import com.versionone.apiclient.MetaException;

/**
 * AttributeType used for testing
 */
class MockAttributeDefinition implements IAttributeDefinition {
	String name;
	AttributeType type = AttributeType.Text;

	public MockAttributeDefinition(String name, AttributeType type) {
		this.name = name;
		this.type = type;
	}
	
	public MockAttributeDefinition(String name) {
        this(name, AttributeType.Opaque);
	}

	public Object coerce(Object value) throws MetaException {
		return value;
	}

	public IAssetType getAssetType() throws MetaException {
		return null;
	}

	public AttributeType getAttributeType() {
		return type;
	}

	public IAttributeDefinition getBase() throws MetaException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	public IAssetType getRelatedAsset() throws MetaException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToken() {
		return "Mock."+ name;
	}

	public boolean isMultiValue() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	public IAttributeDefinition aggregate(
			com.versionone.apiclient.IAttributeDefinition.Aggregate aggregate) {
		// TODO Auto-generated method stub
		return null;
	}

	public IAttributeDefinition downcast(IAssetType assetType) {
		// TODO Auto-generated method stub
		return null;
	}

	public IAttributeDefinition filter(IFilterTerm filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public IAttributeDefinition join(IAttributeDefinition joined) {
		// TODO Auto-generated method stub
		return null;
	}
}