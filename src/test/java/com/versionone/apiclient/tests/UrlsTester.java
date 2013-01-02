package com.versionone.apiclient.tests;

import com.versionone.apiclient.IUrls;
import com.versionone.apiclient.Urls;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class UrlsTester {

    private IUrls _defaultTarget;
    private IUrls _nonDefaultTarget;

    @Before
    public void Setup() throws IOException {
        _defaultTarget = new Urls();
    }

    @Test
    public void GetV1UrlTest(){
        Assert.assertEquals("https://www14.v1host.com/v1sdktesting/", _defaultTarget.getV1Url());
    }

    @Test
    public void GetMetaUrlTest(){
        Assert.assertEquals("https://www14.v1host.com/v1sdktesting/meta.v1/", _defaultTarget.getMetaUrl());
    }

    public void GetDataUrlTest(){
        Assert.assertEquals("https://www14.v1host.com/v1sdktesting/rest-1.v1/", _defaultTarget.getDataUrl());
    }

}
