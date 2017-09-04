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
	<form id="role" method="post" action="roleController.do?doAddorUpdate">
		<input type="hidden" name="roleId" value="${role.roleId }" />
		<input type="hidden" name="state" value="1" />
		 <input type="hidden" name="version" value="${role.version }" />
		<table border="0" cellspacing="0" cellpadding="0" class="submit-table">
			<tr>
				<th><label>角色编码：</label></th>
				<td><input class="easyui-validatebox" type="text" name="roleCode"
					value="${role.roleCode }"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th><label>角色名称：</label></th>
				<td><input class="easyui-validatebox" type="text" name="roleName"
					value="${role.roleName }"
					data-options="required:true,tipPosition:'right'" /></td>
			</tr>
			 



		</table>
	</form>
	<script>
		$(function() {
		//	$('#parent_id').combobox('setValue','${menu.parent_menu.menuId}');
		});

		 
	</script>
</body>
</html>