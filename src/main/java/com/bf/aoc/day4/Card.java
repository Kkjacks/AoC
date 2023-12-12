package com.bf.aoc.day4;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Card {
    @Getter
    private final int number;
    @Getter
    private int copies = 1;
    private final List<Integer> winningNumbers;
    private final List<Integer> numbers;

    public Card(String cardInput) {
        String[] cardData = cardInput.split(":\\s+");
        number = Integer.parseInt(cardData[0].split("\\s+")[1]);
        String[] input = cardData[1].split("\\s+\\|\\s+");
        winningNumbers = Arrays.stream(input[0].split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        numbers = Arrays.stream(input[1].split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public void copy(int x) {
        copies += x;
    }

    public int getMatches() {
        int matches = 0;
        for (Integer number : numbers) {
            if (winningNumbers.contains(number))
                matches += 1;
        }
        return matches;
    }

    public int getCardValue() {
        int value = 0;
        for (Integer number : numbers) {
            if (winningNumbers.contains(number)) {
                if (value == 0)
                    value = 1;
                else
                    value *= 2;
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return "\nCard " + number + "[" + copies + "]";
//        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
//                .append("value", getCardValue())
//                .append("winningNumbers", winningNumbers)
//                .append("numbers", numbers)
//                .toString();
    }
}
