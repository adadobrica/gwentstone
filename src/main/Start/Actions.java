package main.Start;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;
import fileio.Input;
import main.Decks.*;
import main.Player.PlayerDeck;

import java.util.ArrayList;

public final class Actions {
    private static Actions instance;
    private Actions() { }

    /**
     * Defining a getInstance() operation needed to implement a Singleton pattern
     * @return a unique instance of the class
     */
    public static synchronized Actions getInstance() {
        if (instance == null) {
            instance = new Actions();
        }
        return instance;
    }

    /**
     * Method that outputs a player's deck of cards
     * @param i index needed to get the actions list
     * @param j index needed to get the index of the player
     * @param start the input
     * @param command stores information for the output
     * @param arrNode the final output
     * @param playerDecksOne player one's deck
     * @param playerDecksTwo player two's deck
     */

    public void getPlayerDeck(final int i, final int j,
                              final Input start, final ObjectNode command,
                              final ArrayNode arrNode,
                              final ArrayList<Card> playerDecksOne,
                              final ArrayList<Card> playerDecksTwo) {
        int playerIndex = start.getGames().get(i).getActions().get(j).getPlayerIdx();
        command.put("playerIdx", playerIndex);
        if (playerIndex == 1) {
            command.putPOJO("output", playerDecksOne);
        } else {
            command.putPOJO("output", playerDecksTwo);
        }
        arrNode.add(command);
    }

    /**
     * Method that prints a player's hero card
     * @param i index needed for the actions list
     * @param j index needed for the hero card index
     * @param start the input
     * @param command stores information for the output
     * @param arrNode the output
     * @param playerOne information about the first player
     * @param playerTwo information about the second player
     */

    public void getPlayerHero(final int i, final int j,
                              final Input start, final ObjectNode command,
                              final ArrayNode arrNode,
                              final PlayerDeck playerOne,
                              final PlayerDeck playerTwo) {
        int playerHeroIdx = start.getGames().get(i).getActions().get(j).getPlayerIdx();
        command.put("playerIdx", playerHeroIdx);
        if (playerHeroIdx == 1) {
            HeroCard playerOneHero = new HeroCard(playerOne.getHeroCard(), 1);
            command.putPOJO("output", playerOneHero);
        } else {
            HeroCard playerTwoHero = new HeroCard(playerTwo.getHeroCard(), 1);
            command.putPOJO("output", playerTwoHero);
        }
        arrNode.add(command);
    }

    /**
     * Method for the 'endPlayerTurn' command, it checks whether a round has ended or not,
     * and unfreezes a card/resets whether a card has attacked or not after a turn
     * @param round information about the current game
     * @param playerOne information about the first player
     * @param playerTwo information about the second player
     * @param playerDecksOne player one's deck of cards
     * @param playerDecksTwo player two's deck of cards
     */

    public void endPlayerTurn(final StartRound round, final PlayerDeck playerOne,
                              final PlayerDeck playerTwo,
                              final ArrayList<Card> playerDecksOne,
                              final ArrayList<Card> playerDecksTwo) {
        if (round.getNumTurns() % 2 == 1) {
            round.checkRound(playerOne, playerTwo, playerDecksOne, playerDecksTwo);
        }
        round.increaseNumTurns();
        if (round.getPlayerTurn() == 1) {
            round.setPlayerTurn(2);
            round.unfreezeCards(1);
            round.resetAttackField(1);
            playerOne.getHeroCard().setHasAttacked(0);
        } else if (round.getPlayerTurn() == 2) {
            round.setPlayerTurn(1);
            round.unfreezeCards(2);
            round.resetAttackField(2);
            playerTwo.getHeroCard().setHasAttacked(0);
        }
    }

    /**
     * Method that prints a card placed in a certain position from the game table
     * @param x the x coordinate
     * @param y the y coordinate
     * @param round information about the game
     * @param command information about the output
     */

