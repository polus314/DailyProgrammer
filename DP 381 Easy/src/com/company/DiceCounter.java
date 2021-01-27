package com.company;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of how many of each die value has been rolled, and can tell you the max score possible out of those rolls.
 */
public class DiceCounter {
    public static int MAX_DIE_VALUE = 6;    // Used for validating inputs
    private Map<Long, Long> scores;
    private long maxScore;

    /**
     * Default constructor, initializes data members.
     */
    public DiceCounter() {
        maxScore = 0;
        scores = new HashMap<>();
    }

    /**
     * Allows you to record a die value, which will update the internal count and maximum score.
     * @param value The value shown on the die
     */
    public void inputDieValue(long value) {
        if (value > MAX_DIE_VALUE || value < 0) {
            return;
        }

        // Need to check if entry exists, cannot assign a null to a long
        long count = scores.containsKey(value) ? scores.get(value) + 1 : 1;
        scores.put(value, count);
        if (count * value > maxScore) {
            maxScore = count * value;
        }
    }

    /**
     * Gets the maximum score for the set of dice that have been input.
     * @return Max score
     */
    public long getMaxScore() {
        return maxScore;
    }
}
