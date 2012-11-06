package com.versionone.apiclient;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import com.versionone.DB;
import com.versionone.Duration;
import com.versionone.Oid;

/**
 * Represents the definition of an Attribute
 * @author jerry
 *
 */
class AttributeDefinition implements IAttributeDefinition {
	private IMetaModel meta;
	private IAssetType _assettype;
	private String _assettypetoken;
	private String _name;
	private String _token;
	private AttributeType _attributetype;
	private IAttributeDefinition _base = null;
	private String _basetoken = null;
	private boolean _isreadonly;
	private boolean _isrequired;
	private boolean _ismultivalue;
	private IAssetType _relatedasset = null;
	private String _relatedassettoken = null;
	private String _displayname;

	/**
	 * Create
	 * @param meta
	 * @param element
	 * @throws Exception
	 */
	public AttributeDefinition(IMetaModel meta, Element element) throws Exception {
		this.meta = meta;
		_token = element.getAttribute("token");
		StringBuffer prefix = new StringBuffer();
		StringBuffer suffix = new StringBuffer();
		TextBuilder.splitPrefix(_token, '.', prefix, suffix);
		_assettypetoken = prefix.toString();
		_name = suffix.toString();

		_displayname = element.getAttribute("displayname");

		_attributetype = AttributeType.valueOf(element.getAttribute("attributetype"));
		_isreadonly = new DB.Bit(element.getAttribute("isreadonly")).booleanValue();
		_isrequired = new DB.Bit(element.getAttribute("isrequired")).booleanValue();
		_ismultivalue = new DB.Bit(element.getAttribute("ismultivalue")).booleanValue();

		XPath xpath = XPathFactory.newInstance().newXPath();
		Element baseelement = (Element)xpath.evaluate("Base", element, XPathConstants.NODE);
		if (baseelement != null)
			_basetoken = baseelement.getAttribute("tokenref");

		Element relatedelement = (Element)xpath.evaluate("RelatedAsset", element, XPathConstants.NODE);
		if (relatedelement != null)
				_relatedassettoken = relatedelement.getAttribute("nameref");

		((AssetType)getAssetType()).saveAttributeDefinition(this);
	}

	/**
	 * Coerce a value to another type
	 */
	public Object coerce(Object value) throws V1Exception {
		switch(getAttributeType())
		{
			case Boolean:
				return new DB.Bit(value).getValue();
			case Numeric:
				return new DB.Real(value).getValue();
			case Date:
				return new DB.DateTime(value).getValue();
			case Duration:
				if (value == null || value instanceof Duration)
					return value;
				if(value instanceof Integer)
					return new Duration(((Integer)value).intValue(), Duration.Unit.Days);
				return Duration.parse((String) value);
			case Text:
			case LongText:
			case LocalizerTag:
			case Password:
				return new DB.Str(value).getValue();
			case Relation:
				Oid oid = coerceOid(value);
				if (getRelatedAsset() != null && !oid.isNull() && !(oid.getAssetType()).isA(getRelatedAsset()))
					throw new OidException("Wrong OID AssetType", oid.getToken());
				return oid;
			case AssetType:
				return coerceAssetType(value);
			case Opaque:
				return value;
			case State:
				return coerceState(value);
			case Rank:
				return value;
			case LongInt:
				return new DB.BigInt(value).getValue();
            case Blob:
                return value;
			default:
				throw new MetaException("Unsupported AttributeType ", getAttributeType().toString());
		}
	}

	private Object coerceState(Object value) {
		if ( value instanceof AssetState ) {
			return value;
		}
		if ( value instanceof Enum) {
			return ((Enum)value).ordinal();
		}
		if (value instanceof Byte) {
			int intValue = ((Byte)value).intValue();
			if(AssetState.isDefined(intValue))
				return AssetState.valueOf(intValue);
			return intValue;
		}
		String stringval = "" + value;
		try {
			return AssetState.valueOf(stringval);
		}catch(Exception e) {}
		return Integer.parseInt(stringval);
	}

	private Oid coerceOid(Object value) throws V1Exception {
		if(null == value ) {
			return Oid.Null;
		}
		if(value instanceof Oid) {
			return (Oid) value;
		}
		if ( value instanceof String ) {
				return Oid.fromToken((String)value, meta);
		}
		if ( value instanceof Integer ) {
			return new Oid(getRelatedAsset(), new DB.Int(((Integer)value).intValue()), null);
		}
		throw new OidException("Object is not convertible to an OID", value.toString());
	}

	private IAssetType coerceAssetType(Object value) throws MetaException {
		if (value instanceof IAssetType)
			return (IAssetType)value;
		if (value instanceof String)
			return meta.getAssetType((String)value);
		throw new MetaException("Object is not convertible to an AssetType", value.toString());
	}

	/**
	 * Get the owning Asset type
	 */
	public IAssetType getAssetType() throws MetaException {
		if (_assettype == null)
			_assettype = meta.getAssetType(_assettypetoken);
		return _assettype;
	}

