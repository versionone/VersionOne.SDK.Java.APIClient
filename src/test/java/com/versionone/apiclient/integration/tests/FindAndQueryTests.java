package com.versionone.apiclient.integration.tests;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.AndFilterTerm;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.FilterTerm;
import com.versionone.apiclient.GroupFilterTerm;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IFilterTerm;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IServices;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryResult;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1APIConnector;

//TODO:
// Query for single assets
// Query for multiple assets
// Query for relations (select)
// Filter query
// Find query
// Sort query
// Paging query
// Query history
// Query "asof"

public class FindAndQueryTests {

	
	private final static String V1Url= APIClientSuiteIT.getInstanceUrl().getV1Url();
	private final static String V1UserName = APIClientSuiteIT.getInstanceUrl().getV1UserName();
	private final static String V1Password = APIClientSuiteIT.getInstanceUrl().getV1Password();

	
	private static final V1APIConnector metaConnector = new V1APIConnector(V1Url + APIClientSuiteIT.getInstanceUrl().getMetaUrl());
	private static final V1APIConnector dataConnector = new V1APIConnector(V1Url + APIClientSuiteIT.getInstanceUrl().getDataUrl(), V1UserName,
			V1Password);

	private final String InitialStoryName = "Initial name";
	private final String ChangedStoryName = "Changed name";
	private final String FinalStoryName = "Final name";

	private static IMetaModel metaModel;
	private static IServices services;
	private static IServices dataService;
	private static IAssetType storyType;
	// private static IOperation storyDeleteOperation;

	private static IAttributeDefinition nameDef;
	private static IAttributeDefinition scopeDef;
	private static IAttributeDefinition momentDef;
	private static Collection<IAttributeDefinition> attributesToQuery;

	private final Collection<Asset> assetsToDispose = new LinkedList<Asset>();

	@BeforeClass
	public static void beforeClass() {

		
//		V1APIConnector metaConnector = new V1APIConnector(V1Url + APIClientSuiteIT.getInstanceUrl().getMetaUrl());
//		V1APIConnector dataConnector = new V1APIConnector(V1Url + APIClientSuiteIT.getInstanceUrl().getDataUrl(), V1UserName, V1Password);

		metaModel = new MetaModel(metaConnector);
		services = new Services(metaModel, dataConnector);
		storyType = metaModel.getAssetType("Story");
		// storyDeleteOperation = storyType.getOperation("Delete");

		nameDef = storyType.getAttributeDefinition("Name");
		scopeDef = storyType.getAttributeDefinition("Scope");
		momentDef = storyType.getAttributeDefinition("Moment");
		attributesToQuery = new LinkedList<IAttributeDefinition>();
		attributesToQuery.add(nameDef);
		attributesToQuery.add(scopeDef);
		attributesToQuery.add(momentDef);
	}

	// public void after() throws Exception {
	// for(Asset story: assetsToDispose) {
	// services.executeOperation(storyDeleteOperation, story.getOid());
	// }
	// }

	@Test
	public void testQuerySingleAsset() throws Exception {

		try {
			metaModel = new MetaModel(metaConnector);
			dataService = new Services(metaModel, dataConnector);

			Oid memberId = Oid.fromToken("Member:20", metaModel);
			Query query = new Query(memberId);
			IAttributeDefinition nameAttribute = metaModel.getAttributeDefinition("Member.Username");
			query.getSelection().add(nameAttribute);
			QueryResult result = dataService.retrieve(query);

			Assert.assertNotNull(result.getAssets());

			Assert.assertEquals("1 asset", 1, result.getAssets().length);

		} catch (Exception ex) {
			throw ex;
		}
	}

	// Query for attributes (select)
	// @Test
	// @Ignore
	// public void testQueryForAtributtes() throws Exception {
	//
	// initialize();
	//
	// try {
	// dataConnector = new V1APIConnector(dataUrl, V1_USERNAME, V1_PASSWORD);
	// metaConnector = new V1APIConnector(metaUrl);
	//
	// metaModel = new MetaModel(metaConnector);
	// dataService = new Services(metaModel, dataConnector);
	// //not implemented
	//
	// } catch (Exception ex) {
	// throw ex;
	// }
	// }

	@Test
	@Ignore
	public void testQueryStoryByMoment() throws Exception {
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
	@Ignore
	public void testQueryStoryHistoryByMoment() throws Exception {
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
	@Ignore
	public void testQueryStoryChangesWithInequalityFilter() throws Exception {
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
		for (Asset asset : assets) {
			String assetName = asset.getAttribute(nameDef).getValue().toString();

			if (name.equals(assetName)) {
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

	private Asset[] getAssetsByOid(Oid oid, Collection<IAttributeDefinition> attributesToQuery, IFilterTerm filter, boolean historicalQuery)
			throws Exception {
		Query query = new Query(oid, historicalQuery);
		query.getSelection().addAll(attributesToQuery);

		if (filter != null) {
			query.setFilter(filter);
		}

		return services.retrieve(query).getAssets();
	}
}
