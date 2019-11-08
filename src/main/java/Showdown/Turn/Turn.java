package Showdown.Turn;

import Showdown.Players.Player;
import Showdown.Showdown;
import Showdown.Turn.Event.Battle;
import Showdown.Turn.Event.Loot;
import Showdown.Turn.Event.PowerUp;
import Utility.Game.RandomNumberUtility;

import java.util.logging.Logger;

public class Turn {

    private static final Logger logger = Logger.getLogger(Turn.class.getName());
    private Showdown showdown;
    private Player player;

    public Turn(Showdown showdown) {
        this.showdown = showdown;
        player = getRandomPlayer();
        this.pickEvent();
    }

    private Player getRandomPlayer() {

        Player randomPlayer;
        int index = RandomNumberUtility.randomNumberExclusive(showdown.getPlayers().size());

        if (player == null) {
            return showdown.getPlayers().get(index);
        }

        do {
            index = RandomNumberUtility.randomNumberExclusive(showdown.getPlayers().size());
            randomPlayer = showdown.getPlayers().get(index);
        } while (randomPlayer.getName().equals(player.getName()));

        return randomPlayer;
    }

    private void pickEvent() {

        int event = RandomNumberUtility.randomNumber(100);
        logger.info("Event rolls : " + event);

        if (event <= 75) {
            logger.info("Battle starting");
            Player playerTwo = getRandomPlayer();
            new Battle(player, playerTwo);
        } else if (event < 95) {
            logger.info("Looting");
            new Loot(player);
        } else {
            logger.info("Power-up");
            new PowerUp(player);
        }
    }
}
