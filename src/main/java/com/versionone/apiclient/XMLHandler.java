package com.versionone.apiclient;

import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This class centralizes the process of reading XML to create a DOM document
 * @author jerry
 *
 */
class XMLHandler {

	/**
	 * 
	 * @param reader
	 * @param url
	 * @return
	 * @throws APIException
	 * @throws ConnectionException if connection failed
	 */
	public static Document buildDocument(Reader reader, String url) throws APIException, ConnectionException {
		try {
			DocumentBuilder builder = createDocumentBuilder();
			Document doc = builder.parse(new InputSource(reader));
			return doc;
		} catch (ParserConfigurationException e) {
			throw new APIException("Parser Configuration Error ", url, e);
		} catch (IOException e) {
			throw new ConnectionException("IO Error: "+url, e);
		} catch (SAXException e) {
			throw new APIException("SAX Error ", url, e);
		}
		finally {
			if(null != reader){try {reader.close();} catch (IOException e) {}}
		}
	}

	/**
	 * Create the Xerces Document Builder
	 * @return
	 * @throws ParserConfigurationException
	 */
	public static DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder;
	}
}