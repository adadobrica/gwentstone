package main.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import fileio.CardInput;
import main.Decks.*;

public final class PlayerDeck {
    private int playerIdx;
    private HeroCard heroCard;
    private ArrayList<CardInput> decks;
    private ArrayList<Card> decksCopy = new ArrayList<>();
    private int shuffleSeed;
    private ArrayList<Card> playingCards = new ArrayList<>();
    private ArrayList<Card> environmentCardsInHand = new ArrayList<>();

    /**
     * Method that takes cards from the player's chosen decks and adds one to their current hand
     * @param cardInput deck chosen from the input
     * @param cardsArray ArrayList of cards, represents player's current hand
     * @param shuffleSeed shuffleseed
     * @return player's new deck
     */

    public ArrayList<Card> copyFromDecksAndShuffle(final ArrayList<CardInput> cardInput,
                                                   final ArrayList<Card> cardsArray,
                                                   final int shuffleSeed) {
        for (CardInput input : cardInput) {
            switch (input.getName()) {
                case "Winterfell" -> {
                    Winterfell envCard = new Winterfell(input);
                    cardsArray.add(envCard);
                    break;
                }
                case "Heart Hound" -> {
                    HeartHound envCard = new HeartHound(input);
                    cardsArray.add(envCard);
                    break;
                }
                case "Firestorm" -> {
                    FireStorm envCard = new FireStorm(input);
                    cardsArray.add(envCard);
                    break;
                }
                default -> {
                    Card newCard = new Card(input);
                    cardsArray.add(newCard);
                }
            }
        }
        Collections.shuffle(cardsArray, new Random(shuffleSeed));

        this.playingCards.add(cardsArray.get(0));
        cardsArray.remove(0);

        for (Card card : cardsArray) {
            decksCopy.add(card);
        }

        return cardsArray;
    }


    public ArrayList<Card> getEnvironmentCardsInHand() {
        return environmentCardsInHand;
    }

    public void setEnvironmentCardsInHand(final ArrayList<Card> environmentCardsInHand) {
        this.environmentCardsInHand = environmentCardsInHand;
    }

    public void setHeroCard(final HeroCard heroCard) {
        this.heroCard = heroCard;
    }

    public ArrayList<Card> getDecksCopy() {
        return decksCopy;
    }

    public void setDecksCopy(final ArrayList<Card> decksCopy) {
        this.decksCopy = decksCopy;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public ArrayList<Card> getPlayingCards() {
        return playingCards;
    }

    public void setPlayingCards(final ArrayList<Card> playingCards) {
        this.playingCards = playingCards;
    }

    public PlayerDeck(final int index, final HeroCard card,
                      final ArrayList<CardInput> decks) {
        this.playerIdx = index;
        this.heroCard = card;
        this.decks = decks;
    }

    public PlayerDeck(final int index, final ArrayList<CardInput> decks) {
        this.playerIdx = index;
        this.decks = decks;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public HeroCard getHeroCard() {
        return heroCard;
    }

    /**
     * Sets the player's hero card at the beginning of the game
     * @param heroCard hero card
     */
    public void setHeroCard(final Card heroCard) {
        HeroCard hero = new HeroCard(heroCard);
        this.heroCard = hero;
    }

    public ArrayList<CardInput> getDecks() {
        return decks;
    }

    public void setDecks(final ArrayList<CardInput> decks) {
        this.decks = decks;
    }

}

