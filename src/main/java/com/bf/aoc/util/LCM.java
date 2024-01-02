package com.bf.aoc.util;

import java.util.Collection;
import java.util.Iterator;

public class LCM {

    /**
     * Calculate the Least Common Multiple
     */
    public static long calculate(Collection<Long> numbers) {
        Iterator<Long> iter = numbers.iterator();
        long lcm = iter.next();
        while (iter.hasNext()) {
            lcm = lcm(lcm, iter.next());
        }
        return lcm;
    }

    private static long lcm(long number1, long number2) {
        long higherNumber = Math.max(number1, number2);
        long lowerNumber = Math.min(number1, number2);
        long lcm = higherNumber;
        while (lcm % lowerNumber != 0) {
            lcm += higherNumber;
        }
        return lcm;
    }

}
