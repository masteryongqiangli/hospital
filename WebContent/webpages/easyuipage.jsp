<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="Res/jquery-easyui-1.5.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="Res/jquery-easyui-1.5.1/themes/icon.css">
<script type="text/javascript"
	src="Res/jquery-easyui-1.5.1/jquery.min.js"></script>
<script type="text/javascript"
	src="Res/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="Res/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>	
	<script>
	
	$.extend($.fn.validatebox.defaults.rules, {    
		strlength: {    
	        validator: function(value, param){    
	        	 
	            return value.length >= param[0]&&value.length <= param[1];    
	        },    
	        message: '请输入{0}-{1}个字符'   
	    }    
	});
	$.extend($.fn.validatebox.defaults.rules, {    
	    phone: {    
	        validator: function(value, param){ 
	        	return    IsMobile(value)>0; 
	        },    
	        message: '请输入正确的手机号码或电话号码'   
	    }    
	});  
	 function IsMobile(text) {
	        var _emp = /^\s*|\s*$/g;
	        text = text.replace(_emp, "");
	        var _d = /^1[3578][01379]\d{8}$/g;
	        var _l = /^1[34578][01256]\d{8}$/g;
	        var _y = /^(134[0-8]\d{7}|1[34578][012356789]\d{8})$/g;
	        if (_d.test(text)) {
	            return 3;
	        } else if (_l.test(text)) {
	            return 2;
	        } else if (_y.test(text)) {
	            return 1;
	        }
	        return 0;
	    }
	</script>

 
 
	
	
 
 