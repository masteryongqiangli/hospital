<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpages/easyuipage.jsp"%>
<%@include file="/webpages/baselist.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title></title>
</head>
<body>

	<div class="datacontent">
		<div class="toolbar" id="toolbar">
			<div>
				<ul>
					<li><i></i><font>生命周期管理</font></li>
				</ul>
			</div>
		</div>
		<div class="box" style="height:95%">
			<div style="margin: 25px 0px 0px 25px;">
				<label>选择系统停止时间:</label>
				<input id="dateInput" class="easyui-datebox"></input>
				<a id="find"  href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-add'" onclick="submitDate()">提交修改</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(function(){
		$('#dateInput').datebox('setValue', '${stopDate}');
	})
		function submitDate(){
			$.ajax({
		        type: "post",
		        cache:false, 
		        async:false, 
		        url: 'baseUserController.do?submitDate',
		        data: {"changeDate":$("#dateInput").val()},
		        success: function (data) {
		        	$.messager.show('提示信息',data.msg);
		        }
		    });
		}
	</script>
</body>
</html>