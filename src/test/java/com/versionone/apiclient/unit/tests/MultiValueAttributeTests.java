package com.versionone.apiclient.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.services.QueryResult;

public class MultiValueAttributeTests extends ServicesTesterBase  {
	@Test
	public void MultiValueAttribute() throws Exception {
		Services subject = new Services(getMeta(), getDataConnector());
		Query queryStories = new Query(Oid.fromToken("Story:1063", getMeta()));
		IAttributeDefinition ownersDef = getMeta().getAttributeDefinition("Story.Owners");
		queryStories.getSelection().add(ownersDef);
		QueryResult resultStories = subject.retrieve(queryStories);

		Asset story = resultStories.getAssets()[0];
		Oid oldMember = Oid.fromToken("Member:1001", getMeta());
		Oid newMember = Oid.fromToken("Member:20", getMeta());
		Object[] owners = story.getAttribute(ownersDef).getValues();
		Assert.assertEquals(1, owners.length);
		Assert.assertEquals(oldMember, owners[0]);

		story.addAttributeValue(ownersDef, newMember);
		owners = story.getAttribute(ownersDef).getValues();
		Assert.assertEquals(2, owners.length);
		Assert.assertEquals(oldMember, owners[0]);
		Assert.assertEquals(newMember, owners[1]);

		story.removeAttributeValue(ownersDef, oldMember);
		owners = story.getAttribute(ownersDef).getValues();
		Assert.assertEquals(1, owners.length);
		Assert.assertEquals(newMember, owners[0]);

		story.removeAttributeValue(ownersDef, newMember);
		owners = story.getAttribute(ownersDef).getValues();
		Assert.assertEquals(0, owners.length);
	}

	@Override
    protected String getServicesTestKeys() {
	return "MultiValueAttributeTester";
    }
}
