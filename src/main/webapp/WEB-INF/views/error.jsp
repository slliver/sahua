<%@page import="java.net.URLDecoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/include/header.jsp">
  <c:param name="title">错误页</c:param>
</c:import>
<section class="content">
<style>
.errorsindex {
	background: url(${ctx}/static/images/error.png) no-repeat center;
	width: 955px;
	height: 326px;
	margin: 0 auto;
}
.errorscon {
	font-size: 18px;
	left: 360px;
	position: relative;
	top: 208px;
	width: 548px;
	font-weight: bold;
	color: #3c8dbc;
}
.errorsindex a {
	padding: 8px 10px;
	color: #fff;
	font-size: 18px;
	font-weight: bold;
	background: #13a89e;
	display: block;
	width: 120px;
	text-align: center;
	border-radius: 8px;
}
</style>
	<div class="col-top-60 clearfix">
		<div class="errorsindex">
			<div class="errorscon" style="width:95%;height:300px;overflow-y:auto; overflow-x:hidden; ">
				<p>
					<%
					String e = request.getParameter("exception");
					if (e != null) {
						String output = URLDecoder.decode(e, "UTF-8"); 
						out.print("Exception: " + output);
					}
					%>
				</p>
			</div>
		</div>
	</div>
</section>
<c:import url="/WEB-INF/views/include/footer.jsp" />