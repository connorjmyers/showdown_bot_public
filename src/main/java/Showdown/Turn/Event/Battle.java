package Showdown.Turn.Event;

import Showdown.Game;
import Showdown.Players.Player;
import Utility.Database.DAOs.PlayerDAO;
import Utility.Game.RandomNumberUtility;
import Utility.ImageUtility;

import java.util.Random;
import java.util.logging.Logger;

public class Battle {

    private static final Logger logger = Logger.getLogger(Battle.class.getName());
    private static final int DAMAGE_MULTIPLIER = 5;
    private Player playerOne;
    private Player playerTwo;
    private boolean playerOnesTurn = true;

    public Battle(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        boolean result = battle();

        if (result) {
            playerOne.getPlayerStatistics().getKills().increaseValue(1);
            playerOne.getPlayerStatistics().getExperience().increaseValue(playerTwo.getExperienceEarned());
            playerOne.getPlayerStatistics().healUp();
            Game.facebookPost.addToText(playerOne.getSpecialName() + " has killed " + playerTwo.getSpecialName()
                    + " with their " + playerOne.getWeapons().get(playerOne.getWeapons().size() - 1).getName() + "!");
            Game.facebookPost.setImageContent(ImageUtility.createRoundImage(playerOne, playerTwo));
            playerOne.checkLevelUp();
        } else {
            playerTwo.getPlayerStatistics().getKills().increaseValue(1);
            playerTwo.getPlayerStatistics().getExperience().increaseValue(playerOne.getExperienceEarned());
            playerTwo.getPlayerStatistics().healUp();
            Game.facebookPost.addToText(playerTwo.getSpecialName() + " has killed " + playerOne.getSpecialName()
                    + " with their " + playerTwo.getWeapons().get(playerTwo.getWeapons().size() - 1).getName() + "!");
            Game.facebookPost.setImageContent(ImageUtility.createRoundImage(playerTwo, playerOne));
            playerTwo.checkLevelUp();
        }

        PlayerDAO.updatePlayerStatistics(playerOne);
        PlayerDAO.updatePlayerStatistics(playerTwo);
    }

    /**
     * Returns if player one won the battle
     * @return true is playerOne won and false otherwise (since someone always dies, if playerTwo won)
     */
    private boolean battle() {
        // possibility of infinite loop? Don't think so...
        while (playerOne.isAlive() && playerTwo.isAlive()) {
            attack();
        }
        return (playerOne.isAlive());
    }

    private void attack() {

        if (playerOnesTurn && RandomNumberUtility.checkChance(playerOne.getAccuracy()) &&
                !RandomNumberUtility.checkChance(playerTwo.getDodgeChance())) {
            int damage = RandomNumberUtility.randomNumber(DAMAGE_MULTIPLIER) * playerOne.getBaseDamage();
            playerTwo.getPlayerStatistics().getHealth().setValue(playerTwo.getPlayerStatistics().getHealth().getValue()
                    - damage);
            logger.info(playerOne.getName() + " does " + damage + " damage to " + playerTwo.getName() +
                    ". Their hp is now " + playerTwo.getPlayerStatistics().getHealth().getValue());
        } else if (!playerOnesTurn && RandomNumberUtility.checkChance(playerTwo.getAccuracy()) &&
                !RandomNumberUtility.checkChance(playerOne.getDodgeChance())) {
            int damage = RandomNumberUtility.randomNumber(DAMAGE_MULTIPLIER) * playerTwo.getBaseDamage();
            playerOne.getPlayerStatistics().getHealth().setValue(playerOne.getPlayerStatistics().getHealth().getValue()
                    - damage);
            logger.info(playerTwo.getName() + " does " + damage + " damage to " + playerOne.getName() +
                    ". Their hp is now " + playerOne.getPlayerStatistics().getHealth().getValue());
        }
        playerOnesTurn = !playerOnesTurn;
    }
}
