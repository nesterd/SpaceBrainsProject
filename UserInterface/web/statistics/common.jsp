<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="database.PersonPageRankEntity" %>
<%@ page import="database.HibernateUtil" %>
<%@ page import="java.sql.Date" %>
<html>
<head>
    <title>Space Brains</title>
    <link rel="stylesheet" type="text/css" href="../stylesheet.css">
</head>
<body>
    <% Date currentDate = new Date(System.currentTimeMillis());%>
    <div class="top">
        <div class="header_footer">Space Brains</div>
    </div>
    <div class="left">
        <span class="menu_selected" type="button">Общая статистика</span>
        <span class="menu"><a href=<%= "daily.jsp?page=1&begindate=" + currentDate + "&enddate="  + currentDate%>>Ежедневная статистика</a></span>
    </div>
    <div class="border_left"></div>
    <div class="border_top"></div>
    <div class="right">
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
        <p>Общее количество: <%=result.size()%></p>
        <table class="table_content">
            <tr>
                <th class="th_content">

                </th>
                <th class="th_content">
                    Персона
                </th>
                <th class="th_content">
                    Ссылка
                </th>
                <th class="th_content">
                    Ранг
                </th>
            </tr>
            <% String ref = "common.jsp"; %>
            <% for (int i = 10 * (currentPage - 1) + 1; i <= 10 * currentPage; i++) {
                if (i > result.size()) {
                    continue;
                }
                Object element = result.get(i-1); %>
            <tr>
                <td class="td_content">
                    <%= i %>
                </td>
                <td class="td_content">
                    <%= ((PersonPageRankEntity) element).getPersonsByPersonId().getName() %>
                </td>
                <td class="td_content">
                    <%= ((PersonPageRankEntity) element).getPagesByPageId().getUrl() %>
                </td>
                <td class="td_content">
                    <%= ((PersonPageRankEntity) element).getRank() %>
                </td>
            </tr>
            <%}%>
        </table>
        Страницы:
        <% for (int i = 1; i <= pagesCount; i++) {%>
        <% if (currentPage == i) {%>
            <%= i%>
        <%} else {%>
            <a href=<%= ref + "?page=" + i %>><%=i%></a>
        <%}
        }%>
    </div>
    <div class="bottom">
        <div class="header_footer">&copy; 2017 Space Brains Project</div>
    </div>
</body>
</html>
