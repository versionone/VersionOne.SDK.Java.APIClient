package com.versionone.apiclient;

import java.io.Reader;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface that Connector classes must implement
 * @author jerry
 */
public interface IAPIConnector {

	/**
	 * Read data from the root of the connection
	 *
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @return the stream for reading data
         * @throws ConnectionException if any connection problems occur
	 */
	Reader getData() throws ConnectionException;

	/**
	 * Read data from the path provided
	 *
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @param path
	 * @return the stream for reading data
         * @throws ConnectionException if any connection problems occur
	 */
	Reader getData(String path) throws ConnectionException;

	/**
	 * Send data to the path
	 *
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @param path
	 * @param data
	 * @return the response in a stream
	 * @throws ConnectionException if any connection problems occur
	 */
	Reader sendData(String path, String data) throws ConnectionException;

	/**
	 * Creating stream for writing data
	 *
	 * @param path path to the data on server
	 * @param contentType Content-type of HTTP header
	 * @return the stream for writing data
         * @throws ConnectionException if any connection problems occur
	 */
	OutputStream beginRequest(String path, String contentType) throws ConnectionException;

	/**
	 * Getting response from request
	 *
	 * @param path path to the data on server
	 * @return the stream for reading data
         * @throws ConnectionException if any connection problems occur
	 */
	InputStream endRequest(String path) throws ConnectionException;
}
