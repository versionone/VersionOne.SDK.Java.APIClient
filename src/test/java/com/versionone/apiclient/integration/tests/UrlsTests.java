package com.versionone.apiclient.integration.tests;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.apiclient.IUrls;
import com.versionone.apiclient.Urls;

@Ignore
public class UrlsTests {

    private IUrls _defaultTarget;
    private static final String V1_PATH = APIClientSuiteIT.getInstanceUrl();

    @Before
    public void setup() throws IOException {
        _defaultTarget = new Urls();
    }

    @Test
    public void testGetV1Url(){
        Assert.assertEquals(V1_PATH, _defaultTarget.getV1Url());
    }

    @Test
    public void testGetMetaUrl(){
        Assert.assertEquals(V1_PATH + "/meta.v1/", _defaultTarget.getMetaUrl());
    }

    @Test
    public void testGetDataUrl(){
        Assert.assertEquals(V1_PATH + "/rest-1.v1/", _defaultTarget.getDataUrl());
    }

    @Test
    public void testGetConfigUrl(){
        Assert.assertEquals(V1_PATH + "/config.v1/", _defaultTarget.getConfigUrl());
    }

    @Test
    @Ignore
    public void testGetProxyUrl(){
        Assert.assertEquals("https://myProxyServer:3128", _defaultTarget.getProxyUrl());
    }

}
