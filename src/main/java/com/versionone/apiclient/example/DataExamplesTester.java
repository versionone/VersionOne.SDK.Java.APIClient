package com.versionone.apiclient.example;

import java.util.Date;

import com.versionone.apiclient.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.versionone.Oid;

public class DataExamplesTester {
    private DataExamples docExamples;

    @Before
    public void SetUp() {
        docExamples = new DataExamples();
    }

    @Test
    public void TestSingleAsset() throws Exception {
        Asset member = docExamples.SingleAsset();
        Assert.assertNotNull(member);
        Assert.assertEquals("Member:20", member.getOid().getToken());
    }

    @Test
    public void TestSingleAssetWithAttributes() throws Exception {
        Asset member = docExamples.SingleAssetWithAttributes();
        Assert.assertNotNull(member);
        Assert.assertEquals("Member:20", member.getOid().getToken());
        Assert.assertEquals(2, member.getAttributes().size());
    }

    @Test
    public void TestListOfAssets() throws Exception {
        Asset[] stories = docExamples.ListOfAssets();
        Assert.assertNotNull(stories);
        Assert.assertTrue(stories.length > 0);
    }

    @Test
    public void TestFilterListOfAssets() throws Exception {
        Asset[] tasks = docExamples.FilterListOfAssets();
        Assert.assertNotNull(tasks);
        Assert.assertTrue(tasks.length > 0);
    }

    @Test
    public void TestSortListOfAssets() throws Exception {
        Asset[] stories = docExamples.SortListOfAssets();
        Assert.assertNotNull(stories);
        Assert.assertTrue(stories.length > 0);
    }

    @Test
    public void TestPageListOfAssets() throws Exception {
        Asset[] stories = docExamples.PageListOfAssets();
        Assert.assertNotNull(stories);
        Assert.assertEquals(3, stories.length);
    }

    @Test
    public void TestHistorySingleAsset() throws Exception {
        Asset[] members = docExamples.HistorySingleAsset();
        Assert.assertNotNull(members);
        Assert.assertTrue(members.length > 0);
    }

    @Test
    public void TestHistoryListOfAssets() throws Exception {
        Asset[] members = docExamples.HistoryListOfAssets();
        Assert.assertNotNull(members);
        Assert.assertTrue(members.length > 0);
    }

    @Test
    public void TestUpdateScalarAttribute() throws Exception {
        Asset story = docExamples.UpdateScalarAttribute();
        Assert.assertNotNull(story);

        Query query = new Query(Oid.fromToken(story.getOid().getMomentless().getToken(), docExamples.getMetaModel()));
        IAssetType storyType = docExamples.getMetaModel().getAssetType("Story");
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        query.getSelection().add(nameAttribute);
        QueryResult result = docExamples.getServices().retrieve(query);
        Asset actualStory = result.getAssets()[0];

        Assert.assertEquals(story.getAttribute(nameAttribute).getValue(), actualStory.getAttribute(nameAttribute).getValue());
    }

    @Test
    public void TestUpdateSingleValueRelation() throws Exception {
        Asset story = docExamples.UpdateSingleValueRelation();
        Assert.assertNotNull(story);

        Query query = new Query(Oid.fromToken(story.getOid().getMomentless().getToken(), docExamples.getMetaModel()));
        IAssetType storyType = docExamples.getMetaModel().getAssetType("Story");
        IAttributeDefinition sourceAttribute = storyType.getAttributeDefinition("Source");
        query.getSelection().add(sourceAttribute);
        QueryResult result = docExamples.getServices().retrieve(query);
        Asset actualStory = result.getAssets()[0];
        String expectedSource = story.getAttribute(sourceAttribute).getValue().toString();
        String actualSource = actualStory.getAttribute(sourceAttribute).getValue().toString();
        Assert.assertEquals(expectedSource, actualSource);
    }