    public void getCardAtPosition(final int x, final int y,
                                  final StartRound round, final ObjectNode command) {
        command.put("x", x);
        command.put("y", y);
        final int numThree = 3;
        if (x == 0 && y < round.getPlayerTwoBackRow().size()) {
            command.putPOJO("output", new Card(round.getPlayerTwoBackRow().get(y), 1));
        } else if (x == 1 && y < round.getPlayerTwoFrontRow().size()) {
            command.putPOJO("output", new Card(round.getPlayerTwoFrontRow().get(y), 1));
        } else if (x == 2 && y < round.getPlayerOneFrontRow().size()) {
            command.putPOJO("output", new Card(round.getPlayerOneFrontRow().get(y), 1));
        } else if (x == numThree && y < round.getPlayerOneBackRow().size()) {
            command.putPOJO("output", new Card(round.getPlayerOneBackRow().get(y), 1));
        } else {
            command.putPOJO("output", "No card available at that position.");
        }
    }

    /**
     * Method that prints the frozen card from the game table
     * @param command information about the output
     * @param arrNode the output
     * @param round information regarding the current round
     * @param playerOne information regarding the first player
     * @param playerTwo information regarding the second player
     */

    public void getFrozenCardsOnTable(final ObjectNode command, final ArrayNode arrNode,
                                      final StartRound round,
                                      final PlayerDeck playerOne,
                                      final PlayerDeck playerTwo) {
        ArrayList<Card> frozenCardsOnTable = new ArrayList<>();

        for (int i = 0; i < round.getPlayerOneBackRow().size(); i++) {
            Card card = new Card(round.getPlayerOneBackRow().get(i), 1);
            if (card.isFrozen()) {
                frozenCardsOnTable.add(card);
            }
        }

        for (int i = 0; i < round.getPlayerOneFrontRow().size(); i++) {
            Card card = new Card(round.getPlayerOneFrontRow().get(i), 1);
            if (card.isFrozen()) {
                frozenCardsOnTable.add(card);
            }
        }

        for (int i = 0; i < round.getPlayerTwoBackRow().size(); i++) {
            Card card = new Card(round.getPlayerTwoBackRow().get(i), 1);
            if (card.isFrozen()) {
                frozenCardsOnTable.add(card);
            }
        }

        for (int i = 0; i < round.getPlayerTwoFrontRow().size(); i++) {
            Card card = new Card(round.getPlayerTwoFrontRow().get(i), 1);
            if (card.isFrozen()) {
                frozenCardsOnTable.add(card);
            }
        }

        command.putPOJO("output", frozenCardsOnTable);
        arrNode.add(command);
    }

    /**
     * Method for the attack card command
     * @param i index used to get the Actions ArrayList
     * @param j index used to get the coordinates for the card
     * @param start the input
     * @param round information regarding the round
     * @param command information about the output
     * @param arr the output
     */

