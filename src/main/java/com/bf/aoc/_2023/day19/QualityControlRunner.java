package com.bf.aoc._2023.day19;

import com.bf.aoc._2023.Runner2023;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QualityControlRunner extends Runner2023 {
    static {
        DAY=19; TEST=false; PART=2;
    }
    private static final Pattern workflowPattern = Pattern.compile("(\\w+)\\{(.+)\\}");
    private static final Pattern partPattern = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)\\}");

    public static void main(String[] args) {
        QualityControlRunner r = new QualityControlRunner();
        log.info("Result: {}", isPart1() ? r.part1(input()) : r.part2(input()));
    }

    @Override
    public long part1(List<String> lines) {
        Workflows workflows = new Workflows();
        while (true) {
            String line = lines.remove(0);
            if (line.isEmpty()) break;
            Matcher matcher = workflowPattern.matcher(line);
            if (!matcher.matches()) throw new IllegalArgumentException(line);
            workflows.addWorkflow(matcher.group(1), matcher.group(2));
        }

        List<Part> parts = new ArrayList<>();
        for (String line : lines) {
            Matcher matcher = partPattern.matcher(line);
            if (!matcher.matches()) throw new IllegalArgumentException(line);
            parts.add(new Part(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4))
            ));
        }

        return parts.stream().filter(workflows::evaluatePart)
                .map(Part::total)
                .reduce(0L, Long::sum);
    }

    @Override
    public long part2(List<String> lines) {
        Workflows workflows = new Workflows();
        while (true) {
            String line = lines.remove(0);
            if (line.isEmpty()) break;
            Matcher matcher = workflowPattern.matcher(line);
            if (!matcher.matches()) throw new IllegalArgumentException(line);
            workflows.addWorkflow(matcher.group(1), matcher.group(2));
        }

        List<PartRange> ranges = workflows.evaluateRange();

        long result = 0;
        for (PartRange range : ranges) {
            result += range.acceptableCombinations();
        }


        return result;
    }

}
