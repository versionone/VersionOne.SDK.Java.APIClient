package com.versionone.apiclient.fitnesse.metamodel;

import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.MetaException;
import com.versionone.apiclient.V1Exception;

/**
 * Test the ability to retrieve AttributeDefinitions
 */
public class TestAttributeDefinition extends fit.ColumnFixture
{
	private IAttributeDefinition testMe = null;
	public String attribute;

	public boolean valid()
	{
		try
		{
			testMe = Setup.model.getAttributeDefinition(attribute);
		}
		catch(MetaException ex)
		{
			testMe = null;
			if(!ex.getMessage().startsWith("Unknown AttributeDefinition: " + attribute) )
				throw new RuntimeException(ex);
		}
		return null != testMe;
	}

	public String Name() { return (null == testMe) ? null : testMe.getName();}
	public String Token() {return (null == testMe) ? null : testMe.getToken();}
	public String AttributeType() { return (null == testMe) ? null : testMe.getAttributeType().toString();}
	public String Base() {
		return (null == testMe) ? null
        		: (null == testMe.getBase()) ? attribute : testMe.getBase()
        				.getName();
	}
	public String IsReadOnly() { return (null == testMe) ? null : Boolean.toString(testMe.isReadOnly()); }
	public String  IsRequired() { return (null == testMe) ? null : Boolean.toString(testMe.isRequired());}
	public String IsMultiValued() { return (null == testMe) ? null : Boolean.toString(testMe.isMultiValue());}
	public String RelatedAsset() {
		try {
			return (null == testMe) ? null : (null == testMe
					.getRelatedAsset()) ? null : testMe.getRelatedAsset()
					.getToken();
		} catch (MetaException e) {
			throw new RuntimeException(e);
		}
	}
	public String DisplayName() { return (null == testMe) ? null : testMe.getDisplayName(); }
}
