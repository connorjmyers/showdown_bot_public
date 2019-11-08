package Utility.Database.DAOs;

import Managers.DatabaseManager;
import Showdown.Players.Player;
import Showdown.Players.Stats.PlayerStatistics;
import Showdown.Players.Weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PlayerDAO {

    private static final Logger logger = Logger.getLogger(PlayerDAO.class.getName());

    public static void addPlayerToDatabase(String name, Integer showdownId) {
        logger.config("Adding new Player to database: " + "Name: " + name + " showdownId: " + showdownId);
        String query = "INSERT INTO `Player` (`Showdown_Id`, `Name`) VALUES ('" + showdownId + "', '" + name + "');";
        DatabaseManager.getDatabaseManager().inputData(query);
    }

    public static void addPlayerStatisticsToDatabase(String name, int showdownId, PlayerStatistics playerStatistics) {
        logger.config("Adding new PlayerStatistics to database for: " + "Name: " + name + " showdownId: " + showdownId);
        String query = "INSERT INTO `PlayerStatistics` (`Name`, `Showdown_Id`, `Health`, `Strength`, `Dexterity`, " +
                "`Intelligence`, `Charisma`, `Wisdom`, `Constitution`, `Level`, `Experience`, `Kills`)" +
                " VALUES ('" + name + "', '" + showdownId + "', '" + playerStatistics.getHealth().getValue()
                + "', '" + playerStatistics.getStrength().getValue() + "', '" + playerStatistics.getDexterity().getValue()
                + "', '" + playerStatistics.getIntelligence().getValue() + "', '" + playerStatistics.getCharisma().getValue()
                + "', '" + playerStatistics.getWisdom().getValue() + "', '" + playerStatistics.getConstitution().getValue()
                + "', '" + playerStatistics.getLevel().getValue() + "', '" + playerStatistics.getExperience().getValue() +
                "', '" + playerStatistics.getKills().getValue() + "');";
        DatabaseManager.getDatabaseManager().inputData(query);
    }

    public static void updatePlayerStatistics(Player player) {

        logger.config("Updating player statistics for: " + "Name: " + player.getName() + " showdownId: " + player.getShowdownId());
        String query = "UPDATE `PlayerStatistics` SET `Showdown_Id` = '" + player.getShowdownId()
                + "', `Health` = '" + player.getPlayerStatistics().getHealth().getValue()
                + "', `Strength` = '" + player.getPlayerStatistics().getStrength().getValue()
                + "', `Dexterity` = '" + player.getPlayerStatistics().getDexterity().getValue()
                + "', `Intelligence` = '" + player.getPlayerStatistics().getIntelligence().getValue()
                + "', `Charisma` = '" + player.getPlayerStatistics().getCharisma().getValue()
                + "', `Wisdom` = '" + player.getPlayerStatistics().getWisdom().getValue()
                + "', `Constitution` = '" + player.getPlayerStatistics().getConstitution().getValue()
                + "', `Level` = '" + player.getPlayerStatistics().getLevel().getValue()
                + "', `Experience` = '" + player.getPlayerStatistics().getExperience().getValue()
                + "', `Kills` = '" + player.getPlayerStatistics().getKills().getValue()
                + "' WHERE `PlayerStatistics`.`Name` = '" + player.getName() + "'" +
                " AND `PlayerStatistics`.`Showdown_Id` = " + player.getShowdownId() + ";";
        DatabaseManager.getDatabaseManager().inputData(query);
    }

    public static PlayerStatistics getExistingPlayerStatistics(String name, int showdownId) {

        String query = String.format("SELECT * FROM `PlayerStatistics` WHERE `Name` = '%s' AND `Showdown_Id` = '%d';", name, showdownId);
        List<String> statistics = DatabaseManager.getDatabaseManager().getData(query);
        statistics = statistics.subList(2, statistics.size());

        return new PlayerStatistics(statistics);
    }

    public static List<Weapon> getExistingWeapons(String name, int showdownId) {

        List<Weapon> weapons = new ArrayList<>();
        String query = String.format("SELECT weapon_name, base_damage FROM `Weapon` WHERE `player_name` = '%s' AND `showdown_id` = '%d';", name, showdownId);
        List<List<String>> weaponsString = DatabaseManager.getDatabaseManager().getMultiData(query);
        weapons.add(Weapon.getDefaultWeapon()); // everyone has fists first
        for (List<String> string : weaponsString) {
            Weapon weapon = new Weapon(string.get(0), Integer.parseInt(string.get(1)));
            weapons.add(weapon);
        }
        return weapons;
    }

    public static void setPowerUp(String name, int showdownId, String powerUp) {
        String query = String.format("INSERT INTO `PowerUps` (`player_name`, `showdown_id`, `power_up`) " +
                "VALUES ('%s', '%d', '%s');", name, showdownId, powerUp);
        DatabaseManager.getDatabaseManager().inputData(query);
    }

    public static String getCurrentPowerUp(String name, int showdownId) {
        String query = String.format("SELECT power_up FROM `PowerUps` WHERE player_name = '%s' AND showdown_id = '%d'", name, showdownId);
        List<String> powerUps = DatabaseManager.getDatabaseManager().getData(query);
        try {
            return powerUps.get(powerUps.size() - 1);
        } catch (Exception e) {
            return "";
        }
    }
}
