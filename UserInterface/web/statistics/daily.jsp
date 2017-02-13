<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
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
    <% if (session.getAttribute("status") == null || !session.getAttribute("status").equals("Вы авторизованы")) {
            String site = new String("../index.jsp");
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", site);
        }
    %>
    <div class="top">
        <a href="../index.jsp"><div class="header_footer">Space Brains</div></a>
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
                        HibernateUtil.closeSessionFactory();
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
                        HibernateUtil.closeSessionFactory();
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
                            <option <%=String.valueOf("1").equals(request.getParameter("variant")) ? "selected":""%> value="1">Диаграмма популярности личностей</option>
                            <option <%=String.valueOf("2").equals(request.getParameter("variant")) ? "selected":""%> value="2">Диаграмма популярности сайтов</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
        <%  List result;
            String queryText = "SELECT SUM(ppr.Rank), pers.Name AS pn, site.name AS sn" +
                    " FROM ratepersons.personpagerank AS ppr" +
                    " LEFT JOIN ratepersons.persons AS pers ON ppr.PersonId = pers.ID" +
                    " LEFT JOIN ratepersons.pages AS page ON ppr.PageId = page.ID" +
                    " LEFT JOIN ratepersons.sites AS site ON page.SiteID = site.ID" +
                    " WHERE page.FoundDateTime > 0";
            if (begin != null) {
                queryText += " AND page.FoundDateTime >= '" + begin + "'";
            }
            if (end != null) {
                queryText += " AND page.FoundDateTime <= '" + end + "'";
            }
            if (siteId > 0 ) {
                queryText += " AND site.id = " + siteId;
            }
            if (personId > 0 ) {
                queryText += " AND pers.id = " + personId;
            }
            queryText += " GROUP BY pn, sn";
            ORMSession = HibernateUtil.getSessionFactory().openSession();
            query = ORMSession.createSQLQuery(queryText);
            result = query.list();
            ORMSession.close();
            HibernateUtil.closeSessionFactory();

            int pagesCount = result.size() / 18 + 1;
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
                <%= Content.returnTable("daily", result, pagesCount, currentPage, begin, end, String.valueOf(siteId), String.valueOf(personId))%>
            <%}%>
        <%}%>
    </div>
    <div class="bottom">
        <div class="header_footer">&copy; 2017 Space Brains Project</div>
    </div>
</body>
</html>
