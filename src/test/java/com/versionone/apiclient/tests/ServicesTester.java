package com.versionone.apiclient.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Attribute;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryResult;
import com.versionone.apiclient.Services;

public class ServicesTester extends ServicesTesterBase {

	@Override
	protected String getServicesTestKeys() {
		return "ServicesTester";
	}

	@Test
	public void SaveNewAssetWithCommentHasCorrectPath() throws Exception {
		Asset myAsset = new Asset(getAssetType("Story"));
		IAttributeDefinition storyNameDef = myAsset.getAssetType().getAttributeDefinition("Name");
		myAsset.setAttributeValue(storyNameDef, "Fred");
		Services subject = new Services(getMeta(), getDataConnector());

		String changeComment = "Expected Change Comment";
		String expectedUpdatePath = String.format("Data/Story?Comment='%s'", changeComment.replace(' ','+'));
		subject.save(myAsset, changeComment);

		Assert.assertEquals(myAsset.getOid().getToken(), "Story:1025");
		Assert.assertEquals(expectedUpdatePath, getDataConnector().getLastPath());
	}

	@Test
	public void OidQueryMultiValueAttribute() throws Exception
	{
		Services subject = new Services(getMeta(), getDataConnector());
		Query q = new Query(Oid.fromToken("Story:1036", getMeta()));
		IAttributeDefinition def = getMeta().getAttributeDefinition("Story.Owners");
		q.getSelection().add(def);
		QueryResult r = subject.retrieve(q);
		Asset a = r.getAssets()[0];
		Attribute attrib = a.getAttribute(def);
		Assert.assertNotNull(attrib);
		Assert.assertEquals(0, attrib.getValues().length);
	}

	@Test
	public void UpdateAssetAttribute() throws Exception {
		Services subject = new Services(getMeta(), getDataConnector());

		IAssetType assetType = getMeta().getAssetType("Story");

		Query q = new Query(Oid.fromToken("Story:1040", getMeta()));
		QueryResult r = subject.retrieve(q);

		Asset asset = r.getAssets()[0];

		IAttributeDefinition nameAttributeDefinition = assetType.getAttributeDefinition("Name");
		asset.setAttributeValue(nameAttributeDefinition, "Fred");

		String expectedUpdateData = "<Asset href=\"/VersionOne/rest-1.v1/Data/Story/1040/1\" id=\"Story:1040:1\"><Attribute name=\"Name\">Fred</Attribute></Asset>";
		String expectedUpdatePath = "Data/Story/1040";
		subject.save(asset);
		Assert.assertEquals(asset.getOid().getToken(), "Story:1040:1");
		Assert.assertEquals(expectedUpdateData, getDataConnector().getLastData());
		Assert.assertEquals(expectedUpdatePath, getDataConnector().getLastPath());
	}

	@Test
	public void UpdateAssetRelationship() throws Exception {
		Services subject = new Services(getMeta(), getDataConnector());

		IAssetType assetType = getMeta().getAssetType("Story");

		Query q = new Query(Oid.fromToken("Story:1063", getMeta()));
		QueryResult r = subject.retrieve(q);

		Asset asset = r.getAssets()[0];
		IAttributeDefinition ownerAttribute=assetType.getAttributeDefinition("Owners");
		asset.addAttributeValue(ownerAttribute, "Member:1004");

		String expectedUpdateData = "<Asset href=\"/VersionOne/rest-1.v1/Data/Story/1063/1\" id=\"Story:1063:1\"><Relation name=\"Owners\"><Asset href=\"/VersionOne/rest-1.v1/Data/Member/1004\" idref=\"Member:1004\"></Asset></Relation></Asset>";
		String expectedUpdatePath = "Data/Story/1063";
		subject.save(asset);
		Assert.assertEquals(asset.getOid().getToken(), "Story:1063:1");
		Assert.assertEquals(expectedUpdateData, getDataConnector().getLastData());
		Assert.assertEquals(expectedUpdatePath, getDataConnector().getLastPath());
	}

	@Test
	public void TestHasChangedOnMultiValueRemove() throws Exception {

		Services subject = new Services(getMeta(), getDataConnector());

        Oid storyId = Oid.fromToken("Story:1064", getMeta());
        Query query = new Query(storyId);
        IAssetType storyType = getMeta().getAssetType("Story");
        IAttributeDefinition ownersAttribute = storyType.getAttributeDefinition("Owners");
        query.getSelection().add(ownersAttribute);
        QueryResult result = subject.retrieve(query);

        Assert.assertEquals(1, result.getTotalAvaliable());

        Asset story = result.getAssets()[0];
        Assert.assertFalse(story.hasChanged());

        story.removeAttributeValue(ownersAttribute, "Member:1001");
        Assert.assertTrue(story.hasChanged());
	}

	@Test
	public void TestHasChangedOnMultiValueAdd() throws Exception {

		Services subject = new Services(getMeta(), getDataConnector());

        Oid storyId = Oid.fromToken("Story:1064", getMeta());
        Query query = new Query(storyId);
        IAssetType storyType = getMeta().getAssetType("Story");
        IAttributeDefinition ownersAttribute = storyType.getAttributeDefinition("Owners");
        query.getSelection().add(ownersAttribute);
        QueryResult result = subject.retrieve(query);

        Assert.assertEquals(1, result.getTotalAvaliable());

        Asset story = result.getAssets()[0];
        Assert.assertFalse(story.hasChanged());

        story.addAttributeValue(ownersAttribute, "Member:1002");
        Assert.assertTrue(story.hasChanged());
	}

	@Test
	public void TestHasChangedOnAttributeSet() throws Exception {
		Services subject = new Services(getMeta(), getDataConnector());

		IAssetType assetType = getMeta().getAssetType("Story");

		Query q = new Query(Oid.fromToken("Story:1040", getMeta()));
		QueryResult r = subject.retrieve(q);

		Asset asset = r.getAssets()[0];
		Assert.assertFalse(asset.hasChanged());

		IAttributeDefinition nameAttributeDefinition = assetType.getAttributeDefinition("Name");
		asset.setAttributeValue(nameAttributeDefinition, "Fred");
		Assert.assertTrue(asset.hasChanged());
	}

}
