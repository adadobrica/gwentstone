package main.Start;
import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.*;

import java.util.ArrayList;

import main.Decks.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import main.Player.PlayerDeck;

public final class StartGame {
    private final Input start;
    private int oneWins = 0;
    private int twoWins = 0;

    public StartGame(final Input input) {
        start = input;
    }

    /**
     * Method where the game starts
     * @param arrNode the output of the game
     * @param objectMapper maps the output into the arrayNode
     * @return the output of the game
     */
    public ArrayNode gameOn(final ArrayNode arrNode, final ObjectMapper objectMapper) {
        int gameSize = start.getGames().size();

        for (int i = 0; i < gameSize; i++) {
            int actionsSize = start.getGames().get(i).getActions().size();
            int playerOneDeckIndex = start.getGames().get(i).getStartGame().getPlayerOneDeckIdx();
            int playerTwoDeckIndex = start.getGames().get(i).getStartGame().getPlayerTwoDeckIdx();

            StartRound round = new StartRound();
            round.setPlayerTurn(start.getGames().get(i).getStartGame().getStartingPlayer());

            ArrayList<CardInput> playerOneDecks, playerTwoDecks;
            playerOneDecks = start.getPlayerOneDecks().getDecks().get(playerOneDeckIndex);
            PlayerDeck playerOne = new PlayerDeck(playerOneDeckIndex, playerOneDecks);
            playerTwoDecks = start.getPlayerTwoDecks().getDecks().get(playerTwoDeckIndex);
            PlayerDeck playerTwo = new PlayerDeck(playerTwoDeckIndex, playerTwoDecks);

            ArrayList<Card> playerDecksOne = new ArrayList<>();
            ArrayList<Card> playerDecksTwo = new ArrayList<>();
            ArrayList<CardInput> playerDecksInputOne, playerDecksInputTwo;

            int shuffleSeed = start.getGames().get(i).getStartGame().getShuffleSeed();

            playerDecksInputOne = start.getPlayerOneDecks().getDecks().get(playerOneDeckIndex);
            playerDecksOne = playerOne.copyFromDecksAndShuffle(playerDecksInputOne,
                    playerDecksOne, shuffleSeed);
            playerDecksInputTwo = start.getPlayerTwoDecks().getDecks().get(playerTwoDeckIndex);
            playerDecksTwo = playerTwo.copyFromDecksAndShuffle(playerDecksInputTwo,
                    playerDecksTwo, shuffleSeed);
            ArrayList<ArrayList<Card>> cardsOnTable = new ArrayList<>();

            CardInput heroCardOneInput = start.getGames().get(i).getStartGame().getPlayerOneHero();
            CardInput heroCardTwoInput = start.getGames().get(i).getStartGame().getPlayerTwoHero();
            Card heroOne = new Card(heroCardOneInput);
            Card heroTwo = new Card(heroCardTwoInput);
            playerOne.setHeroCard(heroOne);
            playerTwo.setHeroCard(heroTwo);

            for (int j = 0; j < actionsSize; j++) {
                ObjectNode command = objectMapper.createObjectNode();
                String actionCommand = start.getGames().get(i).getActions().get(j).getCommand();

                if (actionCommand.equals("getPlayerDeck")) {
                    command.put("command", actionCommand);
                    Actions.getInstance().getPlayerDeck(i, j, start, command,
                            arrNode, playerDecksOne, playerDecksTwo);
                }
                if (actionCommand.equals("getPlayerHero")) {
                    command.put("command", actionCommand);
                    Actions.getInstance().getPlayerHero(i, j, start, command,
                            arrNode, playerOne, playerTwo);
                }
                if (actionCommand.equals("getPlayerTurn")) {
                    command.put("command", actionCommand);
                    command.put("output", round.getPlayerTurn());
                    arrNode.add(command);
                }
                if (actionCommand.equals("endPlayerTurn")) {
                    command.put("command", actionCommand);
                    Actions.getInstance().endPlayerTurn(round, playerOne, playerTwo,
                            playerDecksOne, playerDecksTwo);
                }
                if (actionCommand.equals("placeCard")) {
                    command.put("command", actionCommand);
                    placeCard(i, j, round, playerOne, playerTwo, command, arrNode);
                }
                if (actionCommand.equals("getCardsInHand")) {
                    command.put("command", actionCommand);
                    getCardsInHandCommand(i, j, playerOne, playerTwo, command, arrNode);
                }
                if (actionCommand.equals("getPlayerMana")) {
                    command.put("command", actionCommand);
                    int playerIdx = start.getGames().get(i).getActions().get(j).getPlayerIdx();
                    Actions.getInstance().getPlayerMana(playerIdx, round, command);
                    arrNode.add(command);
                }
                if (actionCommand.equals("getCardsOnTable")) {
                    command.put("command", actionCommand);
                    Actions.getInstance().getCardsOnTable(cardsOnTable, round, command, arrNode);
                }
                if (actionCommand.equals("getEnvironmentCardsInHand")) {
                    command.put("command", actionCommand);
                    getEnvironmentCardsInHandCommand(i, j, command, arrNode, playerOne, playerTwo);
                }
                if (actionCommand.equals("getCardAtPosition")) {
                    command.put("command", actionCommand);
                    int x = start.getGames().get(i).getActions().get(j).getX();
                    int y = start.getGames().get(i).getActions().get(j).getY();
                    Actions.getInstance().getCardAtPosition(x, y, round, command);
                    arrNode.add(command);
                }
                if (actionCommand.equals("useEnvironmentCard")) {
                    command.put("command", actionCommand);
                    useEnvironmentCardCommand(command, arrNode, playerOne, playerTwo, round, i, j);
                }
                if (actionCommand.equals("getFrozenCardsOnTable")) {
                    command.put("command", actionCommand);
                    Actions.getInstance().getFrozenCardsOnTable(command, arrNode, round,
                            playerOne, playerTwo);
                }
                if (actionCommand.equals("cardUsesAttack")) {
                    command.put("command", actionCommand);
                    Actions.getInstance().cardUsesAttack(i, j, start, round, command, arrNode);
                }
                if (actionCommand.equals("cardUsesAbility")) {
                    command.put("command", actionCommand);
                    Actions.getInstance().cardUsesAbility(i, j, start, round, command, arrNode);
                }
                if (actionCommand.equals("useAttackHero")) {
                    useAttackHero(i, j, start, round, command, arrNode, playerOne, playerTwo);
                }
                if (actionCommand.equals("useHeroAbility")) {
                    command.put("command", actionCommand);
                    if (round.getPlayerTurn() == 1) {
                        Actions.getInstance().useHeroAbilityPlayerOne(i, j, start, round,
                                command, arrNode, playerOne);
                    } else if (round.getPlayerTurn() == 2) {
                        Actions.getInstance().useHeroAbilityPlayerTwo(i, j, start, round,
                                command, arrNode, playerTwo);
                    }
                }
                if (actionCommand.equals("getPlayerOneWins")) {
                    command.put("command", actionCommand);
                    command.put("output", oneWins);
                    arrNode.add(command);
                }
                if (actionCommand.equals("getPlayerTwoWins")) {
                    command.put("command", actionCommand);
                    command.put("output", twoWins);
                    arrNode.add(command);
                }
                if (actionCommand.equals("getTotalGamesPlayed")) {
                    command.put("command", actionCommand);
                    command.put("output", oneWins + twoWins);
                    arrNode.add(command);
                }
            }
        }
        return arrNode;
    }

