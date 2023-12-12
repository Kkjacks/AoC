package com.bf.aoc.day2;

import com.bf.aoc.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoneGame {
    public static final Logger log = LoggerFactory.getLogger("");

    public static void main(String[] args) throws Exception {
        int result = 0;
        for (String line : DataReader.data(2)) {
            String[] gameInput = line.split(": ");
            String game = gameInput[0];
            String input = gameInput[1];
            int gameNumber = Integer.parseInt(game.split(" ")[1]);
            GameResult gameResult = evaluateGame(input.split("; "));
            log.info("{}: {} ({} * {} * {})", gameNumber, gameResult.getPower(), gameResult.red, gameResult.green, gameResult.blue);
            result += gameResult.getPower();
        }
        log.info("Result: {}", result);
    }

    private static GameResult evaluateGame(String[] input) {
        GameResult gameResult = new GameResult();
        for (String round : input) {
            for (String colorNum : round.split(", ")) {
                String[] numberAndColor = colorNum.split(" ");
                int number = Integer.parseInt(numberAndColor[0]);
                String color = numberAndColor[1];
                switch (color) {
                    case "red":
                        gameResult.setRed(number);
                        break;
                    case "green":
                        gameResult.setGreen(number);
                        break;
                    case "blue":
                        gameResult.setBlue(number);
                        break;
                }
            }
        }
        return gameResult;
    }

    private static class GameResult {
        private int red, green, blue = 0;

        public void setRed(int red) {
            if (red > this.red)
                this.red = red;
        }

        public void setGreen(int green) {
            if (green > this.green)
                this.green = green;
        }

        public void setBlue(int blue) {
            if (blue > this.blue)
                this.blue = blue;
        }

        public int getPower() {
            return red * green * blue;
        }
    }
}
