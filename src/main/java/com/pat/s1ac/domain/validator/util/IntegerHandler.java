package com.pat.s1ac.domain.validator.util;

public class IntegerHandler {
    public static boolean isNotZeroOrNaN(double number) {
        return Double.isNaN(number) || number == 0.0;
    }

    public static boolean isNotNaN(double number) {
        return Double.isNaN(number);
    }

    public static boolean isNotGreaterThanZero(double number) {
        return number > 0;
    }

    public static boolean isNotValid(int number) {
        try {
            Integer.valueOf(number);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
