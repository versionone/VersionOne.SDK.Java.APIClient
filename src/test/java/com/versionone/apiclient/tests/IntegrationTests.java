package com.versionone.apiclient.tests;

import com.versionone.Oid;
import com.versionone.apiclient.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class IntegrationTests {

    private String _v1Url;
    private String _username;
    private String _password;
    private String _dataUrl;
    private String _metaUrl;
    private String _configUrl;

    private V1APIConnector _dataConnector;
    private V1APIConnector _metaConnector;
    private IMetaModel _metaModel;
    private IServices _services;

    @Before
    public void Setup(){

        EnvironmentContext environment = new EnvironmentContext();
        EnvironmentContext.ModelsAndServices modelsAndServices = environment.new ModelsAndServices();

        _metaModel = modelsAndServices.getMetaModel();
        _services = modelsAndServices.getServices();

        /*_v1Url = "https://www14.v1host.com/v1sdktesting/";
        _username = "admin";
        _password = "admin";

        _dataUrl = _v1Url + "rest-1.v1/";
        _metaUrl = _v1Url + "meta.v1/";
        _configUrl = _v1Url + "config.v1/";

        V1APIConnector dataConnector = getDataConnector();
        V1APIConnector metaConnector = getMetaConnector();
        _metaModel = new MetaModel(metaConnector);
        _services = new Services(_metaModel, dataConnector);*/
    }

    private V1APIConnector getDataConnector() {
        return new V1APIConnector(_dataUrl, _username, _password);
    }

    private V1APIConnector getMetaConnector() {
        return new V1APIConnector(_metaUrl, _username, _password);
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
