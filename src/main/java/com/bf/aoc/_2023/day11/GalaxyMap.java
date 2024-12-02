package com.bf.aoc._2023.day11;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GalaxyMap {
    private static final Logger log = LoggerFactory.getLogger("");
    public static int solution;
    private final Table<Integer, Integer, Space> map = HashBasedTable.create();
    @Getter private final List<Space> galaxies = new ArrayList<>();

    public GalaxyMap(List<String> lines, int solution) {
        GalaxyMap.solution = solution;
        int maxCol = lines.get(0).length() - 1;
        int row = -1;
        for (String line : lines) {
            int col = -1;
            row++;
            boolean isEmptyRow = line.chars().mapToObj(i->(char)i).allMatch(x -> '.' == x);
            for (char c : line.toCharArray()) {
                col++;
                if (c == '.') {
                    Space space = Space.empty(row, col);
                    if (isEmptyRow)
                        space.expandRowOfEmptySpace();
                    map.put(row, col, space);
                }
                else {
                    Space galaxy = Space.galaxy(row, col);
                    galaxies.add(galaxy);
                    map.put(row, col, galaxy);
                }
            }
        }

        for (int i=0; i<=maxCol; i++) {
            Map<Integer, Space> column = map.columnMap().get(i);
            if (column.values().stream().allMatch(Space::isSpace)) {
                column.values().forEach(Space::expandColumnOfEmptySpace);
            }
        }
    }

    public Space get(int row, int col) {
        return map.get(row, col);
    }

    @Override
    public String toString() {
        String result = "";
        for (Map.Entry<Integer, Map<Integer, Space>> rows : map.rowMap().entrySet()) {
            result += rows.getValue().values().stream().map(Space::toString).reduce(String::concat).get() + "\n";
        }
        return result;
    }
}
