package com.versionone.apiclient.tests;

import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IOperation;
import com.versionone.apiclient.MetaException;

/**
 * AssetType used for testing
 * @author jerry
 */
class MockAssetType implements IAssetType {

	private String _name = "Mock";

	public MockAssetType() {}
	public MockAssetType(String name) {_name = name;}
	public IAttributeDefinition getAttributeDefinition(String name)
			throws MetaException {
		// TODO Auto-generated method stub
		return null;
	}

	public IAssetType getBase() throws MetaException {
		// TODO Auto-generated method stub
		return null;
	}

	public IAttributeDefinition getDefaultOrderBy() throws MetaException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToken() {
		return _name;
	}

	public boolean isA(IAssetType targettype) throws MetaException {
		// TODO Auto-generated method stub
		return false;
	}

	public IAttributeDefinition getShortNameAttribute(){
		return null;
	}
	public IAttributeDefinition getNameAttribute(){
		return null;
	}
	public IAttributeDefinition getDescriptionAttribute(){
		return null;
	}
	public IOperation getOperation(String name) {
		return null;
	}
}