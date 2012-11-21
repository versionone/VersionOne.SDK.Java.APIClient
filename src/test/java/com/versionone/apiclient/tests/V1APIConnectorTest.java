package com.versionone.apiclient.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.apiclient.ConnectionException;
import com.versionone.apiclient.ICookiesManager;
import com.versionone.apiclient.ProxyProvider;
import com.versionone.apiclient.V1APIConnector;

/**
 * This class will test the V1APIConnector. It's a duplicate of the Fitnesse
 * integration tests <p/> It's not part of the nightly builds because we cannot
 * depend on
 *
 * @author jerry
 */
public class V1APIConnectorTest {

	private static final String V1_PATH = "http://localhost/VersionOne.SDK.Java.APIClient.Tests/";	

	@Before
	public void clearCookes() {
		V1APIConnector testMe = new V1APIConnector("http://localhost");
		testMe.getCookiesJar().deleteAllCookies();
		V1APIConnector testMe2 = new V1APIConnector("http://127.0.0.1");
		testMe2.getCookiesJar().deleteAllCookies();
	}

	@Test(expected = ConnectionException.class)
	public void testInvalidUser() throws ConnectionException {
		V1APIConnector testMe = new V1APIConnector(V1_PATH, "foo", "bar");
		testMe.getData("rest-1.v1/Data/Scope/0");
	}

	@Test
	@Ignore("Need to run it manually. Required proxy server.")
	public void testProxy() throws ConnectionException, URISyntaxException {
		URI uri = new URI("http://integvm01.internal.corp:3128");
		ProxyProvider proxy = new ProxyProvider(uri, "user1", "user1");
		V1APIConnector testMe = new V1APIConnector(V1_PATH, "admin", "admin", proxy);
		testMe.getData("rest-1.v1/Data/Scope/0");
	}

	@Test
	public void testValidUser() throws ConnectionException {
		V1APIConnector testMe = new V1APIConnector(V1_PATH, "admin", "admin");
		Reader results = testMe.getData("rest-1.v1/Data/Scope/0");
		Assert.assertTrue(results != null);
	}

	@Test(expected = ConnectionException.class)
	public void testURLInvalidUserAfterValid() throws ConnectionException {
		V1APIConnector testMe = new V1APIConnector(V1_PATH, "admin", "admin");
		Reader results = testMe.getData("rest-1.v1/Data/Scope/0");
        testMe = new V1APIConnector(V1_PATH);
        results = testMe.getData("meta.v1/Scope");
		Assert.assertTrue(results != null);
		testMe = new V1APIConnector(V1_PATH, "foo12", "bar");
		testMe.getData("rest-1.v1/Data/Scope/0");
	}

	// private final static ;

	@Test
	public void testUserCustomHeader() throws ConnectionException, IOException {
		String paramName = "test-param";
		String paramValue = "test-value";
		int port = 4444;
		final TestServer testServer = new TestServer(port, null, 1);
		startServer(testServer);

		V1APIConnector testMe = new V1APIConnector("http://localhost:" + port,
				"foo", "bar");
		testMe.customHttpHeaders.put(paramName, paramValue);
		testMe.getData();

		Map<String, String> headers = testServer.getHeaders(0);
		Assert.assertNotNull("There is no " + paramName + " parameter.",
				headers.get(paramName));
		Assert.assertEquals("Incorrect parameter value.", paramValue, headers
				.get(paramName));
	}

