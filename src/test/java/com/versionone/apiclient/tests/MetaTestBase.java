package com.versionone.apiclient.tests;

import com.versionone.DB;
import com.versionone.Oid;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IOperation;
import com.versionone.apiclient.MetaException;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.V1Exception;

public abstract class MetaTestBase {

	/**
	 * this is where all the test data files are located.  Other "TesterBase" use this.
	 */
	public static final String TEST_DATA = "testdata/TestData.xml";
	
	private static final String META_TOKEN = "meta.v1/";
	

	protected abstract String getMetaTestKeys();

	protected boolean preLoadMetaModel() {
		return false;
	}

	private IMetaModel _meta;

	protected IMetaModel getMeta() {
		if (_meta == null)
			_meta = new MetaModel(new ResponseConnector.XMLResponseConnector(TEST_DATA, META_TOKEN, getMetaTestKeys()), preLoadMetaModel());
		return _meta;
	}

	protected IAssetType getAssetType(String token) throws MetaException {
		return getMeta().getAssetType(token);
	}

	protected IAttributeDefinition getAttributeDefinition(String token) throws V1Exception {
		return getMeta().getAttributeDefinition(token);
	}

	protected IOperation getOperation(String token) throws MetaException {
		return getMeta().getOperation(token);
	}

	protected IAssetType getScopeType() throws MetaException {
		return getAssetType("Scope");
	}

	protected IAssetType getThemeType() throws MetaException {
		return getAssetType("Theme");
	}

	protected IAttributeDefinition getWorkitemScope() throws V1Exception {
		return getAttributeDefinition("Workitem.Scope");
	}

	protected IAttributeDefinition getWorkitemParent() throws V1Exception {
		return getAttributeDefinition("Workitem.Parent");
	}

	protected IAttributeDefinition getWorkitemToDo() throws V1Exception {
		return getAttributeDefinition("Workitem.ToDo");
	}

	protected Oid getScopeOid(int id) throws MetaException {
		return new Oid(getScopeType(), new DB.Int(id), new DB.Int());
	}

	protected Oid getThemeOid(int id) throws MetaException {
		return new Oid(getThemeType(), new DB.Int(id), new DB.Int());
	}
}
