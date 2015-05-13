package com.versionone.sdk.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.exceptions.V1Exception;


public class LocalizerTests extends LocalizerTestBase {

	@Override
	protected String getLocTestKeys() {
		return "LocalizerTester";
	}

	@Test public void SimpleString() throws V1Exception
	{
        System.out.println("Def encoding: "+System.getProperty("file.encoding"));
		Assert.assertEquals("Resolve To Simple", getLoc().resolve("Simple"));
		Assert.assertEquals("Это по-русски", getLoc().resolve("Russian"));
	}
	
	@Test public void ReturnInput() throws V1Exception
	{
		Assert.assertEquals("Blah",getLoc().resolve("Blah"));
	}
}
