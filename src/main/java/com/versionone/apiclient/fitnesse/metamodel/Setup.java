package com.versionone.apiclient.fitnesse.metamodel;

import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.V1APIConnector;

/**
 * Setup MetaModelFixture for testing
 */
public class Setup extends fit.ColumnFixture
{
	public String url;
	static MetaModel model = null;
	
	public boolean connect() 
	{
		if(null == model) 
		{
			V1APIConnector connector = new V1APIConnector(url);
			model = new MetaModel(connector);
			return true;
		}
		else
		{
			return false;
		}
	}
}
