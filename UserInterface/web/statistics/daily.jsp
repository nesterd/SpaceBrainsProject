<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="database.PersonPageRankEntity" %>
<%@ page import="database.HibernateUtil" %>
<%@ page import="java.sql.Date" %>
<%@ page import="userinterface.MyDate" %>
<html>
<head>
    <title>SpaceBrains</title>
    <link rel="stylesheet" type="text/css" href="../calendar/tcal.css" />
    <script type="text/javascript" src="../calendar/tcal.js"></script>
</head>
<p>
    <span><a href="common.jsp">Общая статистика</a></span>
    <span>Ежедневная статистика</span>
    <p>
        <form id="date">
            <%
                String begindate = (request.getParameter("begindate"));
                String enddate = (request.getParameter("enddate"));
                Date begin = MyDate.valueOf(begindate);
                Date end = MyDate.valueOf(enddate);
            %>
            <span><input type="text" name="begindate" class="tcal" value=<%=begin==null ? "" : begin.toString()%> /></span>
            <span><input type="text" name="enddate" class="tcal" value=<%=end==null ? "" : end.toString()%> /></span>
        </form>
        <button type="submit" form="date">Сформировать</button>
    </p>
    <%  List result;
        if (session.getAttribute("dailylist") != null && session.getAttribute("begin") == begin && session.getAttribute("end") == end) {
            result = (List) session.getAttribute("dailylist");
        }
        else {
            String queryText = "FROM PersonPageRankEntity ppr WHERE pagesByPageId.foundDateTime != null ";
            if (begin != null) {
                queryText += " AND pagesByPageId.foundDateTime >= :begindate";
            }
            if (end != null) {
                queryText += " AND pagesByPageId.foundDateTime <= :enddate";
            }
            Session ORMSession = HibernateUtil.getSessionFactory().openSession();
            Query query = ORMSession.createQuery(queryText);
            if (begin != null) {
                query.setParameter("begindate", begin);
            }
            if (end != null) {
                query.setParameter("enddate", end);
            }
            result = query.list();
            session.setAttribute("dailylist", result);
            session.setAttribute("begin", begin);
            session.setAttribute("end", end);
            ORMSession.close();
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
        <% String ref = "daily.jsp"; %>
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
    <a href=<%= ref + "?page=" + i
            + "&begindate=" + (begin==null ? "" : begin.toString())
            + "&enddate=" + (end==null ? "" : end.toString())%>><%=i%></a>
    <%}
    }%>
</body>
</html>
