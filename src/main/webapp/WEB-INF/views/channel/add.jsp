<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">渠道管理-添加</c:param>
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
                        </div>
                        <form id="addForm" method="POST" onsubmit="return false;" action="${ctx}/channel/update" class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="serverPath" id="serverPath" value="${serverPath}"/>
                            <input type="hidden" name="serverAppPath" id="serverAppPath" value="${serverAppPath}"/>
                            <!-- type 10：渠道 -->
                            <input type="hidden" name="type" value="10"/>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="code" class="col-sm-2 control-label">渠道编码</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="code" id="code" maxlength="20" placeholder="请输入渠道编码"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">渠道名称</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="name" id="name" maxlength="20" placeholder="请输入渠道名称"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="chargeUser" class="col-sm-2 control-label">负责人</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="chargeUser" id="chargeUser" maxlength="20" placeholder="请输入负责人"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="phone" class="col-sm-2 control-label">联系电话</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="phone" id="phone" maxlength="20" placeholder="请输入联系电话"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="scale" class="col-sm-2 control-label">扣量数</label>
                                    <div class="col-sm-10">
                                        <input type="number" class="form-control" name="scale" id="scale" maxlength="20" placeholder="请输入扣量数"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="groupNum" class="col-sm-2 control-label">每组数量</label>
                                    <div class="col-sm-10">
                                        <input type="number" class="form-control" name="groupNum" id="groupNum" maxlength="20" placeholder="请输入每组数量"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="startTime" class="col-sm-2 control-label">扣量开始时间</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="startTime" id="startTime" maxlength="40" value="<fmt:formatDate value="${channel.startTime}" pattern='yyyy-MM-dd HH:mm:ss'/>" placeholder="扣量开始时间" class="form-control datepicker_ymd"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="endTime" class="col-sm-2 control-label">扣量结束时间</label>
                                    <div class="col-sm-10">
                                        <input type="text" name="endTime" id="endTime" maxlength="40" value="<fmt:formatDate value="${channel.endTime}" pattern='yyyy-MM-dd HH:mm:ss'/>" placeholder="扣量结束时间" class="form-control datepicker_ymd"/>
                                    </div>
                                </div>

                                <!--div class="form-group">
                                    <label for="phone" class="col-sm-2 control-label">全流程推广链接</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control copy-input" name="url" id="popularizeUrl" value="" maxlength="200" placeholder="请输入全流程推广链接"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <button type="button" id="btn-generate" name="generate" class="btn btn-primary btn-sm">生成连接</button>
                                        <button type="button" id="btn-copy" name="btnCopyUrl" class="btn btn-primary btn-sm">复制连接</button>
                                    </div>
                                </div-->

                                <div class="form-group">
                                    <label for="phone" class="col-sm-2 control-label">App推广链接</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control copy-input" name="appUrl" id="appPopularizeUrl" value="" maxlength="200" placeholder="请输入App推广链接"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <button type="button" id="btn-generate-appUrl" name="generate" class="btn btn-primary btn-sm">生成连接</button>
                                        <button type="button" id="btn-copy-appUrl" name="btnCopyAppUrl" class="btn btn-primary btn-sm">复制连接</button>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="remark" class="col-sm-2 control-label">备注</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="remark" id="remark" maxlength="100" placeholder="请输入备注"/>
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
</div>

<!-- jQuery 2.2.3 -->
<script type="text/javascript" src="${ctx}/assets/scripts/jquery-2.2.3.min.js"></script>

<script type="text/javascript" src="${ctx}/assets/plugins/jquery.zclip/jquery.zclip.js"></script>
<!-- JSON2 -->
<script src="${ctx}/static/common/json/json2.js"></script>
<!-- Bootstrap 3.3.7 -->
<script type="text/javascript" src="${ctx}/assets/plugins/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/plugins/bootbox/bootbox.js"></script>
<script type="text/javascript" src="${ctx}/assets/plugins/bootstrap-toastr/toastr.min.js"></script>
<!-- FastClick -->
<script src="${ctx}/assets/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<%--<script src="${ctx}/static/adminlte/dist/js/app.min.js"></script>--%>
<!-- dataTable -->
<script type="text/javascript" src="${ctx}/assets/plugins/jquery.blockui.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/plugins/datatables.net/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/plugins/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<!-- 自己实现的 datatables -->
<script src="${ctx}/static/common/js/dataTables.js"></script>
<!-- form 验证，暂时去掉，需要的时候在放开 -->
<%--<script src="${ctx}/static/adminlte/plugins/bootstrap-validator/dist/js/bootstrap-validator.js"></script>--%>


<script type="text/javascript" src="${ctx}/static/js/common/jquery/jquery.md5.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/jquery/center-loader.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/jquery/jquery-ui-1.10.3.full.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/jquery/jquery.tips.js"></script>
<script type="text/javascript" src="${ctx}/assets/scripts/jquery.form.min.js"></script>


