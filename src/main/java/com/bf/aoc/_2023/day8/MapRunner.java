package com.bf.aoc._2023.day8;

import com.bf.aoc.DataReader;
import com.bf.aoc.util.LCM;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapRunner {
    private static final Logger log = LoggerFactory.getLogger("");
    private static final Pattern pattern = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)");

    public static void main(String[] args) {
        log.info("Result: {}", run(DataReader.data(8)));
    }

    public static long run(List<String> lines) {
        char[] steps = lines.remove(0).toCharArray();
        lines.remove(0);
        Map<String, Pair<String, String>> map = new HashMap<>();
        Matcher matcher;

        List<String> nodes = new ArrayList<>();
        while (!lines.isEmpty()) {
            String line = lines.remove(0);
            matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalStateException("Line not matched: " + line);
            }
            if (matcher.group(1).endsWith("A"))
                nodes.add(matcher.group(1));
            map.put(matcher.group(1), Pair.of(matcher.group(2), matcher.group(3)));
        }

        List<Long> lcmList = new ArrayList<>();
        for (String node : nodes) {
            lcmList.add(findLoop(map, steps, node));
        }
        log.info("{}", lcmList);
        return LCM.calculate(lcmList);
    }

    private static long findLoop(Map<String, Pair<String, String>> map, char[] steps, String start) {
        String node = start;
        List<String> currentPath = new ArrayList<>();
        while(true) {
            for (char step : steps) {
                node = nextNode(map, node, step);
                currentPath.add(node);
                if (node.endsWith("Z")) {
                    return currentPath.size();
                }
            }
        }
    }

    private static String nextNode(Map<String, Pair<String, String>> map, String node, char step) {
        return step == 'L' ? map.get(node).getLeft() : map.get(node).getRight();
    }
}
