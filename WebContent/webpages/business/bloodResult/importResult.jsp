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
	<table border="0" cellspacing="0" cellpadding="0" class="submit-table">
		<tr>
			<th><label>所属区县：</label></th>
			<td><input id="blooderDistrict"
				class="easyui-combobox easyui-validatebox" value=""
				name="blooderDistrict"
				data-options="required:true,
					data:districtData,
					method:'get',
					valueField:'dataDicId',
					textField:'text',
					panelHeight:'auto',
					editable:false,
					panelMaxHeight:140" /></td>
		</tr>
		<tr>
			<td>
				<div style="width: 100px; display: inline;">
					<form id="bloodResultFile" style="display: inline;"
						action="bloodResultController.do?bloodResultFile" method="post"
						enctype="multipart/form-data">
						<input type="file" id="resultFile" name="resultFile"
							class="resultFile" onchange="UploadFile()" />
					</form>
					<a href="#" class="easyui-linkbutton" data-options="width:'70px'">导入数据</a>
				</div>
			</td>
		</tr>
	</table>
	<script>
		var districtData = [ {
			'dataDicId' : '昌平',
			'text' : '昌平'
		} ]
		$.extend($.fn.validatebox.defaults.rules, {
			equals : {
				validator : function(value, param) {
					return value == $(param[0]).val();
				},
				message : '两次输入的密码不一致！'
			}
		});
	</script>
</body>
</html>