package com.bf.aoc.day10;

import com.bf.aoc.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PipeRunner {
    private static final int day = 10;
    private static final Logger log = LoggerFactory.getLogger("");

    public static void main(String[] args) {
        log.info("Result: {}", part2(DataReader.data(day)));
    }

    public static long part1(List<String> lines) {
        Pipes pipes = new Pipes(lines);
        log.info("Start is {}{}", pipes.getStart(), pipes.getStart().printLocation());
        List<Coordinate> path = pipes.findPath();
        log.info("{} - {}", path.size(), path);

        if (path.size() % 2 == 0)
            return path.size() / 2;
        else
            return path.size() / 2 + 1;
    }

    public static long part2(List<String> lines) {
        Pipes pipes = new Pipes(lines);
        pipes.findPath();
        pipes.getMap().values().forEach(Coordinate::clean);
        log.info("{}", pipes);
        pipes.convertStartToPipe();
        return pipes.countInsideCoordinates();
    }
}