	/**
	 * get the type of attribute
	 */
	public AttributeType getAttributeType() {
		return _attributetype;
	}

	/**
	 * get the base attribute definition
	 */
	public IAttributeDefinition getBase() throws MetaException {
		if (_base == null && _basetoken != null)
			_base = meta.getAttributeDefinition(_basetoken);
		return _base;
	}

	/**
	 * Get the display name
	 */
	public String getDisplayName() {
		return _displayname;
	}

	/**
	 * Determine if this attribute can contain multiple values
	 */
	public boolean isMultiValue() {
		return _ismultivalue;
	}

	/**
	 * determine if this attribute is read-only
	 */
	public boolean isReadOnly() {
		return _isreadonly;
	}

	/**
	 * determine if this attribute is required
	 */
	public boolean isRequired() {
		return _isrequired;
	}

	/**
	 * get the name of this attribute
	 */
	public String getName() {
		return _name;
	}

	/**
	 * get type information for relation asset
	 */
	public IAssetType getRelatedAsset() throws MetaException {
		if (_relatedasset == null && _relatedassettoken != null)
			_relatedasset = meta.getAssetType(_relatedassettoken);
		return _relatedasset;
	}

	/**
	 * get token
	 */
	public String getToken() {
		return _token;
	}

	/**
	 * Downcast current object
	 *
	 * @param assetType IAssetType for asset defined in token
	 * @return Downcasted object
	 */
	public IAttributeDefinition downcast(IAssetType assetType) {
		if (getAttributeType().equals(AttributeType.Relation)) {
			if (assetType.isA(getRelatedAsset()))
				return meta.getAttributeDefinition(getToken() + ":" + assetType.getToken());

			throw new MetaException("Cannot downcast to unrelated type");
		}
		throw new MetaException("Cannot downcast non-relation attributes");
	}

	/**
	 * Add filter to object
	 *
	 * @param filter filter for adding
	 * @return object with filter
	 * @throws APIException if filter has incorrect statement
	 */
	public IAttributeDefinition filter(IFilterTerm filter) throws APIException{
		if (getAttributeType().equals(AttributeType.Relation))
			return meta.getAttributeDefinition(getToken() + "[" + filter.getShortToken() + "]");
		throw new MetaException("Cannot filter non-relation attributes");
	}

	/**
	 * Join current token with new
	 *
	 * @param joined Object with token for joining
	 * @return new object with joined tokens
	 */
	public IAttributeDefinition join(IAttributeDefinition joined){
		if (getAttributeType().equals(AttributeType.Relation)){
			if (getRelatedAsset().isA(joined.getAssetType()))
				return meta.getAttributeDefinition(getToken() + "." + joined.getName());
			throw new MetaException("Cannot join unrelated attributes");
		}
		throw new MetaException("Cannot join non-relation attributes");
	}

	/**
	 * Aggregate objects
	 *
	 * @param aggregate rule for Aggregating
	 * @return agregated object
	 */
	public IAttributeDefinition aggregate(Aggregate aggregate)
	{
		if (isMultiValue())
		{
			if (aggregate.equals(Aggregate.Min))
			{
				if (getAttributeType().equals(AttributeType.Numeric))
					return meta.getAttributeDefinition(getToken() + ".@Min");
				else if (getAttributeType().equals(AttributeType.Date))
					return meta.getAttributeDefinition(getToken() + ".@MinDate");
				throw new MetaException("Must aggregate MIN of numerics and dates");
			}
			else if (aggregate.equals(Aggregate.Max))
			{
				if (getAttributeType().equals(AttributeType.Numeric))
					return meta.getAttributeDefinition(getToken() + ".@Max");
				else if (getAttributeType().equals(AttributeType.Date))
					return meta.getAttributeDefinition(getToken() + ".@MaxDate");
				throw new MetaException("Must aggregate MAX of numerics and dates");
			}
			else if (aggregate.equals(Aggregate.Count))
			{
				if (getAttributeType().equals(AttributeType.Relation))
					return meta.getAttributeDefinition(getToken() + ".@Count");
				throw new MetaException("Must aggregate COUNT of relations");
			}
			else if (aggregate.equals(Aggregate.Sum))
			{
				if (getAttributeType().equals(AttributeType.Numeric))
					return meta.getAttributeDefinition(getToken() + ".@Sum");
				throw new MetaException("Must aggregate SUM of numerics");
			}
			else if (aggregate.equals(Aggregate.And))
			{
				if (getAttributeType().equals(AttributeType.Boolean))
					return meta.getAttributeDefinition(getToken() + ".@and");
				throw new MetaException("Must aggregate AND of booleans");
			}
			else if (aggregate.equals(Aggregate.Or))
			{
				if (getAttributeType().equals(AttributeType.Boolean))
					return meta.getAttributeDefinition(getToken() + ".@or");
				throw new MetaException("Must aggregate OR of booleans");
			}
		}
		throw new MetaException("Must aggregate multi-value attributes");
	}


}