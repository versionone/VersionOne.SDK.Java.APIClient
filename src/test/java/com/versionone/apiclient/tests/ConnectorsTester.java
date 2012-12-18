package com.versionone.apiclient.tests;

import com.versionone.apiclient.*;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectorsTester {

    private IConnectors _defaultTarget;
    private IConnectors _nonDefaultTarget;

    @Before
    public void SetUp(){
        _defaultTarget = new Connectors();

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
                return "jehosaphat55.3/";
            }
        };

        ICredentials credentials = new ICredentials() {
            @Override
            public String getV1UserName() {
                return "BigPapa";
            }

            @Override
            public String getV1Password() {
                return "Jimmy123";
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

    @Test(expected = IllegalArgumentException.class)
    public void InvalidArgumentExceptionTest(){
        IConnectors connectors = new Connectors(null, null);
    }


}
