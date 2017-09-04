package business.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import business.dao.bloodResultDao;
import business.service.bloodResultService;
import system.core.service.impl.CommonServiceImpl;

@Service("bloodResultService")
@Transactional
public class bloodResultServiceImpl extends CommonServiceImpl implements
		bloodResultService {
	@Autowired
	bloodResultDao bloodResultDao;

	public JSONObject getBloodResultList(Map<String, String> map) {
		return bloodResultDao.getBloodResultList(map);
	}

	public boolean readExcel(MultipartFile file) {
		String fileType = file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf(".") + 1,
				file.getOriginalFilename().length());
		Workbook wb = null;
		if (fileType.equals("xls")) {
			try {
				wb = new HSSFWorkbook(file.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (fileType.equals("xlsx")) {
			try {
				wb = new XSSFWorkbook(file.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Sheet sheet = wb.getSheetAt(0);
		List<Map<String, String>> sheetList = new ArrayList<Map<String, String>>();// 对应sheet页
		List<String> titles = new ArrayList<String>();// 放置所有的标题
		int rowSize = sheet.getLastRowNum() + 1;
		for (int m = 0; m < rowSize; m++) {
			Row row = sheet.getRow(m);
			if (row == null) {// 略过空行
				continue;
			}
			int cellSize = row.getLastCellNum();// 行中有多少个单元格，也就是有多少列
			if (m == 0) {// 第一行是标题行
				for (int k = 0; k < cellSize; k++) {
					Cell cell = row.getCell(k);
					titles.add(cell.toString());
				}
			} else {
				Map<String, String> rowMap = new HashMap<String, String>();// 对应一个数据行
				for (int k = 0; k < titles.size(); k++) {
					Cell cell = row.getCell(k);
					String key = titles.get(k);
					String value = cell==null?"":cell.toString();
					rowMap.put(key, value);
				}
				sheetList.add(rowMap);
			}
		}
		List<Object> allPerson = new ArrayList<Object>();
		Map<String, Object> onePerson = new HashMap<>();
		int f=0;
		for(int i=0;i<sheetList.size();i++){
			if (onePerson.isEmpty()) {
				onePerson.put("name", sheetList.get(i).get("姓名"));
			}
			
			onePerson.put(sheetList.get(i).get("项目名"), sheetList.get(i).get("结果"));
			
			int k=i+1;
			if (k==sheetList.size()) {
				if(bloodResultDao.importBloodResult(onePerson)){
					f++;
				}
			}else{
				if (!sheetList.get(k).get("姓名").equals(onePerson.get("name"))) {
					if(bloodResultDao.importBloodResult(onePerson)){
						f++;
					}
					onePerson.clear();
				}
			}
		}
		if (f==allPerson.size()) {
			return true;
		}else{
			return false;
		}
	}
	
	public JSONObject getBloodInfo(String bloodId){
		return bloodResultDao.getBloodInfo(bloodId);
	}
	
	public boolean doDelete(String bloodResultId){
		return bloodResultDao.doDelete(bloodResultId);
	}
	
	public String getResultId(String bloodId){
		return bloodResultDao.getResultId(bloodId);
	}
	
}
