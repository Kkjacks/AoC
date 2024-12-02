package com.bf.aoc._2023.day7;

import com.bf.aoc.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class CamelCardsRunner {
    private static final Logger log = LoggerFactory.getLogger("");
    private static final Pattern pattern = Pattern.compile("(\\w+) (\\d+)");

    public static void main(String[] args) {
        log.info("Result: {}", run(DataReader.data(7)));
    }

    public static long run(List<String> lines) {
        List<Hand> hands = new ArrayList<>();
        while (!lines.isEmpty()) {
            String line = lines.remove(0);
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                log.error("Match not found: {}", line);
                exit(1);
            }
            hands.add(new Hand(matcher.group(1), Integer.parseInt(matcher.group(2))));
        }
        Collections.sort(hands);
        log.info("{}", hands);

        int value = 0;
        for (int i=0; i<hands.size();i++) {
            Hand hand = hands.get(i);
            value += hand.getBid() * (i + 1);
        }
        return value;
    }

}
