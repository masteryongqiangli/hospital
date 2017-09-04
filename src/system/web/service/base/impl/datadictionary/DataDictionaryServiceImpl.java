package system.web.service.base.impl.datadictionary;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import system.core.annotation.Log;
import system.core.service.impl.CommonServiceImpl;
import system.core.util.JSONHelper;
import system.web.dao.base.datadictionary.DataDictionaryDaoI;
import system.web.entity.base.Sys_Base_DataDictionary;
import system.web.service.base.datadictionary.DataDictionaryServiceI;

@Service("dataDictionaryService")
@Transactional
public class DataDictionaryServiceImpl extends CommonServiceImpl implements DataDictionaryServiceI {
	@Autowired
	private DataDictionaryDaoI dataDictionaryDaoImpl;
	public JSONArray getParentDataDicList() {
		 JSONArray jsonArray=new JSONArray ();
		List<Sys_Base_DataDictionary> list = dataDictionaryDaoImpl.getParentDataDicList();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("dataDicId", list.get(i).getDataDicId());
			jsonObject.put("text", list.get(i).getText());
			jsonArray.add(jsonObject);
		}
		
		return jsonArray;
	}

	@Override
	public JSONObject getTreeGridJson(Map<String, String> parms) {
		JSONObject data = new JSONObject();
		JSONArray rows = new JSONArray();
		List<Sys_Base_DataDictionary> list = dataDictionaryDaoImpl.getAllDataDicList(parms);
		for (int i = 0; i < list.size(); i++) {
			JSONObject child = new JSONObject();
			if (list.get(i).getParent_DataDictionary() == null) {
				JSONArray jsonArray = new JSONArray();
				int count = 1;
				for (int j = i; j < list.size(); j++) {
					if (list.get(j).getParent_DataDictionary() != null
							&& list.get(j).getParent_DataDictionary().equals(list.get(i).getDataDicId())) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("No", i + "" + count);
						jsonObject.put("DataDicId", list.get(j).getDataDicId());
						jsonObject.put("Code", list.get(j).getCode());
						jsonObject.put("Text", list.get(j).getText());
						jsonObject.put("OrderNum", list.get(j).getOrderNum());
						count++;
						jsonArray.add(jsonObject);
					}
				}
				child.put("No", i);
				child.put("DataDicId", list.get(i).getDataDicId());
				child.put("Code", list.get(i).getCode());
				child.put("Text", list.get(i).getText() + "（" + jsonArray.size() + "）");
				child.put("OrderNum", list.get(i).getOrderNum());
				child.put("children", jsonArray);
				if (jsonArray.size() > 0) {
					child.put("state", "closed");
				}
				rows.add(child);
			}
		}
		if (parms.size() > 1 && rows.size() == 0) {
			for (int j = 0; j < list.size(); j++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("No", j);
				jsonObject.put("DataDicId", list.get(j).getDataDicId());
				jsonObject.put("Code", list.get(j).getCode());
				jsonObject.put("Text", list.get(j).getText());
				jsonObject.put("OrderNum", list.get(j).getOrderNum());
				rows.add(jsonObject);
			}
		}
		// TODO Auto-generated method stub
		data.put("total", rows.size());
		data.put("rows", rows);
		return data;
	}

	@Override
	public boolean batchDelete(Sys_Base_DataDictionary sys_Base_DataDictionary) {
		return dataDictionaryDaoImpl.batchDelete(sys_Base_DataDictionary) > 0 ? true : false;
	}
}
