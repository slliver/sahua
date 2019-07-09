<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">事件管理-编辑</c:param>
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
                        </div>
                        <form id="addForm" method="POST" onsubmit="return false;" action="${ctx}/event/update" class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="pkid" value="${event.pkid}"/>
                            <input type="hidden" name="flagVersion" value="${event.flagVersion}"/>
                            <input type="hidden" name="serverPath" id="serverPath" value="${serverPath}"/>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="action" class="col-sm-2 control-label">事件编码</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="action" id="action" maxlength="20" placeholder="请输入事件编码" value="${event.action}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="actionName" class="col-sm-2 control-label">事件名称</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="actionName" id="actionName" maxlength="20" placeholder="请输入事件名称" value="${event.actionName}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="minPower" class="col-sm-2 control-label">最小金币数</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="minPower" id="minPower" maxlength="20" placeholder="请输入最小金币数" value="${event.minPower}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="maxPower" class="col-sm-2 control-label">最大金币数</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="maxPower" id="maxPower" maxlength="20" placeholder="请输入最大金币数" value="${event.maxPower}"/>
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
    <script type="text/javascript" src="${ctx}/assets/plugins/jquery.zclip/jquery.zclip.js"></script>
</div>
</body>
<script type="text/javascript">
    var ctx = '${ctx}';
    $(function () {
        $("#btn-cancel").unbind("click").bind("click", function (event) {
            window.location.href = ctx + "/event/list";
        });

        $("#btn-submit").unbind("click").bind("click", function (event) {
            ZW.Ajax.doRequest("addForm", ctx + "/event/update", "", function (data) {
                // 失败
                if (data.res == 0) {
                    ZW.Model.error(data.message);
                }
                // 成功
                if (data.res == 1) {
                    window.location.href = ctx + "/event/list";
                }
            })
        });
    });
</script>
</html>