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
				<td><input id="bloodNumber" class="easyui-validatebox"
					type="text" name="bloodNumber" value="${bloodEnter.bloodNumber}"
					data-options="" readonly="readonly" /></td>
			</tr>
			<tr>
				<th><label>化验人：</label></th>
				<td><input id="blooderName" class="easyui-validatebox"
					name="blooderName" value="${bloodEnter.blooderName}"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th><label>身份证号：</label></th>
				<td><input id="blooderIdCard" class="easyui-validatebox"
					name="blooderIdCard" value="${bloodEnter.blooderIdCard}"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th><label>年龄：</label></th>
				<td><input id="blooderAge" class="easyui-validatebox"
					data-options="" readonly="readonly" name="blooderAge"
					value="${bloodEnter.blooderAge}" validType="intType" /></td>
			</tr>
			<!-- <tr>
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
			</tr> -->
			<tr>
				<th><label>抽血时间：</label></th>
				<td><input id="bloodStartTime" class="easyui-datebox"
					name="bloodStartTime" value="${bloodEnter.bloodStartTime}"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th><label>送达时间：</label></th>
				<td><input id="bloodAriveTime" class="easyui-datebox"
					name="bloodAriveTime" value="${bloodEnter.bloodAriveTime}"
					data-options="required:true" /></td>
			</tr>
		</table>
	</form>
	<script>
		$(document).ready(
				function() {
					var today = new Date();
					var year = today.getFullYear().toString();
					var month = today.getMonth() > 9 ? (today.getMonth() + 1)
							.toString() : "0"
							+ (today.getMonth() + 1).toString();
					var day = today.getDate() > 9 ? today.getDate().toString()
							: "0" + today.getDate().toString();
					var mintues = today.getMinutes()>9?today.getMinutes().toString():"0"+today.getMinutes().toString();
					/* $("#blooderDistrict").combobox('select','${bloodEnter.blooderDistrict}'); */
					var orderNumber=0;
					$.ajax({
						type : "POST",
						async : false,
						url : "bloodEnterController.do?getBloodNumber",
						data : {
						},
						success : function(data) {
							$("#bloodNumber").val(
									'${villageNum}' + year + month + day
											+ today.getHours() + mintues+data.msg);
						}
					});
				});
		$("#blooderIdCard").blur(
				function() {
					var nowDate = new Date();
					var age = nowDate.getFullYear()
							- parseInt($(this).val().substr(6, 4));
					if ($(this).val() == "") {
						$("#blooderAge").val("");
					} else {
						$("#blooderAge").val(age);
					}
				});
		var districtData = JSON.parse('${districtData}').data;
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