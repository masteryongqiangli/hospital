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
				<th><label>用户名：</label></th>
				<td><span class="detail">${user.userName  }</span>  </td>
			</tr>
			<tr>
				<th><label>身份证号：</label></th>
				<td><span class="detail">${user.idCardNumber  }</span>  </td>
			</tr>
			<tr>
				<th><label>姓名：</label></th>
				<td><span class="detail">${user.realName  }</span> </td>
			</tr>
			<tr>
				<th><label>出生日期：</label></th>
				<td><span class="detail">${birthDate  }</span> </td>
			</tr>
			<tr>
				<th><label>性别：</label></th>
				<td><span class="detail">${user.gener   }</span> </td>
			</tr>
			<tr>
				<th><label>年龄：</label></th>
				<td><span class="detail">${age   }</span> </td>
			</tr>
			<tr>
				<th><label>email：</label></th>
				<td><span class="detail">${user.email  }</span> </td>
			</tr>
			<tr> 
				<th><label>手机：</label></th>
				<td><span class="detail">${user.phone  }</span>  </td>
			</tr>




		</table>
	<script>
		$(function() {
			 
		});
		  
	</script>
</body>
</html>