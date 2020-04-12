package org.dhrumil.course.coursefinder.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


  private static final SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");

  public static boolean isCurrentDateInBetween(String start, String end) {
    Date currentDate = new Date();

    if (start == null || start.isEmpty() || end == null
        || end.isEmpty()) {
      return false;
    }

    try {
      return currentDate.after(dateFormate.parse(start))
          && currentDate.before(dateFormate.parse(end));
    } catch (ParseException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean isCurrentDateInBetween(Date targetDate, Date start, Date end) {
    return targetDate.after(start) && targetDate.before(end);

  }

  public static boolean checkIfDateIsInLastThreeMonth(String targetDateStr) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -3);

    try {
      Date currentDate = new Date();
      Date threeMonthBackDate = dateFormate.parse(dateFormate.format(cal.getTime()));
      Date targetDate = dateFormate.parse(targetDateStr);
      return isCurrentDateInBetween(targetDate, threeMonthBackDate, currentDate);
    } catch (ParseException e) {
      e.printStackTrace();
      return false;
    }
  }

}
