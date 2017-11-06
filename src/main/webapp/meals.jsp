<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        .meals {
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<c:choose>
    <c:when test="${empty mealsWithExceed}">
        No such parameter or empty "mealsWithExceed" in attributes
    </c:when>
    <c:otherwise>
        <table class="meals">
            <th>ID</th>
            <th>Description</th>
            <th>DateTime</th>
            <th>Calories</th>
            <th colspan="2">Action</th>
            <c:forEach items="${mealsWithExceed}" var="meal">
                <tr class=" + <c:out value="${meal.isExceed() ? 'exceed' : 'not-exceed'}"/> ">
                    <td>${meal.getId()}</td>
                    <td>${meal.getDescription()}</td>
                    <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="datetimeParsed" type="both"/>
                    <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${datetimeParsed}" var="datetimeFormatted"/>
                    <td>${datetimeFormatted}</td>
                    <td>${meal.getCalories()}</td>
                    <td><a href="meals?action=edit&mealId=<c:out value="${meal.getId()}"/>">Update</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${meal.getId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<h2>Add new meal</h2>
<form method="GET" action="meals">
    <input type="submit" value="Add new"/>
</form>
<form method="POST" action="meals">
    Meal ID : <input type="text" readonly="readonly" name="id" value="<c:out value="${meal.getId()}" />" /> <br />
    Description : <input type="text" name="description" value="<c:out value="${meal.getDescription()}" />" /> <br />
    DateTime : <input type="datetime-local" name="datetime" value="<c:out value="${meal.getDateTime()}" />" /> <br />
    Calories : <input type="text" name="calories" value="<c:out value="${meal.getCalories()}" />" /> <br />
    <input type="submit" value="submit" />
</form>

</body>
</html>