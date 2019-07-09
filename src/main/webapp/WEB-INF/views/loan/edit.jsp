<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">极速贷维护-编辑</c:param>
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
                编辑
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
                        <form id="addForm" method="post" action="${ctx}/loan/update" class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="pkid" value="${loan.pkid}"/>
                            <input type="hidden" name="flagVersion" value="${loan.flagVersion}"/>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="orgName" class="col-sm-2 control-label">机构Logo</label>
                                    <div class="col-sm-10" id="container">
                                        <button id="browse" type="button" class="btn btn-primary">图片上传</button>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="orgName" class="col-sm-2 control-label">图片</label>
                                    <div class="col-sm-10" id="imgContainer">
                                        <c:if test="${loan.logoPkid != null && loan.logoPkid != ''}">
                                            <div class='thumbnail' style="text-align: left;">
                                                <input type='hidden' name='logoPkid' value="${loan.logoPkid}"/>
                                                <img src="${ctx}/resource/showImage?pkid=${loan.logoPkid}" style='height:180px;width: 400px;'/>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="orgName" class="col-sm-2 control-label">优先级</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" name="priority" id="priority" value="${loan.priority}" maxlength="3" placeholder="请输入优先级，没有可以不输入" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                               onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
                                    </div>
                                    <label for="orgNameLable" class="col-sm-4 control-label" style="text-align: left;"><span style="color: red;">如果没有优先级请输入999</span></label>
                                </div>
                                <div class="form-group">
                                    <label for="device" class="col-sm-2 control-label">适用设备</label>
                                    <div class="col-sm-10">
                                        <select name="device" id="device" class="form-control">
                                            <c:forEach var="item" items="${deviceList}">
                                                <option value="${item.key}" <c:if test="${loan.device == item.key}">selected</c:if>>${item.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="orgName" class="col-sm-2 control-label">机构名称</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="orgName" id="orgName"
                                               maxlength="50" placeholder="请输入机构名称" value="${loan.orgName}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="des" class="col-sm-2 control-label">产品简介</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="des" id="des" maxlength="100"
                                               placeholder="请输入产品简介" value="${loan.des}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="loanAmount" class="col-sm-2 control-label">借款金额</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="loanAmount" id="loanAmount"
                                               maxlength="20" placeholder="请输入借款金额" value="${loan.loanAmount}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="loanTime" class="col-sm-2 control-label">借款期限</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="loanTime" id="loanTime"
                                               maxlength="20" placeholder="请输入借款期限" value="${loan.loanTime}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="monthlyInterestRate" class="col-sm-2 control-label">参考月利率</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="monthlyInterestRate"
                                               id="monthlyInterestRate" maxlength="20" placeholder="请输入参考月利率"
                                               value="${loan.monthlyInterestRate}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="passRate" class="col-sm-2 control-label">参考通过率</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="passRate" id="passRate"
                                               maxlength="20" placeholder="请输入参考通过率" value="${loan.passRate}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dayInterestRate" class="col-sm-2 control-label">参考日利率</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="dayInterestRate" id="dayInterestRate" value="${loan.dayInterestRate}" maxlength="20" placeholder="请输入参考日利率"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="fastestSpeed" class="col-sm-2 control-label">最快放款速度</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="fastestSpeed" id="fastestSpeed" value="${loan.fastestSpeed}" maxlength="20" placeholder="请输入最快放款速度"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="totalApply" class="col-sm-2 control-label">申请人数</label>
                                    <div class="col-sm-10">
                                        <%--<input type="text" class="form-control" name="totalApply" id="totalApply" maxlength="10" placeholder="请输入申请人数" value="${loan.totalApply}"/>--%>
                                        <input type="text" class="form-control" name="totalApply" id="totalApply" maxlength="10" placeholder="请输入申请人数" value="${loan.totalApply}"
                                               onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                               onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="label" class="col-sm-2 control-label">所属标签</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="label" id="label" maxlength="50"
                                               placeholder="请输入标签，已空格分开，比如：下款快 额度高" value="${loan.label}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="url" class="col-sm-2 control-label">跳转地址</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="url" id="url" maxlength="400" value="${loan.url}" placeholder="请输入跳转地址"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="bannerPkid" class="col-sm-2 control-label">绑定banner</label>
                                    <div class="col-sm-10">
                                        <input type="hidden" name="originalBannerPkid" value="${loan.originalBannerPkid}"/>
                                        <select name="bannerPkid" id="bannerPkid" class="form-control">
                                            <option value="">请选择banner</option>
                                            <c:forEach var="item" items="${bannerList}">
                                                <option value="${item.pkid}"<c:if test="${loan.originalBannerPkid == loan.bannerPkid}">selected</c:if>>${item.name}</option>
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
                                <c:choose>
                                    <c:when test="${not empty applyConditions && fn:length(applyConditions) > 0}">
                                        <c:forEach items="${applyConditions}" var="item" varStatus="status">
                                            <div class="form-group applyConditions" id="div-applyConditions-${status.index}">
                                                <input type="hidden" name="applyConditions[${status.index}].type" value="1"/>
                                                <label for="applyConditions" class="col-sm-2 control-label"></label>
                                                <div class="col-sm-8" style="margin-top:5px;">
                                                    <input type="text" class="form-control" name="applyConditions[${status.index}].remark" value="${item.remark}" id="applyConditions[${status.index}].remark" maxlength="200"/>
                                                </div>
                                                <div class="col-sm-2">
                                                    <button id="applyConditions_${status.index}" type="button" class="btn btn btn-danger btn-delete-applyConditions">删除</button>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                                <div class="form-group" id="div-reqMaterials">
                                    <label for="reqMaterials" class="col-sm-2 control-label">所需材料</label>
                                    <div class="col-sm-10">
                                        <button id="btn-reqMaterials" name="reqMaterials" type="button" class="btn btn-primary">添加</button>
                                    </div>
                                </div>
                                <c:choose>
                                    <c:when test="${not empty reqMaterials && fn:length(reqMaterials) > 0}">
                                        <c:forEach items="${reqMaterials}" var="item" varStatus="status">
                                            <div class="form-group reqMaterials" id="div-reqMaterials-${status.index}">
                                                <input type="hidden" name="reqMaterials[${status.index}].type" value="2"/>
                                                <label for="reqMaterials" class="col-sm-2 control-label"></label>
                                                <div class="col-sm-8" style="margin-top:5px;">
                                                    <input type="text" class="form-control" name="reqMaterials[${status.index}].remark" value="${item.remark}" id="reqMaterials[${status.index}].remark" maxlength="200"/>
                                                </div>
                                                <div class="col-sm-2">
                                                    <button id="reqMaterials_${status.index}" type="button" class="btn btn btn-danger btn-delete-reqMaterials">删除</button>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                                <div class="form-group" id="div-earlyRepayments">
                                    <label for="earlyRepayments" class="col-sm-2 control-label">还款说明</label>
                                    <div class="col-sm-10">
                                        <button id="btn-earlyRepayments" name="earlyRepayments" type="button" class="btn btn-primary">添加</button>
                                    </div>
                                </div>
                                <c:choose>
                                    <c:when test="${not empty earlyRepayments && fn:length(earlyRepayments) > 0}">
                                        <c:forEach items="${earlyRepayments}" var="item" varStatus="status">
                                            <div class="form-group earlyRepayments" id="div-earlyRepayments-{${status.index}}">
                                                <input type="hidden" name="earlyRepayments[${status.index}].type" value="3"/>
                                                <label for="earlyRepayments" class="col-sm-2 control-label"></label>
                                                <div class="col-sm-8" style="margin-top:5px;">
                                                    <input type="text" class="form-control" name="earlyRepayments[${status.index}].remark" value="${item.remark}" id="earlyRepayments[${status.index}].remark" maxlength="200"/>
                                                </div>
                                                <div class="col-sm-2">
                                                    <button id="earlyRepayments{${status.index}}" type="button" class="btn btn btn-danger btn-delete-earlyRepayments">删除</button>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
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
</div>
<!-- 模板 -->
<c:import url="/WEB-INF/views/loan/template.jsp"/>
</body>
<script type="text/javascript">
    var ctx = '${ctx}';
    $(function () {
        $("#btn-cancel").unbind("click").bind("click", function (event) {
            window.location.href = ctx + "/loan/list";
        });

        $("#btn-submit").unbind("click").bind("click", function (event) {
            ZW.Ajax.doRequest("addForm", ctx + "/loan/update", "", function (data) {
                // 失败
                if (data.res == 0) {
                    ZW.Model.error(data.message);
                }
                // 成功
                if (data.res == 1) {
                    window.location.href = ctx + "/loan/list";
                }
            });
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
</html>