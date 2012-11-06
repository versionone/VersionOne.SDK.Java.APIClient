package com.versionone.apiclient.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.FileAPIConnector;

public class FileAPIConnectorTester {

	@Test
	public void testGet() throws Exception {
		String expectedResult = "<Assets total=\"1\" pageSize=\"2147483647\" pageStart=\"0\">\n\t\t\t\t<Asset href=\"/VersionOne.Web/rest-1.v1/Data/Story/1042\" id=\"Story:1042\">\n\t\t\t\t\t<Attribute name=\"Name\">Story</Attribute>\n\t\t\t\t</Asset>\n\t\t\t</Assets>";
		String expectedRequest = "rest-1.v1/Data/Story?sel=Name";
		
		FileAPIConnector connector = new FileAPIConnector("testdata/SingleTestData.xml", "rest-1.v1/Data");
		connector.getData("Story?sel=Name");
		
		Assert.assertEquals(expectedRequest, connector.getLastPath());
		Assert.assertEquals(expectedResult, connector.getLastData());		
	}
	
	@Test
	public void testPost() throws Exception {

		String expectedResult = "<Asset href=\"/VersionOne/rest-1.v1/Data/Story/1072/214\" id=\"Story:1072:214\">\n\t\t\t\t\t<Attribute name=\"Name\">New Story</Attribute>\n\t\t\t\t\t<Relation name=\"Scope\">\n\t\t\t\t\t\t<Asset href=\"/VersionOne.Web/rest-1.v1/Data/Scope/0\" idref=\"Scope:0\" />\n\t\t\t\t\t</Relation>\n\t\t\t\t</Asset>";	
		String postData = "<Asset><Attribute name=\"Name\" act=\"set\">New Story</Attribute><Relation name=\"Scope\" act=\"set\"><Asset idref=\"Scope:0\"/></Relation></Asset>";
		String expectedRequest = "rest-1.v1/Data/Story";
		
		FileAPIConnector connector = new FileAPIConnector("testdata/SingleTestData.xml", "rest-1.v1/Data");
		connector.sendData("Story", postData);
		
		Assert.assertEquals(expectedRequest, connector.getLastPath());
		Assert.assertEquals(expectedResult, connector.getLastData());		
	}
	
}
