package Utility.Database.DAOs;

import Managers.DatabaseManager;
import Showdown.Players.Player;
import Showdown.Players.Weapons.Weapon;
import Utility.Game.RandomNumberUtility;

import java.util.List;
import java.util.logging.Logger;

public class WeaponDAO {

    private static final Logger logger = Logger.getLogger(WeaponDAO.class.getName());

    /**
     * Adds a newly generated weapon to the database
     * @param weaponName the name of the weapon
     * @param baseDamage the base damage of the weapon (randomly generated)
     */
    public static void addWeaponToDatabase(String weaponName, int baseDamage, String playerName, int showdownId) {
        String query = String.format("INSERT INTO `Weapon` (`player_name`, `showdown_id`, `weapon_name`, `base_damage`)" +
                " VALUES ('%s', '%d', '%s', '%d');", playerName, showdownId, weaponName, baseDamage);
        logger.config("Creating new weapon with: " + query);
        DatabaseManager.getDatabaseManager().inputData(query);
    }

    /**
     * Gets a random weapon name from the database
     * @return a random weapon from the database.
     */
    public static String getRandomWeapon() {
        List<String> weapons = DatabaseManager.getDatabaseManager().getData("SELECT * FROM `PotentialWeapons`");
        return weapons.get(RandomNumberUtility.randomNumberExclusive(weapons.size()));
    }
}
