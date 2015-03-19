package com.versionone.apiclient.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IOperation;

public class OperationTests extends MetaTestBase {

	@Override
	protected String getMetaTestKeys() {
		return "OperationTester";
	}

	@Test
	public void InvalidOperation() {
		try {
			getMeta().getOperation("Story.DoNothing");
		} catch (MetaException e) {
			Assert.assertEquals("Unknown Operation: Story.DoNothing", e.getMessage());
		}
	}

	@Test
	public void BaseAssetDelete() throws V1Exception {
		IOperation op = getMeta().getOperation("BaseAsset.Delete");
		Assert.assertEquals("BaseAsset", op.getAssetType().getToken());
		Assert.assertEquals("BaseAsset.Delete", op.getToken());
		Assert.assertEquals("Delete", op.getName());
		Assert.assertEquals("BaseAsset.IsDeletable", op.getValidatorAttribute().getToken());
	}

	@Test
	public void LoadedByGetAssetType() throws V1Exception {
		getMeta().getAssetType("List");
		IOperation op = getMeta().getOperation("List.Delete");

		Assert.assertEquals("List", op.getAssetType().getToken());
		Assert.assertEquals("List.Delete", op.getToken());
		Assert.assertEquals("Delete", op.getName());
		Assert.assertEquals("List.IsDeletable", op.getValidatorAttribute().getToken());
	}

	@Test
	public void MetaModelLookupOperation() throws V1Exception {
		IOperation op = getMeta().getOperation("MyType.Delete");

		Assert.assertEquals("MyType", op.getAssetType().getToken());
		Assert.assertEquals("MyType.Delete", op.getToken());
		Assert.assertEquals("Delete", op.getName());
		Assert.assertNull(op.getValidatorAttribute());
	}
}
