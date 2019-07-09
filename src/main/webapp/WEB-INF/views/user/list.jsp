<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:import url="/WEB-INF/views/include/header.jsp">
    <c:param name="title"></c:param>
</c:import>
<div class="wrapper">
    <!-- Main Header -->
    <c:import url="/WEB-INF/views/include/header_navbar.jsp"/>
    <!-- Left side column. contains the logo and sidebar -->
    <c:import url="/WEB-INF/views/include/left_navbar.jsp"/>
    <!-- Content Wrapper. Contains page content 内容区域 -->
    <!-- Main content -->
    <div class="content-wrapper" id="mainDiv">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                用户管理
                <small>列表</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="${ctx}"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active">用户管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <form id="queryForm" method="POST" action="${ctx}/user/list">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <section class="content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box">
                            <div class="dataTables_filter" id="searchDiv" style="margin-top: 10px !important; position: relative !important; padding-right: 10px;">
                                <c:if test="${sessionScope.sys_sessionUser.role == 'ADMIN'}">
                                <input placeholder="请输入姓名" name="name" class="form-control" type="text"
                                       likeOption="true" value="${condition.name}" maxlength="10"/>
                                <input placeholder="请输入手机号码" name="phone" class="form-control" type="text"
                                       likeOption="true" value="${condition.phone}" maxlength="10"/>
                                <input placeholder="请输入渠道号" name="channelNo" class="form-control" type="text"
                                       likeOption="true" value="${condition.channelNo}" maxlength="20"/>
                                </c:if>
                                <c:if test="${sessionScope.sys_sessionUser.role != 'ADMIN'}">
                                    <input placeholder="请输入渠道号" name="channelNo" class="form-control" type="hidden"
                                           likeOption="true" value="${sessionScope.sys_sessionUser.channelNo}" maxlength="20"/>
                                </c:if>
                                <c:if test="${sessionScope.sys_sessionUser.role == 'ADMIN'}">
                                <select name="device" id="device" class="form-control" style="min-width: 120px; width: auto;display:inline-block;">
                                    <c:forEach var="item" items="${deviceList}">
                                        <option value="${item.key}" <c:if test="${condition.device == item.key}">selected</c:if> >${item.value}</option>
                                    </c:forEach>
                                </select>
                                <input type="text" maxlength="40" name="startTime" id="startTime" value="${condition.startTime}" placeholder="注册开始日期" class="form-control datepicker_ymd"/>
                                <input type="text" maxlength="40" name="endTime" id="endTime" value="${condition.endTime}" placeholder="注册结束日期" class="form-control datepicker_ymd"/>
                                </c:if>
                                <c:if test="${sessionScope.sys_sessionUser.role == 'ADMIN'}">
                                <div class="btn-group">
                                    <button type="submit" class="btn btn-primary" data-btn-type="search">查询</button>
                                </div>
                                </c:if>
                            </div>
                            <br/>
                            <!-- 管理员操作权限按钮,暂时不开放 -->
                            <c:if test="${sessionScope.sys_sessionUser.role == 'ADMIN'}">
                                <!--
                                <div class="with-border form-inline box-header">
                                    <div class="input-group input-group-sm">
                                        <button type="button" class="btn btn-primary" id="channelAuthorization">渠道授权</button>
                                    </div>
                                    <div class="input-group input-group-sm">
                                        <button type="button" class="btn btn-primary" id="btnEdit">编辑</button>
                                    </div>
                                    <div class="input-group input-group-sm">
                                        <button type="button" class="btn btn-danger" id="btnDelete">删除</button>
                                    </div>
                                </div>
                                -->
                            </c:if>

                            <div class="box-body">
                                <table id="user_table" class="table table-border table-hover">
                                    <thead>
                                    <tr>
                                        <%--<th>操作</th>--%>
                                        <c:if test="${sessionScope.sys_sessionUser.role == 'ADMIN'}">
                                        <th>用户名</th>
                                        </c:if>
                                        <th>姓名</th>
                                        <th>昵称</th>
                                        <th>性别</th>
                                        <c:if test="${sessionScope.sys_sessionUser.role == 'ADMIN'}">
                                        <th>手机号码</th>
                                        <th>渠道号</th>
                                        <th>注册设备</th>
                                        <th>注册时间</th>
                                        </c:if>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:choose>
                                        <c:when test="${not empty dataList && fn:length(dataList) >0}">
                                            <c:forEach items="${dataList}" var="item" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${status.index%2 == 0}"><tr class="odd"></c:when>
                                                    <c:otherwise><tr class="even"></c:otherwise>
                                                </c:choose>
                                                <!--td>
                                                    <input type="checkbox" name="userPkid" value="${item.pkid}" data-pkid="${item.pkid}" data-flagversion="${item.flagVersion}" id="userPkidCheckbox_${status.index}"/>
                                                </td-->
                                                <c:if test="${sessionScope.sys_sessionUser.role == 'ADMIN'}">
                                                <td>${item.userName}</td>
                                                </c:if>
                                                <td>${item.name}</td>
                                                <td>${item.nickName}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.sex == '1'}">男</c:when>
                                                        <c:otherwise>女</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <c:if test="${sessionScope.sys_sessionUser.role == 'ADMIN'}">
                                                <td>${item.phone}</td>
                                                <td>${item.channelNo}</td>
                                                <td>${robinFn:getDeviceValue(item.device)}</td>
                                                <td><fmt:formatDate value='${item.makeTime}' pattern='yyyy-MM-dd HH:mm' /></td>
                                                </c:if>
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
                            <!-- /.box-body -->
                        </div>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </section>
        </form>
    </div>


    <!-- footer
    <footer class="main-footer text-center">
        <strong>Copyright &copy; 2018 <a href="http://www.slliver.com">slliver</a>.</strong> All rights reserved. <b>Email</b>：slliver@163.com
        <b>QQ</b>：35369468
        &nbsp;&nbsp;&nbsp;
        <a href="http://www.miitbeian.gov.cn/" target="_blank">京ICP备16062522号</a>
    </footer>
