package com.versionone.apiclient.interfaces;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import com.versionone.apiclient.exceptions.ConnectionException;

/**
 * @deprecated This interface has been deprecated. Please use V1Connector
 *             instead.
 */
@Deprecated
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
	 * @param path String
	 * @return Reader The stream for reading data
	 * @throws ConnectionException if any connection problems occur
	 */
	Reader getData(String path) throws ConnectionException;

	/**
 	 * Read data from the path provided
	 *
	 * Note: Caller is responsible for closing the returned stream 
	 * 
	 * @param attachmentKey
	 * @return InputStream The stream for reading data
	 * @throws ConnectionException
	 */
	InputStream getAttachment(String attachmentKey) throws ConnectionException;
	
	/**
	 * Send data to the path
	 *
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @param path - String
	 * @param data - String
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
