<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.Date" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="database.HibernateUtil" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>SpaceBrains</title>
    <link rel="stylesheet" type="text/css" href="stylesheet.css">
</head>
<body>
    <% if (session.getAttribute("status") == null) {
        session.setAttribute("status", "Не авторизован");
    }%>
    <% Date currentDate = new Date(System.currentTimeMillis());%>
    <div class="top">
        <div class="header_footer">Space Brains</div>
    </div>
    <div class="left">
        <span class="menu" style="border-bottom: 1px solid gray"><a href="statistics/common.jsp">Общая статистика</a></span>
        <span class="menu" style="border-bottom: 1px solid gray"><a href=<%= "statistics/daily.jsp?page=1&begindate=" + currentDate + "&enddate="  + currentDate + "&site=0&personId=0"%>>Ежедневная статистика</a></span>
    </div>
    <div class="border_left"></div>
    <div class="border_top">
        <br>
        <span class="top_label"><%=session.getAttribute("status")%></span>
    </div>
    <div class="right">
        <%  String login = request.getParameter("login") == null? "": request.getParameter("login");
            String password = request.getParameter("password") == null? "": request.getParameter("password");
            if (!login.equals("") && !password.equals("")) {
                Session ORMSession = HibernateUtil.getSessionFactory().openSession();
                Query query = ORMSession.createQuery("FROM UsersEntity WHERE login = :login AND password = :password");
                query.setParameter("login", login);
                query.setParameter("password", password);
                List result = query.list();
                if (result.size() > 0) {
                    session.setAttribute("status", "Вы авторизованы");
                    response.setStatus(response.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", "index.jsp");
                }
            }
        %>
        <form id="registration" action="index.jsp">
            <table>
                <tr>
                    <td>
                        <p>Логин:</p>
                    </td>
                    <td>
                        <input maxlength="25" size="40" name="login" value="<%=login%>">
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>Пароль:</p>
                    </td>
                    <td>
                        <input type="password" maxlength="25" size="40" name="password" value="<%=password%>">
                    </td>
                </tr>
                <tr>
                    <td><button class="button" type="submit" form="registration">Войти</button></td>
                    <td>Введите логин и пароль.</td>
                </tr>
            </table>
        </form>
    </div>
    <div class="bottom">
        <div class="header_footer">&copy; 2017 Space Brains Project</div>
    </div>
</p>
</body>
</html>

