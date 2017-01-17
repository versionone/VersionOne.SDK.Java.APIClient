package com.versionone.apiclient.querybuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

import com.versionone.apiclient.querybuilder.interfaces.IAssetBase;

public class AssetBase implements IAssetBase {

	public AssetBase(Element element) throws XPathExpressionException {
		
		XPath xpath = XPathFactory.newInstance().newXPath();

		Element descriptionElement = (Element)xpath.evaluate("Attribute[@name='Description']", element, XPathConstants.NODE);
		if (descriptionElement != null)
			this.put("Description", descriptionElement.getTextContent());

		Element nameElement = (Element)xpath.evaluate("Attribute[@name='Name']", element, XPathConstants.NODE);
		if (nameElement != null)
			this.put("Name", nameElement.getTextContent());
	}

//	@Override
	public String OidToken() {
		// TODO Auto-generated method stub
		return null;
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
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
	public Object get(Object arg0) {
		//return this.get(arg0);
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
	public Object put(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRelatedAsset(String relationName, IAssetBase asset) {
		// TODO Auto-generated method stub

	}
	
}
