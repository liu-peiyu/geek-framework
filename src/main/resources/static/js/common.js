var tableModel = (function () {
    return{
        getHeight : function () {
            return $(window).height() - $('.content-header').outerHeight(true);
        },
        getState : function (value,row,index) {
            return value==1 ? "启用" : "禁用";
        }
    }
})();

var layerModel = (function () {
    return{
        closeParent : function () {
            var current = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(current);
        }
    }
})();

var operaModel = (function () {
    return{
        delRow : function (rowid,url,field) {
            layer.confirm('确定删除吗?', function(){
                $.getJSON(url, {ids:rowid}, function(ret){
                    if (ret.status){
                        layer.msg(ret.msg, {icon: 1});
                        $table.bootstrapTable('remove', {
                            field: field,
                            values: [rowid]
                        });
                    } else {
                        layer.msg(ret.msg, {icon: 2});
                    }
                });
            });
        },
        //重新刷新页面，使用location.reload()有可能导致重新提交
        reloadPage : function (win) {
            var location = win.location;
            location.href = location.pathname + location.search;
        },
        /**
         * 页面跳转
         * @param url
         */
        redirect : function (url) {
            location.href = url;
        }
    }
})();


var uploadModel = (function () {
    return{
        /**
         * 打开文件上传对话框
         * @param dialog_title 对话框标题
         * @param callback 回调方法，参数有（当前dialog对象，选择的文件数组，你设置的extra_params）
         * @param extra_params 额外参数，object
         * @param multi 是否可以多选
         * @param filetype 文件类型，image,video,audio,file
         */
        open_upload_dialog : function (title,callback,extra_params,multi,filetype){
            multi = multi ? 1 : 0;
            filetype = filetype ? filetype : 'image';
            var params = 'multi='+multi+'&fileType='+filetype ;
            layer.open({
                type: 2,
                title: title,
                closeBtn: false,
                shadeClose: true,
                shade: false,
                maxmin: false,
                area: ['500px', '600px'],
                content: '/console/upload/index/?' + params,
                btn: ['确定', '取消'],
                yes : function(index, layero){
                    if (typeof callback =='function') {
                        var iframewindow = $(layero).find('iframe')[0].contentWindow;
                        var files=iframewindow.get_selected_files();
                        if(files){
                            callback.apply(layero,[layero, files]);
                        }else{
                            return false;
                        }
                        layer.close(index);
                    }
                },
                btn2: function(index) {
                    layer.close(index);
                }
            });
        },

        upload_one : function (title,input_selector,filetype,extra_params){
            this.open_upload_dialog(title,function(layero,files){
                $('#'+input_selector).val(files[0].filepath);
            },extra_params,0,filetype);
        },

        upload_one_image: function (title,input_selector,extra_params){
            this.open_upload_dialog(title,function(layero,files){
                $('#'+input_selector).val(files[0].preview_url);
                $('#'+input_selector+'-preview').attr('src',files[0].preview_url);
            },extra_params,0,'image');
        },
        /**
         * 多图上传
         * @param dialog_title 上传对话框标题
         * @param container_selector 图片容器
         * @param item_tpl_wrapper_id 单个图片html模板容器id
         */
        upload_multi_image : function (title,container_selector,item_tpl_wrapper_id,extra_params){
            this.open_upload_dialog(title,function(layero,files){
                var tpl=$('#'+item_tpl_wrapper_id).html();
                var html='';
                $.each(files,function(i,item){
                    var itemtpl= tpl;
                    itemtpl=itemtpl.replace(/\{id\}/g,item.id);
                    itemtpl=itemtpl.replace(/\{url\}/g,item.url);
                    itemtpl=itemtpl.replace(/\{preview_url\}/g,item.preview_url);
                    itemtpl=itemtpl.replace(/\{filepath\}/g,item.filepath);
                    itemtpl=itemtpl.replace(/\{name\}/g,item.name);
                    html+=itemtpl;
                });
                $('#'+container_selector).append(html);

            },extra_params,1,'image');
        }
    }
})();

(function () {
    //全局ajax处理
    $.ajaxSetup({
        complete: function (jqXHR) {},
        data: {},
        error: function (jqXHR, textStatus, errorThrown) {}
    });

    if ($.browser && $.browser.msie) {
        //ie 都不缓存
        $.ajaxSetup({
            cache: false
        });
    }

    //不支持placeholder浏览器下对placeholder进行处理
    if (document.createElement('input').placeholder !== '') {
        $('[placeholder]').focus(function () {
            var input = $(this);
            if (input.val() == input.attr('placeholder')) {
                input.val('');
                input.removeClass('placeholder');
            }
        }).blur(function () {
            var input = $(this);
            if (input.val() == '' || input.val() == input.attr('placeholder')) {
                input.addClass('placeholder');
                input.val(input.attr('placeholder'));
            }
        }).blur().parents('form').submit(function () {
            $(this).find('[placeholder]').each(function () {
                var input = $(this);
                if (input.val() == input.attr('placeholder')) {
                    input.val('');
                }
            });
        });
    }

    var ajaxForm_list = $('form.js-ajax-form');
    if (ajaxForm_list.length) {
        var $btn;
        $('button.js-ajax-submit').on('click', function (e) {
            var btn = $(this);
            var form = btn.parents('form.js-ajax-form');
            $btn=btn;

            if(btn.data("loading")){
                return;
            }

            //ie处理placeholder提交问题
            if ($.browser && $.browser.msie) {
                form.find('[placeholder]').each(function () {
                    var input = $(this);
                    if (input.val() == input.attr('placeholder')) {
                        input.val('');
                    }
                });
            }
        });

        ajaxForm_list.each(function(){
            $(this).validate({
                debug:true,
                //是否在获取焦点时验证
                //onfocusout : false,
                //当鼠标掉级时验证
                //onclick : false,
                //给未通过验证的元素加效果,闪烁等
                //highlight : false,
                onkeyup : function( element, event ){
                  return;
                },
                showErrors:function(errorMap, errorArr){
                    if(parseInt(errorArr.length) > 0){
                        $(errorArr[0].element).focus();
                        layer.msg(errorArr[0].message, {icon: 2});
                    }
                },
                submitHandler:function(form){
                    var $form= $(form);
                    $form.ajaxSubmit({
                        url: $btn.data('action') ? $btn.data('action') : $form.attr('action'),
                        dataType: 'json',
                        beforeSubmit: function (arr, $form, options) {
                            $btn.data("loading",true);
                            var text = $btn.text();
                            $btn.text(text + '中...').prop('disabled', true).addClass('disabled');
                        },
                        success: function (data, statusText, xhr, $form) {
                            var text = $btn.text();
                            $btn.removeClass('disabled').prop('disabled', false).text(text.replace('中...', '')).parent().find('span').remove();
                            if (data.state === 'success') {
                                layer.msg(data.msg, {icon: 1}, function () {
                                    if (data.referer) {
                                        operaModel.redirect(data.referer);//返回带跳转地址
                                    } else {
                                        if (data.state === 'success') {
                                            operaModel.reloadPage(window);//刷新当前页
                                        }
                                    }
                                });
                            }else if(data.state === 'error'){
                                layer.msg(data.msg, {icon: 2});
                            }
                        },
                        error:function(xhr,e,statusText){
                            console.log(statusText);
                            operaModel.reloadPage(window);//刷新当前页
                        },
                        complete: function(){
                            $btn.data("loading",false);
                        }
                    });
                }
            });
        });
    }
})();