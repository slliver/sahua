<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">极速贷维护-添加</c:param>
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
                        <form id="addForm" method="POST" action="${ctx}/loan/save" class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="orgName" class="col-sm-2 control-label">机构Logo</label>
                                    <div class="col-sm-10" id="container">
                                        <button id="browse" type="button" class="btn btn-primary">图片上传</button>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="orgName" class="col-sm-2 control-label">图片</label>
                                    <div class="col-sm-10" id="imgContainer" >

                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="orgName" class="col-sm-2 control-label">优先级</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" name="priority" id="priority" maxlength="3" placeholder="请输入优先级，没有可以不输入" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                               onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                    </div>
                                    <label for="orgNameLable" class="col-sm-4 control-label" style="text-align: left;"><span style="color: red;">如果没有优先级请输入999</span></label>
                                </div>
                                <div class="form-group">
                                    <label for="device" class="col-sm-2 control-label">适用设备</label>
                                    <div class="col-sm-10">
                                        <select name="device" id="device" class="form-control">
                                            <c:forEach var="item" items="${deviceList}">
                                                <option value="${item.key}">${item.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="orgName" class="col-sm-2 control-label">机构名称</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="orgName" id="orgName"
                                               maxlength="50" placeholder="请输入机构名称"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="des" class="col-sm-2 control-label">产品简介</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="des" id="des" maxlength="100"
                                               placeholder="请输入产品简介"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="loanAmount" class="col-sm-2 control-label">借款金额</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="loanAmount" id="loanAmount"
                                               maxlength="20" placeholder="请输入借款金额"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="loanTime" class="col-sm-2 control-label">借款期限</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="loanTime" id="loanTime"
                                               maxlength="20" placeholder="请输入借款期限"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="monthlyInterestRate" class="col-sm-2 control-label">参考月利率</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="monthlyInterestRate" id="monthlyInterestRate" maxlength="20" placeholder="请输入参考月利率"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dayInterestRate" class="col-sm-2 control-label">参考日利率</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="dayInterestRate" id="dayInterestRate" maxlength="20" placeholder="请输入参考日利率"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="passRate" class="col-sm-2 control-label">参考通过率</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="passRate" id="passRate" maxlength="20" placeholder="请输入参考通过率"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="fastestSpeed" class="col-sm-2 control-label">最快放款速度</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="fastestSpeed" id="fastestSpeed" maxlength="20" placeholder="请输入最快放款速度"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="totalApply" class="col-sm-2 control-label">申请人数</label>
                                    <div class="col-sm-10">
                                        <%--<input type="text" class="form-control" name="totalApply" id="totalApply" maxlength="10" placeholder="请输入申请人数"/>--%>
                                        <input type="text" class="form-control" name="totalApply" id="totalApply" maxlength="10" placeholder="请输入申请人数" value=""
                                               onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                               onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="label" class="col-sm-2 control-label">所属标签</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="label" id="label" maxlength="50"
                                               placeholder="请输入标签，已空格分开，比如：下款快 额度高"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="url" class="col-sm-2 control-label">跳转地址</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="url" id="url" maxlength="400" placeholder="请输入跳转地址"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="bannerPkid" class="col-sm-2 control-label">绑定banner</label>
                                    <div class="col-sm-10">
                                        <select name="bannerPkid" id="bannerPkid" class="form-control">
                                            <option value="">请选择banner</option>
                                            <c:forEach var="item" items="${bannerList}">
                                                <option value="${item.pkid}">${item.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group" id="div-applyConditions">
                                    <label for="applyConditions" class="col-sm-2 control-label">申请条件</label>
                                    <div class="col-sm-10">
                                        <button id="btn-applyConditions" name="applyConditions" type="button" class="btn btn-primary">添加</button>
                                    </div>
                                </div>
                                <div class="form-group" id="div-reqMaterials">
                                    <label for="reqMaterials" class="col-sm-2 control-label">所需材料</label>
                                    <div class="col-sm-10">
                                        <button id="btn-reqMaterials" name="reqMaterials" type="button" class="btn btn-primary">添加</button>
                                    </div>
                                </div>
                                <div class="form-group" id="div-earlyRepayments">
                                    <label for="earlyRepayments" class="col-sm-2 control-label">还款说明</label>
                                    <div class="col-sm-10">
                                        <button id="btn-earlyRepayments" name="earlyRepayments" type="button" class="btn btn-primary">添加</button>
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
<!-- 模板 -->
<%--<c:import url="/WEB-INF/views/loan/template.jsp"/>--%>
<div id="applyConditions-template" style="display:none;">
    <!-- 申请条件 -->
    <div class="form-group applyConditions" id="div-applyConditions-{index}">
        <input type="hidden" name="applyConditions[{index}].type" value="1"/>
        <label for="applyConditions" class="col-sm-2 control-label"></label>
        <div class="col-sm-8" style="margin-top:5px;">
            <input type="text" class="form-control" name="applyConditions[{index}].remark" id="applyConditions[{index}].remark" maxlength="200"/>
        </div>
        <div class="col-sm-2">
            <button id="applyConditions_{index}" type="button" class="btn btn btn-danger btn-delete-applyConditions">删除</button>
        </div>
    </div>
</div>
<div id="reqMaterials-template" style="display:none;">
    <!-- 所需材料 -->
    <div class="form-group reqMaterials" id="div-reqMaterials-{index}">
        <input type="hidden" name="reqMaterials[{index}].type" value="2"/>
        <label for="reqMaterials" class="col-sm-2 control-label"></label>
        <div class="col-sm-8" style="margin-top:5px;">
            <input type="text" class="form-control" name="reqMaterials[{index}].remark" id="reqMaterials[{index}].remark" maxlength="200"/>
        </div>
        <div class="col-sm-2">
            <button id="reqMaterials{index}" type="button" class="btn btn btn-danger btn-delete-reqMaterials">删除</button>
        </div>
    </div>
</div>
<div id="earlyRepayments-template" style="display:none;">
    <!-- 提前还款说明 -->
    <div class="form-group earlyRepayments" id="div-earlyRepayments-{index}">
        <input type="hidden" name="earlyRepayments[{index}].type" value="3"/>
        <label for="earlyRepayments" class="col-sm-2 control-label"></label>
        <div class="col-sm-8" style="margin-top:5px;">
            <input type="text" class="form-control" name="earlyRepayments[{index}].remark" id="earlyRepayments[{index}].remark" maxlength="200"/>
        </div>
        <div class="col-sm-2">
            <button id="earlyRepayments{index}" type="button" class="btn btn btn-danger btn-delete-earlyRepayments">删除</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var ctx = '${ctx}';
    $(function () {
        $("#btn-cancel").unbind("click").bind("click", function (event) {
            window.location.href = ctx + "/loan/list";
        });

        $("#btn-submit").unbind("click").bind("click", function (event) {
            ZW.Ajax.doRequest("addForm", ctx + "/loan/save", "", function (data) {
                // 失败
                if (data.res == 0) {
                    ZW.Model.error(data.message);
                }
                // 成功
                if (data.res == 1) {
                    window.location.href = ctx + "/loan/list";
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
<script type="text/javascript">
    $(function () {
        // 添加申请条件
        $("#btn-applyConditions").click(function () {
            var items = $(".box-body").find(".applyConditions");
            var length = items.length;
            var nextIndex = parseInt(length) + 1;
            var content = $("#applyConditions-template").html().replace(/\{index\}/g, nextIndex);
            // console.log(content);
            if(length == 0){
                $("#div-applyConditions").after(content)
            }else{
                // 获取样式为.applyConditions的最后一个元素
                var last = $('div.box-body .applyConditions:last');
                $(last).after(content)
            }
        });

        // 删除申请条件
        $(document).on("click", ".btn-delete-applyConditions", function(e) {
            $(this).parent().parent().remove();
        });

        // 添加所需材料
        $("#btn-reqMaterials").click(function () {
            var items = $(".box-body").find(".reqMaterials");
            var length = items.length;
            var nextIndex = parseInt(length) + 1;
            var content = $("#reqMaterials-template").html().replace(/\{index\}/g, nextIndex);
            // console.log(content);
            if(length == 0){
                $("#div-reqMaterials").after(content)
            }else{
                // 获取样式为.applyConditions的最后一个元素
                var last = $('div.box-body .reqMaterials:last');
                $(last).after(content)
            }
        });

        // 删除所需材料
        $(document).on("click", ".btn-delete-reqMaterials", function(e) {
            $(this).parent().parent().remove();
        });

        // 添加提前还款说明
        $("#btn-earlyRepayments").click(function () {
            var items = $(".box-body").find(".earlyRepayments");
            var length = items.length;
            var nextIndex = parseInt(length) + 1;
            var content = $("#earlyRepayments-template").html().replace(/\{index\}/g, nextIndex);
            // console.log(content);
            if(length == 0){
                $("#div-earlyRepayments").after(content)
            }else{
                // 获取样式为.applyConditions的最后一个元素
                var last = $('div.box-body .earlyRepayments:last');
                $(last).after(content)
            }
        });

        // 删除提前还款说明
        $(document).on("click", ".btn-delete-earlyRepayments", function(e) {
            $(this).parent().parent().remove();
        });
    });
</script>
</html>