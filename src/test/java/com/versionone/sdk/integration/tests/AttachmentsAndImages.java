package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
	
	
//	@Test
//	public void CreateStoryWithAttachmentTest() throws V1Exception, IOException {
//	
//		String file = "com/versionone/apiclient/versionone.png";
//		assertNotNull("Test file missing", Thread.currentThread().getContextClassLoader().getResource(file));
//		String mimeType = MimeType.resolve(file);
//		
//		V1Connector connector = V1Connector
//				.withInstanceUrl(_instanceUrl)
//				.withUserAgentHeader("JavaSDKIntegrationTests", "1.0")
//				.withAccessToken(_accessToken)
//				.useEndpoint("attachment.img/")
//				.build();
//		
//		IAttachments attachments = new Attachments(connector);
//
//		IAssetType storyType = _services.getMeta().getAssetType("Story");
//		Asset newStory = _services.createNew(storyType, _projectId);
//		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
//		IAttributeDefinition attachmentsAttribute = storyType.getAttributeDefinition("Attachments");
//		String name = "Test Story " + _projectId + "Create story with attachment";
//		newStory.setAttributeValue(nameAttribute, name);
//		_services.save(newStory);
//
//		IAssetType attachmentType = _services.getMeta().getAssetType("Attachment");
//		IAttributeDefinition attachmentAssetDef = attachmentType.getAttributeDefinition("Asset");
//		IAttributeDefinition attachmentContent = attachmentType.getAttributeDefinition("Content");
//		IAttributeDefinition attachmentContentType = attachmentType.getAttributeDefinition("ContentType");
//		IAttributeDefinition attachmentFileName = attachmentType.getAttributeDefinition("Filename");
//		IAttributeDefinition attachmentName = attachmentType.getAttributeDefinition("Name");
//		
//		Asset attachment = _services.createNew(attachmentType, Oid.Null);
//		attachment.setAttributeValue(attachmentName, "Test Attachment on " + newStory.getOid());
//		attachment.setAttributeValue(attachmentFileName, file);
//		attachment.setAttributeValue(attachmentContentType, mimeType);
//		attachment.setAttributeValue(attachmentContent, "");
//		attachment.setAttributeValue(attachmentAssetDef, newStory.getOid());
//		_services.save(attachment);
//		
//		String key = attachment.getOid().getKey().toString();
//	    FileInputStream inStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource(file).getPath());
//	    OutputStream output =  attachments.getWriter(key, mimeType);
//	    byte[] buffer = new byte[inStream.available() + 1];
//	    while (true){
//	    	int read = inStream.read(buffer, 0, buffer.length);
//	        if (read <= 0)
//	         break;
//	        output.write(buffer, 0, read);
//	    }
//	     
//	    attachments.setWriter(key);
//	    inStream.close();
//		
//		Query query = new Query(newStory.getOid().getMomentless());
//		query.getSelection().add(attachmentsAttribute);
//		Asset story = _services.retrieve(query).getAssets()[0];
//
//		assertEquals(1, story.getAttribute(attachmentsAttribute).getValues().length);
//	 }
//	 
//	 @Test
//	 public void CreateStoryWithEmbeddedImage() throws V1Exception, IOException {
//
//		String file = "com/versionone/apiclient/versionone.png";
//		assertNotNull("Test file missing", Thread.currentThread().getContextClassLoader().getResource(file));
//		String mimeType = MimeType.resolve(file);
//
//		V1Connector connector = V1Connector
//				.withInstanceUrl(_instanceUrl)
//				.withUserAgentHeader("JavaSDKIntegrationTests", "1.0")
//				.withAccessToken(_accessToken)
//				.useEndpoint("embedded.img/")
//				.build();
//		
//		IAttachments attachments = new Attachments(connector);
//		
//		IAssetType storyType = _services.getMeta().getAssetType("Story");
//		Asset newStory = _services.createNew(storyType, _projectId);
//		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
//		IAttributeDefinition descriptionAttribute = storyType.getAttributeDefinition("Description");
//		String name = "Test Story " + _projectId + " Create story with embedded image";
//		newStory.setAttributeValue(nameAttribute, name);
//		newStory.setAttributeValue(descriptionAttribute, "Test description");
//		_services.save(newStory);
//
//		IAssetType embeddedImageType = _services.getMeta().getAssetType("EmbeddedImage");
//		
//		
//		Asset newEmbeddedImage = _services.createNew(embeddedImageType, Oid.Null);
//		IAttributeDefinition assetAttribute = embeddedImageType.getAttributeDefinition("Asset");
//		IAttributeDefinition contentAttribute = embeddedImageType.getAttributeDefinition("Content");
//		IAttributeDefinition contentTypeAttribute = embeddedImageType.getAttributeDefinition("ContentType");
//		newEmbeddedImage.setAttributeValue(assetAttribute, newStory.getOid());
//		newEmbeddedImage.setAttributeValue(contentTypeAttribute, mimeType);
//		newEmbeddedImage.setAttributeValue(contentAttribute, "");
//		_services.save(newEmbeddedImage);
//
//		String key = newEmbeddedImage.getOid().getKey().toString();
//	     FileInputStream inStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource(file).getPath());
//	     OutputStream output =  attachments.getWriter(key, mimeType);
//	     byte[] buffer = new byte[inStream.available() + 1];
//	     while (true){
//	         int read = inStream.read(buffer, 0, buffer.length);
//	         if (read <= 0)
//	             break;
//	    
//	         output.write(buffer, 0, read);
//	         }
//	     
//	    attachments.setWriter(key);
//	    inStream.close();
//
//		Oid embeddedImageOid = _services.saveEmbeddedImage(file, newStory);
//        String embeddedImageTag = String.format("<img src=\""+embeddedImageOid.getKey()+"\" alt=\"\" data-oid="+embeddedImageOid.getMomentless() +" />", "embedded.img/" );
//        newStory.setAttributeValue(descriptionAttribute, embeddedImageTag);
//        _services.save(newStory);
//
//        Query query = new Query(embeddedImageOid);
//	    assertEquals(1, _services.retrieve(query).getAssets().length);
//
//	    Query queryData = new Query(newStory.getOid());
//	    queryData.getSelection().add(descriptionAttribute);
//	    Asset defect = _services.retrieve(queryData).getAssets()[0];
//
//	    assertNotNull(defect);
//	    assertTrue(defect.getAttribute(descriptionAttribute).getValue().toString().contains(embeddedImageTag));
//	 }
	 
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
		
