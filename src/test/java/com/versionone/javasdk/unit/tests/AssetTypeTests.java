package com.versionone.javasdk.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;

public class AssetTypeTests extends MetaTestBase {

	@Override
	protected String getMetaTestKeys() {
		return "AssetTypeTester";
	}
	
	private IAssetType getListType() throws MetaException {return getAssetType("List");}
	private IAssetType getStateType() throws MetaException { return getAssetType("State"); }
	private IAssetType getWorkitemType() throws MetaException { return getAssetType("Workitem"); }
	private IAssetType getStoryType() throws MetaException { return getAssetType("Story"); }
	private IAssetType getTaskType() throws MetaException { return getAssetType("Task"); }
	
	@Test public void Story() throws V1Exception
	{
		IAssetType story = getMeta().getAssetType("Story");
		Assert.assertEquals("Story",story.getToken());
		Assert.assertEquals("AssetType'Story",story.getDisplayName());
		Assert.assertEquals("PrimaryWorkitem",story.getBase().getToken());
		Assert.assertEquals("Story.Order",story.getDefaultOrderBy().getToken());
	}
	
	@Test public void FailLookupAttribute() throws V1Exception
	{
		try {
			getStoryType().getAttributeDefinition("Blah");
			Assert.fail();
		}
		catch(MetaException e) {
			Assert.assertEquals("Unknown AttributeDefinition: Story.Blah", e.getMessage());
		}
	}
	
	@Test public void LookupAttribute() throws V1Exception
	{
		getStoryType().getAttributeDefinition("Name");
	}
	
	@Test public void CachedAttribute() throws V1Exception
	{
		getStoryType().getAttributeDefinition("Order");
	}
	
	@Test public void BaseAsset() throws V1Exception
	{
		IAssetType baseasset = getMeta().getAssetType("BaseAsset");
		Assert.assertEquals("BaseAsset",baseasset.getToken());
		Assert.assertEquals("AssetType'BaseAsset",baseasset.getDisplayName());			
		Assert.assertNull(baseasset.getBase());
		Assert.assertEquals("BaseAsset.Name", baseasset.getDefaultOrderBy().getToken());
	}
	
	@Test public void InvalidAssetType()
	{
		try {
			getMeta().getAssetType("Blah");
		}
		catch(MetaException e) {
			Assert.assertEquals("Unknown AssetType: Blah", e.getMessage());
		}
	}

	@Test public void ClassIsSelf() throws V1Exception
	{
		Assert.assertTrue(getListType().isA(getListType()));
	}

	@Test public void TypeIsSelf() throws V1Exception
	{
		Assert.assertTrue(getStateType().isA(getStateType()));
	}

	@Test public void TypeIsClass() throws V1Exception
	{
		Assert.assertTrue(getStoryType().isA(getWorkitemType()));
	}

	@Test public void ClassIsNotType() throws V1Exception
	{
		Assert.assertFalse(getWorkitemType().isA(getStoryType()));
	}

	@Test public void TypeIsNotPeerType() throws V1Exception
	{
		Assert.assertFalse(getStoryType().isA(getTaskType()));
	}

	@Test public void TypeIsNotClass() throws V1Exception
	{
		Assert.assertFalse(getStateType().isA(getWorkitemType()));
	}

	@Test public void TypeIsNotType() throws V1Exception
	{
		Assert.assertFalse(getStateType().isA(getStoryType()));
	}		

}
