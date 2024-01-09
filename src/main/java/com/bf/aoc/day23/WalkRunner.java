package com.bf.aoc.day23;

import com.bf.aoc.Runner;

import java.util.List;

public class WalkRunner extends Runner {
    static {
        DAY=23; TEST=false; PART=2;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        WalkRunner r = new WalkRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
        long endTime = System.currentTimeMillis();
        log.info("{} sec", String.format("%5.3f", (endTime - startTime) / 1000f));
    }

    @Override
    public long part1(List<String> lines) {
        Trail trail = new Trail(lines);
        List<Location> path = trail.walk();
        log.info("{}", trail.printPath(path));
        return path.size();
    }

    @Override
    public long part2(List<String> lines) {
        Intersection start = new Trail(lines).mapIntersections();
        start.visited(true);
        dfs(start, 0);
        return longest;
    }

    private static long longest = 0;
    private static void dfs(Intersection intersection, long steps) {
        if (intersection.end()) {
            if (longest < steps) {
                longest = steps;
                log.info("Longest: {}", longest);
            }
            return;
        }
        for (Intersection connection : intersection.connections()) {
            if (!connection.visited()) {
                connection.visited(true);
                dfs(connection, steps + intersection.getSteps(connection));
                connection.visited(false);
            }
        }
    }
}
