package com.versionone.javasdk.legacy.integration.tests;

import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.versionone.apiclient.Connectors;
import com.versionone.apiclient.ModelsAndServices;
import com.versionone.apiclient.interfaces.IConnectors;
import com.versionone.apiclient.interfaces.IModelsAndServices;

public class ModelsAndServicesTests {

    private IModelsAndServices _defaultTarget;
    private IModelsAndServices _nonDefaultTarget;

    @Before
    public void setup() throws Exception {
        _defaultTarget = new ModelsAndServices();
        IConnectors connectors = new Connectors();
        _nonDefaultTarget = new ModelsAndServices(connectors);
    }

    @Test
    public void testGetServices(){
        Assert.assertNotNull(_defaultTarget.getServices());
        Assert.assertNotNull(_nonDefaultTarget.getServices());
    }

    @Test
    public void testGetServicesWithProxy() throws URISyntaxException {
        Assert.assertNotNull(_defaultTarget.getServicesWithProxy());
        Assert.assertNotNull(_nonDefaultTarget.getServicesWithProxy());
    }

    @Test
    public void testGetMetaModel(){
        Assert.assertNotNull(_defaultTarget.getMetaModel());
        Assert.assertNotNull(_nonDefaultTarget.getMetaModel());
    }

    @Test
    public void testGetMetaModelWithProxy() throws URISyntaxException {
        Assert.assertNotNull(_defaultTarget.getMetaModelWithProxy());
        Assert.assertNotNull(_nonDefaultTarget.getMetaModelWithProxy());
    }

    @Test
    public void testGetV1Configuration() {
        Assert.assertNotNull(_defaultTarget.getV1Configuration());
        Assert.assertNotNull(_nonDefaultTarget.getV1Configuration());
    }



}
