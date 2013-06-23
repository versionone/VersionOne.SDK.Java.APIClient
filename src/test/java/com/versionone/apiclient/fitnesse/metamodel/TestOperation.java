package com.versionone.apiclient.fitnesse.metamodel;

import com.versionone.apiclient.IOperation;
import com.versionone.apiclient.MetaException;
import com.versionone.apiclient.V1Exception;

/**
 * Test the ability to return operations
 */
public class TestOperation extends fit.ColumnFixture
{
	private IOperation testMe;
	public String operation;

	public boolean valid()
	{
		try
		{
			testMe = Setup.model.getOperation(operation);
		}
		catch(MetaException ex)
		{
			testMe = null;
			if(!ex.getMessage().startsWith("Unknown Operation: " + operation) )
				throw new RuntimeException(ex);
		}
		return null != testMe;
	}

	public String Token() { return (null == testMe) ? null : testMe.getToken();}
	public String Name() { return (null == testMe) ? null : testMe.getName();}
	public String AssetType() {
		try {
			return (null == testMe) ? null : testMe.getAssetType()
					.getToken();
		} catch (MetaException e) {
			throw new RuntimeException(e);
		}
	}

	public String ValidatorAttribute() {
		return (null == testMe) ? null : testMe.getValidatorAttribute()
        		.getName();
	}
}
