package com.versionone.apiclient;

import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

/**
 * Represents information about an Assets type
 * @author jerry
 *
 */
@SuppressWarnings("unchecked")
class AssetType implements IAssetType {
	private IMetaModel _metaModel;
	private Map _map;
	private String _displayname;
	private String _token;
	private IAssetType _base = null;
	private String _basetoken = null;
	private IAttributeDefinition _orderby = null;
	private String _orderbytoken = null;

	private IAttributeDefinition _ShortNameAttribute = null;
	private String _shortNameToken = null;

	private IAttributeDefinition _NameAttribute = null;
	private String _nameToken = null;

	private IAttributeDefinition _DescriptionAttribute = null;
	private String _descriptionToken = null;

	/**
	 * Create
	 * @param meta
	 * @param element
	 * @param map
	 * @throws Exception
	 */
	public AssetType(IMetaModel meta, Element element, Map map) throws Exception {
		_metaModel = meta;
		_map = map;
		_displayname = element.getAttribute("displayname");
		_token = element.getAttribute("token");

		XPath xpath = XPathFactory.newInstance().newXPath();
		Element baseelement = (Element)xpath.evaluate("Base", element, XPathConstants.NODE);
		if (baseelement != null)
			_basetoken = baseelement.getAttribute("nameref");

		Element orderbyelement = (Element)xpath.evaluate("DefaultOrderBy", element, XPathConstants.NODE);
		if (orderbyelement != null)
			_orderbytoken = orderbyelement.getAttribute("tokenref");

		Element nameElement = (Element)xpath.evaluate("Name", element, XPathConstants.NODE);
		if (nameElement != null)
			_nameToken = nameElement.getAttribute("tokenref");

		Element shortNameElement = (Element)xpath.evaluate("ShortName", element, XPathConstants.NODE);
		if (shortNameElement != null)
			_shortNameToken = shortNameElement.getAttribute("tokenref");

		Element descriptionElement = (Element)xpath.evaluate("Description", element, XPathConstants.NODE);
		if (descriptionElement != null)
			_descriptionToken = descriptionElement.getAttribute("tokenref");
	}

	private IAttributeDefinition findAttributeDefinition(String name) {
		Pair key = new Pair(this, name);
		return (IAttributeDefinition) _map.get(key);
	}

	void saveAttributeDefinition(IAttributeDefinition attribdef) {
		Pair key = new Pair(this, attribdef.getName());
		_map.put(key, attribdef);
	}

	/**
	 * Get the definition of an attribute on this type of Asset
	 * @see IAssetType#getAttributeDefinition(String)
	 */
	public IAttributeDefinition getAttributeDefinition(String name) throws MetaException {
		IAttributeDefinition attribdef = findAttributeDefinition(name);
		if (attribdef != null) {
			return attribdef;
		}
		return _metaModel.getAttributeDefinition(this.getToken() + "." + name);
	}

	/**
	 * Is this type also another type
	 */
	public boolean isA(IAssetType targettype) throws MetaException {
		for (IAssetType thistype = this; thistype != null; thistype = thistype.getBase())
			if (thistype == targettype)
				return true;
		return false;
	}

	/**
	 * Get the Base AssetType for this type
	 */
	public IAssetType getBase() throws MetaException {
		if (_base == null && _basetoken != null)
			_base = _metaModel.getAssetType(_basetoken);
		return _base;
	}

	/**
	 * Get the default sort order
	 * {@link #getDefaultOrderBy()}
	 */
	public IAttributeDefinition getDefaultOrderBy() throws MetaException {
		if (_orderby == null && _orderbytoken != null)
			_orderby = _metaModel.getAttributeDefinition(_orderbytoken);
		return _orderby;
	}

	/**
	 * Get the display name
	 */
	public String getDisplayName() {
		return _displayname;
	}

	/**
	 * Get the AssetType token
	 */
	public String getToken() {
		return _token;
	}


	/**
	 * @param name name of operation
	 * @return operation
	 * @throws MetaException if an error occurs finding the attribute
	 */
	public IOperation getOperation(String name) throws MetaException {
		IOperation op = lookupOperation(name);
		if (op != null) {
			return op;
		}

		return _metaModel.getOperation(getToken() + "." + name);
	}

	private IOperation lookupOperation(String name) {
		Pair key = new Pair(this, name);
		return (IOperation) _map.get(key);
	}

	private IAttributeDefinition ResolveAttribute(String token, IAttributeDefinition result) throws MetaException {
		if (result == null && token != null)
			result = _metaModel.getAttributeDefinition(token);
		return result;
	}

	/**
	 * @return short name attribute
	 * @throws MetaException if an error occurs finding the attribute
	 */
	public IAttributeDefinition getShortNameAttribute() throws MetaException {
		return _ShortNameAttribute = ResolveAttribute(_shortNameToken, _ShortNameAttribute);
	}

	/**
	 * @return attribute name
	 * @throws MetaException if an error occurs finding the attribute
	 */
	public IAttributeDefinition getNameAttribute() throws MetaException {
		return _NameAttribute = ResolveAttribute(_nameToken, _NameAttribute);
	}

	/**
	 * @return description attribute
	 * @throws MetaException if an error occurs finding the attribute
	 */
	public IAttributeDefinition getDescriptionAttribute() throws MetaException {
		return _DescriptionAttribute = ResolveAttribute(_descriptionToken, _DescriptionAttribute);
	}
}