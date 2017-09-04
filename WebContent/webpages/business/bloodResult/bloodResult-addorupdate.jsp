<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpages/basedetail.jsp"%>
<%@include file="/webpages/easyuipage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


</head>
<body>
	<form id="bloodResult" method="post"
		action="bloodResultController.do?saveBloodResultinfo&bloodResultId=${result.id}&bId=${result.bloodEnterId}&bloodNumber=${bloodNumber}">
		<table border="0" cellspacing="0" cellpadding="0" class="submit-table">
			<tr class="firstTr">
				<th><label>血样编号</label></th>
				<td><input id="bloodNumber" class="easyui-validatebox" type="text"  
					name="bloodNumber" value="${bloodNumber }" data-options="" readonly="readonly"/></td>
				<th><label>化验人：</label></th>
				<td><input id="blooderName" class="easyui-validatebox"
					name="blooderName"  value="${blooderName }"  data-options="" readonly="readonly" /></td>
			</tr>
			<tr class="firstTr">
				<th><label>总蛋白</label></th>
				<td><input id="ALB" class="easyui-validatebox" type="text"  
					name="ALB" value="${result.ALB }" data-options="" /></td>
				<th><label>白蛋白：</label></th>
				<td><input id="ALP" class="easyui-validatebox"
					name="ALP"  value="${result.ALP }"  data-options=""  /></td>
			</tr>
			<tr class="firstTr">
				<th><label>球蛋白</label></th>
				<td><input id="ALT" class="easyui-validatebox" type="text"  
					name="ALT" value="${result.ALT }" data-options="" /></td>
				<th><label>白球比：</label></th>
				<td><input id="AST" class="easyui-validatebox"
					name="AST"  value="${result.AST }"  data-options=""  /></td>
			</tr>
			<tr class="firstTr">
				<th><label>总胆红素</label></th>
				<td><input id="CK" class="easyui-validatebox" type="text"  
					name="CK" value="${result.CK }" data-options="" /></td>
				<th><label>直接胆红素：</label></th>
				<td><input id="CK_MB" class="easyui-validatebox"
					name="CK_MB"  value="${result.CK_MB }"  data-options=""  /></td>
			</tr>
			<tr class="firstTr">
				<th><label>间接胆红素</label></th>
				<td><input id="CRE" class="easyui-validatebox" type="text"  
					name="CRE" value="${result.CRE }" data-options="" /></td>
				<th><label>转氨酶：</label></th>
				<td><input id="DBIL" class="easyui-validatebox"
					name="DBIL"  value="${result.DBIL }"  data-options=""  /></td>
			</tr>
			<tr class="firstTr">
				<th><label>总胆固醇</label></th>
				<td><input id="GGT" class="easyui-validatebox" type="text"  
					name="GGT" value="${result.GGT }" data-options="" /></td>
				<th><label>甘油三酯：</label></th>
				<td><input id="GLU" class="easyui-validatebox"
					name="GLU"  value="${result.GLU }"  data-options=""  /></td>
			</tr>
			<tr class="firstTr">
				<th><label>高密度脂蛋白</label></th>
				<td><input id="HBDH" class="easyui-validatebox" type="text"  
					name="HBDH" value="${result.HBDH }" data-options="" /></td>
				<th><label>低密度脂蛋白：</label></th>
				<td><input id="HDL_C" class="easyui-validatebox"
					name="HDL_C"  value="${result.HDL_C }"  data-options=""  /></td>
			</tr>
			<tr class="firstTr">
				<th><label>载脂蛋白</label></th>
				<td><input id="LDH" class="easyui-validatebox" type="text"  
					name="LDH" value="${result.LDH }" data-options="" /></td>
				<th><label>空腹血糖：</label></th>
				<td><input id="LDL_C" class="easyui-validatebox"
					name="LDL_C"  value="${result.LDL_C }"  data-options=""  /></td>
			</tr>
			<tr class="firstTr">
				<th><label>肌酐</label></th>
				<td><input id="TBIL" class="easyui-validatebox" type="text"  
					name="TBIL" value="${result.TBIL }" data-options="" /></td>
				<th><label>尿素氮：</label></th>
				<td><input id="TC" class="easyui-validatebox"
					name="TC"  value="${result.TC }"  data-options=""  /></td>
			</tr>
			<tr class="firstTr">
				<th><label>尿酸</label></th>
				<td><input id="TG" class="easyui-validatebox" type="text"  
					name="TG" value="${result.TG }" data-options="" /></td>
				<th><label>乳酸脱氢酶：</label></th>
				<td><input id="TP" class="easyui-validatebox"
					name="TP"  value="${result.TP }"  data-options=""  /></td>
			</tr><tr class="firstTr">
				<th><label>肌酸肌酶</label></th>
				<td><input id="UA" class="easyui-validatebox" type="text"  
					name="UA" value="${result.UA }" data-options="" /></td>
				<th><label>肌酸肌酶1：</label></th>
				<td><input id="UREA" class="easyui-validatebox"
					name="UREA"  value="${result.UREA }"  data-options=""  /></td>
			</tr>
			
		</table>
	</form>
	<script>
	var districtData=[{'dataDicId':'昌平','text':'昌平'}]
		$.extend($.fn.validatebox.defaults.rules, {    
		    equals: {    
		        validator: function(value,param){    
		            return value == $(param[0]).val();    
		        },    
		        message: '两次输入的密码不一致！'   
		    }    
		});  
	</script>
</body>
</html>