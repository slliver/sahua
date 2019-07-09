<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">APP首页消息维护</c:param>
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
                APP首页提示语维护
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
                        <form id="updateForm" method="post" action="${ctx}/message/update" class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="pkid" value="${message.pkid}"/>
                            <input type="hidden" name="flagVersion" value="${message.flagVersion}"/>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="message" class="col-sm-2 control-label">首页提示语</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="message" id="message" placeholder="请输入自定义文字"
                                               value="${message.message}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="button" class="btn btn-info pull-right" id="btn-submit">修改</button>
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
        $("#btn-submit").unbind("click").bind("click", function (event) {
            ZW.Ajax.doRequest("updateForm", ctx + "/messagetext/update", "", function (data) {
                // 失败
                if (data.res == 0) {
                    ZW.Model.error(data.message);
                }
                // 成功
                if (data.res == 1) {
                    ZW.Model.error(data.message);
                }
            });
        });
    });
</script>
</html>