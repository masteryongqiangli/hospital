
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
		</table>
	</form>
	<script>
		function saveUserInfo() {
			$('#userinfoform').form({
				url : 'baseUserController.do?changePswd&id=${user.userId}',
				onSubmit : function() {
					if($('#userinfoform').form('validate'))
						$.messager.progress({text:'数据处理中'}); 
					else return  false;
				},
				success : function(data) {
					$.messager.progress('close');
					$.messager.show({
						title:'提示信息',
						msg:JSON.parse(data).msg,
						showType:'slide',
						timeout:5000
					})
				}
			});
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
