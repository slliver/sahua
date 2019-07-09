<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
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
    <!-- datatables -->
    <link rel="stylesheet" href="${ctx}/assets/plugins/datatables.net-bs/css/dataTables.bootstrap.min.css">
    <%--<link rel="stylesheet" href="/assets/plugins/datatables.net-bs/select.bootstrap.min.css">--%>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${ctx}/assets/plugins/font-awesome/css/font-awesome.min.css">
    <!-- 自己实现的css -->
    <link rel="stylesheet" href="${ctx}/static/common/css/base.css">
    <!-- data table -->
    <link rel="stylesheet" href="${ctx}/assets/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="${ctx}/assets/plugins/datatables/select.bootstrap.min.css">

    <!-- Ionicons -->
    <link rel="stylesheet" href="${ctx}/assets/plugins//Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${ctx}/static/css/AdminLTE.min.css">

    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-toastr/toastr.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-select/css/bootstrap-select.css">

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

    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-multiselect-0.9.13/docs/css/prettify.css"
          type="text/css">
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-multiselect-0.9.13/dist/css/bootstrap-multiselect.css"
          type="text/css">
    <link rel="stylesheet" href="${ctx}/assets/plugins/bootstrap-tagsinput/dist/css/bootstrap-tagsinput.css">
</head>
<body class="hold-transition skin-blue sidebar-mini fixed">

