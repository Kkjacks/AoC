package com.bf.aoc.day22;

import com.bf.aoc.Runner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SandBricksRunner extends Runner {
    static {
        DAY=22; TEST=false; PART=2;
    }

    public static void main(String[] args) {
        SandBricksRunner r = new SandBricksRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        List<Brick> bricks = new BrickStacker().stackBricks(lines);

        List<Integer> destroyable = new ArrayList<>();
        for (Brick brick : bricks) {
            if (brick.canDestroy()) {
                destroyable.add(Integer.parseInt(brick.name()));
            }
        }
        Collections.sort(destroyable);
        log.info("Destroyable = {}", destroyable);
        return destroyable.size();
    }

    @Override
    public long part2(List<String> lines) {
        List<Brick> bricks = new BrickStacker().stackBricks(lines);
        long damage = 0;
        int index = 1;
        for (Brick brick : bricks) {
            damage += brick.countDamage();
            log.info("{}/{}", index++, bricks.size());
        }

        return damage;
    }
}
