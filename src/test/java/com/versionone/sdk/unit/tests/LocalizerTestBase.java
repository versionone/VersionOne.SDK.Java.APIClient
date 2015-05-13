package com.versionone.sdk.unit.tests;

import com.versionone.apiclient.Localizer;
import com.versionone.apiclient.interfaces.ILocalizer;

public abstract class LocalizerTestBase {
	private ILocalizer _loc;
	
	protected ILocalizer getLoc()
		{
			if (_loc == null)
				_loc = new Localizer(new TextResponseConnector(MetaTestBase.TEST_DATA, "loc.v1/", getLocTestKeys()));
			return _loc;
		}

	protected abstract String getLocTestKeys();

}
