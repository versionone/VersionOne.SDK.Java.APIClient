package com.versionone.apiclient.integration.tests;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.apiclient.IUrls;
import com.versionone.apiclient.Urls;

public class UrlsTests {

    private IUrls _defaultTarget;
    private static final String V1_PATH = APIClientSuiteIT.getInstanceUrl();

    @Before
    public void Setup() throws IOException {
        _defaultTarget = new Urls();
    }

    @Test
    public void GetV1UrlTest(){
        Assert.assertEquals(V1_PATH, _defaultTarget.getV1Url());
    }

    @Test
    public void GetMetaUrlTest(){
        Assert.assertEquals(V1_PATH + "/meta.v1/", _defaultTarget.getMetaUrl());
    }

    @Test
    public void GetDataUrlTest(){
        Assert.assertEquals(V1_PATH + "/rest-1.v1/", _defaultTarget.getDataUrl());
    }

    @Test
    public void GetConfigUrlTest(){
        Assert.assertEquals(V1_PATH + "/config.v1/", _defaultTarget.getConfigUrl());
    }

    @Test
    @Ignore
    public void GetProxyUrlTest(){
        Assert.assertEquals("https://myProxyServer:3128", _defaultTarget.getProxyUrl());
    }

}
