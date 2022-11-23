package main.Decks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fileio.CardInput;
import java.util.ArrayList;

public class Card {
    private int mana;
    @JsonProperty("attackDamage")
    private int attackDamage;
    @JsonProperty("health")
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean isFrozen = false;
    private int hasAttacked = 0;

    public Card() {
    }

    public Card(final Card card, final int flag) {
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    public Card(final CardInput card) {
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    /**
     * Getter for the 'hasAttacked' flag
     * @return whether the card has attacked/used its ablity
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
     * Getter for the 'isFrozen' flag
     * @return whether the card is frozen or not
     */
    @JsonIgnore
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Setter for the 'isFrozen' flag
     * @param frozen can be either '1' or '0'
     */

    public void setFrozen(final boolean frozen) {
        isFrozen = frozen;
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
     * Getter for the card's colors
     * @return the card's colors
     */

    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Setter for the card's colors
     * @param colors an ArrayList of strings containing colors
     */

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Getter for the card's description
     * @return card's description
     */

    public String getDescription() {
        return description;
    }

    /**
     * Setter for the card's description
     * @param description sets the card's description with a new string
     */

    public void setDescription(final String description) {
        this.description = description;
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
     * @param name a new name
     */

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter for the 'attackDamage' flag
     * @return card's attackDamage
     */

    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Setter for the 'attackDamage' flag
     * @param attackDamage card's new attackDamage
     */

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
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
     * @param health card's new health
     */

    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * toString method
     * @return string with the card's fields
     */

    @Override
    public String toString() {
        return "Card{"
                + "mana=" + mana
                + ", attackDamage=" + attackDamage
                + ", health=" + health
                + ", description='" + description + '\''
                + ", colors=" + colors
                + ", name='" + name + '\''
                + ", isFrozen=" + isFrozen
                + ", hasAttacked=" + hasAttacked
                + '}';
    }
}
