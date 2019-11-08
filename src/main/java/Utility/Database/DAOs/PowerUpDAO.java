package Utility.Database.DAOs;

import Managers.DatabaseManager;
import Utility.Game.RandomNumberUtility;

import java.util.List;
import java.util.logging.Logger;

public class PowerUpDAO {

    private static final Logger logger = Logger.getLogger(PowerUpDAO.class.getName());

    /**
     * Gets random power-up
     * @return random power up name
     */
    public static String getRandomPowerUp() {
        String query = "SELECT name FROM `PotentialPowerUp`";
        List<String> powerUps = DatabaseManager.getDatabaseManager().getData(query);
        int index = RandomNumberUtility.randomNumberExclusive(powerUps.size());
        return powerUps.get(index);
    }
}
