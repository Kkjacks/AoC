package com.bf.aoc;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataReader {
    @SneakyThrows
    public static List<String> read(boolean test, int day) {
        return test ? test(day) : data(day);
    }

    @SneakyThrows
    public static List<String> read(String path) {
        return Files.readAllLines(Paths.get(path));
    }

    @SneakyThrows
    public static List<String> test(int day) {
        return Files.readAllLines(Paths.get("./src/main/resources/day" + day + "/test.txt"));
    }

    @SneakyThrows
    public static List<String> data(int day) {
        return Files.readAllLines(Paths.get("./src/main/resources/day" + day + "/data.txt"));
    }

}
