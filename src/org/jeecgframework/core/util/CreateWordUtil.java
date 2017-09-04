package org.jeecgframework.core.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CreateWordUtil {

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	public static String StringPattern(String date, String oldPattern,
			String newPattern) {
		if (date == null || oldPattern == null || newPattern == null)
			return "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern); // 实例化模板对象
		SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern); // 实例化模板对象
		Date d = null;
		try {
			d = sdf1.parse(date); // 将给定的字符串中的日期提取出来
		} catch (Exception e) { // 如果提供的字符串格式有错误，则进行异常处理
			e.printStackTrace(); // 打印异常信息
		}
		return sdf2.format(d);
	}

	/**
	 * TODO(生成word模板并下载)
	 * 
	 * @param freemarker
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param templateName
	 *            模板名称
	 * @param wordName
	 *            生成word文档名称
	 * @param dataMap
	 *            数据绑定
	 */
	public static void CreateFile(HttpServletRequest request,
			HttpServletResponse response, String templateName, String wordName,
			Map<String, Object> dataMap) throws Exception {
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		String name = "bloodResult.xml";
		File f = new File(name);
		Configuration con=new Configuration();
		con.setDirectoryForTemplateLoading(new File(request.getSession().getServletContext().getRealPath("sysfile/")));//指定加载模板的位置
        con.setObjectWrapper(new DefaultObjectWrapper());//指定生产模板的方式
        con.setDefaultEncoding("utf-8");//设置模板读取的编码方式，用于处理乱码
        Template template = con.getTemplate("bloodResult.xml");//模板文件，可以是xml,ftl,html
		// 设置FreeMarker的模版文件位置
		/*configuration.setServletContextForTemplateLoading(request.getSession()
				.getServletContext(), "sysfile\\");
		Template t = null;
		// 要装载的模板
		t = configuration.getTemplate(templateName);*/
		// 获取应用的根路径保存到本地
		/*String servletContextRealPath = request.getServletContext().getRealPath("");
		File outFile=new File(servletContextRealPath+"/export/TemporaryFile/" + name);
		Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
		t.process(dataMap, w);
		if (w != null) {
			w.close();
		}*/
		//输出到页面
		Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
		template.process(dataMap, w);
		InputStream fin = null;
		ServletOutputStream out = null;
		f.setReadOnly();
		f.setWritable(false);
		try {
			// 调用工具类WordGenerator的createDoc方法生成Word文档
			fin = new FileInputStream(f);

			response.setCharacterEncoding("utf-8");
			response.setContentType("application/msword");
			// 设置浏览器以下载的方式处理该文件默认名为resume.doc
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(wordName.getBytes("gb2312"), "ISO8859-1")
					+ ".doc");

			out = response.getOutputStream();
			byte[] buffer = new byte[512]; // 缓冲区
			int bytesToRead = -1;
			// 通过循环将读入的Word文件的内容输出到浏览器中
			while ((bytesToRead = fin.read(buffer)) != -1) {
				out.write(buffer, 0, bytesToRead);
			}
		} finally {
			if (w != null) {
				w.close();
			}
			if (fin != null) {
				fin.close();
			}
			if (out != null) {
				out.close();
			}
			if (f != null) {
				f.delete(); // 删除临时文件
			}
		}
	}
	/**
	 * TODO(生成word并返回文件)
	 * 
	 * @param freemarker
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param templateName
	 *            模板名称
	 * @param wordName
	 *            生成word文档名称
	 * @param dataMap
	 *            数据绑定
	 */
	public static File CreateFile(HttpServletRequest request, String templateName, String wordname,
			Map<String, Object> dataMap)  {
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		String wordpath=request.getSession() .getServletContext().getRealPath("")+"\\expWord\\";
		File fpath = new File(wordpath);
		if (!fpath.exists()) {
			fpath.mkdirs();
		}
		File f = new File(wordpath+wordname+".doc");
		// 设置FreeMarker的模版文件位置
		configuration.setServletContextForTemplateLoading(request.getSession()
				.getServletContext(), "export\\template\\");
		Template t = null;
		// 要装载的模板
		try {
			t = configuration.getTemplate(templateName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取应用的根路径保存到本地
		/*String servletContextRealPath = request.getServletContext().getRealPath("");
		File outFile=new File(servletContextRealPath+"/export/TemporaryFile/" + name);
		Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
		t.process(dataMap, w);
		if (w != null) {
			w.close();
		}*/
		//输出到页面
		Writer w=null;
		try {
			w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			t.process(dataMap, w);
			InputStream fin = null;
			fin = new FileInputStream(f);
			FileOutputStream fos = new FileOutputStream("b.txt");
			byte[] b = new byte[1024];
			while((fin.read(b)) != -1){
			fos.write(b);
			}
			w.close();
			fin.close();
			fos.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		
		return f;
	}
	/**
	 * TODO(生成word并返回文件)
	 * 
	 * @param freemarker
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param templateName
	 *            模板名称
	 * @param wordName
	 *            生成word文档名称
	 * @param dataMap
	 *            数据绑定
	 * @param wordpath
	 *           根据CheckID生成文件夹
	 */
	public static File CreateFile2(HttpServletRequest request, String templateName, String wordname,Map<String, Object> dataMap,String wordpath)  {
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		File fpath = new File(wordpath);
		if (!fpath.exists()) {
			fpath.mkdirs();
		}
		File f = new File(wordpath+wordname+".doc");
		// 设置FreeMarker的模版文件位置
		configuration.setServletContextForTemplateLoading(request.getSession()
				.getServletContext(), "export\\template\\");
		Template t = null;
		// 要装载的模板
		try {
			t = configuration.getTemplate(templateName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取应用的根路径保存到本地
		/*String servletContextRealPath = request.getServletContext().getRealPath("");
		File outFile=new File(servletContextRealPath+"/export/TemporaryFile/" + name);
		Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
		t.process(dataMap, w);
		if (w != null) {
			w.close();
		}*/
		//输出到页面
		Writer w=null;
		try {
			w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			t.process(dataMap, w);
			InputStream fin = null;
			fin = new FileInputStream(f);
			FileOutputStream fos = new FileOutputStream("b.txt");
			byte[] b = new byte[1024];
			while((fin.read(b)) != -1){
			fos.write(b);
			}
			w.close();
			fin.close();
			fos.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}
		
		return f;
	}
	/**
	 * 导出word文件 poi
	 * 
	 * @param request
	 * @param response
	 * @param templateName
	 * @param wordName
	 * @param dataMap
	 */
	public static void ExportWordPoi(HttpServletRequest request,
			HttpServletResponse response, String templateName, String wordName,
			Map<String, String> dataMap, List<Map<String, String>> list) {

		// 获取应用的根路径
		String servletContextRealPath = request.getServletContext()
				.getRealPath("");
		// 获取模板文件
		File templateFile = new File(servletContextRealPath
				+ "/export/template/" + templateName);

		ByteArrayOutputStream ostream = null;
		try {
			FileInputStream in = new FileInputStream(templateFile);
			HWPFDocument hwpfDocument = new HWPFDocument(in);
			Range range = hwpfDocument.getRange();

			for (Map.Entry<String, String> entry : dataMap.entrySet()) {
				range.replaceText("${" + entry.getKey() + "}", entry.getValue());
			}

			// 输出 word 内容文件流，提供下载
			response.reset();
			response.setContentType("application/x-msdownload");

			response.addHeader("Content-Disposition", "attachment; filename="
					+ new String(wordName.getBytes("gb2312"), "ISO8859-1")
					+ ".doc");
			ostream = new ByteArrayOutputStream();
			ServletOutputStream servletOS = response.getOutputStream();
			hwpfDocument.write(ostream);
			servletOS.write(ostream.toByteArray());
			servletOS.flush();
			servletOS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO(获取图片的BASE64编码) .
	 *
	 * @param filename
	 *            the filename
	 * @return String
	 * @throws IOException
	 *             String 返回值
	 */
	/*public static String getImageString(String filename) throws IOException {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(filename);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return Base64.encode(data);
	}*/
	public static void CreateZipFile(HttpServletRequest request,String templateName, String wordName,Map<String, Object> dataMap) throws Exception {
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		String name = "temp" + (int) (Math.random() * 100000) + ".xml";
		File f = new File(name);
		// 设置FreeMarker的模版文件位置
		configuration.setServletContextForTemplateLoading(request.getSession()
				.getServletContext(), "export\\template\\");
		Template t = null;
		// 要装载的模板
		t = configuration.getTemplate(templateName);
		//输出到页面
		Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
		t.process(dataMap, w);
		InputStream fin = null;
		f.setReadOnly();
		f.setWritable(false);
		String path = request.getSession().getServletContext().getRealPath("/upload/")+"\\cert\\";
		File file = new File(path);   
        if(!file.exists()) {
        	file.mkdir();
        }
        File wordfile = new File(path+wordName+".doc");
        fin = new FileInputStream(f);
        FileOutputStream outputStream = new FileOutputStream(wordfile);
        byte[] b = new byte[1024000];
        int n;
        while ((n = fin.read(b)) != -1) {
            outputStream.write(b, 0, n);
        }
        if (fin!=null) {
			fin.close();
		}
        if(outputStream!=null){
        	outputStream.close();
        }
	}
}
