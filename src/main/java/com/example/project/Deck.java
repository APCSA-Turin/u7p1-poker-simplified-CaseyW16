package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    // Initializes a deck by creating a card of each rank for each suit.
    public void initializeDeck() { 
        for (String rank : Utility.getRanks()) {
            for (String suit : Utility.getSuits()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    // Shuffles the cards in the deck.
    public void shuffleDeck() { 
        Collections.shuffle(cards);
    }

    // Returns a random card from the deck.
    public Card drawCard() {
        Card randomCard = cards.get((int)(Math.random() * cards.size()));
        return randomCard;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}