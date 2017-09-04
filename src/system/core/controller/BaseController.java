package system.core.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import system.core.interceptors.DateConvertEditor;
import system.core.service.CommonServiceI;
import system.web.entity.base.Sys_Base_DataDictionary;

public class BaseController {
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
 
		binder.registerCustomEditor(Date.class, new  DateConvertEditor());
	}
}
