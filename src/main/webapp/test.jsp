<%--<jsp:useBean id="accessData" scope="request" type="ds.testingsystem.database.model.beans.GroupAccess"/>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:useBean id="test" class="ds.testingsystem.model.Test"></jsp:useBean>--%>
<html>
<head>
    <title>Тестування</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/login-form.css">
    <LINK rel="stylesheet" href="css/main.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="js/getUserAnswer.js"></script>
</head>
<body>
<header class="justify-content-between" style="position: fixed; top:0;">
    <h2 class="col-4">${test.name}</h2>
    <h2 class="col-4" id="timer"></h2>
</header>
<main class="container" style="margin-top: 50px">
    <div id="startTest" class="justify-content-center">
        <div>
            <a href="getTests" class="btn btn-link"><i class="bi bi-arrow-bar-left"></i> Повернутись на сторінку вибору тестів</a>
            <h2>${test.name}</h2>
                <form>
                    <input type="hidden" name="finAccDate" value="${accessData.accFinTime}">
                    <input type="hidden" name="minToFin" value="${accessData.minToFin}">
                    <button type="button" class="btn btn-primary" id="startTestBtn" onclick="startTest(this)">Розпочати тест</button>
                </form>
        </div>
    </div>
    <c:forEach var="t" items="${test.modules}">
        <c:forEach var="m" items="${t.value.questions}">
            <div class="login-form-block p-3 m-3 justify-content-center" id="q${m.key}" style="display: none; max-width: 500px">
                <form class="question-answer">
                    <small>${t.value.description}</small>
                    <p id="qT${m.key}">${m.value.text}</p>
                    <hr>
                    <c:if test="${m.value.imgSrc!=null}">
                        <p>
                            <img src="${m.value.imgSrc}">
                        </p>
                    </c:if>
                    <input type="hidden" name="qId" value="${m.key}">
                    <input type="hidden" name="qTypeId" value="${m.value.qTypeId}">
                    <c:if test="${m.value.qTypeId==1}">
                        <c:forEach var="q" items="${m.value.answers}">
                            <p>
                            <input type="radio" name="chans" value="${q.key}" class="form-check-input">
                            <label for="${q.key}" class="form-check-label">${q.value.text}</label>
                            </p>
                        </c:forEach>
                    </c:if>
                    <c:if test="${m.value.qTypeId==2}">
                        <c:forEach var="q" items="${m.value.answers}">
                            <p>
                            <input type="checkbox" name="chans" value="${q.key}" class="form-check-input">
                            <label for="${q.key}" class="form-check-label">${q.value.text}</label>
                            </p>
                        </c:forEach>
                    </c:if>
                    <c:if test="${m.value.qTypeId==3}">
                        <input type="text" name="chans" class="form-control">
                    </c:if>
                    <input type="hidden" name="isAnswered" value="false">
                    <input type="button" id="btn${m.key}" class="btn btn-primary" value="Відповісти" onclick="getAnswer(this)">
                    <input type="button" id="skip${m.key}" name="skip" class="btn btn-light" value="Наступне питання" onclick="skipQuestion(this)">
                    <br>
            </form>
            </div>
        </c:forEach>
    </c:forEach>
    <div id="endTest" class="login-form-block" style="display: none;">
        <div id="skippedQuestions"></div>
        <br>
        <form>
            <h4>Тест "${test.name}" завершено</h4>
            <input type="hidden" name="testId" value="${testId}">
            <button type="button" id="endTestBtn" class="btn btn-primary" onclick="getTestPoints(this)">Завершити тест</button>
        </form>
    </div>
    <div id="loading" style="display: none;" class="align-items-center">
        <hr>
        <strong>Очікуйте...</strong>
        <div class="spinner-border ms-auto" role="status" aria-hidden="true"></div>
    </div>
    <div id="results" class="justify-content-center" style="display: none;">
        <div>
            <a href="getTests" class="btn btn-link"><i class="bi bi-arrow-bar-left"></i> Повернутись на сторінку вибору тестів</a>
            <hr>
            <p id="resultField">Підсумкова оцінка: </p>
        </div>
    </div>
</main>
</body>
</html>
