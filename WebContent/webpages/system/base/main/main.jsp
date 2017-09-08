<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpages/basepage.jsp"%>
<%@include file="/webpages/easyuipage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="Res/favicon.ico" />
<link rel="bookmark" href="Res/favicon.ico" />
<link href="Res/styles/main/main.css" rel="stylesheet" />

<script src="Res/js/main/main.js"></script>

<title>检验共享系统</title>
</head>
<body style="overflow-y: hidden;">
	<!-- 头部 -->
	<div id="header" class="m-header">
		<div>
			<img
				style="float: left; margin-top: 18px; border: 2px solid #83c2f2; border-radius: 50%;"
				src="Res/images/main/test/head.jpg" />
			<h1 style="font-size: 30px; line-height: 80px;">
				检验共享系统
				<!-- <a><img src="Res/images/main/bg/toplogo.png"></a>  -->
			</h1>
		</div>
		<ul class="m-nav">
			<!-- <li><a href="javascript:void(0)" class="on"><i
					class="iconfont">&#xe60d;</i><span>首页</span></a></li> -->
			<!-- <li><a href="javascript:void(0)"><i class="iconfont">&#xe600;</i><span>快捷导航</span></a></li>
			<li><a href="javascript:void(0)"><i class="iconfont">&#xe608;</i><span>帮助中心</span></a></li>-->
			<li><a href="javascript:void(0)" onclick="userinfo()"><i
					class="iconfont">&#xe609;</i><span>个人中心</span></a></li>
			<!-- 	<li><a href="javascript:void(0)"><i class="iconfont">&#xe601;</i><span>切换皮肤</span></a></li>
			<li><a href="javascript:void(0)"><i class="iconfont">&#xe60f;</i><span>注销</span></a></li>  -->
		</ul>
		<div id="my" class="head-user">
			<div class="g-left user-info">
				<h3 class="g-textohide">${baseUser.realName}</h3>
				<%-- 	<p class="g-textohide">${baseUser.roleNameList}</p> --%>
			</div>
			<img src="Res/images/main/test/head.jpg" class="g-left" />
			<dl>
				<dd>
					<a href="javascript:void(0)" onclick="logout()">退出系统</a>
				</dd>
			</dl>
		</div>
	</div>
	<div class="g-ohide m-tabnavbox">
		<div id="lefttext" class="left-time g-left">加载中......</div>
		<div class="m-tabnav g-left">
			<ul id="tabs">

			</ul>
		</div>
	</div>
	<!-- 中间部分 -->
	<div id="middle" class="m-middle">
		<!-- 左边导航 -->
		<div class="m-leftnav">
			<dl id="menulist">

			</dl>
		</div>
		<!-- 右边内容 -->
		<div class="m-right"></div>
	</div>

	<!-- 尾部 -->
	<div class="m-foot" style="width: 100%; text-align: center;">
		<span> <a target="blank" class="c-cblue">版权所有 ©北京市房山区****
				地址：北京市房山区***** </a>
		</span>
	</div>
	<!-- 消息弹出 -->
	<div id="userinfo" class="easyui-dialog" title="个人中心"
		style="width: 800px; height: 600px;"
		data-options="cache:false,closed:true,modal:true,href:'baseUserController.do?goUserinfo',buttons:[
		{ text:'保存', handler:function(){saveUserInfo()} }
		,{ text:'关闭',handler:function(){$('#userinfo').dialog('close');} }]">

	</div>

	<script>
		//控制高度
		function resetHeight() {
			$('#middle').height(document.documentElement.clientHeight - 156);
		}
		resetHeight();
		$(window).resize(resetHeight);
		//加载菜单
		getUserMenuList();
		//用户快捷操作
		$('#my').mouseenter(function() {
			$(this).find("dl").stop().slideDown('fast');
		}).mouseleave(function() {
			$(this).find("dl").stop().slideUp('fast');
		});

		//左边菜单控制
		$(".m-leftnav dl dt a").click(function() {
			$(".m-leftnav dl dt a").removeClass("on");
			$(this).addClass("on");
		})
		$(".m-leftnav dl dt a").mouseenter(function() {
			if ($(this).parents("dt").next("dd").is(":hidden")) {
				$(".m-leftnav dl dt a").removeClass("hover");
				$(".m-leftnav dl dd").hide();
				$(this).addClass("hover").parent().next("dd").show();
			}
		})
		$(".m-leftnav dl").mouseleave(function() {
			$(".m-leftnav dl dd").hide();
			$(".m-leftnav dl dt a").removeClass("hover")
		})
		function refreshmenu() {
			//左边菜单控制
			$(".m-leftnav dl dt a").click(function() {
				$(".m-leftnav dl dt a").removeClass("on");
				$(this).addClass("on");
			})
			$(".m-leftnav dl dt a").mouseenter(function() {
				if ($(this).parents("dt").next("dd").is(":hidden")) {
					$(".m-leftnav dl dt a").removeClass("hover");
					$(".m-leftnav dl dd").hide();
					$(this).addClass("hover").parent().next("dd").show();
				}
			})
			$(".m-leftnav dl").mouseleave(function() {
				$(".m-leftnav dl dd").hide();
				$(".m-leftnav dl dt a").removeClass("hover")
			})
		}
		//消息
		$(function() {
			$(".js-message .m-rightfixed").click(function() {
				$(this).hide().next().show();
				$(".js-message .message-colse").click(function() {
					$(this).parents(".m-message").hide().prev().show();
				})
			})
		})
		//tab选项卡
		var ul_w = $(".m-right").width() - 20;
		var li_w = ($(".m-tabnav li").width() + 20)
				* ($(".m-tabnav li").length);
		var a_w = (ul_w / ($(".m-tabnav li").length)) - 10;
		if (li_w > ul_w) {
			$(".m-tabnav ul li").css("width", a_w + "px");
		}

		function closetab(obj, id) {
			if ($('.nava.on').next().attr('onclick') == $(obj).attr('onclick')) {
				$(obj).parents("li").prev().find('.nava').addClass('on');
				$(obj).parents("li").remove();
				$('#content' + id).prev().show();
				$('#content' + id).remove();
			} else {
				$(obj).parents("li").remove();
				$('#content' + id).remove();
			}

		}
		function selecttab(obj, id) {
			$('.nava.on').removeClass('on');
			$('.m-right').children().hide();
			$(obj).parents("li").find('.nava').addClass('on');
			$('#content' + id).show();

		}
		//头部筛选旁边两个按钮的点击效果
		$(".js-btnbox .js-btn").click(function() {
			$(this).parent().addClass("on").siblings().removeClass("on");
		})
		addTab('home', '首页', 'loginController.do?goHome', false);
		function logout() {
			$.messager.confirm('确认?', '您确定退出该系统吗？', function(r) {
				if (r) {
					window.location.href = 'loginController.do?logout';
				}
			});

		}
		function userinfo() {
			$('#userinfo').dialog('open');
		}
		function addTab(id, title, url, closeable) {

			if ($('#tab' + id).length > 0) {
				$('.nava.on').removeClass('on');
				$('.m-right').children().hide();
				$('#tab' + id).find('.nava').addClass('on');
				$('#content' + id).show();
			} else {
				$('.nava.on').removeClass('on');
				var str1 = '<li id="tab'+id+'"  ><a  ';
				if (!closeable) {
					str1 += 'style="padding-right:15px" ';
				}
				str1 += 'onclick="selecttab(this,\''
						+ id
						+ '\' ) " oncontextmenu="rightmenu(this)" href="javascript:void(0)" class="nava on">'
						+ title + '</a>';
				str1 + ' <a  href="javascript:void(0)" >';
				if (closeable) {
					str1 += '<i  onclick="closetab(this,\'' + id
							+ '\')" class="iconfont close">&#xe60b;</i> '
				}
				str1 += ' </a></li>';
				$('#tabs').append(str1);
				var str2 = ' <div class="content" id="content' + id
						+ '" style="padding:0px;background: #fafafa;width:'
						+ parseInt($('.m-right').width()) + 'px;' + 'height:'
						+ parseInt($('.m-right').height())
						+ 'px; " > <iframe id="iframe' + id
						+ '" frameborder="0" height="100%" width="100%" src="'
						+ url + '">' + ' </iframe></div>';
				$('.m-right').children().hide();
				$('.m-right').append(str2)
			}

		}
		function getUserMenuList() {
			startTime();
			$.post("menuController.do?getUserMenuList", {

			}, function(data, status) {
				makemenu(data)
			}, 'json');
		}
		function startTime() {
			var today = new Date()
			var h = today.getHours()
			var m = today.getMinutes()
			var s = today.getSeconds()
			// add a zero in front of numbers<10
			m = checkTime(m)
			s = checkTime(s)
			$('#lefttext').html(
					today.getFullYear() + '-' + checkTime(today.getMonth() + 1)
							+ '-' + checkTime(today.getDate()) + '  ' + h + ":"
							+ m + ":" + s);
			t = setTimeout('startTime()', 1000)
		}

		function checkTime(i) {
			if (i < 10) {
				i = "0" + i
			}
			return i
		}
		function makemenu(data) {
			var html = '';
			for (var i = 0; i < data.length; i++) {
				debugger
				if (data[i].children.length == 0) {
					html += '<dt><a href="javascript:void(0)" onclick="addTab(\''
							+ data[i].menuId
							+ '\',\''
							+ data[i].menuName
							+ '\',\''
							+ data[i].menuUrl
							+ '\',true)"><i class="iconfont"></i>'
							+ data[i].menuName + '</a></dt>';
				} else {
					html += '<dt><a href="javascript:void(0)" ><i class="iconfont"></i>'
							+ data[i].menuName + '</a></dt>';
					html += '<dd><div class="nav-chirld"><h2><i class="iconfont"></i>'
							+ data[i].menuName + '<ul>';
					for (var j = 0; j < data[i].children.length; j++) {
						html += '<li><a href="javascript:void(0)" onclick="addTab(\''
								+ data[i].children[j].menuId
								+ '\',\''
								+ data[i].children[j].menuName
								+ '\',\''
								+ data[i].children[j].menuUrl
								+ '\',true)"><i class="iconfont"></i>'
								+ data[i].children[j].menuName + '</a></li>';
					}
					html += '</ul></div></dd>';
				}
			}
			$('#menulist').html(html);
			refreshmenu();
		}

		$(window).resize(function() {
			$('.content').css({
				"padding" : "0px",
				"background" : "#fafafa",
				"width" : parseInt($('.m-right').width()),
				"height" : parseInt($('.m-right').height())
			});
		});
	</script>
</body>
</html>