    /**
     * Handler method for the 'getEnvironmentCardsInHand' command, represents the
     * starting point of the command, choosing between the two players
     * @param i the index of the Actions list we need
     * @param j the index required to get the player index
     * @param command objectNode storing information about the output
     * @param arrNode where the output will be added
     * @param playerOne information about the first player
     * @param playerTwo information about the second player
     */
    public void getEnvironmentCardsInHandCommand(final int i, final int j, final ObjectNode command,
                                                 final ArrayNode arrNode,
                                                 final PlayerDeck playerOne,
                                                 final PlayerDeck playerTwo) {
        ArrayList<Card> envCards;
        int playerIdx = start.getGames().get(i).getActions().get(j).getPlayerIdx();
        command.put("playerIdx", playerIdx);
        if (playerIdx == 1) {
            envCards = setEnvCards(playerOne, command);
            command.putPOJO("output", envCards);
        } else {
            envCards = setEnvCards(playerTwo, command);
            command.putPOJO("output", envCards);
        }
        arrNode.add(command);
    }

    /**
     * Handler method for the 'getCardsInHandCommand', it chooses between the two players
     * and outputs their respective playing cards
     * @param i the index needed to for the actions list
     * @param j the index needed for the player index
     * @param playerOne information regarding the first player
     * @param playerTwo information regarding the second player
     * @param command objectNode storing information about the output
     * @param arrNode where the output will be added at the end
     */

