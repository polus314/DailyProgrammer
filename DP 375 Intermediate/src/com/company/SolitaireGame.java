package com.company;

import java.util.ArrayList;
import java.util.List;

public class SolitaireGame {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        System.out.println("Sample Inputs");
	    printResult("0100110");
	    printResult("01001100111");
	    printResult("100001100101000");

        System.out.println("\nChallenge Inputs");
        printResult("0100110");
        printResult("001011011101001001000");
        printResult("1010010101001011011001011101111");
        printResult("1101110110000001010111011100110");

        System.out.println("\nBonus Input");
        String bonusInput = "010111111111100100101000100110111000101111001001011011000011000";
	    printResult(bonusInput);
	    String superlongInput = "";
	    for (int i = 0; i < 7; i++) {
	        superlongInput = superlongInput + bonusInput;
        }
	    printResult(superlongInput);

        System.out.println("Time in ms: " + (System.currentTimeMillis() - startTime));
    }

    private static void printResult(String cardLayout) {
        SolitaireAI ai = new SolitaireAI();
        List<Integer> moves = ai.solveCardLayout(cardLayout);

        if (moves == null) {
            System.out.println("No solution found");
            return;
        }

        System.out.println(moves);
    }
}
