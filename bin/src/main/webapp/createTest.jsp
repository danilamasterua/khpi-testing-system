<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Створення тесту</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login-form.css">
    <link rel="stylesheet" href="css/create-test.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="js/createTest.js"></script>
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <h2>Створення нового тесту</h2>
    <div class="login-form-block">
        <form>
            <label for="testName" class="col-form-label">Назва тесту</label>
            <input id="testName" type="text" name="name" placeholder="Назва тесту" class="form-control" required>
            <label for="testDescription" class="col-form-label">Опис тесту</label>
            <input id="testDescription" type="text" name="description" placeholder="Опис тесту" class="form-control">
            <input type="button" value="Створити тест" id="tId" onclick="createTest(this)" class="btn btn-primary">
        </form>
    </div>
    <div id="modules" style="display: none">
        <hr>
        <form>
            <input type="button" class="btn btn-primary" value="Додати модуль" onclick="showCreateModuleForm()">
        </form>
    </div>
    <div id="createNewModule" style="display: none;">
        <hr>
        <div class="login-form-block">
            <form>
                <label for="moduleDescription" class="form-label">Назва модулю</label>
                <input id="moduleDescription" type="text" name="description" placeholder="Назва модулю" class="form-control">
                <label for="moduleQCount" class="form-label">Кількість запитань</label>
                <input id="moduleQCount" type="number" name="qCount" placeholder="Кількість запитань" class="form-control">
                <input type="button" value="Створити модуль" class="btn btn-primary">
            </form>
        </div>
    </div>
    <div id="createNewQuestion" style="display: none;">
        <hr>
        <div class="login-form-block">
            <form>
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
            </form>
        </div>
    </div>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
