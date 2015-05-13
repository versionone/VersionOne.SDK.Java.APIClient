package com.versionone.sdk.unit.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.Paging;

public class PagingTests {
	
	@Test
	public void ConstructorToken()
	{
		Paging p = new Paging();
		Assert.assertEquals(String.format("%d,0",Integer.MAX_VALUE),p.getToken());
		
		p = new Paging(7,55);
		Assert.assertEquals("55,7",p.getToken());
	}
	
	@Test
	public void ChangeToken()
	{
		Paging p = new Paging();
		p.setPageSize(12);
		Assert.assertEquals("12,0",p.getToken());			
		p.setStart(38);
		Assert.assertEquals("12,38",p.getToken());
		p.setPageSize(16);
		p.setStart(3);
		Assert.assertEquals("16,3",p.getToken());
	}
	
	@Test
	public void TokenEqualsToString()
	{
		Paging p = new Paging(5,25);
		Assert.assertEquals("25,5",p.getToken());
		Assert.assertEquals("25,5",p.toString());
	}

}
