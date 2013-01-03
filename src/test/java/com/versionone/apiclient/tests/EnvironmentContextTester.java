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
    public void Setup() throws Exception {

        _defaultTarget = new EnvironmentContext();

        IUrls urls = new IUrls() {
            public String getV1Url() {
                return "http://google.com/";
            }

            public String getMetaUrl() {
                return "/blah1.1/";
            }

            public String getDataUrl() {
                return "/jimmy2.2/";
            }

            public String getProxyUrl() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getConfigUrl() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        ICredentials credentials = new ICredentials() {
            public String getV1UserName() {
                return "NA";
            }

            public String getV1Password() {
                return "NA";
            }

            public String getProxyUserName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getProxyPassword() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
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
