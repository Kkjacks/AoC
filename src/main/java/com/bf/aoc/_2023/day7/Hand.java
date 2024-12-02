package com.bf.aoc._2023.day7;

import lombok.Getter;

import java.util.Arrays;

public class Hand implements Comparable {
    private final Integer[] cards;
    @Getter private final int bid;
    private final Rank rank;

    public Hand(String cardInput, int bid) {
        this.bid = bid;
        char[] chars = cardInput.toCharArray();
        this.cards = new Integer[]{
          convertCard(chars[0]),
          convertCard(chars[1]),
          convertCard(chars[2]),
          convertCard(chars[3]),
          convertCard(chars[4])
        };
        this.rank = Rank.fromCards(cards);
    }

    @Override
    public String toString() {
        return "\n" + Arrays.toString(cards) + " - " + rank;
    }

    private Integer convertCard(char c) {
        return switch (c) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 1;
            case 'T' -> 10;
            default -> Character.getNumericValue(c);
        };
    }

    @Override
    public int compareTo(Object o) {
        Hand other = (Hand) o;
        if (rank != other.rank)
            return rank.compareTo(other.rank);
        if (!cards[0].equals(other.cards[0]))
            return cards[0].compareTo(other.cards[0]);
        if (!cards[1].equals(other.cards[1]))
            return cards[1].compareTo(other.cards[1]);
        if (!cards[2].equals(other.cards[2]))
            return cards[2].compareTo(other.cards[2]);
        if (!cards[3].equals(other.cards[3]))
            return cards[3].compareTo(other.cards[3]);
        if (!cards[4].equals(other.cards[4]))
            return cards[4].compareTo(other.cards[4]);
        return 0;
    }
}
