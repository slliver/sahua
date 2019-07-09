<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- CSRF攻击防护 -->
    <sec:csrfMetaTags/>
    <title>
        ${not empty param.title ? param.title : ''}
    </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${ctx}/assets/plugins//font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${ctx}/assets/plugins//Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${ctx}/static/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${ctx}/static/css/skins/_all-skins.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${ctx}/static/plugins/iCheck/square/blue.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition login-page">

<!-- 内容区域 -->
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>
    var ctx = '${ctx}';
</script>
<div class="login-box">
    <div class="login-logo">
        <a href="javascript:void(0);"><b>SDB_Admin</b>LTE</a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">
            &nbsp;
        </p>
        <form action="#" method="post">
            <div class="form-group has-feedback">
                <input type="text" class="form-control" name="userName" id="userName" placeholder="请输入用户名" value=""/>
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码" value=""/>
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <!--
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input type="checkbox" name="rememberMe" id="rememberMe"/> Remember Me
                        </label>
                    </div>
                </div>
                -->
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="button" class="btn btn-primary btn-block btn-flat" id="btn-login">登录</button>
                </div>
                <!-- /.col -->
            </div>
        </form>
        <!-- /.social-auth-links -->
    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
<!-- jQuery 3 -->
<%--<script src="${ctx}/assets/plugins/jquery/dist/jquery.min.js"></script>--%>
<script type="text/javascript" src="${ctx}/assets/plugins/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${ctx}/assets/plugins/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${ctx}/static/plugins/iCheck/icheck.min.js"></script>

<script src="${ctx}/static/js/common/jquery/jquery.md5.js"></script>
<script>
    var btnLogin = "btn-login";
    $(function () {

        $(document).keydown(function (event) {
            event = event ? event : window.event;
            if (event.keyCode === 13) {
                $("#" + btnLogin).trigger("click");
            }
        });

        $("#btn-login").click(function () {
            var userName = $("#userName").val();
            var userPassword = $("#password").val();
            if (userName == "") {
                alert("用户名不能为空!");
                $("#userName").focus();
                return;
            }
            if (userPassword == "") {
                alert("密码不能为空!");
                $("#password").focus();
                return;
            }

            $.post(ctx + "/login", {
                "userName": userName,
                "password": $.md5(userPassword)
            }, function (data) {
                var result = data.res;
                if (result != 1) {
                    alert(data.message);
                    clearLoginForm();//清除信息
                } else {
                    window.location.href = ctx + "/index/index";
                }
            }, "json");
        });
    });

    function clearLoginForm() {
        $("#password").val("");
    }
</script>
</body>
</html>
