package com.versionone.apiclient.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.versionone.apiclient.TextBuilder;

public class TextBuilderTester {
	
	@Test public void SplitPrefixTwoParts()
	{
		StringBuffer prefix = new StringBuffer();
		StringBuffer suffix = new StringBuffer();
		TextBuilder.splitPrefix("some string goes here",' ', prefix, suffix);
		assertEquals("some", prefix.toString());
		assertEquals("string goes here",suffix.toString());
	}
	
	@Test public void SplitPrefixOnePart()
	{
		StringBuffer prefix = new StringBuffer();
		StringBuffer suffix = new StringBuffer();
		TextBuilder.splitPrefix("some",' ', prefix, suffix);
		assertEquals("",prefix.toString());
		assertEquals("some",suffix.toString());
	}		

	@Test public void FromTheCode()
	{
		StringBuffer prefix = new StringBuffer();
		StringBuffer suffix = new StringBuffer();
		TextBuilder.splitPrefix("Story.Order", '.', prefix, suffix);
		assertEquals("Story",prefix.toString());
		assertEquals("Order",suffix.toString());
	}		

	@Test public void JoinSingleItem()
	{
		assertEquals("5", TextBuilder.join(new Integer[] {5}, ";"));
	}		
	
	@Test public void Join()
	{
		assertEquals("5;4;3", TextBuilder.join(new Integer[] {5, 4, 3}, ";"));
	}
	
	@Test public void JoinWithNull()
	{
		assertEquals("5;;3", TextBuilder.join(new Object[] {5, null, 3}, ";"));
	}		

	@Test public void JoinInTheCode()
	{
		List<String> stringList = new ArrayList<String>();
		stringList.add("A");
		stringList.add("B");
		stringList.add("C");
		assertEquals("A/B/C", TextBuilder.join(stringList, "/"));
	}
}
