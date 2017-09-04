
//单选、多选重写
(function ($) {
    var changeSelected = function (selector, area) {
        $(selector, area).each(function (i, n) {
            var me = $(n);
            var parent = me.parent().mouseleave(function () {
                //保证表单验证效果
                $(document.getElementsByName(me.attr('name'))).filter(':last').trigger('blur');
            });
            if ($(".ui-checkbox label, .ui-radio label").hasClass('on')) {
                $(".ui-checkbox .on input, .ui-radio .on input").attr('checked', 'checked');
            };
            if (!n.disabled) {
                if (n.checked) {
                    parent.addClass('on');
                };
            } else {
                n.checked ? parent.addClass('disabled') : parent.addClass('ondisabled');
            }
            if (!me.attr('id')) {
                var id = $(this).attr('type') + (new Date()).valueOf() + i;
                me.attr('id', id).parent().attr('for', id);
            }
            me.on('click', function () {
                if (me.attr('type') == 'radio') {
                    parent.siblings().removeClass('on').find(':radio').each(function (j, m) {
                        $(m).removeAttr('checked');
                    });
                    me.attr('checked', 'checked').parent().addClass('on');
                } else {
                    if (parent.hasClass('on')) {
                        me.removeAttr('checked').parent().removeClass('on');
                    } else {
                        me.attr('checked', 'checked').parent().addClass('on');
                    }
                }
            })
        });
    };

    $.fn.checkbox = $.fn.radio = function (selector) {
        return this.each(function (i, n) {
            changeSelected(selector, n);
        });
    };
})(jQuery);
$(document.body).radio('.ui-radio :radio');
$(document.body).checkbox('.ui-checkbox :checkbox, .ui-vcheckbox :checkbox');
//选项卡切换
$(function () {
    $(".js-tab a").click(function () {
        var showtab = $(this).parent().index();
        $(this).addClass("on").parent().siblings().children().removeClass("on").parents(".js-tab").next(".js-tabinfo").find(".js-tabbox").eq(showtab).show().siblings().hide();
    })
})
//弹出窗口关闭窗口
$(function () {
    $('.js-showdialog').click(function () {
        $('.js-popup').show();
        $('.js-popup .t-close').click(function () {
            $('.js-popup').hide();
        })
    })
});



