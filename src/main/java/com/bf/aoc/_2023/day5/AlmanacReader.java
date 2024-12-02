package com.bf.aoc._2023.day5;

import com.bf.aoc.DataReader;
import org.apache.commons.lang3.NumberRange;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class AlmanacReader {
    private static final Logger log = LoggerFactory.getLogger("");
    private static final Pattern pattern = Pattern.compile("(\\d+) (\\d+) (\\d+)");

    public static void main(String[] args) {
        log.info("Result: {}", part2(DataReader.data(5)));
    }

    public static long part1(List<String> lines) {
        List<Long> seeds = Arrays.stream(StringUtils.removeStart(lines.remove(0), "seeds: ").split(" "))
                .map(Long::parseLong)
                .toList();

        Map<Range<Long>, Long> seedToSoil = buildRanges(lines);
        Map<Range<Long>, Long> soilToFertilizer = buildRanges(lines);
        Map<Range<Long>, Long> fertilizerToWater = buildRanges(lines);
        Map<Range<Long>, Long> waterToLight = buildRanges(lines);
        Map<Range<Long>, Long> lightToTemp = buildRanges(lines);
        Map<Range<Long>, Long> tempToHumid = buildRanges(lines);
        Map<Range<Long>, Long> humidToLocation = buildRanges(lines);

        List<Long> locations = new ArrayList<>();
        for (Long seed : seeds) {
            long soil = calcDestination(seedToSoil, seed);
            long fert = calcDestination(soilToFertilizer , soil);
            long water = calcDestination(fertilizerToWater , fert);
            long light = calcDestination(waterToLight , water);
            long temp = calcDestination(lightToTemp , light);
            long humid = calcDestination(tempToHumid , temp);
            long location = calcDestination(humidToLocation , humid);
            locations.add(location);
        }

        return Collections.min(locations);
    }

    public static long part2(List<String> lines) {
        List<Long> seedData = Arrays.stream(StringUtils.removeStart(lines.remove(0), "seeds: ").split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Range<Long>> seedRanges = new ArrayList<>();
        long start, end;
        while (!seedData.isEmpty()) {
            start = seedData.remove(0);
            end = start + seedData.remove(0);
            seedRanges.add(new NumberRange<>(start, end, null));
        }

        Map<Range<Long>, Long> seedToSoil = buildRanges(lines);
        Map<Range<Long>, Long> soilToFertilizer = buildRanges(lines);
        Map<Range<Long>, Long> fertilizerToWater = buildRanges(lines);
        Map<Range<Long>, Long> waterToLight = buildRanges(lines);
        Map<Range<Long>, Long> lightToTemp = buildRanges(lines);
        Map<Range<Long>, Long> tempToHumid = buildRanges(lines);
        Map<Range<Long>, Long> humidToLocation = buildRanges(lines);

        long minLocation = Long.MAX_VALUE;
        for (Range<Long> seedRange : seedRanges) {
            for (long seed = seedRange.getMinimum(); seed <= seedRange.getMaximum(); seed++) {
                long soil = calcDestination(seedToSoil, seed);
                long fert = calcDestination(soilToFertilizer , soil);
                long water = calcDestination(fertilizerToWater , fert);
                long light = calcDestination(waterToLight , water);
                long temp = calcDestination(lightToTemp , light);
                long humid = calcDestination(tempToHumid , temp);
                long location = calcDestination(humidToLocation , humid);
                if (location < minLocation) {
                    log.info("seed {} -> {} -- {}", seed, location, seedRange);
                    minLocation = location;
                }
            }
        }

        return minLocation;
    }

    private static Long calcDestination(Map<Range<Long>, Long> ranges, Long src) {
        for (Map.Entry<Range<Long>, Long> entry : ranges.entrySet()) {
            Range<Long> range = entry.getKey();
            if (range.contains(src)) {
                long srcStart = range.getMinimum();
                long dest = entry.getValue();
                long diff = dest - srcStart;
                return src + diff;
            }
        }
        return src;
    }

    private static Map<Range<Long>, Long> buildRanges(List<String> lines) {
        Map<Range<Long>, Long> rangeMap = new HashMap<>();
        nextSection(lines);
        while (!lines.isEmpty() && !lines.get(0).isEmpty()) {
            String line = lines.remove(0);
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                log.error("Match not found: {}", line);
                exit(1);
            }
            long dest = Long.parseLong(matcher.group(1));
            long src = Long.parseLong(matcher.group(2));
            long r = Long.parseLong(matcher.group(3));
            Range<Long> range = new NumberRange<>(src, src+r-1, null);
            rangeMap.put(range, dest);
        }
        return rangeMap;
    }

    private static void nextSection(List<String> lines) {
        lines.remove(0);
        lines.remove(0);
    }
}
