package com.bf.aoc;

import com.bf.aoc._2024.Runner2024;

import java.util.List;
import java.util.regex.Pattern;

public class TemplateRunner extends Runner2024 {
    static {
        DAY=0; TEST=true; PART=1;
    }
    private static final Pattern pattern = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)");

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TemplateRunner r = new TemplateRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
        long endTime = System.currentTimeMillis();
        log.info("{} sec", String.format("%5.3f", (endTime - startTime) / 1000f));
    }

    @Override
    public long part1(List<String> lines) {
        return 1;
    }

    @Override
    public long part2(List<String> lines) {
        return 1;
    }

}