    public void getCardsInHandCommand(final int i, final int j,
                                      final PlayerDeck playerOne, final PlayerDeck playerTwo,
                                      final ObjectNode command, final ArrayNode arrNode) {
        int playerIdx = start.getGames().get(i).getActions().get(j).getPlayerIdx();
        ArrayList<Card> cardsInHand;
        if (playerIdx == 1) {
            cardsInHand = getCardsInHand(command, playerOne, 1);
        } else {
            cardsInHand = getCardsInHand(command, playerTwo, 2);
        }
        command.putPOJO("output", cardsInHand);
        arrNode.add(command);
    }

    /**
     * Method that places a card from a player's deck onto the game table
     * @param i the index needed for the actions list
     * @param j the index needed for the hand index (to place the desired hand)
     * @param round information regarding the current game
     * @param playerOne information regarding the first player
     * @param playerTwo information regarding the second player
     * @param command objectNode storing information about the output
     * @param arrNode where the output will be added at the end
     */

    public void placeCard(final int i, final int j,
                          final StartRound round, final PlayerDeck playerOne,
                          final PlayerDeck playerTwo, final ObjectNode command,
                          final ArrayNode arrNode) {
        int handIdx = start.getGames().get(i).getActions().get(j).getHandIdx();
        int errorFlag;
        if (round.getPlayerTurn() == 1) {
            if (handIdx < playerOne.getPlayingCards().size()) {
                Card placedCard = playerOne.getPlayingCards().get(handIdx);
                errorFlag = Errors.getInstance().checkPlaceCardErrors(placedCard, command,
                        round.getPlayerOneMana(), round.getPlayerOneFrontRow().size(),
                        round.getPlayerOneBackRow().size());

                if (errorFlag == -1) {
                    command.put("handIdx", handIdx);
                    arrNode.add(command);
                } else if (errorFlag == 0) {
                    playerOne.getPlayingCards().remove(handIdx);
                    round.decreasePlayerMana(placedCard, 1);
                    round.addCardsOnTable(placedCard, 1);
                }
            }
        } else {
            if (handIdx < playerTwo.getPlayingCards().size()) {
                Card placedCard = playerTwo.getPlayingCards().get(handIdx);
                errorFlag = Errors.getInstance().checkPlaceCardErrors(placedCard, command,
                        round.getPlayerTwoMana(), round.getPlayerTwoFrontRow().size(),
                        round.getPlayerTwoBackRow().size());
                if (errorFlag == -1) {
                    command.put("handIdx", handIdx);
                    arrNode.add(command);
                } else if (errorFlag == 0) {
                    playerTwo.getPlayingCards().remove(handIdx);
                    round.decreasePlayerMana(placedCard, 2);
                    round.addCardsOnTable(placedCard, 2);
                }
            }
        }
    }

