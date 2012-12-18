package com.versionone.apiclient.tests;

import com.versionone.Oid;
import com.versionone.apiclient.*;
import com.versionone.apiclient.EnvironmentContext;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AssetTester {

    private IMetaModel _metaModel;
    private IServices _services;

    @Before
    public void Setup(){
        EnvironmentContext environment = new EnvironmentContext();
        IModelsAndServices modelsAndServices = environment.new ModelsAndServices();
        _metaModel = modelsAndServices.getMetaModel();
        _services = modelsAndServices.getServices();
    }

    @Test(expected = OidException.class)
    public void SetInvalidOidOnAssetTest() throws V1Exception{
        Oid projectId = Oid.fromToken("Scope:0", _metaModel);
        IAssetType assetType = _metaModel.getAssetType("Story");
        Asset newStory = _services.createNew(assetType, projectId);
        newStory.setOid(Oid.fromToken("", _metaModel));
    }

    @Test
    public void SetValidOidOnAssetTest() throws V1Exception {
        Oid projectId = Oid.fromToken("Scope:0", _metaModel);
        IAssetType assetType = _metaModel.getAssetType("Story");
        Asset newStory = _services.createNew(assetType, projectId);
        newStory.setOid(Oid.fromToken("Story:999999", _metaModel));
        Assert.assertNotNull(newStory.getOid());

    }
}
