package com.versionone.apiclient.integration.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IServices;
import com.versionone.apiclient.OidException;
import com.versionone.apiclient.V1Exception;

//TODO:
	//		Add scalar
	//		Update scalar (attribute)
	//		Remove scalar
	//		Add single-relation
	//		Update single-relation
	//		Remove single-relations
	//		Add multi-relation
	//		Update multi-relation
	//		Remove multi-relation
	//		Create new asset
	//		Delete an asset
	//		Close an asset
	//		Reopen an asset
	//		Error: Invalid asset
	//		Error: Asset doesn't exists

public class AssetTests {

    private IMetaModel _metaModel;
    private IServices _services;

    @Before
    public void setup() throws Exception {
        EnvironmentContext environment = new EnvironmentContext();
        _metaModel = environment.getMetaModel();
        _services = environment.getServices();
    }

    @Test(expected = OidException.class)
    public void testSetInvalidOidOnAsset() throws V1Exception{
        Oid projectId = Oid.fromToken("Scope:0", _metaModel);
        IAssetType assetType = _metaModel.getAssetType("Story");
        Asset newStory = _services.createNew(assetType, projectId);
        newStory.setOid(Oid.fromToken("", _metaModel));
    }

    @Test
    public void testSetValidOidOnAsset() throws V1Exception {
        Oid projectId = Oid.fromToken("Scope:0", _metaModel);
        IAssetType assetType = _metaModel.getAssetType("Story");
        Asset newStory = _services.createNew(assetType, projectId);
        newStory.setOid(Oid.fromToken("Story:999999", _metaModel));
        Assert.assertNotNull(newStory.getOid());

    }
}
