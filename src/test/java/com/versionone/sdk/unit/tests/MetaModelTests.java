package com.versionone.sdk.unit.tests;

import com.versionone.apiclient.Services;
import com.versionone.apiclient.interfaces.IMetaModel;
import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IOperation;
import com.versionone.utils.Version;

public class MetaModelTests extends MetaTestBase {

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
