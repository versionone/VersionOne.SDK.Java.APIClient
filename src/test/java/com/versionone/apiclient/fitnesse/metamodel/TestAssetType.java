package com.versionone.apiclient.fitnesse.metamodel;

import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;

/**
 * Test the ability to retrieve the AssetTypes
 */
public class TestAssetType extends fit.ColumnFixture
{
	public IAssetType testMe = null;
	public String type;


	public boolean valid()
	{
		try
		{
			testMe = Setup.model.getAssetType(type);
		}
		catch(MetaException ex)
		{
			testMe = null;
			if(!ex.getMessage().startsWith("Unknown AssetType: " + type) )
				throw new RuntimeException(ex);
		}
		return null != testMe;
	}

	public String Token() {return (null == testMe) ? null : testMe.getToken();}
	public String Base() {
		try {
			return (null == testMe) ? null : testMe.getBase().getToken();
		} catch (MetaException e) {
			throw new RuntimeException(e);
		}
	}
	public String DisplayName() {return (null == testMe) ? null : testMe.getDisplayName();}
	public String DefaultOrderBy() {
		return (null == testMe) ? null : testMe.getDefaultOrderBy()
        		.getToken();
	}
}
