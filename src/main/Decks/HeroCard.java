package main.Decks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class HeroCard {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private static final int THIRTY = 30;
    private int hasAttacked = 0;
    private int health = THIRTY;

    public HeroCard() {

    }

    public HeroCard(final Card card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    public HeroCard(final HeroCard card, final int flag) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.health = card.getHealth();
    }

    /**
     * Getter for the 'hasAttacked' flag
     * @return whether the card has already attacked or used its abilities
     */

    @JsonIgnore
    public int getHasAttacked() {
        return hasAttacked;
    }

    /**
     * Setter for the 'hasAttacked' flag
     * @param hasAttacked can be either '1' or '0'
     */

    public void setHasAttacked(final int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Getter for the card's mana
     * @return the card's mana
     */

    public int getMana() {
        return mana;
    }

    /**
     * Setter for the card's mana
     * @param mana assigns the card's mana with a new value
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Getter for the card's description
     * @return the card's description
     */

    public String getDescription() {
        return description;
    }

    /**
     * Setter for the card's description
     * @param description assigns the card's description with a new String
     */

    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter for the card's colors
     * @return the colors of the card
     */

    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Setter for the card's colors
     * @param colors assigns the card new colors
     */

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Getter for the card's name
     * @return card's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the card's name
     * @param name sets a new name to the card
     */

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter for the card's health
     * @return card's health
     */

    public int getHealth() {
        return health;
    }

    /**
     * Setter for card's health
     * @param health sets a new health to the card
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * toString method
     * @return a string with the card's fields
     */

    @Override
    public String toString() {
        return "HeroCard{"
                + "mana=" + mana
                + ", description='" + description + '\''
                + ", colors=" + colors
                + ", name='" + name + '\''
                + ", health=" + health
                + '}';
    }
}
