package com.bf.aoc.day19;

import com.bf.aoc.day19.functions.AcceptFunction;
import com.bf.aoc.day19.functions.GoToFunction;
import com.bf.aoc.day19.functions.GreaterThanFunction;
import com.bf.aoc.day19.functions.LessThanFunction;
import com.bf.aoc.day19.functions.RangeFunction;
import com.bf.aoc.day19.functions.RejectFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Workflows {
    private static final Pattern functionPattern = Pattern.compile("([xmas])([><])(\\d+):(\\w+)");
    private final Map<String, List<RangeFunction>> workflowMap = new HashMap<>();

    public boolean evaluatePart(Part part) {
        return evaluatePart("in", part);
    }

    private boolean evaluatePart(String key, Part part) {
        for (Function<Part, Result> func : get(key)) {
            Result result = func.apply(part);
            if (result != null) {
                if (result.isAccept()) return true;
                if (result.isReject()) return false;
                return evaluatePart(result.getNextFunctionKey(), part);
            }
        }
        throw new IllegalStateException();
    }

    public List<PartRange> evaluateRange() {
        return evaluateRange("in", new PartRange());
    }

    private List<PartRange> evaluateRange(String key, PartRange partRange) {
        List<PartRange> results = new ArrayList<>();
        for (RangeFunction func : get(key)) {
            PartRange negRange = func.calcRange(partRange);
            if (partRange.isValid()) {
                Result acceptResult = func.getAcceptResult();
                if (acceptResult.isAccept())
                    results.add(partRange);
                else if (!acceptResult.isReject())
                    results.addAll(evaluateRange(acceptResult.getNextFunctionKey(), partRange));
            }

            if (!negRange.isValid()) {
                return results;
            }
            partRange = negRange;
        }
        return results;
    }

    public List<RangeFunction> get(String key) {
        return workflowMap.get(key);
    }

    public void addWorkflow(String key, String input) {
        List<RangeFunction> functions = new ArrayList<>();
        for (String step : input.split(",")) {
            if (!step.contains(":")) {
                if (step.equals("A")) functions.add(AcceptFunction.INSTANCE);
                else if (step.equals("R")) functions.add(RejectFunction.INSTANCE);
                else functions.add(new GoToFunction(step));
            } else {
                Matcher matcher = functionPattern.matcher(step);
                if (!matcher.matches()) throw new IllegalArgumentException(step);
                PartType partType = PartType.from(matcher.group(1));
                String compare = matcher.group(2);
                Integer val = Integer.parseInt(matcher.group(3));
                String result = matcher.group(4);
                if (compare.equals(">")) {
                    functions.add(new GreaterThanFunction(partType, val, result));
                } else if (step.contains("<")) {
                    functions.add(new LessThanFunction(partType, val, result));
                } else {
                    throw new IllegalStateException();
                }
            }
        }
        workflowMap.put(key, functions);

    }
}
