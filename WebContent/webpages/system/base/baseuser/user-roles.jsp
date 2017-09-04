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
	<div class="easyui-layout" data-options="fit:true">
		<div class="target" id="In" data-options="region:'west',title:'已拥有角色'"
			style="width: 50%"></div>
		<div class="target" id="NotIn"
			data-options="region:'east',title:'未拥有角色'" style="width: 50%"></div>
	</div>
	<script>
		$(function() {
			//	$('#parent_id').combobox('setValue','${menu.parent_menu.menuId}');
		});
		$(function() {
			var data = JSON.parse('${userRoles}');
			var In = data.In;
			for (var i = 0; i < In.length; i++) {

				var str = '<div id="'+In[i].roleId+'" class="drag" ><span>'
						+ In[i].roleCode;
				str += '</span><span>' + In[i].roleName+ '</span>';

				str += '</div>'
				$('#In').append(str)
			}
			var NotIn = data.NotIn;
			for (var i = 0; i < NotIn.length; i++) {
				var str = '<div id="'+NotIn[i].roleId+'" class="drag" ><span>'
						+ NotIn[i].roleCode;
				str += '</span><span>' + NotIn[i].roleName + '</span>';

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
									 
									init();
									$(this).removeClass('over');
								}
							});
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

		function saveorupdate(obj, dialog_id) {
			var list = $('#In').children('.drag');
			var array = [];
			for (var i = 0; i < list.length; i++) {
				array.push($(list[i]).attr('id'));
				 
			}
			$.post('baseUserController.do?saveUserRoles', {
				userId : '${user.userId}',
				array : array.join(),
			}, function(data) {
				 
				 top.$('#' + obj.id)[0].contentWindow.reload(data,'',
						dialog_id);  
			}, 'json');
		}
	</script>
</body>
</html>