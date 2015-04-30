package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.BeforeClass;

import com.versionone.DB.DateTime;
import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IOperation;
import com.versionone.apiclient.services.QueryResult;

public class V1ConnectorCreatesTests {
	private final static String TEST_PROJECT_NAME = "JavaSDK Integration Tests";
	private static Oid _testProjectId;
	private static String url = "http://localhost//VersionOne/";
	private static V1Connector connector;
	private static Services services;
	private static String accessToken = "1.yL3CcovObgbQnmMKP8PKTt3fo7A=";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		services = new Services(V1Connector.withInstanceUrl(url).withUserAgentHeader("IntegrationTests", "1.0").withAccessToken(accessToken).build());
		IAssetType assetType = services.getMeta().getAssetType("Scope");
		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
		Oid projectId = services.getOid("Scope:0");
		Asset newAsset = services.createNew(assetType, projectId);
		newAsset.setAttributeValue(nameAttribute, TEST_PROJECT_NAME);
		services.save(newAsset);
		_testProjectId = newAsset.getOid().getMomentless();

	}

	public void init() {
		try {
			connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("IntegrationTests", "1.0").withUsernameAndPassword("admin", "1234")
					.build();
		} catch (MalformedURLException | V1Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		services = new Services(connector);
	}

	// @Test
	public void CreateEpic() throws V1Exception {

		IAssetType epicType = services.getMeta().getAssetType("Epic");
		Asset newEpic = null;
		newEpic = services.createNew(epicType, _testProjectId);
		IAttributeDefinition nameAttribute = epicType.getAttributeDefinition("Name");
		String name = "Test Epic " + _testProjectId + " Create epic";
		newEpic.setAttributeValue(nameAttribute, name);
		services.save(newEpic);

		assertNotNull(newEpic.getOid());
	}

	// @Test
	public void createEpicWithNestedStoryTest() throws V1Exception {

		init();

		IAssetType epicType = services.getMeta().getAssetType("Epic");
		Asset newEpic = services.createNew(epicType, _testProjectId);
		IAttributeDefinition epicNameAttribute = epicType.getAttributeDefinition("Name");
		IOperation generateSubStoryOperation = epicType.getOperation("GenerateSubStory");
		String name = "Test Epic " + _testProjectId + " Create epic with nested story";
		newEpic.setAttributeValue(epicNameAttribute, name);

		services.save(newEpic);
		services.executeOperation(generateSubStoryOperation, newEpic.getOid());

		assertNotNull(newEpic);

		Query query = new Query(newEpic.getOid().getMomentless());
		IAttributeDefinition subsAttributeDefinition = epicType.getAttributeDefinition("Subs");
		query.getSelection().add(subsAttributeDefinition);
		QueryResult result = services.retrieve(query);

		assertEquals(1, result.getTotalAvaliable());
	}

	// @Test
	public void createStoryTest() throws V1Exception {

		init();

		IAssetType storyType = services.getMeta().getAssetType("Story");
		Asset newStory = services.createNew(storyType, _testProjectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _testProjectId + " Create story";
		newStory.setAttributeValue(nameAttribute, name);
		services.save(newStory);

		assertNotNull(newStory);
	}

	// @Test
	public void CreateStoryWithConversation() throws V1Exception {
		init();

		IAssetType storyType = services.getMeta().getAssetType("Story");
		Asset newStory = services.createNew(storyType, _testProjectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		String name = "Test Story " + _testProjectId + " Create story with conversation";
		newStory.setAttributeValue(nameAttribute, name);
		services.save(newStory);

		IAssetType conversationType = services.getMeta().getAssetType("Conversation");
		IAssetType expressionType = services.getMeta().getAssetType("Expression");
		IAttributeDefinition authorAttribute = expressionType.getAttributeDefinition("Author");
		IAttributeDefinition authoredAtAttribute = expressionType.getAttributeDefinition("AuthoredAt");
		IAttributeDefinition contentAttribute = expressionType.getAttributeDefinition("Content");
		IAttributeDefinition belongsToAttribute = expressionType.getAttributeDefinition("BelongsTo");
		IAttributeDefinition inReplyToAttribute = expressionType.getAttributeDefinition("InReplyTo");
		IAttributeDefinition mentionsAttribute = expressionType.getAttributeDefinition("Mentions");
		Asset newConversation = services.createNew(conversationType, null);
		Asset questionExpression = services.createNew(expressionType, null);
		services.save(newConversation);

		questionExpression.setAttributeValue(authorAttribute, services.getOid("Member:20"));
		questionExpression.setAttributeValue(authoredAtAttribute, DateTime.now());
		questionExpression.setAttributeValue(contentAttribute, "Is this a test conversation?");
		questionExpression.setAttributeValue(belongsToAttribute, newConversation.getOid());
		questionExpression.addAttributeValue(mentionsAttribute, newStory.getOid());
		services.save(questionExpression);

		Query query = new Query(questionExpression.getOid().getMomentless());
		IAttributeDefinition subsAttributeDefinition = questionExpression.getAssetType().getAttributeDefinition("Content");
		query.getSelection().add(subsAttributeDefinition);
		QueryResult result = services.retrieve(query);

		assertEquals("Is this a test conversation?", result.getAssets()[0].getAttribute(contentAttribute).getValue().toString());

		Asset answerExpression = services.createNew(expressionType, questionExpression.getOid());
		answerExpression.setAttributeValue(authorAttribute, services.getOid("Member:20"));
		answerExpression.setAttributeValue(authoredAtAttribute, DateTime.now());
		answerExpression.setAttributeValue(contentAttribute, "Yes it is!");
		answerExpression.setAttributeValue(inReplyToAttribute, questionExpression.getOid());
		services.save(answerExpression);

		query = new Query(answerExpression.getOid().getMomentless());
		subsAttributeDefinition = answerExpression.getAssetType().getAttributeDefinition("Content");
		query.getSelection().add(subsAttributeDefinition);
		result = services.retrieve(query);

		assertEquals("Yes it is!", result.getAssets()[0].getAttribute(contentAttribute).getValue().toString());
		assertEquals(1, result.getTotalAvaliable());

	}
	//
	// [TestMethod]
	// public void CreateStoryWithConversationAndMention()
	// {
	// var services = GetServices();
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var storyType = services.Meta.GetAssetType("Story");
	// var newStory = services.New(storyType, contextId);
	// var nameAttribute = storyType.GetAttributeDefinition("Name");
	// var mentionedInExpressionsAttribute = storyType.GetAttributeDefinition("MentionedInExpressions");
	// var name = string.Format("Test Story {0} Create story with conversation and mention", contextId);
	// newStory.SetAttributeValue(nameAttribute, name);
	// services.Save(newStory);
	//
	// var storyTobeMentioned = services.New(storyType, contextId);
	// storyTobeMentioned.SetAttributeValue(nameAttribute, name + " (to be mentioned)");
	// services.Save(storyTobeMentioned);
	//
	// var conversationType = services.Meta.GetAssetType("Conversation");
	// var expressionType = services.Meta.GetAssetType("Expression");
	// var authorAttribute = expressionType.GetAttributeDefinition("Author");
	// var authoredAtAttribute = expressionType.GetAttributeDefinition("AuthoredAt");
	// var contentAttribute = expressionType.GetAttributeDefinition("Content");
	// var belongsToAttribute = expressionType.GetAttributeDefinition("BelongsTo");
	// var inReplyToAttribute = expressionType.GetAttributeDefinition("InReplyTo");
	//
	// var newConversation = services.New(conversationType, newStory.Oid);
	// var questionExpression = services.New(expressionType, newStory.Oid);
	//
	// services.Save(newConversation);
	// questionExpression.SetAttributeValue(authorAttribute, services.GetOid("Member:20"));
	// questionExpression.SetAttributeValue(authoredAtAttribute, DateTime.Now);
	// questionExpression.SetAttributeValue(contentAttribute, "Can I mention another story in a conversation?");
	// questionExpression.SetAttributeValue(belongsToAttribute, newConversation.Oid);
	// services.Save(questionExpression);
	// var answerExpression = services.New(expressionType, questionExpression.Oid);
	// answerExpression.SetAttributeValue(authorAttribute, services.GetOid("Member:20"));
	// answerExpression.SetAttributeValue(authoredAtAttribute, DateTime.Now.AddMinutes(15));
	// answerExpression.SetAttributeValue(contentAttribute, "Yes I can!");
	// answerExpression.SetAttributeValue(inReplyToAttribute, questionExpression.Oid);
	// services.Save(answerExpression);
	//
	// newStory.AddAttributeValue(mentionedInExpressionsAttribute, questionExpression.Oid);
	// services.Save(newStory);
	// storyTobeMentioned.AddAttributeValue(mentionedInExpressionsAttribute, answerExpression.Oid);
	// services.Save(storyTobeMentioned);
	// }
	//
	// [TestMethod]
	// public void CreateStoryWithNestedTask()
	// {
	// var services = GetServices();
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var storyType = services.Meta.GetAssetType("Story");
	// var newStory = services.New(storyType, contextId);
	// var nameAttribute = storyType.GetAttributeDefinition("Name");
	// var childrenAttribute = storyType.GetAttributeDefinition("Children");
	// var name = string.Format("Test Story {0} Create story with nested task", contextId);
	// newStory.SetAttributeValue(nameAttribute, name);
	// services.Save(newStory);
	//
	// var taskType = services.Meta.GetAssetType("Task");
	// var newTask = services.New(taskType, newStory.Oid);
	// newTask.SetAttributeValue(nameAttribute, "Test Task Nested in " + newStory.Oid);
	//
	// services.Save(newTask);
	//
	// Assert.IsNotNull(newStory.Oid);
	// Assert.IsNotNull(newTask.Oid);
	//
	// var query = new Query(newStory.Oid.Momentless);
	// query.Selection.Add(childrenAttribute);
	// var story = services.Retrieve(query).Assets[0];
	//
	// Assert.AreEqual(1, story.GetAttribute(childrenAttribute).Values.Cast<object>().Count());
	// }
	//
	// [TestMethod]
	// public void CreateStoryWithNestedTest()
	// {
	// var services = GetServices();
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var storyType = services.Meta.GetAssetType("Story");
	// var newStory = services.New(storyType, contextId);
	// var nameAttribute = storyType.GetAttributeDefinition("Name");
	// var childrenAttribute = storyType.GetAttributeDefinition("Children");
	// var name = string.Format("Test Story {0} Create story with nested test", contextId);
	// newStory.SetAttributeValue(nameAttribute, name);
	// services.Save(newStory);
	//
	// var testType = services.Meta.GetAssetType("Test");
	// var newTest = services.New(testType, newStory.Oid);
	// newTest.SetAttributeValue(nameAttribute, "Test Test Nested in " + newStory.Oid);
	//
	// services.Save(newTest);
	//
	// Assert.IsNotNull(newStory.Oid);
	// Assert.IsNotNull(newTest.Oid);
	//
	// var query = new Query(newStory.Oid.Momentless);
	// query.Selection.Add(childrenAttribute);
	// var story = services.Retrieve(query).Assets[0];
	//
	// Assert.AreEqual(1, story.GetAttribute(childrenAttribute).Values.Cast<object>().Count());
	// }
	//
	// [TestMethod]
	// [DeploymentItem("versionone.png")]
	// public void CreateStoryWithAttachment()
	// {
	// var services = GetServices();
	// string file = "versionone.png";
	//
	// Assert.IsTrue(File.Exists(file));
	//
	// string mimeType = MimeType.Resolve(file);
	// IAttachments attachments = new Attachments(V1Connector
	// .WithInstanceUrl(_v1InstanceUrl)
	// .WithUserAgentHeader(".NET_SDK_Integration_Test", "1.0")
	// .WithAccessToken(_v1AccessToken).UseEndpoint("attachment.img/")
	// .Build());
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var storyType = services.Meta.GetAssetType("Story");
	// var newStory = services.New(storyType, contextId);
	// var nameAttribute = storyType.GetAttributeDefinition("Name");
	// var attachmentsAttribute = storyType.GetAttributeDefinition("Attachments");
	// var name = string.Format("Test Story {0} Create story with attachment", contextId);
	// newStory.SetAttributeValue(nameAttribute, name);
	// services.Save(newStory);
	//
	// IAssetType attachmentType = services.Meta.GetAssetType("Attachment");
	// IAttributeDefinition attachmentAssetDef = attachmentType.GetAttributeDefinition("Asset");
	// IAttributeDefinition attachmentContent = attachmentType.GetAttributeDefinition("Content");
	// IAttributeDefinition attachmentContentType =
	// attachmentType.GetAttributeDefinition("ContentType");
	// IAttributeDefinition attachmentFileName = attachmentType.GetAttributeDefinition("Filename");
	// IAttributeDefinition attachmentName = attachmentType.GetAttributeDefinition("Name");
	// Asset attachment = services.New(attachmentType, Oid.Null);
	// attachment.SetAttributeValue(attachmentName, "Test Attachment on " + newStory.Oid);
	// attachment.SetAttributeValue(attachmentFileName, file);
	// attachment.SetAttributeValue(attachmentContentType, mimeType);
	// attachment.SetAttributeValue(attachmentContent, string.Empty);
	// attachment.SetAttributeValue(attachmentAssetDef, newStory.Oid);
	// services.Save(attachment);
	// string key = attachment.Oid.Key.ToString();
	// using (Stream input = new FileStream(file, FileMode.Open, FileAccess.Read))
	// {
	// using (Stream output = attachments.GetWriteStream(key))
	// {
	// byte[] buffer = new byte[input.Length + 1];
	// while (true)
	// {
	// int read = input.Read(buffer, 0, buffer.Length);
	// if (read <= 0)
	// break;
	//
	// output.Write(buffer, 0, read);
	// }
	// }
	// }
	// attachments.SetWriteStream(key, mimeType);
	//
	// var query = new Query(newStory.Oid.Momentless);
	// query.Selection.Add(attachmentsAttribute);
	// var story = services.Retrieve(query).Assets[0];
	//
	// Assert.AreEqual(1, story.GetAttribute(attachmentsAttribute).Values.Cast<object>().Count());
	// }
	//
	// [TestMethod]
	// [DeploymentItem("versionone.png")]
	// public void CreateStoryWithEmbeddedImage()
	// {
	// var services = GetServices();
	// string file = "versionone.png";
	//
	// Assert.IsTrue(File.Exists(file));
	//
	// string mimeType = MimeType.Resolve(file);
	// IAttachments attachments = new Attachments(V1Connector
	// .WithInstanceUrl(_v1InstanceUrl)
	// .WithUserAgentHeader(".NET_SDK_Integration_Test", "1.0")
	// .WithAccessToken(_v1AccessToken).UseEndpoint("embedded.img/")
	// .Build());
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var storyType = services.Meta.GetAssetType("Story");
	// var newStory = services.New(storyType, contextId);
	// var nameAttribute = storyType.GetAttributeDefinition("Name");
	// var descriptionAttribute = storyType.GetAttributeDefinition("Description");
	// var name = string.Format("Test Story {0} Create story with embedded image", contextId);
	// newStory.SetAttributeValue(nameAttribute, name);
	// newStory.SetAttributeValue(descriptionAttribute, "Test description");
	// services.Save(newStory);
	//
	// var embeddedImageType = services.Meta.GetAssetType("EmbeddedImage");
	// var newEmbeddedImage = services.New(embeddedImageType, Oid.Null);
	// var assetAttribute = embeddedImageType.GetAttributeDefinition("Asset");
	// var contentAttribute = embeddedImageType.GetAttributeDefinition("Content");
	// var contentTypeAttribute = embeddedImageType.GetAttributeDefinition("ContentType");
	// newEmbeddedImage.SetAttributeValue(assetAttribute, newStory.Oid);
	// newEmbeddedImage.SetAttributeValue(contentTypeAttribute, mimeType);
	// newEmbeddedImage.SetAttributeValue(contentAttribute, string.Empty);
	// services.Save(newEmbeddedImage);
	//
	// string key = newEmbeddedImage.Oid.Key.ToString();
	// using (Stream input = new FileStream(file, FileMode.Open, FileAccess.Read))
	// {
	// using (Stream output = attachments.GetWriteStream(key))
	// {
	// byte[] buffer = new byte[input.Length + 1];
	// while (true)
	// {
	// int read = input.Read(buffer, 0, buffer.Length);
	// if (read <= 0)
	// break;
	//
	// output.Write(buffer, 0, read);
	// }
	// }
	// }
	// attachments.SetWriteStream(key, mimeType);
	// newStory.SetAttributeValue(descriptionAttribute,
	// string.Format("<img src=\"{0}\" alt=\"\" data-oid=\"{1}\" />", "embedded.img/" + key,
	// newEmbeddedImage.Oid.Momentless));
	// services.Save(newStory);
	// }
	//
	// [TestMethod]
	// public void CreateDefect()
	// {
	// var services = GetServices();
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var defectType = services.Meta.GetAssetType("Defect");
	// var newDefect = services.New(defectType, contextId);
	// var nameAttribute = defectType.GetAttributeDefinition("Name");
	// var name = string.Format("Test Defect {0} Create defect", contextId);
	// newDefect.SetAttributeValue(nameAttribute, name);
	// services.Save(newDefect);
	//
	// Assert.IsNotNull(newDefect.Oid);
	// }
	//
	// [TestMethod]
	// public void CreateDefectWithNestedTask()
	// {
	// var services = GetServices();
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var defectType = services.Meta.GetAssetType("Defect");
	// var newDefect = services.New(defectType, contextId);
	// var nameAttribute = defectType.GetAttributeDefinition("Name");
	// var childrenAttribute = defectType.GetAttributeDefinition("Children");
	// var name = string.Format("Test Defect {0} Create defect with nested task", contextId);
	// newDefect.SetAttributeValue(nameAttribute, name);
	// services.Save(newDefect);
	//
	// var taskType = services.Meta.GetAssetType("Task");
	// var newTask = services.New(taskType, newDefect.Oid);
	// newTask.SetAttributeValue(nameAttribute, "Test Task Nested in " + newDefect.Oid);
	//
	// services.Save(newTask);
	//
	// Assert.IsNotNull(newDefect.Oid);
	// Assert.IsNotNull(newTask.Oid);
	//
	// var query = new Query(newDefect.Oid.Momentless);
	// query.Selection.Add(childrenAttribute);
	// var story = services.Retrieve(query).Assets[0];
	//
	// Assert.AreEqual(1, story.GetAttribute(childrenAttribute).Values.Cast<object>().Count());
	// }
	//
	// [TestMethod]
	// public void CreateDefectWithNestedTest()
	// {
	// var services = GetServices();
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var defectType = services.Meta.GetAssetType("Defect");
	// var newDefect = services.New(defectType, contextId);
	// var nameAttribute = defectType.GetAttributeDefinition("Name");
	// var childrenAttribute = defectType.GetAttributeDefinition("Children");
	// var name = string.Format("Test Defect {0} Create defect with nested test", contextId);
	// newDefect.SetAttributeValue(nameAttribute, name);
	// services.Save(newDefect);
	//
	// var testType = services.Meta.GetAssetType("Test");
	// var newTest = services.New(testType, newDefect.Oid);
	// newTest.SetAttributeValue(nameAttribute, "Test Test Nested in " + newDefect.Oid);
	//
	// services.Save(newTest);
	//
	// Assert.IsNotNull(newDefect.Oid);
	// Assert.IsNotNull(newTest.Oid);
	//
	// var query = new Query(newDefect.Oid.Momentless);
	// query.Selection.Add(childrenAttribute);
	// var story = services.Retrieve(query).Assets[0];
	//
	// Assert.AreEqual(1, story.GetAttribute(childrenAttribute).Values.Cast<object>().Count());
	// }
	//
	// [TestMethod]
	// [DeploymentItem("versionone.png")]
	// public void CreateDefectWithAttachment()
	// {
	// var services = GetServices();
	// string file = "versionone.png";
	//
	// string mimeType = MimeType.Resolve(file);
	// IAttachments attachments = new Attachments(V1Connector
	// .WithInstanceUrl(_v1InstanceUrl)
	// .WithUserAgentHeader(".NET_SDK_Integration_Test", "1.0")
	// .WithAccessToken(_v1AccessToken).UseEndpoint("attachment.img/")
	// .Build());
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var defectType = services.Meta.GetAssetType("Defect");
	// var newDefect = services.New(defectType, contextId);
	// var nameAttribute = defectType.GetAttributeDefinition("Name");
	// var attachmentsAttribute = defectType.GetAttributeDefinition("Attachments");
	// var name = string.Format("Test Defect {0} Create defect with attachment", contextId);
	// newDefect.SetAttributeValue(nameAttribute, name);
	// services.Save(newDefect);
	//
	// IAssetType attachmentType = services.Meta.GetAssetType("Attachment");
	// IAttributeDefinition attachmentAssetDef = attachmentType.GetAttributeDefinition("Asset");
	// IAttributeDefinition attachmentContent = attachmentType.GetAttributeDefinition("Content");
	// IAttributeDefinition attachmentContentType =
	// attachmentType.GetAttributeDefinition("ContentType");
	// IAttributeDefinition attachmentFileName = attachmentType.GetAttributeDefinition("Filename");
	// IAttributeDefinition attachmentName = attachmentType.GetAttributeDefinition("Name");
	// Asset attachment = services.New(attachmentType, Oid.Null);
	// attachment.SetAttributeValue(attachmentName, "Test Attachment on " + newDefect.Oid);
	// attachment.SetAttributeValue(attachmentFileName, file);
	// attachment.SetAttributeValue(attachmentContentType, mimeType);
	// attachment.SetAttributeValue(attachmentContent, string.Empty);
	// attachment.SetAttributeValue(attachmentAssetDef, newDefect.Oid);
	// services.Save(attachment);
	// string key = attachment.Oid.Key.ToString();
	// using (Stream input = new FileStream(file, FileMode.Open, FileAccess.Read))
	// {
	// using (Stream output = attachments.GetWriteStream(key))
	// {
	// byte[] buffer = new byte[input.Length + 1];
	// while (true)
	// {
	// int read = input.Read(buffer, 0, buffer.Length);
	// if (read <= 0)
	// break;
	//
	// output.Write(buffer, 0, read);
	// }
	// }
	// }
	// attachments.SetWriteStream(key, mimeType);
	//
	// var query = new Query(newDefect.Oid.Momentless);
	// query.Selection.Add(attachmentsAttribute);
	// var story = services.Retrieve(query).Assets[0];
	//
	// Assert.AreEqual(1, story.GetAttribute(attachmentsAttribute).Values.Cast<object>().Count());
	// }
	//
	// [TestMethod]
	// [DeploymentItem("versionone.png")]
	// public void CreateDefectWithEmbeddedImage()
	// {
	// var services = GetServices();
	// string file = "versionone.png";
	//
	// string mimeType = MimeType.Resolve(file);
	// IAttachments attachments = new Attachments(V1Connector
	// .WithInstanceUrl(_v1InstanceUrl)
	// .WithUserAgentHeader(".NET_SDK_Integration_Test", "1.0")
	// .WithAccessToken(_v1AccessToken).UseEndpoint("embedded.img/")
	// .Build());
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var defectType = services.Meta.GetAssetType("Defect");
	// var newDefect = services.New(defectType, contextId);
	// var nameAttribute = defectType.GetAttributeDefinition("Name");
	// var descriptionAttribute = defectType.GetAttributeDefinition("Description");
	// var name = string.Format("Test Defect {0} Create defect with embedded image", contextId);
	// newDefect.SetAttributeValue(nameAttribute, name);
	// services.Save(newDefect);
	//
	// var embeddedImageType = services.Meta.GetAssetType("EmbeddedImage");
	// var newEmbeddedImage = services.New(embeddedImageType, Oid.Null);
	// var assetAttribute = embeddedImageType.GetAttributeDefinition("Asset");
	// var contentAttribute = embeddedImageType.GetAttributeDefinition("Content");
	// var contentTypeAttribute = embeddedImageType.GetAttributeDefinition("ContentType");
	// newEmbeddedImage.SetAttributeValue(assetAttribute, newDefect.Oid);
	// newEmbeddedImage.SetAttributeValue(contentTypeAttribute, mimeType);
	// newEmbeddedImage.SetAttributeValue(contentAttribute, string.Empty);
	// services.Save(newEmbeddedImage);
	//
	// string key = newEmbeddedImage.Oid.Key.ToString();
	// using (Stream input = new FileStream(file, FileMode.Open, FileAccess.Read))
	// {
	// using (Stream output = attachments.GetWriteStream(key))
	// {
	// byte[] buffer = new byte[input.Length + 1];
	// while (true)
	// {
	// int read = input.Read(buffer, 0, buffer.Length);
	// if (read <= 0)
	// break;
	//
	// output.Write(buffer, 0, read);
	// }
	// }
	// }
	// attachments.SetWriteStream(key, mimeType);
	// newDefect.SetAttributeValue(descriptionAttribute,
	// string.Format("<img src=\"{0}\" alt=\"\" data-oid=\"{1}\" />", "embedded.img/" + key,
	// newEmbeddedImage.Oid.Momentless));
	// services.Save(newDefect);
	// }
	//
	// [TestMethod]
	// public void CreateRequest()
	// {
	// var services = GetServices();
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var requestType = services.Meta.GetAssetType("Request");
	// var newRequest = services.New(requestType, contextId);
	// var nameAttribute = requestType.GetAttributeDefinition("Name");
	// var name = string.Format("Test Request {0} Create request", contextId);
	// newRequest.SetAttributeValue(nameAttribute, name);
	// services.Save(newRequest);
	//
	// Assert.IsNotNull(newRequest.Oid);
	// }
	//
	// [TestMethod]
	// public void CreateIssue()
	// {
	// var services = GetServices();
	//
	// var contextId = IntegrationTestsHelper.TestProjectOid;
	// var issueType = services.Meta.GetAssetType("Issue");
	// var newIssue = services.New(issueType, contextId);
	// var nameAttribute = issueType.GetAttributeDefinition("Name");
	// var name = string.Format("Test Issue {0} Create issue", contextId);
	// newIssue.SetAttributeValue(nameAttribute, name);
	// services.Save(newIssue);
	//
	// Assert.IsNotNull(newIssue.Oid);
	// }
	//
	// [TestMethod]
	// [ExpectedException(typeof(MetaException))]
	// public void CreateUnknownSingleAsset()
	// {
	// var services = GetServices();
	// var unknownAsset = services.Meta.GetAssetType("Unknown");
	// }

}
