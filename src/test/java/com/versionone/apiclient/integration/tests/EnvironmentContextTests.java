package com.versionone.apiclient.integration.tests;

import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.versionone.apiclient.Connectors;
import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.IConnectors;
import com.versionone.apiclient.ICredentials;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IModelsAndServices;
import com.versionone.apiclient.IServices;
import com.versionone.apiclient.IUrls;
import com.versionone.apiclient.ModelsAndServices;
import com.versionone.apiclient.V1Configuration;

public class EnvironmentContextTests {

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
                return "http://wwww.myproxy.com/";
            }

            public String getConfigUrl() {
                return "http://www14.v1.com/config1.1/";
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
                return "fred";
            }

            public String getProxyPassword() {
                return "Wx7123456Wx7";
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

    @Test
    public void GetMetaModelWithProxyTest() throws URISyntaxException {
        IMetaModel model = _defaultTarget.getMetaModelWithProxy();
        Assert.assertNotNull(model);
        model = _nonDefaultTarget.getMetaModelWithProxy();
        Assert.assertNotNull(model);
    }

    @Test
    public void GetServicesWithProxyTest() throws URISyntaxException {
        IServices services = _defaultTarget.getServicesWithProxy();
        Assert.assertNotNull(services);
        services = _nonDefaultTarget.getServicesWithProxy();
        Assert.assertNotNull(services);
    }

    @Test
    public void GetV1ConfigurationTest() {
        V1Configuration config = _defaultTarget.getV1Configuration();
        Assert.assertNotNull(config);
        config = _nonDefaultTarget.getV1Configuration();
        Assert.assertNotNull(config);
    }

    @Test
    public void GetV1ConfigurationWithProxyTest() throws URISyntaxException {
        V1Configuration config = _defaultTarget.getV1ConfigurationWithProxy();
        Assert.assertNotNull(config);
        config = _nonDefaultTarget.getV1ConfigurationWithProxy();
        Assert.assertNotNull(config);
    }

    @Test(expected = IllegalArgumentException.class)
    public void NullModelsAndServicesTest(){
        EnvironmentContext context = new EnvironmentContext(null);
    }

}
