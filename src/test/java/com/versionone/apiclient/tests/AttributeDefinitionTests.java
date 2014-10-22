package com.versionone.apiclient.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.MetaException;
import com.versionone.apiclient.V1Exception;

public class AttributeDefinitionTests extends MetaTestBase {

	@Override
	protected String getMetaTestKeys() {
		return "AttributeDefinitionTester";
	}

	@Test
	public void InvalidAttribute() {
		try {
			getMeta().getAttributeDefinition("Story.Blah");
		} catch (MetaException e) {
			Assert.assertEquals("Unknown AttributeDefinition: Story.Blah", e.getMessage());
		}
	}

	@Test
	public void TextAttribute() throws V1Exception {
		IAttributeDefinition def = getMeta().getAttributeDefinition("Story.Name");
		Assert.assertEquals("Story", def.getAssetType().getToken());
		Assert.assertEquals("Name", def.getName());
		Assert.assertEquals(IAttributeDefinition.AttributeType.Text, def.getAttributeType());
		Assert.assertEquals("BaseAsset.Name", def.getBase().getToken());
		Assert.assertEquals("AttributeDefinition'Name'Story", def.getDisplayName());
		Assert.assertFalse(def.isMultiValue());
		Assert.assertFalse(def.isReadOnly());
		Assert.assertTrue(def.isRequired());
	}
	
	@Test
	public void TestBooleanCoerce() throws V1Exception
	{
		// get a Boolean Attribute
		IAttributeDefinition attributeDef = getMeta().getAttributeDefinition("Note.Personal");
		Boolean boolValue = new Boolean(true);
		
		// Coerce intrinsic value 
		Object result = attributeDef.coerce(true);
		validateBitTrue(result);
		
		// Coerce Boolean class instance 
		result = attributeDef.coerce(boolValue);
		validateBitTrue(result);
		
		// Coerce string value
		result = attributeDef.coerce(boolValue.toString());
		validateBitTrue(result);
		
		result = attributeDef.coerce("true");
		validateBitTrue(result);
		
		result = attributeDef.coerce("True");
		validateBitTrue(result);
	}
	
	private void validateBitTrue(Object testMe) {
		Assert.assertNotNull(testMe);
		Assert.assertTrue(testMe instanceof Boolean);
		Assert.assertTrue(((Boolean)testMe).booleanValue());
	}
}
