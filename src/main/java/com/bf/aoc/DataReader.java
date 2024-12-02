package com.bf.aoc;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataReader {
    @SneakyThrows
    public static List<String> read(boolean test, int year, int day) {
        return test ? test(year, day) : data(year, day);
    }

    @SneakyThrows
    public static List<String> read(String path) {
        return Files.readAllLines(Paths.get(path));
    }

    @SneakyThrows
    public static List<String> test(int day) {
        return test(2023, day);
    }

    @SneakyThrows
    public static List<String> data(int day) {
        return data(2023, day);
    }

    @SneakyThrows
    public static List<String> test(int year, int day) {
        return Files.readAllLines(Paths.get("./src/main/resources/year" + year + "/day" + day(year, day) + "/test.txt"));
    }

    @SneakyThrows
    public static List<String> data(int year, int day) {
        return Files.readAllLines(Paths.get("./src/main/resources/year" + year + "/day" + day(year, day) + "/data.txt"));
    }

    private static String day(int year, int day) {
        return day > 9 && year != 2023 ? day + "" : "0" + day;
    }

}
