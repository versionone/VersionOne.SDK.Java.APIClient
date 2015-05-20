package com.versionone.sdk.legacy.integration.tests;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.apiclient.Urls;
import com.versionone.apiclient.interfaces.IUrls;

@Ignore
public class UrlsTests {

    private IUrls _defaultTarget;
    private static final String V1Url = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getV1Url();

    @Before
    public void setup() throws IOException {
        _defaultTarget = new Urls();
    }

    @Test
    public void testGetV1Url(){
        Assert.assertEquals(V1Url, _defaultTarget.getV1Url());
    }

    @Test
    public void testGetMetaUrl(){
        Assert.assertEquals(V1Url + "/meta.v1/", _defaultTarget.getMetaUrl());
    }

    @Test
    public void testGetDataUrl(){
        Assert.assertEquals(V1Url + "/rest-1.v1/", _defaultTarget.getDataUrl());
    }

    @Test
    public void testGetConfigUrl(){
        Assert.assertEquals(V1Url + "/config.v1/", _defaultTarget.getConfigUrl());
    }

    @Test
    @Ignore
    public void testGetProxyUrl(){
        Assert.assertEquals("https://myProxyServer:3128", _defaultTarget.getProxyUrl());
    }

}
