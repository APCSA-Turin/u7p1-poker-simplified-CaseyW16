package com.example.project;
import java.util.ArrayList;


public class Game {
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        int p1HandValue = Utility.getHandRanking(p1Hand);
        int p2HandValue = Utility.getHandRanking(p2Hand);
        if (p1HandValue > p2HandValue) {
            return "Player 1 wins!";
        } else if (p2HandValue > p1HandValue) {
            return "Player 2 wins!";
        } else {
            int p1Score = 0;
            int p2Score = 0;
            if (p1Hand.equals("Full House")) {
                p1Score = p1.getFullHouseThreeSet();
                p2Score = p2.getFullHouseThreeSet();
                if (p1Score == p2Score) {
                    p1Score = p1.getFullHouseTwoSet();
                    p2Score = p2.getFullHouseTwoSet();
                }
            } else if (p1Hand.equals("Flush")) {
                p1Score = p1.getHighestFlushCard();
                p2Score = p2.getHighestFlushCard();
            } else if (p1Hand.equals("Straight")) {
                p1Score = p1.getHighestStraightCard();
                p2Score = p2.getHighestStraightCard();
            } else if (p1Hand.equals("Nothing")) {
                p1Score = p1.getHighestInHand();
                p2Score = p2.getHighestInHand();
            } else if (p1Hand.equals("Three of a Kind")) {
                p1Score = p1.getFullHouseThreeSet();
                p2Score = p2.getFullHouseThreeSet();
            }

            if (p1Score > p2Score) {
                return "Player 1 wins!";
            } else if (p2Score > p1Score) {
                return "Player 2 wins!";
            } else {
                return "Tie!";
            }
        }
    }

    public static void play() { //simulate card playing
    
    }
}