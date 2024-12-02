package com.bf.aoc._2023.day13;

import com.bf.aoc._2023.Runner2023;

import java.util.ArrayList;
import java.util.List;

public class MirrorRunner extends Runner2023 {
    static {
        DAY=13; TEST=false; PART=2;
    }

    public static void main(String[] args) {
        MirrorRunner r = new MirrorRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        return run(lines, false);
    }

    @Override
    public long part2(List<String> lines) {
        return run(lines, true);
    }

    private long run(List<String> lines, boolean isPart2) {
        List<Data> dataList = new ArrayList<>();
        List<String> dataInput = new ArrayList<>();
        int i = 1;
        for (String line : lines) {
            if (line.isEmpty()) {
                dataList.add(new Data(dataInput, isPart2));
                dataInput.clear();
            } else {
                dataInput.add(line);
            }
        }
        dataList.add(new Data(dataInput, isPart2));
        int result = 0;
        for (Data data : dataList) {
            Integer vertMirror = data.findVerticalMirror();
            if (vertMirror != null) {
                log.info("{}) {} V", i++, vertMirror);
                result += vertMirror;
            } else {
                Integer horizontalMirror = data.findHorizontalMirror();
                log.info("{}) {} H", i++, horizontalMirror);
                result += (horizontalMirror * 100);
            }
        }

        return result;
    }

}
