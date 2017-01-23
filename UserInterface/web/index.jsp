<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.Date" %>
<html>
<head>
    <title>SpaceBrains</title>
    <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body>
    <% Date currentDate = new Date(System.currentTimeMillis());%>
    <div class="top">
        <div class="header_footer">Space Brains</div>
    </div>
    <div class="left">
        <span class="menu"><a href="statistics/common.jsp">Общая статистика</a></span>
        <span class="menu"><a href=<%= "statistics/daily.jsp?page=1&begindate=" + currentDate + "&enddate="  + currentDate + "&site=0&personId=0"%>>Ежедневная статистика</a></span>
    </div>
    <div class="border_left"></div>
    <div class="border_top"></div>
    <div class="right"></div>
    <div class="bottom">
        <div class="header_footer">&copy; 2017 Space Brains Project</div>
    </div>
</p>
</body>
</html>

