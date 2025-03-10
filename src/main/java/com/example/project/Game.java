package com.example.project;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    // Decides the winner of the game based on both players, their hands, and the community cards. 
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        int p1HandValue = Utility.getHandRanking(p1Hand);
        int p2HandValue = Utility.getHandRanking(p2Hand);
        if (p1HandValue > p2HandValue) {
            return "Player 1 wins!";
        } else if (p2HandValue > p1HandValue) {
            return "Player 2 wins!";
        } else {
            // Do a tiebreaker
            int p1Score = 0;
            int p2Score = 0;
            if (p1Hand.equals("Full House")) {
                // If they both have a Full House, the tiebreaker should be based
                // on the higher set of three cards, or if neccessary, the higher
                // pair.
                p1Score = p1.getFullHouseThreeSet();
                p2Score = p2.getFullHouseThreeSet();
                if (p1Score == p2Score) {
                    p1Score = p1.getFullHouseTwoSet();
                    p2Score = p2.getFullHouseTwoSet();
                }
            } else if (p1Hand.equals("Flush")) {
                // If they both have a Flush, the higher one wins.
                p1Score = p1.getHighestFlushCard();
                p2Score = p2.getHighestFlushCard();
            } else if (p1Hand.equals("Straight")) {
                // If they both have a Straight, the higher one wins.
                p1Score = p1.getHighestStraightCard();
                p2Score = p2.getHighestStraightCard();
            } else if (p1Hand.equals("Nothing")) {
                // If they both have nothing, whoever has a higher card has a High Card
                p1Score = p1.getHighestInHand();
                p2Score = p2.getHighestInHand();
            } else if (p1Hand.equals("Three of a Kind")) {
                // If they both have Three of a Kind, whoever has the higher
                // set of three cards wins.
                p1Score = p1.getFullHouseThreeSet();
                p2Score = p2.getFullHouseThreeSet();
            } else if (p1Hand.equals("High Card")) {
                // If both players have a High Card, whoever has a higher one wins.
                p1Score = p1.getHighestInHand();
                p2Score = p2.getHighestInHand();
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

    // Displays a simple interface where you can cycle through random
    // cards for two players and see the outcome.
    public static void play() {
        // Create a standard deck and two players
        Deck deck = new Deck();
        deck.initializeDeck();
        Player a = new Player();
        Player b  = new Player();

        boolean playing = true;
        Scanner scan = new Scanner(System.in);
        ArrayList<Card> communityCards = new ArrayList<>();
        // Game loop
        while (playing) {
            // Resets the cards on the table
            a.clearCards();
            a.addCard(deck.drawCard());
            a.addCard(deck.drawCard());
            b.clearCards();
            b.addCard(deck.drawCard());
            b.addCard(deck.drawCard());
            communityCards.clear();
            communityCards.add(deck.drawCard());
            communityCards.add(deck.drawCard());
            communityCards.add(deck.drawCard());
            
            // Prints out the players' hands and the community cards
            System.out.println("Player 1's Hand: ");
            for (Card card : a.getHand()) {
                System.out.println(card.getPrintable());
            }
            System.out.println("Community Cards: ");
            for (Card card : communityCards) {
                System.out.println(card.getPrintable());
            }
            System.out.println("Player 2's Hand: ");
            for (Card card : b.getHand()) {
                System.out.println(card.getPrintable());
            }

            // Finds what hands they have and the winner
            String aResult = a.playHand(communityCards);
            String bResult = b.playHand(communityCards);
            String winner = determineWinner(a, b, aResult, bResult, communityCards);
            // If both players have "Nothing", but one has a higher card than the other, then
            // they have a High Card.
            if (a.playHand(communityCards).equals("Nothing") && b.playHand(communityCards).equals("Nothing")) {
                if (a.getHighestInHand() > b.getHighestInHand()) {
                    aResult = "High Card";
                } else if (b.getHighestInHand() > a.getHighestInHand()) {
                    bResult = "High Card";
                }
            }
            System.out.println("Player 1 has " + aResult);
            System.out.println("Player 2 has " + bResult);
            System.out.println(winner);
            System.out.println("Press enter to simulate another game");
            scan.nextLine();
        }
        scan.close();
    }

    public static void main(String[] args) {
        play();
    }
}