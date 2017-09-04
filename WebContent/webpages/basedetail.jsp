
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 
 

<link rel="stylesheet" type="text/css" href="Res/styles/item-detail.css">
<script type="text/javascript">
	function saveorupdate(obj,dialog_id) {
		debugger	
		if(typeof(othflag)!='undefined'){
			othfunction();
		}
     var formobj=$('form')[0];
		$('#'+formobj.id).form({

			url : formobj.action,
			onSubmit : function() {
				if ($('#'+formobj.id).form('validate'))
					$.messager.progress({
						text : '数据处理中'
					});
				else
					return false;

				// do some check    
				// return false to prevent submit;    
			},
			success : function(data) {
				$.messager.progress('close');
				data = JSON.parse(data);
				top.$('#' + obj.id)[0].contentWindow.reload(data,formobj.id+'-list',dialog_id);
			}
		});
		// submit the form    
		$('#'+formobj.id).submit();

	}
</script>