package com.versionone.apiclient.unit.tests;

import com.versionone.apiclient.IOperation;
import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;

/**
 * AssetType used for testing
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