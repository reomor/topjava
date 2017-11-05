<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
    <style type="text/css">
        .exceed {
            color: #f41716;
        }
        .not-exceed {
            color: #27c530;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<c:choose>
    <c:when test="${empty meals}">
        No such parameter in attributes
    </c:when>
    <c:otherwise>
        <table>
            <c:forEach items="${meals}" var="meal">
                <tr class=" + <c:out value="${meal.isExceed() ? 'exceed' : 'not-exceed'}"/> ">
                    <td>${meal.getDescription()}</td>
                    <td>${meal.getDateTime().toLocalDate()} ${meal.getDateTime().toLocalTime()}</td>
                    <td>${meal.getCalories()}</td>
                    <td>${meal.isExceed()}</td>
                </tr>
            </c:forEach>
        </table>

    </c:otherwise>
</c:choose>

</body>
</html>