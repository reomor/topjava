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
        No such parameter "meals" in attributes
    </c:when>
    <c:otherwise>
        <table>
            <c:forEach items="${meals}" var="meal">
                <tr>
                    <td>${meal.getId()}</td>
                    <td>${meal.getDescription()}</td>
                    <td>${meal.getDateTime().toLocalDate()} ${meal.getDateTime().toLocalTime()}</td>
                    <td>${meal.getCalories()}</td>
                    <td><a href="meals?action=edit&mealId=<c:out value="${meal.getId()}"/>">Update</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${meal.getId()}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<h2>Add new meal</h2>
<form method="POST" action="meals">
    Meal ID : <input type="text" readonly="readonly" name="id" value="<c:out value="${meal.getId()}" />" /> <br />
    Description : <input type="text" name="description" value="<c:out value="${meal.getDescription()}" />" /> <br />
    DateTime : <input type="datetime-local" name="datetime" value="<c:out value="${meal.getDateTime()}" />" /> <br />
    Calories : <input type="text" name="calories" value="<c:out value="${meal.getCalories()}" />" /> <br />
    <input type="submit" value="Submit" />
</form>
<h2>Meals with Exceed</h2>
<c:choose>
    <c:when test="${empty mealsWithExceed}">
        No such parameter "mealsWithExceed" in attributes
    </c:when>
    <c:otherwise>
        <table>
            <c:forEach items="${mealsWithExceed}" var="meal">
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