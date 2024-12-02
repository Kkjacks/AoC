package com.bf.aoc._2023.day19;

public class Result {
    public static final Result ACCEPT = new Result("A");
    public static final Result REJECT = new Result("R");
    private static final String A = "A";
    private static final String R = "R";
    private final String result;

    public Result(String result) {
        this.result = result;
    }

    public boolean isAccept() {
        return A.equals(result);
    }

    public boolean isReject() {
        return R.equals(result);
    }

    public String getNextFunctionKey() {
        return result;
    }
}