    /**
     * Handler method for the 'useEnvironmentCard' command, prints errors if it's needed,
     * else the player uses an environment card against its opponent
     * @param command objectNode storing information about the output
     * @param arrNode where the output will be added at the end
     * @param playerOne information regarding the first player
     * @param playerTwo information regarding the second player
     * @param round information regarding the current game
     * @param i the index needed for the actions list
     * @param j the index needed for the affected row and the chosen card index
     */
    public void useEnvironmentCardCommand(final ObjectNode command,
                                          final ArrayNode arrNode,
                                          final PlayerDeck playerOne,
                                          final PlayerDeck playerTwo,
                                          final StartRound round,
                                          final int i, final int j) {
        int handIdx = start.getGames().get(i).getActions().get(j).getHandIdx();
        int affectedRow = start.getGames().get(i).getActions().get(j).getAffectedRow();
        int error = 0;
        final int threeNum = 3;
        int size1 = playerOne.getPlayingCards().size();
        int size2 = playerTwo.getPlayingCards().size();
        if (round.getPlayerTurn() == 1) {
            if (handIdx < size1) {
                Card card = new Card(playerOne.getPlayingCards().get(handIdx), 1);
                if (!card.getName().equals("Winterfell")
                        && !card.getName().equals("Firestorm")
                        && !card.getName().equals("Heart Hound")) {
                    Errors.getInstance().environmentCardErrors(command,
                            "Chosen card is not of type environment.",
                            arrNode, affectedRow, handIdx);
                    error = -1;
                }
                if (error == 0 && card.getMana() > round.getPlayerOneMana()) {
                    Errors.getInstance().environmentCardErrors(command,
                            "Not enough mana to use environment card.",
                            arrNode, affectedRow, handIdx);
                    error = -1;
                }
            }
            if (error == 0 && affectedRow == 2 || affectedRow == threeNum) {
                Errors.getInstance().environmentCardErrors(command,
                        "Chosen row does not belong to the enemy.",
                        arrNode, affectedRow, handIdx);
                error = -1;
            }
        }
        if (round.getPlayerTurn() == 2) {
            if (handIdx < size2) {
                Card card = new Card(playerTwo.getPlayingCards().get(handIdx), 1);
                if (!card.getName().equals("Winterfell")
                        && !card.getName().equals("Firestorm")
                        && !card.getName().equals("Heart Hound")) {
                    Errors.getInstance().environmentCardErrors(command,
                            "Chosen card is not of type environment.",
                            arrNode, affectedRow, handIdx);
                    error = -1;
                }
                if (error == 0 && card.getMana() > round.getPlayerTwoMana()) {
                    Errors.getInstance().environmentCardErrors(command,
                            "Not enough mana to use environment card.",
                            arrNode, affectedRow, handIdx);
                    error = -1;
                }
            }
            if (error == 0 && affectedRow == 0 || affectedRow == 1) {
                Errors.getInstance().environmentCardErrors(command,
                        "Chosen row does not belong to the enemy.",
                        arrNode, affectedRow, handIdx);
                error = -1;
            }
        }
        if (error == 0) {
            int err = 0;
            useEnvironmentCard(handIdx, affectedRow, command, playerOne, playerTwo,
                    round.getPlayerTurn(), round, arrNode, err);
        }
    }

    /**
     * Method for the attack hero command
     * @param i the index of the action ArrayList that we will need
     * @param j the index used to get the coordinates for the attacker card
     * @param start the input
     * @param round information regarding the game round
     * @param command objectNode storing information about the output
     * @param arr the ArrayNode that will be the output
     * @param playerOne information regarding the first player
     * @param playerTwo infomation regarding the second player
     */

