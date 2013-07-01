package com.versionone.apiclient;

import com.versionone.DB;
import com.versionone.Oid;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Wraps the services available in the VersionOne API
 *
 * @author Jerry D. Odenwelder Jr.
 */
public class Services implements IServices {

    private IMetaModel _meta;
    private IAPIConnector _connector;
    private Oid _loggedin;

    /**
     * Create
     *
     * @param metaModel - VersionOne MetaModel
     * @param connector - Connection to the server
     */
    public Services(IMetaModel metaModel, IAPIConnector connector) {
        _meta = metaModel;
        _connector = connector;
    }

    /**
     * Query VersionOne
     *
     * @see IServices#retrieve(Query)
     */
    public QueryResult retrieve(Query query) throws ConnectionException,
            APIException, OidException {
        String queryUrl = new QueryURLBuilder(query).toString();
        Reader reader = null;
        try {
            reader = _connector.getData(queryUrl);
            Document doc = XMLHandler.buildDocument(reader, queryUrl);
            return parseQueryResult(doc.getDocumentElement(), query);
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
                    //do nothing
                }
            }
        }
    }

    private QueryResult getEmptyQueryResult(Query query) {
        return new QueryResult(new Asset[]{}, 0, query);
    }

    /**
     * Create new Asset
     *
     * @see IServices#createNew(IAssetType, Oid)
     */
    public Asset createNew(IAssetType assetType, Oid context) throws V1Exception {

        String path = "New/" + assetType.getToken();

        if (context != null && !context.isNull())
            path += "?ctx=" + context.getToken();

        Reader reader = null;
        try {
            reader = _connector.getData(path);
            Document doc = XMLHandler.buildDocument(reader, path);
            return parseNewAssetNode(doc.getDocumentElement(), assetType);
        }
        catch (Exception ex) {
            throw new APIException("Failed to create new asset!", assetType.getToken(), ex);
        }
        finally {
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
        String path = "Data/" + oid.getAssetType().getToken() + "/" + oid.getKey().toString() + "?op=" + op.getName();

        Reader reader = null;
        try {
            reader = _connector.sendData(path, "");
            Document doc = XMLHandler.buildDocument(reader, path);
            Asset asset = parseAssetNode(doc.getDocumentElement());
            return asset.getOid();
        }
        catch (Exception ex) {
            throw new APIException("Error executing Operation!", op.getName(), ex);
        }
        finally {
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

            String path = "Data/" + asset.getAssetType().getToken();

            if (!asset.getOid().isNull())
                path += "/" + asset.getOid().getKey().toString();

            if (comment != null && !comment.equals("")) {
				try {
					comment = URLEncoder.encode(comment, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					comment = comment.replace(" ","%20");
					comment = comment.replace("&","%26");
					comment = comment.replace("#","%23");
				}
                path += String.format("?Comment='%s'", comment);
            }

            Reader reader = null;
            try {
                reader = _connector.sendData(path, data);
                Document doc = XMLHandler.buildDocument(reader, path);
                parseSaveAssetNode(doc.getDocumentElement(), asset);
            } catch (OidException e) {
                throw new APIException("Error processing response", e);
            }
            finally {
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

    private QueryResult parseQueryResult(Element element, Query query)
            throws APIException, OidException {
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

    private void parseAttributeNode(Asset asset,
                                    IAttributeDefinition attribdef,
                                    Element element) throws APIException {
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
                    asset.loadAttributeValue(attribdef, v.toString());
                else
                    asset.forceAttributeValue(attribdef, v.toString());
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

    private QueryResult parseAssetListQueryResult(Element element, Query query) throws APIException, OidException {
        List<Asset> list = new ArrayList<Asset>();

        int total = Integer.parseInt(element.getAttribute("total"));

        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes;
        try {
            nodes = (NodeList) xpath.evaluate("Asset", element, XPathConstants.NODESET);
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

    private List<Asset> treeAssetListByAttribute(
            List<Asset> input, IAttributeDefinition def) throws APIException {

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

    private QueryResult parseHistoryQueryResult(Element element, Query query)
            throws OidException, APIException {
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
}
