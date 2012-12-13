package com.versionone.apiclient.tests;

import com.versionone.Oid;
import com.versionone.apiclient.*;
import junit.framework.Assert;
import org.junit.Test;

public class AssetTester {

    private IMetaModel _metaModel;
    private IServices _services;

    @Test
    public void SetValidOidOnAssetTest() throws V1Exception {

        _metaModel = EnvironmentContext.Models.getMetaModel();
        _services = EnvironmentContext.Services.getServices();

        Oid projectId = Oid.fromToken("Scope:0", _metaModel);
        IAssetType assetType = _metaModel.getAssetType("Story");
        Asset newStory = _services.createNew(assetType, projectId);
        newStory.setOid(Oid.fromToken("Story:999999", _metaModel));
        Assert.assertNotNull(newStory.getOid());

    }
}
