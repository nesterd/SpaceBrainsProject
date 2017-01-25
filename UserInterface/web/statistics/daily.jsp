<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="database.PersonPageRankEntity" %>
<%@ page import="database.HibernateUtil" %>
<%@ page import="java.sql.Date" %>
<%@ page import="userinterface.MyDate" %>
<%@ page import="userinterface.Content" %>
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
        <span class="menu_selected">Ежедневная статистика</span>
    </div>
    <div class="border_left"></div>
    <div class="border_top">
        <br>
        <span class="top_label">Ежедневная статистика</span>
    </div>
    <div class="right">
        <form id="params">
            <%
                String begindate = (request.getParameter("begindate"));
                String enddate = (request.getParameter("enddate"));
                Date begin = MyDate.valueOf(begindate);
                Date end = MyDate.valueOf(enddate);
            %>
            <table class="table_params">
                <tr>
                    <td>
                        Дата начала
                        <div><input type="text" name="begindate" class="tcal" value=<%=begin==null ? "" : begin.toString()%> /></div>
                    </td>
                    <td>
                        Дата конца
                        <div><input type="text" name="enddate" class="tcal" value=<%=end==null ? "" : end.toString()%> /></div>
                    </td>
                    <%
                        Session ORMSession = HibernateUtil.getSessionFactory().openSession();
                        Query query = ORMSession.createQuery("FROM SitesEntity");
                        List sites = query.list();
                        ORMSession.close();
                        int siteId = 0;
                    %>
                    <td>
                        Сайт
                        <div>
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
                        </div>
                    </td>
                    <%
                        ORMSession = HibernateUtil.getSessionFactory().openSession();
                        query = ORMSession.createQuery("FROM PersonsEntity ");
                        List persons = query.list();
                        ORMSession.close();
                        int personId = 0;
                    %>
                    <td>
                        Персона
                        <div>
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
                        </div>
                    </td>
                </tr>
            </table>
            <table class="table_params">
                <tr>
                    <td>
                        <button class="button" type="submit" form="params">Сформировать</button>
                    </td>
                    <td>
                        <select size="1" name="variant">
                            <option <%=String.valueOf("0").equals(request.getParameter("variant")) ? "selected":""%> value="0">Таблица</option>
                            <option <%=String.valueOf("1").equals(request.getParameter("variant")) ? "selected":""%> value="1">Диаграма популярности личностей</option>
                            <option <%=String.valueOf("2").equals(request.getParameter("variant")) ? "selected":""%> value="2">Диаграма популярности сайтов</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
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
        <span>Общее количество: <%=result.size()%></span>
        <% if (result.size() > 0) {%>
            <% if (String.valueOf("1").equals(request.getParameter("variant"))) {%>
                <%= Content.returnChart(result, "persons")%>
            <%} else if (String.valueOf("2").equals(request.getParameter("variant"))) {%>
                <%= Content.returnChart(result, "sites")%>
            <%} else {%>
                <%= Content.returnTable("daily", result, pagesCount, currentPage, begin, end, String.valueOf(siteId), String.valueOf(personId))%>
            <%}%>
        <%}%>
    </div>
    <div class="bottom">
        <div class="header_footer">&copy; 2017 Space Brains Project</div>
    </div>
</body>
</html>
