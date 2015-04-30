/**
 * 
 */
package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.ProxyProvider;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Configuration;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IV1Configuration;

/**
 * @author 
 *
 */
public class V1ConnectorEndPointsTest {

	public static String url;
	public static Services services;
	public static V1Connector connector = null;
	
//	to test
//	 META_API_ENDPOINT = "meta.v1/";
//	 DATA_API_ENDPOINT = "rest-1.v1/Data/";
//	 NEW_API_ENDPOINT = "rest-1.v1/New/";
//	 HISTORY_API_ENDPOINT = "rest-1.v1/Hist/";
//	
//	 LOC_API_ENDPOINT = "loc.v1/"; //TODO: Need to support this endpoint.
//	LOC2_API_ENDPOINT = "loc-2.v1/"; //TODO: Need to support this endpoint.
//	CONFIG_API_ENDPOINT = "config.v1/"; //T
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}



	private String username;
	private String password;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		url = "http://localhost//VersionOne/";
		try {
			connector = V1Connector.withInstanceUrl(url)
						.withUserAgentHeader("name", "1.0")
						.withUsernameAndPassword("admin", "1234")
						.build();
		} catch (MalformedURLException | V1Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 services = new Services(connector);
		
	}
	


	// @Test
	public void LocEpicName() {

		IAssetType epicType = services.getMeta().getAssetType("Epic");
		IAttributeDefinition nameAttribute = epicType.getAttributeDefinition("Name");

		String locName = null;
		try {
			locName = services.loc(nameAttribute);
		} catch (V1Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(StringUtils.isNotBlank(locName));
	}

	// @Test
	public void LocStoryName() {

		IAssetType storyType = services.getMeta().getAssetType("Story");
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");

		// locDict = services.loc(new []{nameAttribute, estimateAttribute});
		//
		// Assert.IsTrue(locDict.Keys.Count > 0);
		// var locName = locDict[nameAttribute.Token];
		// Assert.IsTrue(!string.IsNullOrWhiteSpace(locName));
		// var locEstimate = locDict[estimateAttribute.Token];
		// Assert.IsTrue(!string.IsNullOrWhiteSpace(locEstimate));
	}



	// Config
//	@Test
	public void getConfig() {

		V1Configuration configuration = new V1Configuration(connector);

		try {
			assertNotNull(configuration.isEffortTracking());
		} catch (ConnectionException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(IV1Configuration.TrackingLevel.On, configuration.getStoryTrackingLevel().On);
		} catch (ConnectionException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	//	 QUERY_API_ENDPOINT = "query.v1/";
	@Test
	  public void executeSingleJsonQueryTest(){

          String jsonQuery =
              "{" +
              "  \"from\": \"Story\"," +
              "  \"select\": [\"Name\",\"Estimate\"]," +
              "  \"filter\": [\"Name='StoryName'|Estimate>'0'\"]" +
              "}";
          
          String res = services.executePassThroughQuery(jsonQuery);
          
          assertNotNull(res);
//          var stories = JArray.Parse(res).First;
//
//          foreach (var story in stories)
//          {
//              var storyName = story["Name"].Value<string>();
//              var storyEstimate = story["Estimate"].Value<double>();
//
//              Assert.IsTrue(storyName.Equals("StoryName") || storyEstimate > 0);
//          }
          System.out.println(res);
      }

//      [TestMethod]
//      public void ExecuteSingleYamlQuery()
//      {
//          var services = GetServices();
//
//          var yaml = "from: Story\n" +
//                     "select:\n" +
//                     "  - Name\n" +
//                     "  - Estimate\n" +
//                     "filter:\n" +
//                     "  - Name='StoryName'|Estimate>'0'";
//
//          var res = services.ExecutePassThroughQuery(yaml);
//          var stories = JArray.Parse(res).First;
//
//          foreach (var story in stories)
//          {
//              var storyName = story["Name"].Value<string>();
//              var storyEstimate = story["Estimate"].Value<double>();
//
//              Assert.IsTrue(storyName.Equals("StoryName") || storyEstimate > 0);
//          }
//      }
//
//      [TestMethod]
//      public void ExecuteMultipleJsonQuery()
//      {
//          var services = GetServices();
//
//          var json =
//              "[" +
//              " {" +
//              "    \"from\": \"Story\"," +
//              "    \"select\": [\"Name\",\"Estimate\"]," +
//              "    \"filter\": [\"Name='StoryName'|Estimate>'0'\"]" +
//              " }," +
//              " {" +
//              "    \"from\": \"Member\"," +
//              "    \"select\": [\"Name\"]" +
//              " }" +
//              "]";
//
//          var res = services.ExecutePassThroughQuery(json);
//          var stories = JArray.Parse(res).First;
//          var members = JArray.Parse(res).Last;
//
//          foreach (var story in stories)
//          {
//              var storyName = story["Name"].Value<string>();
//              var storyEstimate = story["Estimate"].Value<double>();
//
//              Assert.IsTrue(storyName.Equals("StoryName") || storyEstimate > 0);
//          }
//
//          foreach (var member in members)
//          {
//              var memberName = member["Name"].Value<string>();
//
//              Assert.IsTrue(!string.IsNullOrWhiteSpace(memberName));
//          }
//      }
//
//      [TestMethod]
//      public void ExecuteMultipleYamlQuery()
//      {
//          var services = GetServices();
//
//          var yaml = "  - from: Story\n" +
//                     "    select:\n" +
//                     "      - Name\n" +
//                     "      - Estimate\n" +
//                     "    filter:\n" +
//                     "      - Name='StoryName'|Estimate>'0'\n" +
//                     "  - from: Member\n" +
//                     "    select:\n" +
//                     "      - Name";
//
//          var res = services.ExecutePassThroughQuery(yaml);
//          var stories = JArray.Parse(res).First;
//          var members = JArray.Parse(res).Last;
//
//          foreach (var story in stories)
//          {
//              var storyName = story["Name"].Value<string>();
//              var storyEstimate = story["Estimate"].Value<double>();
//
//              Assert.IsTrue(storyName.Equals("StoryName") || storyEstimate > 0);
//          }
//
//          foreach (var member in members)
//          {
//              var memberName = member["Name"].Value<string>();
//
//              Assert.IsTrue(!string.IsNullOrWhiteSpace(memberName));
//          }
//      }
//
//      [TestMethod]
//      public void ExecuteSubSelectJsonQuery()
//      {
//          var services = GetServices();
//
//          var json =
//              "{" +
//              "   \"from\": \"Story\"," +
//              "   \"select\": [\"Name\",\"Estimate\", " +
//              "                {\"from\": \"Owners\", " +
//              "                 \"select\": [\"Name\", \"Nickname\"]" +
//              "                }" +
//              "               ]}";
//
//          var res = services.ExecutePassThroughQuery(json);
//          var stories = JArray.Parse(res).First;
//
//          foreach (var story in stories)
//          {
//              var storyName = story["Name"].Value<string>();
//              var storyEstimate = story["Estimate"];
//              var storyOwners = story["Owners"];
//
//              Assert.IsTrue(!string.IsNullOrWhiteSpace(storyName));
//              Assert.IsNotNull(storyEstimate);
//              Assert.IsNotNull(storyOwners);
//              foreach (var owner in storyOwners)
//              {
//                  Assert.IsTrue(!string.IsNullOrWhiteSpace(owner["Name"].Value<string>()));
//                  Assert.IsTrue(!string.IsNullOrWhiteSpace(owner["Nickname"].Value<string>()));
//              }
//
//          }
//      }
//
//      [TestMethod]
//      public void ExecuteSubSelectYamlQuery()
//      {
//          var services = GetServices();
//
//          var yaml = "  - from: Story\n" +
//                     "    select:\n" +
//                     "      - Name\n" +
//                     "      - Estimate\n" +
//                     "      - from: Owners\n" +
//                     "        select:\n" +
//                     "          - Name\n" +
//                     "          - Nickname\n";
//
//          var res = services.ExecutePassThroughQuery(yaml);
//          var stories = JArray.Parse(res).First;
//
//          foreach (var story in stories)
//          {
//              var storyName = story["Name"].Value<string>();
//              var storyEstimate = story["Estimate"];
//              var storyOwners = story["Owners"];
//
//              Assert.IsTrue(!string.IsNullOrWhiteSpace(storyName));
//              Assert.IsNotNull(storyEstimate);
//              Assert.IsNotNull(storyOwners);
//              foreach (var owner in storyOwners)
//              {
//                  Assert.IsTrue(!string.IsNullOrWhiteSpace(owner["Name"].Value<string>()));
//                  Assert.IsTrue(!string.IsNullOrWhiteSpace(owner["Nickname"].Value<string>()));
//              }
//
//          }
//      }
//
//      [TestMethod]
//      public void ExecuteGroupJsonQuery()
//      {
//          var services = GetServices();
//
//          var json =
//              "{" +
//                 "\"from\": \"Story\"," +
//                 "\"select\": [" +
//                     "\"Name\", \"Number\" " +
//                   "]," +
//                 "\"group\": [" +
//                    "{" +
//                      "\"from\": \"Status\"," + 
//                      "\"select\": [" +
//                          "\"Name\"" +
//                       "]" +
//                    "}" +
//                 "]" +
//              "}";
//
//          var res = services.ExecutePassThroughQuery(json);
//          var groups = JArray.Parse(res).First;
//
//          foreach (var group in groups)
//          {
//              Assert.IsTrue(group["_children"].Values<object>().Any());
//          }
//      }
//
//      [TestMethod]
//      public void ExecuteGroupYamlQuery()
//      {
//          var services = GetServices();
//
//          var yaml = "  - from: Story\n" +
//                     "    select:\n" +
//                     "      - Name\n" +
//                     "      - Number\n" +
//                     "    group:\n" +
//                     "      - from: Status\n" +
//                     "        select:\n" +
//                     "          - Name\n";
//
//          var res = services.ExecutePassThroughQuery(yaml);
//          var groups = JArray.Parse(res).First;
//
//          foreach (var group in groups)
//          {
//              Assert.IsTrue(group["_children"].Values<object>().Any());
//          }
//      }
//
//      [TestMethod]
//      [ExpectedException(typeof(WebException))]
//      public void MalformedJsonQuery()
//      {
//          var services = GetServices();
//
//          var json =
//              "{" +
//              "  \"from\": \"Story\"," +
//              "  \"select\": \"Name\",\"Estimate\"," +
//              "  \"filter\": [\"Name='StoryName'|Estimate>'0'\"" +
//              "}";
//
//          var res = services.ExecutePassThroughQuery(json);
//      }
//
//      [TestMethod]
//      [ExpectedException(typeof(WebException))]
//      public void MalformedYamlQuery()
//      {
//          var services = GetServices();
//
//          var yaml = "from: Story\n" +
//                     "   select:\n" +
//                     "   Name\n" +
//                     "  - Estimate\n" +
//                     "filter:\n" +
//                     "- Name='StoryName'|Estimate>'0'";
//
//          var res = services.ExecutePassThroughQuery(yaml);
//
//      }
//
}