-->
    <c:import url="/WEB-INF/views/include/footer.jsp"/>
    <!-- Add the sidebar's background. This div must be placed immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>


<script type="text/javascript">
    var ctx = "${ctx}";//给外部js文件传递路径参数
    var _toPage = function (pageNum) {
        var _f = $('#queryForm')[0] ? $('#queryForm')[0] : $('form')[0];
        if (_f == undefined) alert('页面不存在form！');
        else $(_f).append('<input type="hidden" id="pageNum" name="pageNum" value="' + pageNum + '" />').submit();
    }
</script>
<!-- jQuery 2.2.3 -->
<script type="text/javascript" src="${ctx}/assets/scripts/jquery-2.2.3.min.js"></script>
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
    $(function () {
        //TC日期共同格式化 yyyy-MM-dd
        $(".datepicker_ymd").datetimepicker({
            todayHighlight: true,
            minView: "month",
            format: "yyyy-mm-dd",
            language: 'zh-CN',
            autoclose: true,
        });
    });
</script>

<script type="text/javascript">
    $(function (){
        /**
         * 用户渠道授权
         */
        $("#btnEdit").unbind("click").bind("click", function (event) {
            var userPkids = [];
            $('input[name="userPkid"]:checked').each(function () {
                userPkids.push($(this).val());
            });

            var ulength = userPkids.length;
            if (ulength == 0) {
                ZW.Model.error("请选择用户");
                return;
            }
            if(ulength > 1){
                ZW.Model.error("只能选择一个用户");
                return;
            }
            var userPkid = userPkids[0];
            window.location.href = ctx + '/user/' + userPkid + '/edit';
        });


        /**
         * 用户渠道授权
         */
        $("#channelAuthorization").unbind("click").bind("click", function (event) {
            var userPkids = [];
            $('input[name="userPkid"]:checked').each(function () {
                userPkids.push($(this).val());
            });

            var ulength = userPkids.length;
            if (ulength == 0) {
                ZW.Model.error("请选择用户");
                return;
            }
            if(ulength > 1){
                ZW.Model.error("只能选择一个用户");
                return;
            }
            var userPkid = userPkids[0];
            Dialog.channelDialog(userPkid, function (event) {
                var pkids = [];
                $('input[name="channelPkid"]:checked').each(function () {
                    pkids.push($(this).val());
                });
                if (pkids.length == 0) {
                    ZW.Model.error("请选择用户负责的渠道");
                    return;
                }

                var array = new Array();
                $('input[name="channelPkid"]:checked').each(function () {
                    var $this = $(this);
                    var userPkid = $this.data("userpkid");
                    var channelPkid = $this.data("pkid");
                    var channelCode = $this.data("code");
                    var obj = new Object();
                    obj.userPkid = userPkid;
                    obj.channelPkid = channelPkid;
                    obj.channelCode = channelCode;
                    array.push(obj);
                });

                $.ajax({
                    type: "POST",
                    url: ctx+"/channel/authorization",
                    contentType: "application/json;charset=utf-8",
                    data:JSON.stringify(array),
                    dataType: "json",
                    success:function (data) {
                        if (data.res == "1") {
                            ZW.Model.info(data.message);
                            $("#channelDialog").modal('hide');
                        }else{
                            ZW.Model.info(data.message);
                        }
                    },
                    error:function (message) {
                        ZW.Model.error(data.message);
                    }
                });
                return true;
            });
        });
    });
</script>
</body>
</html>