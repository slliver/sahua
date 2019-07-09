<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title">信用卡</c:param>
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

        <form id="queryForm" method="POST" action="${ctx}/creditcard/list">
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
                                    <button type="button" id="addLoan" name="addLoan" class="btn btn-block btn-primary btn-sm" onclick="window.location.href = '${ctx}/creditcard/add'">添加</button>
                                </h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>操作</th>
                                        <th>Logo</th>
                                        <th>是否绑定banner</th>
                                        <th>银行名称</th>
                                        <th>信用卡名称</th>
                                        <th>标签</th>
                                        <th>适用设备</th>
                                        <th>优先级</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:choose>
                                        <c:when test="${not empty dataList && fn:length(dataList) >0}">
                                            <c:forEach items="${dataList}" var="item">
                                                <tr>
                                                    <td>
                                                        <button type="button" class="btn btn-primary btn-sm" onclick="window.location.href = '${ctx}/creditcard/${item.pkid}/edit'">编辑</button>
                                                        <button type="button" class="btn btn-primary btn-sm btn-delete" data-pkid="${item.pkid}" data-flagVesion="${item.flagVersion}">删除</button>
                                                    </td>
                                                    <td>
                                                        <c:if test="${item.logoPkid != null && item.logoPkid != ''}">
                                                            <img src="${ctx}/resource/showImage?pkid=${item.logoPkid}" style='height:32px;width: 32px;'/>
                                                        </c:if>
                                                        <c:if test="${item.logoPkid == null || item.logoPkid == ''}">
                                                            未上传logo
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${item.bannerPkid != null && item.bannerPkid !=''}">
                                                                <font style="color: green;"><a href="javascript:openWin('${item.bannerPkid}');">已绑定</a></font>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <font style="color: red;">未绑定</font>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${item.bankName}</td>
                                                    <td>${item.cardName}</td>
                                                    <td>${item.label}</td>
                                                    <td>${robinFn:getDeviceValue(item.device)}</td>
                                                    <td>${item.priority}</td>
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
<script type="text/javascript" src="${ctx}/static/common/js/base-modal.js"></script>
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
                window.location.href = ctx + '/creditcard/'+pkid+'/delete';
            }
        });
    });

    function openWin(bannerPkid){
        modals.openWin({
            winId:'userWin',
            title:'banner',
            width:'900px',
            url: ctx + "/resource/showBannerImage?bannerPkid=" + bannerPkid
        });
    }
</script>
</html>