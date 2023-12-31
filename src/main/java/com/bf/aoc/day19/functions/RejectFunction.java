package com.bf.aoc.day19.functions;

import com.bf.aoc.day19.Part;
import com.bf.aoc.day19.PartRange;
import com.bf.aoc.day19.Result;

public class RejectFunction implements RangeFunction {
    public static RangeFunction INSTANCE = new RejectFunction();

    private RejectFunction(){}

    @Override
    public Result apply(Part part) {
        return Result.REJECT;
    }

    @Override
    public PartRange calcRange(PartRange partRange) {
        PartRange negRange = new PartRange(partRange);
        partRange.setValid(false);
        return negRange;
    }

    @Override
    public Result getAcceptResult() {
        return Result.REJECT;
    }
}
