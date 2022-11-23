package main.Decks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public class FireStorm extends EnvironmentCard {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int health;
    private int attackDamage;

    public FireStorm(final CardInput card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }
    public FireStorm(final Card card, final int flag) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    /**
     * Method for Firestorm's special ability: decreases the opponent's cards' lives with 1
     * @param cards the opponent's cards
     */

    public void specialAbility(final ArrayList<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Card card  = cards.get(i);
            if (card.getHealth() == 1) {
                cards.remove(i);
                i--;
            } else {
                card.setHealth(card.getHealth() - 1);
            }
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
     * @param mana sets the mana of the card
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
     * @param description the description of the card
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
     * @param colors the new colors of the card
     */

    @Override
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Getter for the card's name
     * @return card's name
     */

    @Override
    public String getName() {
        return name;
    }

    /**
     * Setter for card's name
     * @param name sets the name of the card
     */

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter for card's health
     * @return card's health
     */

    @JsonIgnore
    public int getHealth() {
        return health;
    }

    /**
     * Setter for card's health
     * @param health assigns the card's health to a new value
     */

    @Override
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Getter for card's attackDamage
     * @return card's attackDamage
     */

    @JsonIgnore
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Setter for card's attackDamage
     * @param attackDamage assigns the card's attack damage with a new value
     */

    @Override
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * toString method
     * @return string containing the card's fields
     */

    @Override
    public String toString() {
        return "FireStorm{"
                + "mana=" + mana
                + ", description='" + description + '\''
                + ", colors=" + colors
                + ", name='" + name + '\''
                + ", health=" + health
                + ", attackDamage=" + attackDamage
                + '}';
    }
}
