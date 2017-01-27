package userinterface;

import database.PersonPageRankEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by oldfox on 25.01.17.
 */
public class Content {

    public static String returnTable(String type, List result, int pagesCount, int currentPage, Date begin, Date end, String siteId, String personId) {

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
        for (int i = 10 * (currentPage - 1) + 1; i <= 10 * currentPage; i++) {

            if (i > result.size()) {
                continue;
            }
            Object element = result.get(i-1);
            table +="<tr>\n" +
                "       <td class=\"td_content\">\n" +
            i +
                "       </td>\n" +
                "       <td class=\"td_content\">\n" +
            ((PersonPageRankEntity) element).getPersonsByPersonId().getName() +
                "       </td>\n" +
                "       <td class=\"td_content\">\n" +
            ((PersonPageRankEntity) element).getPagesByPageId().getUrl() +
                "       </td>\n" +
                "       <td class=\"td_content\">\n" +
            ((PersonPageRankEntity) element).getRank() +
                "       </td>\n" +
                "    </tr>\n";
        }
        table +=    "</table>\n" +
                "   Страницы:\n";
        for (int i = 1; i <= pagesCount; i++) {
            if (currentPage == i) {
                table += i;
            } else {
                if (type.equals("common")) {
                    table += "<a href=\"common.jsp?page=" + i + "\">" + i + "</a>";
                } else {
                    table += "<a href=\"daily.jsp?page=" + i +
                            "&begindate=" + (begin==null ? "" : begin.toString()) +
                            "&enddate=" + (end==null ? "" : end.toString()) +
                            "&siteId=" + siteId +
                            "&personId=" + personId + "\">" + i + "</a>";
                }
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
                name = ((PersonPageRankEntity) element).getPersonsByPersonId().getName();
            } else {
                name = ((PersonPageRankEntity) element).getPagesByPageId().getSitesById().getName();
            }
            parameter1.add(name);
            Integer mapElement = parameter2.get(name);
            if (mapElement == null) {
                parameter2.put(name, 1);
            } else {
                parameter2.put(name, mapElement + 1);
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
