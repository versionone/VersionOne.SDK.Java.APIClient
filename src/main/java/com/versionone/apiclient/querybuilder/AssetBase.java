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

	private String _nameToken;
	private String _numberToken;

	public AssetBase(Element element) throws XPathExpressionException {
		
		XPath xpath = XPathFactory.newInstance().newXPath();
	
		Element nameElement = (Element)xpath.evaluate("Name", element, XPathConstants.NODE);
		if (nameElement != null)
			_nameToken = nameElement.getAttribute("tokenref");

		Element number = (Element)xpath.evaluate("Number", element, XPathConstants.NODE);
		if (number != null)
			_numberToken = number.getAttribute("tokenref");

	}

	@Override
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

	@Override
	public Object get(Object arg0) {
		// TODO Auto-generated method stub
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

	@Override
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
	//
	// private enum AddOrRemove
	// {
	// original,
	// add,
	// remove
	// }
	//
	// private Map<Object, Object> _wrapped;
	//
	// private Map<String, List<Pair<String, AddOrRemove>>>
	// _relationshipAssetReferencesMap =
	// new HashMap<String, List<Pair<String, AddOrRemove>>>();
	//
	// private Map<String, Boolean> _modifiedAttributes = new HashMap<String,
	// Boolean>();
	//
	//
	// private boolean _fromDynamic;
	// private boolean _fromQueryResult;
	//
	// public AssetBase(HashMap wrapped, boolean fromQueryResult )
	// {
	// _wrapped = wrapped;
	// _fromDynamic = true;
	// _fromQueryResult = fromQueryResult;
	// }
	//
	// public AssetBase(String assetTypeName, Object attributes )
	// {
	// if (attributes != null) _wrapped = JSONObject.stringToValue((String)
	// attributes);
	// else _wrapped = new JSONObject();
	//
	// _wrapped["AssetTypeName"] = assetTypeName;
	// }
	//
	// public AssetBase(string assetTypeName, string oidToken) :
	// this(assetTypeName)
	// {
	// if (string.IsNullOrWhiteSpace(oidToken))
	// {
	// throw new ArgumentNullException("oidToken");
	// }
	//
	// OidToken = oidToken;
	// }
	//
	// [JsonIgnore]
	// public string AssetTypeName
	// {
	// get
	// {
	// var assetTypeName = _wrapped["AssetTypeName"];
	// if (assetTypeName != null &&
	// !string.IsNullOrWhiteSpace(assetTypeName.ToString())) return
	// assetTypeName.ToString();
	// else return string.Empty;
	// }
	// }
	//
	// private string _oidToken;
	//
	//
	// public JObject GetChangesDto()
	// {
	// if (_fromDynamic && !_fromQueryResult) return _wrapped as JObject;
	//
	// var changesObj = new JObject();
	// foreach (var key in _modifiedAttributes.Keys)
	// {
	// changesObj[key] = _wrapped[key];
	// }
	//
	// foreach (var list in _relationshipAssetReferencesMap)
	// {
	// var items = new List<object>();
	// foreach (var oidTokenReference in list.Value)
	// {
	// var act = oidTokenReference.Item2.ToString();
	// items.Add(new
	// {
	// idref = oidTokenReference.Item1,
	// act = act
	// });
	// }
	// changesObj[list.Key] = JArray.FromObject(items);
	// }
	//
	// return changesObj;
	// }
	//
	// // TODO this is pretty hacky
	// public object Attributes
	// {
	// get
	// {
	// return GetChangesDto();
	// }
	// }
	//
	// public string GetYamlPayload()
	// {
	// return string.Empty;
	// //return QueryYamlPayloadBuilder.Build(this);
	// }
	//
	// // TODO: We might not need this _link thing anymore. It's a holdover from
	// a HAL-style approach
	// private T GetRelation<T>(string relationName) where T : class =>
	// _wrapped["_links"][relationName] as T;
	//
	// private List<Tuple<string, AddOrRemove>> GetOrCreateRelationMap(string
	// relationName)
	// {
	// if (!_relationshipAssetReferencesMap.ContainsKey(relationName))
	// {
	// var newMap = new List<Tuple<string, AddOrRemove>>();
	// _relationshipAssetReferencesMap[relationName] = newMap;
	// }
	// var map = _relationshipAssetReferencesMap[relationName];
	// return map;
	// }
	//
	//
	// private void RegisterRemovedRelationshipAssetReference(string
	// relationName, string oidToken) =>
	// RegisterRelationshipAssetReference(relationName, oidToken,
	// AddOrRemove.remove);
	//
	// private void RegisterAddedRelationshipAssetReference(string relationName,
	// string oidToken) =>
	// RegisterRelationshipAssetReference(relationName, oidToken,
	// AddOrRemove.add);
	//
	// private void RegisterRelationshipAssetReference(string relationName,
	// string oidToken, AddOrRemove direction)
	// {
	// var map = GetOrCreateRelationMap(relationName);
	// var entry = map.FirstOrDefault(m => m.Item1 == oidToken);
	// if (entry == null)
	// {
	// entry = new Tuple<string, AddOrRemove>(oidToken, direction);
	// map.Add(entry);
	// }
	// else
	// {
	// var newEntry = new Tuple<string, AddOrRemove>(entry.Item1, direction);
	// map.Remove(entry);
	// map.Add(newEntry);
	// }
	// }
	//
	// public int RemoveRelatedAssets(string relationName, params object[]
	// oidTokens)
	// {
	// int removed = 0;
	// try
	// {
	// foreach (var oidToken in oidTokens)
	// {
	// var oid = oidToken.ToString();
	// var relation = GetRelation<JArray>(relationName);
	//
	// foreach (dynamic item in relation)
	// {
	// if (item.idref == oid)
	// {
	// relation.Remove(item);
	// RegisterRemovedRelationshipAssetReference(relationName, oid);
	// removed++;
	// break;
	// }
	// }
	// }
	// }
	// catch (Exception ex)
	// {
	// string msg = ex.Message;
	// }
	// return removed;
	// }
	//
	// public void AddRelatedAssets(string relationName, params object[]
	// oidTokens)
	// {
	// // TODO check for duplicates first
	// foreach (var oidToken in oidTokens)
	// {
	// var oid = oidToken.ToString();
	// var relation = GetRelation<JArray>(relationName);
	// RegisterAddedRelationshipAssetReference(relationName, oid);
	// relation.Add(JObject.FromObject(new
	// {
	// idref = oidToken
	// }));
	// }
	// }
	//
	// public override bool TryInvokeMember(InvokeMemberBinder binder, object[]
	// args, out object result)
	// {
	// if (binder.Name == "RemoveRelatedAssets")
	// {
	// result = this.RemoveRelatedAssets(args[0] as string, args[1] as object);
	// return true;
	// }
	// else if (binder.Name == "AddRelatedAssets")
	// {
	// this.AddRelatedAssets(args[0] as string, args[1] as object);
	// result = null;
	// return true;
	// }
	// else if (binder.Name == "GetChangesDto")
	// {
	// result = this.GetChangesDto();
	// return true;
	// }
	//
	// return base.TryInvokeMember(binder, args, out result);
	// }
	//
	// public object Get(String attributeName) -> _wrapped[attributeName];
	//
	// public boolean tryGetMember(GetMemberBinder binder, object result)
	// {
	// bool success;
	// try
	// {
	// result = _wrapped[binder.Name];
	// success = true;
	// }
	// catch (Exception)
	// {
	// result = null;
	// success = false;
	// }
	// return success;
	// }
	//
	// public void Set(string atttributeName, object value)
	// {
	// try
	// {
	// _wrapped[atttributeName] = JToken.FromObject(value);
	// }
	// catch (Exception ex)
	// {
	// _wrapped[atttributeName] = JToken.FromObject(value.ToString());
	// // If we still blow up, then we're just out of luck
	// }
	// _modifiedAttributes[atttributeName] = true;
	// }
	//
	// public boolean trySetMember(SetMemberBinder binder, object value)
	// {
	// bool success;
	//
	// try
	// {
	// var originalValue = _wrapped[binder.Name];
	// if (originalValue != value)
	// {
	// Set(binder.Name, value);
	// }
	// success = true;
	// }
	// catch (Exception ex)
	// {
	// // TODO...
	// success = false;
	// }
	//
	// return success;
	// }
	//
	//
	// //public Map<Object, Object> GetWrappedDynamic -> return this._wrapped;
	//
	// // public object this[string attributeName]
	// // {
	// // get
	// // {
	// // return Get(attributeName);
	// // }
	// // set
	// // {
	// // Set(attributeName, value);
	// // }
	// // }
	//
	//
	//
	//
	// @Override
	// public String OidToken() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void addRelatedAsset(String relationName, IAssetBase asset) {
	// // TODO Auto-generated method stub
	//
	// }
	//

}
