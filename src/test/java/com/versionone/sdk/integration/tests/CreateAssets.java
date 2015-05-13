package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang.time.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.DB.DateTime;
import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IOperation;
import com.versionone.apiclient.interfaces.IServices;
import com.versionone.apiclient.services.QueryResult;

public class CreateAssets {
	
	private static IServices _services;
	private static Oid _projectId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Running create assets integration tests...");
		_services = APIClientIntegrationTestSuiteIT.get_services();
		_projectId = APIClientIntegrationTestSuiteIT.get_projectId();
	}

	@Test
	public void createEpic() throws V1Exception {

		IAssetType epicType = _services.getMeta().getAssetType("Epic");
		Asset newEpic = null;
		newEpic = _services.createNew(epicType, _projectId);
		IAttributeDefinition nameAttribute = epicType.getAttributeDefinition("Name");
		String name = "Test Epic " + _projectId + " Create epic";
		newEpic.setAttributeValue(nameAttribute, name);
		_services.save(newEpic);

		assertNotNull(newEpic.getOid());
	}

	@Test
	public void createEpicWithNestedStoryTest() throws V1Exception {

		IAssetType epicType = _services.getMeta().getAssetType("Epic");
		Asset newEpic = _services.createNew(epicType, _projectId);
		IAttributeDefinition epicNameAttribute = epicType.getAttributeDefinition("Name");
		IOperation generateSubStoryOperation = epicType.getOperation("GenerateSubStory");
		String name = "Test Epic " + _projectId + " Create epic with nested story";
		newEpic.setAttributeValue(epicNameAttribute, name);

		_services.save(newEpic);
		_services.executeOperation(generateSubStoryOperation, newEpic.getOid());
		assertNotNull(newEpic);

		Query query = new Query(newEpic.getOid().getMomentless());
		IAttributeDefinition subsAttributeDefinition = epicType.getAttributeDefinition("Subs");
		query.getSelection().add(subsAttributeDefinition);
		QueryResult result = _services.retrieve(query);
		assertEquals(1, result.getTotalAvaliable());
	}

	@Test
	public void createStoryTest() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Story ";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);
		assertNotNull(newStory);
	}

	@Test
	public void createStoryWithConversation() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _projectId + " Create story with conversation";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

		IAssetType conversationType = _services.getMeta().getAssetType("Conversation");
		IAssetType expressionType = _services.getMeta().getAssetType("Expression");
		IAttributeDefinition authorAttribute = expressionType.getAttributeDefinition("Author");
		IAttributeDefinition authoredAtAttribute = expressionType.getAttributeDefinition("AuthoredAt");
		IAttributeDefinition contentAttribute = expressionType.getAttributeDefinition("Content");
		IAttributeDefinition belongsToAttribute = expressionType.getAttributeDefinition("BelongsTo");
		IAttributeDefinition inReplyToAttribute = expressionType.getAttributeDefinition("InReplyTo");
		IAttributeDefinition mentionsAttribute = expressionType.getAttributeDefinition("Mentions");
		Asset newConversation = _services.createNew(conversationType, null);
		Asset questionExpression = _services.createNew(expressionType, null);
		_services.save(newConversation);

		questionExpression.setAttributeValue(authorAttribute, _services.getOid("Member:20"));
		questionExpression.setAttributeValue(authoredAtAttribute, DateTime.now());
		questionExpression.setAttributeValue(contentAttribute, "Is this a test conversation?");
		questionExpression.setAttributeValue(belongsToAttribute, newConversation.getOid());
		questionExpression.addAttributeValue(mentionsAttribute, newStory.getOid());
		_services.save(questionExpression);

		Query query = new Query(questionExpression.getOid().getMomentless());
		IAttributeDefinition subsAttributeDefinition = questionExpression.getAssetType().getAttributeDefinition("Content");
		query.getSelection().add(subsAttributeDefinition);
		QueryResult result = _services.retrieve(query);

		assertEquals("Is this a test conversation?", result.getAssets()[0].getAttribute(contentAttribute).getValue().toString());

		Asset answerExpression = _services.createNew(expressionType, questionExpression.getOid());
		answerExpression.setAttributeValue(authorAttribute, _services.getOid("Member:20"));
		answerExpression.setAttributeValue(authoredAtAttribute, DateUtils.addMinutes(DateTime.now().getValue(), 15));
		answerExpression.setAttributeValue(contentAttribute, "Yes it is!");
		answerExpression.setAttributeValue(inReplyToAttribute, questionExpression.getOid());
		_services.save(answerExpression);

		query = new Query(answerExpression.getOid().getMomentless());
		subsAttributeDefinition = answerExpression.getAssetType().getAttributeDefinition("Content");
		query.getSelection().add(subsAttributeDefinition);
		result = _services.retrieve(query);

		assertEquals("Yes it is!", result.getAssets()[0].getAttribute(contentAttribute).getValue().toString());
		assertEquals(1, result.getTotalAvaliable());
	}

	@Test
	public void createStoryWithConversationAndMentionTest() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		IAttributeDefinition mentionedInExpressionsAttribute = storyType.getAttributeDefinition("MentionedInExpressions");
		String name = "Test Story " + _projectId + " Create story with conversation and mention";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

		Asset storyTobeMentioned = _services.createNew(storyType, _projectId);
		storyTobeMentioned.setAttributeValue(nameAttribute, name + " (to be mentioned)");
		_services.save(storyTobeMentioned);

		IAssetType conversationType = _services.getMeta().getAssetType("Conversation");
		IAssetType expressionType = _services.getMeta().getAssetType("Expression");
		IAttributeDefinition authorAttribute = expressionType.getAttributeDefinition("Author");
		IAttributeDefinition authoredAtAttribute = expressionType.getAttributeDefinition("AuthoredAt");
		IAttributeDefinition contentAttribute = expressionType.getAttributeDefinition("Content");
		IAttributeDefinition belongsToAttribute = expressionType.getAttributeDefinition("BelongsTo");
		IAttributeDefinition inReplyToAttribute = expressionType.getAttributeDefinition("InReplyTo");

		Asset newConversation = _services.createNew(conversationType, newStory.getOid());
		Asset questionExpression = _services.createNew(expressionType, newStory.getOid());

		_services.save(newConversation);

		questionExpression.setAttributeValue(authorAttribute, _services.getOid("Member:20"));
		questionExpression.setAttributeValue(authoredAtAttribute, DateTime.now());
		questionExpression.setAttributeValue(contentAttribute, "Can I mention another story in a conversation?");
		questionExpression.setAttributeValue(belongsToAttribute, newConversation.getOid());
		_services.save(questionExpression);

		Query query = new Query(questionExpression.getOid().getMomentless());
		IAttributeDefinition subsAttributeDefinition = questionExpression.getAssetType().getAttributeDefinition("Content");
		query.getSelection().add(subsAttributeDefinition);
		QueryResult result = _services.retrieve(query);

		assertEquals("Can I mention another story in a conversation?", result.getAssets()[0].getAttribute(contentAttribute).getValue().toString());

		Asset answerExpression = _services.createNew(expressionType, questionExpression.getOid());
		answerExpression.setAttributeValue(authorAttribute, _services.getOid("Member:20"));
		answerExpression.setAttributeValue(authoredAtAttribute, DateUtils.addMinutes(DateTime.now().getValue(), 15));
		answerExpression.setAttributeValue(contentAttribute, "Yes I can!");
		answerExpression.setAttributeValue(inReplyToAttribute, questionExpression.getOid());
		_services.save(answerExpression);

		newStory.addAttributeValue(mentionedInExpressionsAttribute, questionExpression.getOid());
		_services.save(newStory);
		storyTobeMentioned.addAttributeValue(mentionedInExpressionsAttribute, answerExpression.getOid());
		_services.save(storyTobeMentioned);

		query = new Query(answerExpression.getOid().getMomentless());
		subsAttributeDefinition = answerExpression.getAssetType().getAttributeDefinition("Content");
		query.getSelection().add(subsAttributeDefinition);
		result = _services.retrieve(query);

		assertEquals("Yes I can!", result.getAssets()[0].getAttribute(contentAttribute).getValue().toString());
		assertEquals(1, result.getTotalAvaliable());
	}

	@Test
	public void createStoryWithNestedTaskTest() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		IAttributeDefinition childrenAttribute = storyType.getAttributeDefinition("Children");
		String name = "Test Story" + _projectId + " Create story with nested task";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

		IAssetType taskType = _services.getMeta().getAssetType("Task");
		Asset newTask = _services.createNew(taskType, newStory.getOid());
		newTask.setAttributeValue(nameAttribute, "Test Task Nested in " + newStory.getOid());

		_services.save(newTask);

		assertNotNull(newStory.getOid());
		assertNotNull(newTask.getOid());

		Query query = new Query(newStory.getOid().getMomentless());
		query.getSelection().add(childrenAttribute);
		Asset story = _services.retrieve(query).getAssets()[0];

		assertEquals(1, story.getAttributes().size());
	}

	@Test
	public void createStoryWithNestedTest() throws V1Exception {

		IAssetType storyType = _services.getMeta().getAssetType("Story");
		Asset newStory = _services.createNew(storyType, _projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		IAttributeDefinition childrenAttribute = storyType.getAttributeDefinition("Children");
		String name = "Test Story " + _projectId + " Create story with nested test";
		newStory.setAttributeValue(nameAttribute, name);
		_services.save(newStory);

		IAssetType testType = _services.getMeta().getAssetType("Test");
		Asset newTest = _services.createNew(testType, newStory.getOid());
		newTest.setAttributeValue(nameAttribute, "Test Test Nested in " + newStory.getOid());

		_services.save(newTest);

		assertNotNull(newStory.getOid());
		assertNotNull(newTest.getOid());

		Query query = new Query(newStory.getOid().getMomentless());
		query.getSelection().add(childrenAttribute);
		Asset story = _services.retrieve(query).getAssets()[0];

		assertEquals(1, story.getAttributes().size());
	}

	@Test
	public void createDefectTest() throws V1Exception {

		IAssetType defectType = _services.getMeta().getAssetType("Defect");
		Asset newDefect = _services.createNew(defectType, _projectId);
		IAttributeDefinition nameAttribute = defectType.getAttributeDefinition("Name");
		String name = "Test Defect " + _projectId + " Create defect";
		newDefect.setAttributeValue(nameAttribute, name);
		_services.save(newDefect);

		assertNotNull(newDefect.getOid());
	}

	@Test
	public void createDefectWithNestedTaskTest() throws V1Exception {

		IAssetType defectType = _services.getMeta().getAssetType("Defect");
		Asset newDefect = _services.createNew(defectType, _projectId);
		IAttributeDefinition nameAttribute = defectType.getAttributeDefinition("Name");
		IAttributeDefinition childrenAttribute = defectType.getAttributeDefinition("Children");
		String name = "Test Defect " + _projectId + " Create defect with nested task";
		newDefect.setAttributeValue(nameAttribute, name);
		_services.save(newDefect);

		IAssetType taskType = _services.getMeta().getAssetType("Task");
		Asset newTask = _services.createNew(taskType, newDefect.getOid());
		newTask.setAttributeValue(nameAttribute, "Test Task Nested in " + newDefect.getOid());

		_services.save(newTask);

		assertNotNull(newDefect.getOid());
		assertNotNull(newTask.getOid());

		Query query = new Query(newDefect.getOid().getMomentless());
		query.getSelection().add(childrenAttribute);
		Asset story = _services.retrieve(query).getAssets()[0];

		assertEquals(1, story.getAttribute(childrenAttribute).getValues().length);
	}

	@Test
	public void createDefectWithNestedTest() throws V1Exception {

		IAssetType defectType = _services.getMeta().getAssetType("Defect");
		Asset newDefect = _services.createNew(defectType, _projectId);
		IAttributeDefinition nameAttribute = defectType.getAttributeDefinition("Name");
		IAttributeDefinition childrenAttribute = defectType.getAttributeDefinition("Children");
		String name = "Test Defect " + _projectId + " Create defect with nested test";
		newDefect.setAttributeValue(nameAttribute, name);
		_services.save(newDefect);

		IAssetType testType = _services.getMeta().getAssetType("Test");
		Asset newTest = _services.createNew(testType, newDefect.getOid());
		newTest.setAttributeValue(nameAttribute, "Test Test Nested in " + newDefect.getOid());
		_services.save(newTest);

		assertNotNull(newDefect.getOid());
		assertNotNull(newTest.getOid());

		Query query = new Query(newDefect.getOid().getMomentless());
		query.getSelection().add(childrenAttribute);
		Asset story = _services.retrieve(query).getAssets()[0];

		assertEquals(1, story.getAttribute(childrenAttribute).getValues().length);
	}
	
	@Test
	public void createRequestTest() throws V1Exception {

		IAssetType requestType = _services.getMeta().getAssetType("Request");
		 Asset newRequest = _services.createNew(requestType, _projectId);
		IAttributeDefinition nameAttribute = requestType.getAttributeDefinition("Name");
		String name = "Test Request "+ _projectId + " Create request";
		newRequest.setAttributeValue(nameAttribute, name);
		_services.save(newRequest);

		assertNotNull(newRequest.getOid());
	}

	@Test
	public void createIssueTest() throws V1Exception {
	
		IAssetType issueType = _services.getMeta().getAssetType("Issue");
		Asset newIssue = _services.createNew(issueType, _projectId);
		IAttributeDefinition nameAttribute = issueType.getAttributeDefinition("Name");
		String name = "Test Issue " + _projectId + " Create issue";
		newIssue.setAttributeValue(nameAttribute, name);
		_services.save(newIssue);

		assertNotNull(newIssue.getOid());
	}

	@Test(expected = MetaException.class)
	public void createUnknownSingleAsset() {
		@SuppressWarnings("unused")
		IAssetType unknownAsset = _services.getMeta().getAssetType("Unknown");
	}

}
