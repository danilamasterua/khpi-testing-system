<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/img/DSTS_logo.png" alt="DSTSLogo" class="logo_header">
    <div style="display: flex; justify-content: space-between; margin-left: auto; align-items: center">
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-link">To home</a>
    </div>
</header>
<main>
    <h2>Error 403</h2>
    <p>Access denied</p>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
