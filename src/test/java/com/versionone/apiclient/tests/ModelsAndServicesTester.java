package com.versionone.apiclient.tests;

import com.versionone.apiclient.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelsAndServicesTester {

    private IModelsAndServices _defaultTarget;
    private IModelsAndServices _nonDefaultTarget;

    @Before
    public void Setup(){
        _defaultTarget = new ModelsAndServices();
        IConnectors connectors = new Connectors();
        _nonDefaultTarget = new ModelsAndServices(connectors);
    }

    @Test
    public void GetServicesTest(){
        Assert.assertNotNull(_defaultTarget.getServices());
    }

    @Test
    public void GetMetaModelTest(){
        Assert.assertNotNull(_defaultTarget.getMetaModel());
    }

}
