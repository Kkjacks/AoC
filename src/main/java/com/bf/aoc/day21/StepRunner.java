package com.bf.aoc.day21;

import com.bf.aoc.Runner;
import com.bf.aoc.util.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StepRunner extends Runner {
    static {
        DAY=21; TEST=false; PART=2;
    }

    public static void main(String[] args) {
        StepRunner r = new StepRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        Location start = GardenMap.init(lines);
        Set<Location> locations = GardenMap.move(start, TEST ? 6 : 64);
        log.info("{}", GardenMap.print(locations));
        return locations.size();
    }

    @Override
    public long part2(List<String> lines) {
        /*
            This stupid puzzle only works b/c the grid symmetrical with no
            rocks on the center lines (x & y)
         */
        Location start = GardenMap.init(lines);

        Set<Point> allOdds = GardenMap.move(start, 131).stream().map(Location::getPoint).collect(Collectors.toSet());
        GardenMap.resetCache();
        Set<Point> allEvens = GardenMap.move(start, 132).stream().map(Location::getPoint).collect(Collectors.toSet());
        GardenMap.resetCache();
        Set<Point> middleOdds = GardenMap.move(start, 65).stream().map(Location::getPoint).collect(Collectors.toSet());
        GardenMap.resetCache();
        Set<Point> middleEvens = GardenMap.move(start, 64).stream().map(Location::getPoint).collect(Collectors.toSet());
        Set<Point> evenCorners = new HashSet<>(allEvens);
        evenCorners.removeAll(middleEvens);
        Set<Point> oddCorners = new HashSet<>(allOdds);
        oddCorners.removeAll(middleOdds);
        log.info("Evens = {}", allEvens.size());
        log.info(" Odds = {}", allOdds.size());
        log.info("Even Corners = {}", evenCorners.size());
        log.info(" Odd Corners = {}", oddCorners.size());

        long gridLength = (26501365 - 65) / 131;

        long totalOdds = (gridLength + 1) * (gridLength + 1) * allOdds.size();
        long totalEvens = gridLength * gridLength * allEvens.size();
        long totalOddCorners = (gridLength + 1) * oddCorners.size();
        long totalEvenCorners = gridLength * evenCorners.size();

        return totalOdds + totalEvens - totalOddCorners + totalEvenCorners;
    }

}
