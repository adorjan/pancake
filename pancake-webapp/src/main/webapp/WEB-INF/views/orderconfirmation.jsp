<%@ include file="init.jsp" %>

<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pancake Order Confirmation</title>
</head>

<body>
<h1>Pancake Order Confirmation</h1>
<c:choose>
    <c:when test="${orderedPancakes.isEmpty()}">
        <c:out value="No pancake order has arrived from you. " /><a href="<%= request.getContextPath() %>/order">Back to Pancake Order Form.</a>
    </c:when>
    <c:otherwise>
        <c:out value="Your pancake order: " /><br />
        <c:forEach items="${orderedPancakes}" var="orderedPancake">
            <c:out value="${orderedPancake.key}: ${orderedPancake.value}" /><br />
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>
