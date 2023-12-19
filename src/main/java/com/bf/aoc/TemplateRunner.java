package com.bf.aoc;

import java.util.List;
import java.util.regex.Pattern;

public class TemplateRunner extends com.bf.aoc.Runner {
    static {
        DAY=0; TEST=true; PART=1;
    }
    private static final Pattern pattern = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)");

    public static void main(String[] args) {
        TemplateRunner r = new TemplateRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
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
