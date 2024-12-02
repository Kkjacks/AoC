package com.bf.aoc._2023.day16;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;

public class EnergyGrid {
    private final Table<Integer, Integer, Cell> grid = HashBasedTable.create();
    final int rows, cols;

    public EnergyGrid(List<String> lines) {
        this.rows = lines.size()+1;
        this.cols = lines.get(0).length()+1;
        int row = 0;
        for (String line : lines) {
            row++;
            int col = 0;
            for (char c : line.toCharArray()) {
                grid.put(row, ++col, new Cell(c));
            }
        }
    }

    public Cell getCell(Coordinates coordinates) {
        return grid.get(coordinates.row, coordinates.col);
    }

    public Long countEnergizedCells() {
        return grid.values().stream().filter(Cell::isEnergized).count();
    }

    public void reset() {
        grid.values().forEach(Cell::reset);
    }

    public String printEnergyGrid(Coordinates coordinates) {
        StringBuilder sb = new StringBuilder();
        for (int r=1; r<rows; r++) {
            for (int c=1; c<cols; c++) {
                if (coordinates.row == r && coordinates.col == c) {
                    sb.append("X");
                } else {
                    //noinspection DataFlowIssue
                    sb.append(grid.get(r, c).energized ? "#" : ".");
                }
            }
            sb.append("\n");
        }
        return sb.append("\n").toString();
    }
}
