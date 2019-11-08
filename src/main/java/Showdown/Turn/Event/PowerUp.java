package Showdown.Turn.Event;

import Showdown.Game;
import Showdown.Players.Player;
import Showdown.Players.Stats.Stat;
import Utility.Database.DAOs.PlayerDAO;
import Utility.Database.DAOs.PowerUpDAO;
import Utility.Game.RandomNumberUtility;
import Utility.ImageUtility;

import java.awt.image.BufferedImage;

public class PowerUp {

    public PowerUp(Player player) {
        String powerUp = PowerUpDAO.getRandomPowerUp();
        player.setCurrentPowerUp(powerUp);
        PlayerDAO.setPowerUp(player.getName(), player.getShowdownId(), powerUp);
        Game.facebookPost.addToText(player.getName() +
                " has gone even further beyond, they've gone " + powerUp + "!");
        Game.facebookPost.setImageContent(ImageUtility.createDualImage
                (ImageUtility.setImage(player.getImageDirectory()), getPowerUpImage(powerUp)));
        increaseStats(player);
    }

    private void increaseStats(Player player) {
        Game.facebookPost.addToText("New Stats");
        increaseStat(player, player.getPlayerStatistics().getStrength());
        increaseStat(player, player.getPlayerStatistics().getDexterity());
        increaseStat(player, player.getPlayerStatistics().getIntelligence());
        increaseStat(player, player.getPlayerStatistics().getCharisma());
        increaseStat(player, player.getPlayerStatistics().getWisdom());
        increaseStat(player, player.getPlayerStatistics().getConstitution());
        PlayerDAO.updatePlayerStatistics(player);
    }

    private void increaseStat(Player player, Stat stat) {
        int increase = stat.getValue() / RandomNumberUtility.randomNumber(3) + 1;
        Game.facebookPost.addToText(String.format("%s: %d + %d = %d", stat.getName(), stat.getValue(), increase, (stat.getValue() + increase)));
        stat.increaseValue(increase);
    }

    private BufferedImage getPowerUpImage(String powerUp) {
        String directory = String.format("Database/Images/EventImages/%s.jpg", powerUp);
        return ImageUtility.setImage(directory);
    }
}
