<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta charset="UTF-8">
<header>
    <img src="${pageContext.request.contextPath}/img/DSTS_logo.png" alt="DSTSLogo" class="logo_header">
    <c:if test="${model!=null}">
        <div style="display: inline-flex; align-items: center">
        <form action="getTests" method="get">
            <input type="submit" value="Available tests" class="btn btn-light">
        </form>
            <c:if test="${model.currentUser.roleId==2 || model.currentUser.roleId==1}">
                <form action="test-control-panel" method="get"><input type="submit" value="Test control panel" class="btn btn-light"></form>
                <c:if test="${model.currentUser.roleId==1}">
                    <form action="userControl" method="get"><input type="submit" value="User control panel" class="btn btn-light"></form>
                </c:if>
            </c:if>
        </div>
        <div style="display: flex; justify-content: space-between; margin-left: auto; align-items: center">
            <div class="dropdown">
                <button class="dropdown-button btn btn-light">${model.currentUser.firstName} ${model.currentUser.lastName}</button>
                <div class="dropdown-child">
                    <a href="userCab">Profile</a>
                    <a href="exit">Exit <i class="bi bi-box-arrow-left"></i></a>
                </div>
            </div>
        </div>
    </c:if>
</header>
<div style="display: <c:choose>
<c:when test="${error!=null}">block</c:when>
<c:when test="${error==null}">none</c:when>
        </c:choose>">
    <%@include file="error.jsp"%>
</div>