    @Test
    public void TestUpdateMultiValueRelation() throws Exception {
        Asset story = docExamples.UpdateMultiValueRelation();
        Assert.assertNotNull(story);

        Query query = new Query(Oid.fromToken(story.getOid().getMomentless().getToken(), docExamples.getMetaModel()));
        IAssetType storyType = docExamples.getMetaModel().getAssetType("Story");
        IAttributeDefinition ownersAttribute = storyType.getAttributeDefinition("Owners");
        query.getSelection().add(ownersAttribute);
        QueryResult result = docExamples.getServices().retrieve(query);
        Asset actualStory = result.getAssets()[0];
        String expectedOwners = story.getAttribute(ownersAttribute).getValues()[0].toString();
        String actualOwners = actualStory.getAttribute(ownersAttribute).getValues()[0].toString();
        Assert.assertEquals(expectedOwners, actualOwners);
    }

    @Test
    public void TestAddNewAsset() throws Exception {
        Asset story = docExamples.AddNewAsset();
        Assert.assertNotNull(story);

        Query query = new Query(Oid.fromToken(story.getOid().getMomentless().getToken(), docExamples.getMetaModel()));
        IAssetType storyType = docExamples.getMetaModel().getAssetType("Story");
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        query.getSelection().add(nameAttribute);
        QueryResult result = docExamples.getServices().retrieve(query);
        Asset actualStory = result.getAssets()[0];
        Assert.assertNotNull(actualStory);
        Assert.assertEquals(story.getAttribute(nameAttribute).getValue(), actualStory.getAttribute(nameAttribute).getValue());
    }

    @Test
    public void TestDeleteAsset() throws Exception {
        Oid deletedStoryID = docExamples.DeleteAsset();
        IAssetType storyType = docExamples.getMetaModel().getAssetType("Story");
        Query query = new Query(storyType);
        IAttributeDefinition assetState = storyType.getAttributeDefinition("AssetState");
        IAttributeDefinition idAttribute = storyType.getAttributeDefinition("ID");
        IAttributeDefinition isDeleted = storyType.getAttributeDefinition("IsDeleted");
        query.getSelection().add(assetState);

        FilterTerm idTerm = new FilterTerm(idAttribute);
        idTerm.equal(deletedStoryID.getMomentless());

        FilterTerm isDeletedTerm = new FilterTerm(isDeleted);
        isDeletedTerm.equal(true);
        query.setFilter(new AndFilterTerm(idTerm, isDeletedTerm));

        QueryResult result = docExamples.getServices().retrieve(query);
        Asset deletedStory = result.getAssets()[0];
        Assert.assertNotNull(deletedStory);
        Assert.assertEquals(AssetState.Deleted.value(), deletedStory.getAttribute(assetState).getValue());
    }

    @Test
    public void TestInactivateAsset() throws Exception {
        Asset inactiveStory = docExamples.CloseAsset();
        IAttributeDefinition assetState = docExamples.getMetaModel().getAttributeDefinition("Story.AssetState");
        Assert.assertEquals(AssetState.Closed.value(), inactiveStory.getAttribute(assetState).getValue());
    }

    @Test
    public void TestReOpenAsset() throws Exception {
        Asset activeStory = docExamples.ReOpenAsset();
        IAttributeDefinition assetState = docExamples.getMetaModel().getAttributeDefinition("Story.AssetState");
        Assert.assertEquals(AssetState.Active.value(), activeStory.getAttribute(assetState).getValue());
    }

    @Test
    public void createStoryThroughProxyTest() throws Exception {
        String storyName = "proxy story test " + new Date();
        String projectToken = "Scope:0";
        Asset story = docExamples.createStoryThroughProxy(storyName, projectToken);
        IAttributeDefinition nameDef = story.getAssetType().getAttributeDefinition("Name");

        Assert.assertEquals(storyName, story.getAttribute(nameDef).getValue());

        IAssetType storyType = docExamples.getMetaModel().getAssetType("Story");
        Query query = new Query(storyType);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition idAttribute = storyType.getAttributeDefinition("ID");
        query.getSelection().add(nameAttribute);
        FilterTerm idTerm = new FilterTerm(idAttribute);
        idTerm.equal(story.getOid());
        query.setFilter(idTerm);

        QueryResult result = docExamples.getServices().retrieve(query);
        Asset storyWithoutProxy = result.getAssets()[0];

        Assert.assertEquals(storyName, storyWithoutProxy.getAttribute(nameDef).getValue());
    }
}
