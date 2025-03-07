package com.example.project;
import java.util.ArrayList;
public class Test {
    public static void main(String[] args) {
        
        // Player player = new Player();
        // player.addCard(new Card("10", "♠"));
        // player.addCard(new Card("J", "♠"));
        
        // // Add community cards (3 cards in total for this example)
        // ArrayList<Card> communityCards = new ArrayList<>();
        // communityCards.add(new Card("Q", "♠"));
        // communityCards.add(new Card("K", "♠"));
        // communityCards.add(new Card("A", "♠"));
        
        // System.out.println(player.playHand(communityCards));
        // Player player = new Player();
        // player.addCard(new Card("10", "♠"));
        // player.addCard(new Card("J", "♠"));
        
        // // Community Cards
        // ArrayList<Card> communityCards = new ArrayList<>();
        // communityCards.add(new Card("Q", "♠"));
        // communityCards.add(new Card("K", "♠"));
        // communityCards.add(new Card("A", "♠"));
        
        // player.playHand(communityCards);
        // String handResult = player.playHand(communityCards);
        // player.addCard(new Card("10", "♠"));
        // player.addCard(new Card("J", "♦"));

        // ArrayList<Card> communityCards = new ArrayList<>();
        // communityCards.add(new Card("9", "♣"));
        // communityCards.add(new Card("Q", "♥"));
        // communityCards.add(new Card("8", "♠"));
        

        // String handResult = player.playHand(communityCards);
        // player.sortAllCards();
        // System.out.println(player.getAllCards());
        // System.out.println(handResult);
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("7", "♠"));
        player1.addCard(new Card("8", "♠"));
        
        player2.addCard(new Card("9", "♠"));
        player2.addCard(new Card("10", "♠"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("Q", "♦"));
        communityCards.add(new Card("J", "♣"));
        communityCards.add(new Card("A", "♠"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        System.out.println(winner);
    }
}