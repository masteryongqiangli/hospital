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
					<li><i></i><font>角色</font></li>
					<li><input id="roleCode" class="easyui-textbox" type="text"  
						data-options="prompt:'角色编码',width:70"/></li>
					<li><input id="roleName" class="easyui-textbox" type="text"  
						data-options="prompt:'角色名称',width:70"/></li>
					<li><a id="find"  href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" onclick="find('role-list')">查询</a></li>
				</ul>
			</div>
			<div>
				<ul>
					<li><a id="add" href="#" onclick="add()" class="easyui-linkbutton"
						data-options="iconCls:'icon-add'">新增</a></li>
					 
				</ul>
			</div>
		</div>
		<div class="box">
			<table id="role-list">
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$('#role-list').datagrid({
			url : 'roleController.do?datagrid',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			toolbar : '#toolbar',
			pagination:true,
			columns : [ [ {
				field : 'roleCode',
				title : '角色编码',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'roleName',
				title : '角色名称',
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
						+ row.roleId
						+ '\')">修改</a><a href="#" class="grid-btn grid-more" onclick="menulist(\''
						+ row.roleId + '\',\''+row.roleName+'\')">菜单</a>';
							}
				if('${auth.lookAuth}'=='true'){
					str+='<a href="#" class="grid-btn grid-detail" onclick="lookItem(\''
							+ row.roleId
							+ '\')">查看</a>';
							}
				if('${auth.delAuth}'=='true'){
					str+='<a href="#" class="grid-btn grid-delete" onclick="deleteRole(\''
						+ row.roleId + '\')">删除</a>';
									}
				return str;	
					
					/* <a href="#" class="grid-btn grid-detail" onclick="userlist(\''
						+ row.roleId + '\',\''+row.roleName+'\')">用户</a> */
		}
		 
		function deleteRole(roleId){
			deleteItem('roleController.do?doDelete&roleId='+roleId,'role-list');
		}
		function updateItem(roleId) {
			openDialog('修改','roleController.do?goAddorUpdate&roleId='+ roleId ,500,top.$(window).height() * 0.6);
		}
		function lookItem(roleId) {
			openDialogDetail('查看','roleController.do?goLook&roleId='+ roleId ,500,top.$(window).height() * 0.6);
		}
		function add() {
			openDialog('新增','roleController.do?goAddorUpdate',500,top.$(window).height() * 0.6);
		}
		function userlist(roleId,roleName) {
			openDialog(roleName+'[用户]','roleController.do?goRoleUsers&roleId='+ roleId ,500,top.$(window).height() * 0.6);
		}
		function menulist(roleId,roleName) {
			openDialog(roleName+'[菜单]','roleController.do?goRoleMenus&roleId='+ roleId ,$(window).width()*0.7,top.$(window).height() * 0.8);
		}
	</script>
</body>
</html>