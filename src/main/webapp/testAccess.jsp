<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<jsp:useBean id="accessedGroups" class="java.util.HashMap<java.lang.Integer, ds.testingsystem.database.model.Group>"></jsp:useBean>--%>
<%--<jsp:useBean id="notAcessedGroups" class="java.util.HashMap<java.lang.Integer, ds.testingsystem.database.model.Group>"></jsp:useBean>--%>
<html>
<head>
    <title>Надання доступу до тесту</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login-form.css">
    <link rel="stylesheet" href="css/create-test.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="js/accessTable.js"></script>
</head>
<body>
    <jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
    <main>
        <h1>${test.name}</h1>
        <div id="accessTable" class="row justify-content-center">
            <div class="col-6 col-sm-4">
                <h4>Групи, що мають доступ до тесту</h4>
                <table class="table table-striped">
                    <c:forEach var="g" items="${accessedGroups}">
                        <tr>
                            <td>
                                <details>
                                    <summary>${g.value.name}</summary>
                                    <c:forEach var="u" items="${g.value.users}">
                                        <p>${u.value.lastName}, ${u.value.firstName}</p>
                                    </c:forEach>
                                </details>
                            </td>
                            <td>
                                <form action="denyAccess" method="get">
                                    <input type="hidden" name="testId" value="${testId}">
                                    <input type="hidden" name="gId" value="${g.key}">
                                    <button type="submit" class="btn btn-danger"><i class="bi bi-folder-minus"></i> Заборонити доступ</button>
                                    <button type="button" id="nsa${g.key}" class="btn btn-primary" onclick="showCrUserForm(this)">Додати студента</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col-6 col-sm-4">
                <h4>Групи, що не мають доступу до тесту <button class="btn btn-link" onclick="showCrGroupForm(this)">Створити групу</button></h4>
                <table class="table table-striped">
                    <c:forEach var="g" items="${notAcessedGroups}">
                        <tr>
                            <td>
                                <details>
                                    <summary>${g.value.name}</summary>
                                    <c:forEach var="u" items="${g.value.users}">
                                        <p>${u.value.lastName}, ${u.value.firstName}</p>
                                    </c:forEach>
                                </details>
                            </td>
                            <td>
                                <form>
                                    <input type="hidden" name="gId" value="${g.key}">
                                    <button type="button" id="ng${g.key}" class="btn btn-primary" onclick="showCrAccForm(this)"><i class="bi bi-folder-plus"></i> Надати доступ</button>
                                    <button type="button" id="ns${g.key}" class="btn btn-primary" onclick="showCrUserForm(this)">Додати студента</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div id="getAccess" style="display:none;">
            <div class="login-form-block">
                <form action="grantAccess" method="get">
                    <input type="hidden" value="${testId}" name="testId">
                    <select onchange="changeAccType(this)" class="form-select">
                        <option value="temp">Тимчасовий доступ</option>
                        <option value="permanent">Постійний доступ</option>
                        <option value="permanentTime">Постійний доступ, з обмеженням часу</option>
                    </select>
                    <div id="tempAccess" style="display: block;">
                        <label for="accStTime" class="form-label">Початок доступу до тесту</label>
                        <input type="datetime-local" id="accStTime" class="form-control" name="accStTime">
                        <label for="accFinTime" class="form-label">Кінець доступу до тесту</label>
                        <input type="datetime-local" id="accFinTime" class="form-control" name="accFinTime">
                    </div>
                    <div id="permTimeAcc" style="display: none;">
                        <label for="mtf" class="form-label">Час на проходження тесту</label>
                        <input id="mtf" type="number" name="minToFin" class="form-control">
                    </div>
                    <p class="row mb-0">
                        <button type="button" id="backMM" class="btn btn-light col m-2" onclick="backToTable(this)"><i class="bi bi-arrow-bar-left"></i> Назад</button>
                        <input type="submit" id="cMB" class="btn btn-primary float-end col m-2" value="Зберегти">
                    </p>
                </form>
            </div>
        </div>
        <div id="createNewUser" style="display:none;">
            <div class="login-form-block">
            <form>
                <input type="hidden" name="testId" value="${testId}">
                <label for="firstName" class="form-label">Ім'я</label>
                <input id="firstName" type="text" class="form-control" placeholder="Ім'я" name="firstName">
                <label for="lastName" class="form-label">Прізвище</label>
                <input id="lastName" type="text" class="form-control" placeholder="Прізвище" name="lastName">
                <label for="email" class="form-label">EMail</label>
                <input id="email" type="email" class="form-control" placeholder="EMail" name="email">
                <p class="row mb-0">
                    <button type="button" id="backM" class="btn btn-light col m-2" onclick="backToTable(this)"><i class="bi bi-arrow-bar-left"></i> Назад</button>
                    <button type="button" id="cNU" class="btn btn-primary col m-2" onclick="createStudent(this)">Зберегти</button>
                </p>
            </form>
            </div>
        </div>
        <div id="createNewGroup" style="display: none;">
            <div class="login-form-block">
                <form>
                <label for="gd" class="form-label">Назва групи</label>
                <input name="description" id="gd" type="text" class="form-control">
                <button type="button" id="gdBtn" class="btn btn-primary" onclick="createGroup(this)">Створити групу</button>
                </form>
            </div>
        </div>
    </main>
    <jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
