package com.versionone.apiclient.tests;
import com.versionone.Oid;
import com.versionone.apiclient.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class IntegrationTests {

    private V1APIConnector _dataConnector;
    private V1APIConnector _metaConnector;
    private IMetaModel _metaModel;
    private IServices _services;

    @Before
    public void Setup(){
        _dataConnector = EnvironmentContext.Connectors.getDataConnector();
        _metaConnector = EnvironmentContext.Connectors.getMetaConnector();
        _metaModel = EnvironmentContext.Models.getMetaModel();
        _services = EnvironmentContext.Services.getServices();
    }

    @Test @Ignore("WIP")
    public void AddNewAssetWithNonExistentScopeContext() throws Exception {
        Oid projectId = Oid.fromToken("Scope:999999999", _metaModel);
        IAssetType assetType = _metaModel.getAssetType("Story");
        Asset newStory = _services.createNew(assetType, projectId);
        IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
        newStory.setAttributeValue(nameAttribute, "My New Story");
        _services.save(newStory);

        Assert.assertTrue(newStory.getOid().getToken().length() != 0);
        Assert.assertTrue(newStory.getAttribute(assetType.getAttributeDefinition("Scope")).getValue() != null);
        Assert.assertTrue(newStory.getAttribute(nameAttribute).getValue() != null);
    }

    @Test
    public void AddNewAssetWithExistingScopeContext() throws Exception {
        Oid projectId = Oid.fromToken("Scope:0", _metaModel);
        IAssetType assetType = _metaModel.getAssetType("Story");
        Asset newStory = _services.createNew(assetType, projectId);
        IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
        newStory.setAttributeValue(nameAttribute, "My New Story");
        _services.save(newStory);

        Assert.assertTrue(newStory.getOid().getToken().length() != 0);
        Assert.assertTrue(newStory.getAttribute(assetType.getAttributeDefinition("Scope")).getValue() != null);
        Assert.assertTrue(newStory.getAttribute(nameAttribute).getValue() != null);
    }

}
