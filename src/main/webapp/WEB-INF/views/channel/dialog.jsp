<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
</head>
<body>
<div class="modal-body">
    <div class="row mar-top-15">
        <table id="baseTable" class="table table-striped table-bordered table-condensed" style="position: static;">
            <thead>
            <tr>
                <th>&nbsp;</th>
                <th>渠道编码</th>
                <th>渠道名称</th>
                <th>负责人</th>
                <th>联系方式</th>
                <th>创建时间</th>
            </tr>
            </thead>
            <tbody id="body">
            <c:choose>
                <c:when test="${not empty channelList && fn:length(channelList) >0}">
                    <c:forEach items="${channelList}" var="item" varStatus="status">
                        <c:choose>
                            <c:when test="${status.index%2 == 0}"><tr class="odd"></c:when>
                            <c:otherwise><tr class="even"></c:otherwise>
                        </c:choose>
                        <td>
                            <input type="checkbox" name="channelPkid" value="${item.pkid}" data-pkid="${item.pkid}" data-code="${item.code}" data-userpkid="${userPkid}" id="checkbox_${status.index}" <c:if test="${item.checked == '1'}"> checked </c:if>/>
                        </td>
                        <td>${item.code}</td>
                        <td>${item.name}</td>
                        <td>${item.chargeUser}</td>
                        <td>${item.phone}</td>
                        <td><fmt:formatDate value='${item.makeTime}' pattern='yyyy-MM-dd HH:mm'/></td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan='6' align='center'> 暂无数据~</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
