<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="f" uri="https://danmmas.pp.ua/functions" %>
<%--<jsp:useBean id="gI" type="java.util.HashMap<ds.testingsystem.database.model.Group, java.util.LinkedList<ds.testingsystem.database.model.beans.UserTestPoints>>"></jsp:useBean>--%>
<html>
<head>
    <title>Статистика по тесту</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="bootstrap-5.3.0-dist/js/bootstrap.bundle.js"></script>
    <script src="js/accessTable.js"></script>
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <div class="container">
        <div class="nav nav-pills mb-3" id="pills-tab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="pills-student-results-tab" data-bs-toggle="pill" data-bs-target="#studentResults" role="tab" aria-controls="pills-student-results" aria-selected="true">Результати студентів</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="pills-statistic-tab" data-bs-toggle="pill" data-bs-target="#detailedStatistic" role="tab" aria-controls="pills-statistic" aria-selected="false">Статистика за запитаннями</button>
            </li>
        </div>
        <div class="tab-content" id="content">
            <div id="studentResults" class="tab-pane fade show active" role="tabpanel" aria-labelledby="pills-student-results-tab" tabindex="0">
                <form action="getResults/xlsFile">
                    <input type="hidden" name="testId" value="${testId}">
                    <button type="submit" class="btn btn-primary"><i class="bi bi-file-spreadsheet"></i> Завантажити</button>
                </form>
                <c:forEach var="g" items="${gI.entrySet()}">
                    <h4>${g.key.name}</h4>
                    <table class="table table-stripped-columns">
                        <tr>
                            <th class="col-lg-4">Студент</th>
                            <th class="col-lg-2">Оцінка</th>
                            <th class="col-lg-2">Дата оцінювання</th>
                            <th></th>
                        </tr>
                        <c:forEach var="u" items="${g.value}">
                            <tr>
                                <td>${u.user.firstName} ${u.user.lastName}</td>
                                <td>${u.points}</td>
                                <td>${f:formatLocalDateTime(u.dateTime, "dd.MM.yyyy HH:mm")}</td>
                                <td>
                                    <form action="deleteUserPoints" method="post">
                                        <input type="hidden" value="${testId}" name="testId">
                                        <input type="hidden" value="${u.user.userId}" name="userId">
                                        <button type="submit" class="btn btn-warning">Видалити результати користувача</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:forEach>
            </div>
            <div id="detailedStatistic" class="tab-pane fade" role="tabpanel" aria-labelledby="pills-statistic-tab" tabindex="0">
                <table class="table table-striped-columns table_sort">
                    <tr>
                        <th class="col-lg-6">Запитання</th>
                        <th class="col-lg-2">Відсоток вірних відповідей</th>
                    </tr>
                    <c:forEach var="s" items="${statistic}">
                        <tr>
                            <td>
                                <details>
                                    <summary>${s.key.text}</summary>
                                    <c:if test="${s.key.imgSrc!=null}">
                                        <img src="${s.key.imgSrc}" alt="${s.key.text}">
                                    </c:if>
                                </details>
                            </td>
                            <td>${s.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
    </div>
    </div>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
