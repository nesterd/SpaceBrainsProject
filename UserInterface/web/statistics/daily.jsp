<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="database.PersonPageRankEntity" %>
<%@ page import="database.HibernateUtil" %>
<%@ page import="database.PersonsEntity" %>
<%@ page import="database.PagesEntity" %>
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
            <span><input type="text" name="begindate" class="tcal" value="" /></span>
            <span><input type="text" name="enddate" class="tcal" value="" /></span>
        </form>
        <button type="submit" form="date">Сформировать</button>
    </p>
    <%  List result;
        if (session.getAttribute("list") == null) {
            Session ORMSession = HibernateUtil.getSessionFactory().openSession();
            Query query = ORMSession.createQuery("FROM PersonPageRankEntity ppr");
            result = query.list();
            session.setAttribute("list", result);
            ORMSession.close();
        }
        else {
            if ((request.getParameter("begindate") == null || request.getParameter("begindate").equals(""))
                && (request.getParameter("enddate") == null || request.getParameter("enddate").equals(""))) {
                result = (List) session.getAttribute("list");
            }
            else {
                String queryText = "FROM PersonPageRankEntity ppr WHERE pagesByPageId.foundDateTime != null ";
                if (request.getParameter("begindate").length() > 0) {
                    queryText += " AND pagesByPageId.foundDateTime >= :begindate";
                }
                if (request.getParameter("enddate").length() > 0) {
                    queryText += " AND pagesByPageId.foundDateTime <= :enddate";
                }
                Session ORMSession = HibernateUtil.getSessionFactory().openSession();
                Query query = ORMSession.createQuery(queryText);
                query.setParameter("begindate", session.getAttribute("begindate"));
                query.setParameter("enddate", session.getAttribute("enddate"));
                result = query.list();
                System.out.println(result.size());
                session.setAttribute("list", result);
                ORMSession.close();
            }
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
    <a href=<%= ref + "?page=" + i %>><%=i%></a>
    <%}
    }%>
</body>
</html>
