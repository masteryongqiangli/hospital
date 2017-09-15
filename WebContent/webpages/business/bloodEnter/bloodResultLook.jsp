<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpages/easyuipage.jsp"%>
<%@include file="/webpages/baselist.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
 .resultFile {
	opacity: 0.0;
	width: 50px;
	position: absolute;
	z-index: 5000;
} 
</style>
<title></title>
</head>
<body>
	<div class="datacontent">
		<div class="toolbar" id="toolbar">
			<div>
				<ul>
					<li><input id="bloodNumber" class="easyui-textbox" type="text"
						data-options="prompt:'血样编号',width:100" /></li>
					<li><input id="blooderName" class="easyui-textbox" type="text"
						data-options="prompt:'姓名',width:100" /></li>
					<li><a id="find" href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'"
						onclick="find('bloodResult-list')">查询</a></li>
					<li><a id="find" href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-print'"
						onclick="printAllItem()">批量打印</a></li>
				</ul>
			</div>
		</div>
		<div class="box">
			<table id="bloodResult-list">
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$('#bloodResult-list').datagrid({
			url : 'bloodResultController.do?getBloodResultList',
			fit : true,
			striped : true,
			rownumbers : true,
			toolbar : '#toolbar',
			pagination : true,
			columns : [ [ {
				field : 'selectbox',
				title : '选择',
				checkbox:true
			},{
				field : 'bloodNumber',
				title : '血样编号',
				width : 150,
				align : 'center',
				halign : 'center'
			}, {
				field : 'blooderName',
				title : '化验人',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'ALB',
				title : '总蛋白',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'ALP',
				title : '白蛋白',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'ALT',
				title : '球蛋白',
				width : 60,
				align : 'center',
				halign : 'center'
			}, {
				field : 'AST',
				title : '白球比',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'CK',
				title : '总胆红素',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'CK_MB',
				title : '直接胆红素',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'CRE',
				title : '间接胆红素',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'DBIL',
				title : '转氨酶',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'GGT',
				title : '总胆固醇',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'GLU',
				title : '甘油三酯',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'HBDH',
				title : '高密度脂蛋白',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'HDL_C',
				title : '低密度脂蛋白',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'LDH',
				title : '载脂蛋白',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'LDL_C',
				title : '空腹血糖',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'TBIL',
				title : '肌酐',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'TC',
				title : '尿素氮',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'TG',
				title : '尿酸',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'TP',
				title : '乳酸脱氢酶',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'UA',
				title : '肌酸肌酶',
				width : 60,
				align : 'center',
				halign : 'center',
			}, {
				field : 'UREA',
				title : '肌酸肌酶1',
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
			}  ] ]
		});
		function optformatter(value, row, index) {
			var str='';
			if(true){
				str+='<a href="#" class="grid-btn grid-edit" onclick="printItem(\''
					+ row.id+ '\')">打印</a>';
						}
			return str;	
		}
		function deleteBlood(Id){
			deleteItem('bloodResultController.do?doDelete&resultId='+Id,'bloodResult-list');
		}
		function updateItem(Id,index) {
			openDialog('修改','bloodResultController.do?goAddorUpdate&resultId='+ Id ,600,top.$(window).height() * 0.85);
		}
		function openImportDialog(){
			openDialogNoBtn('录入结果','bloodResultController.do?goImportResult' ,600,top.$(window).height() * 0.5);
		}
	</script>
</body>
</html>