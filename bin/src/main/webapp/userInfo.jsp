<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:useBean id="user" class="ds.testingsystem.database.model.User"/>--%>
<html>
<head>
    <title>Особистий кабінет</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/usercard.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script type="text/javascript">
        function showPasswordChanger(){
            document.getElementById("change-password").style.display="block";
        }
    </script>
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
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
      <h5>Groups:</h5>
      <c:forEach var="g" items="${user.groups}">
          <p>${g.value.name}</p>
      </c:forEach>
    </div>
        <div id="action-block">
            <input type="button" onclick="showPasswordChanger()" class="btn btn-link">
        </div>
        <div id="change-password">
            <form>
                <p>Старий пароль</p>
                <input type="password" name="oldPassword" required>
                <p>Новий пароль</p>
                <input type="password" name="newPassword1" required>
                <p>Повторіть</p>
                <input type="password" name="newPassword2" required>
                <input type="button" class="btn btn-primary" value="Змінити пароль">
                <a href="#action-block">Повернутися назад</a>
            </form>
        </div>
    </div>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
