<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/taglibs.jsp"%>
<html>
<head>
  <title>Erro</title>
</head>
<body>
<h1>Voltar à <a href="${pageContext.request.contextPath}/index.jsp">home</a></h1>
  <c:if test="${not empty sessionScope.mensagemErro}">
    <p style="color: red; font-weight: bold">${sessionScope.mensagemErro}</p>
  </c:if>
</body>
</html>
