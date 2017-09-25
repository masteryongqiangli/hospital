package system.core.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class SystemStopUtil {
	public boolean systemStop(String xmlPath){
		File file = new File(xmlPath);
		boolean loginState=true;
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(file);
			String xmlTime = document.getElementsByTagName("month").item(0).getFirstChild().getNodeValue();
			int timeLength=0;
			if (xmlTime.equals("a")) {
				timeLength=6;
			}else if (xmlTime.equals("b")) {
				timeLength=9;
			}else if (xmlTime.equals("c")) {
				timeLength=9999;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			java.util.Date startDate = dateFormat.parse("2017-09-01");
			calendar.setTime(startDate);
			calendar.add(Calendar.MONTH, timeLength);
			long startTime = calendar.getTimeInMillis();
			java.util.Date nowDate = dateFormat.parse(dateFormat.format(new Date()));
			calendar.setTime(nowDate);
			long nowTime = calendar.getTimeInMillis();
			
			if (Integer.parseInt(String.valueOf((nowTime-startTime)/(1000*3600*24)))>=0) {
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
