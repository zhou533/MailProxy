<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>邮件 - 搜索</title>
</head>
<body>

<div class="main_content" id="main_content">
    <div id="result_count">找到<span>${count}</span>条记录</div>
    <div id="result_list">
        <c:if test="${!empty results}">
            <c:forEach items="${results}" var="r">
                <div id="title" style="font-weight:bold;color:#999;">${r.name} - ${r.email}</div>
                <div id="journal">
                    <c:if test="${!empty r.isan}">
                        <span id="isan">${r.isan}</span>
                    </c:if>
                    <c:if test="${!empty r.journal}">
                        <span id="journal">${r.journal}</span>
                    </c:if>
                </div>
                <c:if test="${!empty r.subject}">
                    <div id="subject" style="color:#123;">${r.subject}</div>
                </c:if>

                <br>
            </c:forEach>
        </c:if>
    </div>
</div>

</body>
</html>