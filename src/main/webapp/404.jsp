<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/include/header.jsp">
  <c:param name="title">错误页</c:param>
</c:import>
<section class="content">
<style>

</style>
	<div class="col-top-60 clearfix">
		<div class="errorsindex">
			<div class="errorscon">
				<p>
					我们到处找，但找不到！最可能的原因是 ==>> 404:
				</p>
			</div>
		</div>
	</div>
</section>
<c:import url="/WEB-INF/views/include/footer.jsp" />