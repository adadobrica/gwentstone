package main.Decks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public abstract class EnvironmentCard extends Card {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int health;
    private int attackDamage;

    public EnvironmentCard() {
    }
    public EnvironmentCard(final CardInput card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    /**
     *
     * @return the mana of the card
     */

    public int getMana() {
        return mana;
    }

    /**
     *
     * @param mana sets the mana of the card
     */

    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     *
     * @return the description of the card
     */

    public String getDescription() {
        return description;
    }

    /**
     * Setter for the Environment card
     * @param description the description of the card
     */

    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter for the card's colors
     * @return an ArrayList of strings containing the card's colors
     */

    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Setter for the card's colors
     * @param colors the colors of the card
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Getter for the card's name
     * @return the name
     */

    public String getName() {
        return name;
    }

    /**
     * Setter
     * @param name sets the name of the card
     */

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter for health
     * @return the health of the card
     */
    @Override
    @JsonIgnore
    public int getHealth() {
        return health;
    }

    /**
     * Setter for health
     * @param health assigns the card's health to a new value
     */

    @Override
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Getter for attack damage
     * @return card's attack damage
     */

    @Override
    @JsonIgnore
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Setter for attack damage
     * @param attackDamage assigns the card's attack damage with a new value
     */

    @Override
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * toString method for Environment Cards
     * @return a String with the card's fields
     */

    @Override
    public String toString() {
        return "EnvironmentCard{"
                + "mana=" + mana
                + ", description='"
                + description + '\'' + ", colors="
                + colors + ", name='" + name
                + '\'' + '}';
    }
}
