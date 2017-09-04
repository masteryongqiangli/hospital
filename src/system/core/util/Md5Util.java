package system.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Md5Util {
	public static String EncoderByMd5(String password) {
		  MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			   BASE64Encoder base64en = new BASE64Encoder();
			   String newstr=base64en.encode(md5.digest(password.getBytes("utf-8")));
			   return newstr;
		} catch (NoSuchAlgorithmException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		return "";
	}
}
