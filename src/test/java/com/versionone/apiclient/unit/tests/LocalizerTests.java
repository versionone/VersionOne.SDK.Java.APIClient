package com.versionone.apiclient.unit.tests;

import org.junit.Assert;
import org.junit.Test;


public class LocalizerTests extends LocalizerTestBase {

	@Override
	protected String getLocTestKeys() {
		return "LocalizerTester";
	}

	@Test public void SimpleString()
	{
        System.out.println("Def encoding: "+System.getProperty("file.encoding"));
		Assert.assertEquals("Resolve To Simple", getLoc().resolve("Simple"));
		Assert.assertEquals("Это по-русски", getLoc().resolve("Russian"));
	}
	
	@Test public void ReturnInput()
	{
		Assert.assertEquals("Blah",getLoc().resolve("Blah"));
	}
}
