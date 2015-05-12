package com.versionone.javasdk.unit.tests;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.interfaces.IAttributeDefinition.AttributeType;
import com.versionone.apiclient.XmlApiWriter;


public class XmlApiWriterTests {

	@Test
	public void testWriteAssetCreate() throws Exception {
		MockAssetType assetType = new MockAssetType("Scope"); 
		MockAttributeDefinition nameDefinition = new MockAttributeDefinition("Name", AttributeType.Text);
		MockAttributeDefinition scopeDefinition = new MockAttributeDefinition("Scope", AttributeType.Relation);
		
		Asset asset = new Asset(assetType);
		asset.setAttributeValue(nameDefinition, "Test Asset");
		asset.setAttributeValue(scopeDefinition, new Oid(assetType));
				
		//NOTE: For some reason the Xml document using the 1.8 version is builded on a different way than the one using the 1.7 version
		//So if this test fails check wich version ar you using and uncomment the correct one

		//String used for JVM 1.7
		//String expected = "<Asset><Attribute act=\"set\" name=\"Name\">Test Asset</Attribute><Relation act=\"set\" name=\"Scope\"><Asset idref=\"Scope:0\"/></Relation></Asset>";
		
		//String used for JVM 1.8
		String expected = "<Asset><Relation act=\"set\" name=\"Scope\"><Asset idref=\"Scope:0\"/></Relation><Attribute act=\"set\" name=\"Name\">Test Asset</Attribute></Asset>";

		StringWriter assetData = new StringWriter();
		XmlApiWriter testMe = new XmlApiWriter(true);
		testMe.write(asset, assetData);
		String data = assetData.toString();
		if(data.startsWith("<?xml")) {
			data = data.substring(data.indexOf("?>")+2);
		}
		
		Assert.assertEquals(expected, data);		
	}
	
	@Test
	public void testWriteDate() throws Exception {
		MockAssetType assetType = new MockAssetType("Task");
		MockAttributeDefinition deadlineDefinition = new MockAttributeDefinition("Deadline", AttributeType.Date);
		Asset asset = new Asset(assetType);
		Date date = createDate();
		asset.setAttributeValue(deadlineDefinition, date);

		String expected = "<Asset><Attribute act=\"set\" name=\"Deadline\">2007-10-30T08:00:00.450</Attribute></Asset>";
		StringWriter assetData = new StringWriter();
		XmlApiWriter testMe = new XmlApiWriter(true);
		testMe.write(asset, assetData);
		String data = assetData.toString();
		if(data.startsWith("<?xml")) {
			data = data.substring(data.indexOf("?>")+2);
		}
		Assert.assertEquals(expected, data);				
	}

	private Date createDate() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(450);
		c.set(2007, 9, 30, 8, 0, 0);
		return c.getTime();
	}
}
