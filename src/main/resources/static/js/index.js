/**
 * Created by Administrator on 2017/3/27 0027.
 */
var indexModel = (function () {
    return {
        initFrameHeight : function () {
            var window_height = $(window).height();
            var header_height = $('.main-header').outerHeight();
            var footer_height = $('.main-footer').outerHeight();
            $("#mainFrame").height(window_height - header_height - footer_height-10);
            //$("#mainFrame").contents().find("body").css('min-height', window_height - $('.main-footer').outerHeight());
        }

    }
})();

(function () {
    indexModel.initFrameHeight();
})();