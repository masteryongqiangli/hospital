/**
 * 
 */

$(function() {
	var str='<div class="easyuiLoading"><div><table><tr><td class="text">加载中......</td></tr>';
	str+='<tr><td class="loading"></td></tr></table></div></div>';
	$("body").prepend(str); 
	$("body").css('z-index',-1);
});

function closes() {
	$(".easyuiLoading").fadeOut("normal", function() {
		$(this).remove();
		$("body").css('z-index','');
	});
}
var pc;
$.parser.onComplete = function() {
	if (pc) {
		clearTimeout(pc);
	}
	pc = setTimeout(closes, 500);
}