    public void useAttackHero(final int i, final int j, final Input start,
                              final StartRound round, final ObjectNode command,
                              final ArrayNode arr, final PlayerDeck playerOne,
                              final PlayerDeck playerTwo) {
        Coordinates cardAttacker = start.getGames().get(i).getActions().get(j).getCardAttacker();
        if (cardAttacker.getX() == 0 || cardAttacker.getX() == 1) {
            Card attackerCard;
            int error = 0;
            if (cardAttacker.getX() == 0) {
                attackerCard = round.getPlayerTwoBackRow().get(cardAttacker.getY());
            } else {
                attackerCard = round.getPlayerTwoFrontRow().get(cardAttacker.getY());
            }
            if (attackerCard.isFrozen()) {
                Errors.getInstance().attackHeroErrors(command, "Attacker card is frozen.",
                        arr, cardAttacker);
                error = -1;
            }
            if (error == 0 && attackerCard.getHasAttacked() == 1) {
                Errors.getInstance().attackHeroErrors(command, "Attacker card has already"
                                + " attacked this turn.",
                        arr, cardAttacker);
                error = -1;
            }
            if (error == 0) {
                ArrayList<Card> tankCards = new ArrayList<>();
                for (int k = 0; k < round.getPlayerOneBackRow().size(); k++) {
                    String name = round.getPlayerOneBackRow().get(k).getName();
                    if (name.equals("Warden") || name.equals("Goliath")) {
                        tankCards.add(round.getPlayerOneBackRow().get(k));
                    }
                }
                for (int k = 0; k < round.getPlayerOneFrontRow().size(); k++) {
                    String name = round.getPlayerOneFrontRow().get(k).getName();
                    if (name.equals("Warden") || name.equals("Goliath")) {
                        tankCards.add(round.getPlayerOneFrontRow().get(k));
                    }
                }
                if (tankCards.size() > 0) {
                    error = -1;
                    Errors.getInstance().attackHeroErrors(command,
                            "Attacked card is not of type 'Tank'.", arr, cardAttacker);
                }
                if (error == 0) {
                    HeroCard heroCard = new HeroCard(playerOne.getHeroCard(), 1);
                    heroCard.setHealth(heroCard.getHealth() - attackerCard.getAttackDamage());
                    playerOne.setHeroCard(heroCard);
                    attackerCard.setHasAttacked(1);
                    if (playerOne.getHeroCard().getHealth() < 0) {
                        playerOne.getHeroCard().setHealth(0);
                    }
                    if (playerOne.getHeroCard().getHealth() <= 0) {
                        command.put("gameEnded", "Player two killed the enemy hero.");
                        arr.add(command);
                        twoWins++;
                    }
                }
            }
        } else {
            Card attackerCard;
            int error = 0;
            if (cardAttacker.getX() == 2) {
                attackerCard = round.getPlayerOneFrontRow().get(cardAttacker.getY());
            } else {
                attackerCard = round.getPlayerOneBackRow().get(cardAttacker.getY());
            }
            if (attackerCard.isFrozen()) {
                Errors.getInstance().attackHeroErrors(command, "Attacker card is frozen.",
                        arr, cardAttacker);
                error = -1;
            }
            if (error == 0 && attackerCard.getHasAttacked() == 1) {
                Errors.getInstance().attackHeroErrors(command, "Attacker card has already"
                                + " attacked this turn.",
                        arr, cardAttacker);
                error = -1;
            }
            if (error == 0) {
                ArrayList<Card> tankCards = new ArrayList<>();
                for (int k = 0; k < round.getPlayerTwoBackRow().size(); k++) {
                    String name = round.getPlayerTwoBackRow().get(k).getName();
                    if (name.equals("Warden") || name.equals("Goliath")) {
                        tankCards.add(round.getPlayerTwoBackRow().get(k));
                    }
                }
                for (int k = 0; k < round.getPlayerTwoFrontRow().size(); k++) {
                    String name = round.getPlayerTwoFrontRow().get(k).getName();
                    if (name.equals("Warden") || name.equals("Goliath")) {
                        tankCards.add(round.getPlayerTwoFrontRow().get(k));
                    }
                }
                if (tankCards.size() > 0) {
                    error = -1;
                    Errors.getInstance().attackHeroErrors(command,
                            "Attacked card is not of type 'Tank'.", arr, cardAttacker);
                }
                if (error == 0) {
                    playerTwo.getHeroCard().setHealth(playerTwo.getHeroCard().getHealth()
                            - attackerCard.getAttackDamage());
                    attackerCard.setHasAttacked(1);
                    if (playerTwo.getHeroCard().getHealth() < 0) {
                        playerTwo.getHeroCard().setHealth(0);
                    }
                    if (playerTwo.getHeroCard().getHealth() <= 0) {
                        command.put("gameEnded", "Player one killed the enemy hero.");
                        arr.add(command);
                        oneWins++;
                    }
                }
            }
        }
    }

