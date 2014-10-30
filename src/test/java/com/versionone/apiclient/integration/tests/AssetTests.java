package com.versionone.apiclient.integration.tests;

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

	public Asset createsAMember() throws V1Exception {
		Asset newMember = null;
		IAssetType assetType = _metaModel.getAssetType("Member");
		newMember = _services.createNew(assetType, APIClientSuiteIT.get_projectId());
		IAttributeDefinition defaultRoleAttribute = assetType.getAttributeDefinition("DefaultRole");
		newMember.setAttributeValue(defaultRoleAttribute, "Role:2");
		IAttributeDefinition isCollaboratorAttribute = assetType.getAttributeDefinition("IsCollaborator");
		newMember.setAttributeValue(isCollaboratorAttribute, false);
		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
		newMember.setAttributeValue(nameAttribute, "Andre Agile");
		IAttributeDefinition nicknameAttribute = assetType.getAttributeDefinition("Nickname");
		newMember.setAttributeValue(nicknameAttribute, "Andre");
		IAttributeDefinition notifyViaEmailAttribute = assetType.getAttributeDefinition("NotifyViaEmail");
		newMember.setAttributeValue(notifyViaEmailAttribute, false);
		IAttributeDefinition sendConversationEmailsAttribute = assetType.getAttributeDefinition("SendConversationEmails");
		newMember.setAttributeValue(sendConversationEmailsAttribute, false);

		_services.save(newMember);

		return newMember;
	}

	//	Error: Invalid asset
    @Test(expected = OidException.class)
    public void testSetInvalidOidOnAsset() throws V1Exception{
        Asset newStory = _services.createNew(_assetType, APIClientSuiteIT.get_projectId());
        newStory.setOid(Oid.fromToken("", _metaModel));
        Assert.assertNull(newStory.getOid());
    }
    
    //	Error: Asset doesn't exists
    @Test
    public void testSetValidOidOnAsset() throws V1Exception {
        Asset newStory = _services.createNew(_assetType, APIClientSuiteIT.get_projectId());
        newStory.setOid(Oid.fromToken("Story:999999", _metaModel));
        Assert.assertNotNull(newStory.getOid());
    }
    
    //	Create  asset
	@Test
	public void testAddAnAsset() throws V1Exception {
		QueryResult result = null;
		Asset newStory = createsAnAsset();
		Oid memberId = newStory.getOid();
		Query query = new Query(memberId);
		result = _services.retrieve(query);
		Asset member = result.getAssets()[0];

		Assert.assertEquals(newStory.getOid(), member.getOid());
	}
    
	//	delete  asset    
    @Test
    public void testDeleteAnAsset() throws V1Exception {
    	
    	QueryResult result = null;
		Asset newStory = createsAnAsset();
        IOperation deleteOperation = _metaModel.getOperation("Story.Delete");
        Oid deletedID = _services.executeOperation(deleteOperation, newStory.getOid());
        Query query = new Query(deletedID);
		result = _services.retrieve(query);

		Assert.assertEquals(0,result.getTotalAvaliable());
    }
    
	//	Close an asset
    @Test
	public void testCloseAnAsset() throws V1Exception {

		QueryResult result = null;
		Asset newStory = createsAnAsset();
		IOperation closeOperation = _metaModel.getOperation("Story.Inactivate");
		Oid closeID = _services.executeOperation(closeOperation, newStory.getOid());
		Query query = new Query(closeID.getMomentless());
		IAttributeDefinition assetState = _metaModel.getAttributeDefinition("Story.AssetState");
		query.getSelection().add(assetState);
		
		result = _services.retrieve(query);
		Asset closeStory = result.getAssets()[0];
		AssetState state = AssetState.valueOf(((Integer) closeStory.getAttribute(assetState).getValue()).intValue());
	
		Assert.assertEquals("Closed", state.toString());
	}

	//	Reopen an asset
	@Test
	public void testReopenAnAsset() throws V1Exception {

		QueryResult result = null;
		Asset newStory = createsAnAsset();
		IOperation closeOperation = _metaModel.getOperation("Story.Inactivate");
		Oid closeID = _services.executeOperation(closeOperation, newStory.getOid());
		Query query = new Query(closeID.getMomentless());
		IAttributeDefinition assetState = _metaModel.getAttributeDefinition("Story.AssetState");
		query.getSelection().add(assetState);
		result = _services.retrieve(query);
		Asset story = result.getAssets()[0];

		IOperation activateOperation = _metaModel.getOperation("Story.Reactivate");
		Oid activeID = _services.executeOperation(activateOperation, story.getOid());
		query = new Query(activeID.getMomentless());
		assetState = _metaModel.getAttributeDefinition("Story.AssetState");
		query.getSelection().add(assetState);
		result = _services.retrieve(query);
		Asset activeStory = result.getAssets()[0];
		AssetState state = AssetState.valueOf(((Integer) activeStory.getAttribute(assetState).getValue()).intValue());

		Assert.assertEquals("Active", state.toString());
	}
    
	//	Update an scalar    
    @Test
    public void testUpdateScalarAttribute() throws Exception {
  
    	Asset newStory = createsAnAsset();
    	Query query = new Query(newStory.getOid());
        IAssetType storyType = _metaModel.getAssetType("Story");
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        query.getSelection().add(nameAttribute);
        QueryResult result = _services.retrieve(query);
        Asset story = result.getAssets()[0];
        String oldName = story.getAttribute(nameAttribute).getValue().toString();
        story.setAttributeValue(nameAttribute, "IT New Name Defined");
        _services.save(story);

        Assert.assertNotSame("Values:", oldName, story.getAttribute(nameAttribute).getValue().toString());
    }
    
    
    //	Update single-relation
	@Test
	public void testUpdateSingleValueRelation() throws Exception {

		Asset newStory = createsAnAsset();
		IAssetType storyType = _metaModel.getAssetType("Story");
		IAttributeDefinition sourceAttribute = storyType.getAttributeDefinition("Source");
		newStory.setAttributeValue(sourceAttribute, "StorySource:156");
		_services.save(newStory);
		Query query = new Query(newStory.getOid());
		query.getSelection().add(sourceAttribute);
		QueryResult result = _services.retrieve(query);
		Asset story = result.getAssets()[0];

		Assert.assertNotNull(story.getAttribute(sourceAttribute).getValue().toString());
	}
   
	//	Add multi-relation
	@Test
	public void testAddMultipleValueRelation() throws Exception {

		Asset parentStory = createsAnAsset();
		Asset childStory1 = createsAnAsset();
		Asset childStory2 = createsAnAsset();
		
		IAssetType storyType = _metaModel.getAssetType("Story");
		IAttributeDefinition dependantsAttribute = storyType.getAttributeDefinition("Dependants");
		parentStory.addAttributeValue(dependantsAttribute,  childStory1.getOid());
        _services.save(parentStory);

        Query query = new Query(parentStory.getOid());
		query.getSelection().add(dependantsAttribute);
		QueryResult result = _services.retrieve(query);
		Asset story = result.getAssets()[0];
        
		Assert.assertEquals(1, story.getAttribute(dependantsAttribute).getValues().length);
       
		parentStory.addAttributeValue(dependantsAttribute,  childStory2.getOid());
        _services.save(parentStory);

        query = new Query(parentStory.getOid());
      	query.getSelection().add(dependantsAttribute);
      	result = _services.retrieve(query);
      	story = result.getAssets()[0];
              
      	Assert.assertEquals(2, story.getAttribute(dependantsAttribute).getValues().length); 
	}

	//	Remove multi-relation
	@Test
	public void testRemoveMultipleValueRelation() throws Exception {

		Asset parentStory = createsAnAsset();
		Asset childStory1 = createsAnAsset();
		Asset childStory2 = createsAnAsset();
		
		IAssetType storyType = _metaModel.getAssetType("Story");
		IAttributeDefinition dependantsAttribute = storyType.getAttributeDefinition("Dependants");
		parentStory.addAttributeValue(dependantsAttribute,  childStory1.getOid());
		parentStory.addAttributeValue(dependantsAttribute,  childStory2.getOid());
        _services.save(parentStory);

        Query query = new Query(parentStory.getOid());
		query.getSelection().add(dependantsAttribute);
		QueryResult result = _services.retrieve(query);
		Asset story = result.getAssets()[0];
		
		Assert.assertEquals(2, story.getAttribute(dependantsAttribute).getValues().length); 

		parentStory.removeAttributeValue(dependantsAttribute,  childStory1.getOid());
        _services.save(parentStory);
        
        query = new Query(parentStory.getOid());
		query.getSelection().add(dependantsAttribute);
		result = _services.retrieve(query);
		story = result.getAssets()[0];
		
        Assert.assertEquals(1, story.getAttribute(dependantsAttribute).getValues().length);
	}	
}
