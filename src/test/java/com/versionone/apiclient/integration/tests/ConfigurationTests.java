package com.versionone.apiclient.integration.tests;

import com.versionone.apiclient.APIConfiguration;
import com.versionone.apiclient.IAPIConfiguration;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ConfigurationTests {

    private IAPIConfiguration _target;

    @Before
    public void setup() throws IOException {
        _target = new APIConfiguration();
    }

    @Test
    public void testGetV1Url(){
        String v1Url = _target.getV1Url();
        Assert.assertTrue(v1Url.length() != 0);
        Assert.assertTrue(v1Url.startsWith("http"));
        Assert.assertTrue(v1Url.endsWith("/"));
    }

    @Test
    public void testGetDataUrl(){
        String dataUrl = _target.getDataUrl();
        Assert.assertTrue(dataUrl.length() != 0);
        Assert.assertTrue(dataUrl.endsWith("rest-1.v1/"));  //reasonable assumption...for now
    }

    @Test
    public void testGetMetaUrl(){
        String metaUrl = _target.getMetaUrl();
        Assert.assertTrue(metaUrl.length() != 0);
        Assert.assertTrue(metaUrl.endsWith("meta.v1/"));  //reasonable assumption...for now
    }

    @Test
    public void testGetUserName(){
        String userName = _target.getV1UserName();
        Assert.assertTrue(userName.length() != 0);
    }

    @Test
    public void testGetPassword(){
        String password = _target.getV1Password();
        Assert.assertTrue(password.length() != 0);
    }

    @Test
    public void testGetConfigUrl(){
        String configUrl = _target.getConfigUrl();
        Assert.assertTrue(configUrl.length() != 0);
    }

    @Test
    public void testGetProxyUrl(){
        String proxyUrl = _target.getProxyUrl();
        Assert.assertTrue(proxyUrl.length() != 0);
        Assert.assertTrue(proxyUrl.startsWith("http"));
    }

    @Test
    public void testGetProxyUserName(){
        String userName = _target.getProxyUserName();
        Assert.assertTrue(userName.length() != 0);
    }

    @Test
    public void testGetProxyPassword(){
        String password = _target.getProxyPassword();
        Assert.assertTrue(password.length() != 0);
    }
}
