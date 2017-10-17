package com.versionone.apiclient;

import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;

/**
 * This class centralizes the process of reading XML to create a DOM document
 */
public class XMLHandler {

	/**
	 * @param reader Reader
	 * @param url String
	 * @return Document Document
	 * @throws APIException APIException
	 * @throws ConnectionException if connection failed
	 */
	public static Document buildDocument(Reader reader, String url) throws APIException, ConnectionException {
		try {
			DocumentBuilder builder = createDocumentBuilder();
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			Document doc = builder.parse(is);

			return doc;
		} catch (ParserConfigurationException e) {
			throw new APIException("Parser Configuration Error ", url, e);
		} catch (IOException e) {
			throw new ConnectionException("IO Error: "+url, e);
		} catch (SAXException e) {
			throw new APIException("SAX Error ", url, e);
		}
		finally {
			if(null != reader){
				try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
	}

	/**
	 * Create the Xerces Document Builder
	 * 
	 * @return DocumentBuilder DocumentBuilder
	 * @throws ParserConfigurationException ParserConfigurationException
	 */
	public static DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		factory.setExpandEntityReferences(false);
		factory.setValidating(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder;
	}
}