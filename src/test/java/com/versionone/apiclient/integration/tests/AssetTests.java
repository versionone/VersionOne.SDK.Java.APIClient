package com.versionone.apiclient.integration.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.AssetState;
import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.IOperation;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.OidException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IMetaModel;
import com.versionone.apiclient.interfaces.IServices;
import com.versionone.apiclient.services.Query;
import com.versionone.apiclient.services.QueryResult;


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
     * @return Asset
     * @throws V1Exception
     */
    public Asset createsAnAsset(String assetName) throws V1Exception {
		//creates the asset
		Asset newStory = _services.createNew(_assetType, APIClientIntegrationTestSuiteIT.get_projectId());
		IAttributeDefinition nameAttribute = _assetType.getAttributeDefinition("Name");
		newStory.setAttributeValue(nameAttribute, assetName);
		_services.save(newStory);
    	
		return newStory;
    }

	public Asset createsAMember() throws V1Exception {
		Asset newMember = null;
		IAssetType assetType = _metaModel.getAssetType("Member");
		newMember = _services.createNew(assetType, APIClientIntegrationTestSuiteIT.get_projectId());
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
	
	public QueryResult query(Oid memberId, IAttributeDefinition attribute) {
		QueryResult result = null;
		Query query = new Query(memberId);
		if(attribute != null){
			query.getSelection().add(attribute);
		}
		try {
			result = _services.retrieve(query);
		} catch (ConnectionException | APIException | OidException e) {
			e.printStackTrace();
		};
	
		return result;
	}

	//	Error: Invalid asset
    @Test(expected = OidException.class)
    public void testSetInvalidOidOnAsset() throws V1Exception{
        Asset newStory = _services.createNew(_assetType, APIClientIntegrationTestSuiteIT.get_projectId());
        newStory.setOid(Oid.fromToken("", _metaModel));
        Assert.assertNull(newStory.getOid());
    }
    
    //	Error: Asset doesn't exists
    @Test
    public void testSetValidOidOnAsset() throws V1Exception {
        Asset newStory = _services.createNew(_assetType, APIClientIntegrationTestSuiteIT.get_projectId());
        newStory.setOid(Oid.fromToken("Story:999999", _metaModel));
        Assert.assertNotNull(newStory.getOid());
    }
    
    //	Create  asset
	@Test
	public void testAddAnAsset() throws V1Exception {
	
		Asset newStory = createsAnAsset("AssetTests: Add a new asset");
		Oid memberId = newStory.getOid();
		Asset member = query(memberId, null).getAssets()[0];

		Assert.assertEquals(newStory.getOid(), member.getOid());
	}
    
	//	delete  asset    
    @Test
    public void testDeleteAnAsset() throws V1Exception {
    	
		Asset newStory = createsAnAsset("AssetTests: Delete an asset");
        IOperation deleteOperation = _metaModel.getOperation("Story.Delete");
        Oid deletedID = _services.executeOperation(deleteOperation, newStory.getOid());

        Assert.assertEquals(0,query(deletedID, null).getTotalAvaliable());
    }
    
	//	Close an asset
    @Test
	public void testCloseAnAsset() throws V1Exception {

		Asset newStory = createsAnAsset("AssetTests: Close an asset");
		IOperation closeOperation = _metaModel.getOperation("Story.Inactivate");
		Oid closeID = _services.executeOperation(closeOperation, newStory.getOid());
		IAttributeDefinition assetState = _metaModel.getAttributeDefinition("Story.AssetState");
		
		Asset closeStory = query(closeID.getMomentless(), assetState).getAssets()[0];
		AssetState state = AssetState.valueOf(((Integer) closeStory.getAttribute(assetState).getValue()).intValue());
	
		Assert.assertEquals("Closed", state.toString());
	}

	//	Reopen an asset
	@Test
	public void testReopenAnAsset() throws V1Exception {

		Asset newStory = createsAnAsset("AssetTests: Reopen an asset");
		IOperation closeOperation = _metaModel.getOperation("Story.Inactivate");
		Oid closeID = _services.executeOperation(closeOperation, newStory.getOid());
		IAttributeDefinition assetState = _metaModel.getAttributeDefinition("Story.AssetState");
	
		Asset story = query(closeID.getMomentless(), assetState).getAssets()[0];

		IOperation activateOperation = _metaModel.getOperation("Story.Reactivate");
		Oid activeID = _services.executeOperation(activateOperation, story.getOid());
		assetState = _metaModel.getAttributeDefinition("Story.AssetState");
	
		Asset activeStory =query(activeID.getMomentless(), assetState).getAssets()[0];
		AssetState state = AssetState.valueOf(((Integer) activeStory.getAttribute(assetState).getValue()).intValue());

		Assert.assertEquals("Active", state.toString());
	}
    
	//	Update an scalar    
    @Test
    public void testUpdateScalarAttribute() throws Exception {
  
    	Asset newStory = createsAnAsset("AssetTests: Update an Scalar");
        IAssetType storyType = _metaModel.getAssetType("Story");
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        Asset story =  query(newStory.getOid(), nameAttribute).getAssets()[0];
        String oldName = story.getAttribute(nameAttribute).getValue().toString();
        story.setAttributeValue(nameAttribute, "AssetTests: Update an Scalar - Name updated");
        _services.save(story);

        Assert.assertNotSame("Values:", oldName, story.getAttribute(nameAttribute).getValue().toString());
    }
    
    
    //	Update single-relation
	@Test
	public void testUpdateSingleValueRelation() throws Exception {

		Asset newStory = createsAnAsset("AssetTests: Update Single Value Relation");
		IAssetType storyType = _metaModel.getAssetType("Story");
		IAttributeDefinition sourceAttribute = storyType.getAttributeDefinition("Source");
		newStory.setAttributeValue(sourceAttribute, "StorySource:156");
		_services.save(newStory);
		
		Asset story =  query(newStory.getOid(), sourceAttribute).getAssets()[0];

		Assert.assertNotNull(story.getAttribute(sourceAttribute).getValue().toString());
	}
   
	//	Add multi-relation
	@Test
	public void testAddMultipleValueRelation() throws Exception {

		Asset parentStory = createsAnAsset("AssetTests: Add Multiple Value Relation - Parent Story");
		Asset childStory1 = createsAnAsset("AssetTests: Add Multiple Value Relation - Child1 Story");
		Asset childStory2 = createsAnAsset("AssetTests: Add Multiple Value Relation - Child2 Story");
		
		IAssetType storyType = _metaModel.getAssetType("Story");
		IAttributeDefinition dependantsAttribute = storyType.getAttributeDefinition("Dependants");
		parentStory.addAttributeValue(dependantsAttribute,  childStory1.getOid());
        _services.save(parentStory);

		Asset story =  query(parentStory.getOid(), dependantsAttribute).getAssets()[0];
        
		Assert.assertEquals(1, story.getAttribute(dependantsAttribute).getValues().length);
       
		parentStory.addAttributeValue(dependantsAttribute,  childStory2.getOid());
        _services.save(parentStory);

      	story =  query(parentStory.getOid(), dependantsAttribute).getAssets()[0];
              
      	Assert.assertEquals(2, story.getAttribute(dependantsAttribute).getValues().length); 
	}

	//	Remove multi-relation
	@Test
	public void testRemoveMultipleValueRelation() throws Exception {

		Asset parentStory = createsAnAsset("AssetTests: Remove Multiple Value Relation - Parent Story");
		Asset childStory1 = createsAnAsset("AssetTests: Remove Multiple Value Relation - Child1 Story");
		Asset childStory2 = createsAnAsset("AssetTests: Remove Multiple Value Relation - Child2 Story");
		
		IAssetType storyType = _metaModel.getAssetType("Story");
		IAttributeDefinition dependantsAttribute = storyType.getAttributeDefinition("Dependants");
		parentStory.addAttributeValue(dependantsAttribute,  childStory1.getOid());
		parentStory.addAttributeValue(dependantsAttribute,  childStory2.getOid());
        _services.save(parentStory);

		Asset story =  query(parentStory.getOid(), dependantsAttribute).getAssets()[0];
		
		Assert.assertEquals(2, story.getAttribute(dependantsAttribute).getValues().length); 

		parentStory.removeAttributeValue(dependantsAttribute,  childStory1.getOid());
        _services.save(parentStory);
        
		story =  query(parentStory.getOid(), dependantsAttribute).getAssets()[0];
		
        Assert.assertEquals(1, story.getAttribute(dependantsAttribute).getValues().length);
	}	
}
