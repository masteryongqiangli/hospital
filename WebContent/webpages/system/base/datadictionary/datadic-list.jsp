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
					<li><i></i><font>数据字典</font></li>
					<li><input id="text" class="easyui-textbox" type="text"  
						data-options="prompt:'文本',width:70"></input</li>
					<li><input id="code" class="easyui-textbox" type="text"  
						data-options="prompt:'编码',width:70"></input</li>
					<li><select id="parent_id" class="easyui-combobox"  
						style="width: 200px;"
						data-options="
					url:'dataDictionaryController.do?getParentDataDicList',
					method:'get',
					valueField:'dataDicId',
					textField:'text',
					panelHeight:'auto',
					panelMaxHeight:150">
					</select></li>
					<li><a  id="find" href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" onclick="find()">查询</a></li>
				</ul>
			</div>
			<div>
				<ul>
					<li><a href="#" onclick="add()" class="easyui-linkbutton"
						data-options="iconCls:'icon-add'">新增</a></li>
					<li><a href="#" onclick="collapse()" class="easyui-linkbutton"
						data-options="iconCls:'icon-collapse'">合并全部</a></li>
					<li><a href="#" onclick="expend()" class="easyui-linkbutton"
						data-options="iconCls:'icon-expend'">展开全部</a></li>
				</ul>
			</div>
		</div>
		<div class="box">
			<table id=datadic-list>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$('#datadic-list').treegrid({
			url : 'dataDictionaryController.do?treegrid',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			animate : true,
			toolbar : '#toolbar',
			idField : 'No',
			treeField : 'Text',
			columns : [ [ {
				field : 'Text',
				title : '字典文本',
				width : 60,
				align : 'left',
				halign : 'center'
			}, {
				field : 'Code',
				title : '字典编码',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'OrderNum',
				title : '排序',
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
			if(typeof(row.children)=='undefined')
		 	{
				var str=''
					if('${auth.updateAuth}'=='true'){
						str+='<a href="#" class="grid-btn grid-edit" onclick="updateItem(\''
							+ row.DataDicId
							+ '\')">修改</a>';
								}
					if('${auth.lookAuth}'=='true'){
						str+='<a href="#" class="grid-btn grid-detail" onclick="lookItem(\''
								+ row.DataDicId
								+ '\')">查看</a>';
								}
					if('${auth.delAuth}'=='true'){
						str+='<a href="#" class="grid-btn grid-delete" onclick="deleteDataDic(\''
							+ row.DataDicId + '\')">删除</a>';
										}
					return str;	
			}
			
		}
		function deleteDataDic(DataDicId){
			deleteItem('dataDictionaryController.do?doDelete&DataDicId='+DataDicId,'datadic-list');
		}
		function updateItem(DataDicId) {
			openDialog('修改','dataDictionaryController.do?goAddorUpdate&DataDicId='+ DataDicId ,500,top.$(window).height() * 0.6);
		}
		function lookItem(DataDicId) {
			openDialogDetail('查看','dataDictionaryController.do?goLook&DataDicId='+ DataDicId ,500,top.$(window).height() * 0.6);
		}
		function expend() {
			$('#datadic-list').treegrid('expandAll');
		}
		function collapse() {
			$('#datadic-list').treegrid('collapseAll');
		}
		function find(){
			$('#datadic-list').treegrid('load', {    
				parent_id: $('#parent_id').combobox('getValue'),    
			    text: $('#text').val(),
			    code:$('#code').val()
			});  
		}
		function add() {
			openDialog('新增','dataDictionaryController.do?goAddorUpdate',500,top.$(window).height() * 0.6);
		}
		
	</script>
</body>
</html>