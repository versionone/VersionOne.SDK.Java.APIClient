package com.versionone.apiclient.tests;

import com.versionone.Oid;
import com.versionone.apiclient.*;
import org.junit.*;

import java.util.Collection;
import java.util.LinkedList;

@Ignore("These tests need VersionOne instance to run; several stories will be created and deleted at the end of test run.")
public class QueryIntegrationTester {
    private static final String V1Url = "http://integsrv01/VersionOneSDK";
    private static final String Username = "admin";
    private static final String Password = "admin";

    private final String InitialStoryName = "Initial name";
    private final String ChangedStoryName = "Changed name";
    private final String FinalStoryName = "Final name";

    private static IMetaModel metaModel;
    private static IServices services;
    private static IAssetType storyType;
    private static IOperation storyDeleteOperation;

    private static IAttributeDefinition nameDef;
    private static IAttributeDefinition scopeDef;
    private static IAttributeDefinition momentDef;
    private static Collection<IAttributeDefinition> attributesToQuery;

    private final Collection<Asset> assetsToDispose = new LinkedList<Asset>();

    @BeforeClass
    public static void beforeClass() {
        V1APIConnector metaConnector = new V1APIConnector(V1Url + "/meta.v1/");
        ClientConfiguration config = new ClientConfiguration(V1Url, Username, Password);
        V1APIConnector dataConnector = new V1APIConnector(V1Url + "/rest-1.v1/", config);
        metaModel = new MetaModel(metaConnector);
        services = new Services(metaModel, dataConnector);

        storyType = metaModel.getAssetType("Story");
        storyDeleteOperation = storyType.getOperation("Delete");

        nameDef = storyType.getAttributeDefinition("Name");
        scopeDef = storyType.getAttributeDefinition("Scope");
        momentDef = storyType.getAttributeDefinition("Moment");
        attributesToQuery = new LinkedList<IAttributeDefinition>();
        attributesToQuery.add(nameDef);
        attributesToQuery.add(scopeDef);
        attributesToQuery.add(momentDef);
    }

    @After
    public void after() throws Exception {
        for(Asset story: assetsToDispose) {
            services.executeOperation(storyDeleteOperation, story.getOid());
        }
    }

    @Test
    public void queryStoryByMoment() throws Exception {
        Asset storyAsset = createDisposableStory();
        storyAsset.setAttributeValue(nameDef, InitialStoryName);
        storyAsset.setAttributeValue(scopeDef, Oid.fromToken("Scope:0", metaModel));
        services.save(storyAsset);

        storyAsset = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery)[0];
        Object moment = storyAsset.getAttribute(momentDef).getValue();
        Assert.assertEquals(storyAsset.getAttribute(nameDef).getValue(), InitialStoryName);

        storyAsset.setAttributeValue(nameDef, ChangedStoryName);
        services.save(storyAsset);

        storyAsset = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery)[0];
        Assert.assertEquals(storyAsset.getAttribute(nameDef).getValue(), ChangedStoryName);

        FilterTerm filter = new FilterTerm(momentDef);
        filter.equal(moment);
        storyAsset = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery, filter, false)[0];
        Assert.assertEquals(storyAsset.getAttribute(nameDef).getValue(), ChangedStoryName);
    }

    @Test
    public void queryStoryHistoryByMoment() throws Exception {
        Asset storyAsset = createDisposableStory();
        storyAsset.setAttributeValue(nameDef, InitialStoryName);
        storyAsset.setAttributeValue(scopeDef, Oid.fromToken("Scope:0", metaModel));
        services.save(storyAsset);

        storyAsset = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery)[0];
        Object moment = storyAsset.getAttribute(momentDef).getValue();
        Assert.assertEquals(storyAsset.getAttribute(nameDef).getValue(), InitialStoryName);

        storyAsset.setAttributeValue(nameDef, ChangedStoryName);
        services.save(storyAsset);

        storyAsset = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery)[0];
        Assert.assertEquals(storyAsset.getAttribute(nameDef).getValue(), ChangedStoryName);

        FilterTerm filter = new FilterTerm(momentDef);
        filter.equal(moment);
        storyAsset = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery, filter, true)[0];
        Assert.assertEquals(storyAsset.getAttribute(nameDef).getValue(), InitialStoryName);
    }

    @Test
    public void queryStoryChangesWithInequalityFilter() throws Exception {
        Asset storyAsset = createDisposableStory();
        storyAsset.setAttributeValue(nameDef, InitialStoryName);
        storyAsset.setAttributeValue(scopeDef, Oid.fromToken("Scope:0", metaModel));
        services.save(storyAsset);

        storyAsset = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery)[0];
        Object moment1 = storyAsset.getAttribute(momentDef).getValue();

        storyAsset.setAttributeValue(nameDef, ChangedStoryName);
        services.save(storyAsset);

        storyAsset.setAttributeValue(nameDef, FinalStoryName);
        services.save(storyAsset);
        storyAsset = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery)[0];
        Object moment3 = storyAsset.getAttribute(momentDef).getValue();

        FilterTerm filter = new FilterTerm(momentDef);
        filter.greaterOrEqual(moment1);
        Asset[] assets = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery, filter, true);
        Assert.assertEquals(3, assets.length);
        Assert.assertTrue(nameMatch(InitialStoryName, assets));
        Assert.assertTrue(nameMatch(ChangedStoryName, assets));
        Assert.assertTrue(nameMatch(FinalStoryName, assets));

        filter = new FilterTerm(momentDef);
        filter.greater(moment1);
        assets = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery, filter, true);
        Assert.assertEquals(2, assets.length);
        Assert.assertFalse(nameMatch(InitialStoryName, assets));
        Assert.assertTrue(nameMatch(ChangedStoryName, assets));
        Assert.assertTrue(nameMatch(FinalStoryName, assets));

        FilterTerm lessFilter = new FilterTerm(momentDef);
        lessFilter.less(moment3);
        FilterTerm greaterFilter = new FilterTerm(momentDef);
        greaterFilter.greater(moment1);
        GroupFilterTerm groupFilter = new AndFilterTerm(lessFilter, greaterFilter);
        assets = getAssetsByOid(storyAsset.getOid().getMomentless(), attributesToQuery, groupFilter, true);
        Assert.assertEquals(1, assets.length);
        Assert.assertFalse(nameMatch(InitialStoryName, assets));
        Assert.assertTrue(nameMatch(ChangedStoryName, assets));
        Assert.assertFalse(nameMatch(FinalStoryName, assets));
    }

    private boolean nameMatch(String name, Asset[] assets) throws Exception {
        for(Asset asset : assets) {
            String assetName = asset.getAttribute(nameDef).getValue().toString();

            if(name.equals(assetName)) {
                return true;
            }
        }
        
        return false;
    }
    
    private Asset createDisposableStory() {
        Asset story = new Asset(storyType);
        assetsToDispose.add(story);
        return story;
    }

    private Asset[] getAssetsByOid(Oid oid, Collection<IAttributeDefinition> attributesToQuery) throws Exception {
        return getAssetsByOid(oid, attributesToQuery, null, false);
    }
    
    private Asset[] getAssetsByOid(Oid oid, Collection<IAttributeDefinition> attributesToQuery, IFilterTerm filter, boolean historicalQuery) throws Exception {
        Query query = new Query(oid, historicalQuery);
        query.getSelection().addAll(attributesToQuery);

        if(filter != null) {
            query.setFilter(filter);
        }

        return services.retrieve(query).getAssets();
    }
}