	private void startServer(final TestServer test) {
		test.start();
		// waiting till server is started
		int count = 0;
		while (test.isNotRun && count < 50) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// Do nothing
			}
			count++;
		}
		// JIC
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// Do nothing
		}
	}

	@Test
	public void testCookiesManager() throws ConnectionException, IOException {
		String cookies = "value=my_custom_cookies";
		int port = 4444;
		TestServer testServer = new TestServer(port, Arrays.asList(cookies), 5);
		startServer(testServer);

		V1APIConnector testMe = new V1APIConnector("http://localhost:" + port,
				"foo", "bar");
		// first request (no cookies)
		testMe.getData();
		// second request (only cookie from server)
		testMe.getData();

		V1APIConnector testMe2 = new V1APIConnector("http://localhost:" + port,
				"foo", "bar");
		String paramName2 = "new_test_name";
		String paramValue2 = "new_test_value";
		Date expireDate = new Date();
		expireDate.setTime(new Date().getTime() + 1000000);
		testMe2.getCookiesJar().addCookie(paramName2, paramValue2, expireDate);
		testServer.addCookies(paramName2, paramValue2);
		// third request (server cookie from server and additional user cookie)
		testMe2.getData();

		// no cookies
		V1APIConnector testMe3 = new V1APIConnector("http://localhost:" + port,
				"foo1", "bar");
		// fourth request. we use another user name (no cookies from the server)
		testMe3.getData();

		testMe3.getCookiesJar().addCookie(paramName2, paramValue2, expireDate);
		// fifth request. will be 2 cookies. (added and from server)
		testMe3.getData();

		testServer.stopServer();

		// test first request
		Map<String, String> headers = testServer.getHeaders(0);
		testCookies(headers, null);

		// test second request
		headers = testServer.getHeaders(1);
		testCookies(headers, Arrays.asList(cookies));

		// test third request
		headers = testServer.getHeaders(2);
		testCookies(headers, Arrays.asList(cookies, paramName2 + "="
				+ paramValue2));

		// test fourth request(same list of cookies for other login)
		headers = testServer.getHeaders(3);
		testCookies(headers, null);

		// test fifth request
		headers = testServer.getHeaders(4);
		testCookies(headers, Arrays.asList(cookies, paramName2 + "="
				+ paramValue2));

		// one more test, to test only custom added cookies
		testServer = new TestServer(port, null, 3);
		startServer(testServer);

		// no cookies
		V1APIConnector testMe4 = new V1APIConnector("http://127.0.0.1:" + port,
				"foo1", "bar");
		// first request. we use another domen (no cookies from the server)
		testMe4.getData();
		testMe4.getCookiesJar().addCookie(paramName2, paramValue2, expireDate);
		// second request. will be only custom added cookies.
		testMe4.getData();

		// create connection with already requested domen
		V1APIConnector testMe5 = new V1APIConnector("http://localhost:" + port,
				"foo", "bar");
		// third request. we have to have 2 cookies
		testMe5.getData();

		// test first request
		headers = testServer.getHeaders(0);
		testCookies(headers, null);
		// test second request
		headers = testServer.getHeaders(1);
		testCookies(headers, Arrays.asList(paramName2 + "=" + paramValue2));
		// third request
		headers = testServer.getHeaders(2);
		testCookies(headers, Arrays.asList(cookies, paramName2 + "="
				+ paramValue2));
	}

	private void testCookies(Map<String, String> headers, List<String> cookies) {
		String tmpHeadCookies = headers.get("Cookie");
		if (tmpHeadCookies == null && cookies != null) {
			Assert.fail("No cookies");
		} else if (tmpHeadCookies == null && cookies == null) {
			return;
		}

		String[] actualCookies = tmpHeadCookies.split("; ");
		if (cookies == null || actualCookies.length != cookies.size()) {
			Assert.fail("Incorrect numbers of cookies. Have " + actualCookies.length + " cookies");
		}
		for (String actualCookie : actualCookies) {
			if (!cookies.contains(actualCookie)) {
				Assert.fail("Cookie '" + actualCookie + "' is not expected.");
			}
		}
	}

	@Test
	public void testCookiesManger2() {
		Date expireDate = new Date();
		expireDate.setTime(new Date().getTime() + 1000000);
		String name1 = "name1";
		String value1 = "value1";
		String name2 = "name2";
		String value2 = "value2";
		V1APIConnector testMe = new V1APIConnector("http://localhost/test",
				"foo", "bar");
		ICookiesManager cookiesManager = testMe.getCookiesJar();
		cookiesManager.addCookie(name1, value1, expireDate);
		cookiesManager.addCookie(name2, value2, expireDate);
		Assert.assertEquals(value1, cookiesManager.getCookie(name1));
		Assert.assertEquals(value2, cookiesManager.getCookie(name2));
		cookiesManager.deleteCookie(name1);
		Assert.assertEquals(null, cookiesManager.getCookie(name1));
		Assert.assertEquals(value2, cookiesManager.getCookie(name2));
	}

	@Test
	public void testReSetCookies() throws ConnectionException {
		Date expireDate = new Date();
		expireDate.setTime(new Date().getTime() + 1000000);
		String nameCookie = "testReSetCookies";
		String cookie = "my_custom_cookies";
		String anotherCookie = "my_changed_custom_cookies";
		int port = 4444;
		TestServer testServer = new TestServer(port, Arrays.asList(nameCookie + "=" + cookie), 5);
		startServer(testServer);

		V1APIConnector testMe = new V1APIConnector("http://localhost:" + port,
				"foo", "bar");
		testMe.getCookiesJar().deleteAllCookies();
		// first request (no cookies)
		testMe.getData();
		// second request (only cookie from server)
		testMe.getData();
		// set cookie with same name but with other value
		testMe.getCookiesJar().addCookie(nameCookie, anotherCookie, expireDate);
		// test another cookies with same name
		testServer.addCookies(nameCookie, anotherCookie);
		// third request (re-set cookie has to be used)
		testMe.getData();

		Map<String, String> headers = testServer.getHeaders(0);
		testCookies(headers, null);
		// test second request
		headers = testServer.getHeaders(1);
		testCookies(headers, Arrays.asList(nameCookie + "=" + cookie));
		// third request
		headers = testServer.getHeaders(2);
		testCookies(headers, Arrays.asList(nameCookie + "="
				+ anotherCookie));

	}

	@Test(expected = ConnectionException.class)
	public void testEmptyUserAfterValid() throws ConnectionException {
		V1APIConnector testMe = new V1APIConnector(V1_PATH, "admin", "admin");
		testMe.getData("rest-1.v1/Data/Scope/0");
		testMe = new V1APIConnector(V1_PATH, "", "");
		testMe.getData("rest-1.v1/Data/Scope/0");// throws ConnectionException
	}

	private class TestServer extends Thread {
		public volatile boolean isNotRun = true;
		final String HTTP = "HTTP/1.0 ";
		private final List<String> cookies = new LinkedList<String>();
		private final int port;
		private final int requestNumbers;
		private ServerSocket serverSocket;
		private final List<Map<String, String>> headers;

		public TestServer(int port, List<String> cookies, int requestNumbers) {
			if (cookies != null) {
				this.cookies.addAll(cookies);
			}
			this.requestNumbers = requestNumbers;
			this.port = port;
			headers = new ArrayList<Map<String, String>>(requestNumbers);
		}

		public void addCookies(String name, String value) {
			this.cookies.add(name + "=" + value);
		}

		public void run() {
			serverSocket = null;
			Socket clientSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				System.out.println("Could not listen on port: " + port + ". "
						+ e.getMessage());
				System.exit(-1);
			}

			isNotRun = false;
			try {
				for (int i = 0; i < requestNumbers; i++) {
					clientSocket = null;
					out = null;
					in = null;

					clientSocket = serverSocket.accept();
					out = new PrintWriter(clientSocket.getOutputStream(), true);

					in = new BufferedReader(new InputStreamReader(clientSocket
							.getInputStream()));
					headers.add(getHeaderParams(in));

					// out.println(HTTP + result);
					out.println(HTTP + "200 OK");
					if (cookies != null && cookies.size() > 0) {
						out.println("Set-Cookie:" + getCookies());
					}
					out.println("Content-Type:text/plain");
					out.println("<test>ok</test>");
					out.close();
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			} finally {
				if (out != null) {
					out.close();
				}
				try {
					if (in != null) {
						in.close();
					}
					if (clientSocket != null) {
						clientSocket.close();
					}
				} catch (Exception ex) {
				}

				stopServer();
			}
		}

		public Map<String, String> getHeaders(int request) {
			return headers.get(request);
		}

		public void stopServer() {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (Exception ex) {
			}
		}

		private String getCookies() {
			String result = "";
			for (String cookie : cookies) {
				result += cookie + "; ";
			}

			if (result.length() > 0) {
				return result.substring(0, result.length() - 2);
			} else {
				return "";
			}
		}

		private Map<String, String> getHeaderParams(BufferedReader in)
				throws IOException {
			Map<String, String> headers = new HashMap<String, String>();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("")) {
					break;
				} else if (!inputLine.contains(": ")) {
					continue;
				}
				String[] data = inputLine.split(": ");
				headers.put(data[0], data[1]);
			}
			return headers;
		}
	}
}