    public void cardUsesAttack(final int i, final int j,
                               final Input start, final StartRound round,
                               final ObjectNode command, final ArrayNode arr) {
        Coordinates cardAttacker = start.getGames().get(i).getActions().get(j).getCardAttacker();
        Coordinates cardAttacked = start.getGames().get(i).getActions().get(j).getCardAttacked();
        final int numThree = 3;
        int error = 0;
        if (cardAttacker.getX() == 0 || cardAttacker.getX() == 1) {
            Card attackerCard;
            if (cardAttacker.getX() == 0) {
                attackerCard = round.getPlayerTwoBackRow().get(cardAttacker.getY());
            } else {
                attackerCard = round.getPlayerTwoFrontRow().get(cardAttacker.getY());
            }
            if (attackerCard.isFrozen()) {
                Errors.getInstance().attackCardErrors(command, "Attacker card is frozen.",
                        arr, cardAttacker, cardAttacked);
                error = -1;
            }
            if (error == 0 && attackerCard.getHasAttacked() == 1) {
                Errors.getInstance().attackCardErrors(command,
                        "Attacker card has already attacked this turn.", arr,
                        cardAttacker, cardAttacked);
                error = -1;
            }
            if (error == 0 && cardAttacked.getX() == 0 || cardAttacked.getX() == 1) {
                Errors.getInstance().attackCardErrors(command,
                        "Attacked card does not belong to the enemy.", arr,
                        cardAttacker, cardAttacked);
                error = -1;
            }
            Card attackedCard = new Card();
            ArrayList<Card> tankCards = new ArrayList<>();
            if (cardAttacked.getX() == 2
                    && cardAttacked.getY() < round.getPlayerOneFrontRow().size()) {
                attackedCard = round.getPlayerOneFrontRow().get(cardAttacked.getY());
            } else if (cardAttacked.getX() == numThree
                    && cardAttacked.getY() < round.getPlayerOneBackRow().size()) {
                attackedCard = round.getPlayerOneBackRow().get(cardAttacked.getY());
            }
            if (error == 0) {
                for (int k = 0; k < round.getPlayerOneFrontRow().size(); k++) {
                    if (round.getPlayerOneFrontRow().get(k).getName().equals("Goliath")
                            || round.getPlayerOneFrontRow().get(k).getName().equals("Warden")) {
                        tankCards.add(round.getPlayerOneFrontRow().get(k));
                    }
                }
                for (int k = 0; k < round.getPlayerOneBackRow().size(); k++) {
                    if (round.getPlayerOneBackRow().get(k).getName().equals("Goliath")
                            || round.getPlayerOneBackRow().get(k).getName().equals("Warden")) {
                        tankCards.add(round.getPlayerOneBackRow().get(k));
                    }
                }
                int isValid = 0,  err = 0;
                for (Card tankCard : tankCards) {
                    if (!tankCard.equals(attackedCard)) {
                        isValid = 1;
                        break;
                    }
                }
                if (isValid == 1 && (!attackedCard.getName().equals("Warden")
                        && !attackedCard.getName().equals("Goliath"))) {
                    Errors.getInstance().attackCardErrors(command,
                            "Attacked card is not of type 'Tank'.", arr,
                            cardAttacker, cardAttacked);
                    err = -1;
                }
                if (err == 0) {
                    attackedCard.setHealth(attackedCard.getHealth()
                            - attackerCard.getAttackDamage());
                    attackerCard.setHasAttacked(1);
                    if (attackedCard.getHealth() <= 0 && cardAttacked.getX() == 2) {
                        round.getPlayerOneFrontRow().remove(cardAttacked.getY());
                    } else if (attackedCard.getHealth() <= 0 && cardAttacked.getX() == numThree) {
                        round.getPlayerOneBackRow().remove(cardAttacked.getY());
                    }
                }
            }
        } else {
            Card attackerCard = new Card();
            if (cardAttacker.getX() == 2) {
                attackerCard = round.getPlayerOneFrontRow().get(cardAttacker.getY());
            } else if (cardAttacker.getX() == numThree) {
                attackerCard = round.getPlayerOneBackRow().get(cardAttacker.getY());
            }
            if (attackerCard.isFrozen()) {
                Errors.getInstance().attackCardErrors(command, "Attacker card is frozen.",
                        arr, cardAttacker, cardAttacked);
                error = -1;
            }
            if (error == 0 && attackerCard.getHasAttacked() == 1) {
                Errors.getInstance().attackCardErrors(command,
                        "Attacker card has already attacked this turn.", arr,
                        cardAttacker, cardAttacked);
                error = -1;
            }
            if (error == 0 && cardAttacked.getX() == 2 || cardAttacked.getX() == numThree) {
                Errors.getInstance().attackCardErrors(command,
                        "Attacked card does not belong to the enemy.", arr,
                        cardAttacker, cardAttacked);
                error = -1;
            }
            Card attackedCard = new Card();
            ArrayList<Card> tankCards = new ArrayList<>();
            if (cardAttacked.getX() == 0) {
                attackedCard = round.getPlayerTwoBackRow().get(cardAttacked.getY());

            } else if (cardAttacked.getX() == 1) {
                attackedCard = round.getPlayerTwoFrontRow().get(cardAttacked.getY());
            }
            if (error == 0) {
                for (int k = 0; k < round.getPlayerTwoFrontRow().size(); k++) {
                    if (round.getPlayerTwoFrontRow().get(k).getName().equals("Goliath")
                            || round.getPlayerTwoFrontRow().get(k).getName().equals("Warden")) {
                        tankCards.add(round.getPlayerTwoFrontRow().get(k));
                    }
                }
                for (int k = 0; k < round.getPlayerTwoBackRow().size(); k++) {
                    if (round.getPlayerTwoBackRow().get(k).getName().equals("Goliath")
                            || round.getPlayerTwoBackRow().get(k).getName().equals("Warden")) {
                        tankCards.add(round.getPlayerTwoBackRow().get(k));
                    }
                }
                int isValid = 0;
                for (Card tankCard : tankCards) {
                    if (!tankCard.equals(attackedCard)) {
                        isValid = 1;
                    }
                }
                if (isValid == 1) {
                    Errors.getInstance().attackCardErrors(command,
                            "Attacked card is not of type 'Tank'.", arr,
                            cardAttacker, cardAttacked);
                    error = -1;
                }
                if (error == 0) {
                    attackedCard.setHealth(attackedCard.getHealth()
                            - attackerCard.getAttackDamage());
                    attackerCard.setHasAttacked(1);
                    if (attackedCard.getHealth() <= 0 && cardAttacked.getX() == 0) {
                        round.getPlayerTwoBackRow().remove(cardAttacked.getY());
                    } else if (attackedCard.getHealth() <= 0 && cardAttacked.getX() == 1) {
                        round.getPlayerTwoFrontRow().remove(cardAttacked.getY());
                    }
                }
            }
        }
    }

