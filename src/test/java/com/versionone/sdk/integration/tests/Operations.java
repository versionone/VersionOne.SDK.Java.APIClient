package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.OidException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IServices;

public class Operations {
	
	private static IServices _services;
	private static Oid _projectId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Running operations integration tests...");
		_services = APIClientIntegrationTestSuiteIT.get_services();
		_projectId = APIClientIntegrationTestSuiteIT.get_projectId();
	}

	@Test(expected = ConnectionException.class)
    public void operationDeleteOnEpic() throws V1Exception {

		IAssetType epicType = _services.getMeta().getAssetType("Epic");
        Asset newEpic = _services.createNew(epicType, _projectId);
        IAttributeDefinition nameAttribute = epicType.getAttributeDefinition("Name");
        String name = "Test Epic " + _projectId + " Deleted epic";
        newEpic.setAttributeValue(nameAttribute, name);
        _services.save(newEpic);

        Oid deletedEpicId = _services.executeOperation(epicType.getOperation("Delete"), newEpic.getOid());
        
        Query query = new Query(deletedEpicId.getMomentless());
        query.getSelection().add(nameAttribute);

        assertTrue(_services.retrieve(query).getAssets().length == 0);
    }
	
	@Test(expected = OidException.class)
    public void operationDeleteUnknownAsset() throws V1Exception {
		IAssetType epicType = _services.getMeta().getAssetType("Epic");
        @SuppressWarnings("unused")
		Oid deletedEpicId = _services.executeOperation(epicType.getOperation("Delete"), _services.getOid("unknown:007"));
    }
	
	@Test
    public void operationCloseOnStory() throws V1Exception {

		IAssetType assetType = _services.getMeta().getAssetType("Story");
        Asset newAsset = _services.createNew(assetType, _projectId);
        IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
        String name = "Test Story " + _projectId + " closed story";
        newAsset.setAttributeValue(nameAttribute, name);
        _services.save(newAsset);

        Oid closedAssetId = _services.executeOperation(assetType.getOperation("Inactivate"), newAsset.getOid());
        
        Query query = new Query(closedAssetId.getMomentless());
        query.getSelection().add(nameAttribute);
        IAttributeDefinition isClosedAttr = assetType.getAttributeDefinition("IsClosed");
        query.getSelection().add(isClosedAttr);
        Asset story = _services.retrieve(query).getAssets()[0];

        assertNotNull(story);
        assertTrue(Boolean.parseBoolean(story.getAttribute(isClosedAttr).getValue().toString()));
    }
	
	@Test
    public void operationReopenOnStory() throws V1Exception {

		IAssetType assetType = _services.getMeta().getAssetType("Story");
        Asset newAsset = _services.createNew(assetType, _projectId);
        IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
        String name = "Test Story " + _projectId + " reopen story";
        newAsset.setAttributeValue(nameAttribute, name);
        _services.save(newAsset);

        Oid closedAssetId = _services.executeOperation(assetType.getOperation("Inactivate"), newAsset.getOid());
        
        Query query = new Query(closedAssetId.getMomentless());
        query.getSelection().add(nameAttribute);
        IAttributeDefinition isClosedAttr = assetType.getAttributeDefinition("IsClosed");
        query.getSelection().add(isClosedAttr);
        Asset story = _services.retrieve(query).getAssets()[0];

        assertNotNull(story);
        assertTrue(Boolean.parseBoolean(story.getAttribute(isClosedAttr).getValue().toString()));
        
        Oid reopenedId = _services.executeOperation(assetType.getOperation("Reactivate"), closedAssetId);
        query = new Query(reopenedId);
        query.getSelection().add(isClosedAttr);
        story = _services.retrieve(query).getAssets()[0];

        assertNotNull(story);
        assertFalse(Boolean.parseBoolean(story.getAttribute(isClosedAttr).getValue().toString()));
    }
	
}
