package com.versionone.apiclient;

import com.versionone.DB.DateTime;
import com.versionone.Oid;
import com.versionone.apiclient.IAttributeDefinition.AttributeType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class responsible creating XML from an Asset.  This
 * XML is used in the POST command when creating or
 * updating an Asset
 *
 * @author jerry
 */
public class XmlApiWriter {

    private static final String ELEMENT_NAME_ASSET = "Asset";
    private static final String ELEMENT_NAME_ATTRIBUTE = "Attribute";
    private static final String ELEMENT_NAME_RELATION = "Relation";
    private static final String ELEMENT_NAME_VALUE = "Value";

    private static final String ATTRIBUTE_NAME_ACTION = "act";
    private static final String ATTRIBUTE_NAME_IDREF = "idref";
    private static final String ATTRIBUTE_NAME_NAME = "name";

    private static final String ACTION_NAME_ADD = "add";
    private static final String ACTION_NAME_REMOVE = "remove";
    private static final String ACTION_NAME_SET = "set";

    private Document doc = null;
    private boolean _changesOnly = false;

    /**
     * Construction
     *
     * @param changesOnly - Only write attributes that have changed
     */
    public XmlApiWriter(boolean changesOnly) {
        _changesOnly = changesOnly;
    }

    /**
     * Write the asset to the specified destination
     *
     * @param asset       - asset to process
     * @param destination - writer to hold xml
     * @throws APIException -
     */
    public void write(Asset asset, Writer destination) throws APIException {

        buildDocument(asset);

        Source xmlSource = new DOMSource(doc);
        Result outputTarget = new StreamResult(destination);

        try {
            Transformer xform = TransformerFactory.newInstance().newTransformer();
            xform.transform(xmlSource, outputTarget);
        } catch (TransformerException e) {
            throw new APIException("Error creating XML", e);
        }
    }

    /**
     * Entry point for work
     *
     * @param asset Asset
     * @throws APIException -
     */
    private void buildDocument(Asset asset) throws APIException {
        DocumentBuilder builder;
        try {
            builder = XMLHandler.createDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new APIException("Parser Configuration Error ", e);
        }
        doc = builder.newDocument();
        Element root = doc.createElement(ELEMENT_NAME_ASSET);
        if (!asset.getOid().isNull())
            root.setAttribute("id", asset.getOid().getToken());
        Collection<Attribute> attributes = asset.getAttributes().values();
        Iterator<Attribute> iter = attributes.iterator();
        while (iter.hasNext()) {
            addAttribute(iter.next(), root);
        }
        doc.appendChild(root);
    }

    /**
     * add the attribute information
     *
     * @param attribute -
     * @param parent    -
     * @throws APIException -
     */
    private void addAttribute(Attribute attribute, Element parent) throws APIException {
        if (_changesOnly && !attribute.hasChanged())
            return;
        Element element = createAttributeElement(attribute.getDefinition().getAttributeType());
        element.setAttribute(ATTRIBUTE_NAME_NAME, attribute.getDefinition().getName());
        if (AttributeType.Relation == attribute.getDefinition().getAttributeType()) {
            populateRelationElement(element, attribute);
        } else {
            populateAttributeElement(element, attribute);
        }
        parent.appendChild(element);
    }

    private Element createAttributeElement(AttributeType attributeType) {
        String tagName = (AttributeType.Relation == attributeType) ? ELEMENT_NAME_RELATION : ELEMENT_NAME_ATTRIBUTE;
        return doc.createElement(tagName);
    }

    private void populateRelationElement(Element element, Attribute attribute) {
        if (attribute.hasChanged() && attribute.getDefinition().isMultiValue()) {
            writeAttributeValues(attribute.getAddedValues(), ACTION_NAME_ADD, element);
            writeAttributeValues(attribute.getRemovedValues(), ACTION_NAME_REMOVE, element);
        } else {
            if (attribute.hasChanged())
                element.setAttribute(ATTRIBUTE_NAME_ACTION, ACTION_NAME_SET);
            writeAttributeValues(attribute.getValues(), null, element);
        }
    }

    private void populateAttributeElement(Element element, Attribute attribute) throws APIException {
        try {
            if (attribute.getDefinition().isMultiValue()) {
                valuesToXml(attribute.getDefinition(), attribute.getValues(), ELEMENT_NAME_VALUE, element);
            } else {
                if (attribute.hasChanged())
                    element.setAttribute(ATTRIBUTE_NAME_ACTION, ACTION_NAME_SET);
                element.setTextContent(valueToXmlString(attribute.getDefinition().getAttributeType(),
                                                        attribute.getValue()));
            }
        } catch (Exception e) {
            throw new APIException("Error populating Attribute element", e);
        }
    }

    private void valuesToXml(IAttributeDefinition attribdef, Object[] values, String elementName, Element parent)
                                                                                                throws Exception {
        if (values != null) {
            for (Object val : values)
                valueToXml(attribdef, val, elementName, parent);
        }
    }

    private void valueToXml(IAttributeDefinition attribdef, Object val, String elementName, Element parent)
                                                                                                throws APIException {
        Element element = doc.createElement(elementName);
        element.setTextContent(valueToXmlString(attribdef.getAttributeType(), val));
        parent.appendChild(element);
    }

    private void writeAttributeValues(Object[] list, String action, Element parent) {
        if (list == null)
            return;

        for (int i = 0; i < list.length; ++i) {
            Oid oid = (Oid) list[i];
            if (oid.isNull())
                continue;
            Element element = doc.createElement(ELEMENT_NAME_ASSET);
            element.setAttribute(ATTRIBUTE_NAME_IDREF, oid.getToken());
            if (action != null)
                element.setAttribute(ATTRIBUTE_NAME_ACTION, action);
            parent.appendChild(element);
        }
    }

    /**
     * Converts the Attribute value into a string that we can put in XML
     *
     * @param type  - type of value
     * @param value - the value
     * @return value as a string
     * @throws APIException when an unsupported type is passed
     */
    private String valueToXmlString(AttributeType type, Object value) throws APIException {
        if (value == null)
            return "";

        switch (type) {
            case Boolean:
            case LongText:
            case Text:
            case LocalizerTag:
            case Duration:
            case Rank:
            case Opaque:
            case State:
            case Password:
            case LongInt:
                return value.toString();

            case Numeric:
                NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
                return nf.format(value);

            case Date:
                final SimpleDateFormat formatter;
                Date datetimevalue = (Date) value;
                if (getSecondOfDay(datetimevalue) == 0)
                    formatter = DateTime.DAY_FORMAT;
                else
                    formatter = DateTime.DAY_N_TIME_FORMAT;
//                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                return formatter.format(value);

            case Relation:
                if (((Oid) value).isNull())
                    return "";
                return ((Oid) value).getToken();

            case AssetType:
                return ((IAssetType) value).getToken();

            case Blob:
                return "";

            default:
                throw new APIException("Unsupported AttributeType ", type.toString());
        }
    }

    /**
     * @param date - date to calculate.
     * @return number of seconds last from begining of specified day (in local time).
     */
    private int getSecondOfDay(Date date) {
        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int min = cal.get(Calendar.MINUTE);
        final int sec = cal.get(Calendar.SECOND);
        return (hour * 60 + min) * 60 + sec;
    }
}
