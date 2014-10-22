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
    public void SetUp() throws IOException {
        _target = new APIConfiguration();
    }

    @Test
    public void GetV1UrlTest(){
        String v1Url = _target.getV1Url();
        Assert.assertTrue(v1Url.length() != 0);
        Assert.assertTrue(v1Url.startsWith("http"));
        Assert.assertTrue(v1Url.endsWith("/"));
    }

    @Test
    public void GetDataUrlTest(){
        String dataUrl = _target.getDataUrl();
        Assert.assertTrue(dataUrl.length() != 0);
        Assert.assertTrue(dataUrl.endsWith("rest-1.v1/"));  //reasonable assumption...for now
    }

    @Test
    public void GetMetaUrlTest(){
        String metaUrl = _target.getMetaUrl();
        Assert.assertTrue(metaUrl.length() != 0);
        Assert.assertTrue(metaUrl.endsWith("meta.v1/"));  //reasonable assumption...for now
    }

    @Test
    public void GetUserNameTest(){
        String userName = _target.getV1UserName();
        Assert.assertTrue(userName.length() != 0);
    }

    @Test
    public void GetPasswordTest(){
        String password = _target.getV1Password();
        Assert.assertTrue(password.length() != 0);
    }

    @Test
    public void GetConfigUrlTest(){
        String configUrl = _target.getConfigUrl();
        Assert.assertTrue(configUrl.length() != 0);
    }

    @Test
    public void GetProxyUrlTest(){
        String proxyUrl = _target.getProxyUrl();
        Assert.assertTrue(proxyUrl.length() != 0);
        Assert.assertTrue(proxyUrl.startsWith("http"));
    }

    @Test
    public void GetProxyUserNameTest(){
        String userName = _target.getProxyUserName();
        Assert.assertTrue(userName.length() != 0);
    }

    @Test
    public void GetProxyPasswordTest(){
        String password = _target.getProxyPassword();
        Assert.assertTrue(password.length() != 0);
    }
}
