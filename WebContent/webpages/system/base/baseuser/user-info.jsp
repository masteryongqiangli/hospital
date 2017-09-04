
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<body>
	<link rel="stylesheet" type="text/css"
		href="Res/styles/item-detail.css">
	<form id="userinfoform" method="post" >
		<input type="hidden" name="userId" value="${user.userId }" />
		<input type="hidden" name="state" value="1" />
		 <input type="hidden" name="version" value="${version }" />
		<table border="0" cellspacing="0" cellpadding="0" class="submit-table"
			style="width: 95%">
			<tr>
				<th><label>用户名：</label></th>
				<td><input id="userName" class="easyui-validatebox" type="text"
					invalidMessage="该用户已存在" missingMessage="用户名不能为空"
					validType="remote['baseUserController.do?userInfoValid&userId=${user.userId }&type=0','userName']"
					name="userName" value="${user.userName }"
					data-options="required:true" /></td>
			</tr>
			<tr>
				<th><label>修改密码：</label></th>
				<td><input id="password" class="easyui-validatebox" type="password"
					name="password" value="" /></td>
			</tr>
			<tr>
				<th><label>确认密码：</label></th>
				<td><input class="easyui-validatebox" type="password"
					name="rePassword" value="" validType="equals['#password']" /></td>
			</tr>
			<tr>
				<th><label>姓名：</label></th>
				<td><input class="easyui-validatebox" type="text"
					name="realName" value="${user.realName }"
					data-options="required:true,tipPosition:'right'" /></td>
			</tr>
			<tr>
				<th><label>身份证号：</label></th>
				<td><input class="easyui-validatebox" type="text"
					id="idCardNumber" name="idCardNumber" value="${user.idCardNumber }"
					invalidMessage="身份证号格式错误" onblur="idcardready()" 
					validType="remote['baseUserController.do?userInfoValid&type=1','idCardNumber']"
					data-options="tipPosition:'right',onBeforeValidate:beforeValididCard" /></td>
			</tr>
			<tr>
				<th><label>出生日期：</label></th>
				<td><div id="birthDate">${user.birthDate }</div></td>
			</tr>
			<tr>
				<th><label>性别：</label></th>
				<td><div id="gener">${user.gener }</div></td>
			</tr>
			<tr>
				<th><label>年龄：</label></th>
				<td><div id="age">${user.age }</div></td>
			</tr>
			<tr>
				<th><label>email：</label></th>
				<td><input class="easyui-validatebox" type="text" name="email"
					value="${user.email }"
					data-options="" /></td>
			</tr>
			<tr>
				<th><label>手机：</label></th>
				<td><input class="easyui-validatebox" type="text" name="phone"
					value="${user.phone }"
					data-options="" /></td>
			</tr>


		</table>
	</form>
	<script>
		function saveUserInfo() {
			$('#userinfoform').form({
				 
				 
				url : 'baseUserController.do?saveUserinfo',
				onSubmit : function() {
					if($('#userinfoform').form('validate'))
						$.messager.progress({text:'数据处理中'}); 
					else return  false;
					

					// do some check    
					// return false to prevent submit;    
				},
				success : function(data) {
					$.messager.progress('close');
					data = JSON.parse(data);
					alert(data.msg)
					 
				}
			});
			// submit the form    
			$('#userinfoform').submit();
			 
			 
		}
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
