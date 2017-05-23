<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>电脑列表</title>
</head>
<body>
<c:if test="${!empty computers}">
    <table>
        <tr>
            <th>id</th>
            <th>computer_name</th>
            <th>computer_desc</th>
            <th>computer_type</th>
            <th>computer_country</th>
            <th>computer_count</th>
            <th>computer_price</th>
            <th>computer_size</th>
            <th>computer_color</th>
            <th>removed</th>
            <th>created</th>
            <th>updated</th>
        </tr>


        <c:forEach var="computer" items="${computers}">
            <tr>
                <td>${computer.computerId}</td>
                <td>${computer.computerName}</td>
                <td>${computer.computerDesc}</td>
                <td>${computer.computerType}</td>
                <td>${computer.computerCountry}</td>
                <td>${computer.computerCount}</td>
                <td>${computer.computerPrice}</td>
                <td>${computer.computerSize}</td>
                <td>${computer.computerColor}</td>
                <td>${computer.removed}</td>
                <td><fmt:formatDate value="${computer.created}" pattern="yyyy-MM-dd HH-mm-ss"></fmt:formatDate></td>
                <td><fmt:formatDate value="${computer.updated}" pattern="yyyy-MM-dd HH-mm-ss"></fmt:formatDate></td>
            </tr>

        </c:forEach>
    </table>
</c:if>
</body>
</html>
