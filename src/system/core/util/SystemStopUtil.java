package system.core.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SystemStopUtil {
	public boolean systemStop(String xmlPath){
		File file = new File(xmlPath);
		boolean loginState=true;
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(file);
			String xmlTime = document.getElementsByTagName("year").item(0).getFirstChild().getNodeValue()+"-"+
					document.getElementsByTagName("month").item(0).getFirstChild().getNodeValue()+"-"+
					document.getElementsByTagName("day").item(0).getFirstChild().getNodeValue();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			java.util.Date nowDate = dateFormat.parse(dateFormat.format(new java.util.Date()));
			calendar.setTime(nowDate);
			long nowTime = calendar.getTimeInMillis();
			java.util.Date stopDate = dateFormat.parse(xmlTime);
			calendar.setTime(stopDate);
			long stopTime = calendar.getTimeInMillis();
			if (Integer.parseInt(String.valueOf((nowTime-stopTime)/(1000*3600*24)))>=0) {
				loginState=false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loginState;
	}
}
