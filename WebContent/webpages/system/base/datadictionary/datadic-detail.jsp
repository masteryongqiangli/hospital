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
				<th><label>编码：</label></th>
				<td><span class="detail">${datadic.code  }</span> </td>
			</tr>
			<tr>
				<th><label>文本：</label></th>
				<td><span class="detail">${datadic.text  }</span> </td>
			</tr>
			<tr>
				<th><label>排序：</label></th>
				<td><span class="detail">${datadic.orderNum  }</span> </td>
			</tr>
			<tr>
				<th><label>分类：</label></th>
				<td><span class="detail">${ parent  }</span></td>
			</tr>



		</table>
	<script>
		$(function() {
		});

		 
	</script>
</body>
</html>