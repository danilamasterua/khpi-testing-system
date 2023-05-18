<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<jsp:useBean id="model" type="ds.testingsystem.database.model.Model"></jsp:useBean>--%>
<html>
<head>
    <title>Здані тест</title>
    <meta charset="UTF-8">
    <title>Головна - DS Testing System</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <link rel="stylesheet" href="css/main.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
  <c:forEach var="t" items="${model.passedTests}">
      <div class="test-card">
          <h3>${t.key.name}</h3>
          <p>${t.key.description}</p>
          <p>Оцінка за тест: ${t.value.doubleValue()}</p>
      </div>
  </c:forEach>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
