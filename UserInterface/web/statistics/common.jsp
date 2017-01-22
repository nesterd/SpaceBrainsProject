<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="database.PersonPageRankEntity" %>
<%@ page import="database.HibernateUtil" %>
<%@ page import="java.sql.Date" %>
<html>
<head>
    <title>SpaceBrains</title>
</head>
<body>
    <% Date currentDate = new Date(System.currentTimeMillis());%>
    <span>Общая статистика</span>
    <span><a href=<%= "daily.jsp?page=1&begindate=" + currentDate + "&enddate="  + currentDate%>>Ежедневная статистика</a></span>
    <%  List result;
        if (session.getAttribute("commonlist") == null) {
            Session ORMSession = HibernateUtil.getSessionFactory().openSession();
            Query query = ORMSession.createQuery("FROM PersonPageRankEntity ppr");
            result = query.list();
            session.setAttribute("commonlist", result);
            ORMSession.close();
        }
        else {
            result = (List) session.getAttribute("commonlist");
        }
        int pagesCount = result.size() / 10 + 1;
        int currentPage;
        if (request.getParameter("page") == null) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }%>
    <table border="1">
        <tr>
            <td>

            </td>
            <td>
                Персона
            </td>
            <td>
                Ссылка
            </td>
            <td>
                Ранг
            </td>
        </tr>
        <% String ref = "common.jsp"; %>
        <% for (int i = 10 * (currentPage - 1) + 1; i <= 10 * currentPage; i++) {
            if (i > result.size()) {
                continue;
            }
            Object element = result.get(i-1); %>
        <tr>
            <td>
                <%= ((PersonPageRankEntity) element).getId() %>
            </td>
            <td>
                <%= ((PersonPageRankEntity) element).getPersonsByPersonId().getName() %>
            </td>
            <td>
                <%= ((PersonPageRankEntity) element).getPagesByPageId().getUrl() %>
            </td>
            <td>
                <%= ((PersonPageRankEntity) element).getRank() %>
            </td>
        </tr>
        <%}%>
    </table>
    <% for (int i = 1; i <= pagesCount; i++) {%>
    <% if (currentPage == i) {%>
        <%= i%>
    <%} else {%>
        <a href=<%= ref + "?page=" + i %>><%=i%></a>
    <%}
    }%>
</body>
</html>
