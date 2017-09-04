<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/webpages/easyuipage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="Res/styles/item-detail.css">
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div class="target" id="In" data-options="region:'west',title:'已有菜单'"
			style="width: 50%"></div>
		<div class="target" id="NotIn"
			data-options="region:'east',title:'其他菜单'" style="width: 50%"></div>
	</div>
	<script>
		$(function() {
			var data = JSON.parse('${rolemenus}');
			var In = data.In;
			for (var i = 0; i < In.length; i++) {
				var parent_id = In[i].menu.parent_menu == null ? 'null'
						: In[i].menu.parent_menu.menuId

				var str = '<div id="'+In[i].menu.menuId+'"parent_id="'+parent_id +'" class="drag" ><div><span>'
						+ In[i].menu.menuName;
				str += '</span></div><div><span>' + In[i].menu.menuUrl + '</span></div>';
				if(parent_id!='null'){
					str += '<div><span>增&nbsp;<input type="checkbox" name="addAuth" '
					if(In[i].addAuth){
						str+='checked="checked"';
					}
					str +=	' />';
					str += '删&nbsp;<input type="checkbox"  name="delAuth"  ';
					if(In[i].delAuth){
						str+='checked="checked"';
					}
					str +=	' />';
					str += '改&nbsp;<input type="checkbox"  name="updateAuth"  ';
					if(In[i].updateAuth){
						str+='checked="checked"';
					}
					str +=	' />';
					str += '查&nbsp;<input type="checkbox"  name="lookAuth"  ';
					if(In[i].lookAuth){
						str+='checked="checked"';
					}
					str +=	' /></span></div>';
				}
				str += '</div>'
				$('#In').append(str)
			}
			var NotIn = data.NotIn;
			for (var i = 0; i < NotIn.length; i++) {
				var parent_id = NotIn[i].parent_menu == null ? 'null'
						: NotIn[i].parent_menu.menuId
				var str = '<div id="'+NotIn[i].menuId+'"parent_id="'+parent_id +'" class="drag" ><div><span>'
						+ NotIn[i].menuName;
				str += '</span></div><div><span>' + NotIn[i].menuUrl + '</span></div>';
				if(parent_id!='null'){
					str += '<div><span>增<input type="checkbox" name="addAuth" />';
					str += '删<input type="checkbox" name="delAuth"/>';
					str += '改<input type="checkbox" name="updateAuth"/>';
					str += '查<input type="checkbox" name="lookAuth" /></span></div>';
				}
				
				str += '</div>'
				$('#NotIn').append(str)
			}
			init();
			$('.target')
					.droppable(
							{
								accept : '.drag',
								onDragEnter : function(e, source) {
									$(source).draggable('options').cursor = 'auto';
									$(source).draggable('proxy').css('border',
											'1px solid red');
									$(this).addClass('over');
								},
								onDragLeave : function(e, source) {
									$(source).draggable('options').cursor = 'not-allowed';
									$(source).draggable('proxy').css('border',
											'1px solid #ccc');
									$(this).removeClass('over');
								},
								onDrop : function(e, source) {
									$(this).append(source)
									if ($(source).attr('parent_id') == 'null') {
										var list = $(".target").find(
												"[parent_id='" + source.id
														+ "']");
										for (var i = 0; i < list.length; i++) {
											$(source).parent('.target').append(
													list[i])
										}

									}
									init();
									$(this).removeClass('over');
								}
							});
			//	$('#parent_id').combobox('setValue','${menu.parent_menu.menuId}');
		});
		function init() {
			$('.drag').draggable({
				proxy : 'clone',
				revert : true,
				cursor : 'move',
				onStartDrag : function() {
					$(this).draggable('options').cursor = 'not-allowed';
					$(this).draggable('proxy').addClass('dp');
				},
				onStopDrag : function() {
					$(this).draggable('options').cursor = 'auto';
				}
			});
		}

		 function saveorupdate(objobj, dialog_id) {
			var list = $('#In').children('.drag');
			var array = [];
			var parentarray = [];
			for (var i = 0; i < list.length; i++) {
				if($(list[i]).attr('parent_id')!='null'){
					var obj={id:$(list[i]).attr('id'),
							addAuth:$(list[i]).find("[name='addAuth']")[0].checked,
							delAuth:$(list[i]).find("[name='delAuth']")[0].checked,
							updateAuth:$(list[i]).find("[name='updateAuth']")[0].checked,
							lookAuth:$(list[i]).find("[name='lookAuth']")[0].checked}
					array.push(obj);
					if(parentarray.indexOf($(list[i]).attr('parent_id'))<0 ){
						var obj1={id:$(list[i]).attr('parent_id')}
						array.push(obj1);
						parentarray.push($(list[i]).attr('parent_id'));
					}
				}
			}
 
			    $.post('roleController.do?saveRoleMenus', {
				roleId : '${role.roleId}',
				array :JSON.stringify(array)
			}, function(data) {
				/* $.messager.show({
					title : '提示消息',
					msg : data.msg,
					timeout : 5000,
					showType : 'slide'
				}); */
				  top.$('#' + objobj.id)[0].contentWindow.reload(data,'role-list',
						dialog_id);  
			}, 'json');   
		} 
	</script>
</body>
</html>