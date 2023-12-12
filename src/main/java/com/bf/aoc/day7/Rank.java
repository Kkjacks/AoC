package com.bf.aoc.day7;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Rank {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_KIND,
    FULL_HOUSE,
    FOUR_OF_KIND,
    FIVE_OF_KIND;

    public static Rank fromCards(Integer... cards) {
        Map<Integer, Integer> cardMap = new HashMap<>();
        for (Integer card : cards) {
            cardMap.merge(card, 1, Integer::sum);
        }
        Integer jokers = cardMap.get(1);
        if (jokers != null && jokers != 5) {
            cardMap.remove(1);
            Map.Entry<Integer, Integer> bestEntry = null;
            for (Map.Entry<Integer, Integer> entry : cardMap.entrySet()) {
                if (bestEntry == null || entry.getValue() > bestEntry.getValue()) {
                    bestEntry = entry;
                }
            }
            cardMap.put(bestEntry.getKey(), bestEntry.getValue() + jokers);
        }

        int bestMatch;
        switch (cardMap.size()) {
            case 1:
                return FIVE_OF_KIND;
            case 2:
                bestMatch = cardMap.values().stream().max(Integer::compareTo).get();
                if (bestMatch == 4) {
                    return FOUR_OF_KIND;
                } else {
                    return FULL_HOUSE;
                }
            case 3:
                bestMatch = cardMap.values().stream().max(Integer::compareTo).get();
                if (bestMatch == 3) {
                    return THREE_OF_KIND;
                } else if (bestMatch == 2) {
                    return TWO_PAIR;
                }
                throw new IllegalStateException("Case 3: " + Arrays.toString(cards));
            case 4:
                return ONE_PAIR;
            case 5:
                return HIGH_CARD;
            default:
                throw new IllegalStateException("Invalid cardMap size: " + cardMap.size());
        }
    }
}
