package main.Decks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public final class Winterfell extends EnvironmentCard {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int health;
    private int attackDamage;

    private int isUsed = 0;

    /**
     * Method used for Winterfell's special ability
     * All the opponent's cards from a row are frozen
     * @param cards the opponent's cards
     */
    public void specialAbility(final ArrayList<Card> cards) {
        for (Card card : cards) {
            card.setFrozen(true);
        }
    }

    public Winterfell() {

    }

    public Winterfell(final CardInput card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    public Winterfell(final Card card, final int flag) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    @JsonIgnore
    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(final int isUsed) {
        this.isUsed = isUsed;
    }

    public int getMana() {
        return mana;
    }


    public void setMana(final int mana) {
        this.mana = mana;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(final String description) {
        this.description = description;
    }


    public ArrayList<String> getColors() {
        return colors;
    }


    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }


    public String getName() {
        return name;
    }


    public void setName(final String name) {
        this.name = name;
    }


    @JsonIgnore
    public int getHealth() {
        return health;
    }


    public void setHealth(final int health) {
        this.health = health;
    }


    @JsonIgnore
    public int getAttackDamage() {
        return attackDamage;
    }


    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    @Override
    public String toString() {
        return "Winterfell{"
                + "mana=" + mana
                + ", description='" + description + '\''
                + ", colors=" + colors
                + ", name='" + name + '\''
                + ", health=" + health
                + ", attackDamage=" + attackDamage
                + ", isUsed=" + isUsed
                + '}';
    }
}