//	@Test
//	public void CreateStoryWithEmbeddedImageWithOauthTest() throws V1Exception, IOException {
//			
//		String file = "com/versionone/apiclient/versionone.png";
//		assertNotNull("Test file missing", Thread.currentThread().getContextClassLoader().getResource(file));
//		
//		IAssetType storyType = _services.getMeta().getAssetType("Story");
//		Asset newStory = _services.createNew(storyType, _projectId);
//		IAttributeDefinition storyNameAttribute = storyType.getAttributeDefinition("Name");
//		IAttributeDefinition descriptionAttribute = storyType.getAttributeDefinition("Description");
//		String name = "Test Story " + _projectId + "Create story with embedded image";
//		newStory.setAttributeValue(storyNameAttribute, name);
//		_services.save(newStory);
//		
//		Oid embeddedImageOid = _services.saveEmbeddedImage(Thread.currentThread().getContextClassLoader().getResource(file).getPath(), newStory);
//		
//        String embeddedImageTag = String.format("<img src=\"embedded.img/"+embeddedImageOid.getKey()+"\" alt=\"\" data-oid=\""+embeddedImageOid.getMomentless()+"\" />", "embedded.img/");
//        newStory.setAttributeValue(descriptionAttribute, embeddedImageTag);
//        _services.save(newStory);
//
//        Query query = new Query(embeddedImageOid);
//        assertEquals(1, _services.retrieve(query).getAssets().length);
//
//        Query queryData = new Query(newStory.getOid());
//        queryData.getSelection().add(descriptionAttribute);
//        Asset defect = _services.retrieve(queryData).getAssets()[0];
//
//        assertNotNull(defect);
//        assertTrue(defect.getAttribute(descriptionAttribute).getValue().toString().contains(embeddedImageTag));	
//	}
	 
}
