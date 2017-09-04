 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 
 
<link rel="stylesheet" type="text/css"
	href="Res/styles/item-list.css">
	<link rel="stylesheet" type="text/css"
	href="Res/jquery-easyui-1.5.1/easyuiloading.css">
<script type="text/javascript"
	src="Res/jquery-easyui-1.5.1/easyuiloading.js"></script>
	<script type="text/javascript">
	$(function() {
 		if('${auth.addAuth}'!='true'){
			$('#add').remove();
			if('${auth.updateAuth}'!='true'&&'${auth.delAuth}'!='true'){
				$('#'+$('.box').find('.datagrid-f').attr('id')).datagrid('hideColumn','opt')
			}
		}
	});
	 
	$(document).keydown(function(event) {
		if (event.keyCode == 13) { //绑定回车 
			$("#find").click();  
		}
	});
	function find(id){
		var data={};
		var parms=$('#toolbar').find('input');
		for(var i=0;i<parms.length;i++){
			data[parms[i].id]=$(parms[i]).val();
		}
		try{
			$('#'+id).datagrid('load',  data); 
			$('#'+id).treegrid('load',  data);
		}catch(e){
			
		}
		
	}
	function openDialogFit(title,url){
		var id= window.frameElement.id;
		var obj=top.$('#'+ id)[0];
		var str = '<div id="dialog'+id+'" style="width:90%;height:90%">Dialog Content.</div> '
		top.$('body').prepend(str);
		top.$('#dialog'+id).dialog(
				{
					title : title,
					closed : false,
					cache : false,
					content : '<iframe id="iframechild'+id+'" frameborder="0" height="99%" width="100%" src="'+url+'">  </iframe>',
					modal : true,
					buttons : [
							{
								text : '保存',
								handler : function() {
									top.$('#iframechild'+id)[0].contentWindow.saveorupdate(obj,'dialog'+id) ;
								}
							}, {
								text : '关闭',
								handler : function() {
									top.$('#dialog'+id).dialog('destroy');
								}
							} ],
					onClose : function() {
						top.$('#dialog'+id).dialog('destroy');
					}
				});
	}
	function openDialog(title,url,width,height){
		var id= window.frameElement.id;
		var obj=top.$('#'+ id)[0];
		var str = '<div id="dialog'+id+'">Dialog Content.</div> '
		top.$('body').prepend(str);
		top.$('#dialog'+id).dialog(
				{
					title : title,
					width : width,  
					height : height,
					closed : false,
					cache : false,
					content : '<iframe id="iframechild'+id+'" frameborder="0" height="99%" width="100%" src="'+url+'">  </iframe>',
					modal : true,
					buttons : [
							{
								text : '保存',
								handler : function() {
									top.$('#iframechild'+id)[0].contentWindow.saveorupdate(obj,'dialog'+id) ;
								}
							}, {
								text : '关闭',
								handler : function() {
									top.$('#dialog'+id).dialog('destroy');
								}
							} ],
					onClose : function() {
						top.$('#dialog'+id).dialog('destroy');
					}
				});
	}
	function openDialogdefine(title,url,width,height,btn_name){
		var id= window.frameElement.id;
		var obj=top.$('#'+ id)[0];
		var str = '<div id="dialog'+id+'">Dialog Content.</div> '
		top.$('body').prepend(str);
		top.$('#dialog'+id).dialog(
				{
					title : title,
					width : width,  
					height : height,
					closed : false,
					cache : false,
					content : '<iframe id="iframechild'+id+'" frameborder="0" height="99%" width="100%" src="'+url+'">  </iframe>',
					modal : true,
					buttons : [
							{
								text : btn_name,
								handler : function() {
									top.$('#iframechild'+id)[0].contentWindow.saveorupdate(obj,'dialog'+id) ;
								}
							}, {
								text : '关闭',
								handler : function() {
									top.$('#dialog'+id).dialog('destroy');
								}
							} ],
					onClose : function() {
						top.$('#dialog'+id).dialog('destroy');
					}
				});
	}
	function openDialogDetail(title,url,width,height){
		var id= window.frameElement.id;
		var obj=top.$('#'+ id)[0];
		var str = '<div id="dialog'+id+'">Dialog Content.</div> '
		top.$('body').prepend(str);
		top.$('#dialog'+id).dialog(
				{
					title : title,
					width : width,  
					height : height,
					closed : false,
					cache : false,
					content : '<iframe id="iframechild'+id+'" frameborder="0" height="99%" width="100%" src="'+url+'">  </iframe>',
					modal : true,
					buttons : [ {
								text : '关闭',
								handler : function() {
									top.$('#dialog'+id).dialog('destroy');
								}
							} ],
					onClose : function() {
						top.$('#dialog'+id).dialog('destroy');
					}
				});
	}
	function openDialogDetailFit(title,url){
		var id= window.frameElement.id;
		var obj=top.$('#'+ id)[0];
		var str = '<div id="dialog'+id+'" style="width:90%;height:90%">Dialog Content.</div> '
		top.$('body').prepend(str);
		top.$('#dialog'+id).dialog(
				{
					title : title,
				 
					closed : false,
					cache : false,
					content : '<iframe id="iframechild'+id+'" frameborder="0" height="99%" width="100%" src="'+url+'">  </iframe>',
					modal : true,
					buttons : [ {
								text : '关闭',
								handler : function() {
									top.$('#dialog'+id).dialog('destroy');
								}
							} ],
					onClose : function() {
						top.$('#dialog'+id).dialog('destroy');
					}
				});
	}
	function openDialogNoBtn(title,url,width,height){
		var id= window.frameElement.id;
		var obj=top.$('#'+ id)[0];
		var str = '<div id="dialog'+id+'">Dialog Content.</div> '
		top.$('body').prepend(str);
		top.$('#dialog'+id).dialog(
				{
					title : title,
					width : width,  
					height : height,
					closed : false,
					cache : false,
					content : '<iframe id="iframechild'+id+'" frameborder="0" height="99%" width="100%" src="'+url+'">  </iframe>',
					modal : true,
				 
					onClose : function() {
						top.$('#dilaog'+id).dialog('destroy');
					}
				});
	}
	function reload(data,grid_id,dialog_id){
		$.messager.show({
			title : '提示消息',
			msg : data.msg,
			timeout : 5000,
			showType : 'slide'
		}); 
		try{
			$('#'+grid_id).datagrid('reload');
			$('#'+grid_id).treegrid('reload');
		}catch(e){
			
		}
		
		
		top.$('#'+dialog_id).dialog( 'destroy');
	}
	
	
	function deleteItem(url,grid_id) {
		top.$.messager.confirm('提示信息', '您确认要删除该项目吗？', function(r) {
			if (r) {
				$.post(url, {
					 
				}, function(data) {
					$.messager.show({
						title : '提示消息',
						msg : data.msg,
						timeout : 5000,
						showType : 'slide'
					});
					$('#'+grid_id).datagrid('reload');
					$('#'+grid_id).treegrid('reload');
				}, 'json');
			}
		});

	}
	function changeItem(url,grid_id,msg) {
		top.$.messager.confirm('提示信息', msg, function(r) {
			if (r) {
				$.post(url, {
					 
				}, function(data) {
					$.messager.show({
						title : '提示消息',
						msg : data.msg,
						timeout : 5000,
						showType : 'slide'
					});
					$('#'+grid_id).datagrid('reload');
					$('#'+grid_id).treegrid('reload');
				}, 'json');
			}
		});

	}
	</script>
 