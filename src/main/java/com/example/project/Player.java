package com.example.project;
import java.util.ArrayList;


public class Player {
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    private int fullHouseThreeSet = 0;
    private int fullHouseTwoSet = 0;
    private int highestFlushCard = 0;
    private int highestStraightCard = 0;
    private int highestInHand = 0;
    
    public Player() {
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand() { return hand; }
    public ArrayList<Card> getAllCards() { return allCards; }

    // Adds the specified card to the player's hand.
    public void addCard(Card c) {
        hand.add(c);
        allCards.add(c);
    }

    // Clears all cards from the player's hand.
    public void clearCards() {
        hand.clear();
        allCards.clear();
    }

    // Returns a String with the name of whichever
    // hand the player has. If the player has no hand,
    // "Nothing" is returned.
    public String playHand(ArrayList<Card> communityCards) {  
        // Reset the "all cards" list so that it can be
        // re-sorted with everything in the player's hand.
        allCards = new ArrayList<Card>(hand);
        allCards.addAll(communityCards);
        sortAllCards();
            
        // If all 5 cards can be found in one suit, a Royal or Straight flush
        // is possible.
        for (int freq : findSuitFrequency()) {
            if (freq == 5) {
                // 5 cards, all with the same suit
                int royalCards = 0;
                int consecutiveCards = 0;
                int lastCardValue = 0;
                for (Card card : allCards) {
                    if (Utility.getRankValue(card.getRank()) >= 10) {
                        // This card is royal or a 10
                        royalCards++;
                    }
                    if (lastCardValue != 0) {
                        if (lastCardValue == Utility.getRankValue(card.getRank()) - 1) {
                            consecutiveCards++;
                        }
                    }
                    lastCardValue = Utility.getRankValue(card.getRank());
                }
                if (royalCards == 5) {
                    // All of these are valued >= 10, it's a royal flush
                    return "Royal Flush";
                }
                if (consecutiveCards == 4) {
                    return "Straight Flush";
                }
            }
        }

        // Finds how many cards of a particular rank the
        // player has, to check for a Four of a Kind, a Full House,
        // or a Three of a Kind.
        boolean hasFoundThreeOfARank = false;
        boolean hasFoundTwoOfARank = false;
        ArrayList<Integer> rankingFrequency = findRankingFrequency();
        for (int i = 0; i < rankingFrequency.size(); i++) {
            int freq = rankingFrequency.get(i);

            if (freq >= 4) {
                return "Four of a Kind";
            }
            if (freq >= 3) {
                hasFoundThreeOfARank = true;
                fullHouseThreeSet = Utility.getRankValue(Utility.getRanks()[i]);
            } else if (freq >= 2) {
                hasFoundTwoOfARank = true;
                fullHouseTwoSet = Utility.getRankValue(Utility.getRanks()[i]);
            }
        }

        if (hasFoundThreeOfARank && hasFoundTwoOfARank) {
            return "Full House";
        }

        // If all 5 cards can be found in the same suit, a regular
        // Flush is possible.
        for (int freq : findSuitFrequency()) {
            if (freq == 5) {
                highestFlushCard = Utility.getRankValue(allCards.get(allCards.size() - 1).getRank());
                return "Flush";
            }
        }

        // Counts the number of cards with consecutive, increasing
        // values. This can be used later to decide if the player has
        // a Straight.
        int consecutives = 0;
        int lastCardValue = 0;
        for (Card card : allCards) {
            if (lastCardValue != 0) {
                int val = Utility.getRankValue(card.getRank());

                if (val == lastCardValue + 1) {
                    consecutives++;
                    highestStraightCard = Utility.getRankValue(card.getRank());
                }
            }
            lastCardValue = Utility.getRankValue(card.getRank());
        }

        // Finds the number of pairs of duplicates of cards,
        // by ccounting the number of distinct duplicates and 
        // performing integer division by two.
        ArrayList<Integer> alreadyValuePairs = new ArrayList<>();
        int totalPairs = 0;
        for (int i = 0; i < allCards.size(); i++) {
            int duplicates = 1;
            int iVal = Utility.getRankValue(allCards.get(i).getRank());
            boolean shouldIgnore = false;
            for (int alr : alreadyValuePairs) {
                if (alr == iVal) {
                    shouldIgnore = true;
                }
            }
            if (shouldIgnore) continue;
            for (int j = i + 1; j < allCards.size(); j++) {
                int jVal = Utility.getRankValue(allCards.get(j).getRank());
                if (iVal == jVal) {
                    duplicates++;
                }
            }
            alreadyValuePairs.add(iVal);
            totalPairs += duplicates / 2;
        }

        // If all cards are consecutively increasing, the player
        // has a Straight.
        if (consecutives == 4) {
            return "Straight";
        }

        // If the player has exactly three cards of a rank, they
        // have a Three of a kind.
        if (hasFoundThreeOfARank && !hasFoundTwoOfARank) {
            return "Three of a Kind";
        }

        if (totalPairs == 2) {
            return "Two Pair";
        } else if (totalPairs == 1) {
            return "A Pair";
        }

        // Gets the highest-value card in hand (not necessarily the highest
        // value card overall).
        int maxValueInHand = 0;
        for (Card card : hand) {
            int val = Utility.getRankValue(card.getRank());
            if (val > maxValueInHand) {
                maxValueInHand = val;
            }
        }
        // Since the list of all cards is sorted, the highest value card
        // overall will be the last item.
        int maxValueOverall = Utility.getRankValue(allCards.get(allCards.size() - 1).getRank());
        
        highestInHand = maxValueInHand;

        // If the player's highest card is in their hand, they have a High Card
        if (maxValueInHand == maxValueOverall) {
            return "High Card";
        }

        return "Nothing";
    }

    // Sorts the "allCards" list by order of ascending value.
    public void sortAllCards() { 
        for (int i = 0; i < allCards.size(); i++) {
            int val = Utility.getRankValue(allCards.get(i).getRank());
            int iIndex = i;
            for (int j = i; j >= 0; j--) {
                int val2 = Utility.getRankValue(allCards.get(j).getRank());
                if (val < val2) {
                    Card temp = allCards.set(iIndex, allCards.get(j));
                    allCards.set(j, temp);
                    iIndex = j;
                }
            }
        }
    } 

    // Returns the frequency of each rank in the deck, in an array
    // with indices corresponding to the array of ranks found in the
    // Utility class.
    public ArrayList<Integer> findRankingFrequency() {
        ArrayList<Integer> freq = new ArrayList<Integer>();
        for (String rank : Utility.getRanks()) {
            int amt = 0;
            for (Card card : allCards) {
                if (card.getRank().equals(rank)) {
                    amt++;
                }
            }
            freq.add(amt);
        }
        return freq; 
    }

    // Returns the frequency of each suit in the deck, in an array
    // with indices corresponding to the array of suits found in the
    // Utility class.
    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> freq = new ArrayList<Integer>();
        for (String suit : Utility.getSuits()) {
            int amt = 0;
            for (Card card : allCards) {
                if (card.getSuit().equals(suit)) {
                    amt++;
                }
            }
            freq.add(amt);
        }
        return freq; 
    }

    // The following methods are used to provide tie-breaking info
    // for the Game class.
    public int getFullHouseThreeSet() {
        return fullHouseThreeSet;
    }
    public int getFullHouseTwoSet() {
        return fullHouseTwoSet;
    }
    public int getHighestFlushCard() {
        return highestFlushCard;
    }
    public int getHighestStraightCard() {
        return highestStraightCard;
    }
    public int getHighestInHand() {
        return highestInHand;
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
