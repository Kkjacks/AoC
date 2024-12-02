package com.bf.aoc._2024.day01;

import com.bf.aoc._2024.Runner2024;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 extends Runner2024 {
    static {
        DAY=1; TEST=false; PART=2;
    }
    private static final Pattern pattern = Pattern.compile("(\\d+)\\s+(\\d+)");

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Day1 r = new Day1();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
        long endTime = System.currentTimeMillis();
        log.info("{} sec", String.format("%5.3f", (endTime - startTime) / 1000f));
    }

    @Override
    public long part1(List<String> lines) {
        Pair<List<Integer>, List<Integer>> locationListPair = createLists(lines);
        List<Integer> one = locationListPair.getLeft();
        List<Integer> two = locationListPair.getRight();
        List<Integer> distances = new ArrayList<>();
        for (int i = 0; i < one.size(); i++) {
            distances.add(Math.abs(one.get(i) - two.get(i)));
        }
        return distances.stream().reduce(0, Integer::sum);
    }

    @Override
    public long part2(List<String> lines) {
        Pair<List<Integer>, Map<Integer, Integer>> locationListPair = createListToMap(lines);
        List<Integer> one = locationListPair.getLeft();
        Map<Integer, Integer> two = locationListPair.getRight();
        List<Integer> dups = new ArrayList<>();
        for (Integer loc : one) {
            Integer dupCount = two.get(loc);
            if (dupCount == null) {
                dupCount = 0;
            }
            dups.add(loc * dupCount);
        }
        return dups.stream().reduce(0, Integer::sum);
    }

    private static Pair<List<Integer>, List<Integer>> createLists(List<String> lines) {
        List<Integer> one = new ArrayList<>();
        List<Integer> two = new ArrayList<>();
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            if (!m.matches()) throw new IllegalArgumentException(line);
            one.add(Integer.parseInt(m.group(1)));
            two.add(Integer.parseInt(m.group(2)));
        }
        Collections.sort(one);
        Collections.sort(two);
        return Pair.of(one, two);
    }

    private static Pair<List<Integer>, Map<Integer, Integer>> createListToMap(List<String> lines) {
        List<Integer> one = new ArrayList<>();
        Map<Integer, Integer> two = new HashMap<>();
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            if (!m.matches()) throw new IllegalArgumentException(line);
            one.add(Integer.parseInt(m.group(1)));
            Integer loc = Integer.parseInt(m.group(2));
            if (!two.containsKey(loc)) {
                two.put(loc, 1);
            } else {
                two.put(loc, two.get(loc) + 1);
            }
        }
        return Pair.of(one, two);
    }

}
