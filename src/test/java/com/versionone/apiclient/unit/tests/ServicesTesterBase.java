package com.versionone.apiclient.unit.tests;

import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IMetaModel;

public abstract class ServicesTesterBase {
	
	protected abstract String getServicesTestKeys();	
	protected boolean preload() {return false;}
	
	private IMetaModel _meta;
	
	protected IMetaModel getMeta() {
		if (_meta == null)
			_meta = new MetaModel(new ResponseConnector.XMLResponseConnector(MetaTestBase.TEST_DATA,"meta.v1/",getServicesTestKeys()), preload());
		return _meta;
	}
	
	private ResponseConnector.XMLResponseConnector _dataConnector;
	ResponseConnector getDataConnector() {
			if(_dataConnector == null)
				_dataConnector = new ResponseConnector.XMLResponseConnector(MetaTestBase.TEST_DATA, "rest-1.v1/", getServicesTestKeys());
			return _dataConnector;
	}

	protected IAssetType getAssetType(String token) throws MetaException
	{
		return getMeta().getAssetType(token);
	}
}
