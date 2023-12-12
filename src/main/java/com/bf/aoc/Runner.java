package com.bf.aoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class Runner {
    protected static int DAY = 0;
    protected static boolean TEST = true;
    protected static int PART = 1;
    public static final Logger log = LoggerFactory.getLogger("");

    public static List<String> input() {
        return DataReader.read(TEST, DAY);
    }
    public abstract long part1(List<String> lines);
    public abstract long part2(List<String> lines);

    protected static boolean isPart1() {
        return PART == 1;
    }
}
