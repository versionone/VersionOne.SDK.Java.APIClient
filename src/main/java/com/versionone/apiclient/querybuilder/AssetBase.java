package com.versionone.apiclient.querybuilder;

import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import com.versionone.apiclient.querybuilder.interfaces.IAssetBase;

public class AssetBase extends HashMap<String, Object> implements IAssetBase  {

	public AssetBase(Element element) throws XPathExpressionException {
		
		XPath xpath = XPathFactory.newInstance().newXPath();

		Element descriptionElement = (Element)xpath.evaluate("Attribute[@name='Description']", element, XPathConstants.NODE);
		if (descriptionElement != null)
			super.put("Description", descriptionElement.getTextContent());

		Element nameElement = (Element)xpath.evaluate("Attribute[@name='Name']", element, XPathConstants.NODE);
		if (nameElement != null)
			super.put("Name", nameElement.getTextContent());
	}


	private Object get(String key) {
		return super.get(key);
	}

	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String OidToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRelatedAsset(String relationName, IAssetBase asset) {
		// TODO Auto-generated method stub
		
	}

}
