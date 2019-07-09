<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>
        ${not empty param.title ? param.title : ''}
    </title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/plugins/datatables/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/assets/plugins/font-awesome/css/font-awesome.min.css"/>
    <!-- Theme style -->
    <link rel="stylesheet" href="${ctx}/assets/styles/AdminLTE.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-toastr/toastr.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-select/css/bootstrap-select.css">
    ${param.styles}
    <link rel="stylesheet" href="${ctx}/assets/styles/sys_style.css"/>

    <link rel="stylesheet" href="${ctx}/static/css/sys/style.css">
    <link rel="stylesheet" href="${ctx}/static/css/common/ace/font-awesome.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/common/bootstrap/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/common/notification/style_notification.css">
    <!--[if lt IE 9]>
    <script src="${ctx}/assets/scripts/html5shiv.min.js"></script>
    <script src="${ctx}/assets/scripts/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-multiselect-0.9.13/docs/css/prettify.css"
          type="text/css">
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-multiselect-0.9.13/dist/css/bootstrap-multiselect.css"
          type="text/css">
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-tagsinput/dist/css/bootstrap-tagsinput.css">

    <script type="text/javascript" src="${ctx}/static/js/common/jquery/jquery-1.10.2.min.js"></script>
</head>
<body class="hold-transition skin-custome sidebar-mini" style="overflow-x:auto; ">

    <header class="main-header">
        <a href="${ctx}/" class="logo">
            <%--<span class="logo-lg">${param.logo}</span>--%>
            <span class="logo-mini"><small><b>忠旺</b></small></span> <span class="logo-lg">忠旺检测中心管理系统</span>
        </a>
        <nav class="navbar navbar-static-top">
        </nav>
    </header>
</body>