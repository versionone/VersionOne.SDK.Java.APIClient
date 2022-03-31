package com.versionone.apiclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.versionone.DB;
import com.versionone.Oid;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.NotImplementedException;
import com.versionone.apiclient.exceptions.OidException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.filters.FilterTerm;
import com.versionone.apiclient.interfaces.IAPIConnector;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IMetaModel;
import com.versionone.apiclient.interfaces.IOperation;
import com.versionone.apiclient.interfaces.IServices;
import com.versionone.apiclient.services.QueryResult;
import com.versionone.apiclient.services.QueryURLBuilder;
import com.versionone.utils.V1Util;

/**
 * Wraps the services available in the VersionOne API
 */
public class Services implements IServices {

	private IMetaModel _meta;
	private IAPIConnector _connector;
	private Oid _loggedin;
	private V1Connector _v1Connector;

	public IMetaModel getMeta() {
		return _meta;
	}

	public IAPIConnector get_connector() {
		return _connector;
	}

	public Oid get_loggedin() {
		return _loggedin;
	}

	public V1Connector getV1Connector() {
		return _v1Connector;
	}

	/**
	 * @param metaModel
	 *            - VersionOne MetaModel
	 * @param connector
	 *            - Connection to the server
	 */
	public Services(IMetaModel metaModel, IAPIConnector connector) {
		_meta = metaModel;
		_connector = connector;
	}

	public Services(V1Connector v1Connector) throws NullArgumentException {
		if (v1Connector == null)
			throw new NullArgumentException("v1Connector");

		_v1Connector = v1Connector;
		_meta = new MetaModel(_v1Connector);
	}

	public Services(V1Connector connector, IMetaModel metaModel) throws NullArgumentException {
		if (connector == null)
			throw new NullArgumentException("connector");
		if (metaModel == null)
			throw new NullArgumentException("metaModel");

		_v1Connector = connector;
		_meta = metaModel;
	}

	public Services(V1Connector connector, boolean preLoadMeta) throws NullArgumentException {
		if (connector == null)
			throw new NullArgumentException("connector");

		_v1Connector = connector;
		_meta = new MetaModel(connector, preLoadMeta);
	}

