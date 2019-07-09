function uploadFile() {
    var ctx = '${ctx}';
    // CSRF攻击使用的TOKEN，配合header的csrfMetaTags标签
    var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[csrfHeader] = csrfToken;
    var uploader = new plupload.Uploader({
        runtimes: 'html5,flash,silverlight,html4',
        browse_button: 'browse', // this can be an id of a DOM element or the DOM element itself
        container: document.getElementById('container'), // ... or DOM Element itself
        url: '${ctx}/resource/upload',
        headers: headers,
        multi_selection: false,
        multipart_params: {
            'module': 'loan'
        },
        filters: {
            max_file_size: '20mb',
            mime_types: [
                {title: "Image files", extensions: "jpg,gif,png"},
            ]
        },
        // Flash settings
        flash_swf_url: "${ctx}/static/plupload/Moxie.swf",
        // Silverlight settings
        silverlight_xap_url: "${ctx}/static/plupload/js/Moxie.xap",
        init: {
            PostInit: function () {
                // 初始化操作
            },

            FilesAdded: function (up, files) {
                plupload.each(files, function (file) {

                });
                uploader.start();
            },
            Error: function (up, err) {
                // 上传错误
                ZW.Model.error(err.message);
            }
        }
    });
    uploader.init();

    uploader.bind('BeforeUpload', function (uploader, file) {
        //每个事件监听函数都会传入一些很有用的参数，
        //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
        $.blockUI({message: '正在处理，请稍侯...'});
    });

    uploader.bind('FileUploaded', function (uploader, files, result) {
        if (result.status == 200) {
            // toastr.success('文件上传成功，文件访问路径为：' + result.response);
            uploader.files = [];
            var file = JSON.parse(result.response);
            var fileId = file.data;

            $("#imgContainer").html("");
            $("#logoPkid").val(fileId);

            var item = "";
            item += "   <div class='thumbnail' style=\"text-align: left;\">";
            item += "       <img src= " + ctx + "/resource/showImage?pkid=" + fileId + " style='height:180px;width: 400px;'/>";
            item += "   </div>";
            $("#imgContainer").append(item);
            comp.unblock();
        }
    });
}