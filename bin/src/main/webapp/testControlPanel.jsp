<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:useBean id="tests" class="java.util.HashMap"></jsp:useBean>--%>
<html>
<head>
    <title>Панель керування тестами</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <h2>Панель керування тестами</h2>
    <form action="create-test">
        <input type="submit" value="Створити тест" class="btn btn-primary">
    </form>
    <div class="scroll-list">
        <c:forEach items="${tests}" var="t">
            <div class="test-card">
                <h3>${t.value.name}</h3>
                <p>${t.value.description}</p>
                <form action="loadTest" method="post">
                    <input type="hidden" value="${t.key}" name="testId">
                    <input type="submit" value="Завантажити тест" class="btn btn-primary">
                </form>
            </div>
        </c:forEach>
    </div>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
