<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Головна - DS Testing System</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login-form.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<%--    <jsp:useBean id="model" class="ds.testingsystem.model.Model"></jsp:useBean>--%>
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <h2>Доступні тести</h2>
    <a href="getPassedTests" class="btn btn-link">Виконані тести</a>
    <div style="display: flex">
        <div class="m-2">
            <form action="searchTest">
                <label for="search" class="form-label">Пошук за назвою:</label>
                <input type="text" id="search" placeholder="Пошук" name="search" class="form-control">
                <button type="submit" class="btn btn-primary"><i class="bi bi-search"></i></button>
            </form>
            <form action="filtrateTest">
                <label for="teacher" class="form-label">Фільтрувати за викладачем:</label>
                <select class="form-select" id="teacher" name="teacherId">
                    <c:forEach var="t" items="${teachers}">
                        <option value="${t.key}">${t.value.firstName} ${t.value.lastName}</option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn btn-primary">Фільтрувати</button>
            </form>
<%--            <form action="sortTests">--%>
<%--                <label for="sort" class="form-label">Сортувати по назві тесту:</label>--%>
<%--                <select class="form-select" id="sort" name="sortType">--%>
<%--                    <option value="true">За зростанням (А-Я)</option>--%>
<%--                    <option value="false">За спаданням (Я-А)</option>--%>
<%--                </select>--%>
<%--                <button type="submit" class="btn btn-primary">Сортувати</button>--%>
<%--            </form>--%>
        </div>
        <div class="scroll-list m-2">
            <c:forEach var="t" items="${model.tests}">
                <div class="test-card">
                    <h3>${t.value.name}</h3>
                    <p>${t.value.description}</p>
                    <form action="loadTest" method="get">
                        <input type="hidden" value="${t.key}" name="testId">
                        <input type="submit" value="Завантажити тест" class="btn btn-primary">
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
