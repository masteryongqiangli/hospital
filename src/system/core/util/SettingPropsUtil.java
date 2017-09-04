package system.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import system.web.entity.base.Sys_Base_DataDictionary;

public class SettingPropsUtil {
	public static <T> T SettingProps(String[] codes, Map<String, String> parms, Map<String, List<Sys_Base_DataDictionary>> selects,
			T entity) {
		for (int i = 0; i < codes.length; i++) {
			for (Sys_Base_DataDictionary sys_Base_DataDictionary : selects.get(codes[i])) {
				if ((  sys_Base_DataDictionary).getDataDicId()
						.equals(parms.get(codes[i] + "_id"))) {
					String firstWord = codes[i].substring(0, 1).toUpperCase();
					String methodName = String.format("set%s%s", firstWord, codes[i].substring(1));
					Method method;
					try {
						method = entity.getClass().getMethod(methodName, sys_Base_DataDictionary.getClass());
						method.invoke(entity, sys_Base_DataDictionary);
					} catch (NoSuchMethodException | SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}

		}
		return entity;
	}
}
