/******************************************************************
 *
 *  CyberXML for Java
 *
 *  Copyright (C) Satoshi Konno 2009
 *
 *  File: XmlPullParser.java
 *
 *  Revision:
 *
 *  05/11/09
 *      - First revision for Android.
 *
 ******************************************************************/
package org.cybergarage.xml.parser;

import java.io.InputStream;
import org.cybergarage.xml.Node;
import org.cybergarage.xml.ParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * The XmlPullParser.
 */
public final class XmlPullParser extends org.cybergarage.xml.Parser {

    /**
     * Constructor.
     */
    public XmlPullParser() {
    }

    /**
     * Prase.
     * @param xpp xmlpullparser
     * @param inStream inputstream
     * @return the node
     * @throws ParserException exception
     */
    public Node parse(final org.xmlpull.v1.XmlPullParser xpp, final InputStream inStream) throws ParserException {
        Node rootNode = null;
        Node currNode = null;

        try {
            //InputStreamReader inReader = new InputStreamReader(inStream);
            //xpp.setInput(inReader);
            xpp.setInput(inStream, null);
            int eventType = xpp.getEventType();
            while (eventType != org.xmlpull.v1.XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case org.xmlpull.v1.XmlPullParser.START_TAG: {
                        Node node = new Node();
                        String namePrefix = xpp.getPrefix();
                        String name = xpp.getName();
                        StringBuilder nodeName = new StringBuilder();
                        if (namePrefix != null && 0 < namePrefix.length()) {
                            nodeName.append(namePrefix);
                            nodeName.append(":");
                        }
                        if (name != null && 0 < name.length()) {
                            nodeName.append(name);
                        }
                        node.setName(nodeName.toString());
                        int attrsLen = xpp.getAttributeCount();
                        for (int n = 0; n < attrsLen; n++) {
                            String attrName = xpp.getAttributeName(n);
                            String attrValue = xpp.getAttributeValue(n);
                            node.setAttribute(attrName, attrValue);
                        }

                        if (currNode != null) {
                            currNode.addNode(node);
                        }
                        currNode = node;
                        if (rootNode == null) {
                            rootNode = node;
                        }
                    }
                    break;
                    case org.xmlpull.v1.XmlPullParser.TEXT: {
                        String value = xpp.getText();
                        if (value != null && currNode != null) {
                            currNode.setValue(value);
                        }
                    }
                    break;
                    case org.xmlpull.v1.XmlPullParser.END_TAG: {
                        currNode = currNode.getParentNode();
                    }
                    break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            throw new ParserException(e);
        }

        return rootNode;
    }

    @Override
    public Node parse(InputStream inStream) throws ParserException {
        Node rootNode = null;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            org.xmlpull.v1.XmlPullParser xpp = factory.newPullParser();
            rootNode = parse(xpp, inStream);
        } catch (Exception e) {
            throw new ParserException(e);
        }

        return rootNode;
    }
}
