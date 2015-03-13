package com.versionone.apiclient.unit.tests;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.interfaces.IAPIConnector;

public class ResponseConnector implements IAPIConnector {

	public interface IResolveContent {
		String resolve(Node node);
	}

	private Map<String, String> _data = new HashMap<String, String>();
	private String _prefix = "";

	private String _lastPath;
	private String _lastData;

	public ResponseConnector(String datafile, String prefix, String keys, IResolveContent resolver)
	{
		_prefix = prefix;

		if (keys == null || keys.equals(""))
			return;

		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(datafile));
			doc.normalizeDocument();

			String[] parts = keys.split(";");

			XPath xpath = XPathFactory.newInstance().newXPath();
			for(String part : parts)
			{
				NodeList nodes = (NodeList)xpath.evaluate("Test[@name='" + part + "']", doc.getDocumentElement(), XPathConstants.NODESET);
				if (nodes.getLength() == 0)
					continue;
				Node node = nodes.item(0);
				NodeList responses =(NodeList)xpath.evaluate("Response", node, XPathConstants.NODESET);
				for(int i = 0; i < responses.getLength(); ++i) {
					Element response = (Element) responses.item(i);
					String value = resolver.resolve(response);

					_data.put(response.getAttribute("path"), value);
				}
			}
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Parser Configuration Error ", e);
		} catch (IOException e) {
			throw new RuntimeException("IO Error ", e);
		} catch (SAXException e) {
			throw new RuntimeException("SAX Error ", e);
		} catch (XPathExpressionException e) {
			throw new RuntimeException("XPath exception", e);
		}
	}

	public Reader getData() throws ConnectionException {
		return getData("");
	}

	public Reader getData(String path) throws ConnectionException {
		_lastPath = _prefix + path;

		if(this._data.containsKey(_lastPath )) {
			_lastData = _data.get(_lastPath);
			return new StringReader(_lastData);
		}
		throw new ConnectionException("Cannot get data for " + path, 401);
	}

	public Reader sendData(String path, String data) throws ConnectionException {
		_lastPath = path;
		return findData(_prefix + path);
	}

	private Reader findData(String path) {
		_lastData = _data.get(path);
		if (_lastData == null)
			throw new RuntimeException("Response Connector missing data for path: " + path);
		return new StringReader(_lastData);
	}

	public static class XMLResponseConnector extends ResponseConnector {
		public XMLResponseConnector(String datafile, String prefix, String key) {
			super(datafile, prefix, key, new IResolveContent(){

					public String resolve(Node node) {
						StringBuffer rc = new StringBuffer();
						NodeList children = node.getChildNodes();
						for(int i=0;i<children.getLength();++i)
							resolveChildren(children.item(i), rc);
						return rc.toString();
					}

					private void resolveChildren(Node node, StringBuffer dest) {
						if(node instanceof Text) {
					        String text = "";
			                if (null != node.getNodeValue())
			                	text = node.getNodeValue();
			                else if (null != node.getTextContent())
			                	text = node.getTextContent();
					        dest.append(text.trim());
							return;
						}
						NamedNodeMap attributes = node.getAttributes();
				        dest.append('<');
				        dest.append(node.getNodeName());
				        if(attributes != null) {
				            int attributeCount = attributes.getLength();
				            for (int i = 0; i < attributeCount; i++) {
				                Attr attribute = (Attr)attributes.item(i);
				                dest.append(' ');
				                dest.append(attribute.getNodeName());
				                dest.append("=\"");
				                dest.append(attribute.getNodeValue());
				                dest.append('"');
				            }
				        }
				        dest.append('>');
				        if(node.hasChildNodes());
				        	dest.append(resolve(node));
				        dest.append("</");
				        dest.append(node.getNodeName());
				        dest.append(">");
					}
			});
		}
	}

	public String getLastPath() {
		return _lastPath;
	}

	public String getLastData() {
		return _lastData;
	}

    /**
     * Operation is not supported
     */
    public OutputStream beginRequest(String path, String contentType) {
        throw new UnsupportedOperationException();
    }

    /**
     * Operation is not supported
     */
    public InputStream endRequest(String path) {
        throw new UnsupportedOperationException();
    }
}