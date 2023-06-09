<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Заблоковані тести</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <h2>Заблоковані тести</h2>
    </p>
    <div class="scroll-list">
        <c:forEach items="${tests}" var="t">
            <div class="test-card">
                <h3>${t.value.name}</h3>
                <p>${t.value.description}</p>
                <p class="btn-group">
                <form action="unblockTest" method="post" class="btn">
                    <input type="hidden" value="${t.key}" name="testId">
                    <button type="submit" class="btn btn-warning"><i class="bi bi-unlock"></i> Розблокувати тест</button>
                </form>
                <form action="loadTestAccessPage" method="get" class="btn">
                    <input type="hidden" value="${t.key}" name="testId">
                    <button type="submit" class="btn btn-primary">Доступ до тесту</button>
                </form>
                <form action="updateServlet" method="get" class="btn">
                    <input type="hidden" value="${t.key}" name="testId">
                    <button type="submit" class="btn btn-primary">Редагувати тест</button>
                </form>
                <form action="getResults" method="get" class="btn">
                    <input type="hidden" value="${t.key}" name="testId">
                    <button type="submit" class="btn btn-primary">Оцінки за тест</button>
                </form>
                </p>
            </div>
        </c:forEach>
    </div>
</main>
</body>
</html>