    /**
     * Method for the use card ability command
     * @param i index used to get the Actions ArrayList
     * @param j index used to get the coordinates for the cards
     * @param start the input
     * @param round information regarding the round
     * @param command information about the output
     * @param arr ArrayNode that will be the output
     */

    public void cardUsesAbility(final int i, final int j,
                                final Input start, final StartRound round,
                                final ObjectNode command, final ArrayNode arr) {
        Coordinates cardAttacker = start.getGames().get(i).getActions().get(j).getCardAttacker();
        Coordinates cardAttacked = start.getGames().get(i).getActions().get(j).getCardAttacked();
        final int numThree = 3;
        int error = 0;
        if (cardAttacker.getX() == 0 || cardAttacker.getX() == 1) {
            Card attackerCard;
            if (cardAttacker.getX() == 0) {
                attackerCard = round.getPlayerTwoBackRow().get(cardAttacker.getY());
            } else {
                attackerCard = round.getPlayerTwoFrontRow().get(cardAttacker.getY());
            }
            if (attackerCard.isFrozen()) {
                Errors.getInstance().attackCardErrors(command, "Attacker card is frozen.",
                        arr, cardAttacker, cardAttacked);
                error = -1;
            }
            if (error == 0 && attackerCard.getHasAttacked() == 1) {
                Errors.getInstance().attackCardErrors(command,
                        "Attacker card has already attacked this turn.", arr,
                        cardAttacker, cardAttacked);
                error = -1;
            }
            if (error == 0 && attackerCard.getName().equals("Disciple")) {
                if (cardAttacked.getX() == 2 || cardAttacked.getX() == numThree) {
                    Errors.getInstance().attackCardErrors(command,
                            "Attacked card does not belong to the current player.", arr,
                            cardAttacker, cardAttacked);
                    error = -1;
                }
            }
            if (error == 0 && (attackerCard.getName().equals("Miraj")
                    || attackerCard.getName().equals("The Ripper")
                    || attackerCard.getName().equals("The Cursed One"))) {
                if (cardAttacked.getX() == 0 || cardAttacked.getX() == 1) {
                    Errors.getInstance().attackCardErrors(command,
                            "Attacked card does not belong to the enemy.", arr,
                            cardAttacker, cardAttacked);
                    error = -1;
                }
            }
            Card attackedCard = new Card();
            if (cardAttacked.getX() == 2
                    && cardAttacked.getY() < round.getPlayerOneFrontRow().size()) {
                attackedCard = round.getPlayerOneFrontRow().get(cardAttacked.getY());
            } else if (cardAttacked.getX() == numThree
                    && cardAttacked.getY() < round.getPlayerOneBackRow().size()) {
                attackedCard = round.getPlayerOneBackRow().get(cardAttacked.getY());
            }
            if (error == 0) {
                ArrayList<Card> tankCards = new ArrayList<>();
                for (int k = 0; k < round.getPlayerOneBackRow().size(); k++) {
                    String cardName = round.getPlayerOneBackRow().get(k).getName();
                    if (cardName.equals("Goliath") || cardName.equals("Warden")) {
                        Card newCard = new Card(round.getPlayerOneBackRow().get(k), 1);
                        tankCards.add(newCard);
                    }
                }
                for (int k = 0; k < round.getPlayerOneFrontRow().size(); k++) {
                    String cardName = round.getPlayerOneFrontRow().get(k).getName();
                    if (cardName.equals("Goliath") || cardName.equals("Warden")) {
                        Card newCard = new Card(round.getPlayerOneFrontRow().get(k), 1);
                        tankCards.add(newCard);
                    }
                }
                int isValid = 0;
                int err = 0;
                if (attackerCard.getName().equals("The Ripper")
                        || attackerCard.getName().equals("Miraj")
                        || attackerCard.getName().equals("The Cursed One")) {
                    if (attackerCard.getName().equals("The Ripper")
                            || attackerCard.getName().equals("Miraj")
                            || attackerCard.getName().equals("The Cursed One")) {
                        if (attackedCard.getName().equals("Goliath")
                                || attackedCard.getName().equals("Warden")) {
                            isValid = 1;
                        }
                        if (error == 0 && tankCards.size() > 0 && isValid == 0) {
                            err = -1;
                            Errors.getInstance().attackCardErrors(command,
                                    "Attacked card is not of type 'Tank'.", arr,
                                    cardAttacker, cardAttacked);
                        }
                    }
                }
                if (error == 0 && err == 0) {
                    if (attackerCard.getName().equals("Disciple")) {
                        attackedCard.setHealth(attackedCard.getHealth() + 2);
                        attackerCard.setHasAttacked(1);
                    }
                    if (attackerCard.getName().equals("The Ripper")) {
                        int attack = attackedCard.getAttackDamage() - 2;
                        attackedCard.setAttackDamage(attack);
                        attackerCard.setHasAttacked(1);
                    }
                    if (attackerCard.getName().equals("Miraj")) {
                        int attackerHealth = attackerCard.getHealth();
                        int attackedHealth = attackedCard.getHealth();
                        attackedCard.setHealth(attackerHealth);
                        attackerCard.setHealth(attackedHealth);
                        attackerCard.setHasAttacked(1);
                    }
                    if (attackerCard.getName().equals("The Cursed One")) {
                        int health = attackedCard.getHealth();
                        int attack = attackedCard.getAttackDamage();
                        attackedCard.setAttackDamage(health);
                        attackedCard.setHealth(attack);
                        attackerCard.setHasAttacked(1);
                    }
                    if (attackedCard.getHealth() <= 0 && cardAttacked.getX() == 2) {
                        round.getPlayerOneFrontRow().remove(cardAttacked.getY());
                    } else if (attackedCard.getHealth() <= 0 && cardAttacked.getX() == numThree) {
                        round.getPlayerOneBackRow().remove(cardAttacked.getY());
                    }
                    if (attackedCard.getAttackDamage() < 0) {
                        attackedCard.setAttackDamage(0);
                    }
                }
            }
        } else {
            Card attackerCard = new Card();
            if (cardAttacker.getX() == 2) {
                attackerCard = round.getPlayerOneFrontRow().get(cardAttacker.getY());
            } else if (cardAttacker.getX() == numThree) {
                attackerCard = round.getPlayerOneBackRow().get(cardAttacker.getY());
            }
            if (attackerCard.isFrozen()) {
                Errors.getInstance().attackCardErrors(command, "Attacker card is frozen.",
                        arr, cardAttacker, cardAttacked);
                error = -1;
            }
            if (error == 0 && attackerCard.getHasAttacked() == 1) {
                Errors.getInstance().attackCardErrors(command,
                        "Attacker card has already attacked this turn.", arr,
                        cardAttacker, cardAttacked);
                error = -1;
            }
            if (error == 0 && attackerCard.getName().equals("Disciple")) {
                if (cardAttacked.getX() == 0 || cardAttacked.getX() == 1) {
                    Errors.getInstance().attackCardErrors(command,
                            "Attacked card does not belong to the current player.", arr,
                            cardAttacker, cardAttacked);
                    error = -1;
                }
            }
            if (error == 0 && (attackerCard.getName().equals("Miraj")
                    || attackerCard.getName().equals("The Ripper")
                    || attackerCard.getName().equals("The Cursed One"))) {
                if (cardAttacked.getX() == 2 || cardAttacked.getX() == numThree) {
                    Errors.getInstance().attackCardErrors(command,
                            "Attacked card does not belong to the enemy.", arr,
                            cardAttacker, cardAttacked);
                    error = -1;

                }
            }
            Card attackedCard = new Card();
            ArrayList<Card> tankCards = new ArrayList<>();
            if (cardAttacked.getX() == 0) {
                attackedCard = round.getPlayerTwoBackRow().get(cardAttacked.getY());
            } else if (cardAttacked.getX() == 1) {
                attackedCard = round.getPlayerTwoFrontRow().get(cardAttacked.getY());
            }
            if (error == 0) {
                for (int k = 0; k < round.getPlayerTwoFrontRow().size(); k++) {
                    String cardName = round.getPlayerTwoFrontRow().get(k).getName();
                    if (cardName.equals("Warden") || cardName.equals("Goliath")) {
                        Card newCard = new Card(round.getPlayerTwoFrontRow().get(k), 1);
                        tankCards.add(newCard);
                    }
                }
                int isValid = 0;
                int err = 0;
                if (attackerCard.getName().equals("The Ripper")
                        || attackerCard.getName().equals("Miraj")
                        || attackerCard.getName().equals("The Cursed One")) {
                    if (attackedCard.getName().equals("Goliath")
                            || attackedCard.getName().equals("Warden")) {
                        isValid = 1;
                    }
                    if (error == 0 && tankCards.size() > 0 && isValid == 0) {
                        err = -1;
                        Errors.getInstance().attackCardErrors(command,
                                "Attacked card is not of type 'Tank'.", arr,
                                cardAttacker, cardAttacked);
                    }
                }
                if (error == 0 && err == 0) {

                    if (attackerCard.getName().equals("Disciple")) {
                        attackedCard.setHealth(attackedCard.getHealth() + 2);
                        attackerCard.setHasAttacked(1);
                    }
                    if (attackerCard.getName().equals("The Ripper")) {
                        int attack = attackedCard.getAttackDamage() - 2;
                        attackedCard.setAttackDamage(attack);
                        attackerCard.setHasAttacked(1);
                    }
                    if (attackerCard.getName().equals("Miraj")) {
                        Card attackerCardAux = new Card(attackerCard, 1);
                        int attackerHealth = attackerCardAux.getHealth();
                        int attackedHealth = attackedCard.getHealth();
                        attackedCard.setHealth(attackerHealth);
                        attackerCard.setHealth(attackedHealth);
                        attackerCard.setHasAttacked(1);
                    }
                    if (attackerCard.getName().equals("The Cursed One")) {
                        int health = attackedCard.getHealth();
                        int attack = attackedCard.getAttackDamage();
                        attackedCard.setAttackDamage(health);
                        attackedCard.setHealth(attack);
                        attackerCard.setHasAttacked(1);
                    }
                    if (attackedCard.getHealth() <= 0 && cardAttacked.getX() == 0) {
                        round.getPlayerTwoBackRow().remove(cardAttacked.getY());
                    } else if (attackedCard.getHealth() <= 0 && cardAttacked.getX() == 1) {
                        round.getPlayerTwoFrontRow().remove(cardAttacked.getY());
                    }
                    if (attackedCard.getAttackDamage() < 0) {
                        attackedCard.setAttackDamage(0);
                    }
                }
            }
        }
    }

