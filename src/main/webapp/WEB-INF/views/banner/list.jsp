<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="robinFn" uri="http://www.robin.com/taglib/functions" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">banner</c:param>
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
                <small>Optional description</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                <li class="active">Here</li>
            </ol>
        </section>
        <!-- Main content -->

        <form id="queryForm" method="POST" action="${ctx}/banner/list">
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
                                    <button type="button" id="addLoan" name="addLoan" class="btn btn-block btn-primary btn-sm" onclick="window.location.href = '${ctx}/banner/add'">添加</button>
                                </h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th style="width: 20%;">操作</th>
                                        <th>Logo</th>
                                        <th>名称</th>
                                        <th>业务类型</th>
                                        <th>是否绑定业务</th>
                                        <th>跳转方式</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:choose>
                                        <c:when test="${not empty dataList && fn:length(dataList) >0}">
                                            <c:forEach items="${dataList}" var="item">
                                                <tr>
                                                    <td>
                                                        <button type="button" class="btn btn-primary btn-sm" onclick="window.location.href = '${ctx}/banner/${item.pkid}/edit'">编辑</button>
                                                        <button type="button" class="btn btn-primary btn-sm btn-delete" data-pkid="${item.pkid}" data-flagVesion="${item.flagVersion}">删除</button>
                                                        <c:if test="${item.bussinessPkid != null && item.bussinessPkid !=''}">
                                                            <button type="button" class="btn btn-primary btn-sm btn-cancal-bind" data-pkid="${item.pkid}" data-flagVesion="${item.flagVersion}">解除绑定</button>
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <c:if test="${item.logoPkid != null && item.logoPkid != ''}">
                                                            <img src="${ctx}/resource/showImage?pkid=${item.logoPkid}" style='height:32px;width: 120px;'/>
                                                        </c:if>
                                                        <c:if test="${item.logoPkid == null || item.logoPkid == ''}">
                                                            未上传logo
                                                        </c:if>
                                                    </td>
                                                    <td>${item.name}</td>
                                                    <td>${robinFn:getBussinessValue(item.bussinessType)}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${item.bussinessPkid != null && item.bussinessPkid !=''}">
                                                                <font style="color: green;">已绑定</font>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <font style="color: red;">未绑定</font>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${robinFn:getForwardValue(item.forwardType)}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan='9' align='center'> 暂无数据~</td>
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
    <c:import url="/WEB-INF/views/include/footer.jsp"/>
    <div class="control-sidebar-bg"></div>
</div>
</body>
<script type="text/javascript">
    var ctx = '${ctx}';
    $(".btn-delete").each(function() {
        $(this).unbind("click").bind("click", function(){
            // 获取当前操作的pkid以及flagVersion
            var pkid = $(this).attr("data-pkid");
            var flagVersion = $(this).attr("data-flagVesion");
            var record = {};
            record.pkid = pkid;
            record.flagVersion = flagVersion;
            var array = [];
            array.push(record);
            if(window.confirm("确定删除当前数据么?")){
                window.location.href = ctx + '/banner/'+pkid+'/delete';
            }
        });
    });

    $(".btn-cancal-bind").each(function() {
        $(this).unbind("click").bind("click", function(){
            // 获取当前操作的pkid以及flagVersion
            var pkid = $(this).attr("data-pkid");
            var flagVersion = $(this).attr("data-flagVesion");
            var record = {};
            record.pkid = pkid;
            record.flagVersion = flagVersion;
            var array = [];
            array.push(record);
            if(window.confirm("确定解除绑定么，解除绑定后对应数据不再引用当前banner")){
                window.location.href = ctx + '/banner/'+pkid+'/cancalBind';
            }
        });
    });
</script>
</html>