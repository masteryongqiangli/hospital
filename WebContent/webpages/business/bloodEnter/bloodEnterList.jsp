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
					<li><input id="bloodNumber" class="easyui-textbox" type="text"  
						data-options="prompt:'血样编号',width:100"/></li>
					<li><input id="blooderName" class="easyui-textbox" type="text"  
						data-options="prompt:'姓名',width:100"/></li>
					<li><a id="find" href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" onclick="find('bloodEnter-list')">查询</a></li>
					<li><a id="addTestResult" href="#" onclick="addBlood()" class="easyui-linkbutton"
						data-options="iconCls:'icon-add'">新增</a></li>
					<li><a id="goCheck" href="#" onclick="goCheck()" class="easyui-linkbutton"
						data-options="iconCls:'icon-redo'">送检</a></li>
				</ul>
			</div>
		</div>
		<div class="box">
			<table id="bloodEnter-list">
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$('#bloodEnter-list').datagrid({
			url : 'bloodEnterController.do?getBloodEnterList',
			fit : true,
			fitColumns : true,
			nowrap:false,
			striped : true,
			rownumbers : true,
			toolbar : '#toolbar',
			pagination:true,
			columns : [ [ {
				field : 'selectbox',
				title : '选择',
				checkbox:true
			},{
				field : 'bloodNumber',
				title : '血样编号',
				width : 80,
				align : 'center',
				halign : 'center'
			}, {
				field : 'blooderName',
				title : '化验人',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'blooderAge',
				title : '年龄',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'blooderDistrict',
				title : '区县',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'bloodStartTime',
				title : '抽血时间',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'bloodAriveTime',
				title : '送达时间',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'bloodResultTime',
				title : '结果时间',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'bloodOperator',
				title : '操作人',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'opt111',
				title : '操作',
				width : 80,
				align : 'center',
				halign : 'center',
				formatter:optformatter
			}] ]
		});
		function optformatter(value, row, index) {
			var str='';
			if(true){
				str+='<a href="#" class="grid-btn grid-edit" onclick="updateItem(\''
					+ row.id+ '\')">修改</a>';
						}
			if(true){
				str+='<a href="#" class="grid-btn grid-delete" onclick="deleteBlood(\''
					+ row.id + '\')">删除</a>';
								}
			if(row.state==0){
				str+='<a href="#" class="grid-btn grid-more" onclick="goCheckBlood(\''
					+ row.id + '\')">送检</a>';
			}
			return str;	
		}
		 
		function deleteBlood(bloodId){
			deleteItem('bloodEnterController.do?doDelete&bloodId='+bloodId,'bloodEnter-list');
		}
		function updateItem(bloodId) {
			openDialog('修改','bloodEnterController.do?goAddorUpdate&bloodId='+ bloodId ,600,top.$(window).height() * 0.8);
		}
		function lookItem(bloodId) {
			var resultId = "";
			$.ajax({
		        type: "post",
		        cache:false, 
		        async:false, 
		        url: 'bloodResultController.do?getResultId',
		        data: {bloodId:bloodId},
		        success: function (data) {
		        	resultId = data.msg;
		        }
		    });
			openDialog('查看','bloodResultController.do?goAddorUpdate&resultId='+ resultId ,600,top.$(window).height() * 0.85);
		}
		function addBlood() {
			openDialog('新增','bloodEnterController.do?goAddorUpdate',600,top.$(window).height() * 0.8);
		}
		function exportItem(bloodEnterId){
			window.location.href="bloodEnterController.do?exportWord&bloodEnterId="+bloodEnterId;
		}
		function goCheck(){
			var rows = $("#bloodEnter-list").datagrid('getSelections');
			if(rows.length==0){
				$.messager.alert('提示信息','请先选择检验人');
			}else{
				var array = new Array();
				for(var i=0;i<rows.length;i++){
					if(rows[i].state==0){
						array.push(rows[i].id);
					}
				}
				doGoCheck(array);
			}
		}
		function goCheckBlood(id){
			var array = new Array();
			array.push(id);
			doGoCheck(array);
		}
		function doGoCheck(array){
			$.ajax({
		        type: "post",
		        cache:false, 
		        async:false, 
		        url: 'bloodEnterController.do?doGoCheck&array='+array,
		        data: {},
		        success: function (data) {
		        	$.messager.show({
		        		title:'提示信息',
		        		msg:data.msg,
		        		timeout:3000,
		        		showType:'slide'
		        	});
		        	$("#bloodEnter-list").datagrid("reload");
		        }
		    });
		}
	</script>
</body>
</html>