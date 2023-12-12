package com.bf.aoc.day6;

import com.bf.aoc.DataReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class BoatRaceRunner {
    private static final Logger log = LoggerFactory.getLogger("");

    public static void main(String[] args) {
        //log.info("Result: {}", part1(DataReader.test(6)));
        log.info("Result: {}", part2(DataReader.data(6)));
    }

    public static long part2(List<String> lines) {
        String line = StringUtils.removeStart(lines.remove(0), "Time:").trim();
        List<Long> times = Arrays.stream(line.split(" +"))
                .map(Long::parseLong)
                .toList();

        line = StringUtils.removeStart(lines.remove(0), "Distance:").trim();
        List<Long> distances = Arrays.stream(line.split(" +"))
                .map(Long::parseLong)
                .toList();

        long answer = 1;
        for (int i=0; i<times.size(); i++) {
            long options = new BoatRace(times.get(i), distances.get(i)).evaluateOptions();
            log.info("Race {}: {}", i, options);
            answer *= options;
        }
        return answer;
    }
}
