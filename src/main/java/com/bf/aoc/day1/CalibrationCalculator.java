package com.bf.aoc.day1;

import com.bf.aoc.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalibrationCalculator {
    public static final Logger log = LoggerFactory.getLogger("");

    public static void main(String[] args) {
        int total = 0;
        for (String input : DataReader.data(1)) {
            int digit1 = findFirstDigit(input);
            int digit2 = findSecondDigit(input);
            int val = Integer.parseInt("" + digit1 + digit2);
            total += val;
        }

        log.info("Result: {}", total);
    }

    private static Integer findFirstDigit(String input) {
        for (int i = 0; i < input.length(); i++) {
            String x = input.substring(i);
            if (Character.isDigit(x.charAt(0)))
                return Character.getNumericValue(x.charAt(0));
            Integer digit = textToInt(x);
            if (digit != null)
                return digit;
        }
        System.out.println("1st Not Found: " + input);
        throw new IllegalArgumentException(input);
    }

    private static Integer findSecondDigit(String input) {
        for (int i = input.length() - 1; i >= 0; i--) {
            String x = input.substring(i);
            if (Character.isDigit(x.charAt(0)))
                return Character.getNumericValue(x.charAt(0));
            Integer digit = textToInt(x);
            if (digit != null)
                return digit;
        }
        System.out.println("2nd Not Found: " + input);
        throw new IllegalArgumentException(input);
    }

    private static Integer textToInt(String x) {
        if (x.startsWith("zero")) return 0;
        if (x.startsWith("one")) return 1;
        if (x.startsWith("two")) return 2;
        if (x.startsWith("three")) return 3;
        if (x.startsWith("four")) return 4;
        if (x.startsWith("five")) return 5;
        if (x.startsWith("six")) return 6;
        if (x.startsWith("seven")) return 7;
        if (x.startsWith("eight")) return 8;
        if (x.startsWith("nine")) return 9;
        return null;
    }
}
