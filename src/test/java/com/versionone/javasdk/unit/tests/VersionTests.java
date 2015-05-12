package com.versionone.javasdk.unit.tests;

import junit.framework.Assert;

import org.junit.Test;

import com.versionone.utils.Version;


public class VersionTests {
	
	@Test
	public void All() {
		String version = "1.2.3.4";
		Version testMe = new Version(version);
		Assert.assertEquals(1, testMe.getMajor());
		Assert.assertEquals(2, testMe.getMinor());
		Assert.assertEquals(3, testMe.getBuild());
		Assert.assertEquals(4, testMe.getRevision());
		Assert.assertEquals(version, testMe.toString());
	}

	@Test
	public void ImpliedRevision() {
		String version = "1.2.3";
		Version testMe = new Version(version);
		Assert.assertEquals(1, testMe.getMajor());
		Assert.assertEquals(2, testMe.getMinor());
		Assert.assertEquals(3, testMe.getBuild());
		Assert.assertEquals(0, testMe.getRevision());
		Assert.assertEquals("1.2.3.0", testMe.toString());
	}
	
	@Test
	public void ImpliedBuildAndRevision() {
		String version = "1.2";
		Version testMe = new Version(version);
		Assert.assertEquals(1, testMe.getMajor());
		Assert.assertEquals(2, testMe.getMinor());
		Assert.assertEquals(0, testMe.getBuild());
		Assert.assertEquals(0, testMe.getRevision());
		Assert.assertEquals("1.2.0.0", testMe.toString());
	}
	
	@Test
	public void ImpliedMinorAndBuildAndRevision() {
		String version = "1";
		Version testMe = new Version(version);
		Assert.assertEquals(1, testMe.getMajor());
		Assert.assertEquals(0, testMe.getMinor());
		Assert.assertEquals(0, testMe.getBuild());
		Assert.assertEquals(0, testMe.getRevision());
		Assert.assertEquals("1.0.0.0", testMe.toString());
	}

	@Test
	public void EmptyString() {
		String version = "";
		Version testMe = new Version(version);
		Assert.assertEquals(1, testMe.getMajor());
		Assert.assertEquals(0, testMe.getMinor());
		Assert.assertEquals(0, testMe.getBuild());
		Assert.assertEquals(0, testMe.getRevision());
		Assert.assertEquals("1.0.0.0", testMe.toString());
	}
	
	@Test
	public void NullString() {
		String version = null;
		Version testMe = new Version(version);
		Assert.assertEquals(1, testMe.getMajor());
		Assert.assertEquals(0, testMe.getMinor());
		Assert.assertEquals(0, testMe.getBuild());
		Assert.assertEquals(0, testMe.getRevision());
		Assert.assertEquals("1.0.0.0", testMe.toString());
	}

	@Test
	public void DefaultConstructor() {
		Version testMe = new Version();
		Assert.assertEquals(1, testMe.getMajor());
		Assert.assertEquals(0, testMe.getMinor());
		Assert.assertEquals(0, testMe.getBuild());
		Assert.assertEquals(0, testMe.getRevision());
		Assert.assertEquals("1.0.0.0", testMe.toString());
	}
	
}
