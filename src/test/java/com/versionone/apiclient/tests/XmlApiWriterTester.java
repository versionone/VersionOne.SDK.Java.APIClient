package com.versionone.apiclient.tests;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.XmlApiWriter;
import com.versionone.apiclient.IAttributeDefinition.AttributeType;


public class XmlApiWriterTester {

	@Test
	public void testWriteAssetCreate() throws Exception {
		MockAssetType assetType = new MockAssetType("Scope"); 
		MockAttributeDefinition nameDefinition = new MockAttributeDefinition("Name", AttributeType.Text);
		MockAttributeDefinition scopeDefinition = new MockAttributeDefinition("Scope", AttributeType.Relation);
		
		Asset asset = new Asset(assetType);
		asset.setAttributeValue(nameDefinition, "Test Asset");
		asset.setAttributeValue(scopeDefinition, new Oid(assetType));
		
		String expected = "<Asset><Attribute act=\"set\" name=\"Name\">Test Asset</Attribute><Relation act=\"set\" name=\"Scope\"><Asset idref=\"Scope:0\"/></Relation></Asset>";

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
