package com.bf.aoc._2023.day13;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;
import java.util.Map;

public class Data {
    private final int rows, cols;
    private final Table<Integer, Integer, Character> table = HashBasedTable.create();
    boolean part2;

    public Data(List<String> lines, boolean isPart2) {
        part2 = isPart2;
        rows = lines.size();
        cols = lines.get(0).length();
        int row = 0;
        for (String line : lines) {
            for (int col=0; col<line.length(); col++) {
                table.put(row, col, line.charAt(col));
            }
            row++;
        }
    }

    public Integer findVerticalMirror() {
        for (int col=0; col<cols-1; col++) {
            if (testColumn(col)) {
                return col+1;
            }
        }
        return null;
    }

    private boolean testColumn(int col) {
        int col1 = col;
        int col2 = col+1;
        boolean smudged = !part2;
        while (col1>=0 && col2<cols) {
            Map<Integer, Character> rows1 = table.column(col1);
            Map<Integer, Character> rows2 = table.column(col2);
            for (int r=0; r<rows; r++) {
                if (!(rows1.get(r) == rows2.get(r))) {
                    if (smudged)
                        return false;
                    smudged = true;
                }
            }
            col1--;
            col2++;
        }
        return smudged;
    }

    public Integer findHorizontalMirror() {
        for (int row=0; row<rows-1; row++) {
            if (testRow(row)) {
                return row+1;
            }
        }
        return null;
    }

    private boolean testRow(int row) {
        int row1 = row;
        int row2 = row+1;
        boolean smudged = !part2;
        while (row1>=0 && row2<rows) {
            Map<Integer, Character> cols1 = table.row(row1);
            Map<Integer, Character> cols2 = table.row(row2);
            for (int c=0; c<cols; c++) {
                if (!(cols1.get(c) == cols2.get(c))) {
                    if (smudged)
                        return false;
                    smudged = true;
                }
            }
            row1--;
            row2++;
        }
        return smudged;
    }

}
