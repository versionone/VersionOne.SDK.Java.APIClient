package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IServices;

public class UpdateAssets {
	private static IServices _services;
	private static Oid _projectId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Running attachments integration tests...");
		_services = APIClientIntegrationTestSuiteIT.get_services();
		_projectId = APIClientIntegrationTestSuiteIT.get_projectId();
	}

   @Test
    public void UpdateScalarAttributeOnStory() throws MetaException, V1Exception{

        IAssetType storyType = _services.getMeta().getAssetType("Story");
        Asset newStory = _services.createNew(storyType, _projectId);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        String name = "Test Story " + _projectId + " Update scalar attribute";
        newStory.setAttributeValue(nameAttribute, name);
        _services.save(newStory);

        assertNotNull(newStory.getOid());

        Query query = new Query(newStory.getOid());
        query.getSelection().add(nameAttribute);
        Asset story = _services.retrieve(query).getAssets()[0];
        String oldName = story.getAttribute(nameAttribute).getValue().toString();
        story.setAttributeValue(nameAttribute, name + " - Name updated");
        _services.save(story);

       assertNotSame(oldName, story.getAttribute(nameAttribute).getValue().toString());
    }

   	@Test
    public void UpdateSingleRelationAttributeOnStory() throws V1Exception {

        IAssetType storyType = _services.getMeta().getAssetType("Story");
        Asset newStory = _services.createNew(storyType, _projectId);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        String name = "Test Story "+ _projectId+ " Update single-relation attribute";
        newStory.setAttributeValue(nameAttribute, name);
        _services.save(newStory);

        assertNotNull(newStory.getOid());

        IAttributeDefinition sourceAttribute = storyType.getAttributeDefinition("Source");
        newStory.setAttributeValue(sourceAttribute, "StorySource:156");
        _services.save(newStory);

        Query query = new Query(newStory.getOid());
        query.getSelection().add(sourceAttribute);
        Asset story = _services.retrieve(query).getAssets()[0];

        assertNotNull(story.getAttribute(sourceAttribute).getValue().toString());
    }

   	@Test
    public void UpdateMultiRelationAttributeOnStory() throws V1Exception
    {
        IAssetType storyType = _services.getMeta().getAssetType("Story");
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");

        Asset parentStory = _services.createNew(storyType, _projectId);
        String name = "Test Story " + _projectId +  " Update multi-relation attribute";
        parentStory.setAttributeValue(nameAttribute,  name);
        _services.save(parentStory);
        
        Asset child1Story = _services.createNew(storyType, _projectId);
        child1Story.setAttributeValue(nameAttribute, name + " - Child 1");
        _services.save(child1Story);
        Asset child2Story = _services.createNew(storyType, _projectId);
        child2Story.setAttributeValue(nameAttribute, name + " - Child 2");
        _services.save(child2Story);

        IAttributeDefinition dependantsAttribute = storyType.getAttributeDefinition("Dependants");
        parentStory.addAttributeValue(dependantsAttribute, child1Story.getOid());
        _services.save(parentStory);

        Query query = new Query(parentStory.getOid());
        query.getSelection().add(dependantsAttribute);
        Asset story = _services.retrieve(query).getAssets()[0];

       assertEquals(1, story.getAttribute(dependantsAttribute).getValues().length);

        parentStory.addAttributeValue(dependantsAttribute, child2Story.getOid());
        _services.save(parentStory);

        Query query2 = new Query(parentStory.getOid());
        query2.getSelection().add(dependantsAttribute);
        story = _services.retrieve(query2).getAssets()[0];

        assertEquals(2, story.getAttribute(dependantsAttribute).getValues().length);
    }
}
