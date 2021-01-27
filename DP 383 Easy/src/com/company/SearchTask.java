package com.company;

import java.util.*;

/**
 * Searches through its list for 4 necklaces that are all the same. This task that can be run on its own thread.
 */
public class SearchTask extends Thread {
    private List<String> data;

    // These members are used to determine when to print the Search Complete message
    private static int serialNumber = 0;
    private static int numberDone = 0;
    public static boolean allThreadsStarted = false;

    // For tracking how long the search takes, roughly. Actually only tells you how long the longest single thread took.
    private long timeStarted;

    /**
     * Parameterized constructor.
     * @param stringsToSearch The list of strings that the search will be going through.
     */
    public SearchTask(List<String> stringsToSearch)
    {
        data = stringsToSearch;
        serialNumber++;
        timeStarted = System.currentTimeMillis();
    }

    /**
     * Run method from Thread class. Kicks off the search.
     */
    public void run() {
        findFourMatchingHelper();

        numberDone++;
        if (allThreadsStarted && numberDone == serialNumber)
        {
            System.out.println("Search complete");
            System.out.println("Time in ms: " + (System.currentTimeMillis() - timeStarted));
        }
    }

    /**
     * Method that actually does the searching. If any exist, finds and prints groups of 4 strings that are all the
     * "same" according to the rules of the challenge.
     */
    private void findFourMatchingHelper()
    {
        if (data == null || data.isEmpty()) {
            return;
        }

        // Use a Set to avoid double-counting strings, e.g. as necklace2 and then later as a necklace1
        Map<String, Set<String>> mapForFindingFour = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            // Progress bar of sorts, can use if the data set is really large and user needs feedback
            //System.out.print("Progress: " + i + "/" + necklaces.size() + " (" + ((float)i/ necklaces.size()*100) + "%)                             \r");
            for (int j = i + 1; j < data.size(); j++) {
                String necklace1 = data.get(i);
                String necklace2 = data.get(j);

                // Only use necklaces that match at least one other necklace
                if (sameNecklace(necklace1, necklace2)) {
                    // Add to map so that we can find the set of 4
                    for (String str : mapForFindingFour.keySet()) {
                        if (sameNecklace(str, necklace1)) {
                            mapForFindingFour.get(str).add(necklace1);
                            mapForFindingFour.get(str).add(necklace2);
                            break;
                        }
                    }

                    // Values are not already in 4-way map, so add them as new key-value
                    Set<String> newSet = new HashSet<>();
                    newSet.add(necklace1);
                    newSet.add(necklace2);
                    mapForFindingFour.put(necklace1, newSet);
                }
            }
        }

        // Print out any results that were found
        for (String str : mapForFindingFour.keySet()) {
            if (mapForFindingFour.get(str).size() == 4)
            {
                System.out.print("Set of 4: ");
                System.out.println(mapForFindingFour.get(str));
                return;
            }
        }
    }

    /**
     * Determines whether the two strings, representing a "necklace" with letters on it,
     * are the same. Same here means that the letters could be moved from one end to the other
     * to produce the same arrangement. I.e., "ABCD" is the same as "CDAB".
     * @param necklace1 First sequence of letters
     * @param necklace2 Second sequence of letters
     * @return True if the necklaces are the same, false otherwise
     */
    public static boolean sameNecklace(String necklace1, String necklace2)
    {
        // Faster check, found in someone else's solution
        if (necklace1.length() != necklace2.length())
        {
            return false;
        }

        return (necklace1 + necklace1).contains(necklace2);
        // End of faster check

        // My original solution
//        // Trivial case, letters are in exact same order
//        if (necklace1.equals(necklace2))
//        {
//            return true;
//        }
//
//        //Create list of matches for first character on first necklace
//        List<Integer> matchingIndices = new ArrayList<>();
//        char firstChar = necklace1.charAt(0);
//
//        int matchingIndex = necklace2.indexOf(firstChar, 0);
//        while (matchingIndex != -1) {
//            matchingIndices.add(matchingIndex);
//            matchingIndex = necklace2.indexOf(firstChar, matchingIndex + 1);
//        }
//
//        // Check each possible match to see if all characters following that match as well
//        if (matchingIndices.isEmpty())
//        {
//            return false;
//        }
//
//        for (Integer index : matchingIndices) {
//            // Rearrange chars from necklace2 to try to match necklace1
//            String possibleMatch = necklace2.substring(index) + necklace2.substring(0, index);
//            if (possibleMatch.equals(necklace1))
//            {
//                return true;
//            }
//        }
//
//        return false;
    }
}
