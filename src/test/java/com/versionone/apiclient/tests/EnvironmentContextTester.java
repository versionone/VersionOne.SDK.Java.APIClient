package com.versionone.apiclient.tests;

import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IServices;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnvironmentContextTester {

    private EnvironmentContext _defaultTarget;
    private EnvironmentContext _nonDefaultTarget;

    @Before
    public void Setup(){
        _defaultTarget = new EnvironmentContext();

    }

    @Test
    public void GetMetaModelTest(){
        IMetaModel model = _defaultTarget.getMetaModel();
        Assert.assertNotNull(model);
    }

    @Test
    public void GetServicesTest(){
        IServices services = _defaultTarget.getServices();
        Assert.assertNotNull(services);
    }

}
