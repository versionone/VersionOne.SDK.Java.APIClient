package com.versionone.apiclient.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IOperation;
import com.versionone.apiclient.MetaException;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.V1Exception;
import com.versionone.apiclient.Version;

public class MetaModelTester extends MetaTesterBase {

	@Override
	protected String getMetaTestKeys() {
		return "MetaModelTester";
	}
	
	@Override
	protected boolean preLoadMetaModel() {
		return true;
	}

	@Test
	public void VersionCheck() throws V1Exception
	{
		Assert.assertEquals(new Version("1.2.3.4"),((MetaModel)getMeta()).getVersion());
	}
	
	@Test public void LoadedAssetTypes() throws MetaException
	{
		IAssetType type = getMeta().getAssetType("Story");
		Assert.assertEquals("Story",type.getToken());
	}
	
	@Test public void LoadedAttributes() throws V1Exception
	{
		IAttributeDefinition def = getMeta().getAttributeDefinition("Story.Name");
		Assert.assertEquals("Story.Name",def.getToken());
		Assert.assertEquals("Name",def.getName());
	}		
	
	@Test public void LoadedOperations() throws MetaException
	{
		IOperation op = getMeta().getOperation("Story.Copy");
		Assert.assertEquals("Story.Copy",op.getToken());
	}
}
