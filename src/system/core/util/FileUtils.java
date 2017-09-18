package system.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Thread;
import org.springframework.web.multipart.MultipartFile;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import sun.util.logging.resources.logging;

/**
 * 文件操作工具类
 * @author renrti
 */
public class FileUtils {
	/**
	 * InputStream存储为文件
	 * @param ins
	 * @param file
	 */
	public static File inputstreamtofile(InputStream ins,String filePath) {
		  File file=new File(filePath);
		 try {
			
			   OutputStream os = new FileOutputStream(file);
			   int bytesRead = 0;
			   byte[] buffer = new byte[8192];
			   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			    os.write(buffer, 0, bytesRead);
			   }
			   os.close();
			   ins.close();
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		 return file;
	 }
	/**
	 * 设置目录，不存在则创建
	 * @param realPath
	 */
	public static void setpath(String realPath){
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();// 创建根目录
		}
	}
	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	
	public static String getExtend(String filename) {
		return getExtend(filename, "");
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtend(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return (filename.substring(i+1)).toLowerCase();
			}
		}
		return defExt.toLowerCase();
	}

	/**
	 * 获取文件名称[不含后缀名]
	 * 
	 * @param
	 * @return String
	 */
	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex).replaceAll("\\s*", "");
	}
	
	/**
	 * 获取文件名称[不含后缀名]
	 * 不去掉文件目录的空格
	 * @param
	 * @return String
	 */
	public static String getFilePrefix2(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}
	
	/**
	 * 文件复制
	 *方法摘要：这里一句话描述方法的用途
	 *@param
	 *@return void
	 */
	public static void copyFile(String inputFile,String outputFile) throws FileNotFoundException{
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = new FileInputStream(sFile);
		FileOutputStream fos = new FileOutputStream(tFile);
		int temp = 0;  
		byte[] buf = new byte[10240];
        try {  
        	while((temp = fis.read(buf))!=-1){   
        		fos.write(buf, 0, temp);   
            }   
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally{
            try {
            	fis.close();
            	fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
	}
	/**
	 * 判断文件是否为图片<br>
	 * <br>
	 * 
	 * @param filename
	 *            文件名<br>
	 *            判断具体文件类型<br>
	 * @return 检查后的结果<br>
	 * @throws Exception
	 */
	public static boolean isPicture(String filename) {
		// 文件名称为空的场合
		if (StringUtil.isEmpty(filename)) {
			// 返回不和合法
			return false;
		}
		// 获得文件后缀名
		//String tmpName = getExtend(filename);
		String tmpName = filename;
		// 声明图片后缀名数组
		String imgeArray[][] = { { "bmp", "0" }, { "dib", "1" },
				{ "gif", "2" }, { "jfif", "3" }, { "jpe", "4" },
				{ "jpeg", "5" }, { "jpg", "6" }, { "png", "7" },
				{ "tif", "8" }, { "tiff", "9" }, { "ico", "10" } };
		// 遍历名称数组
		for (int i = 0; i < imgeArray.length; i++) {
			// 判断单个类型文件的场合
			if (imgeArray[i][0].equals(tmpName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断文件是否为DWG<br>
	 * <br>
	 * 
	 * @param filename
	 *            文件名<br>
	 *            判断具体文件类型<br>
	 * @return 检查后的结果<br>
	 * @throws Exception
	 */
	public static boolean isDwg(String filename) {
		// 文件名称为空的场合
		if (StringUtil.isEmpty(filename)) {
			// 返回不和合法
			return false;
		}
		// 获得文件后缀名
		String tmpName = getExtend(filename);
		// 声明图片后缀名数组
		if (tmpName.equals("dwg")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 删除指定的文件
	 * 
	 * @param strFileName
	 *            指定绝对路径的文件名
	 * @return 如果删除成功true否则false
	 */
	public static boolean delete(String strFileName) {
		File fileDelete = new File(strFileName);

		if (!fileDelete.exists() || !fileDelete.isFile()) {
			return false;
		}

		return fileDelete.delete();
	}
	
	/**
	 * 
	* @Title: encodingFileName 2015-11-26 huangzq add
	* @Description: 防止文件名中文乱码含有空格时%20 
	* @param @param fileName
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String encodingFileName(String fileName) {
        String returnFileName = "";
        try {
            returnFileName = URLEncoder.encode(fileName, "UTF-8");
            returnFileName = StringUtils.replace(returnFileName, "+", "%20");
            if (returnFileName.length() > 150) {
                returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
                returnFileName = StringUtils.replace(returnFileName, " ", "%20");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnFileName;
    }
	
	public static String getFileSize(long byts){
		 DecimalFormat df = new DecimalFormat("#.00");
		if (byts<1024) {
			return df.format(byts)+"B";
		}else if (byts<1024*1024) {
			return df.format(byts/1024)+"KB";
		}else if (byts<1004*1024*1024) {
			return df.format(byts/1024/1024)+"MB";
		}else if (byts<1004*1024*1024*1024) {
			return df.format(byts/1024/1024/1024)+"GB";
		}else {
			return df.format(byts/1024/1024/1024/1024)+"GB";
		}
		
	}
	/**
	 * 错误格式Excel转换
	 * @param file
	 * @return
	 */
	public static MultipartFile transformFile(MultipartFile file,String filePath,String fileName){
		try {
			InputStream inputStream = file.getInputStream();
			inputstreamtofile(inputStream, filePath+"\\"+fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	public static void downloadFile(String fileName,String filePath,HttpServletResponse response) throws IOException{
		InputStream inputStream = new FileInputStream(filePath);
		OutputStream out = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/msword");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("gb2312"), "ISO8859-1")
				+ ".doc");
		out = response.getOutputStream();
		byte[] buffer = new byte[512]; 
		int bytesToRead = -1;
		while ((bytesToRead = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, bytesToRead);
		}
		if (out!=null) {
			out.close();
		}
		if (inputStream!=null) {
			inputStream.close();
		}
		File file = new File(filePath);
		file.delete();
	}
	/**
	 * 批量文件创建压缩包
	 * @param zipPath
	 * @param batchDirPath
	 * @param zipName
	 * @throws IOException
	 */
	public String makeZipFile(String zipPath,String zipName) throws IOException {
		String zipFilePath = zipPath+zipName;
		File files[] = new File(zipPath).listFiles();
		File zipFile = new File(zipFilePath);
		OutputStream outputStream = null;
		FileInputStream inputStream = null;
		BufferedInputStream bufferedInputStream = null;
		ZipOutputStream zipOutputStream = null;
		outputStream = new FileOutputStream(zipFile);
		zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
		byte[] bufs = new byte[1024 * 10];
		for (int i = 0; i < files.length; i++) {
			ZipEntry zipEntry = new ZipEntry(files[i].getName());
			zipOutputStream.putNextEntry(zipEntry);
			inputStream = new FileInputStream(files[i]);
			bufferedInputStream = new BufferedInputStream(inputStream, 1024 * 10);
			int read = 0;  
            while ((read = bufferedInputStream.read(bufs, 0, 1024 * 10)) != -1) {  
                zipOutputStream.write(bufs, 0, read);  
            }
		}
		if (bufferedInputStream!=null) {
			bufferedInputStream.close();
		}
		if (zipOutputStream!=null) {
			zipOutputStream.close();
		}
		return zipFilePath;
	}
	/**
	 * 下载服务器端生成的zip压缩文件
	 * @param zipFilePath
	 * @param batchDirPath
	 * @param response
	 */
	public void downloadZipFile(String zipFilePath, String zipFileName,HttpServletResponse response) {
        try {  
            File file = new File(zipFilePath+zipFileName);  
            response.setCharacterEncoding("UTF-8");  
            response.setHeader("Content-Disposition",  
                    "attachment; filename=" + new String(zipFileName.getBytes("UTF-8"), "ISO-8859-1"));  
            response.setContentLength((int) file.length());  
            response.setContentType("application/zip");
            FileInputStream fis = new FileInputStream(file);  
            BufferedInputStream buff = new BufferedInputStream(fis);  
            byte[] b = new byte[1024];
            long k = 0;
            OutputStream myout = response.getOutputStream();  
            while (k < file.length()) {  
                int j = buff.read(b, 0, 1024);  
                k += j;  
                myout.write(b, 0, j);  
            }  
            myout.flush();  
            buff.close();  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
	}
}
