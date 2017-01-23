<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="database.PersonPageRankEntity" %>
<%@ page import="database.HibernateUtil" %>
<%@ page import="java.sql.Date" %>
<%@ page import="userinterface.MyDate" %>
<%@ page import="database.SitesEntity" %>
<%@ page import="database.PersonsEntity" %>
<html>
<head>
    <title>Space Brains</title>
    <link rel="stylesheet" type="text/css" href="../stylesheet.css">
    <link rel="stylesheet" type="text/css" href="../calendar/tcal.css" />
    <script type="text/javascript" src="../calendar/tcal.js"></script>
</head>
<body>
    <div class="top">
        <div class="header_footer">Space Brains</div>
    </div>
    <div class="left">
        <span class="menu"><a href="common.jsp">Общая статистика</a></span>
        <span class="menu_selected" type="button">Ежедневная статистика</span>
    </div>
    <div class="border_left"></div>
    <div class="border_top"></div>
    <div class="right">
        <form id="params">
            <%
                String begindate = (request.getParameter("begindate"));
                String enddate = (request.getParameter("enddate"));
                Date begin = MyDate.valueOf(begindate);
                Date end = MyDate.valueOf(enddate);
            %>
            <span><input type="text" name="begindate" class="tcal" value=<%=begin==null ? "" : begin.toString()%> /></span>
            <span><input type="text" name="enddate" class="tcal" value=<%=end==null ? "" : end.toString()%> /></span>
            <%
                Session ORMSession = HibernateUtil.getSessionFactory().openSession();
                Query query = ORMSession.createQuery("FROM SitesEntity");
                List sites = query.list();
                ORMSession.close();
                int siteId = 0;
            %>
            <select size="1" name="siteId">
                <option value=0>Выберите сайт</option>
                <% for (Object site : sites) {
                    int id = ((SitesEntity)site).getId();
                    if (String.valueOf(id).equals(request.getParameter("siteId"))) {
                        siteId = id;
                    }
                %>
                <option <%=String.valueOf(id).equals(request.getParameter("siteId")) ? "selected":""%> value=<%=id%>><%=((SitesEntity)site).getName()%></option>
                <%}%>
            </select>
            <%
                ORMSession = HibernateUtil.getSessionFactory().openSession();
                query = ORMSession.createQuery("FROM PersonsEntity ");
                List persons = query.list();
                ORMSession.close();
                int personId = 0;
            %>
            <select size="1" name="personId">
                <option value=0>Выберите персону</option>
                <% for (Object person : persons) {
                    int id = ((PersonsEntity)person).getId();
                    if (String.valueOf(id).equals(request.getParameter("personId"))) {
                        personId = id;
                    }
                %>
                <option <%=String.valueOf(id).equals(request.getParameter("personId")) ? "selected":""%> value=<%=id%>><%=((PersonsEntity)person).getName()%></option>
                <%}%>
            </select>
        </form>
        <button class="button" type="submit" form="params">Сформировать</button>
        <%  List result;
            if (session.getAttribute("dailylist") != null
                    && session.getAttribute("begin") == begin
                    && session.getAttribute("end") == end
                    && session.getAttribute("siteId") == (Object) siteId
                    && session.getAttribute("personId") == (Object) personId) {
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
                if (siteId > 0 ) {
                    queryText += " AND pagesByPageId.sitesById.id = :siteId";
                }
                if (personId > 0 ) {
                    queryText += " AND personsByPersonId.id = :personId";
                }
                ORMSession = HibernateUtil.getSessionFactory().openSession();
                query = ORMSession.createQuery(queryText);
                if (begin != null) {
                    query.setParameter("begindate", begin);
                }
                if (end != null) {
                    query.setParameter("enddate", end);
                }
                if (siteId > 0) {
                    query.setParameter("siteId", siteId);
                }
                if (personId > 0 ) {
                    query.setParameter("personId", personId);
                }
                result = query.list();
                session.setAttribute("dailylist", result);
                session.setAttribute("begin", begin);
                session.setAttribute("end", end);
                session.setAttribute("siteId", siteId);
                session.setAttribute("personId", personId);
                ORMSession.close();
            }
            int pagesCount = result.size() / 10 + 1;
            int currentPage;
            if (request.getParameter("page") == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(request.getParameter("page"));
            }%>
        <p>Общее количество: <%=result.size()%></p>
        <table>
            <tr>
                <th>

                </th>
                <th>
                    Персона
                </th>
                <th>
                    Ссылка
                </th>
                <th>
                    Ранг
                </th>
            </tr>
            <% for (int i = 10 * (currentPage - 1) + 1; i <= 10 * currentPage; i++) {
                if (i > result.size()) {
                    continue;
                }
                Object element = result.get(i-1); %>
            <tr>
                <td>
                    <%= i %>
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
        Страницы:
        <% for (int i = 1; i <= pagesCount; i++) {%>
        <% if (currentPage == i) {%>
        <%= i%>
        <%} else {%>
        <a href=<%= "daily.jsp?page=" + i
                + "&begindate=" + (begin==null ? "" : begin.toString())
                + "&enddate=" + (end==null ? "" : end.toString()
                + "&siteId=" + request.getParameter("siteId")
                + "&personId=" + request.getParameter("personId"))%>><%=i%></a>
        <%}
        }%>
    </div>
    <div class="bottom">
        <div class="header_footer">&copy; 2017 Space Brains Project</div>
    </div>
</body>
</html>
