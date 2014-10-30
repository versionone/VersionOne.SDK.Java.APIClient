package com.versionone.apiclient.integration.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.DB.DateTime;
import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.AssetState;
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

	//	Error: Invalid asset
    @Test(expected = OidException.class)
    //@Ignore
    public void testSetInvalidOidOnAsset() throws V1Exception{
        Asset newStory = _services.createNew(_assetType, APIClientSuiteIT.get_projectId());
        newStory.setOid(Oid.fromToken("", _metaModel));
        Assert.assertNull(newStory.getOid());
    }
    
    //	Error: Asset doesn't exists
    @Test
    //@Ignore
    public void testSetValidOidOnAsset() throws V1Exception {
        Asset newStory = _services.createNew(_assetType, APIClientSuiteIT.get_projectId());
        newStory.setOid(Oid.fromToken("Story:999999", _metaModel));
        Assert.assertNotNull(newStory.getOid());
    }
    
    /**
     * common method for creation of an asset
     * @return
     * @throws V1Exception
     */
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
	//@Ignore
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
    //@Ignore
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
    
	//	Close an asset
    @Test
    //@Ignore
	public void testCloseAnAsset() throws V1Exception {

		QueryResult result = null;
		// creates the asset
		Asset newStory = createsAnAsset();
		// change the value to close and Query 
		IOperation closeOperation = _metaModel.getOperation("Story.Inactivate");
		Oid closeID = _services.executeOperation(closeOperation, newStory.getOid());

		Query query = new Query(closeID.getMomentless());
		IAttributeDefinition assetState = _metaModel.getAttributeDefinition("Story.AssetState");
		query.getSelection().add(assetState);
		
		result = _services.retrieve(query);
		
		Asset closeStory = result.getAssets()[0];

		AssetState state = AssetState.valueOf(((Integer) closeStory.getAttribute(assetState).getValue()).intValue());
		// assertion
		Assert.assertEquals("Closed", state.toString());
	}

	//	Reopen an asset
	@Test
	//@Ignore
	public void testReopenAnAsset() throws V1Exception {

		QueryResult result = null;
		//creates an asset
		Asset newStory = createsAnAsset();
		// change the value to close and Query 
		IOperation closeOperation = _metaModel.getOperation("Story.Inactivate");
		Oid closeID = _services.executeOperation(closeOperation, newStory.getOid());
		//return the closed story
		Query query = new Query(closeID.getMomentless());
		IAttributeDefinition assetState = _metaModel.getAttributeDefinition("Story.AssetState");
		query.getSelection().add(assetState);
		result = _services.retrieve(query);
		Asset story = result.getAssets()[0];
		
		//reopen the closed story
		IOperation activateOperation = _metaModel.getOperation("Story.Reactivate");
		Oid activeID = _services.executeOperation(activateOperation, story.getOid());
		query = new Query(activeID.getMomentless());
		assetState = _metaModel.getAttributeDefinition("Story.AssetState");
		query.getSelection().add(assetState);
		result = _services.retrieve(query);
		Asset activeStory = result.getAssets()[0];
				
		AssetState state = AssetState.valueOf(((Integer) activeStory.getAttribute(assetState).getValue()).intValue());

		// assertion
		Assert.assertEquals("Active", state.toString());
	}
    
	//	Update an scalar    
    @Test
    //@Ignore
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
    
    
    //	Update single-relation
	@Test
	//@Ignore
	public void testUpdateSingleValueRelation() throws Exception {

		// add an asset
		Asset newStory = createsAnAsset();
		//define the attribute 
		IAssetType storyType = _metaModel.getAssetType("Story");
		IAttributeDefinition sourceAttribute = storyType.getAttributeDefinition("Source");
		newStory.setAttributeValue(sourceAttribute, "StorySource:156");
		_services.save(newStory);
		//query for the asset 
		Query query = new Query(newStory.getOid());
		query.getSelection().add(sourceAttribute);
		QueryResult result = _services.retrieve(query);
		Asset story = result.getAssets()[0];

		//assertion
        Assert.assertNotNull(story.getAttribute(sourceAttribute).getValue().toString());
	}
   
	//	Add multi-relation
	@Test
	//@Ignore
	public void testAddMultipleValueRelation() throws Exception {

		//Oid storyId = Oid.fromToken("Story:1124", _metaModel);
		
		Asset newStory = createsAnAsset();
		
		IAssetType storyType = _metaModel.getAssetType("Story");
		IAttributeDefinition ownersAttribute = storyType.getAttributeDefinition("Owners");

		//assertion no owner set
        Assert.assertNull(newStory.getAttribute(ownersAttribute).getValue().toString());
        //add the relation 
        newStory.addAttributeValue(ownersAttribute,  "StorySource:156");
		
		_services.save(newStory);

	}

	 //	Remove multi-relation
	
    //	Update multi-relation
  

}
