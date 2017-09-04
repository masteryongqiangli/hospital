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
	<form id="menu" method="post" action="menuController.do?doAddorUpdate">
		<input type="hidden" name="menuId" value="${menu.menuId }" />
		<input type="hidden" name="state" value="1" />
		 <input type="hidden" name="version" value="${menu.version }" />
		<table border="0" cellspacing="0" cellpadding="0" class="submit-table">
			<tr>
				<th><label>菜单名称：</label></th>
				<td><input class="easyui-validatebox" type="text" name="menuName"
					value="${menu.menuName }"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th><label>Url：</label></th>
				<td><input class="easyui-validatebox" type="text" name="menuUrl"
					value="${menu.menuUrl }"
					data-options="required:true,tipPosition:'right'" /></td>
			</tr>
			<tr>
				<th><label>排序：</label></th>
				<td><input class="easyui-validatebox" type="text" name="orderNum"
					value="${menu.orderNum }"
					data-options="required:true,tipPosition:'right'" /></td>
			</tr>
			<tr>
				<th><label>父级菜单：</label></th>
				<td><input id="parent_id" class="easyui-combobox"
					name="parent_id"
					data-options="
					url:'menuController.do?getParentMenuList',
					method:'get',
					valueField:'value',
					textField:'text',
					panelHeight:'auto',
					editable:false,
					panelMaxHeight:150"></td>
			</tr>



		</table>
	</form>
	<script>
		$(function() {
			$('#parent_id').combobox('setValue','${menu.parent_menu.menuId}');
		});

		 
	</script>
</body>
</html>