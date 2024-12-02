package com.bf.aoc._2023.day14;

import com.bf.aoc.Runner;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RockLoadRunner extends Runner {
    static {
        DAY=14; TEST=false; PART=2;
    }

    public static void main(String[] args) {
        RockLoadRunner r = new RockLoadRunner(input());
        log.info("Result: {}", isPart1() ? r.part1(null) : r.part2(null));
    }

    private static final Character ROCK = 'O';
    private static final Character WALL = '#';
    private static final Character EMPTY = '.';
    private final int numRows, numCols;
    private final Table<Integer, Integer, Character> table = HashBasedTable.create();

    public RockLoadRunner(List<String> lines) {
        numRows = lines.size();
        numCols = lines.get(0).length();
        for (int row = 0; row< numRows; row++) {
            for (int col = 0; col< numCols; col++) {
                table.put(row, col, lines.get(row).charAt(col));
            }
        }
    }

    @Override
    public long part1(List<String> lines) {
        rollNorth();
        return countWeight();
    }

    @Override
    public long part2(List<String> lines) {
        List<Integer> weightLoop = new ArrayList<>();
        int loopStart = establishLoop(weightLoop);

        int additionalRotations = 1000000000 - loopStart;
        int weightIndex = additionalRotations % weightLoop.size();
        return weightLoop.get(weightIndex);
    }

    private Integer establishLoop(List<Integer> resultLoop) {
        Map<String, Integer> tableToWeightCache = new HashMap<>();
        String key = generateKey();
        int rotations = 0;
        while (!tableToWeightCache.containsKey(key)) {
            int weight = countWeight();
            tableToWeightCache.put(key, weight);
            rotate();
            rotations++;
            key = generateKey();
        }
        tableToWeightCache.put(key, countWeight());
        log.info("Loop detected after {} rotations", rotations);


        int weight = countWeight();
        resultLoop.add(weight);

        // Iterate until original key is found again
        String nextKey;
        while (true) {
            rotate();
            nextKey = generateKey();
            weight = countWeight();
            if (key.equals(nextKey)) {
                log.info("Loop size is {}", resultLoop.size());
                log.info("Loop = {}", resultLoop);
                return rotations;
            } else {
                resultLoop.add(weight);
            }
        }
    }

    private void rotate() {
        rollNorth();
        rollWest();
        rollSouth();
        rollEast();
    }

    private void rollNorth() {
        for (int row = 0; row<numRows; row++) {
            Map<Integer, Character> rowEntries = table.column(row);
            for (int col = 0; col<numCols; col++) {
                Integer emptySpace = findNext(EMPTY, rowEntries, col);
                if (emptySpace != null) {
                    Integer rockColumn = findNext(ROCK, rowEntries, emptySpace+1);
                    if (rockColumn != null) {
                        rowEntries.put(emptySpace, ROCK);
                        rowEntries.put(rockColumn, EMPTY);
                    }
                }
            }
        }
    }

    private void rollSouth() {
        for (int row = numRows; row>=0; row--) {
            Map<Integer, Character> rowEntries = table.column(row);
            for (int col = numCols; col>=0; col--) {
                Integer emptySpace = findPrev(EMPTY, rowEntries, col);
                if (emptySpace != null) {
                    Integer rockColumn = findPrev(ROCK, rowEntries, emptySpace-1);
                    if (rockColumn != null) {
                        rowEntries.put(emptySpace, ROCK);
                        rowEntries.put(rockColumn, EMPTY);
                    }
                }
            }
        }
    }

    private void rollWest() {
        for (int col = 0; col<numCols; col++) {
            Map<Integer, Character> colEntries = table.row(col);
            for (int row = 0; row<this.numRows; row++) {
                Integer emptySpace = findNext(EMPTY, colEntries, row);
                if (emptySpace != null) {
                    Integer rockSpace = findNext(ROCK, colEntries, emptySpace+1);
                    if (rockSpace != null) {
                        colEntries.put(emptySpace, ROCK);
                        colEntries.put(rockSpace, EMPTY);
                    }
                }
            }
        }
    }

    private void rollEast() {
        for (int col=numCols; col>=0; col--) {
            Map<Integer, Character> colEntries = table.row(col);
            for (int row=numRows; row>=0; row--) {
                Integer emptySpace = findPrev(EMPTY, colEntries, row);
                if (emptySpace != null) {
                    Integer rockSpace = findPrev(ROCK, colEntries, emptySpace-1);
                    if (rockSpace != null) {
                        colEntries.put(emptySpace, ROCK);
                        colEntries.put(rockSpace, EMPTY);
                    }
                }
            }
        }
    }

    private Integer findNext(Character find, Map<Integer, Character> map, int start) {
        while (start<numCols) {
            Character next = map.get(start);
            if (next == find) {
                return start;
            } else if (next == WALL) {
                return null;
            }
            start++;
        }
        return null;
    }

    private Integer findPrev(Character find, Map<Integer, Character> map, int start) {
        while (start >= 0) {
            Character next = map.get(start);
            if (next == find) {
                return start;
            } else if (next == WALL) {
                return null;
            }
            start--;
        }
        return null;
    }

    private Integer countWeight() {
        int total = 0;
        int weight = numCols;
        for (int row = 0; row< numRows; row++) {
            for (int col = 0; col< numCols; col++) {
                if (ROCK == table.get(row, col)) {
                    total += weight;
                }
            }
            weight--;
        }
        return total;
    }

    private String generateKey() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row< numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                sb.append(table.get(row, col));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
