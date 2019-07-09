<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">Banner维护-添加</c:param>
</c:import>
<div class="wrapper">
    <!-- Main Header -->
    <c:import url="/WEB-INF/views/include/header_navbar.jsp"/>
    <!-- Left side column. contains the logo and sidebar -->
    <c:import url="/WEB-INF/views/include/left_navbar.jsp"/>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                添加
                <small>Optional description</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">Here</li>
            </ol>
        </section>
        <!-- Main content -->

        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">Horizontal Form</h3>
                        </div>
                        <form id="addForm" method="POST" action="${ctx}/banner/save" class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">Logo</label>
                                    <div class="col-sm-10" id="container">
                                        <button id="browse" type="button" class="btn btn-primary">图片上传</button>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">图片</label>
                                    <div class="col-sm-10" id="imgContainer" >

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">名称</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="name" id="name" maxlength="50" placeholder="请输入名称"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="bussinessType" class="col-sm-2 control-label">业务类型</label>
                                    <div class="col-sm-4">
                                        <select name="bussinessType" id="bussinessType" class="form-control">
                                            <c:forEach var="item" items="${bussinessList}">
                                                <option value="${item.key}">${item.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="forwardType" class="col-sm-2 control-label">跳转类型</label>
                                    <div class="col-sm-4">
                                        <select name="forwardType" id="forwardType" class="form-control">
                                            <c:forEach var="item" items="${forwardList}">
                                                <option value="${item.key}">${item.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="url" class="col-sm-2 control-label">跳转地址</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="url" id="url" maxlength="500" placeholder="请输入跳转地址"/>
                                    </div>
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="button" class="btn btn-default" id="btn-cancel">取消</button>
                                <button type="button" class="btn btn-info pull-right" id="btn-submit">提交</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <c:import url="/WEB-INF/views/include/footer.jsp"/>
    <script type="text/javascript" src="${ctx}/static/plugins/plupload/plupload.full.min.js"></script>
</div>
</body>
<script type="text/javascript">
    var ctx = '${ctx}';

    $(function () {
        $("#btn-cancel").unbind("click").bind("click", function (event) {
            window.location.href = ctx + "/banner/list";
        });

        $("#btn-submit").unbind("click").bind("click", function (event) {
            ZW.Ajax.doRequest("addForm", ctx + "/banner/save", "", function (data) {
                // 失败
                if (data.res == 0) {
                    ZW.Model.error(data.message);
                }
                // 成功
                if (data.res == 1) {
                    window.location.href = ctx + "/banner/list";
                }
            })
        });

        uploadFile();
    });

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
                'module': 'banner'
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
                var item = "";
                item += "   <div class='thumbnail' style=\"text-align: left;\">";
                item += "       <input type='hidden' name='logoPkid' value='" + fileId + "'/>";
                item += "       <img src= " + ctx + "/resource/showImage?pkid=" + fileId + " style='height:180px;width: 400px;'/>";
                item += "   </div>";
                $("#imgContainer").append(item);
                comp.unblock();
            }
        });
    }
</script>
</html>