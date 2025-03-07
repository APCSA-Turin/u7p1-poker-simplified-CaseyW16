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

    public void addCard(Card c) {
        hand.add(c);
        allCards.add(c);
    }

    public String playHand(ArrayList<Card> communityCards) {  
        allCards = new ArrayList<Card>(hand);
        allCards.addAll(communityCards);
        sortAllCards();
            
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

        for (int freq : findSuitFrequency()) {
            if (freq == 5) {
                highestFlushCard = Utility.getRankValue(allCards.get(allCards.size() - 1).getRank());
                return "Flush";
            }
        }

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

        if (consecutives == 4) {
            return "Straight";
        }

        if (hasFoundThreeOfARank && !hasFoundTwoOfARank) {
            return "Three of a Kind";
        }

        if (totalPairs == 2) {
            return "Two Pair";
        } else if (totalPairs == 1) {
            return "A Pair";
        }

        int maxValueInHand = 0;
        for (Card card : hand) {
            int val = Utility.getRankValue(card.getRank());
            if (val > maxValueInHand) {
                maxValueInHand = val;
            }
        }
        int maxValueOverall = Utility.getRankValue(allCards.get(allCards.size() - 1).getRank());
        
        highestInHand = maxValueInHand;

        if (maxValueInHand == maxValueOverall) {
            return "High Card";
        }

        return "Nothing";
    }

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
