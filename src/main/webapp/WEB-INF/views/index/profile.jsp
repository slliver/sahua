<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">设置</c:param>
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
                个人设置
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
                <li class="active">个人设置</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <!-- 分割 -->
                <div class="col-md-12">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#settings" data-toggle="tab">修改密码</a></li>
                        </ul>
                        <div class="tab-content">
                            <!-- /.tab-pane -->
                            <div class="active tab-pane" id="timeline">
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label for="password" class="col-sm-2 control-label">当前密码</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" name="password" id="password" placeholder="当前密码"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="newPassword" class="col-sm-2 control-label">新密码</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="新密码"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="confirmPassword" class="col-sm-2 control-label">确认新密码</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="确认新密码"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="button" class="btn btn-danger" id="btn-update-password">修改</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- 结束 -->
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <!-- Main Footer -->
    <c:import url="/WEB-INF/views/include/footer.jsp"/>
    <div class="control-sidebar-bg"></div>
</div>
<script type="text/javascript" src="${ctx}/static/common/js/base-modal.js"></script>
</body>
<script type="text/javascript">
    var ctx = '${ctx}';
    $("#btn-update-password").click(function () {
        var password = $("#password").val();
        var newPassword = $("#newPassword").val();
        var confirmPassword = $("#confirmPassword").val();

        $.post(ctx + "/index/password", {
            "password": password,
            "newPassword": newPassword,
            "confirmPassword": confirmPassword
        }, function (data) {
            var result = data.res;
            if (result != 1) {
                alert(data.message);
            } else {
                alert(data.message);
                window.location.href = ctx + "/logout";
            }
        }, "json");
    });
</script>
</html>