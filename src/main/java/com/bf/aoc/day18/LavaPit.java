package com.bf.aoc.day18;

import com.bf.aoc.day10.Direction;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class LavaPit {
    private final Table<Long, Long, Boolean> pit = HashBasedTable.create();
    final long rows, cols, rStart, cStart;

    public LavaPit(List<Pair<Direction, Long>> digPath) {
        Direction direction;
        Long count;
        long r=0;
        long c=0;
        for (Pair<Direction, Long> dig : digPath) {
            direction = dig.getLeft();
            count = dig.getRight();
            while (count > 0) {
                count--;
                r += direction.rowOffset();
                c += direction.colOffset();
                pit.put(r, c, true);
            }
        }

        rStart = pit.rowKeySet().stream().min(Long::compareTo).get();
        long rEnd = pit.rowKeySet().stream().max(Long::compareTo).get();
        rows = rEnd - rStart + 1;

        cStart = pit.columnKeySet().stream().min(Long::compareTo).get();
        long cEnd = pit.columnKeySet().stream().max(Long::compareTo).get();
        cols = cEnd - cStart + 1;

        for (r = rStart; r < rows; r++) {
            for (c = cStart; c < cols; c++) {
                if (get(r, c) == null) {
                    pit.put(r, c, false);
                }
            }
        }
    }

    public void dig() {
        Table<Long, Long, Boolean> origPit = HashBasedTable.create(pit);
        boolean dig = false;
        for (long r=rStart; r<rows; r++) {
            for (long c=cStart; c<cols; c++) {
                if (origPit.get(r, c)) {
                    // trench start
                    if (!dig && Boolean.TRUE == origPit.get(r - 1, c)) {
                        dig = true;
                    // trench end
                    } else if (dig && Boolean.TRUE == origPit.get(r - 1, c)) {
                        dig = false;
                    }
                }
                if (dig)
                    pit.put(r, c, true);
            }
            dig = false;
        }
    }

    public Long sum() {
        return pit.values().stream().filter(v -> v).count();
    }

    public Boolean get(long row, long col) {
        return pit.get(row, col);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (long r=rStart; r<rows; r++) {
            sb.append(String.format("%03d", r)).append(" ");
            for (long c=cStart; c<cols; c++) {
                sb.append(get(r, c) ? "X" : ".");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
