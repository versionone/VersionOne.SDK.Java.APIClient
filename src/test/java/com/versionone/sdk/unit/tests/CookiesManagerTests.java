package com.versionone.sdk.unit.tests;

import com.versionone.apiclient.CookiesManager;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class will test the CookiesManager.
 *
 * @author VersionOne
 */
public class CookiesManagerTests {

	private Map<String, List<String>> getHeaderData() {
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		List<String> cookies = new ArrayList<String>();
		cookies.add("name1=value1; expires=Mon, 26-May-2008 12:02:10 GMT");
		cookies.add("name2=value2; expires=Tue, 26-May-3009 12:02:10 GMT");
		headers.put("Set-Cookie", cookies);
		return headers;
	}

	@Test
	public void testCookiesExpired() {
		CookiesManager cookiesManager = CookiesManager
				.getCookiesManager("http://localhost/test");
		cookiesManager.addCookie(getHeaderData());
		final String expect = "name2=value2";
		String cookies = cookiesManager.getCookies();
		Assert.assertEquals(expect, cookies);
		cookies = cookiesManager.getCookie("name2");
		Assert.assertEquals("value2", cookies);
	}

	@Test
	public void testCookiesExpired2() {
		CookiesManager cookiesManager = CookiesManager
				.getCookiesManager("http://localhost/test");
		Date expireDate1 = new Date();
		expireDate1.setTime(new Date().getTime() - 1);
		Date expireDate2 = new Date();
		expireDate2.setTime(new Date().getTime() + 1000000);
		String cookieName1 = "name1";
		String cookieValue1 = "value1";
		String cookieName2 = "name2";
		String cookieValue2 = "value2";
		cookiesManager.addCookie(cookieName1, cookieValue1, expireDate1);
		cookiesManager.addCookie(cookieName2, cookieValue2, expireDate2);
		final String expect = cookieName2 + "=" + cookieValue2;
		String cookies = cookiesManager.getCookies();
		Assert.assertEquals(expect, cookies);
		cookies = cookiesManager.getCookie("name2");
		Assert.assertEquals("value2", cookies);
	}

	@Test
	public void testDeleteCookies() {
		Date expireDate = new Date();
		expireDate.setTime(new Date().getTime() + 1000000);
		String name1 = "name1";
		String value1 = "value1";
		String name2 = "name2";
		String value2 = "value2";
		CookiesManager cookiesManager = CookiesManager
				.getCookiesManager("http://localhost/test");
		cookiesManager.addCookie(name1, value1, expireDate);
		cookiesManager.addCookie(name2, value2, expireDate);
		Assert.assertEquals(value1, cookiesManager.getCookie(name1));
		Assert.assertEquals(value2, cookiesManager.getCookie(name2));
		cookiesManager.deleteCookie(name2);
		Assert.assertEquals(null, cookiesManager.getCookie(name2));
	}

	@Test
	public void testgetDomenToken() {
		String domen1 = "http://www.domen.com/aaa/vvv/ccc";
		String domen2 = "http://domen.com/aaa/vvv/ccc";
		String domen3 = "http://www.domen1.com/aaa/vvv/ccc";
		String domen4 = "http://www.127.0.0.1:8080/aaa/vvv/ccc";

		Assert.assertEquals(CookiesManager.getDomenToken(domen1),
				CookiesManager.getDomenToken(domen2));
		Assert.assertEquals("domen1.com", CookiesManager
				.getDomenToken(domen3));
		Assert.assertEquals("127.0.0.1", CookiesManager
				.getDomenToken(domen4));

	}
}
