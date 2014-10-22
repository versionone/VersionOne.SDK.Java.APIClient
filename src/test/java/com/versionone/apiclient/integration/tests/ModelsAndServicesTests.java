package com.versionone.apiclient.integration.tests;

import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.versionone.apiclient.Connectors;
import com.versionone.apiclient.IConnectors;
import com.versionone.apiclient.IModelsAndServices;
import com.versionone.apiclient.ModelsAndServices;

public class ModelsAndServicesTests {

    private IModelsAndServices _defaultTarget;
    private IModelsAndServices _nonDefaultTarget;

    @Before
    public void Setup() throws Exception {
        _defaultTarget = new ModelsAndServices();
        IConnectors connectors = new Connectors();
        _nonDefaultTarget = new ModelsAndServices(connectors);
    }

    @Test
    public void GetServicesTest(){
        Assert.assertNotNull(_defaultTarget.getServices());
        Assert.assertNotNull(_nonDefaultTarget.getServices());
    }

    @Test
    public void GetServicesWithProxyTest() throws URISyntaxException {
        Assert.assertNotNull(_defaultTarget.getServicesWithProxy());
        Assert.assertNotNull(_nonDefaultTarget.getServicesWithProxy());
    }

    @Test
    public void GetMetaModelTest(){
        Assert.assertNotNull(_defaultTarget.getMetaModel());
        Assert.assertNotNull(_nonDefaultTarget.getMetaModel());
    }

    @Test
    public void GetMetaModelWithProxyTest() throws URISyntaxException {
        Assert.assertNotNull(_defaultTarget.getMetaModelWithProxy());
        Assert.assertNotNull(_nonDefaultTarget.getMetaModelWithProxy());
    }

    @Test
    public void GetV1ConfigurationTest() {
        Assert.assertNotNull(_defaultTarget.getV1Configuration());
        Assert.assertNotNull(_nonDefaultTarget.getV1Configuration());
    }



}
