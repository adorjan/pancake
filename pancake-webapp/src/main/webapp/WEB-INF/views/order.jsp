<%@ include file="init.jsp" %>

<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pancake Order</title>
</head>

<body>
<h1>Pancake Order Form</h1>
<form action="<%= request.getContextPath() %>/orderconfirmation" method="POST">
<c:out value="Your e-mail address: " /><input type="text" name="eMailAddress" /><br />
<c:forEach items="${pancakes}" var="pancake">
    <c:out value="${pancake}: " /><input type="text" name="${pancake}"><br />
</c:forEach>
<input type="submit" value="Order" />
</form>
</body>
</html>
