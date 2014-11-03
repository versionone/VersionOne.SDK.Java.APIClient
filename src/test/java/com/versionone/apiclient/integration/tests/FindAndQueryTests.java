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
import com.versionone.apiclient.AttributeSelection;
import com.versionone.apiclient.FilterTerm;
import com.versionone.apiclient.GroupFilterTerm;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IFilterTerm;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IServices;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryFind;
import com.versionone.apiclient.QueryResult;
import com.versionone.apiclient.V1Exception;


public class FindAndQueryTests {
	
	private final String InitialStoryName = "Initial name";
	private final String ChangedStoryName = "Changed name";
	private final String FinalStoryName = "Final name";

	private static IMetaModel metaModel;
	private static IServices services;
	private static IAssetType storyType;

	private static IAttributeDefinition nameDef;
	private static IAttributeDefinition scopeDef;
	private static IAttributeDefinition momentDef;
	private static Collection<IAttributeDefinition> attributesToQuery;

	private final Collection<Asset> assetsToDispose = new LinkedList<Asset>();

	@BeforeClass
	public static void beforeClass() {

		metaModel = APIClientSuiteIT.get_metaModel();
		services = APIClientSuiteIT.get_services();
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

	// Query for single assets
	@Test
	public void testQuerySingleAsset() throws Exception {

			Oid memberId = Oid.fromToken("Member:20", metaModel);
			Query query = new Query(memberId);
			IAttributeDefinition nameAttribute = metaModel.getAttributeDefinition("Member.Username");
			query.getSelection().add(nameAttribute);
			QueryResult result = services.retrieve(query);

			Assert.assertNotNull(result.getAssets());

			Assert.assertEquals("1 asset", 1, result.getAssets().length);
	}

	// Query for multiple assets
	@Test
	public void testQueryMultipleAsset() throws Exception {
	      
//			IAssetType storyType = metaModel.getAssetType("Story");
	        Query query = new Query(storyType);
	        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
	        IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
	        query.getSelection().add(nameAttribute);
	        query.getSelection().add(estimateAttribute);
	        QueryResult result = services.retrieve(query);

	        Assert.assertNotNull(result.getAssets());

	        Assert.assertTrue(result.getAssets().length > 1);
	}
	
	// Filter query // Find query
	@Test
	public void testFindInAQuery() throws Exception {

		Asset newStoryUrgent = services.createNew(storyType, APIClientSuiteIT.get_projectId());
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		newStoryUrgent.setAttributeValue(nameAttribute, "FindAndQueryTest: Find in a Query - Urgent story");
		services.save(newStoryUrgent);

		Asset newStory = services.createNew(storyType, APIClientSuiteIT.get_projectId());
		nameAttribute = storyType.getAttributeDefinition("Name");
		newStory.setAttributeValue(nameAttribute, "FindAndQueryTest: Find in a Query - Common story");
		services.save(newStory);
//		query
		IAssetType requestType = metaModel.getAssetType("Story");
		Query query = new Query(requestType);
		query.getSelection().add(nameAttribute);
		QueryResult result = services.retrieve(query);

		Assert.assertTrue(result.getAssets().length>0);
//		find
		AttributeSelection selection = new AttributeSelection();
		selection.add(nameAttribute);
		query.setFind(new QueryFind("Urgent", selection)); 
		result = services.retrieve(query);
		Asset urgentStory = result.getAssets()[0];
		
		Assert.assertEquals("FindAndQueryTest: Find in a Query - Urgent story", urgentStory.getAttribute(nameAttribute).getValue().toString());
	}

	// Sort query
	@Test
	public void testQueryStoryByMoment() throws Exception {
		Asset storyAsset = createDisposableStory();
		storyAsset.setAttributeValue(nameDef, InitialStoryName);
		storyAsset.setAttributeValue(scopeDef, APIClientSuiteIT.get_projectId());
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
	public void testQueryStoryHistoryByMoment() throws Exception {
		Asset storyAsset = createDisposableStory();
		storyAsset.setAttributeValue(nameDef, InitialStoryName);
		storyAsset.setAttributeValue(scopeDef,APIClientSuiteIT.get_projectId());
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
	public void testQueryStoryChangesWithInequalityFilter() throws Exception {
	
		Asset storyAsset = createDisposableStory();
		storyAsset.setAttributeValue(nameDef, InitialStoryName);
		storyAsset.setAttributeValue(scopeDef, APIClientSuiteIT.get_projectId());
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

//	Paging
	@Test
	public void testPageListOfAssets() throws Exception {

		Query query = new Query(storyType);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(estimateAttribute);
        QueryResult result = services.retrieve(query);
        
        Assert.assertTrue(result.getAssets().length > 2 );
       
        query.getPaging().setPageSize(2);
        query.getPaging().setStart(0);
        result = services.retrieve(query);
    
        Assert.assertTrue(result.getAssets().length == 2 );
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

	private Asset createDisposableStory() throws V1Exception {
		Asset story = services.createNew(storyType, APIClientSuiteIT.get_projectId());
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
