package main.Decks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class GeneralKocioraw extends HeroCard {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int hasAttacked = 0;
    private static final int THIRTY = 30;
    private int health = THIRTY;
    public GeneralKocioraw() { }

    /**
     * Method for General Kocioraw's special ability: increases the card's attackDamage with 1
     * @param cards player's cards
     */
    public void bloodThirst(final ArrayList<Card> cards) {
        for (Card card : cards) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }
    }

    /**
     * Getter for card's mana
     * @return card's mana
     */

    @Override
    public int getMana() {
        return mana;
    }

    /**
     * Setter for card's mana
     * @param mana assigns the card's mana with a new value
     */

    @Override
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Getter for card's description
     * @return card's description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Setter for card's description
     * @param description assigns the card's description with a new String
     */
    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter for card's colors
     * @return card's colors
     */
    @Override
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Setter for card's colors
     * @param colors assigns the card new colors
     */
    @Override
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Getter for card's name
     * @return card's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Setter for card's name
     * @param name sets a new name to the card
     */
    @Override
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter for card's 'hasAttacked' field
     * @return whether the card has attacked or not
     */
    @Override
    @JsonIgnore
    public int getHasAttacked() {
        return hasAttacked;
    }

    /**
     * Setter for card's 'hasAttacked' field
     * @param hasAttacked can be either '1' or '0'
     */
    @Override
    public void setHasAttacked(final int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Getter for card's health
     * @return card's health
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * Setter for card's health
     * @param health sets a new health to the card
     */
    @Override
    public void setHealth(final int health) {
        this.health = health;
    }
}
