package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IServices;

public class Localization {
	
	private static IServices _services;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Running localization integration tests...");
		_services = APIClientIntegrationTestSuiteIT.get_services();
	}

	@Test
    public void localizationWithSingleAttributeTest() throws V1Exception {

		String assetName = "Defect";
		String attributeName = "Title";
        IAssetType assetType = _services.getMeta().getAssetType(assetName);
        IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");

        String locName = _services.getLocalization(nameAttribute);
        
        assertTrue(StringUtils.isNotBlank(locName));
        assertEquals(attributeName, locName);
    }
	
	@Test
	public void localizationWithKeyTest() throws V1Exception {
		String locName = _services.getLocalization("Epic");
		assertTrue(StringUtils.isNotBlank(locName));
	}
	
	@Test
	public void localizationWithMultipleAttibutesTest() throws ConnectionException {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");

		ArrayList<IAttributeDefinition> attributes = new ArrayList<IAttributeDefinition>(Arrays.asList(nameAttribute, estimateAttribute));
		
		Map<String, String> locData = _services.getLocalization(attributes);

		assertTrue(locData.size() > 0);
		
		String locName = locData.get(nameAttribute.getToken());
		assertTrue(StringUtils.isNotBlank(locName));
	
		String locEstimate = locData.get(estimateAttribute.getToken());
		assertTrue(StringUtils.isNotBlank(locEstimate));
	}
	
}
