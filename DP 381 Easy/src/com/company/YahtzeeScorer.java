package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Solves the puzzles for Daily Programmer #381 from 2019-11-11. Description text:
 *
 * The game of Yahtzee is played by rolling five 6-sided dice, and scoring the results in a number of ways. You are
 * given a Yahtzee dice roll, represented as a sorted list of 5 integers, each of which is between 1 and 6 inclusive.
 * Your task is to find the maximum possible score for this roll in the upper section of the Yahtzee score card. Here's
 * what that means.
 *
 * For the purpose of this challenge, the upper section of Yahtzee gives you six possible ways to score a roll. 1 times
 * the number of 1's in the roll, 2 times the number of 2's, 3 times the number of 3's, and so on up to 6 times the
 * number of 6's. For instance, consider the roll [2, 3, 5, 5, 6]. If you scored this as 1's, the score would be 0,
 * since there are no 1's in the roll. If you scored it as 2's, the score would be 2, since there's one 2 in the roll.
 * Scoring the roll in each of the six ways gives you the six possible scores:
 *
 * 0 2 3 0 10 6
 *
 * The maximum here is 10 (2x5), so your result should be 10.
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/dv0231/20191111_challenge_381_easy_yahtzee_upper_section/
 */
public class YahtzeeScorer {
    private static final String currentFolder = "C:\\Users\\johnp\\IdeaProjects\\Daily Programmer\\DP 381 Easy\\";

    /**
     * Sovles the main puzzle of finding the maximum value for a set of 5 dice that are 6-sided.
     */
    private void doMainChallenge() {
        System.out.println("Yahtzee Scorer!");
        File testDataFile = new File(currentFolder + "TestData.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(testDataFile));
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                DiceCounter counter = new DiceCounter();
                String[] dice = currentLine.split(" ", 5);
                for (String strDieValue : dice) {
                    int intDieValue = Integer.parseInt(strDieValue);
                    counter.inputDieValue(intDieValue);
                }
                System.out.println(counter.getMaxScore());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Solves the bonus challenge where the "die" can now have a much higher number of sides and there can be many
     * thousands of them rolled. Essentially, making your solution scalable.
     */
    private void doBonusChallenge() {
        System.out.println("Bonus Challenge");
        File testDataFile = new File(currentFolder + "ChallengeData.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(testDataFile));
            String currentLine;
            DiceCounter counter = new DiceCounter();
            DiceCounter.MAX_DIE_VALUE = 999999999;

            while ((currentLine = br.readLine()) != null) {
                counter.inputDieValue(Integer.parseInt(currentLine));
            }
            System.out.println(counter.getMaxScore());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main entry point for the program. Calls methods on a DiceCounter object for actual logic.
     * @param args Unused, input is from a text file
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        YahtzeeScorer scorer = new YahtzeeScorer();
        scorer.doMainChallenge();
        scorer.doBonusChallenge();
        System.out.println("Runtime in ms: " + (System.currentTimeMillis() - startTime));
    }
}
