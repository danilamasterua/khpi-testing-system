<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:useBean id="user" class="ds.testingsystem.database.model.User"/>--%>
<html>
<head>
    <title>Особистий кабінет</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login-form.css">
    <link rel="stylesheet" href="css/usercard.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="js/userInfo.js"></script>
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <div id="alert-box" class="container">

    </div>
    <div class="user-actions">
    <div class="user-card">
      <h2>${user.firstName} ${user.lastName}</h2>
      <br>
      <p>Email: ${user.email}</p>
      <p>Role: <c:choose>
          <c:when test="${user.roleId==1}">Системний адміністратор</c:when>
          <c:when test="${user.roleId==2}">Викладач</c:when>
          <c:when test="${user.roleId==3}">Здобувач освіти</c:when>
      </c:choose></p>
        <p>Group: ${user.groupId}</p>
        <button type="button" class="btn btn-link" onclick="openChangePasswordForm()">Змінити пароль</button>
    </div>
        <div id="change-password" class="login-form-block" style="display: none">
            <form>
                <input type="hidden" value="${user.userId}" name="userId">
                <label for="oldPwd" class="form-label">Старий пароль</label>
                <input id="oldPwd" type="password" name="oldPassword" class="form-control" >
                <label for="pwd1" class="form-label">Введіть новий пароль</label>
                <input id="pwd1" type="password" name="newPassword1" class="form-control" onchange="isRightFormatCheck(this.value)">
                <label for="pwd2" class="form-label">Повторіть</label>
                <input id="pwd2" type="password" name="newPassword2" class="form-control" onchange="checkPasswords(this.value)" disabled>
                <button id="chBtn" type="button" class="btn btn-primary" onclick="preparePasswordChanging()" disabled>Змінити пароль</button>
            </form>
        </div>
        <div id="verification" class="login-form-block" style="display: none;">
            <form>
                <input type="hidden" value="${user.userId}" name="userId">
                <label for="verCode" class="form-label">Введіть отриманий код</label><br>
                <small>Код, що ви отримали актуальний лише 15 хвилин</small>
                <input id="verCode" type="text" class="form-control" name="verCode">
                <button type="button" id="verBtn" class="btn btn-primary" onclick="changePassword()">Підтвердити</button>
            </form>
        </div>
    </div>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
