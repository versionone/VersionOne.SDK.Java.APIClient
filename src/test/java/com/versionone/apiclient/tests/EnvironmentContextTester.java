package com.versionone.apiclient.tests;

import com.versionone.apiclient.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class EnvironmentContextTester {

    private EnvironmentContext _defaultTarget;
    private EnvironmentContext _nonDefaultTarget;

    @Before
    public void Setup() throws IOException {

        _defaultTarget = new EnvironmentContext();

        IUrls urls = new IUrls() {
            @Override
            public String getV1Url() {
                return "http://google.com/";
            }

            @Override
            public String getMetaUrl() {
                return "/blah1.1/";
            }

            @Override
            public String getDataUrl() {
                return "/jimmy2.2/";
            }
        };

        ICredentials credentials = new ICredentials() {
            @Override
            public String getV1UserName() {
                return "NA";
            }

            @Override
            public String getV1Password() {
                return "NA";
            }
        };

        //can implement your own IConnector if needed.
        IConnectors connectors = new Connectors(urls, credentials);
        IModelsAndServices modelsAndServices = new ModelsAndServices(connectors);

        _nonDefaultTarget = new EnvironmentContext(modelsAndServices);

    }

    //retrieve meta model using environmentContext
    @Test
    public void GetMetaModelTest(){
        IMetaModel model = _defaultTarget.getMetaModel();
        Assert.assertNotNull(model);
        model = _nonDefaultTarget.getMetaModel();
        Assert.assertNotNull(model);
    }

    //retrieve services using environmentContext
    @Test
    public void GetServicesTest(){
        IServices services = _defaultTarget.getServices();
        Assert.assertNotNull(services);
        services = _nonDefaultTarget.getServices();
        Assert.assertNotNull(services);
    }

}
