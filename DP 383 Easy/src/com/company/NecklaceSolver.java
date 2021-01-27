package com.company;

import java.io.*;
import java.util.*;

/**
 * Solves the puzzles for Daily Programmer #383 from 2020-03-09. Description text:
 *
 * Imagine a necklace with lettered beads that can slide along the string. Here's an example image. In this example,
 * you could take the N off NICOLE and slide it around to the other end to make ICOLEN. Do it again to get COLENI, and
 * so on. For the purpose of today's challenge, we'll say that the strings "nicole", "icolen", and "coleni" describe the
 * same necklace.
 *
 * Generally, two strings describe the same necklace if you can remove some number of letters from the beginning of one,
 * attach them to the end in their original ordering, and get the other string. Reordering the letters in some other way
 * does not, in general, produce a string that describes the same necklace.
 *
 * Write a function that returns whether two strings describe the same necklace.
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/ffxabb/20200309_challenge_383_easy_necklace_matching/
 */
public class NecklaceSolver {

    private static final String currentFolder = "C:\\Users\\johnp\\IdeaProjects\\DP 383 Easy\\";

    /**
     * Main challenge is to determine if two necklaces are the same.
     */
    public void doMainChallenge() {
        File testDataFile = new File(currentFolder + "TestData0.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(testDataFile));
            String currentLine;

            // One pair of necklaces per line
            while ((currentLine = br.readLine()) != null) {
                String[] necklaces = currentLine.split(" ", 2);
                printWhetherSame(necklaces[0], necklaces[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bonus 1 is determine how many times a word will repeat itself by cycling through all letters.
     */
    public void doBonusChallenge1() {
        System.out.println("\nBonus Challenge 1");
        File testDataFile = new File(currentFolder + "TestData1.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(testDataFile));
            String currentLine;

            // One necklace per line
            while ((currentLine = br.readLine()) != null) {
                printRepeatCount(currentLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Bonus 2 is finding 4 necklaces in a large list that are all the same.
     */
    public void doBonusChallenge2() {
        System.out.println("\nBonus Challenge 2");
        File testDataFile2 = new File(currentFolder + "TestData2.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(testDataFile2));
            String currentLine;
            List<String> allNecklaces = new ArrayList<>();

            // One necklace per line
            while ((currentLine = br.readLine()) != null) {
                allNecklaces.add(currentLine);
            }
            findFourMatchingNecklaces(allNecklaces);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check this string to see if there are repeats when cycling through the letters.
     * @param necklace String of letters
     * @return Number of repeats (including original order)
     */
    private int checkForRepeats(String necklace) {
        // Using 1 because we want shifting all letters to the end to count, empty string should also get a 1
        int repeats = 1;

        // No optimizations yet, just brute force
        for (int i = 1; i < necklace.length(); i++) {
            String temp = necklace.substring(i) + necklace.substring(0, i);
            if (temp.equals(necklace))
            {
                repeats++;
            }
        }
        return repeats;
    }

    /**
     * Finds 4 matching necklaces in the given list. Work is done on another thread, results are written to standard
     * output.
     * @param allNecklaces List of necklaces to search through
     */
    private void findFourMatchingNecklaces(List<String> allNecklaces) {
        // Determine what lengths to use for splitting up work
        Set<Integer> lengths = new HashSet<>();
        for (String necklace : allNecklaces) {
            lengths.add(necklace.length());
        }

        // Divide necklaces by length, since different lengths are never the "same" necklace
        for (int length : lengths) {
            List<String> someNecklaces = new ArrayList<>();
            for (String str : allNecklaces) {
                if (str.length() == length) {
                    someNecklaces.add(str);
                }
            }

            // Search through this list in another thread
            (new SearchTask(someNecklaces)).start();

        }
        SearchTask.allThreadsStarted = true;
    }

    /**
     * Method for outputting results in specific format of repeat check.
     * @param necklace String to check for repeats
     */
    private void printRepeatCount(String necklace) {
        int repeatCount = checkForRepeats(necklace);
        System.out.println("repeats(\"" + necklace + "\") => " + repeatCount);
    }

    /**
     * Method for outputting results of checking for "same" necklaces, in specific format.
     * @param necklace1 First string to check
     * @param necklace2 Second string to check
     */
    private void printWhetherSame(String necklace1, String necklace2)
    {
        System.out.print("same_necklace(\"" + necklace1 + "\", \"" + necklace2 + "\") => ");
        System.out.println(SearchTask.sameNecklace(necklace1, necklace2));
    }

    /**
     * Program entry point, calls methods for main challenge and the bonus challenges. Input is read from text files.
     * @param args Unused
     */
    public static void main(String[] args)  {
        NecklaceSolver solver = new NecklaceSolver();
        System.out.println("Daily Programmer #383 - Necklace Matching [Easy]");

        solver.doMainChallenge();
        solver.doBonusChallenge1();
        solver.doBonusChallenge2();
    }
}
