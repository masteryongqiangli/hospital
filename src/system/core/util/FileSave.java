package system.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileSave implements Runnable {
	private InputStream ins;
	private String filePath;
	@Override
	public void run() {
		 try {
			  File file=new File(filePath);
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

	}
	public InputStream getIns() {
		return ins;
	}
	public void setIns(InputStream ins) {
		this.ins = ins;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
 

}
