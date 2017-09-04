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
					<li><input id="userName" class="easyui-textbox" type="text"  
						data-options="prompt:'用户名',width:70"/></li>
					<li><input id="realName" class="easyui-textbox" type="text"  
						data-options="prompt:'姓名',width:70"/></li>
					<li><a id="find" href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" onclick="find('user-list')">查询</a></li>
					<li><a id="add" href="#" onclick="add()" class="easyui-linkbutton"
						data-options="iconCls:'icon-add'">新增</a></li>
					 
				</ul>
			</div>
		</div>
		<div class="box">
			<table id="user-list">
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$('#user-list').datagrid({
			url : 'baseUserController.do?datagrid',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			toolbar : '#toolbar',
			pagination:true,
			columns : [ [ {
				field : 'userName',
				title : '用户名',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'realName',
				title : '姓名',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'gener',
				title : '性别',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'village',
				title : '村庄',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'phone',
				title : '电话',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'opt',
				title : '操作',
				width : 60,
				align : 'center',
				halign : 'center',
				formatter : optformatter
			}, ] ]

		});
		function optformatter(value, row, index) {
			var str=''
				if('${auth.updateAuth}'=='true'){
					str+='<a href="#" class="grid-btn grid-edit" onclick="updateItem(\''
						+ row.userId
						+ '\')">修改</a><a href="#" class="grid-btn grid-more" onclick="rolelist(\''
						+ row.userId + '\',\''+row.userName+'\')">角色</a>';
							}
				if('${auth.lookAuth}'=='true'){
					str+='<a href="#" class="grid-btn grid-detail" onclick="lookItem(\''
							+ row.userId
							+ '\')">查看</a>';
							}
				if('${auth.delAuth}'=='true'){
					str+='<a href="#" class="grid-btn grid-delete" onclick="deleteUser(\''
						+ row.userId + '\')">删除</a>';
									}
				return str;	
		}
		 
		function deleteUser(userId){
			deleteItem('baseUserController.do?doDelete&userId='+userId,'user-list');
		}
		function updateItem(userId) {
			openDialog('修改','baseUserController.do?goAddorUpdate&userId='+ userId ,600,top.$(window).height() * 0.8);
		}
		function lookItem(userId) {
			openDialogDetail('查看','baseUserController.do?goLook&userId='+ userId ,600,top.$(window).height() * 0.8);
		}
		function add() {
			openDialog('新增','baseUserController.do?goAddorUpdate',600,top.$(window).height() * 0.85);
		}
		function rolelist(userId,userName) {
			openDialog(userName+'[角色]','baseUserController.do?goUserRoles&userId='+ userId ,600,top.$(window).height() * 0.6);
		}
		 
	</script>
</body>
</html>