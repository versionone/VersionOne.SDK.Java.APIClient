package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IServices;

public class AttachmentsAndImages {
	
	private static IServices _services;
	private static String _instanceUrl;
	private static String _accessToken;
	private static Oid _projectId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Running attachments integration tests...");
		_services = APIClientIntegrationTestSuiteIT.get_services();
		_instanceUrl = APIClientIntegrationTestSuiteIT.get_instanceUrl();
		_accessToken = APIClientIntegrationTestSuiteIT.get_accessToken();
		_projectId = APIClientIntegrationTestSuiteIT.get_projectId();
	}
	
	
	 
	@Test
	@Ignore
	public void CreateStoryWithAttachmentTest() throws V1Exception, IOException {
		String file = "com/versionone/apiclient/versionone.png";
		assertNotNull("Test file missing", Thread.currentThread().getContextClassLoader().getResource(file));
		
		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition storyNameAttr = storyType.getAttributeDefinition("Name");
		IAttributeDefinition attachmentsAttribute = storyType.getAttributeDefinition("Attachments");
		String name = "Test Story " + _projectId + "Create story with attachment";
		newStory.setAttributeValue(storyNameAttr, name);
		_services.save(newStory);
	
		_services.saveAttachment(Thread.currentThread().getContextClassLoader().getResource(file).getPath(), newStory, newStory.getOid().toString());
		
		Query query = new Query(newStory.getOid().getMomentless());
		query.getSelection().add(attachmentsAttribute);
		Asset story = _services.retrieve(query).getAssets()[0];

		assertEquals(1, story.getAttribute(attachmentsAttribute).getValues().length);
			
	}
		
	 
}