<script src="${ctx}/static/plugins/iCheck/icheck.min.js"></script>
<!-- daterangepicker -->
<!-- bootstrap-datepicker 的内容等同于datepicker -->
<script src="${ctx}/assets/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="${ctx}/assets/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>

<!-- treeview -->
<script src="${ctx}/assets/plugins/bootstrap-treeview/bootstrap-treeview.min.js"></script>
<!-- AdminLTE for demo purposes -->
<%--<script src="${ctx}/static/js/demo.js"></script>--%>
<!--select2-->
<script src="${ctx}/assets/plugins/select2/select2.full.min.js"></script>

<script type="text/javascript" src="${ctx}/static/common/js/base.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/base-modal.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/base-form.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/base-datasource.js"></script>
<script type="text/javascript" src="${ctx}/static/common/js/base-org.js"></script>

<!-- 弹框需要的js -->
<script type="text/javascript" src="${ctx}/assets/scripts/common.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/main.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/dialog.js"></script>

<!-- datetimepicker 日期控件 -->
<link rel="stylesheet" href="${ctx}/static/css/common/bootstrap/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="${ctx}/static/js/common/bootstrap/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/bootstrap/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
    var ctx = '${ctx}';
    $(function () {
        /**
        $("#btn-generate").unbind("click").bind("click", function (event) {
            var code = $("#code").val();
            if(code == "" || code == null){
                alert("请输入渠道号");
                $("#popularizeUrl").val('');
                return;
            }

            var serverPath = $("#serverPath").val();
            if(serverPath == "" || serverPath == null){
                alert("获取服务器地址失败");
                return;
            }

            var url = serverPath + "/" + "?channelNo=" + code;
            $("#popularizeUrl").val(url);
        });

        $("#btn-copy").zclip({
            path: "${ctx}/assets/plugins/jquery.zclip/ZeroClipboard.swf",
            copy: function(){
                var popularizeUrl = $("#popularizeUrl").val();
                if(popularizeUrl == "" || code == popularizeUrl){
                    alert("还没生成全流程推广连接，请先生成全流程推广连接");
                    return;
                }
                return $("#popularizeUrl").val();
            },
            afterCopy:function(){
                alert("复制成功");
            }
        });
    **/


        $("#btn-generate-appUrl").unbind("click").bind("click", function (event) {
            var code = $("#code").val();
            if(code == "" || code == null){
                alert("请输入渠道号");
                $("#appPopularizeUrl").val('');
                return;
            }

            var serverAppPath = $("#serverAppPath").val();
            if(serverAppPath == "" || serverAppPath == null){
                alert("获取服务器地址失败");
                return;
            }

            var url = serverAppPath + "/" + "?channelNo=" + code;
            $("#appPopularizeUrl").val(url);
        });

        $("#btn-copy-appUrl").zclip({
            path: "${ctx}/assets/plugins/jquery.zclip/ZeroClipboard.swf",
            copy: function(){
                var popularizeUrl = $("#appPopularizeUrl").val();
                if(popularizeUrl == "" || code == popularizeUrl){
                    alert("还没生成App推广连接，请先生成App推广连接");
                    return;
                }
                return $("#appPopularizeUrl").val();
            },
            afterCopy:function(){/* 复制成功后的操作 */
                alert("复制成功");
            }
        });

        $("#btn-cancel").unbind("click").bind("click", function (event) {
            window.location.href = ctx + "/channel/list";
        });

        $("#btn-submit").unbind("click").bind("click", function (event) {
            generateUrl();
            ZW.Ajax.doRequest("addForm", ctx + "/channel/update", "", function (data) {
                // 失败
                if (data.res == 0) {
                    ZW.Model.error(data.message);
                }
                // 成功
                if (data.res == 1) {
                    window.location.href = ctx + "/channel/list";
                }
            })
        });

        //TC日期共同格式化 yyyy-MM-dd
        $(".datepicker_ymd").datetimepicker({
            todayHighlight: true,
            minView: 1,
            format: "yyyy-mm-dd hh:00:00",
            language: 'zh-CN',
            autoclose: true,
        });
    });

    function generateUrl() {
        var code = $("#code").val();

        //临时关闭全流程推广
        // var serverPath = $("#serverPath").val();
        // var url = serverPath + "/" + "?channelNo=" + code;
        // $("#popularizeUrl").val(url);

        var serverAppPath = $("#serverAppPath").val();
        var appUrl = serverAppPath + "/" + "?channelNo=" + code;
        $("#appPopularizeUrl").val(appUrl);

        var scale = $("#scale").val();
        var groupNum = $("#groupNum").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        if (scale == null || scale == "") {
            $("#scale").val(0);
        }
        if (groupNum == null || groupNum == "") {
            $("#groupNum").val(0);
        }

        if (startTime == null || startTime == "") {
            startTime = new Date().Format("yyyy-MM-dd 00:00:00");
            $("#startTime").val(startTime);
        }
        if (endTime == null || endTime == "") {
            endTime = new Date().Format("yyyy-MM-dd 00:00:00");
            $("#endTime").val(endTime);
        }
    }
</script>

</body>
</html>