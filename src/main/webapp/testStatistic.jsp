<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<jsp:useBean id="gI" type="java.util.HashMap<ds.testingsystem.database.model.Group, java.util.LinkedList<ds.testingsystem.database.model.beans.UserTestPoints>>"></jsp:useBean>--%>
<html>
<head>
    <title>Статистика по тесту</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <c:forEach var="g" items="${gI.entrySet()}">
        <h4>${g.key.name}</h4>
        <table class="table table-stripped-columns">
            <tr>
                <th>Студент</th>
                <th>Оцінка</th>
            </tr>
            <c:forEach var="u" items="${g.value}">
                <tr>
                    <td>${u.user.firstName} ${u.user.lastName}</td>
                    <td>${u.points}</td>
                </tr>
            </c:forEach>
        </table>
    </c:forEach>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
