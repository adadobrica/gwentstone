package main.Start;

import main.Decks.*;
import main.Player.PlayerDeck;
import java.util.ArrayList;

public final class StartRound {
    private PlayerDeck playerOne;
    private PlayerDeck playerTwo;
    private int playerOneMana = 1;
    private int playerTwoMana = 1;
    private int playerOneTurn = 0;
    private int playerTwoTurn = 0;
    private int playerTurn;
    private int numTurnsOne = 0;
    private int numTurnsTwo = 0;
    private int numTurns = 0;
    private int numRounds = 1;

    private ArrayList<ArrayList<Card>> cardsOnTable = new ArrayList<>();
    private ArrayList<Card> playerOneBackRow = new ArrayList<>();

    private ArrayList<Card> playerOneFrontRow = new ArrayList<>();

    private ArrayList<Card> playerTwoFrontRow = new ArrayList<>();
    private ArrayList<Card> playerTwoBackRow = new ArrayList<>();

    /**
     * Method that resets whether a card has attacked or not
     * after a turn ends
     * @param playerIdx the index of the player
     */

    public void resetAttackField(final int playerIdx) {
        if (playerIdx == 1) {
            for (Card card : playerOneBackRow) {
                if (card.getHasAttacked() == 1) {
                    card.setHasAttacked(0);
                }
            }
            for (Card card : playerOneFrontRow) {
                if (card.getHasAttacked() == 1) {
                    card.setHasAttacked(0);
                }
            }
        } else {
            for (Card card : playerTwoBackRow) {
                if (card.getHasAttacked() == 1) {
                    card.setHasAttacked(0);
                }
            }
            for (Card card : playerTwoFrontRow) {
                if (card.getHasAttacked() == 1) {
                    card.setHasAttacked(0);
                }
            }
        }
    }

    /**
     * Method that unfreezes the frozen cards
     * after a turn is ended
     * @param playerIdx the index of the player
     */

    public void unfreezeCards(final int playerIdx) {
        if (playerIdx == 1) {
            for (Card card : playerOneBackRow) {
                if (card.isFrozen()) {
                    card.setFrozen(false);
                }
            }
            for (Card card : playerOneFrontRow) {
                if (card.isFrozen()) {
                    card.setFrozen(false);
                }
            }
        } else {
            for (Card card : playerTwoBackRow) {
                if (card.isFrozen()) {
                    card.setFrozen(false);
                }
            }
            for (Card card : playerTwoFrontRow) {
                if (card.isFrozen()) {
                    card.setFrozen(false);
                }
            }
        }
    }

    /**
     * Method that adds cards on the game table
     * @param card the card tha is about to be added on the game table
     * @param whichPlayer index of the player
     */
    public void addCardsOnTable(final Card card, final int whichPlayer) {
        final int numFive = 5;
        if (whichPlayer == 1) {
            if (card.getName().equals("Warden")
                    || card.getName().equals("Goliath")
                    || card.getName().equals("Miraj")
                    || card.getName().equals("The Ripper")) {
                if (playerOneFrontRow.size() < numFive) {
                    playerOneFrontRow.add(card);
                }
            }
            if (card.getName().equals("Sentinel")
                    || card.getName().equals("Berserker")
                    || card.getName().equals("The Cursed One")
                    || card.getName().equals("Disciple")) {
                if (playerOneBackRow.size() < numFive) {
                    playerOneBackRow.add(card);
                }
            }
        } else {
            if (card.getName().equals("Warden")
                    || card.getName().equals("Goliath")
                    || card.getName().equals("Miraj")
                    || card.getName().equals("The Ripper")) {
                if (playerTwoFrontRow.size() < numFive) {
                    playerTwoFrontRow.add(card);
                }
            }
            if (card.getName().equals("Sentinel")
                    || card.getName().equals("Berserker")
                    || card.getName().equals("The Cursed One")
                    || card.getName().equals("Disciple")) {
                if (playerTwoBackRow.size() < numFive) {
                    playerTwoBackRow.add(card);
                }
            }
        }
    }

    /**
     * Method that increases the number of turns made in a game
     */
    public void increaseNumTurns() {
        this.numTurns++;
    }

    /**
     * Method that decreases the mana of the player after they use a card
     * @param card the card that was used
     * @param whichPlayer the index of the player
     */
    public void decreasePlayerMana(final Card card, final int whichPlayer) {
        if (whichPlayer == 1) {
            this.playerOneMana -= card.getMana();
        } else {
            this.playerTwoMana -= card.getMana();
        }
    }

