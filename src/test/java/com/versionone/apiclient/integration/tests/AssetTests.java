package com.versionone.apiclient.integration.tests;

import java.sql.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import util.DateTimeUtil;

import com.versionone.DB.DateTime;
import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IOperation;
import com.versionone.apiclient.IServices;
import com.versionone.apiclient.OidException;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryResult;
import com.versionone.apiclient.V1Exception;

//TODO:
	//		Add scalar
	//		Update scalar (attribute)
	//		Remove scalar
	//		Add single-relation
	//		Update single-relation
	//		Remove single-relations
	//		Add multi-relation
	//		Update multi-relation
	//		Remove multi-relation
	//		Close an asset
	//		Reopen an asset
	//		Error: Invalid asset
	//		Error: Asset doesn't exists

public class AssetTests {

    private IMetaModel _metaModel;
    private IServices _services;
    private IAssetType _assetType;

    @Before
    public void setup() throws Exception {
        EnvironmentContext environment = new EnvironmentContext();
        _metaModel = environment.getMetaModel();
        _services = environment.getServices();
        _assetType = _metaModel.getAssetType("Story");
    }

    @Test(expected = OidException.class)
    @Ignore
    public void testSetInvalidOidOnAsset() throws V1Exception{
        Asset newStory = _services.createNew(_assetType, APIClientSuiteIT.get_projectId());
        newStory.setOid(Oid.fromToken("", _metaModel));
        Assert.assertNull(newStory.getOid());
    }
    
    @Test
    @Ignore
    public void testSetValidOidOnAsset() throws V1Exception {
        Asset newStory = _services.createNew(_assetType, APIClientSuiteIT.get_projectId());
        newStory.setOid(Oid.fromToken("Story:999999", _metaModel));
        Assert.assertNotNull(newStory.getOid());
    }
 
    public Asset createsAnAsset() throws V1Exception {
		//creates the asset
		Asset newStory = _services.createNew(_assetType, APIClientSuiteIT.get_projectId());
		IAttributeDefinition nameAttribute = _assetType.getAttributeDefinition("Name");
		newStory.setAttributeValue(nameAttribute, "IT JAVA SDK Add Story"+ DateTime.now().toString());
		_services.save(newStory);
    	
		return newStory;
    }
    
//	Create  asset
	@Test
	@Ignore
	public void testAddAnAsset() throws V1Exception {
		QueryResult result = null;
		// add an asset
		Asset newStory = createsAnAsset();
		// query for the new asset
		Oid memberId = newStory.getOid();
		Query query = new Query(memberId);
		result = _services.retrieve(query);
		Asset member = result.getAssets()[0];
		// assertion
		Assert.assertEquals(newStory.getOid(), member.getOid());
	}
    
//	delete  asset    
    @Test
    @Ignore
    public void testDeleteAnAsset() throws V1Exception {
    	
    	QueryResult result = null;
    	//creates the asset
		Asset newStory = createsAnAsset();
		//delete and Query if it exist
        IOperation deleteOperation = _metaModel.getOperation("Story.Delete");
        Oid deletedID = _services.executeOperation(deleteOperation, newStory.getOid());
        Query query = new Query(deletedID);
        //Query query = new Query(deletedID.getMomentless());
		result = _services.retrieve(query);
		//assertion
		Assert.assertEquals(0,result.getTotalAvaliable());
    }
    
//	Update an scalar    
    @Test
    public void testUpdateScalarAttribute() throws Exception {
    	//add an asset
    	Asset newStory = createsAnAsset();
    	//look for that asset
    	Query query = new Query(newStory.getOid());
        IAssetType storyType = _metaModel.getAssetType("Story");
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
       
        query.getSelection().add(nameAttribute);
        
        QueryResult result = _services.retrieve(query);
        Asset story = result.getAssets()[0];
        String oldName = story.getAttribute(nameAttribute).getValue().toString();
        //new values 
        story.setAttributeValue(nameAttribute, "IT New Name Defined");
        //saves the history
        _services.save(story);

        Assert.assertNotSame("Values:", oldName, story.getAttribute(nameAttribute).getValue().toString());
    }
 
}
