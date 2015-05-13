package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.apiclient.interfaces.IServices;

public class QueryAPI {
	
	private static IServices _services;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Running query API integration tests...");
		_services = APIClientIntegrationTestSuiteIT.get_services();
	}

	@Test
	public void executeSingleJsonQueryTest() throws Exception{

          String jsonQuery =
              "{" +
              "  \"from\": \"Story\"," +
              "  \"select\": [\"Name\",\"Number\"]" +
              "}";
          
          String res = _services.executePassThroughQuery(jsonQuery);
          
          assertNotNull(res);
         
          JSONArray story_arr = new JSONArray(res).getJSONArray(0);
          JSONObject jsonProductObject = null;

          for (int i=0; i<story_arr.length(); i++){
        	  jsonProductObject = story_arr.getJSONObject(i);
        	  assertNotNull(jsonProductObject.get("Name"));
        	  assertNotNull(jsonProductObject.get("Number"));
          }
    }
	
	@Test
	public void executeSingleYamlQueryTest() throws JSONException {

		String yaml = "from: Story\n" +
                     "select:\n" +
                     "  - Name\n" +
                     "  - Number\n";

        String res = _services.executePassThroughQuery(yaml);
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

           String res = _services.executePassThroughQuery(json);
           assertNotNull(res);
           JSONArray stories_arr = new JSONArray(res).getJSONArray(0);
           JSONArray members_arr = new JSONArray(res).getJSONArray(1);

           JSONObject jsonProductObject = null;
           
           for (int i=0; i<stories_arr.length(); i++){
        	  jsonProductObject = stories_arr.getJSONObject(i);
        	  assertNotNull(jsonProductObject.get("Name"));
          }

          for (int i=0; i<members_arr.length(); i++){
        	  jsonProductObject = members_arr.getJSONObject(i);
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

		String res = _services.executePassThroughQuery(yaml);
		assertNotNull(res);
		JSONArray stories_arr = new JSONArray(res).getJSONArray(0);
		JSONArray members_arr = new JSONArray(res).getJSONArray(1);

		JSONObject jsonProductObject = null;

		for (int i = 0; i < stories_arr.length(); i++) {
			jsonProductObject = stories_arr.getJSONObject(i);
			assertNotNull(jsonProductObject.get("Name"));
		}

		for (int i = 0; i < members_arr.length(); i++) {
			jsonProductObject = members_arr.getJSONObject(i);
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

    	  String res = _services.executePassThroughQuery(json);
    	  JSONArray stories_arr = new JSONArray(res).getJSONArray(0);
    	  JSONObject jsonProductObject = null;

    	  for (int i = 0; i < stories_arr.length(); i++) {
               
    		  jsonProductObject = stories_arr.getJSONObject(i);
       		  assertNotNull(jsonProductObject.get("Name"));
			
       		  JSONArray owners = jsonProductObject.getJSONArray("Owners");

       		  for (int x = 0; i < owners.length(); x++) {
       			  jsonProductObject = owners.getJSONObject(x);
       			  assertNotNull(jsonProductObject.get("Name"));
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

        String res = _services.executePassThroughQuery(yaml);
      	JSONArray stories_arr = new JSONArray(res).getJSONArray(0);
      		
      	JSONObject jsonProductObject = null;

      	for (int i = 0; i < stories_arr.length(); i++) {
              
      		jsonProductObject = stories_arr.getJSONObject(i);
          	assertNotNull(jsonProductObject.get("Name"));

          	JSONArray owners = jsonProductObject.getJSONArray("Owners");
			for (int x = 0; x < owners.length(); x++) {
           	  jsonProductObject = owners.getJSONObject(x);
           	  assertNotNull(jsonProductObject.get("Name"));
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

		String res = _services.executePassThroughQuery(json);
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

		String res = _services.executePassThroughQuery(yaml);
		JSONArray groups_arr = new JSONArray(res).getJSONArray(0);

		JSONObject jsonProductObject = null;

		for (int x = 0; x < groups_arr.length(); x++) {
			jsonProductObject = groups_arr.getJSONObject(x);
			JSONArray childrens = jsonProductObject.getJSONArray("_children");
			assertNotNull(childrens);
		}
      }
     
    @Test
    public void malformedJsonQuery(){

    	String json =
    		"{" +
            "  \"from\": \"Story\"," +
            "  \"select\": \"Name\",\"Estimate\"," +
            "  \"filter\": [\"Name='StoryName'|Estimate>'0'\"" +
            "}";

         String res = _services.executePassThroughQuery(json);
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

          String res = _services.executePassThroughQuery(yaml);
          assertEquals("Server Error", res.trim());
      }

}
