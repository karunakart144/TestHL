/*
 * Copyright (c) 2011.Information Systems(IGATE)
 */

package com.igate.iconnect.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * 
 * @Author: 717417
 * @Description: This class takes care of Renaming the CSS and Scripts folder for the deployment
 * @CreationDate:21-March-2012
 */

public class DeploymentUtility {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"ddMMMyyyy_HHmmss");
			Date today = new Date();
			String date = formatter.format(today.getTime());
			File oldfile = null;
			File newfile = null;
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			String file = new String();
			file = "../target/WEB-INF/web.xml";
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(file));

			doc.getDocumentElement().normalize();
			boolean cssRenamed = false;
			boolean scriptsRenamed = false;
			if (doc.getDocumentElement().getNodeName().toString()
					.equalsIgnoreCase("web-app")) {

				NodeList listOfservelets = doc
						.getElementsByTagName("context-param");
				for (int s = 0; s < listOfservelets.getLength(); s++) {
					Node initnode = listOfservelets.item(s);
					if (initnode.getNodeType() == Node.ELEMENT_NODE) {
						Element initelem = (Element) initnode;

						NodeList paramnamelist = initelem
								.getElementsByTagName("param-name");
						Element paramnameemem = (Element) paramnamelist.item(0);

						NodeList paramvallist = initelem
								.getElementsByTagName("param-value");
						Element paramvalelem = (Element) paramvallist.item(0);

						Element paramvalelemnew = doc
								.createElement("param-value");

						if (paramnameemem.getTextContent().equalsIgnoreCase(
								"cssDirPath")) {
							oldfile = new File("../target/css");
							newfile = new File("../target/css_" + date);
							initnode.removeChild(paramvalelem);
							paramvalelemnew.appendChild(doc
									.createTextNode("css_" + date));
							initnode.appendChild(paramvalelemnew);
							cssRenamed = oldfile.renameTo(newfile);

						}
						if (paramnameemem.getTextContent().equalsIgnoreCase(
								"jsDirPath")) {
							oldfile = new File("../target/scripts");
							newfile = new File("../target/scripts_" + date);
							initnode.removeChild(paramvalelem);
							paramvalelemnew.appendChild(doc
									.createTextNode("scripts_" + date));
							initnode.appendChild(paramvalelemnew);
							scriptsRenamed = oldfile.renameTo(newfile);
						}

					}
				}
			}

			if (cssRenamed && scriptsRenamed) {
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(file);
				transformer.transform(source, result);
			}
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

}
