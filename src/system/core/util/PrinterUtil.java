package system.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;

import sun.print.Win32PrintServiceLookup;

public class PrinterUtil {
	public static void printFile(String filePath) throws FileNotFoundException, PrintException {
		PrintService ps = new Win32PrintServiceLookup().getDefaultPrintService();
		DocPrintJob job = ps.createPrintJob();
		FileInputStream fis = new FileInputStream(filePath);
		DocAttributeSet das = new HashDocAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		MediaPrintableArea area = new MediaPrintableArea(0, 0, 250, 800,MediaPrintableArea.MM);
		pras.add(area);
		Doc doc = new SimpleDoc(fis, flavor, das);
		job.print(doc, pras);
	}
}