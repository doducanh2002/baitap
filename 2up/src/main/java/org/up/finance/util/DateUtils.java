package org.up.finance.util;

import java.time.LocalDate;

public class DateUtils {
  private DateUtils() {

  }

  public static String getCurrentDateString() {
    return LocalDate.now().toString();
  }
}