	/**
	 * Query VersionOne
	 *
	 * @see IServices#retrieve(Query)
	 */
	public QueryResult retrieve(Query query) throws ConnectionException, APIException, OidException {
		String queryUrl = null;
		Reader reader = null;
		try {
			if (_connector != null) {
				queryUrl = new QueryURLBuilder(query, false).toString();
				reader = _connector.getData(queryUrl);
			} else {
				queryUrl = new QueryURLBuilder(query, true).toString();
				if (query.isHistorical()) {
					_v1Connector.useHistoryAPI();
				} else {
					_v1Connector.useDataAPI();
				}
				reader = _v1Connector.getData(queryUrl);
			}
			Document doc = XMLHandler.buildDocument(reader, queryUrl);
			Element root = doc.getDocumentElement();
			removeEmptyTextNodes(root);
			return parseQueryResult(root, query);

		} catch (ConnectionException ex) {
			if (ex.getServerResponseCode() == 404) {
				return getEmptyQueryResult(query);
			}
			throw ex;
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}

	private QueryResult getEmptyQueryResult(Query query) {
		return new QueryResult(new Asset[] {}, 0, query);
	}

	/**
	 * Create new Asset
	 *
	 * @see IServices#createNew(IAssetType, Oid)
	 */
	public Asset createNew(IAssetType assetType, Oid context) throws V1Exception {

		String path = (_connector != null) ? "New/" + assetType.getToken() : assetType.getToken();

		if (context != null && !context.isNull())
			path += "?ctx=" + context.getToken();

		Reader reader = null;
		try {
			if (_connector != null) {
				reader = _connector.getData(path);
			} else {
				_v1Connector.useNewAPI();
				reader = _v1Connector.getData(path);
			}

			Document doc = XMLHandler.buildDocument(reader, path);
			Element root = doc.getDocumentElement();
			removeEmptyTextNodes(root);
			return parseNewAssetNode(doc.getDocumentElement(), assetType);
		} catch (Exception ex) {
			throw new APIException("Failed to create new asset!", assetType.getToken(), ex);
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Execute an operation on the server
	 *
	 * @see IServices#executeOperation(IOperation, Oid)
	 */
	public Oid executeOperation(IOperation op, Oid oid) throws APIException {

		String path = (_connector != null) ? "Data/" + oid.getAssetType().getToken() + "/" + oid.getKey().toString() + "?op=" + op.getName() : oid
				.getAssetType().getToken() + "/" + oid.getKey().toString() + "?op=" + op.getName();

		Reader reader = null;
		try {
			if (_connector != null) {
				reader = _connector.sendData(path, "");
			} else {
				_v1Connector.useDataAPI();
				reader = _v1Connector.sendData(path, "");
			}
			Document doc = XMLHandler.buildDocument(reader, path);
			Element root = doc.getDocumentElement();
			removeEmptyTextNodes(root);
			Asset asset = parseAssetNode(root);
			return asset.getOid();
		} catch (Exception ex) {
			throw new APIException("Error executing Operation!", op.getName(), ex);
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Get the asset type from an token
	 *
	 * @see IServices#getAssetType(String)
	 */
	public IAssetType getAssetType(String token) throws MetaException {
		return _meta.getAssetType(token);
	}

	/**
	 * Get the attribute definition
	 *
	 * @see IServices#getAttributeDefinition(String)
	 */
	public IAttributeDefinition getAttributeDefinition(String token) throws MetaException {
		return _meta.getAttributeDefinition(token);
	}

	/**
	 * Get the Object Identifier from a token
	 *
	 * @see IServices#getOid(String)
	 */
	public Oid getOid(String token) throws OidException {
		return Oid.fromToken(token, _meta);
	}

	/**
	 * Get an operation from a token
	 *
	 * @see IServices#getOperation(String)
	 */
	public IOperation getOperation(String token) throws MetaException {
		return _meta.getOperation(token);
	}

	/**
	 * Get the Oid of the current logged in user
	 *
	 * @see IServices#getLoggedIn()
	 */
	public Oid getLoggedIn() throws APIException, ConnectionException, OidException {
		if (_loggedin == null) {
			Query q = new Query(getAssetType("Member"));
			FilterTerm term = new FilterTerm(getAttributeDefinition("Member.IsSelf"));
			term.equal(true);
			q.setFilter(term);
			QueryResult result = retrieve(q);
			if (result.getAssets().length != 1)
				_loggedin = Oid.Null;
			else
				_loggedin = result.getAssets()[0].getOid();
		}
		return _loggedin;
	}

	/**
	 * Save an asset with no comment
	 *
	 * @see IServices#save(Asset)
	 */
	public void save(Asset asset) throws APIException, ConnectionException {
		save(asset, "");
	}

	/**
	 * Save an asset with comment
	 *
	 * @see IServices#save(Asset, String)
	 */
	public void save(Asset asset, String comment) throws APIException, ConnectionException {

		if (asset.hasChanged() || asset.getOid().isNull()) {

			StringWriter assetData = new StringWriter();
			XmlApiWriter writer = new XmlApiWriter(true);
			writer.write(asset, assetData);
			String data = assetData.toString();
			if (data.startsWith("<?xml")) {
				data = data.substring(data.indexOf("?>") + 2);
			}

			String path = _connector != null ? "Data/" + asset.getAssetType().getToken() : asset.getAssetType().getToken();

			if (!asset.getOid().isNull())
				path += "/" + asset.getOid().getKey().toString();

			if (comment != null && !comment.equals("")) {
				try {
					comment = URLEncoder.encode(comment, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					comment = comment.replace(" ", "%20");
					comment = comment.replace("&", "%26");
					comment = comment.replace("#", "%23");
				}
				path += String.format("?Comment='%s'", comment);
			}

			Reader reader = null;
			try {
				if (_connector != null) {
					reader = _connector.sendData(path, data);
				} else {
					_v1Connector.useDataAPI();
					reader = _v1Connector.sendData(path, data);
				}
				Document doc = XMLHandler.buildDocument(reader, path);
				Element root = doc.getDocumentElement();
				removeEmptyTextNodes(root);
				parseSaveAssetNode(root, asset);
			} catch (OidException e) {
				throw new APIException("Error processing response", e);
			} finally {
				if (null != reader) {
					try {
						reader.close();
					} catch (Exception e) {
					}
				}
			}
		}

		Iterator<Asset> newAssetIter = asset.getNewAssets().values().iterator();
		while (newAssetIter.hasNext()) {
			save(newAssetIter.next());
		}
	}

	/**
	 * Save an array of assets without comment
	 *
	 * @see IServices#save(Asset[])
	 */
	public void save(Asset[] assets) throws V1Exception {
		for (Asset asset : assets)
			save(asset);
	}

	private QueryResult parseQueryResult(Element element, Query query) throws APIException, OidException {
		if ("History".equals(element.getNodeName())) {
			return parseHistoryQueryResult(element, query);
		} else if ("Assets".equals(element.getNodeName())) {
			return parseAssetListQueryResult(element, query);
		} else if ("Asset".equals(element.getNodeName())) {
			return parseAssetQueryResult(element, query);
		} else if ("Attribute".equals(element.getNodeName())) {
			return parseAttributeQueryResult(element, query);
		} else if ("Relation".equals(element.getNodeName())) {
			return parseAttributeQueryResult(element, query);
		} else {
			return new QueryResult(query);
		}
	}

	private QueryResult parseAttributeQueryResult(Element element, Query query) throws APIException {
		List<Asset> list = new ArrayList<Asset>();

		Asset asset = new Asset(query.getOid());
		list.add(asset);

		IAttributeDefinition attribdef = getAttributeDefinition(query.getAssetType().getToken() + "." + element.getAttribute("name"));

		parseAttributeNode(asset, attribdef, element);

		return new QueryResult(assetArray(list), 1, query);
	}

	private void parseAttributeNode(Asset asset, IAttributeDefinition attribdef, Element element) throws APIException {
		String type = element.getNodeName();
		asset.ensureAttribute(attribdef);
		if ("Relation".equals(type)) {
			if (attribdef.isMultiValue()) {
				NodeList nodes = element.getChildNodes();
				for (int i = 0; i < nodes.getLength(); ++i) {
					Element child = (Element) nodes.item(i);
					String token = child.getAttribute("idref");

					boolean add = child.hasAttribute("act") && child.getAttribute("act").equals("add");
					if (!add)
						asset.loadAttributeValue(attribdef, token);
					else
						asset.addAttributeValue(attribdef, token);
				}
			} else {
				String token = Oid.Null.getToken();
				if (element.hasChildNodes())
					token = ((Element) element.getChildNodes().item(0)).getAttribute("idref");

				boolean force = element.hasAttribute("act") && element.getAttribute("act").equals("set");

				if (!force)
					asset.loadAttributeValue(attribdef, token);
				else
					asset.forceAttributeValue(attribdef, token);
			}
		} else {
			if (attribdef.isMultiValue()) {
				NodeList nodes = element.getChildNodes();
				for (int i = 0; i < nodes.getLength(); ++i) {
					Element child = (Element) nodes.item(i);

					boolean add = child.hasAttribute("act") && child.getAttribute("act").equals("add");

					if (!add)
						asset.loadAttributeValue(attribdef, child.getTextContent());
					else
						asset.addAttributeValue(attribdef, child.getTextContent());
				}
			} else {
				Object v = DB.Null;
				if (null != element.getNodeValue())
					v = element.getNodeValue();
				else if (null != element.getTextContent())
					v = element.getTextContent();

				boolean force = element.hasAttribute("act") && element.getAttribute("act").equals("set");

				if (!force)
					asset.loadAttributeValue(attribdef, V1Util.convertXmlCrToSystemCr(v.toString()));
				else
					asset.forceAttributeValue(attribdef, V1Util.convertXmlCrToSystemCr(v.toString()));
			}
		}
	}

	private QueryResult parseAssetQueryResult(Element element, Query query) throws OidException, APIException {
		List<Asset> list = new ArrayList<Asset>();
		list.add(parseAssetNode(element));
		return new QueryResult(assetArray(list), 1, query);
	}

	private Asset parseNewAssetNode(Element element, IAssetType assetType) throws APIException {
		Asset asset = new Asset(assetType);

		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			Element child = (Element) children.item(i);
			parseAttributeNode(asset, asset.getAssetType().getAttributeDefinition(child.getAttribute("name")), child);
		}
		return asset;
	}

	private Asset parseAssetNode(Element element) throws APIException, OidException {
		Asset asset = new Asset(getOid(element.getAttribute("id")));

		NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); ++i) {
			Element child = (Element) nodes.item(i);
			parseAttributeNode(asset, asset.getAssetType().getAttributeDefinition(child.getAttribute("name")), child);
		}
		return asset;
	}
	
	private void removeEmptyTextNodes(Element element) throws APIException{
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			// XPath to find empty text nodes.
			XPathExpression xpathExp = xpath.compile("//text()[normalize-space(.) = '']");
			NodeList emptyTextNodes = (NodeList) xpathExp.evaluate(element, XPathConstants.NODESET);
			// Remove each empty text node from document.
			for (int i = 0; i < emptyTextNodes.getLength(); i++) {
				Node emptyTextNode = emptyTextNodes.item(i);
				emptyTextNode.getParentNode().removeChild(emptyTextNode);
			}
		} catch (XPathExpressionException e) {
			throw new APIException("Error trying to remove empty text from nodes", e);
		}		
	}

	private QueryResult parseAssetListQueryResult(Element element, Query query) throws APIException, OidException {
		List<Asset> list = new ArrayList<Asset>();
		
		String totalValue = element.getAttribute("total");
		int total = -1;
		if (totalValue != null && !totalValue.isEmpty()) {
			total = Integer.parseInt(totalValue);
		}

		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodes;
		try {
			removeEmptyTextNodes(element);
			String nodeName = query.isHistorical() ? "History" : "Assets";
			nodes = (NodeList) xpath.compile(String.format("/%s/Asset", nodeName)).evaluate(element, XPathConstants.NODESET);
			
		} catch (XPathExpressionException e) {
			throw new APIException("Error reading nodes", "Asset", e);
		}

		for (int i = 0; i < nodes.getLength(); ++i) {
			list.add(parseAssetNode((Element) nodes.item(i)));
		}

		if (query.getParentRelation() != null)
			list = treeAssetListByAttribute(list, query.getParentRelation());

		return new QueryResult(assetArray(list), total, query);
	}

	private Asset[] assetArray(List<Asset> list) {
		Asset[] rc = new Asset[list.size()];
		return list.toArray(rc);
	}

	private List<Asset> treeAssetListByAttribute(List<Asset> input, IAttributeDefinition def) throws APIException {

		Map<String, Asset> h = new HashMap<String, Asset>();
		for (Asset asset : input)
			h.put(asset.getOid().getToken(), asset);

		List<Asset> r = new ArrayList<Asset>();
		for (Asset asset : input) {
			Asset parent = h.get(((Oid) asset.getAttribute(def).getValue()).getToken());
			List<Asset> t = parent != null ? parent.getChildren() : r;
			t.add(asset);
		}
		return r;
	}

	private QueryResult parseHistoryQueryResult(Element element, Query query) throws OidException, APIException {
		if (!element.hasChildNodes()) {
			return new QueryResult(query);
		}
		if (element.getFirstChild().getNodeName().equals("Asset")) {
			return parseAssetListQueryResult(element, query);
		}
		throw new NotImplementedException();
	}

	private void parseSaveAssetNode(Element element, Asset asset) throws OidException {
		asset.setOid(getOid(element.getAttribute("id")));
		asset.acceptChanges();
	}

	@Override
	public String executePassThroughQuery(String query) {
		_v1Connector.useQueryAPI();
		return _v1Connector.stringSendData(query, "application/json");
	}

	@Override
	public String getLocalization(IAttributeDefinition attribute) throws V1Exception {
		if (null != attribute) {
			return getStringData("?" + attribute.getDisplayName());
		} else {
			throw new NullArgumentException("IAttributeDefinition");
		}
	}

	/**
	 * @param joinedStrings
	 * @return
	 * @throws ConnectionException
	 */
	private String getStringData(String joinedStrings) throws ConnectionException {

		Reader stream;

		if (_connector != null) {
			stream = _connector.getData("loc.v1/" + joinedStrings);
		} else {
			_v1Connector.useLocAPI();
			stream = _v1Connector.getData(joinedStrings);
		}

		String result = null;
		try {
			result = IOUtils.toString(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getLocalization(String key) throws V1Exception {
		String path = "?" + key;
		return getStringData(path);
	}

	@Override
	public Map<String, String> getLocalization(ArrayList<IAttributeDefinition> attributes) throws ConnectionException {

		Map<String, String> locs = new HashMap<String, String>();
		List<String> data = new ArrayList<String>();
		String result = null;

		for (IAttributeDefinition iAttributeDefinition : attributes) {
			data.add("AttributeDefinition'" + iAttributeDefinition.getName() + "'" + iAttributeDefinition.getAssetType().getToken());
		}

		String path = "?[" + StringUtils.join(data, ",") + "]";

		Reader stream = null;
		if (_connector != null) {
			path = "loc-2.v1/" + path;
			stream = _connector.getData(path);
		} else {
			_v1Connector.useLoc2API();
			stream = _v1Connector.getData(path);
		}
		try {
			result = IOUtils.toString(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject(result);

		for (IAttributeDefinition iAttribute : attributes) {
			String param = "AttributeDefinition'" + iAttribute.getName() + "'" + iAttribute.getAssetType().getToken();
			locs.put(iAttribute.getToken(), jsonObject.getString(param));
		}
		return locs;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Oid saveAttachment(String filePath, Asset asset, String attachmentName) throws V1Exception, IOException {
		if (StringUtils.isEmpty(filePath))
             throw new NullArgumentException("filePath");
		
		File file = new File(filePath);
        if (!file.exists())
       	 	throw new FileNotFoundException(String.format("File \"%s\" does not exist.", filePath));
       
        String mimeType = MimeType.resolve(filePath);        
        IAssetType attachmentType = _meta.getAssetType("Attachment");
        IAttributeDefinition attachmentAssetDef = attachmentType.getAttributeDefinition("Asset");
        IAttributeDefinition attachmentContent = attachmentType.getAttributeDefinition("Content");
        IAttributeDefinition attachmentContentType = attachmentType.getAttributeDefinition("ContentType");
        IAttributeDefinition attachmentFileName = attachmentType.getAttributeDefinition("Filename");
        IAttributeDefinition attachmentNameAttr = attachmentType.getAttributeDefinition("Name");
        Asset attachment = createNew(attachmentType, Oid.Null);
        attachment.setAttributeValue(attachmentNameAttr, attachmentName);
        attachment.setAttributeValue(attachmentFileName, filePath);
        attachment.setAttributeValue(attachmentContentType, mimeType);
        attachment.setAttributeValue(attachmentContent, "");
        attachment.setAttributeValue(attachmentAssetDef, asset.getOid());
        save(attachment);

        String key = attachment.getOid().getKey().toString();
 	    FileInputStream inStream = new FileInputStream(filePath);
 	    OutputStream output =  _connector != null ? _connector.beginRequest(key.substring(key.lastIndexOf('/') + 1), null) : _v1Connector.beginRequest(key.substring(key.lastIndexOf('/') + 1), null);
 	    byte[] buffer = new byte[inStream.available() + 1];
 	    while (true) {
 	    	int read = inStream.read(buffer, 0, buffer.length);
 	        if (read <= 0)
 	        	break;
 	        output.write(buffer, 0, read);
 	    }
         
         if (_connector != null){
             _connector.endRequest(key.substring(key.lastIndexOf('/') + 1));
         }
         else
         {
             _v1Connector.useAttachmentApi();
             _v1Connector.endRequest(key.substring(key.lastIndexOf('/') + 1));
         }

         return attachment.getOid();
     }
	
	
	@Override
	public InputStream getAttachment(Oid attachmentOid) throws V1Exception{
		InputStream result = null;
		if (_connector != null) {
			result = _connector.getAttachment(attachmentOid.getKey().toString());
		} else if (_v1Connector != null) {
			_v1Connector.useAttachmentApi();
			result = _v1Connector.getAttachment(attachmentOid.getKey().toString());
		}

		return result;
	}
     
	@Override
	public Oid saveEmbeddedImage(String filePath, Asset asset) throws V1Exception, IOException {
		if (StringUtils.isEmpty(filePath))
			throw new NullArgumentException("Null value " + filePath);
		
		File file = new File(filePath);
        if (!file.exists())
       	 	throw new FileNotFoundException(String.format("File \"%s\" does not exist.", filePath));
		
        String mimeType = MimeType.resolve(filePath);
		IAssetType embeddedImageType = _meta.getAssetType("EmbeddedImage");
		Asset newEmbeddedImage = createNew(embeddedImageType, Oid.Null);
		IAttributeDefinition assetAttribute = embeddedImageType
				.getAttributeDefinition("Asset");
		IAttributeDefinition contentAttribute = embeddedImageType
				.getAttributeDefinition("Content");
		IAttributeDefinition contentTypeAttribute = embeddedImageType
				.getAttributeDefinition("ContentType");
		newEmbeddedImage.setAttributeValue(assetAttribute, asset.getOid());
		newEmbeddedImage.setAttributeValue(contentTypeAttribute, mimeType);
		newEmbeddedImage.setAttributeValue(contentAttribute, "");
		save(newEmbeddedImage);

		String key = newEmbeddedImage.getOid().getKey().toString();
		FileInputStream inStream = new FileInputStream(filePath);
		OutputStream output = _connector != null ? _connector.beginRequest(
				key.substring(key.lastIndexOf('/') + 1), null) : _v1Connector
				.beginRequest(key.substring(key.lastIndexOf('/') + 1), null);
		byte[] buffer = new byte[inStream.available() + 1];
		while (true) {
			int read = inStream.read(buffer, 0, buffer.length);
			if (read <= 0)
				break;

			output.write(buffer, 0, read);
		}

		if (_connector != null) {
			_connector.endRequest(key.substring(key.lastIndexOf('/') + 1));
		} else {
			_v1Connector.useEmbeddedApi();
			_v1Connector.endRequest(key.substring(key.lastIndexOf('/') + 1));
		}

		return newEmbeddedImage.getOid();
	}

	@Override
	public Reader getEmbeddedImage(Oid embeddedImageOid) throws V1Exception {
		Reader result = null;
		if (_connector != null) {
			result = _connector.getData(embeddedImageOid.getKey().toString());
		} else if (_v1Connector != null) {
			_v1Connector.useEmbeddedApi();
			result = _v1Connector.getData(embeddedImageOid.getKey().toString());
		}

		return result;
	}

}
