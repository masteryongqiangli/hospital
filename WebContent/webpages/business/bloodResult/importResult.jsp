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
<form id="bloodResult" method="post" enctype="multipart/form-data"
		action="bloodResultController.do?bloodResultFile">
	<table border="0" cellspacing="0" cellpadding="0" class="submit-table">
		<tr>
			<th><label>所属卫生站：</label></th>
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
			<td colspan="2">
				<div style="width: 100px; display: inline;">
						<input type="file" id="resultFile" name="resultFile"
							class="resultFile" onchange="UploadFile()" />
				</div>
			</td>
		</tr>
	</table>
	</form>
	<script>
		var districtData = JSON.parse('${village.selectData}');
	</script>
</body>
</html>