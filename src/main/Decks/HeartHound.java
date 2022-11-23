package main.Decks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public final class HeartHound extends EnvironmentCard {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int health;
    private int attackDamage;

    /**
     * Method for the special ability of the Heart Hound environment card
     * @param opponentCards ArrayList of the opponent's cards
     * @param playerCard ArrayList of the attacker's cards
     */

    public void specialAbility(final ArrayList<Card> opponentCards,
                               final ArrayList<Card> playerCard) {
        Card maxMinion = new Card();
        int minionColumnIdx = 0;
        for (int i = 0; i < opponentCards.size(); i++) {
            String cardName = opponentCards.get(i).getName();
            if (cardName.equals("Warden")
                    || cardName.equals("Berserker")
                    || cardName.equals("Sentinel")
                    || cardName.equals("Goliath")) {
                    if (opponentCards.get(i).getHealth() > maxMinion.getHealth()) {
                        maxMinion = opponentCards.get(i);
                        minionColumnIdx = i;
                    }
            }
        }
        playerCard.add(minionColumnIdx, opponentCards.get(minionColumnIdx));
        opponentCards.remove(minionColumnIdx);

    }

    public HeartHound(final CardInput card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    public HeartHound(final Card card, final int flag) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
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
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(final int health) {
        this.health = health;
    }

    @Override
    @JsonIgnore
    public int getAttackDamage() {
        return attackDamage;
    }

    @Override
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    @Override
    public String toString() {
        return "HeartHound{"
                + "mana=" + mana
                + ", description='" + description + '\''
                + ", colors=" + colors
                + ", name='" + name + '\''
                + ", health=" + health
                + ", attackDamage=" + attackDamage
                + '}';
    }
}
