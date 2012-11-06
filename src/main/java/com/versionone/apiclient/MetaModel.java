package com.versionone.apiclient;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Concrete class for obtaining metadata from the VersionOne server
 * @author jerry
 *
 */
public class MetaModel implements IMetaModel {
	private Map<String, Object> _map = new HashMap<String, Object>();
	private IAPIConnector _connector;
	private Version _version;
	private String _versionString = null;

	/**
	 * Create from a connection and obtain meta-data as needed
	 * @param connector
	 */
	public MetaModel(IAPIConnector connector) {
		this(connector, false);
	}

	/**
	 * Create from a connection and pre-load meta data
	 * @param connector
	 * @param hookup
	 */
	public MetaModel(IAPIConnector connector, boolean hookup) {
		_connector = connector;
		if (hookup) {
			hookup();
		}
	}

	/**
	 * Get an asset type based on a token
	 * @see IMetaModel#getAssetType(String)
	 */
	public IAssetType getAssetType(String token) throws MetaException {
		try {
			return findAssetType(token);
		} catch (Exception ex) {
			throw new MetaException("Unknown AssetType", token, ex);
		}
	}

	/**
	 * Get MetaMode version
	 * @return Version of MetaModel
	 * @throws MetaException
	 */
	public Version getVersion() throws MetaException {
		if (_version == null) {
			if (_versionString == null)
				getAssetType("BaseAsset");

			if (_versionString != null)
				_version = new Version(_versionString);
		}
		return _version;
	}

	/**
	 * Get an attribute definition from a token
	 * @see IMetaModel#getAttributeDefinition(String)
	 */
	public IAttributeDefinition getAttributeDefinition(String token) throws MetaException {
		try {
			return findAttributeDefinition(token);
		} catch (Exception ex) {
			throw new MetaException("Unknown AttributeDefinition", token, ex);
		}
	}

	/**
	 * Get an operation based on a token
	 * @see IMetaModel#getOperation(String)
	 */
	public IOperation getOperation(String token) throws MetaException {
		try {
			return findOperation(token);
		} catch (Exception ex) {
			throw new MetaException("Unknown Operation", token, ex);
		}
	}

	private IAssetType findAssetType(String token) throws Exception {
		if (_map.containsKey(token)) {
			return (IAssetType) _map.get(token);
		}
		return hookupAssetType(token);
	}

	private void saveAssetType(IAssetType assettype) {
		_map.put(assettype.getToken(), assettype);
	}

	private IAttributeDefinition findAttributeDefinition(String token) throws Exception {
		StringBuffer prefix = new StringBuffer();
		StringBuffer suffix = new StringBuffer();
		TextBuilder.splitPrefix(token, '.', prefix, suffix);
		
		findAssetType(prefix.toString());

		if (_map.containsKey(token)) {
			return (IAttributeDefinition) _map.get(token);
		}

		return hookupAttributeDefinition(prefix.toString(), suffix.toString());
	}

	private void saveAttributeDefinition(IAttributeDefinition attribdef) {
		_map.put(attribdef.getToken(), attribdef);
	}

	private IOperation findOperation(String token) throws Exception {

		StringBuffer prefix = new StringBuffer();
		StringBuffer suffix = new StringBuffer();
		TextBuilder.splitPrefix(token, '.', prefix, suffix);
		findAssetType(prefix.toString());
		if (_map.containsKey(token)) {
			return (IOperation) _map.get(token);
		}
		return hookupOperation(prefix.toString(), suffix.toString());
	}

	private void saveOperation(IOperation op) {
		_map.put(op.getToken(), op);
	}

	private IAssetType hookupAssetType(String token) throws Exception {

		Document doc = null;

		doc = createDocument(token);

		AssetType assetType = new AssetType(this, doc.getDocumentElement(), _map);
		saveAssetType(assetType);

		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList attribnodes = (NodeList)xpath.evaluate("AttributeDefinition", doc.getDocumentElement(), XPathConstants.NODESET);	
		for (int attrIndex = 0; attrIndex < attribnodes.getLength(); ++attrIndex)
			saveAttributeDefinition(new AttributeDefinition(this, (Element) attribnodes.item(attrIndex)));

		NodeList opnodes = (NodeList)xpath.evaluate("Operation", doc.getDocumentElement(), XPathConstants.NODESET);
		for (int opIndex = 0; opIndex < opnodes.getLength(); ++opIndex)
			saveOperation(new Operation(this, assetType.getToken(), (Element) opnodes.item(opIndex)));

		return assetType;
	}

	private IAttributeDefinition hookupAttributeDefinition(String assettypetoken, String name) throws Exception {
		Document doc = createDocument(assettypetoken + "/" + name);
		AttributeDefinition attribdef = new AttributeDefinition(this, doc.getDocumentElement());
		saveAttributeDefinition(attribdef);
		return attribdef;
	}

	private IOperation hookupOperation(String assettypetoken, String name) throws V1Exception {
		Document doc = this.createDocument(assettypetoken + "/" + name);
		Operation op = new Operation(this, doc.getDocumentElement());
		saveOperation(op);
		return op;
	}

	private void hookup() {
		try {
			Document doc = this.createDocument("");

			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList assetnodes = (NodeList)xpath.evaluate("//AssetType", doc.getDocumentElement(), XPathConstants.NODESET);
			for (int assetIndex = 0; assetIndex < assetnodes.getLength(); ++assetIndex) {
				Element element = (Element) assetnodes.item(assetIndex);
				saveAssetType(new AssetType(this, element, _map));

				NodeList attribnodes = element.getElementsByTagName("AttributeDefinition");
				for (int attribIndex = 0; attribIndex < attribnodes.getLength(); ++attribIndex) {
					Element attribelement = (Element) attribnodes.item(attribIndex);
					saveAttributeDefinition(new AttributeDefinition(this, attribelement));
				}

				NodeList opnodes = element.getElementsByTagName("Operation");
				for (int opIndex = 0; opIndex < opnodes.getLength(); ++opIndex) {
					Element opelement = (Element) opnodes.item(opIndex);
					saveOperation(new Operation(this, element.getAttribute("token"), opelement));
				}
			}
		} catch (Exception e) {
		}
	}
	
	private Document createDocument(String token) throws V1Exception {
		Reader reader = null;
		Document rc = null;
		try {
			reader = _connector.getData(token);
			rc = XMLHandler.buildDocument(reader, token);
			_versionString = rc.getDocumentElement().getAttribute("version").toString();
		} catch (ConnectionException e) {
			throw new MetaException("Error creating Document", token, e);
		}
		finally {
			if(null != reader){try {reader.close();} catch (IOException e) {}}
		}
		return rc;		
	}
}