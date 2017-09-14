<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpages/basedetail.jsp"%>
<%@include file="/webpages/easyuipage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div>
	<form id="bloodResult" method="post" enctype="multipart/form-data"
		action="bloodResultController.do?bloodResultFile">
		<table border="0" cellspacing="0" cellpadding="0" class="submit-table">
			<tr>
				<th><label>所属卫生站：</label></th>
				<td><input id="blooderDistrict"
					class="easyui-combobox easyui-validatebox" value=""
					name="blooderDistrict"
					data-options="required:true,
					data:selectData,
					method:'get',
					valueField:'dataDicId',
					textField:'text',
					panelHeight:'auto',
					editable:false,
					panelMaxHeight:140" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<div style="width: 100px; display: inline;">
						<input type="file" id="resultFile" name="resultFile"
							class="resultFile"/>
					</div>
				</td>
			</tr>
		</table>
	</form>
	</div>
	<div style="margin: 130px 0 0 45%;">
		<a href="#" class="easyui-linkbutton" data-options="width:'70px'"
			onclick="UploadFile()">确定</a>
	</div>
	<script>
		var selectData = JSON.parse('${village.selectData}');
		var id= window.frameElement.id;
		var obj=top.$('#'+ id)[0];
		var formobj = $('form')[0];
		function UploadFile() {
			var msg="请先";
			if($("#blooderDistrict").val()==""){
				msg += "选择卫生站、";
			}
			if($("#resultFile").val()==""){
				msg += "选择文件、";
			}
			if(msg=="请先"){
				var selections = $("#blooderDistrict").combobox('getText');
				$.messager.confirm('提示信息', "请确认要导入"+selections+"的数据吗", function(r){
					if (r){
						$('#bloodResult').form({
							url : 'bloodResultController.do?bloodResultFile',
							onSubmit : function() {
									$.messager.progress({
										text : '数据处理中'
									});
							},
							success : function(data) {
								$.messager.progress('close');
								$('#bloodResult-list').datagrid('reload');
								$("#resultFile").val('');
								top.$('#dialog'+id).dialog('destroy');
							},
							error:function(){
								$.messager.progress('close');
								$("#resultFile").val('');
								top.$('#dialog'+id).dialog('destroy');
							}
						});
						$('#bloodResult').submit();
					}
				});
			}else{
				$.messager.confirm('提示信息', msg.substring(0,msg.length-1), function(r){
					if (r){
					}
				});
			}
		}
	</script>
</body>
</html>