package com.versionone.apiclient;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


class Operation implements IOperation {
	
	private IMetaModel Meta;
	private String _name;
	private String _assettypetoken;
	private IAssetType _assettype;
	private String _validatortoken;
	private IAttributeDefinition _validator;

	public Operation(IMetaModel meta, Element element) {
		Meta = meta;
		String of = element.getAttribute("of");
		String[] ofs = of.split("/");
		_assettypetoken = ofs[ ofs.length - 1 ];
		_name = element.getAttribute("name");
		NodeList validators = element.getElementsByTagName("Validator");
		if (validators != null && validators.getLength() > 0)
			_validatortoken = ((Element)validators.item(0)).getAttribute("tokenref");
	}

	public Operation(IMetaModel meta, String assettypetoken, Element element) {
		this(meta,element);
		_assettypetoken = assettypetoken;
	}

	public IAssetType getAssetType() throws MetaException {
		if (_assettype == null && _assettypetoken != null)
			_assettype = Meta.getAssetType(_assettypetoken);
		return _assettype;
	}

	public String getName() {
		return _name;
	}

	public String getToken() {
		return  _assettypetoken + "." + _name;
	}

	public IAttributeDefinition getValidatorAttribute() throws MetaException {
		if (_validator == null && _validatortoken != null)
			_validator = Meta.getAttributeDefinition(_validatortoken);
		return _validator;
	}
	
}