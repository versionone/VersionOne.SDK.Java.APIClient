package com.versionone.sdk.integration.tests;


public class Unused {

//	@Test()
//	public void saveAndUpdateTest() throws V1Exception, MalformedURLException {
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
//				.build();
//
//		Services services = new Services(connector);
//
//		Oid projectId = Oid.fromToken("Scope:0", services.getMeta());
//		IAssetType storyType = services.getMeta().getAssetType("Story");
//		Asset newStory = services.createNew(storyType, projectId);
//		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
//		newStory.setAttributeValue(nameAttribute, "My New Story");
//		services.save(newStory);
//
//		assertNotNull("Token: " + newStory.getOid().getToken());
//		assertEquals("Scope:0", newStory.getAttribute(storyType.getAttributeDefinition("Scope")).getValue().toString());
//		assertEquals("My New Story", newStory.getAttribute(nameAttribute).getValue().toString());
//
//		Oid storyId = newStory.getOid();
//		Query query = new Query(storyId);
//		nameAttribute = services.getMeta().getAssetType("Story").getAttributeDefinition("Name");
//		query.getSelection().add(nameAttribute);
//		QueryResult result = services.retrieve(query);
//		Asset story = result.getAssets()[0];
//		String newName = "This is my New Name";
//		story.setAttributeValue(nameAttribute, newName);
//		services.save(story);
//
//		assertEquals("This is my New Name", story.getAttribute(nameAttribute).getValue().toString());
//
//	}

//	@Test()
//	public void queryTest() throws V1Exception, MalformedURLException {
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
//				.build();
//
//		Services services = new Services(connector);
//
//		IAssetType assetType = services.getAssetType("Member");
//		Query query = new Query(assetType);
//		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
//		query.getSelection().add(nameAttribute);
//		IAttributeDefinition isSelf = assetType.getAttributeDefinition("IsSelf");
//		FilterTerm filter = new FilterTerm(isSelf);
//		filter.equal("true");
//
//		QueryResult result = services.retrieve(query);
//		assertNotNull(result);
//		assertTrue(result.getAssets().length > 0);
//	}

//	@Test(expected = MalformedURLException.class)
//	public void validatePathTest() throws V1Exception, MalformedURLException {
//
//		url = "http//localhost/versionone/";
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
//				.build();
//	}

//	@Test()
//	@Ignore
//	public void withAccessTokenThruAProxyTest() throws V1Exception, MalformedURLException {
//
//		String accessToken = "access_token";
//		URI address = null;
//		try {
//			address = new URI("http://localhost:808");
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		ProxyProvider proxy = new ProxyProvider(address, "user1", "user1");
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withAccessToken(accessToken).withProxy(proxy)
//				.build();
//
//		Services services = new Services(connector);
//		Oid oid = services.getLoggedIn();
//		assertNotNull(oid);
//	}
//
//	@Test()
//	@Ignore
//	public void connetionWithProxyUsingUsernameAndPasswordTest() throws V1Exception, MalformedURLException {
//
//		URI address = null;
//		try {
//			address = new URI("http://localhost:808");
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		ProxyProvider proxy = new ProxyProvider(address, "user1", "user1");
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
//				.withProxy(proxy).build();
//
//		Services services = new Services(connector);
//
//		IAssetType assetType = services.getAssetType("Member");
//		Query query = new Query(assetType);
//		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
//		query.getSelection().add(nameAttribute);
//		IAttributeDefinition isSelf = assetType.getAttributeDefinition("IsSelf");
//		FilterTerm filter = new FilterTerm(isSelf);
//		filter.equal("true");
//
//		assertNotNull(assetType);
//		QueryResult result = services.retrieve(query);
//		assertNotNull(result);
//		assertTrue(result.getAssets().length > 0);
//	}
//
//	@Test()
//	@Ignore
//	public void testConnectionNtlm() throws Exception {
//
//		url = "http://localhost/VersionOneNtlm/";
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated().build();
//
//		Services services = new Services(connector);
//		Oid oid = services.getLoggedIn();
//		System.out.println(oid.getAssetType().getDisplayName());
//		assertNotNull(oid);
//	}
//
//	@Test()
//	@Ignore
//	public void testConnectionNtlmWithUsernamePass() throws Exception {
//
//		url = "http://localhost/VersionOneNtlm/";
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated("vplechuc", "moifaku72")
//				.build();
//
//		Services services = new Services(connector);
//		Oid oid = services.getLoggedIn();
//		assertNotNull(oid);
//	}

//	@Test
//	@Ignore
//	public void testConnectionNtlmWithProxy() throws Exception {
//
//		String url = "http://localhost/VersionOne/";
//		URI address = null;
//		try {
//			address = new URI("http://localhost:808");
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		ProxyProvider proxy = new ProxyProvider(address, "user1", "user1");
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated().withProxy(proxy).build();
//
//		Services services = new Services(connector);
//
//		IAssetType assetType = services.getAssetType("Member");
//
//		assertNotNull(assetType);
//	}


}
