package userinterface;

import database.PersonpagerankEntity;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by oldfox on 25.01.17.
 */
public class Content {

    public static String returnTable(String type, List result, int pagesCount, int currentPage, Date begin, Date end, String siteId, String personId) {

        System.out.println(result.toString());
        String table = "<table class=\"table_content\">\n" +
                "            <tr>\n" +
                "                <th class=\"th_content\">\n" +
                "                </th>\n" +
                "                <th class=\"th_content\">\n" +
                "                    Персона\n" +
                "                </th>\n" +
                "                <th class=\"th_content\">\n" +
                "                    Ссылка\n" +
                "                </th>\n" +
                "                <th class=\"th_content\">\n" +
                "                    Ранг\n" +
                "                </th>\n" +
                "            </tr>\n";
        for (int i = 18 * (currentPage - 1) + 1; i <= 18 * currentPage; i++) {

            if (i > result.size()) {
                continue;
            }
            Object element = result.get(i-1);
            System.out.println(element.getClass().getName());
            table +="<tr>\n" +
                "       <td class=\"td_content\">\n" +
            i +
                "       </td>\n" +
                "       <td class=\"td_content\">\n" +
                    ((Object[])element)[1] +
                "       </td>\n" +
                "       <td class=\"td_content\">\n" +
                    ((Object[])element)[2] +
                "       </td>\n" +
                "       <td class=\"td_content\">\n" +
                    ((Object[])element)[0] +
                "       </td>\n" +
                "    </tr>\n";
        }
        table +=    "</table>\n" +
                "   Страницы:\n";
        if (currentPage > 6) {
            if (type.equals("common")) {
                table += "<a href=\"common.jsp?page=" + 1 + "\">" + 1 + "</a>";
            } else {
                table += "<a href=\"daily.jsp?page=" + 1 +
                        "&begindate=" + (begin==null ? "" : begin.toString()) +
                        "&enddate=" + (end==null ? "" : end.toString()) +
                        "&siteId=" + siteId +
                        "&personId=" + personId + "\">" + 1 + "</a>";
            }
            table += "...";
        }
        for (int i = (currentPage > 6 && currentPage <= pagesCount)? currentPage - 5 : 1; i <= ((currentPage <= pagesCount - 6)? currentPage + 5 : pagesCount); i++) {
            if (currentPage == i) {
                table += i + "&nbsp;";
            } else {
                if (type.equals("common")) {
                    table += "<a href=\"common.jsp?page=" + i + "\">" + i + "</a>&nbsp;";
                } else {
                    table += "<a href=\"daily.jsp?page=" + i +
                            "&begindate=" + (begin==null ? "" : begin.toString()) +
                            "&enddate=" + (end==null ? "" : end.toString()) +
                            "&siteId=" + siteId +
                            "&personId=" + personId + "\">" + i + "</a>&nbsp;";
                }
            }
        }
        if (currentPage < pagesCount - 5) {
            table += "...";
            if (type.equals("common")) {
                table += "<a href=\"common.jsp?page=" + pagesCount + "\">" + pagesCount + "</a>";
            } else {
                table += "<a href=\"daily.jsp?page=" + pagesCount +
                        "&begindate=" + (begin==null ? "" : begin.toString()) +
                        "&enddate=" + (end==null ? "" : end.toString()) +
                        "&siteId=" + siteId +
                        "&personId=" + personId + "\">" + pagesCount + "</a>";
            }
        }

        return table;
    }

    public static String returnChart(List result, String variant) {

        HashSet<String> parameter1 = new HashSet<>();
        HashMap<String, Integer> parameter2 = new HashMap<>();

        for (Object element:result) {
            String name = "";
            if (variant.equals("persons")) {
                name = String.valueOf(((Object[]) element)[1]);
            } else {
                name = String.valueOf(((Object[]) element)[2]);
            }
            parameter1.add(name);
            Integer mapElement = parameter2.get(name);
            if (mapElement == null) {
                parameter2.put(name, Integer.parseInt(String.valueOf(((Object[]) element)[0])) + 1);
            } else {
                parameter2.put(name, mapElement + Integer.parseInt(String.valueOf(((Object[]) element)[0])) + 1);
            }
        }

        String chart = "<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                "        <script type=\"text/javascript\">\n" +
                "            google.charts.load('current', {'packages':['corechart']});\n" +
                "            google.charts.setOnLoadCallback(drawChart);\n" +
                "            function drawChart() {\n" +
                "                var data = google.visualization.arrayToDataTable([\n" +
                "['" + variant + "', 'Популярность']";

        String contentString = "";

        for (String element:parameter1) {
            contentString += ",\n ['" + element + "', " + parameter2.get(element) + "]";
        }
        chart += contentString + "]);\n" +
                "\n" +
                "                var options = {\n";
        if (variant.equals("persons")) {
            chart += "title: 'Диаграмма популярности личностей',\n";
        } else {
            chart += "title: 'Диаграмма популярности сайтов',\n";
        }
        chart += "                 pieHole: 0.3,\n" +
                "                };\n" +
                "\n" +
                "                var chart = new google.visualization.PieChart(document.getElementById('donutchart'));\n" +
                "\n" +
                "                chart.draw(data, options);\n" +
                "            }\n" +
                "        </script>\n" +
                "        <div id=\"donutchart\" style=\"width: 70%; height: 80%; left: 25%; position: relative;\"></div>";

        return chart;
    }
}
