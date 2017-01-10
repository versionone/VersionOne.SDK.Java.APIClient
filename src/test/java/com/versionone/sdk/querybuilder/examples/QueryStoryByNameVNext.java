package com.versionone.sdk.querybuilder.examples;
import static java.lang.System.in;
import static java.lang.System.out;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.querybuilder.interfaces.IAssetBase;

public class QueryStoryByNameVNext {

	String instanceUrl = "https://www16.v1host.com/api-examples";
	String accessToken = "1.bndNO51GiliELZu1bbQdq3omgRI=";

	public static void main(String[] args) throws V1Exception, IOException {

		QueryStoryByNameVNext example = new QueryStoryByNameVNext();
		example.execute();
		out.println("Press any key to exit...");
		in.read();
	}

	public void execute() throws MalformedURLException, V1Exception {
		// Set up a connection to VersionOne using simple authentication
		List<IAssetBase> assets = V1Connector.withInstanceUrl(instanceUrl)
				.withUserAgentHeader("Examples", "0.1")
				.withAccessToken(accessToken).query("Story")
				.where("Name", "Hello, Lifecycle!")
				.select("Name", "Number", "another").retrieve();

		(assets).forEach(story -> out.println(story.get("OidToken") + "%n"
											+ story.get("Name") + "%n" 
											+ story.get("Number")));
	}
}
