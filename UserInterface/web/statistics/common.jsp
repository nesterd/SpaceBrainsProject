<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="userinterface.Content" %>
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
        <span class="menu_selected" style="border-top: 1px solid gray">Общая статистика</span>
        <span class="menu" style="border-bottom: 1px solid gray"><a href=<%= "daily.jsp?page=1&begindate=" + currentDate + "&enddate="  + currentDate%>>Ежедневная статистика</a></span>
    </div>
    <div class="border_left"></div>
    <div class="border_top">
        <br>
        <span class="top_label">Общая статистика</span>
    </div>
    <div class="right">
        <form id="params">
            <table class="table_params">
                <tr>
                    <td>
                        <button class="button" type="submit" form="params">Сформировать</button>
                    </td>
                    <td>
                        <select size="1" name="variant">
                            <option <%=String.valueOf("0").equals(request.getParameter("variant")) ? "selected":""%> value="0">Таблица</option>
                            <option <%=String.valueOf("1").equals(request.getParameter("variant")) ? "selected":""%> value="1">Диаграмма популярности личностей</option>
                            <option <%=String.valueOf("2").equals(request.getParameter("variant")) ? "selected":""%> value="2">Диаграмма популярности сайтов</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
        <%  List result;
            Session ORMSession = HibernateUtil.getSessionFactory().openSession();
            Query query = ORMSession.createQuery("FROM PersonPageRankEntity ppr");
            result = query.list();
            session.setAttribute("commonlist", result);
            ORMSession.close();
            HibernateUtil.closeSessionFactory();

            int pagesCount = result.size() / 10 + 1;
            int currentPage;
            if (request.getParameter("page") == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(request.getParameter("page"));
            }
        %>
        <span>Общее количество: <%=result.size()%></span>
        <% if (result.size() > 0) {%>
            <% if (String.valueOf("1").equals(request.getParameter("variant"))) {%>
                <%= Content.returnChart(result, "persons")%>
            <%} else if (String.valueOf("2").equals(request.getParameter("variant"))) {%>
                <%= Content.returnChart(result, "sites")%>
            <%} else {%>
                <%= Content.returnTable("common", result, pagesCount, currentPage, null, null, "", "")%>
            <%}%>
        <%}%>
    </div>
    <div class="bottom">
        <div class="header_footer">&copy; 2017 Space Brains Project</div>
    </div>
</body>
</html>
