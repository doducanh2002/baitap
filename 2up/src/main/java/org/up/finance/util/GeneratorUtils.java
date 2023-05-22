package org.up.finance.util;

import java.text.DecimalFormat;
import java.util.Random;

import static org.up.finance.constant.FinanceConstant.OTPConstant.FOUR_DIGITS_STRING;
import static org.up.finance.constant.FinanceConstant.OTPConstant.FOUR_DIGITS_UPPER_BOUND;

public class GeneratorUtils {
  public static String generateOTP() {
    Random random = new Random();
    return new DecimalFormat(FOUR_DIGITS_STRING).format(random.nextInt(FOUR_DIGITS_UPPER_BOUND));
  }
}
