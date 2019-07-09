/**
 * 文件上传 封装
 */
(function( $ ){  
	/**
	 * @param module 模块名称,可为空,用于服务器存储文件夹命名,默认demo
	 * @param businessPkid 业务id,可为空
	 * @param fn 回调函数 返回文件pkid,用于图片展示及回写修改文件的业务id
	 */
	$.fn.pluploader = function(option) {
		// CSRF攻击使用的TOKEN，配合header的csrfMetaTags标签
		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
	    var csrfToken = $("meta[name='_csrf']").attr("content");
	    var headers = {};
	    headers[csrfHeader] = csrfToken;
	    var ext = '';
	    if(option.type == 'image'){
	    	ext = "jpg,gif,png,jpeg";
	    } else if(option.type == 'doc'){
	    	ext = "pdf,txt,doc,docx,xls,xlsx,xml";
	    } else if(option.type == 'all'){
            ext = "jpg,gif,png,jpeg,doc,docx";
        } else {
	    	ext = option.type;
	    }
	    var ct = document.getElementById(option.container);
	    var uploader = new plupload.Uploader({
	        runtimes : 'html5,flash,silverlight,html4',
	        browse_button: this.attr('id'), // this can be an id of a DOM element or the DOM element itself
	        container: ct, // ... or DOM Element itself
	        url: option.url || ctx+'/common/resource/upload',
	        headers : headers,
	        multi_selection: option.module || false,
	        multipart_params: option.params || {},
	        filters : {
	            max_file_size : '600mb',
	            mime_types: [
	                {title : "files", extensions : ext},
	            ]
	        },
	        // Flash settings
	        flash_swf_url : "${ctx}/static/plupload/Moxie.swf",
	        // Silverlight settings
	        silverlight_xap_url : "${ctx}/static/plupload/js/Moxie.xap",
	        init: {
	            PostInit: function() {
	                // 初始化操作
	            },

	            FilesAdded: function(up, files) {
	                var max = 3;
	                // 验证已经上传的文件个数
	                var flength = $("#fileList").find(".fileItem").length;
	                if(flength > max){
	                    ZW.Model.error("最多上传" + (max+1) + "个文件");
	                    return;
	                }
	                uploader.start();
	            },
	            Error: function(up, err) {
	                // 上传错误
	                ZW.Model.error(err.message);
	            }
	        }
	    });
	    uploader.init();

	    uploader.bind('Error', function(uploader, error) {
			var errorMsg = error.message;
	   		var errorCode = error.code;
	   		if(errorCode == -600) 
	   			errorMsg = '上传文件大小请不要超过' + uploader.getOption().filters.max_file_size;
	   		else if(errorCode == -601) 
	   			errorMsg = '请上传' + uploader.getOption().filters.mime_types[0].extensions + '类型文件';
	   		else 
	   			errorMsg = '上传失败'
	   		ZW.Model.error(errorMsg);
	   		comp.unblock();
		});
	    
	    uploader.bind('FileUploaded', function(uploader, files, result) {
	    	if(typeof(option.success) == 'function') {
	    		if(result.status == 200) {
		            uploader.files = [];
		            var rst = JSON.parse(result.response);
		            // 上传完成后处理,返回的为RichAjaxResult
		            option.success.call(this,rst);
		            comp.unblock();
		        } 
	    	}
	    });

        uploader.bind('BeforeUpload',function(uploader,file){
            //每个事件监听函数都会传入一些很有用的参数，
            //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
            $.blockUI({ message: '正在处理，请稍侯...' });
        });

    };
})( jQuery );  
    