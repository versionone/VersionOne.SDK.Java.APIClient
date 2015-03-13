package com.versionone.apiclient.fitnesse;

import com.versionone.apiclient.Localizer;
import com.versionone.apiclient.V1APIConnector;
import com.versionone.apiclient.exceptions.V1Exception;

import fit.ColumnFixture;

public class LocalizeFixture extends ColumnFixture {

	public String input;
	public String url;

	public String output() throws V1Exception {
		V1APIConnector server = new V1APIConnector(url);
		Localizer testMe = new Localizer(server);
		return testMe.resolve(input);
	}
}
