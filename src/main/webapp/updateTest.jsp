<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редагування тесту</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login-form.css">
    <link rel="stylesheet" href="css/create-test.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="js/createTest.js"></script>
    <script src="js/updateTest.js"></script>
</head>
<body>
    <jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
    <main>
        <input type="hidden" name="testId" id="creatingTestId" value="${sessionScope.get("creatingTestId")}">
        <div class="login-form-block" id="testMeta">
            <form action="updateServlet" method="post">
                <input type="hidden" name="testId" value="${sessionScope.get("creatingTestId")}">
                <label for="testName" class="form-label">Назва тесту</label>
                <input type="text" id="testName" name="testName" value="${test.name}" class="form-control">
                <label for="testDescription" class="form-label">Опис тесту</label>
                <input type="text" id="testDescription" name="testDescription" value="${test.description}" class="form-control">
                <input type="submit" class="btn btn-primary" value="Оновити">
            </form>
        </div>
        <div id="modules" class="fluid-container">
            <form>
                <input type="hidden" name="testId" value="${sessionScope.get("creatingTestId")}">
                <input type="button" id="newModuleBtn" class="btn btn-primary" value="Додати модуль" onclick="sCM(this)">
            </form>
            <c:forEach var="m" items="${test.modules}">
                    <h2>${m.value.description}</h2>
                <form class="btn-group">
                    <input type="hidden" name="moduleId" value="${m.key}">
                    <button type="button" id="dMBtn" class="btn btn-danger" onclick="deleteModule(this)">Видалити модуль</button>
                    <button type="button" id="uMBtn" class="btn btn-primary" onclick="showUpdateModuleForm(this)">Редагувати модуль</button>
                    <button type="button" id="cQBtn" class="btn btn-primary" onclick="showCreateQuestionFormM(this)">Додати запитання</button>
                </form>
                <c:forEach var="q" items="${m.value.questions}">
                    <details class="mb-2">
                        <summary class="row">
                            <a class="col-lg-4">${q.value.text}</a>
                            <button type="button" class="btn btn-primary col-lg-1 m-2" id="${q.key}" onclick="openUpdateQuestionForm(this)">Редагувати</button>
                            <button type="button" class="btn btn-danger col-lg-1 m-2" id="${q.key}" onclick="deleteQuestion(this)">Видалити</button>
                        </summary>
                        <table>
                            <c:if test="${q.value.imgSrc!=null && q.value.imgSrc!=\"\"}">
                                <img src="${q.value.imgSrc}" alt="${q.key}">
                            </c:if>
                            <c:forEach var="a" items="${q.value.answers}">
                                <tr>
                                    <td>${a.value.text}</td>
                                    <td>
                                        <c:if test="${a.value.right==true}">
                                            <i class="bi bi-check-circle"></i>
                                        </c:if>
                                        <c:if test="${a.value.right==false}">
                                            <i class="bi bi-x-circle"></i>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </details>
                </c:forEach>
            </c:forEach>
        </div>
        <div id="createNewQuestion" style="display: none">
            <div class="login-form-block">
                <form>
                    <p class="row mb-0">
                        <button type="button" id="backMM" class="btn btn-light col m-2" onclick="backToModule(this)"><i class="bi bi-arrow-bar-left"></i> Назад</button>
                        <input type="button" id="cMB" class="btn btn-primary float-end col m-2" value="Зберегти" onclick="">
                    </p>
                    <label for="qQType" class="form-label">Тип запитання</label>
                    <select name="questionType" id="qQType" class="form-select">
                        <c:forEach var="qT" items="${questionTypes}">
                            <option value="${qT.key}">${qT.value.description}</option>
                        </c:forEach>
                    </select>
                    <label for="questionText" class="form-label">Текст запитання</label>
                    <input type="text" name="text" id="questionText" placeholder="Назва тесту" class="form-control">
                    <label for="qImgSrc" class="form-label">Посилання на зображення <i class="bi bi-question-circle" title="Використовуйте хостинги зображень, такі як "></i></label>
                    <input type="text" name="imgSrc" id="qImgSrc" placeholder="Посилання на зображення" class="form-control">
                    <label for="qDiff" class="form-label">Складність запитання</label>
                    <select name="questionDif" id="qDiff" class="form-select">
                        <c:forEach var="d" items="${difficults}">
                            <option value="${d.key}">${d.value.description}</option>
                        </c:forEach>
                    </select>
                    <button type="button" id="addAnsBtn" class="btn btn-light" onclick="addAnswerVar(this)"><i class="bi bi-plus-circle"></i> Додати варіант відповіді</button>
                </form>
            </div>
        </div>
        <div id="createNewModule" style="display: none;">
            <hr>
            <div class="login-form-block mb-2">
                <form>
                    <p class="row mb-0">
                        <button type="button" id="backTM" class="btn btn-light col m-2" onclick="backToModule(this)"><i class="bi bi-arrow-bar-left"></i> Назад</button>
                        <input type="button" id="cM" class="btn btn-primary float-end col m-2" value="Зберегти" onclick="createModule(this)">
                    </p>
                    <label for="moduleDescription" class="form-label">Назва модулю</label>
                    <input id="moduleDescription" type="text" name="description" placeholder="Назва модулю" class="form-control">
                    <label for="moduleQCount" class="form-label">Кількість запитань</label>
                    <input id="moduleQCount" type="number" name="qCount" placeholder="Кількість запитань" class="form-control">
                </form>
            </div>
        </div>
    </main>
    <jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
