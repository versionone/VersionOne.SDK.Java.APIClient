/**
 * 
 */
package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Configuration;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IV1Configuration;

public class Endpoints {

	public static String url;
	public static Services services;
	public static V1Connector connector = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		url = "http://localhost//VersionOne";
		try {
			connector = V1Connector.withInstanceUrl(url)
						.withUserAgentHeader("AppName", "1.0")
						.withUsernameAndPassword("admin", "admin")
						.build();
		} catch (MalformedURLException | V1Exception e) {
			e.printStackTrace();
		}
		 services = new Services(connector);
	}
	
	@Test
	public void getConfigTest() throws ConnectionException, APIException {

		V1Configuration configuration = new V1Configuration(connector);

		assertNotNull(configuration.isEffortTracking());
		assertEquals(IV1Configuration.TrackingLevel.On, configuration.getStoryTrackingLevel().On);
		assertNotNull(configuration.getDefectTrackingLevel());
		assertNotNull(configuration.getMaxAttachmentSize());
		assertNotNull(configuration.getStoryTrackingLevel());
	}
	
	@Test
	public void executeSingleJsonQueryTest() throws Exception{

          String jsonQuery =
              "{" +
              "  \"from\": \"Story\"," +
              "  \"select\": [\"Name\",\"Number\"]" +
              "}";
          
          String res = services.executePassThroughQuery(jsonQuery);
          
          assertNotNull(res);
         
          JSONArray story_arr = new JSONArray(res).getJSONArray(0);
          JSONObject jsonProductObject = null;

          for (int i=0; i<story_arr.length(); i++){
        	 
        	   jsonProductObject = story_arr.getJSONObject(i);
        	   System.out.println(jsonProductObject.get("Name"));
        	  assertNotNull(jsonProductObject.get("Name"));
        	  System.out.println(jsonProductObject.get("Number"));
        	  assertNotNull(jsonProductObject.get("Number"));
          }
   
      }
		@Test
		public void executeSingleYamlQueryTest() throws JSONException {

			String yaml = "from: Story\n" +
                     "select:\n" +
                     "  - Name\n" +
                     "  - Number\n";

           String res = services.executePassThroughQuery(yaml);
           assertNotNull(res);
           JSONArray story_arr = new JSONArray(res).getJSONArray(0);
           JSONObject jsonProductObject = null;
           for (int i=0; i<story_arr.length(); i++){
          	 
        	   jsonProductObject = story_arr.getJSONObject(i);
        	  assertNotNull(jsonProductObject.get("Name"));
          }
      }

     @Test
     public void executeMultipleJsonQueryTest() throws Exception{

          String json =
              "[" +
              " {" +
              "    \"from\": \"Story\"," +
              "    \"select\": [\"Name\"]," +
              "    \"filter\": [\"Name='Story'\"]" +
              " }," +
              " {" +
              "    \"from\": \"Member\"," +
              "    \"select\": [\"Name\"]" +
              " }" +
              "]";

           String res = services.executePassThroughQuery(json);
           assertNotNull(res);
           JSONArray stories_arr = new JSONArray(res).getJSONArray(0);
           JSONArray members_arr = new JSONArray(res).getJSONArray(1);

           JSONObject jsonProductObject = null;
           
           for (int i=0; i<stories_arr.length(); i++){
        	  jsonProductObject = stories_arr.getJSONObject(i);
        	  System.out.println(jsonProductObject.get("Name"));
        	  assertNotNull(jsonProductObject.get("Name"));
          }

          for (int i=0; i<members_arr.length(); i++){
        	  jsonProductObject = members_arr.getJSONObject(i);
        	  System.out.println(jsonProductObject.get("Name"));        	  
        	  assertNotNull(jsonProductObject.get("Name"));
          }
      }

      @Test
      public void executeMultipleYamlQueryTest() throws Exception{

          String yaml = "  - from: Story\n" +
                     "    select:\n" +
                     "      - Name\n" +
                     "    filter:\n" +
                     "      - Name='StoryName'\n" +
                     "  - from: Member\n" +
                     "    select:\n" +
                     "      - Name";

		String res = services.executePassThroughQuery(yaml);
		assertNotNull(res);
		JSONArray stories_arr = new JSONArray(res).getJSONArray(0);
		JSONArray members_arr = new JSONArray(res).getJSONArray(1);

		JSONObject jsonProductObject = null;

		for (int i = 0; i < stories_arr.length(); i++) {
			jsonProductObject = stories_arr.getJSONObject(i);
			System.out.println(jsonProductObject.get("Name"));
			assertNotNull(jsonProductObject.get("Name"));
		}

		for (int i = 0; i < members_arr.length(); i++) {
			jsonProductObject = members_arr.getJSONObject(i);
			System.out.println(jsonProductObject.get("Name"));
			assertNotNull(jsonProductObject.get("Name"));
		}
      }

      @Test
      public void executeSubSelectJsonQueryTest() throws Exception{

          String json =
              "{" +
              "   \"from\": \"Story\"," +
              "   \"select\": [\"Name\", " +
              "                {\"from\": \"Owners\", " +
              "                 \"select\": [\"Name\", \"Nickname\"]" +
              "                }" +
              "               ]}";

           String res = services.executePassThroughQuery(json);
       		JSONArray stories_arr = new JSONArray(res).getJSONArray(0);
       		
       		JSONObject jsonProductObject = null;

       		for (int i = 0; i < stories_arr.length(); i++) {
               
           	jsonProductObject = stories_arr.getJSONObject(i);
			
           	System.out.println(jsonProductObject.get("Name"));
			
           	assertNotNull(jsonProductObject.get("Name"));
			
			JSONArray owners = jsonProductObject.getJSONArray("Owners");

			for (int x = 0; i < owners.length(); x++) {
            
            	  jsonProductObject = owners.getJSONObject(i);
            	  System.out.println(jsonProductObject.get("Name"));
            	  assertNotNull(jsonProductObject.get("Name"));
            	  System.out.println(jsonProductObject.get("Nickname"));
            	  assertNotNull(jsonProductObject.get("Nickname"));
              }
          }
      }

     @Test
      public void executeSubSelectYamlQueryTest() throws Exception{

          String yaml = "  - from: Story\n" +
                     "    select:\n" +
                     "      - Name\n" +
                     "      - from: Owners\n" +
                     "        select:\n" +
                     "          - Name\n" +
                     "          - Nickname\n";

          String res = services.executePassThroughQuery(yaml);
      		JSONArray stories_arr = new JSONArray(res).getJSONArray(0);
      		
      		JSONObject jsonProductObject = null;

      		for (int i = 0; i < stories_arr.length(); i++) {
              
          	jsonProductObject = stories_arr.getJSONObject(i);
			
          	System.out.println(jsonProductObject.get("Name"));
			
          	assertNotNull(jsonProductObject.get("Name"));
			
			JSONArray owners = jsonProductObject.getJSONArray("Owners");

			for (int x = 0; x < owners.length(); x++) {
           
           	  jsonProductObject = owners.getJSONObject(x);
           	  System.out.println(jsonProductObject.get("Name"));
           	  assertNotNull(jsonProductObject.get("Name"));
           	  System.out.println(jsonProductObject.get("Nickname"));
           	  assertNotNull(jsonProductObject.get("Nickname"));
             }
         }
      }

   @Test
   public void executeGroupJsonQueryTest() throws Exception  {

          String json =
              "{" +
                 "\"from\": \"Story\"," +
                 "\"select\": [" +
                     "\"Name\", \"Number\" " +
                   "]," +
                 "\"group\": [" +
                    "{" +
                      "\"from\": \"Status\"," + 
                      "\"select\": [" +
                          "\"Name\"" +
                       "]" +
                    "}" +
                 "]" +
              "}";

		String res = services.executePassThroughQuery(json);
		JSONArray groups_arr = new JSONArray(res).getJSONArray(0);

		JSONObject jsonProductObject = null;

		for (int x = 0; x < groups_arr.length(); x++) {

			jsonProductObject = groups_arr.getJSONObject(x);
			JSONArray childrens = jsonProductObject.getJSONArray("_children");

			assertNotNull(childrens);
		}
      }

    @Test
     public void executeGroupYamlQueryTest() throws Exception  {

          String yaml = "  - from: Story\n" +
                     "    select:\n" +
                     "      - Name\n" +
                     "      - Number\n" +
                     "    group:\n" +
                     "      - from: Status\n" +
                     "        select:\n" +
                     "          - Name\n";

		String res = services.executePassThroughQuery(yaml);
		JSONArray groups_arr = new JSONArray(res).getJSONArray(0);

		JSONObject jsonProductObject = null;

		for (int x = 0; x < groups_arr.length(); x++) {

			jsonProductObject = groups_arr.getJSONObject(x);
			JSONArray childrens = jsonProductObject.getJSONArray("_children");

			assertNotNull(childrens);
		}
      }
     
    @Test()
     public void malformedJsonQuery(){

     		String json =
              "{" +
              "  \"from\": \"Story\"," +
              "  \"select\": \"Name\",\"Estimate\"," +
              "  \"filter\": [\"Name='StoryName'|Estimate>'0'\"" +
              "}";

          String res = services.executePassThroughQuery(json);
          assertEquals("Server Error", res.trim());
      }

      @Test
      public void malformedYamlQueryTest() {

          String yaml = "from: Story\n" +
                     "   select:\n" +
                     "   Name\n" +
                     "  - Estimate\n" +
                     "filter:\n" +
                     "- Name='StoryName'|Estimate>'0'";

          String res = services.executePassThroughQuery(yaml);
          assertEquals("Server Error", res.trim());

      }
}
