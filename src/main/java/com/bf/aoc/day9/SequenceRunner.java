package com.bf.aoc.day9;

import com.bf.aoc.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SequenceRunner {
    private static final Logger log = LoggerFactory.getLogger("");

    public static void main(String[] args) {
        log.info("Result: {}", part2(DataReader.data(9)));
    }

    public static long part1(List<String> lines) {
        Cell first = null;
        Cell cell = null;
        List<Integer> nextValues = new ArrayList<>();
        for (String line : lines) {
            for (String input : line.split(" ")) {
                int number = Integer.parseInt(input);
                if (cell == null) {
                    cell = new Cell(number);
                    first = cell;
                } else {
                    cell = cell.next(number);
                }
            }
            // log.info("Calculating children");
            first.calculateChild();
            //log.info("{}\n", first.print());

            // log.info("Calculating next");
            Cell next = first.calculateNext();
            // log.info("{}", next.value);
            
            nextValues.add(next.value);
            first = null;
            cell = null;
        }

        return nextValues.stream().reduce(0, Integer::sum);
    }

    public static long part2(List<String> lines) {
        Cell first = null;
        Cell cell = null;
        List<Integer> nextValues = new ArrayList<>();
        for (String line : lines) {
            List<String> numbers = Arrays.asList(line.split(" "));
            Collections.reverse(numbers);
            for (String input : numbers) {
                int number = Integer.parseInt(input);
                if (cell == null) {
                    cell = new Cell(number);
                    first = cell;
                } else {
                    cell = cell.next(number);
                }
            }
            // log.info("Calculating children");
            first.calculateChild();
            //log.info("{}\n", first.print());

            // log.info("Calculating next");
            Cell next = first.calculateNext();
            // log.info("{}", next.value);

            nextValues.add(next.value);
            first = null;
            cell = null;
        }

        return nextValues.stream().reduce(0, Integer::sum);
    }

}
