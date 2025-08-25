package com.pat.inventory.domain.shared.util;

public class IntHandler {
    public static boolean isZeroOrNaN(double number) {
        return Double.isNaN(number) || number == 0.0;
    }

    public static boolean isNaN(double number) {
        return Double.isNaN(number);
    }

    public static boolean isGreaterThanZero(double number) {
        return number > 0;
    }

    public static boolean isValid(int number) {
        try {
            Integer.valueOf(number);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