    /**
     * Method that puts the players' cards on the game table
     * @param cardsOnTable contains each of the player's cards
     * @param round information about the current game
     * @param command information for the output
     * @param arrNode the output
     */

    public void getCardsOnTable(final ArrayList<ArrayList<Card>> cardsOnTable,
                                final StartRound round, final ObjectNode command,
                                final ArrayNode arrNode) {
        cardsOnTable.add(new ArrayList<>(round.getPlayerTwoBackRow()));
        cardsOnTable.add(new ArrayList<>(round.getPlayerTwoFrontRow()));
        cardsOnTable.add(new ArrayList<>(round.getPlayerOneFrontRow()));
        cardsOnTable.add(new ArrayList<>(round.getPlayerOneBackRow()));

        round.setCardsOnTable(cardsOnTable);
        command.putPOJO("output", round.getCardsOnTable());
        arrNode.add(command);
    }

    /**
     * Method to use the hero ability command for the second player
     * @param i the index for the Actions ArrayList
     * @param j the index to get the affected row
     * @param start the input
     * @param round our information regarding the current round
     * @param command information for the output
     * @param arr the ArrayNode that will contain the output
     * @param playerOne the information regarding the first player
     */
    public void useHeroAbilityPlayerOne(final int i, final int j, final Input start,
                                        final StartRound round, final ObjectNode command,
                                        final ArrayNode arr, final PlayerDeck playerOne) {
        int affectedRow = start.getGames().get(i).getActions().get(j).getAffectedRow();
        final int numThree = 3;
        int error = 0;
        if (round.getPlayerOneMana() < playerOne.getHeroCard().getMana()) {
            Errors.getInstance().heroAbilityErrors(command,
                    "Not enough mana to use hero's ability.", arr, affectedRow);
            error = -1;
        }
        if (error == 0 && playerOne.getHeroCard().getHasAttacked() == 1) {
            Errors.getInstance().heroAbilityErrors(command,
                    "Hero has already attacked this turn.", arr, affectedRow);
            error = -1;
        }
        if (error == 0 && (playerOne.getHeroCard().getName().equals("Lord Royce")
                || playerOne.getHeroCard().getName().equals("Empress Thorina"))) {
            if (affectedRow == 2 || affectedRow == numThree) {
                Errors.getInstance().heroAbilityErrors(command,
                        "Selected row does not belong to the enemy.", arr, affectedRow);
                error = -1;
            }
        }
        if (error == 0 && (playerOne.getHeroCard().getName().equals("General Kocioraw")
                || playerOne.getHeroCard().getName().equals("King Mudface"))) {
            if (affectedRow == 0 || affectedRow == 1) {
                Errors.getInstance().heroAbilityErrors(command,
                        "Selected row does not belong to the current player.", arr, affectedRow);
                error = -1;
            }
        }
        if (error == 0) {
            if (playerOne.getHeroCard().getName().equals("Lord Royce")) {
                LordRoyce lordRoyce = new LordRoyce();
                if (affectedRow == 0) {
                    lordRoyce.subZero(round.getPlayerTwoBackRow());
                    playerOne.getHeroCard().setHasAttacked(1);
                }
                if (affectedRow == 1) {
                    lordRoyce.subZero(round.getPlayerTwoFrontRow());
                    playerOne.getHeroCard().setHasAttacked(1);
                }
            }
            if (playerOne.getHeroCard().getName().equals("Empress Thorina")) {
                EmpressThorina empressThorina = new EmpressThorina();
                if (affectedRow == 0) {
                    empressThorina.lowBlow(round.getPlayerTwoBackRow());
                    playerOne.getHeroCard().setHasAttacked(1);
                } else if (affectedRow == 1) {
                    empressThorina.lowBlow(round.getPlayerTwoFrontRow());
                    playerOne.getHeroCard().setHasAttacked(1);
                }
            }
            if (playerOne.getHeroCard().getName().equals("King Mudface")) {
                KingMudface kingMudface = new KingMudface();
                if (affectedRow == 2) {
                    kingMudface.earthBorn(round.getPlayerOneFrontRow());
                    playerOne.getHeroCard().setHasAttacked(1);
                } else if (affectedRow == numThree) {
                    kingMudface.earthBorn(round.getPlayerOneBackRow());
                    playerOne.getHeroCard().setHasAttacked(1);
                }
            }
            if (playerOne.getHeroCard().getName().equals("General Kocioraw")) {
                GeneralKocioraw generalKocioraw = new GeneralKocioraw();
                if (affectedRow == 2) {
                    generalKocioraw.bloodThirst(round.getPlayerOneFrontRow());
                    playerOne.getHeroCard().setHasAttacked(1);
                } else if (affectedRow == numThree) {
                    generalKocioraw.bloodThirst(round.getPlayerOneBackRow());
                    playerOne.getHeroCard().setHasAttacked(1);
                }
            }
            Card temp = new Card();
            temp.setMana(playerOne.getHeroCard().getMana());
            round.decreasePlayerMana(temp, 1);
        }
    }

