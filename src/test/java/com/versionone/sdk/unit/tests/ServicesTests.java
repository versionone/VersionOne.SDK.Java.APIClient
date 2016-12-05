package com.versionone.sdk.unit.tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.sun.jna.platform.win32.Guid.GUID;
import com.versionone.apiclient.*;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.OidException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.filters.FilterTerm;

import org.apache.commons.lang.NullArgumentException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.services.QueryResult;

import java.net.MalformedURLException;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class ServicesTests extends ServicesTesterBase {

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
	
	@Test
	public void Asset_with_valid_Guid_Attribute() throws ConnectionException, APIException, OidException {
		UUID payloadGuid = UUID.fromString("98771fb4-71b8-42ec-be8b-69414daa020e");

		Services subject = new Services(getMeta(), getDataConnector());
		IAssetType publicationType = getMeta().getAssetType("Publication");
		IAttributeDefinition payloadAttribute = publicationType.getAttributeDefinition("Payload");

		Query query = new Query(publicationType);
		query.getSelection().add(payloadAttribute);
		FilterTerm filter = new FilterTerm(payloadAttribute);
		filter.equal(payloadGuid);
		query.setFilter(filter);

		QueryResult result = subject.retrieve(query);

		UUID payloadFromResult = (UUID) result.getAssets()[0].getAttribute(payloadAttribute).getValue();

		Assert.assertEquals(payloadGuid, payloadFromResult);
		
		result.getAssets()[0].setAttributeValue(payloadAttribute, payloadGuid);
	}

	@Test
	public void Asset_with_null_Guid_Attribute() throws OidException {
		Services subject = new Services(getMeta(), getDataConnector());
		Query query = new Query(Oid.fromToken("Publication:12346", getMeta()));

		IAssetType publicationType = getMeta().getAssetType("Publication");
		IAttributeDefinition payloadAttribute = publicationType
				.getAttributeDefinition("Payload");
		query.getSelection().add(payloadAttribute);

		try {
			subject.retrieve(query);
		} catch (NullArgumentException ex) {
			Assert.assertEquals(
					"value must not be null.",
					ex.getMessage());
			return;
		} catch (Exception ex) {
			Assert.fail("Expected to raise NullArgumentException, but did not.");
		}
	}
	 
}