    /**
     * Method that adds cards into the hands of the player from their deck
     * and removes said card from the deck
     * @param playerOne information about the first player
     * @param playerTwo information about the second player
     * @param deckOne player one's decks
     * @param deckTwo player two's decks
     */
    public void checkRound(final PlayerDeck playerOne, final PlayerDeck playerTwo,
                           final ArrayList<Card> deckOne, final ArrayList<Card> deckTwo) {

        numRounds++;
        if (deckOne.size() > 0) {
            playerOne.getPlayingCards().add(deckOne.get(0));
            deckOne.remove(0);
        }

        if (deckTwo.size() > 0) {
            playerTwo.getPlayingCards().add(deckTwo.get(0));
            deckTwo.remove(0);
        }
        this.increasePlayerMana(numRounds);
    }

    public ArrayList<ArrayList<Card>> getCardsOnTable() {
        return cardsOnTable;
    }

    public void setCardsOnTable(final ArrayList<ArrayList<Card>> cardsOnTable) {
        this.cardsOnTable = cardsOnTable;
    }

    public ArrayList<Card> getPlayerOneFrontRow() {
        return playerOneFrontRow;
    }

    public void setPlayerOneFrontRow(final ArrayList<Card> playerOneFrontRow) {
        this.playerOneFrontRow = playerOneFrontRow;
    }

    public ArrayList<Card> getPlayerTwoFrontRow() {
        return playerTwoFrontRow;
    }

    public void setPlayerTwoFrontRow(final ArrayList<Card> playerTwoFrontRow) {
        this.playerTwoFrontRow = playerTwoFrontRow;
    }

    public int getNumTurns() {
        return numTurns;
    }

    public void setNumTurns(final int numTurns) {
        this.numTurns = numTurns;
    }

    public int getNumRounds() {
        return numRounds;
    }

    public void setNumRounds(final int numRounds) {
        this.numRounds = numRounds;
    }

    public int getNumTurnsOne() {
        return numTurnsOne;
    }

    public void setNumTurnsOne(final int numRoundsOne) {
        this.numTurnsOne = numRoundsOne;
    }

    public int getNumTurnsTwo() {
        return numTurnsTwo;
    }

    public void setNumTurnsTwo(final int numRoundsTwo) {
        this.numTurnsTwo = numRoundsTwo;
    }


    public int getPlayerOneTurn() {
        return playerOneTurn;
    }

    public void setPlayerOneTurn(final int playerOneTurn) {
        this.playerOneTurn = playerOneTurn;
    }

    public int getPlayerTwoTurn() {
        return playerTwoTurn;
    }

    public void setPlayerTwoTurn(final int playerTwoTurn) {
        this.playerTwoTurn = playerTwoTurn;
    }

    /**
     * Method that increases the mana of the player after a round
     * @param value the number of the round
     */
    public void increasePlayerMana(final int value) {
        final int numTen = 10;
        if (value < numTen) {
            playerOneMana += value;
            playerTwoMana += value;
        } else {
            playerOneMana = numTen;
            playerTwoMana = numTen;
        }
    }

    public PlayerDeck getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(final PlayerDeck playerOne) {
        this.playerOne = playerOne;
    }

    public PlayerDeck getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(final PlayerDeck playerTwo) {
        this.playerTwo = playerTwo;
    }

    public int getPlayerOneMana() {
        return playerOneMana;
    }

    public void setPlayerOneMana(final int playerOneMana) {
        this.playerOneMana = playerOneMana;
    }

    public int getPlayerTwoMana() {
        return playerTwoMana;
    }

    public void setPlayerTwoMana(final int playerTwoMana) {
        this.playerTwoMana = playerTwoMana;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(final int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public ArrayList<Card> getPlayerOneBackRow() {
        return playerOneBackRow;
    }

    public void setPlayerOneBackRow(final ArrayList<Card> playerOneBackRow) {
        this.playerOneBackRow = playerOneBackRow;
    }

    public ArrayList<Card> getPlayerTwoBackRow() {
        return playerTwoBackRow;
    }

    public void setPlayerTwoBackRow(final ArrayList<Card> playerTwoBackRow) {
        this.playerTwoBackRow = playerTwoBackRow;
    }
}
