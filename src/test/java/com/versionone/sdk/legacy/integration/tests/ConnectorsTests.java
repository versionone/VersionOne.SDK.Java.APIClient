package com.versionone.sdk.legacy.integration.tests;

import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.versionone.apiclient.Connectors;
import com.versionone.apiclient.V1APIConnector;
import com.versionone.apiclient.interfaces.IConnectors;
import com.versionone.apiclient.interfaces.ICredentials;
import com.versionone.apiclient.interfaces.IUrls;

public class ConnectorsTests {

    private IConnectors _defaultTarget;
    private IConnectors _nonDefaultTarget;

    @Before
    public void SetUp() throws Exception {
        _defaultTarget = new Connectors();

        IUrls urls = new IUrls() {
            public String getV1Url() {
                return "http://google.com/";
            }

            public String getMetaUrl() {
                return "/blah1.1/";
            }

            public String getDataUrl() {
                return "jehosaphat55.3/";
            }

            public String getProxyUrl() {
                return "http://192.168.100.1:9090/";
            }

            public String getConfigUrl() {
                return "noConfig.1/";
            }
        };

        ICredentials credentials = new ICredentials() {
            public String getV1UserName() {
                return "BigPapa";
            }

            public String getV1Password() {
                return "Jimmy123";
            }

            public String getProxyUserName() {
                return "Administrator";
            }

            public String getProxyPassword() {
                return "123456";
            }
        };

        _nonDefaultTarget = new Connectors(urls, credentials);

    }

    @Test
    public void GetDataConnectorTest(){
        V1APIConnector connector = _defaultTarget.getDataConnector();
        Assert.assertNotNull(connector);
        connector = _nonDefaultTarget.getDataConnector();
        Assert.assertNotNull(connector);
    }

    @Test
    public void GetMetaConnectorTest(){
        V1APIConnector connector = _defaultTarget.getMetaConnector();
        Assert.assertNotNull(connector);
        connector = _nonDefaultTarget.getMetaConnector();
        Assert.assertNotNull(connector);
    }

    @Test
    public void GetConfigConnectorTest() throws URISyntaxException {
        V1APIConnector connector = _defaultTarget.getConfigConnector();
        Assert.assertNotNull(connector);
        connector = _nonDefaultTarget.getConfigConnector();
        Assert.assertNotNull(connector);
        connector = _defaultTarget.getConfigConnectorWithProxy();
        Assert.assertNotNull(connector);
    }

    @SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
    public void InvalidArgumentExceptionTest() throws Exception {
        IConnectors connectors = new Connectors(null, null);
    }



}
