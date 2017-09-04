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
	<form id="user" method="post"
		action="baseUserController.do?saveUserinfo">
		<input type="hidden" name="userId" value="${user.userId }" /> <input
			type="hidden" name="state" value="1" />
			 <input type="hidden" name="version" value="${user.version }" />
		<table border="0" cellspacing="0" cellpadding="0" class="submit-table">
			<tr>
				<th><label>用户名：(6-18)</label></th>
				<td><input id="userName" class="easyui-validatebox" type="text"  
					    invalidMessage="用户名不合法，或用户名已存在" 
					validType="remote['baseUserController.do?userInfoValid&userId=${user.userId }&type=0','userName']"
					name="userName" value="${user.userName }"  
					data-options="required:true,onBeforeValidate:userNameValid" /></td>
			</tr>
			<tr class="password">
				<th><label>密码：</label></th>
				<td><input id="password" class="easyui-validatebox"
					type="password" name="password" value=""  data-options="required:true"/></td>
			</tr>
			<tr class="password">
				<th><label>确认密码：</label></th>
				<td><input class="easyui-validatebox" type="password" data-options="required:true"
					name="rePassword" value="" validType="equals['#password']" /></td>
			</tr>
			<tr>
				<th><label>身份证号：</label></th>
				<td><input class="easyui-validatebox" type="text"
					id="idCardNumber" name="idCardNumber" value="${user.idCardNumber }"
					invalidMessage="身份证号格式错误" onblur="idcardready()" 
					validType="remote['baseUserController.do?userInfoValid&type=1','idCardNumber']"
					data-options="tipPosition:'right',onBeforeValidate:beforeValididCard,required:true" /></td>
			</tr>
			<tr>
				<th><label>姓名：</label></th>
				<td><input class="easyui-validatebox" type="text"
					name="realName" value="${user.realName }"
					data-options="required:true,tipPosition:'right'" /></td>
			</tr>
			<!-- <tr>
				<th><label>所属区县：</label></th>
				<td><input id="district_id" class="easyui-combobox easyui-validatebox"
					name="dataDicId"
					data-options="required:true,
					 data:bj,
					method:'get',
					valueField:'dataDicId',
					textField:'text',
					panelHeight:'auto',
					editable:false,
					panelMaxHeight:140"/></td>
			</tr>
			<tr>
				<th><label>所属乡镇：</label></th>
				<td><input id="town_id" class="easyui-combobox easyui-validatebox"
					name="dataDicId"
					data-options="required:true,
					data:bj,
					method:'get',
					valueField:'dataDicId',
					textField:'text',
					panelHeight:'auto',
					editable:false,
					panelMaxHeight:140"/></td>
			</tr> -->
			<tr>
				<th><label>所属村庄：</label></th>
				<td><input id="village_id" class="easyui-combobox easyui-validatebox"
					name="dataDicId"
					data-options="required:true,
					data:bj,
					method:'get',
					valueField:'dataDicId',
					textField:'text',
					panelHeight:'auto',
					editable:false,
					panelMaxHeight:140"/></td>
			</tr>
			<!--<tr>
				<th><label>出生日期：</label></th>
				<td><div id="birthDate">${birthDate }</div></td>
			</tr>
			<tr>
				<th><label>性别：</label></th>
				<td><div id="gener">${user.gener }</div></td>
			</tr>
			<tr>
				<th><label>年龄：</label></th>
				<td><div id="age">${age}</div></td>
			</tr>
			<tr>
				<th><label>email：</label></th>
				<td><input class="easyui-validatebox" type="text" name="email"
					value="${user.email }" data-options="" validType="email"  /></td>
			</tr>-->
			<tr> 
				<th><label>手机：</label></th>
				<td><input class="easyui-validatebox" type="text" name="phone"  validType="phone"
					value="${user.phone }" data-options="required:true" /></td>
			</tr>




		</table>
	</form>
	<script>
		$(function() {
			if('${user.userId }'!=''){
				$('.password').remove();
			}
			$('#village_id').combobox('select','${user.village.text}');
		});
       var bj=${selects.village};
		function idcardready() {
			$.post('baseUserController.do?getIdCardInfo', {
				idCardNumber : $('#idCardNumber').val(),
			}, function(data) {
				if (data.state == true) {
					for (key in data) {
						$('#' + key).html(data[key]);
					}
				}
			}, 'json');
		}
		function userNameValid(){
			if ($('#userName').val().length >=6
					&& $('#userName').val().length <= 18) {
				return true;
			} else {
				return false;
			}
		}
		function beforeValididCard() {
			if ($('#idCardNumber').val().length == 15
					|| $('#idCardNumber').val().length == 18) {
				return true;
			} else {
				return false;
			}

		}
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