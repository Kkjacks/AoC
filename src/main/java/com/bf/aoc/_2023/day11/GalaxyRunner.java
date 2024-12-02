package com.bf.aoc._2023.day11;

import com.bf.aoc._2023.Runner2023;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalaxyRunner extends Runner2023 {
    static {
        DAY = 11; TEST = false; PART = 2;
    }

    public static void main(String[] args) {
        GalaxyRunner r = new GalaxyRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        GalaxyMap galaxyMap = new GalaxyMap(lines, 1);
        return run(galaxyMap);
    }

    @Override
    public long part2(List<String> lines) {
        GalaxyMap galaxyMap = new GalaxyMap(lines, 2);
        return run(galaxyMap);
    }

    private static long run(GalaxyMap galaxyMap) {
        log.info("{}", galaxyMap);

        List<Space> galaxies = galaxyMap.getGalaxies();
        List<Pair<Space, Space>> galaxyPairs = new ArrayList<>();
        while (!galaxies.isEmpty()) {
            Space galaxy1 = galaxies.remove(0);
            for (Space galaxy2 : galaxies) {
                galaxyPairs.add(Pair.of(galaxy1, galaxy2));
            }
        }

        Map<Pair<Space, Space>, Long> distances = new HashMap<>();
        for (Pair<Space, Space> galaxyPair : galaxyPairs) {
            Space galaxy1 = galaxyPair.getLeft();
            Space galaxy2 = galaxyPair.getRight();

            int row1 = galaxy1.getRow();
            int row2 = galaxy2.getRow();
            long rowMoves = 0;
            for (int row=Math.min(row1, row2); row<Math.max(row1, row2); row++) {
                rowMoves += galaxyMap.get(row, galaxy1.getCol()).getRowSize();
            }

            int col1 = galaxy1.getCol();
            int col2 = galaxy2.getCol();
            long colMoves = 0;
            for (int col=Math.min(col1, col2); col<Math.max(col1, col2); col++) {
                colMoves += galaxyMap.get(galaxy1.getRow(), col).getColSize();
            }
            distances.put(galaxyPair, rowMoves + colMoves);
        }

        return distances.values().stream().reduce(0L, Long::sum);
    }
}
