<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Вітаємо</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <LINK rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login-form.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <div class="login-form-block">
        <form action="login" method="post" class="center-block">
            <label for="login" class="form-label">Login</label>
            <input id="login" type="text" name="login" placeholder="Login" class="form-control" required>
            <label for="pass" class="form-label">Password</label>
            <input id="pass" type="password" name="pass" placeholder="Password" class="form-control" required>
            <button type="submit" class="btn btn-primary"><i class="bi bi-arrow-right-circle"></i> Увійти</button>
        </form>
    </div>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>