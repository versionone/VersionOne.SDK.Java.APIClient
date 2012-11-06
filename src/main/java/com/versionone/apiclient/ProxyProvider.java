package com.versionone.apiclient;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;

public class ProxyProvider {

	private final URI address;
	private final String userName;
	private final String password;

	/**
	 * Create proxy for connection.
	 * @param address host and port for proxy server.
	 * @param userName user name for proxy.
	 * @param password password for proxy.
	 */
	public ProxyProvider(URI address, String userName, String password) {
		this.address = address;
		this.userName = userName;
		this.password = password;
	}

	protected java.net.Proxy getProxyObject() {
		java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(address.getHost(), address.getPort()));
		return proxy;
	}

	protected void addAuthorizationToHeader(HttpURLConnection request) {
		String encoded = new String
	      (Base64Encode.base64Encode(new String(userName + ":" + password)));
		request.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
	}

	/**
	 *  * This code handles Base64 encoding for basic authentication
	 * and the like
	 */
	private static class Base64Encode {

	    /**
	     * the encode alphabet
	     */
		private final static char base64Array [] = {
		      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		      'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
		      'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
		      'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
		      'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
		      'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		      'w', 'x', 'y', 'z', '0', '1', '2', '3',
		      '4', '5', '6', '7', '8', '9', '+', '/'
		  };



	    /**
	     * Encode a block of binary data as base64 as specified in RFC1521.
	     *
	     * @param data the binary data to encode.
	     * @return An array of characters that represent the data encoded
	     *      as Base64
	     */
		  private static String base64Encode(String string) {
			String encodedString = "";
			byte bytes[] = string.getBytes();
			int i = 0;
			int pad = 0;
			while (i < bytes.length) {
				byte b1 = bytes[i++];
				byte b2;
				byte b3;
				if (i >= bytes.length) {
					b2 = 0;
					b3 = 0;
					pad = 2;
				} else {
					b2 = bytes[i++];
					if (i >= bytes.length) {
						b3 = 0;
						pad = 1;
					} else
						b3 = bytes[i++];
				}
				byte c1 = (byte) (b1 >> 2);
				byte c2 = (byte) (((b1 & 0x3) << 4) | (b2 >> 4));
				byte c3 = (byte) (((b2 & 0xf) << 2) | (b3 >> 6));
				byte c4 = (byte) (b3 & 0x3f);
				encodedString += base64Array[c1];
				encodedString += base64Array[c2];
				switch (pad) {
				case 0:
					encodedString += base64Array[c3];
					encodedString += base64Array[c4];
					break;
				case 1:
					encodedString += base64Array[c3];
					encodedString += "=";
					break;
				case 2:
					encodedString += "==";
					break;
				}
			}
			return encodedString;
		}


	}

}
