package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolitaireAI {
    private static Map<String, List<Integer>> lookupTable = new HashMap<>();

    // Face up card is "1", face down is "0", removed cards are "-"

    private final char FACE_UP_CARD = '1';
    private final char FACE_DOWN_CARD = '0';
    private final char REMOVED_CARD = '-';

    public List<Integer> solveCardLayout(String cards) {
        // If solution has been found before, use that
        if (hasCachedSolution(cards)) {
            return getCachedSolution(cards);
        }

        List<Integer> faceUpCards = findCards(cards, FACE_UP_CARD);
        // Base cases
        if (faceUpCards.size() == 0) {
            if (findCards(cards, FACE_DOWN_CARD).size() == 0) {
                setCachedSolution(cards, new ArrayList<>());
            }
            else {
                setCachedSolution(cards, null);
            }

            return getCachedSolution(cards);
        }

        // Even parity is never solvable
        if (faceUpCards.size() % 2 == 0) {
            setCachedSolution(cards, null);
            return getCachedSolution(cards);
        }

        // Now we need to look through all the different possible moves
        for (int cardPos : faceUpCards) {
            String nextLayout = removeCard(cards, cardPos);
            // If there are two "islands", solve each separately
            if (hasMultipleIslands(nextLayout)) {
                // Get first island and solve
                List<Integer> firstSolution = solveCardLayout(getIsland(nextLayout, 0));
                if (firstSolution == null) {
                    setCachedSolution(nextLayout, null);
                    continue;
                }
                int startOfFirst = getIslandStart(nextLayout, 0);
                for (int i = 0; i < firstSolution.size(); i++) {
                    int newVal = firstSolution.get(i) + startOfFirst;
                    firstSolution.set(i, newVal);
                }

                // Get second island and solve
                List<Integer> secondSolution = solveCardLayout(getIsland(nextLayout, 1));
                if (secondSolution == null) {
                    setCachedSolution(nextLayout, null);
                    continue;
                }
                int startOfSecond = getIslandStart(nextLayout, 1);
                for (int i = 0; i < secondSolution.size(); i++) {
                    int newVal = secondSolution.get(i) + startOfSecond;
                    secondSolution.set(i, newVal);
                }

                // Combine two solutions
                List<Integer> overallSolution = new ArrayList<>();
                overallSolution.add(cardPos);
                overallSolution.addAll(firstSolution);
                overallSolution.addAll(secondSolution);
                setCachedSolution(cards, overallSolution);
                return overallSolution;


            } else {
                List<Integer> subListSolution = solveCardLayout(nextLayout);

                if (subListSolution != null) {
                    subListSolution.add(0, cardPos);
                    setCachedSolution(cards, subListSolution);
                    return getCachedSolution(cards);
                }
            }

        }

        // None of the possible moves will work, so this is unsolvable
        setCachedSolution(cards, null);
        return null;
    }

    private String removeCard(String currentLayout, int cardToRemove) {
        char[] chars = currentLayout.toCharArray();

        if (cardToRemove - 1 >= 0) {
            chars[cardToRemove - 1] = flipCard(chars[cardToRemove - 1]);
        }
        chars[cardToRemove] = REMOVED_CARD;
        if (cardToRemove + 1 < chars.length) {
            chars[cardToRemove + 1] = flipCard(chars[cardToRemove + 1]);
        }
        return new String(chars);
    }

    private char flipCard(char currentValue) {
        switch (currentValue)
        {
            case FACE_DOWN_CARD: return FACE_UP_CARD;
            case FACE_UP_CARD: return FACE_DOWN_CARD;
            default: return REMOVED_CARD;
        }
    }

    private List<Integer> findCards(String cards, char charToFind) {
        List<Integer> faceUpCards = new ArrayList<>();

        // Simply iterate through and add them to the list
        for (int i = 0; i < cards.length(); i++) {
            if (cards.charAt(i) == charToFind) {
                faceUpCards.add(i);
            }
        }
        return faceUpCards;
    }

    private boolean hasMultipleIslands(String cardLayout) {
        // Maybe not the fastest, but I think the simplest to read/write
        return cardLayout.contains("1-1") ||
                cardLayout.contains("0-0") ||
                cardLayout.contains("1-0") ||
                cardLayout.contains("0-1");
    }

    private String getIsland(String cardLayout, int islandIndex) {
        // Remove removed cards at beginning or end - trim only works on whitespace so replace, trim, then replace back
        cardLayout = cardLayout.replace(REMOVED_CARD, ' ').trim().replace(' ', REMOVED_CARD);
        String[] islands = cardLayout.split(String.valueOf(REMOVED_CARD));


        return islandIndex < islands.length ? islands[islandIndex] : "";
    }

    private int getIslandStart(String cardLayout, int islandIndex) {
        int numLeadingRemoved = 0;
        for (int i = 0; i < cardLayout.length(); i++) {
            if (cardLayout.charAt(i) == REMOVED_CARD) {
                numLeadingRemoved++;
            }
            else {
                break;
            }
        }

        int startIndex = numLeadingRemoved;
        for (int i = 0; i < islandIndex; i++) {
            // The island starts at next char after '-'
            // Also, on our next indexOf call, we want to start searching AFTER the previous '-', otherwise we'll be
            // stuck in an infinite loop
            startIndex = cardLayout.indexOf(REMOVED_CARD, startIndex) + 1;
        }
        return startIndex;
    }

    private boolean hasCachedSolution(String cardLayout) {
        return lookupTable.containsKey(cardLayout);
    }

    private List<Integer> getCachedSolution(String cardLayout) {
        // Make a copy for the client code, so they can change it to their heart's content
        List<Integer> valueToReturn = lookupTable.get(cardLayout);
        return valueToReturn == null ? null : new ArrayList<>(valueToReturn);
    }

    private void setCachedSolution(String cardLayout, List<Integer> solution) {
        // Make a copy for the lookup table, so client code doesn't change it on us
        List<Integer> valueToStore = solution == null ? null : new ArrayList<>(solution);
        lookupTable.put(cardLayout, valueToStore);
    }
}
