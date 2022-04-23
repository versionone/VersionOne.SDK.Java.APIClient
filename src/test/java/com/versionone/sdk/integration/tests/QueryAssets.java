package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.DB.DateTime;
import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Attachments;
import com.versionone.apiclient.Attribute;
import com.versionone.apiclient.AttributeSelection;
import com.versionone.apiclient.MimeType;
import com.versionone.apiclient.Paging;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.OidException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.filters.AndFilterTerm;
import com.versionone.apiclient.filters.FilterTerm;
import com.versionone.apiclient.filters.GroupFilterTerm;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttachments;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IServices;
import com.versionone.apiclient.services.OrderBy;
import com.versionone.apiclient.services.OrderBy.Order;
import com.versionone.apiclient.services.QueryFind;
import com.versionone.apiclient.services.QueryResult;

public class QueryAssets {

	private static IServices _services;
	private static String _instanceUrl;
	private static String _accessToken;
	private static Oid _projectId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Running query assets integration tests...");
		_services = APIClientIntegrationTestSuiteIT.get_services();
		_instanceUrl = APIClientIntegrationTestSuiteIT.get_instanceUrl();
		_accessToken = APIClientIntegrationTestSuiteIT.get_accessToken();
		_projectId = APIClientIntegrationTestSuiteIT.get_projectId();
	}

	@Test(expected = OidException.class)
	public void queryInvalidOid() throws OidException {
		@SuppressWarnings("unused")
		Oid invalidOid = _services.getOid("unknown:007");
	}

	@Test
	public void querySingleAssetTest() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Query single asset";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

		Query query = new Query(newStory.getOid());
		query.getSelection().add(nameAttribute);
		Asset story = _services.retrieve(query).getAssets()[0];

		assertNotNull(story);
		assertTrue(story.getAttribute(nameAttribute).getValue().toString().equals(name));
	}

	@Test(expected = MetaException.class)
	public void queryUnknownSingleAssetTest() {
		@SuppressWarnings("unused")
		IAssetType storyType = _services.getMeta().getAssetType("Unknown");
	}

	@Test
	public void queryMultipleAssetsTest() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Query multiple assets";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);
		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);
		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

		Query query = new Query(storyType);
		query.getSelection().add(nameAttribute);
		QueryResult result = _services.retrieve(query);

		assertTrue(result.getAssets().length > 2);
	}

	@Test
	public void queryAttributesTest() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		IAttributeDefinition benefitsAttribute = storyType.getAttributeDefinition("Benefits");
		IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
		newStory.setAttributeValue(nameAttribute, "Test Story on " + _projectId + " - Name attribute");
		newStory.setAttributeValue(benefitsAttribute, "Test Story on " + _projectId + " - Benefits attribute");
		newStory.setAttributeValue(estimateAttribute, "24");
		_services.save(newStory);

		Query query = new Query(newStory.getOid());
		query.getSelection().add(nameAttribute);
		query.getSelection().add(benefitsAttribute);
		query.getSelection().add(estimateAttribute);

		Asset story = _services.retrieve(query).getAssets()[0];
		assertNotNull(story);

		Attribute nameAttr = story.getAttribute(nameAttribute);
		assertNotNull(nameAttr);
		assertTrue(nameAttr.getValue().toString().equals("Test Story on " + _projectId + " - Name attribute"));

		Attribute benefitsAttr = story.getAttribute(benefitsAttribute);
		assertNotNull(benefitsAttr);
		assertTrue(benefitsAttr.getValue().toString().equals("Test Story on " + _projectId + " - Benefits attribute"));

		Attribute estimateAttr = story.getAttribute(estimateAttribute);
		assertNotNull(estimateAttr);
		assertTrue(estimateAttr.getValue().toString().equals("24.0"));
	}

	@SuppressWarnings("unused")
	@Test(expected = MetaException.class)
	public void queryUnknownAttributeTest() throws V1Exception {
		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition queryUnknowAttribute = storyType.getAttributeDefinition("Unknown");
	}

	@SuppressWarnings("unused")
	@Test
	public void queryRelationsTest() throws V1Exception {
		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Query relations";
		newStory.setAttributeValue(nameAttribute, _projectId);
		IAssetType workitemPriorityType = _services.getMeta().getAssetType("WorkitemPriority");
		Asset newWorkitemPriority = _services.createNew(workitemPriorityType, _projectId);
		newWorkitemPriority.setAttributeValue(workitemPriorityType.getAttributeDefinition("Name"), "Test WorkItemPriority on " + _projectId);
		_services.save(newWorkitemPriority);

		IAttributeDefinition priorityAttribute = storyType.getAttributeDefinition("Priority");
		newStory.setAttributeValue(priorityAttribute, newWorkitemPriority.getOid());
		_services.save(newStory);

		Query query = new Query(newStory.getOid());
		query.getSelection().add(priorityAttribute);
		Asset story = _services.retrieve(query).getAssets()[0];

		assertNotNull(story);

		Attribute workitemPriority = story.getAttribute(priorityAttribute);
		assertNotNull(workitemPriority);
		assertTrue(workitemPriority.getValue().toString().equals(newWorkitemPriority.getOid().toString()));
	}

	@Test
	public void queryFilterTest() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Query filter";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);
		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);
		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, "Another " + name);
		_services.save(newStory);

		Query query = new Query(storyType);
		query.getSelection().add(nameAttribute);
		FilterTerm filterTerm = new FilterTerm(nameAttribute);
		filterTerm.equal(name);
		query.setFilter(filterTerm);
		QueryResult result = _services.retrieve(query);

		assertNotNull(result);
		assertEquals(2l, (long)result.getAssets().length);

		for (Asset asset : result.getAssets()) {
			assertTrue(asset.getAttribute(nameAttribute).getValue().toString().equals(name));
		}
	}

	@Test
	public void queryFilterWithMultipleAttributesTest() throws V1Exception {
		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
		String name = "Test Story " + _projectId + " Query filter with multiple attributes";
		newStory.setAttributeValue(nameAttribute, name);
		newStory.setAttributeValue(estimateAttribute, "24");
		_services.save(newStory);
		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, name);
		newStory.setAttributeValue(estimateAttribute, "2");
		_services.save(newStory);
		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, "Another " + name);
		newStory.setAttributeValue(estimateAttribute, "2");
		_services.save(newStory);

		QueryResult result = queryForCreatedAssets(storyType, nameAttribute, estimateAttribute, name);
		assertNotNull(result);
		if (result.getAssets().length == 0) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new V1Exception("Interrupted while waiting for search index update", e);
			}
			result = queryForCreatedAssets(storyType, nameAttribute, estimateAttribute, name);
		}
		assertEquals(1l, (long)result.getAssets().length);

		for (Asset asset : result.getAssets()) {
			assertTrue(asset.getAttribute(nameAttribute).getValue().toString().equals(name));
		}
		for (Asset asset : result.getAssets()) {
			assertTrue(asset.getAttribute(estimateAttribute).getValue().toString().equals("24.0"));
		}

	}

	private QueryResult queryForCreatedAssets(IAssetType storyType, IAttributeDefinition nameAttribute,
			IAttributeDefinition estimateAttribute, String name)
			throws ConnectionException, APIException, OidException {
		Query query = new Query(storyType);
		query.getSelection().add(nameAttribute);
		query.getSelection().add(estimateAttribute);
		FilterTerm nameFilterTerm = new FilterTerm(nameAttribute);
		nameFilterTerm.equal(name);
		FilterTerm estimateFilterTerm = new FilterTerm(estimateAttribute);
		estimateFilterTerm.equal("24");
		query.setFilter(new AndFilterTerm(nameFilterTerm, estimateFilterTerm));
		QueryResult result = _services.retrieve(query);
		return result;
	}

	@Test
	public void queryFindTest() throws V1Exception, InterruptedException {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");

		String name = _projectId + " Query find test";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, name + " plus one");
		_services.save(newStory);

		Thread.currentThread().sleep(8000);

		Query query = new Query(storyType);
        AttributeSelection selection = new AttributeSelection();
        selection.add(nameAttribute);
        query.setFind(new QueryFind(_projectId + " Query find", selection));
		QueryResult result = _services.retrieve(query);

		assertNotNull(result);
		assertEquals(2l, (long)result.getAssets().length);
	}

	@SuppressWarnings("unused")
	@Test(expected = MetaException.class)
	public void queryFindUnknownAssetTest() {
		Query query = new Query(_services.getMeta().getAssetType("Unknown"));
	}

	@Test
	public void querySortTest() throws V1Exception, InterruptedException {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");

		String name = "Test Query sort " + _projectId;
		newStory.setAttributeValue(nameAttribute, name + " 2");
		_services.save(newStory);

		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, name + " 1");
		_services.save(newStory);

		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, name + " 3");
		_services.save(newStory);

		Thread.currentThread().sleep(8000);

		Query query = new Query(storyType);
		query.getSelection().add(nameAttribute);
        AttributeSelection selection = new AttributeSelection();
        selection.add(nameAttribute);
		query.setFind(new QueryFind(name, selection));
		OrderBy value = new OrderBy();
		value.minorSort(nameAttribute, Order.Ascending);
		query.setOrderBy(value);
		QueryResult result = _services.retrieve(query);

		assertEquals(3l, result.getAssets().length);
		assertTrue(result.getAssets()[0].getAttribute(nameAttribute).getValue().toString().endsWith("1"));
		assertTrue(result.getAssets()[1].getAttribute(nameAttribute).getValue().toString().endsWith("2"));
		assertTrue(result.getAssets()[2].getAttribute(nameAttribute).getValue().toString().endsWith("3"));
	}

	@Test
	public void queryPagingTest() throws V1Exception, InterruptedException {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);

		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Query paging";
		newStory.setAttributeValue(nameAttribute, name + " 1");
		_services.save(newStory);

		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, name + " 2");
		_services.save(newStory);

		newStory = _services.createNew(storyType, _projectId);
		newStory.setAttributeValue(nameAttribute, name + " 3");
		_services.save(newStory);

		// waits for search index
		Thread.currentThread().sleep(12000);

		Query query = new Query(storyType);
        AttributeSelection selection = new AttributeSelection();
        selection.add(nameAttribute);
		query.setFind(new QueryFind(name, selection));
		query.setPaging(new Paging(0, 2));
		QueryResult result = _services.retrieve(query);

		assertTrue(result.getAssets().length == 2);
	}

	@Test
	public void queryHistoryTest() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Query history";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);
		newStory.setAttributeValue(nameAttribute, name + " - updated");
		_services.save(newStory);
		newStory.setAttributeValue(nameAttribute, name + " - updated again");
		_services.save(newStory);

		Query query = new Query(newStory.getOid().getMomentless(), true);
		QueryResult result = _services.retrieve(query);

		assertNotNull(result);
		assertTrue(result.getAssets().length == 3);
	}

	@Test
	public void queryAsofTest() throws V1Exception, InterruptedException {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Query asof";

		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

		newStory.setAttributeValue(nameAttribute, name + " - updated");
		_services.save(newStory);

		newStory.setAttributeValue(nameAttribute, name + " - updated again");
		_services.save(newStory);

		Query query = new Query(storyType);
		query.getSelection().add(nameAttribute);
		query.setAsOf(DateUtils.addMilliseconds(DateTime.now().getValue(), -3500));
		QueryResult result = _services.retrieve(query);

		assertNotNull(result);
		assertTrue(result.getTotalAvaliable() > 0);
	}

	@Test
	public void queryAttachment() throws V1Exception, IOException {
		String file = "com/versionone/apiclient/versionone.png";
		assertNotNull("Test file missing", Thread.currentThread().getContextClassLoader().getResource(file));
		String mimeType = MimeType.resolve(file);

		IAttachments attachments = new Attachments(V1Connector.withInstanceUrl(_instanceUrl).withUserAgentHeader(".NET_SDK_Integration_Test", "1.0")
				.withAccessToken(_accessToken).useEndpoint("attachment.img/").build());

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		IAttributeDefinition attachmentsAttribute = storyType.getAttributeDefinition("Attachments");
		String name = "Test Story " + _projectId + " Query attachment";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

		IAssetType attachmentType = _services.getMeta().getAssetType("Attachment");
		IAttributeDefinition attachmentAssetDef = attachmentType.getAttributeDefinition("Asset");
		IAttributeDefinition attachmentContent = attachmentType.getAttributeDefinition("Content");
		IAttributeDefinition attachmentContentType = attachmentType.getAttributeDefinition("ContentType");
		IAttributeDefinition attachmentFileName = attachmentType.getAttributeDefinition("Filename");
		IAttributeDefinition attachmentName = attachmentType.getAttributeDefinition("Name");
		Asset newAttachment = _services.createNew(attachmentType, Oid.Null);
		newAttachment.setAttributeValue(attachmentName, "Test Attachment on " + newStory.getOid());
		newAttachment.setAttributeValue(attachmentFileName, file);
		newAttachment.setAttributeValue(attachmentContentType, mimeType);
		newAttachment.setAttributeValue(attachmentContent, "");
		newAttachment.setAttributeValue(attachmentAssetDef, newStory.getOid());
		_services.save(newAttachment);
		String key = newAttachment.getOid().getKey().toString();

		FileInputStream inStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource(file).getPath());
		OutputStream output = attachments.getWriter(key, mimeType);
		byte[] buffer = new byte[inStream.available() + 1];
		while (true) {
			int read = inStream.read(buffer, 0, buffer.length);
			if (read <= 0)
				break;
			output.write(buffer, 0, read);
		}
		attachments.setWriter(key);
		inStream.close();

		Query query = new Query(attachmentType);
		query.getSelection().add(attachmentContent);
		Asset attachment = _services.retrieve(query).getAssets()[0];

		assertNotNull(attachment);
		assertNotNull(attachment.getAttribute(attachmentContent).getValue());
	}


	@Test
	public void queryBetweenDates() throws V1Exception, IOException {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Query history";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

	    storyType = _services.getMeta().getAssetType("Story");

	    Query query = new Query(storyType);
	    IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Scope.Name");
	    IAttributeDefinition numberAttribute = storyType.getAttributeDefinition("Number");
	    IAttributeDefinition changeDateAttribute = storyType.getAttributeDefinition("ChangeDate");
	    IAttributeDefinition OwnersAttribute = storyType.getAttributeDefinition("Owners.Name");
	    IAttributeDefinition statusNameAttribute = storyType.getAttributeDefinition("Status.Name");
	    IAttributeDefinition teamNameAttribute = storyType.getAttributeDefinition("Team.Name");
	    IAttributeDefinition priorityNameAttribute = storyType.getAttributeDefinition("Priority.Name");

	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -180);

	    FilterTerm startDateTerm = new FilterTerm(changeDateAttribute);
		startDateTerm.greaterOrEqual(calendar.getTime());
	    FilterTerm endDateTerm = new FilterTerm(changeDateAttribute);
	    endDateTerm.lessOrEqual(new Date());

	    GroupFilterTerm groupFilter = new AndFilterTerm(startDateTerm,endDateTerm);
	    query.setFilter(groupFilter);

	    QueryResult result = null;
	    result = _services.retrieve(query);

	    assertTrue(result.getAssets().length > 0);
	    Asset member = result.getAssets()[0];
	    assertTrue(!member.getOid().isNull());
	}


	@Test
	public void queryDates() throws V1Exception, IOException {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Query history";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

	    storyType = _services.getMeta().getAssetType("Story");

	    Query query = new Query(storyType);
	    IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Scope.Name");
	    IAttributeDefinition numberAttribute = storyType.getAttributeDefinition("Number");
	    IAttributeDefinition changeDateAttribute = storyType.getAttributeDefinition("ChangeDate");
	    IAttributeDefinition OwnersAttribute = storyType.getAttributeDefinition("Owners.Name");
	    IAttributeDefinition statusNameAttribute = storyType.getAttributeDefinition("Status.Name");
	    IAttributeDefinition teamNameAttribute = storyType.getAttributeDefinition("Team.Name");
	    IAttributeDefinition priorityNameAttribute = storyType.getAttributeDefinition("Priority.Name");

	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -180);

	    FilterTerm startDateTerm = new FilterTerm(changeDateAttribute);
		startDateTerm.greater(calendar.getTime());

	    GroupFilterTerm groupFilter = new AndFilterTerm(startDateTerm);
	    query.setFilter(groupFilter);

	    QueryResult result = null;
	    result = _services.retrieve(query);

	    assertTrue(result.getAssets().length > 0);

		FilterTerm endDateTerm = new FilterTerm(changeDateAttribute);
	    endDateTerm.less(new Date());

	    groupFilter = new AndFilterTerm(endDateTerm);
	    query.setFilter(groupFilter);

	    result = null;
	    result = _services.retrieve(query);

	    assertTrue(result.getAssets().length > 0);
	    Asset member = result.getAssets()[0];
	    assertTrue(!member.getOid().isNull());
	}

}
