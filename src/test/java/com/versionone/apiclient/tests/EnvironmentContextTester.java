package com.versionone.apiclient.tests;

import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IModelsAndServices;
import com.versionone.apiclient.IServices;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnvironmentContextTester {

    private EnvironmentContext _environment;

    @Before
    public void Setup(){
        _environment = new EnvironmentContext();
    }

    @Test
    public void GetMetaModelTest(){
        IModelsAndServices modelsAndServices = _environment.new ModelsAndServices();
        IMetaModel model = modelsAndServices.getMetaModel();
        Assert.assertNotNull(model);
    }

    public void GetServicesTest(){
        IModelsAndServices modelsAndServices = _environment.new ModelsAndServices();
        IServices services = modelsAndServices.getServices();
        Assert.assertNotNull(services);
    }

}
