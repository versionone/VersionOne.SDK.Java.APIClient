package com.versionone.apiclient.integration.tests;

import com.versionone.apiclient.*;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.services.BuildResult;
import com.versionone.apiclient.services.FindBuilder;
import com.versionone.apiclient.services.QueryFind;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FindBuilderTests {

    private FindBuilder _target;
    private EnvironmentContext _context;

    @Before
    public void setup() throws Exception {
        _target = new FindBuilder();
        _context = new EnvironmentContext();
    }

    @After
    public void tearDown() {
        _context = null;
        _target = null;
    }

    @Test
    public void testBuildWithInvalidParameters()
    {
        IAssetType assetType = _context.getMetaModel().getAssetType("Member");
        _target.build(null, null);
        _target.build(new Query(assetType), new BuildResult());
        BuildResult result = new BuildResult();
        _target.build(new Query(assetType), result);
        Assert.assertEquals(0, result.querystringParts.size());
    }

    @Test
    public void testBuildWithValidParameters()
    {
        IAssetType assetType = _context.getMetaModel().getAssetType("Member");
        Query query = new Query(assetType);
        IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Username");
        query.getSelection().add(nameAttribute);
        AttributeSelection selection = new AttributeSelection();
        selection.add(nameAttribute);
        query.setFind(new QueryFind("admin", selection));
        BuildResult result = new BuildResult();
        _target.build(query, result);
        Assert.assertEquals(2, result.querystringParts.size()); //one part for find, one part for findin
        Assert.assertEquals("?find=admin&findin=Member.Username", result.toUrl());
    }

    @Test
    public void testQueryStringEscape()
    {
        IAssetType assetType = _context.getMetaModel().getAssetType("Member");
        Query query = new Query(assetType);
        IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Username");
        query.getSelection().add(nameAttribute);
        AttributeSelection selection = new AttributeSelection();
        selection.add(nameAttribute);
        query.setFind(new QueryFind("admin@mydomain.com", selection));  //make sure ampersand get url encoded
        BuildResult result = new BuildResult();
        _target.build(query, result);
        Assert.assertEquals(2, result.querystringParts.size()); //one part for find, one part for findin
        Assert.assertEquals("?find=admin%40mydomain.com&findin=Member.Username", result.toUrl());
    }

}
