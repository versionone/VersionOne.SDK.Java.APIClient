package com.versionone.apiclient.example;

import java.util.Date;

import com.versionone.apiclient.*;
import org.junit.*;

import com.versionone.Oid;

public class DataExamplesTester {

    private DataExamples _docExamples;

    @Before
    public void SetUp() throws Exception {
        _docExamples = new DataExamples();
    }

    @Test
    public void TestStoryAndDefectTrackingLevel() throws Exception {
        _docExamples.StoryAndDefectTrackingLevel();
    }

    @Test
    public void TestHistoryAsOfTime() throws Exception {
        Asset[] assets = _docExamples.HistoryAsOfTime();
        Assert.assertNotNull(assets);
        Assert.assertTrue(assets.length > 0);
    }

    @Test
    public void TestFindListOfAssets() throws Exception {
        Asset[] assets = _docExamples.FindListOfAssets();
        Assert.assertNotNull(assets);
        Assert.assertTrue(assets.length > 0);
    }

    @Test
    public void TestIsEffortTrackingEnabled() throws Exception{
        Assert.assertTrue(_docExamples.IsEffortTrackingEnabled());
    }

    @Test
    public void TestSingleAsset() throws Exception {
        Asset member = _docExamples.SingleAsset();
        Assert.assertNotNull(member);
        Assert.assertEquals("Member:20", member.getOid().getToken());
    }

    @Test
    public void TestSingleAssetWithAttributes() throws Exception {
        Asset member = _docExamples.SingleAssetWithAttributes();
        Assert.assertNotNull(member);
        Assert.assertEquals("Member:20", member.getOid().getToken());
        Assert.assertEquals(2, member.getAttributes().size());
    }

    @Test
    public void TestListOfAssets() throws Exception {
        Asset[] stories = _docExamples.ListOfAssets();
        Assert.assertNotNull(stories);
        Assert.assertTrue(stories.length > 0);
    }

    @Test
    public void TestFilterListOfAssets() throws Exception {
        Asset[] tasks = _docExamples.FilterListOfAssets();
        Assert.assertNotNull(tasks);
        Assert.assertTrue(tasks.length > 0);
    }

    @Test
    public void TestSortListOfAssets() throws Exception {
        Asset[] stories = _docExamples.SortListOfAssets();
        Assert.assertNotNull(stories);
        Assert.assertTrue(stories.length > 0);
    }

    @Test
    public void TestPageListOfAssets() throws Exception {
        Asset[] stories = _docExamples.PageListOfAssets();
        Assert.assertNotNull(stories);
        Assert.assertEquals(3, stories.length);
    }

    @Test
    public void TestHistorySingleAsset() throws Exception {
        Asset[] members = _docExamples.HistorySingleAsset();
        Assert.assertNotNull(members);
        Assert.assertTrue(members.length > 0);
    }

    @Test
    public void TestHistoryListOfAssets() throws Exception {
        Asset[] members = _docExamples.HistoryListOfAssets();
        Assert.assertNotNull(members);
        Assert.assertTrue(members.length > 0);
    }

    @Test
    public void TestUpdateScalarAttribute() throws Exception {
        Asset story = _docExamples.UpdateScalarAttribute();
        Assert.assertNotNull(story);

        Query query = new Query(Oid.fromToken(story.getOid().getMomentless().getToken(), _docExamples.getMetaModel()));
        IAssetType storyType = _docExamples.getMetaModel().getAssetType("Story");
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        query.getSelection().add(nameAttribute);
        QueryResult result = _docExamples.getServices().retrieve(query);
        Asset actualStory = result.getAssets()[0];

        Assert.assertEquals(story.getAttribute(nameAttribute).getValue(), actualStory.getAttribute(nameAttribute).getValue());
    }

    @Test
    public void TestUpdateSingleValueRelation() throws Exception {
        Asset story = _docExamples.UpdateSingleValueRelation();
        Assert.assertNotNull(story);

        Query query = new Query(Oid.fromToken(story.getOid().getMomentless().getToken(), _docExamples.getMetaModel()));
        IAssetType storyType = _docExamples.getMetaModel().getAssetType("Story");
        IAttributeDefinition sourceAttribute = storyType.getAttributeDefinition("Source");
        query.getSelection().add(sourceAttribute);
        QueryResult result = _docExamples.getServices().retrieve(query);
        Asset actualStory = result.getAssets()[0];
        String expectedSource = story.getAttribute(sourceAttribute).getValue().toString();
        String actualSource = actualStory.getAttribute(sourceAttribute).getValue().toString();
        Assert.assertEquals(expectedSource, actualSource);
    }

