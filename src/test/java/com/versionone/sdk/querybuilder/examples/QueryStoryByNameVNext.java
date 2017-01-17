package com.versionone.sdk.querybuilder.examples;

import static java.lang.System.in;
import static java.lang.System.out;

import java.io.IOException;
import java.util.List;

import javax.management.OperationsException;

import org.apache.commons.lang.NullArgumentException;
import org.apache.http.client.ClientProtocolException;

import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.querybuilder.interfaces.IAssetBase;

public class QueryStoryByNameVNext {

	String instanceUrl = "https://www16.v1host.com/api-examples";
	String accessToken = "1.bndNO51GiliELZu1bbQdq3omgRI=";

	public static void main(String[] args) throws V1Exception, IOException,
			NullArgumentException, UnsupportedOperationException,
			OperationsException {

		QueryStoryByNameVNext example = new QueryStoryByNameVNext();
		example.execute();
		out.println("Press any key to exit...");
		in.read();
	}

	public void execute() throws V1Exception, NullArgumentException,
			ClientProtocolException, UnsupportedOperationException,
			IOException, OperationsException {
		// Set up a connection to VersionOne using simple authentication
		List<IAssetBase> assets = V1Connector.withInstanceUrl(instanceUrl)
				.withUserAgentHeader("Examples", "0.1")
				.withAccessToken(accessToken)
				.query("Story")
				// .where("Name", "Hello, Lifecycle!")
				// .select("Name", "Number", "another")
				.retrieve();

		(assets).forEach(story -> out.println(	"Name:  " + story.get("Name") + "\n" + 
												"Deescription: " + story.get("Description")));
	}
}
