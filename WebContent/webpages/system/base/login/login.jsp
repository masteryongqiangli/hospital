<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpages/basepage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="Res/favicon.ico" />
<link rel="bookmark" href="Res/favicon.ico" />
<link rel="stylesheet" href="Res/styles/login/login.css">
<script src="Res/js/jquery.js"></script>
<title>北京市村镇卫生站血液检验系统</title>
<script>
	function checkuser() {
		if($('#userName').val()==''){
			$('#USER_NULL').show();
			return ;
		}
		if($('#password').val()==''){
			$('#PASSWORD_NULL').show();
			return ;
		}
		$('#submit').val('登录中......');
		$('#submit').css('background','#55abed');
		$('#submit').attr('onclick','');
		$.post('loginController.do?checkuser', {
			userName : $('#userName').val(),
			password : $('#password').val()
		}, function(data) {
			if (data.loginState != 'LOGIN_SUCCESS') {
				$('#' + data.loginState).show();
				$('#submit').val('登录');
				$('#submit').attr('onclick','checkuser()');
			} else {
				$('#submit').val('登录成功，正在跳转中');
				/* sleep(1000); */
				window.location.href = 'loginController.do?login';
			}
		}, 'json');
	}
	$(document).keydown(function(event) {
		if (event.keyCode == 13) { //绑定回车 
			checkuser(); //自动触发登录按钮 
		}
	});
	$(function() {
		$('#userName').change(function() {
			$('#userName').nextAll('lable').hide();
		});
		$('#password').change(function() {
			$('#password').nextAll('lable').hide();
		});
	});
	function sleep(numberMillis) {
		var now = new Date();
		var exitTime = now.getTime() + numberMillis;
		while (true) {
			now = new Date();
			if (now.getTime() > exitTime)
				return;
		}
	}
	
</script>
</head>
<body style="overflow-y:hidden">
	<div class="content">
		<div class="content_body">
			<div class="top">
				<h1>
					<img src=" Res/images/login/hospital.png">
				</h1>
				<h1>北京市村镇卫生站血液检验系统</h1>
				 
			</div>
			<div class="middle">
				<form id="login" name="login" action="loginController.do?checkuser"
					method="post">
					<div class="input-contians">
						<div class="input-wrapper input-username">
							<input id="userName" name="userName" placeholder="用户名"
								type="text" value="" />
							<lable id="NOT_USER">用户不存在</lable>
							<lable id="USER_NULL">用户不能为空</lable>
						</div>
						<div class="input-wrapper ">
							<input id="password" name="password" type="password"
								placeholder="密码" value="" />
							<lable id="PASSWORD_ERROR">密码错误</lable>
							<lable id="PASSWORD_NULL">密码不能为空</lable>
						</div>
					</div>
					<div class="submit-wrapper">
						<input id="submit" type="button" value="登录" onclick="checkuser()" />
					</div>
				</form>
			</div>
		</div>
	</div>
	 

</body>
</html>