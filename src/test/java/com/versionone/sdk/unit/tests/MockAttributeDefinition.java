package com.versionone.sdk.unit.tests;

import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.filters.IFilterTerm;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;

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
		return null;
	}

	public String getDisplayName() {
		return null;
	}

	public String getName() {
		return name;
	}

	public IAssetType getRelatedAsset() throws MetaException {
		return null;
	}

	public String getToken() {
		return "Mock."+ name;
	}

	public boolean isMultiValue() {
		return false;
	}

	public boolean isReadOnly() {
		return false;
	}

	public boolean isRequired() {
		return false;
	}

	public IAttributeDefinition aggregate(
			com.versionone.apiclient.interfaces.IAttributeDefinition.Aggregate aggregate) {
		return null;
	}

	public IAttributeDefinition downcast(IAssetType assetType) {
		return null;
	}

	public IAttributeDefinition filter(IFilterTerm filter) {
		return null;
	}

	public IAttributeDefinition join(IAttributeDefinition joined) {
		return null;
	}
}