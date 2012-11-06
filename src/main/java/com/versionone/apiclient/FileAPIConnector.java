package com.versionone.apiclient;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * IAPIConnector that reads data from a file.
 *
 * @author Jerry D. Odenwelder.
 *
 */
public class FileAPIConnector implements IAPIConnector {

	/**
	 * Get Elements only contain a response
	 * Key is the path
	 * Value is the XML response for that path
	 */
	HashMap<String, String> _getData = new HashMap<String, String>();

	/**
	 * Post Elements contain request and response
	 * Key is the path
	 * Value is an array of Strings where element 0 contains the request and element 1 contains the response
	 */
	HashMap<String, String[]> _postData = new HashMap<String, String[]>();
	private static int REQUEST_INDEX = 0;
	private static int RESPONSE_INDEX = 1;

	private String _prefix = "";

	private String _lastPath;
	private String _lastData;

	/**
	 * Create Connector
	 * @param fileName - fully qualified filename
	 */
	public FileAPIConnector(String fileName, String prefix) {

		if( !(prefix.endsWith("/") || prefix.endsWith("\\")))
			_prefix = prefix + "/";
		else
			_prefix = prefix;

		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(fileName));
			doc.normalizeDocument();

			NodeList nodes = doc.getDocumentElement().getChildNodes();
			for(int i = 0; i < nodes.getLength(); ++i) {
				Node oneNode = nodes.item(i);
				if(oneNode.getNodeName().equals("Get")) {
					processGetNode((Element) oneNode);
				} else if(oneNode.getNodeName().equals("Post")) {
					processPostNode((Element) oneNode);
				}
			}
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Parser Configuration Error ", e);
		} catch (IOException e) {
			throw new RuntimeException("IO Error ", e);
		} catch (SAXException e) {
			throw new RuntimeException("SAX Error ", e);
		}
	}

	private void processPostNode(Element postNode) {
		String[] data = {"", ""};

		NodeList nodes = postNode.getChildNodes();
		for(int i = 0; i < nodes.getLength(); ++i) {
			Node oneNode = nodes.item(i);
			if(oneNode.getNodeName().equals("Request")) {
				data[REQUEST_INDEX]= oneNode.getTextContent().trim();
			} else if (oneNode.getNodeName().equals("Response")) {
				data[RESPONSE_INDEX]= oneNode.getTextContent().trim();
			}
		}
		_postData.put(postNode.getAttribute("path"), data);
	}

	private void processGetNode(Element oneNode) {
		_getData.put(oneNode.getAttribute("path"), oneNode.getTextContent().trim());
	}

	public Reader getData() throws ConnectionException {
		return getData("");
	}

	public Reader getData(String path) throws ConnectionException {
		_lastPath = _prefix + path;

		if(_getData.containsKey(_lastPath )) {
			_lastData = _getData.get(_lastPath);
			return new StringReader(_lastData);
		}
		throw new ConnectionException("Cannot get data for " + path, 401);
	}

	public Reader sendData(String path, String data) throws ConnectionException {
		_lastPath = _prefix + path;
		if(this._postData.containsKey(_lastPath)) {
			String[] postData = this._postData.get(_lastPath);
			compareRequest(data, postData[REQUEST_INDEX]);
			_lastData = postData[RESPONSE_INDEX];
			return new StringReader(_lastData);
		} else {
			throw new ConnectionException("Cannot get data for " + path, 401);
		}
	}

	private void compareRequest(String expected, String actual) throws ConnectionException {
		String trimmedExpected = expected.trim().replaceAll("\\s", "");
		String trimmedActual = actual.trim().replaceAll("\\s", "");
		if(!trimmedExpected.equals(trimmedActual)) {
			throw new ConnectionException("Invalid Request", 400);
		}
	}

	public String getLastPath() {
		return _lastPath;
	}

	public String getLastData() {
		return _lastData;
	}

	/**
	 * Operation not supported
	 */
	public OutputStream beginRequest(String path, String contentType) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Operation not supported
	 */
	public InputStream endRequest(String path) {
		throw new UnsupportedOperationException();
	}
}
