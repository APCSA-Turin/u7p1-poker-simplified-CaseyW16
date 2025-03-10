package com.example.project;

public class Card {
    private String rank;
    private String suit;

    // Creates a new card with the specified rank and suit
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Gets a nicely formatted version of the card,
    // in the following format:
    //  ┌─────┐
    //  │s    │
    //  │  #  │
    //  │    s│
    //  └─────┘
    public String getPrintable() {
        String extraSpace = " ";
        if (rank.equals("10")) {
            extraSpace = "";
        }
        return "┌─────┐\r\n" + //
                        "│" + suit + "    │\r\n" + //
                        "│  " + rank + extraSpace + " │\r\n" + //
                        "│    " + suit + "│\r\n" + //
                        "└─────┘";
    }

    public String getRank() { return rank; }
    public String getSuit() { return suit; }
    
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}