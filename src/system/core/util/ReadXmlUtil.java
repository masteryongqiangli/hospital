package system.core.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ReadXmlUtil {
	public Document readXmlUtil(String xmlPath) throws ParserConfigurationException, SAXException, IOException{
		File file = new File(xmlPath);
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(file);
		return document;
	}
}
