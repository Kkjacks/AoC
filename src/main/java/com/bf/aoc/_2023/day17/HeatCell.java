package com.bf.aoc._2023.day17;

import java.util.Objects;

public class HeatCell {
    final int row, col, cost;

    public HeatCell(int row, int col, int cost) {
        this.row = row;
        this.col = col;
        this.cost = cost;
    }

    public String toString() {
        return "["+row+","+col+"]"+"("+cost+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeatCell heatCell)) return false;
        return row == heatCell.row && col == heatCell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
