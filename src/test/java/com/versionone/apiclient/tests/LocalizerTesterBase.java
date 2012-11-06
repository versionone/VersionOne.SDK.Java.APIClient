package com.versionone.apiclient.tests;

import com.versionone.apiclient.ILocalizer;
import com.versionone.apiclient.Localizer;

public abstract class LocalizerTesterBase {
	private ILocalizer _loc;
	
	protected ILocalizer getLoc()
		{
			if (_loc == null)
				_loc = new Localizer(new TextResponseConnector(MetaTesterBase.TEST_DATA, "loc.v1/", getLocTestKeys()));
			return _loc;
		}

	protected abstract String getLocTestKeys();

}
