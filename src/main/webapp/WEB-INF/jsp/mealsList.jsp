<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3>Привет ${userName}!</h3>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Дата</th>
            <th>Еда</th>
            <th>Калории</th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.UserMeal"/>

            <tr>
                <td>
                    <%!
                        String formateDate(LocalDateTime date) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m dd-MMMM-yyyy ");
                            return date.format(formatter);
                        }
                    %>
                    <%= formateDate(meal.getDate()) %>
                </td>
                <td>${meal.meal}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
    </table>
</section>
<hr>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>