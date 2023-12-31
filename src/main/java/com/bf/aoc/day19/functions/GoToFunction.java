package com.bf.aoc.day19.functions;

import com.bf.aoc.day19.Part;
import com.bf.aoc.day19.PartRange;
import com.bf.aoc.day19.Result;

public class GoToFunction implements RangeFunction {
    private final Result result;

    public GoToFunction(String result) {
        this.result = new Result(result);
    }

    @Override
    public Result apply(Part part) {
        return result;
    }

    @Override
    public PartRange calcRange(PartRange partRange) {
        PartRange negRange = new PartRange(partRange);
        negRange.setValid(false);
        return negRange;
    }

    @Override
    public Result getAcceptResult() {
        return result;
    }
}
