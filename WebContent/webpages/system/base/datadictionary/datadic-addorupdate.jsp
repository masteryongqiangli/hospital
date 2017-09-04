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
	<form id="datadic" method="post" action="dataDictionaryController.do?doAddorUpdate">
		<input type="hidden" name="dataDicId" value="${datadic.dataDicId }" />
		<input type="hidden" name="state" value="1" />
		 <input type="hidden" name="version" value="${datadic.version }" />
		<table border="0" cellspacing="0" cellpadding="0" class="submit-table">
			<tr>
				<th><label>编码：</label></th>
				<td><input class="easyui-validatebox" type="text" name="code"
					value="${datadic.code }"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th><label>文本：</label></th>
				<td><input class="easyui-validatebox" type="text" name="text"
					value="${datadic.text }"
					data-options="required:true,tipPosition:'right'" /></td>
			</tr>
			<tr>
				<th><label>排序：</label></th>
				<td><input class="easyui-validatebox" type="text" name="orderNum"
					value="${datadic.orderNum }"
					data-options="required:true,tipPosition:'right'" /></td>
			</tr>
			<tr>
				<th><label>分类：</label></th>
				<td><input id="parent_id" class="easyui-combobox"
					name="parent_id"
					data-options="
					url:'dataDictionaryController.do?getParentDataDicList',
					method:'get',
					valueField:'dataDicId',
					textField:'text',
					panelHeight:'auto',
					editable:false,
					panelMaxHeight:100"></td>
			</tr>



		</table>
	</form>
	<script>
		$(function() {
			$('#parent_id').combobox('setValue','${datadic.parent_DataDictionary}');
		});

		 
	</script>
</body>
</html>