    /**
     * Method to use the hero ability command for the second player
     * @param i the index for the Actions ArrayList
     * @param j the index to get the affected row
     * @param start the input
     * @param round our information regarding the current round
     * @param command information for the output
     * @param arr the ArrayNode that will contain the output
     * @param playerTwo the information regarding the second player
     */

    public void useHeroAbilityPlayerTwo(final int i, final int j, final Input start,
                                        final StartRound round, final ObjectNode command,
                                        final ArrayNode arr, final PlayerDeck playerTwo) {
        int affectedRowInput = start.getGames().get(i).getActions().get(j).getAffectedRow();
        final int numThree = 3;
        int error = 0;
        if (round.getPlayerTwoMana() < playerTwo.getHeroCard().getMana()) {
            Errors.getInstance().heroAbilityErrors(command,
                    "Not enough mana to use hero's ability.", arr, affectedRowInput);
            error = -1;
        }
        if (error == 0 && playerTwo.getHeroCard().getHasAttacked() == 1) {
            Errors.getInstance().heroAbilityErrors(command,
                    "Hero has already attacked this turn.", arr, affectedRowInput);
            error = -1;
        }
        if (error == 0 && (playerTwo.getHeroCard().getName().equals("Lord Royce")
                || playerTwo.getHeroCard().getName().equals("Empress Thorina"))) {
            if (affectedRowInput == 0 || affectedRowInput == 1) {
                Errors.getInstance().heroAbilityErrors(command,
                        "Selected row does not belong to the enemy.", arr, affectedRowInput);
                error = -1;
            }
        }
        if (error == 0 && (playerTwo.getHeroCard().getName().equals("General Kocioraw")
                || playerTwo.getHeroCard().getName().equals("King Mudface"))) {
            if (affectedRowInput == 2 || affectedRowInput == numThree) {
                Errors.getInstance().heroAbilityErrors(command,
                        "Selected row does not belong to the current player.",
                        arr, affectedRowInput);
                error = -1;
            }
        }

        if (error == 0) {
            if (playerTwo.getHeroCard().getName().equals("Lord Royce")) {
                LordRoyce lordRoyce = new LordRoyce();
                if (affectedRowInput == 2) {
                    lordRoyce.subZero(round.getPlayerOneFrontRow());
                    playerTwo.getHeroCard().setHasAttacked(1);
                }
                if (affectedRowInput == numThree) {
                    lordRoyce.subZero(round.getPlayerOneBackRow());
                    playerTwo.getHeroCard().setHasAttacked(1);
                }
            }
            if (playerTwo.getHeroCard().getName().equals("Empress Thorina")) {
                EmpressThorina empressThorina = new EmpressThorina();
                if (affectedRowInput == 2) {
                    empressThorina.lowBlow(round.getPlayerOneFrontRow());
                    playerTwo.getHeroCard().setHasAttacked(1);
                } else {
                    empressThorina.lowBlow(round.getPlayerOneBackRow());
                    playerTwo.getHeroCard().setHasAttacked(1);
                }
            }
            if (playerTwo.getHeroCard().getName().equals("King Mudface")) {
                KingMudface kingMudface = new KingMudface();
                if (affectedRowInput == 0) {
                    kingMudface.earthBorn(round.getPlayerTwoBackRow());
                    playerTwo.getHeroCard().setHasAttacked(1);
                } else {
                    kingMudface.earthBorn(round.getPlayerTwoFrontRow());
                    playerTwo.getHeroCard().setHasAttacked(1);
                }
            }
            if (playerTwo.getHeroCard().getName().equals("General Kocioraw")) {
                GeneralKocioraw generalKocioraw = new GeneralKocioraw();
                if (affectedRowInput == 0) {
                    generalKocioraw.bloodThirst(round.getPlayerTwoBackRow());
                    playerTwo.getHeroCard().setHasAttacked(1);
                } else {
                    generalKocioraw.bloodThirst(round.getPlayerTwoFrontRow());
                    playerTwo.getHeroCard().setHasAttacked(1);
                }
            }
            Card temp = new Card();
            temp.setMana(playerTwo.getHeroCard().getMana());
            round.decreasePlayerMana(temp, 2);
        }
    }

    /**
     * Method that gets the mana of the desired player
     * @param playerIdx the index of the player
     * @param round the round
     * @param command information for the output
     */

    public void getPlayerMana(final int playerIdx, final StartRound round,
                              final ObjectNode command) {
        command.put("playerIdx", playerIdx);
        if (playerIdx == 1) {
            command.put("output", round.getPlayerOneMana());
        } else {
            command.put("output", round.getPlayerTwoMana());
        }
    }
}
