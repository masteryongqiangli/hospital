package org.jeecgframework.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.jca.cci.connection.ConnectionFactoryUtils;


public class GetConnection {
	private static String driverName = null;
	private static String url = null;
	private static String username = null;
	private static String passwd = null;
	Connection connection = null;

	public GetConnection() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(
					URLDecoder.decode(this.getClass().getClassLoader().getResource("dbconfig.properties").getPath())));
			driverName = properties.getProperty("jdbc.driver");
			url = properties.getProperty("jdbc.url");
			username = properties.getProperty("jdbc.username");
			passwd = properties.getProperty("jdbc.password");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection Connection(){
		try {
			Class.forName(driverName);
			try {
				connection = DriverManager.getConnection(url,username,passwd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	/**
	 * 数据库增、删、改操作
	 * @param sql
	 * @return
	 */
	public boolean insert(String sql){
		boolean result;
		java.sql.Statement statement=null;
		connection = this.Connection();
		try {
			statement = (java.sql.Statement) connection.createStatement();
			int m = statement.executeUpdate(sql);
			if (m>0) {
				result=true;
			}else result = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		if (connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}
	/**
	 * 数据库查询操作
	 * @param sql
	 * @param page
	 * @param rows
	 * @return
	 */
	public net.sf.json.JSONObject getJsonObjectPageBySql(String sql, String page,
			String rows){
		net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
		net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
		connection = this.Connection();
		java.sql.PreparedStatement preparedStatement = null;
		java.sql.ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = (java.sql.ResultSet) preparedStatement.executeQuery();
			int k = resultSet.getMetaData().getColumnCount();
			while(resultSet.next()){
				net.sf.json.JSONObject jsonObject2 = new net.sf.json.JSONObject();
				for(int i=1;i<=k;i++){
					String columnNameString = resultSet.getMetaData().getColumnLabel(i);
					if (resultSet.getObject(i)==null) {
						jsonObject2.put(columnNameString, "");
					}else{
						String value = resultSet.getObject(i).toString();
						jsonObject2.put(columnNameString, value);
					}
				}
				jsonArray.add(jsonObject2);
			}
			int intPage = Integer.parseInt((String) ((page==null||page=="0")?1:page));
			int number = Integer.parseInt((String) ((rows==null||rows=="0")?10:rows));
			int start = (intPage-1)*number;
			int end = start+number;
			if (end>jsonArray.size()) {
				end = jsonArray.size();
			}
			net.sf.json.JSONArray jsonArray2 = new net.sf.json.JSONArray();
			for(int i=start;i<end;i++){
				jsonArray2.add(jsonArray.get(i));
			}
			jsonObject.put("total", jsonArray.size());
			jsonObject.put("rows", jsonArray2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * 数据库增、删、改操作
	 * @param sql
	 * @return
	 */
	public String getcol(String sql){
		String str="";
		connection = this.Connection();
		java.sql.PreparedStatement preparedStatement=null;
		java.sql.ResultSet result=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			result=preparedStatement.executeQuery();
			while(result.next()){
				str=result.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	public net.sf.json.JSONObject getJsonObjectBySql(String sql){
		net.sf.json.JSONObject jsonObject=new net.sf.json.JSONObject();
		net.sf.json.JSONArray array=new net.sf.json.JSONArray();
		connection=this.Connection();
		java.sql.PreparedStatement preparedStatement=null;
		java.sql.ResultSet resultSet=null;
		try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();
			int k=resultSet.getMetaData().getColumnCount();
			while(resultSet.next()){
				net.sf.json.JSONObject jsonObject2=new net.sf.json.JSONObject();
				for(int i=1;i<=k;i++){
					String columString=resultSet.getMetaData().getColumnLabel(i);
					if (resultSet.getObject(i)==null) {
						jsonObject2.put(columString, "");
					}else{
						jsonObject2.put(columString, resultSet.getObject(i));
					}
				}
				array.add(jsonObject2);
			}
			jsonObject.put("data", array);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
}
