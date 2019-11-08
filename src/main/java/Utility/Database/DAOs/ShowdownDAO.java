package Utility.Database.DAOs;

import Managers.DatabaseManager;
import Showdown.Players.Player;
import Showdown.Showdown;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ShowdownDAO {

    private static final Logger logger = Logger.getLogger(ShowdownDAO.class.getName());

    /**
     * Sets the id for this showdown and creates the database entry for the showdown
     *
     * @return the id for this showdown
     */
    public static int setId() {
        int newId;
        try {
            List<String> id = DatabaseManager.getDatabaseManager().getData("SELECT `Showdown_Id` FROM `Showdown`");
            newId = Integer.parseInt(id.get(id.size() - 1)) + 1;
        } catch (Exception e) {
            newId = 1;
        }
        DatabaseManager.getDatabaseManager().inputData("INSERT INTO `Showdown` (`Showdown_Id`, `game_over`)" +
                " VALUES ('" + newId  + "', '" + 0 + "');");
        return newId;
    }

    public static void updateGameOverDatabase(int showdownId) {

        String query = "UPDATE `Showdown` SET `game_over` = '" + 1 + "' WHERE `Showdown`.`Showdown_Id` = " + showdownId + ";";
        DatabaseManager.getDatabaseManager().inputData(query);
    }

    /**
     * Checks if a game is on-going. I.e. most recent showdown entry in database has gameOver = 0.
     * @return
     * True if there is an existing game, false otherwise
     */
    public static boolean checkExistingGame() {

        String gameOverQuery = "SELECT `game_over` FROM `Showdown`";
        List<String> gameOvers = DatabaseManager.getDatabaseManager().getData(gameOverQuery);
        String gameOver;

        try {
            gameOver = gameOvers.get(gameOvers.size() - 1);
        } catch (Exception e) {
            return false;
        }

        return Integer.parseInt(gameOver) == 0;
    }

    public static int getLastShowdownId() {

        String query = "SELECT `Showdown_Id` FROM `Showdown`";
        List<String> showdownIds = DatabaseManager.getDatabaseManager().getData(query);
        String showdownId = showdownIds.get(showdownIds.size() - 1);
        return Integer.parseInt(showdownId);
    }

    /**
     * Gets players from existing Showdown
     * @return
     * The players from existing showdown
     */
    public static List<Player> getExistingPlayers(int showdownId) {

        String query = "SELECT `Name` FROM `Player` WHERE `Showdown_Id` = " + showdownId + ";";
        List<String> playerNames = DatabaseManager.getDatabaseManager().getData(query);
        List<Player> players = new ArrayList<>();

        for (String name : playerNames) {
            logger.config("Creating new player from database entry");
            Player player = new Player(name, showdownId, true);
            players.add(player);
        }
        return players;
    }
}