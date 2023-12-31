package com.bf.aoc.day19.functions;

import com.bf.aoc.day19.Part;
import com.bf.aoc.day19.PartRange;
import com.bf.aoc.day19.Result;

public class AcceptFunction implements RangeFunction {
    public static RangeFunction INSTANCE = new AcceptFunction();

    private AcceptFunction(){}

    @Override
    public Result apply(Part part) {
        return Result.ACCEPT;
    }

    @Override
    public PartRange calcRange(PartRange partRange) {
        PartRange negRange = new PartRange(partRange);
        negRange.setValid(false);
        return negRange;
    }

    @Override
    public Result getAcceptResult() {
        return Result.ACCEPT;
    }
}