    @Test
    public void TestUpdateMultiValueRelation() throws Exception {
        Asset story = _docExamples.UpdateMultiValueRelation();
        Assert.assertNotNull(story);

        Query query = new Query(Oid.fromToken(story.getOid().getMomentless().getToken(), _docExamples.getMetaModel()));
        IAssetType storyType = _docExamples.getMetaModel().getAssetType("Story");
        IAttributeDefinition ownersAttribute = storyType.getAttributeDefinition("Owners");
        query.getSelection().add(ownersAttribute);
        QueryResult result = _docExamples.getServices().retrieve(query);
        Asset actualStory = result.getAssets()[0];
        String expectedOwners = story.getAttribute(ownersAttribute).getValues()[0].toString();
        String actualOwners = actualStory.getAttribute(ownersAttribute).getValues()[0].toString();
        Assert.assertEquals(expectedOwners, actualOwners);
    }

    @Test
    public void TestAddNewAsset() throws Exception {
        Asset story = _docExamples.AddNewAsset();
        Assert.assertNotNull(story);

        Query query = new Query(Oid.fromToken(story.getOid().getMomentless().getToken(), _docExamples.getMetaModel()));
        IAssetType storyType = _docExamples.getMetaModel().getAssetType("Story");
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        query.getSelection().add(nameAttribute);
        QueryResult result = _docExamples.getServices().retrieve(query);
        Asset actualStory = result.getAssets()[0];
        Assert.assertNotNull(actualStory);
        Assert.assertEquals(story.getAttribute(nameAttribute).getValue(), actualStory.getAttribute(nameAttribute).getValue());
    }

    @Test
    public void TestDeleteAsset() throws Exception {
        Oid deletedStoryID = _docExamples.DeleteAsset();
        IAssetType storyType = _docExamples.getMetaModel().getAssetType("Story");
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

        QueryResult result = _docExamples.getServices().retrieve(query);
        Asset deletedStory = result.getAssets()[0];
        Assert.assertNotNull(deletedStory);
        Assert.assertEquals(AssetState.Deleted.value(), deletedStory.getAttribute(assetState).getValue());
    }

    @Test
    public void TestInactivateAsset() throws Exception {
        Asset inactiveStory = _docExamples.CloseAsset();
        IAttributeDefinition assetState = _docExamples.getMetaModel().getAttributeDefinition("Story.AssetState");
        Assert.assertEquals(AssetState.Closed.value(), inactiveStory.getAttribute(assetState).getValue());
    }

    @Test
    public void TestReOpenAsset() throws Exception {
        Asset activeStory = _docExamples.ReOpenAsset();
        IAttributeDefinition assetState = _docExamples.getMetaModel().getAttributeDefinition("Story.AssetState");
        Assert.assertEquals(AssetState.Active.value(), activeStory.getAttribute(assetState).getValue());
    }

    @Test @Ignore("Test won't run without a properly configured proxy.")
    public void createStoryThroughProxyTest() throws Exception {
        String storyName = "proxy story test " + new Date();
        String projectToken = "Scope:0";
        Asset story = _docExamples.createStoryThroughProxy(storyName, projectToken);
        IAttributeDefinition nameDef = story.getAssetType().getAttributeDefinition("Name");

        Assert.assertEquals(storyName, story.getAttribute(nameDef).getValue());

        IAssetType storyType = _docExamples.getMetaModel().getAssetType("Story");
        Query query = new Query(storyType);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition idAttribute = storyType.getAttributeDefinition("ID");
        query.getSelection().add(nameAttribute);
        FilterTerm idTerm = new FilterTerm(idAttribute);
        idTerm.equal(story.getOid());
        query.setFilter(idTerm);

        QueryResult result = _docExamples.getServices().retrieve(query);
        Asset storyWithoutProxy = result.getAssets()[0];

        Assert.assertEquals(storyName, storyWithoutProxy.getAttribute(nameDef).getValue());
    }
}
