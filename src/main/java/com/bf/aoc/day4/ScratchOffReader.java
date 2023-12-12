package com.bf.aoc.day4;

import com.bf.aoc.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScratchOffReader {
    private static final Logger log = LoggerFactory.getLogger("");

    public static void main(String[] args) {
        log.info("Result: {}", part2());
    }

    public static int part1() {
        int totalValue = 0;
        for (String cardInput : DataReader.data(4)) {
            totalValue += new Card(cardInput).getCardValue();
        }
        return totalValue;
    }

    public static int part2() {
        Map<Integer, Card> cardMap = new HashMap<>();
        List<Card> cards = new ArrayList<>();
        for (String cardInput : DataReader.data(4)) {
            Card card = new Card(cardInput);
            cardMap.put(card.getNumber(), card);
            cards.add(card);
        }
        for (Card card : cards) {
            int copies = card.getCopies();
            for (int i = 1; i <= card.getMatches(); i++) {
                int index = cards.indexOf(card);
                Card cardToCopy = cardMap.get(index+i+1);
                cardToCopy.copy(copies);
            }
            log.info("{} - {}", card.getNumber(), cards);
        }

        int total = 0;
        for (Card card : cards) {
            total += card.getCopies();
        }

        log.info("{}", cards);
        return total;
    }
}
