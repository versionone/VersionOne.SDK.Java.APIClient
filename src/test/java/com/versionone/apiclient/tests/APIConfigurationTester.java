package com.versionone.apiclient.tests;

import com.versionone.apiclient.APIConfiguration;
import com.versionone.apiclient.IAPIConfiguration;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class APIConfigurationTester {

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
}
