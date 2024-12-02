package com.bf.aoc._2023.day12;

import com.bf.aoc._2023.Runner2023;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DamagedSpringsRunner extends Runner2023 {
    static {
        DAY=12; TEST=false; PART=2;
    }

    private static final Pattern pattern = Pattern.compile("\\.*(.*)\\.* ((\\d|,)+)");
    private static final Pattern patternPart2 = Pattern.compile("(.*) ((\\d|,)+)");

    public static void main(String[] args) {
        DamagedSpringsRunner r = new DamagedSpringsRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        List<Long> counts = new ArrayList<>();
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) throw new IllegalStateException();
            String springs = matcher.group(1).replaceAll("\\.{2,}", ".");
            List<Integer> damagedGroups = Arrays.stream(matcher.group(2).split(",")).map(Integer::parseInt).collect(Collectors.toList());
            long count = recurse(springs, damagedGroups);
            log.info("{} {} - {}", matcher.group(1), damagedGroups, count);
            counts.add(count);
        }

        return counts.stream().reduce(0L, Long::sum);
    }

    @Override
    public long part2(List<String> lines) {
        List<Long> counts = new ArrayList<>();
        int i=103;
        for (String line : lines) {
            Long start = System.currentTimeMillis();
            Matcher matcher = patternPart2.matcher(line);
            if (!matcher.matches()) throw new IllegalStateException();
            String springs = matcher.group(1);
            springs += "?" + springs + "?" + springs + "?" + springs + "?" + springs;
            springs = springs.replaceAll("\\.{2,}", ".");
            List<Integer> damagedGroups = Arrays.stream(matcher.group(2).split(",")).map(Integer::parseInt).collect(Collectors.toList());
            List<Integer> finalDamagedGroups = new ArrayList<>(damagedGroups);
            finalDamagedGroups.addAll(damagedGroups);
            finalDamagedGroups.addAll(damagedGroups);
            finalDamagedGroups.addAll(damagedGroups);
            finalDamagedGroups.addAll(damagedGroups);
            long count = recurse(springs, finalDamagedGroups);
            Long end = System.currentTimeMillis();
            Long totalSeconds = (end - start) / 1000;
            log.info("{}) {}s {} {} - {}", ++i, totalSeconds, matcher.group(1), damagedGroups, count);
            counts.add(count);
        }

        return counts.stream().reduce(0L, Long::sum);
    }

    private static final Map<Pair<String, List<Integer>>, Long> cache = new HashMap<>();

    private long recurse(String springs, List<Integer> groups) {
        if (springs.isEmpty()) {
            return groups.isEmpty() ? 1 : 0;
        } else if (groups.isEmpty()) {
            return springs.contains("#") ? 0 : 1;
        } else if (!hasSpace(springs, groups)) {
            return 0;
        }

        Pair<String, List<Integer>> key = Pair.of(springs, groups);
        if (cache.containsKey(key))
            return cache.get(key);

        long count;
        char next = springs.charAt(0);
        if (next == '.') {
            count = recurse(springs.substring(1), groups);
        } else if (next == '#') {
            int nextGroup = groups.get(0);
            if (springs.length() < nextGroup) {
                cache.put(key, 0L);
                return 0;
            } else if (springs.length() == nextGroup) {
                count = groups.size() > 1 || springs.contains(".") ? 0 : 1;
                cache.put(key, count);
                return count;
            } else if (springs.substring(0, nextGroup).contains(".")) {
                cache.put(key, 0L);
                return 0;
            } else if (springs.charAt(nextGroup) == '#') {
                cache.put(key, 0L);
                return 0;
            }
            List<Integer> newGroups = new ArrayList<>(groups);
            newGroups.remove(0);
            count = recurse(springs.substring(nextGroup + 1), newGroups);
        } else {
            count = recurse("#" + springs.substring(1), groups);
            count += recurse("." + springs.substring(1), groups);
        }

        cache.put(key, count);

        return count;
    }

    private static boolean hasSpace(String springs, List<Integer> groups) {
        int groupTotal = groups.stream().reduce(0, Integer::sum);
        int springTotal = springs.length();
        int minNeeded = groupTotal + groups.size() - 1;
        if (minNeeded > springTotal) {
            return false;
        }

        int has = springTotal - StringUtils.countMatches(springs, '.');
        if (groupTotal > has) {
            return false;
        }
        return true;
    }

}
