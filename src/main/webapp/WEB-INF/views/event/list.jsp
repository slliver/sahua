<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">渠道管理</c:param>
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
                &nbsp;
                <small>事件管理</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">Here</li>
            </ol>
        </section>
        <!-- Main content -->

        <form id="queryForm" method="POST" action="${ctx}/event/list">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <section class="content container-fluid">
                <!--------------------------
                  | Your Page Content Here |
                  -------------------------->
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h3 class="box-title">
                                    <button type="button" id="addLoan" name="addLoan" class="btn btn-block btn-primary btn-sm" onclick="window.location.href = '${ctx}/event/add'">添加</button>
                                </h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>操作</th>
                                        <th>事件编码</th>
                                        <th>事件名称</th>
                                        <th>最小金币</th>
                                        <th>最大金币</th>
                                        <th>创建时间</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:choose>
                                        <c:when test="${not empty dataList && fn:length(dataList) >0}">
                                            <c:forEach items="${dataList}" var="item">
                                                <tr>
                                                    <td>
                                                        <button type="button" class="btn btn-primary btn-sm" onclick="window.location.href = '${ctx}/event/${item.pkid}/edit'">编辑</button>
                                                        <button type="button" class="btn btn-danger btn-sm btn-delete" data-pkid="${item.pkid}" data-flagVesion="${item.flagVersion}">删除</button>
                                                    </td>
                                                    <td>${item.action}</td>
                                                    <td>${item.actionName}</td>
                                                    <td>${item.minPower}</td>
                                                    <td>${item.maxPower}</td>
                                                    <td><fmt:formatDate value='${item.makeTime}' pattern='yyyy-MM-dd HH:mm'/></td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan='7' align='center'> 暂无数据~</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                            <div class="box-footer clearfix text-right">
                                <c:if test="${not empty dataList && fn:length(dataList) >0}">
                                    ${pagnation}
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </form>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            Anything you want
        </div>
        <strong>Copyright &copy; 2016 <a href="#">Company</a>.</strong> All rights reserved.
    </footer>
    -->
    <c:import url="/WEB-INF/views/include/footer.jsp"/>
    <div class="control-sidebar-bg"></div>
</div>
<script type="text/javascript" src="${ctx}/static/common/js/base-modal.js"></script>
</body>
<!-- 弹框需要的js -->
<script type="text/javascript" src="${ctx}/assets/scripts/common.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/main.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/dialog.js"></script>

<script type="text/javascript">
    var ctx = '${ctx}';
    $(".btn-delete").each(function () {
        $(this).unbind("click").bind("click", function () {
            // 获取当前操作的pkid以及flagVersion
            var pkid = $(this).attr("data-pkid");
            var flagVersion = $(this).attr("data-flagVesion");
            var record = new Object();
            record.pkid = pkid;
            record.flagVersion = flagVersion;
            var array = new Array();
            // array.push(record);

            if(window.confirm("确定删除当前数据么?")){
                window.location.href = ctx + '/event/'+pkid+'/delete';
            }


            // $.ajax({
            //     type: "POST",
            //     url: ctx+"/event/delete",
            //     contentType: "application/json;charset=utf-8",
            //     data:JSON.stringify(record),
            //     dataType: "json",
            //     success:function (data) {
            //         if (data.res == "1") {
            //             ZW.Model.info(data.message);
            //             alert("删除成功");
            //             window.location.reload();
            //         }else{
            //             ZW.Model.info(data.message);
            //         }
            //     },
            //     error:function (message) {
            //         ZW.Model.error(data.message);
            //     }
            // });
        });
    });
</script>
</html>