    /**
     * Method for the use environment card command
     * @param handIdx the index to know which environment card to get
     * @param affectedRow the row that will be affected
     * @param command contains information about the output
     * @param playerOne information about the first player
     * @param playerTwo information about the second player
     * @param playerIdx the index to know which player will attack
     * @param round information about the game round
     * @param arr the output
     * @param err a variable to signal whether an error occurred
     */
    public void useEnvironmentCard(final int handIdx, final int affectedRow,
                                   final ObjectNode command, final PlayerDeck playerOne,
                                   final PlayerDeck playerTwo, final int playerIdx,
                                   final StartRound round, final ArrayNode arr, int err) {
        final int numFive = 5, numThree = 3;
        if (playerIdx == 1) {
            int envCardsSize = playerOne.getPlayingCards().size();
            if (handIdx < envCardsSize) {
                Card envCard = new Card(playerOne.getPlayingCards().get(handIdx), 1);
                if (envCard.getName().equals("Winterfell")) {
                    Winterfell w = new Winterfell(envCard, 1);
                    if (affectedRow == 0) {
                        w.specialAbility(round.getPlayerTwoBackRow());
                    }
                    if (affectedRow == 1) {
                        w.specialAbility(round.getPlayerTwoFrontRow());
                    }
                }
                if (envCard.getName().equals("Firestorm")) {
                    FireStorm fS = new FireStorm(envCard, 1);
                    if (affectedRow == 0) {
                        fS.specialAbility(round.getPlayerTwoBackRow());
                    }
                    if (affectedRow == 1) {
                        fS.specialAbility(round.getPlayerTwoFrontRow());
                    }
                }
                if (envCard.getName().equals("Heart Hound")) {
                    HeartHound hH = new HeartHound(envCard, 1);
                    if (affectedRow == 0) {
                        if (round.getPlayerOneBackRow().size() == numFive) {
                            Errors.getInstance().environmentCardErrors(command,
                                    "Cannot steal enemy card since the player's row is full.",
                                    arr, affectedRow, handIdx);
                            err = -1;
                        } else {
                            hH.specialAbility(round.getPlayerTwoBackRow(),
                                    round.getPlayerOneBackRow());
                        }
                    }
                    if (affectedRow == 1) {
                        if (round.getPlayerOneFrontRow().size() == numFive) {
                            Errors.getInstance().environmentCardErrors(command,
                                    "Cannot steal enemy card since the player's row is full.",
                                    arr, affectedRow, handIdx);
                            err = -1;
                        } else {
                            hH.specialAbility(round.getPlayerTwoFrontRow(),
                                    round.getPlayerOneFrontRow());
                        }
                    }
                }
                if (err == 0) {
                    round.decreasePlayerMana(envCard, 1);
                    playerOne.getPlayingCards().remove(handIdx);
                }
            }
        }
        if (playerIdx == 2) {
            int envCardsSize = playerTwo.getPlayingCards().size();
            if (handIdx < envCardsSize) {
                Card envCard = new Card(playerTwo.getPlayingCards().get(handIdx), 1);
                if ("Winterfell".equals(envCard.getName())) {
                    Winterfell w = new Winterfell(envCard, 1);
                    if (affectedRow == 2) {
                        w.specialAbility(round.getPlayerOneFrontRow());
                    }
                    if (affectedRow == numThree) {
                        w.specialAbility(round.getPlayerOneBackRow());
                    }
                } else if ("Firestorm".equals(envCard.getName())) {
                    FireStorm fS = new FireStorm(envCard, 1);
                    if (affectedRow == 2) {
                        fS.specialAbility(round.getPlayerOneFrontRow());
                    }
                    if (affectedRow == numThree) {
                        fS.specialAbility(round.getPlayerOneBackRow());
                    }
                } else if ("Heart Hound".equals(envCard.getName())) {
                    HeartHound hH = new HeartHound(envCard, 1);
                    if (affectedRow == 2) {
                        if (round.getPlayerTwoFrontRow().size() == numFive) {
                            Errors.getInstance().environmentCardErrors(command,
                                    "Cannot steal enemy card since the player's row is full.",
                                    arr, affectedRow, handIdx);
                            err = -1;
                        } else {
                            hH.specialAbility(round.getPlayerOneFrontRow(),
                                    round.getPlayerTwoFrontRow());
                        }
                    }
                    if (affectedRow == numThree) {
                        if (round.getPlayerTwoBackRow().size() == numFive) {
                            Errors.getInstance().environmentCardErrors(command,
                                    "Cannot steal enemy card since the player's row is full.",
                                    arr, affectedRow, handIdx);
                            err = -1;
                        } else {
                            hH.specialAbility(round.getPlayerOneBackRow(),
                                    round.getPlayerTwoBackRow());
                        }
                    }
                }
                if (err == 0) {
                    round.decreasePlayerMana(envCard, 2);
                    playerTwo.getPlayingCards().remove(handIdx);
                }
            }
        }
    }

