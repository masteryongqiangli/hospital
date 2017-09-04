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
				<th><label>角色编码：</label></th>
				<td><span class="detail">${role.roleCode }</span> </td>
			</tr>
			<tr>
				<th><label>角色名称：</label></th>
				<td><span class="detail">${role.roleName  }</span>  </td>
			</tr>
		</table>
	<script>
		$(function() {
		});

		 
	</script>
</body>
</html>