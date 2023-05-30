<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Панель керування користувачами</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login-form.css">
    <link rel="stylesheet" href="css/create-test.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head>
<body>
  <jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
  <main>
    <h1>Панель керування тестами</h1>
    <form action="CreateNewUser.jsp">
      <input type="submit" value="Створити обліковий запис викладача" class="btn btn-primary">
    </form>
    <div class="scroll-list">
      <c:forEach var="u" items="${users}">
        <div class="test-card">
          <h2>${u.value.firstName} ${u.value.lastName}</h2>
          <p>
              <b>Email:</b> ${u.value.email}
          </p>
            <div class="btn-group">
                <form class="btn" action="updateUser">
                    <input type="hidden" name="userId" value="${u.key}">
                    <button type="submit" class="btn btn-primary">Редагувати користувача</button>
                </form>
            </div>
            <form class="btn">
                <input type="hidden" name="userId" value="${u.key}">
                <button type="submit" class="btn btn-primary">Заблокувати користувача</button>
            </form>
        </div>
      </c:forEach>
    </div>
  </main>
  <jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
