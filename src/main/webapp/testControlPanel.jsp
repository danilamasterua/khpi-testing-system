<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:useBean id="tests" class="java.util.HashMap"></jsp:useBean>--%>
<html>
<head>
    <title>Панель керування тестами</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <h2>Панель керування тестами</h2>
    <form action="create-test">
        <button type="submit" class="btn btn-primary"><i class="bi bi-plus-circle"></i> Створити тест</button>
    </form>
    <div class="scroll-list">
        <c:forEach items="${tests}" var="t">
            <div class="test-card">
                <h3>${t.value.name}</h3>
                <p>${t.value.description}</p>
                <p class="btn-group">
                <form action="deleteTest" method="post" class="btn">
                    <input type="hidden" value="${t.key}" name="testId">
                    <button type="submit" class="btn btn-danger"><i class="bi bi-trash"></i> Видалити тест</button>
                </form>
                <form action="loadTestAccessPage" method="get" class="btn">
                    <input type="hidden" value="${t.key}" name="testId">
                    <button type="submit" class="btn btn-primary">Доступ до тесту</button>
                </form>
                <form action="updateServlet" method="get" class="btn">
                    <input type="hidden" value="${t.key}" name="testId">
                    <button type="submit" class="btn btn-primary">Редагувати тест</button>
                </form>
                </p>
            </div>
        </c:forEach>
    </div>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
