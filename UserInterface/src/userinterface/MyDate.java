package userinterface;

import java.sql.Date;

/**
 * Created by oldfox on 22.01.17.
 */
public class MyDate {
    public static Date valueOf(String s) {
        final int YEAR_LENGTH = 4;
        final int MONTH_LENGTH = 2;
        final int DAY_LENGTH = 2;
        final int MAX_MONTH = 12;
        final int MAX_DAY = 31;
        int firstDash;
        int secondDash;
        Date d = null;
        if (s == null) {
            return d;
        }

        firstDash = s.indexOf('-');
        secondDash = s.indexOf('-', firstDash + 1);

        if ((firstDash > 0) && (secondDash > 0) && (secondDash < s.length() - 1)) {
            String yyyy = s.substring(0, firstDash);
            String mm = s.substring(firstDash + 1, secondDash);
            String dd = s.substring(secondDash + 1);
            if (yyyy.length() == YEAR_LENGTH &&
                    (mm.length() >= 1 && mm.length() <= MONTH_LENGTH) &&
                    (dd.length() >= 1 && dd.length() <= DAY_LENGTH)) {
                int year;
                int month;
                int day;
                try {
                    year = Integer.parseInt(yyyy);
                    month = Integer.parseInt(mm);
                    day = Integer.parseInt(dd);
                }
                catch (NumberFormatException e) {
                    return d;
                }

                if ((month >= 1 && month <= MAX_MONTH) && (day >= 1 && day <= MAX_DAY)) {
                    d = new Date(year - 1900, month - 1, day);
                }
            }
        }

        return d;

    }
}
