package com.versionone.javasdk.unit.tests;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class TextResponseConnector extends ResponseConnector {

	public TextResponseConnector(String fileName, String nameSpace,
			String key) {
		super(fileName, nameSpace, key, new IResolveContent(){
			
			public String resolve(Node node) {
				StringBuffer rc = new StringBuffer();
				NodeList children = node.getChildNodes();
				for(int i=0;i<children.getLength();++i)
					resolveChildren(children.item(i), rc);
				return rc.toString();
			}

			private void resolveChildren(Node node, StringBuffer dest) {
				if(node instanceof Text) {
					dest.append(node.getNodeValue().trim()); // return;
				}
//				NamedNodeMap attributes = node.getAttributes();
//		        dest.append('<');
//		        dest.append(node.getNodeName());
//		        if(attributes != null) {
//		            int attributeCount = attributes.getLength();
//		            for (int i = 0; i < attributeCount; i++) {
//		                Attr attribute = (Attr)attributes.item(i);
//		                dest.append(' ');
//		                dest.append(attribute.getNodeName());
//		                dest.append("=\"");
//		                dest.append(attribute.getNodeValue());
//		                dest.append('"');
//		            }
//		        }
//		        dest.append('>');
//		        if(node.hasChildNodes());
//		        	dest.append(resolve(node));
//		        dest.append("</");
//		        dest.append(node.getNodeName());
//		        dest.append(">");
			}
	});
	}

}
