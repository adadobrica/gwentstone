package main.Decks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public final class EmpressThorina extends HeroCard {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int hasAttacked = 0;
    private int health;
    public EmpressThorina() { }

    /**
     * Method that uses the card's special ability
     * Destroys the card with the maximum health from a row
     * @param cards ArrayList of opponent's cards
     */

    public void lowBlow(final ArrayList<Card> cards) {
        int maxHealth = -1;
        int maxHealthPos = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getHealth() > maxHealth) {
                maxHealth = cards.get(i).getHealth();
                maxHealthPos = i;
            }
        }
        cards.remove(maxHealthPos);
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void setMana(final int mana) {
        this.mana = mana;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public ArrayList<String> getColors() {
        return colors;
    }

    @Override
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    @JsonIgnore
    public int getHasAttacked() {
        return hasAttacked;
    }

    @Override
    public void setHasAttacked(final int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(final int health) {
        this.health = health;
    }
}
