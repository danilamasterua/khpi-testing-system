<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:useBean id="test" class="ds.testingsystem.model.Test"></jsp:useBean>--%>
<html>
<head>
    <title>Тестування</title>
    <link rel="stylesheet" href="css/dropdown.css">
    <LINK rel="stylesheet" href="css/main.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="js/getUserAnswer.js"></script>
</head>
<body>
<jsp:include page="servicejsp/standardHeader.jsp"></jsp:include>
<main>
    <c:forEach var="t" items="${test.modules}">
        <h2><c:out value="${t.value.description}"></c:out></h2>
        <br>
        <c:forEach var="m" items="${t.value.questions}">
            <c:out value="${m.value.text}"></c:out>
            <c:if test="${m.value.imgSrc!=null}">
                <p>
                <img src="${m.value.imgSrc}">
                </p>
            </c:if>
            <form class="question-answer">
                <input type="hidden" name="qId" value="${m.key}">
                <input type="hidden" name="qTypeId" value="${m.value.qTypeId}">
                <c:if test="${m.value.qTypeId==1}">
                    <c:forEach var="q" items="${m.value.answers}">
                        <p>
                        <input type="radio" name="chans" value="${q.key}">
                        <label for="${q.key}">${q.value.text}</label>
                        </p>
                    </c:forEach>
                </c:if>
                <c:if test="${m.value.qTypeId==2}">
                    <c:forEach var="q" items="${m.value.answers}">
                        <p>
                        <input type="checkbox" name="chans" value="${q.key}">
                        <label for="${q.key}">${q.value.text}</label>
                        </p>
                    </c:forEach>
                </c:if>
                <c:if test="${m.value.qTypeId==3}">
                    <input type="text" name="chans">
                </c:if>
                <input type="button" id="btn${m.key}" class="btn btn-primary" value="Відповісти" onclick="getAnswer(this)">
                <br>
            </form>
        </c:forEach>
    </c:forEach>
</main>
<jsp:include page="servicejsp/footer.jsp"></jsp:include>
</body>
</html>
