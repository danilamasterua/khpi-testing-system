<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Створення нового користувача</title>
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
<main>
    <div class="login-form-block">
        <form action="createTeacher" method="post">
            <label for="firstName" class="form-label">Ім'я</label>
            <input id="firstName" type="text" class="form-control" placeholder="Ім'я" name="firstName" required>
            <label for="lastName" class="form-label">Прізвище</label>
            <input id="lastName" type="text" class="form-control" placeholder="Прізвище" name="lastName" required>
            <label for="email" class="form-label">EMail</label>
            <input id="email" type="email" class="form-control" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" placeholder="EMail" name="email" required>
            <input type="submit" value="Створити користувача" class="btn btn-primary">
        </form>
    </div>
</main>
</body>
</html>
