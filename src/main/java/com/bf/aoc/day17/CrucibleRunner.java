package com.bf.aoc.day17;

import com.bf.aoc.Runner;
import com.bf.aoc.day10.Direction;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class CrucibleRunner extends Runner {
    static {
        DAY=17; TEST=false; PART=2;
    }

    public static void main(String[] args) {
        CrucibleRunner r = new CrucibleRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        return evaluate(new HeatGrid(lines));
    }

    @Override
    public long part2(List<String> lines) {
        return evaluate(new HeatGrid(lines));
    }

    private static int evaluate(HeatGrid grid) {
        HashSet<Path> cache = new HashSet<>();
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(new Path(0, 1, Direction.E, 1), grid.get(0,1).cost, null));
        queue.add(new State(new Path(1, 0, Direction.S, 1), grid.get(1,0).cost, null));
        while (!queue.isEmpty()) {
            State cur = queue.remove();
            if (cache.contains(cur.path)) {
                continue;
            }

            cache.add(cur.path);
            Path path = cur.path;
            if (path.row() == grid.rows - 1 && path.col() == grid.cols - 1 && (isPart1() || cur.path.steps() > 2)) {
                return cur.cost;
            }
            if (isPart1()) cur.addNext(queue, grid);
            else cur.addNextPart2(queue, grid);
        }
        return -1;
    }
}