    /**
     * Method that returns an ArrayList of Environment Cards from the
     * player's current hand
     * @param player information about the player
     * @param command contains information about the output
     * @return the player's environment cards
     */

    public ArrayList<Card> setEnvCards(final PlayerDeck player, final ObjectNode command) {
        ArrayList<Card> envCards = new ArrayList<>();
        for (int i = 0; i < player.getPlayingCards().size(); i++) {
            String cardName = player.getPlayingCards().get(i).getName();
            Card card = new Card(player.getPlayingCards().get(i), 1);
            if (cardName.equals("Winterfell")) {
                Winterfell w = new Winterfell(card, 1);
                envCards.add(w);
            }
            if (cardName.equals("Heart Hound")) {
                HeartHound hH = new HeartHound(card, 1);
                envCards.add(hH);
            }
            if (cardName.equals("Firestorm")) {
                FireStorm fS = new FireStorm(card, 1);
                envCards.add(fS);
            }
        }
        player.setEnvironmentCardsInHand(envCards);
        return envCards;
    }

    /**
     * Method used to get the playing cards from the deck of the player
     * @param command contains information about the output
     * @param player the desired player
     * @param playerIdx the index of the player
     * @return returns an ArrayList of cards
     */

    public ArrayList<Card> getCardsInHand(final ObjectNode command,
                                          final PlayerDeck player, final int playerIdx) {
        command.put("playerIdx", playerIdx);
        ArrayList<Card> cardsInHand = new ArrayList<>();
        for (int i = 0; i < player.getPlayingCards().size(); i++) {
            Card newCard = new Card(player.getPlayingCards().get(i), 1);
            switch (newCard.getName()) {
                case "Winterfell" -> {
                    Winterfell envCard = new Winterfell(newCard, 1);
                    cardsInHand.add(envCard);
                }
                case "Firestorm" -> {
                    FireStorm envCard = new FireStorm(newCard, 1);
                    cardsInHand.add(envCard);
                }
                case "Heart Hound" -> {
                    HeartHound envCard = new HeartHound(newCard, 1);
                    cardsInHand.add(envCard);
                }
                default -> cardsInHand.add(newCard);
            }

        }
        return cardsInHand;
    }
}
