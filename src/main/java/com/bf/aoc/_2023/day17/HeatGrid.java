package com.bf.aoc._2023.day17;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;

public class HeatGrid {
    private final Table<Integer, Integer, HeatCell> grid = HashBasedTable.create();
    final int rows, cols;

    public HeatGrid(List<String> lines) {
        this.rows = lines.size();
        this.cols = lines.get(0).length();
        int row = -1;
        for (String line : lines) {
            row++;
            for (int col=0; col<line.length(); col++) {
                grid.put(row, col,  new HeatCell(row, col, Character.getNumericValue(line.charAt(col))));
            }
        }
    }

    public HeatCell get(int row, int col) {
        return grid.get(row, col);
    }
}
