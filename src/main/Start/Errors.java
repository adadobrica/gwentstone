package main.Start;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;
import main.Decks.Card;

public final class Errors {
    private static Errors instance;
    private Errors() { }

    /**
     * Defining a getInstance() operation needed to implement a Singleton pattern
     * @return a unique instance of the class
     */
    public static synchronized Errors getInstance() {
        if (instance == null) {
            instance = new Errors();
        }
        return instance;
    }

    /**
     * Method that handles the errors for the 'useHeroAbility' command
     * @param command information for the output
     * @param string a string containing the error
     * @param arrNode the output
     * @param row row to be printed alongside the error
     */

    public void heroAbilityErrors(final ObjectNode command, final String string,
                                  final ArrayNode arrNode, final int row) {
        if (string.equals("Not enough mana to use hero's ability.")) {
            command.put("affectedRow", row);
            command.put("error", "Not enough mana to use hero's ability.");
            arrNode.add(command);
        }
        if (string.equals("Hero has already attacked this turn.")) {
            command.put("affectedRow", row);
            command.put("error", "Hero has already attacked this turn.");
            arrNode.add(command);
        }
        if (string.equals("Selected row does not belong to the enemy.")) {
            command.put("affectedRow", row);
            command.put("error", "Selected row does not belong to the enemy.");
            arrNode.add(command);
        }
        if (string.equals("Selected row does not belong to the current player.")) {
            command.put("affectedRow", row);
            command.put("error", "Selected row does not belong to the current player.");
            arrNode.add(command);
        }
    }

    /**
     * Method that handles the errors for the 'useEnvironmentCard' command
     * @param command stores information for the output
     * @param string string containing the error message
     * @param arrNode the output
     * @param row information printed alongside the error
     * @param idx information printed alongside the error
     */

    public void environmentCardErrors(final ObjectNode command,
                                      final String string, final ArrayNode arrNode,
                                      final int row, final int idx) {
        if (string.equals("Cannot steal enemy card since the player's row is full.")) {
            command.put("handIdx", idx);
            command.put("affectedRow", row);
            command.put("error", "Cannot steal enemy card since"
                    + " the player's row is full.");
            arrNode.add(command);
        }
        if (string.equals("Chosen card is not of type environment.")) {
            command.put("handIdx", idx);
            command.put("affectedRow", row);
            command.put("error", "Chosen card is not of type environment.");
            arrNode.add(command);
        }
        if (string.equals("Not enough mana to use environment card.")) {
            command.put("handIdx", idx);
            command.put("affectedRow", row);
            command.put("error", "Not enough mana to use environment card.");
            arrNode.add(command);
        }
        if (string.equals("Chosen row does not belong to the enemy.")) {
            command.put("handIdx", idx);
            command.put("affectedRow", row);
            command.put("error", "Chosen row does not belong to the enemy.");
            arrNode.add(command);
        }
    }

    /**
     * Method that handles errors for the 'attackCard' command
     * @param command stores information for the output
     * @param string string containing the error message
     * @param arr the output
     * @param attacker information printed alongside the error
     * @param attacked information printed alongside the error
     */

    public void attackCardErrors(final ObjectNode command, final String string,
                                 final ArrayNode arr, final Coordinates attacker,
                                 final Coordinates attacked) {
        if (string.equals("Attacker card is frozen.")) {
            command.putPOJO("cardAttacker", attacker);
            command.putPOJO("cardAttacked", attacked);
            command.put("error", "Attacker card is frozen.");
            arr.add(command);
        }
        if (string.equals("Attacker card has already attacked this turn.")) {
            command.putPOJO("cardAttacker", attacker);
            command.putPOJO("cardAttacked", attacked);
            command.put("error", "Attacker card has already attacked this turn.");
            arr.add(command);
        }
        if (string.equals("Attacked card does not belong to the enemy.")) {
            command.putPOJO("cardAttacker", attacker);
            command.putPOJO("cardAttacked", attacked);
            command.put("error", "Attacked card does not belong to the enemy.");
            arr.add(command);
        }
        if (string.equals("Attacked card is not of type 'Tank'.")) {
            command.putPOJO("cardAttacker", attacker);
            command.putPOJO("cardAttacked", attacked);
            command.put("error", "Attacked card is not of type 'Tank'.");
            arr.add(command);
        }
        if (string.equals("Attacked card does not belong to the current player.")) {
            command.putPOJO("cardAttacker", attacker);
            command.putPOJO("cardAttacked", attacked);
            command.put("error", "Attacked card does not belong to the current player.");
            arr.add(command);
        }
    }

    /**
     * Method that handles errors for the 'attackHero' command
     * @param command stores information for the output
     * @param string string containing the error message
     * @param arr the output
     * @param cardAttacker information printed alongside the error
     */

    public void attackHeroErrors(final ObjectNode command, final String string,
                                 final ArrayNode arr, final Coordinates cardAttacker) {
        if (string.equals("Attacker card is frozen.")) {
            command.put("command", "useAttackHero");
            command.putPOJO("cardAttacker", cardAttacker);
            command.put("error", "Attacker card is frozen.");
            arr.add(command);
        }
        if (string.equals("Attacker card has already attacked this turn.")) {
            command.put("command", "useAttackHero");
            command.putPOJO("cardAttacker", cardAttacker);
            command.put("error", "Attacker card has already attacked this turn.");
            arr.add(command);
        }
        if (string.equals("Attacked card is not of type 'Tank'.")) {
            command.put("command", "useAttackHero");
            command.putPOJO("cardAttacker", cardAttacker);
            command.put("error", "Attacked card is not of type 'Tank'.");
            arr.add(command);
        }
    }

    /**
     * Method to print errors in case the command "placeCard" fails
     * @param card the current card
     * @param command the string containing the command
     * @param playerMana the mana of the current player
     * @param frontRowSize how many cards the player's front row has
     * @param backRowSize how many cards the player's back row has
     * @return returns whether an error occurred or not
     */

    public int checkPlaceCardErrors(final Card card, final ObjectNode command, final int playerMana,
                                    final int frontRowSize, final int backRowSize) {
        String cardName = card.getName();
        final int numFive = 5;
        if (cardName.equals("Winterfell") || cardName.equals("Firestorm")
                || cardName.equals("Heart Hound")) {
            command.put("error", "Cannot place environment card on table.");
            return -1;
        }

        if (card.getMana() > playerMana) {
            command.put("error", "Not enough mana to place card on table.");
            return -1;
        }
        if (cardName.equals("The Ripper") || cardName.equals("Miraj")
                || cardName.equals("Goliath")
                || cardName.equals("Warden")) {
            if (frontRowSize == numFive) {
                command.put("error", "Cannot place card on table since row is full.");
                return -1;
            }
        }
        if (cardName.equals("Sentinel") || cardName.equals("Berserker")
                || cardName.equals("The Cursed One")
                || cardName.equals("Disciple")) {
            if (backRowSize == numFive) {
                command.put("error", "Cannot place card on table since row is full.");
                return -1;
            }
        }
        return 0;
    }

}
