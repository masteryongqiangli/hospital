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
	<form id="bloodEnter" method="post"
		action="bloodEnterController.do?saveBloodinfo&bloodId=${bloodId}">
		<table border="0" cellspacing="0" cellpadding="0" class="submit-table">
			<tr>
				<th><label>血样编号</label></th>
				<td><input id="bloodNumber" class="easyui-validatebox" type="text"  
					name="bloodNumber" value="${bloodEnter.bloodNumber}" data-options="" readonly="readonly"/></td>
			</tr>
			<tr>
				<th><label>化验人：</label></th>
				<td><input id="blooderName" class="easyui-validatebox"
					name="blooderName"  value="${bloodEnter.blooderName}"  data-options="required:true"/></td>
			</tr>
			<tr>
				<th><label>身份证号：</label></th>
				<td><input id="blooderIdCard" class="easyui-validatebox"
					name="blooderIdCard"  value="${bloodEnter.blooderIdCard}"  data-options="required:true"/></td>
			</tr>
			<tr>
				<th><label>年龄：</label></th>
				<td><input id="blooderAge" class="easyui-validatebox" data-options="" readonly="readonly"
					name="blooderAge" value="${bloodEnter.blooderAge}" validType="intType" /></td>
			</tr>
			<tr>
				<th><label>所属区县：</label></th>
				<td><input id="blooderDistrict" class="easyui-combobox easyui-validatebox" value=""
					name="blooderDistrict"
					data-options="required:true,
					data:districtData,
					method:'get',
					valueField:'dataDicId',
					textField:'text',
					panelHeight:'auto',
					editable:false,
					panelMaxHeight:140"/></td>
			</tr>
			<tr>
				<th><label>抽血时间：</label></th>
				<td><input id="bloodStartTime"  class="easyui-datebox"
					name="bloodStartTime" value="${bloodEnter.bloodStartTime}"  data-options="required:true"/>
					</td>
			</tr>
			<tr>
				<th><label>送达时间：</label></th>
				<td><input id="bloodAriveTime" class="easyui-datebox"
					name="bloodAriveTime"  value="${bloodEnter.bloodAriveTime}"  data-options="required:true"/>
					</td>
			</tr>
			<!-- <tr>
				<th><label>结果时间：</label></th>
				<td><input id="bloodResultTime" class="easyui-datebox"
					name="bloodResultTime"  value=""  data-options="required:true"/></td>
			</tr> -->
		</table>
	</form>
	<script>
	$(document).ready(function(){
		$("#blooderDistrict").combobox('select','${bloodEnter.blooderDistrict}' );
		var today = new Date();
		$("#bloodNumber").val('${villageNum}'+today.getFullYear()+today.getMonth()+today.getDay()+today.getHours()+today.getMinutes()+today.getSeconds());
	});
	$("#blooderIdCard").blur(function(){
		alert($(this).val().substr(6,4))
		var nowDate = new Date();
		var age = nowDate.getFullYear()- parseInt($(this).val().substr(6,4));
		$("#blooderAge").val(age);
	});
	var districtData=JSON.parse('${districtData}').data;
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