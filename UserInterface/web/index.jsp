<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.Date" %>
<html>
<head>
    <title>SpaceBrains</title>
</head>
<body>
    <% Date currentDate = new Date(System.currentTimeMillis());%>
    <span><a href="statistics/common.jsp">Общая статистика</a></span>
    <span><a href=<%= "statistics/daily.jsp?page=1&begindate=" + currentDate + "&enddate="  + currentDate + "&site=0&personId=0"%>>Ежедневная статистика</a></span>
</p>
</body>
</html>

