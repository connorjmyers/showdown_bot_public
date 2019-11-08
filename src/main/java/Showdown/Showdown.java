package Showdown;

import Managers.DatabaseManager;
import Showdown.Players.Player;
import Showdown.Turn.Turn;
import Utility.Database.DAOs.ShowdownDAO;
import Utility.Facebook.FacebookPost;
import Utility.ImageUtility;

import java.util.*;
import java.util.logging.Logger;

public class Showdown {

    private static final Logger logger = Logger.getLogger(Showdown.class.getName());
    private int showdownId;
    private final int MAX_PLAYERS = 100;
    private List<Player> players;
    private boolean gameOver;

    /**
     * Creates a new showdown
     */
    public Showdown() {
        Game.facebookPost.addToText("A new Ultimate Showdown is starting! \n \n The competitors are... \n");
        Game.facebookPost.setImageContent(ImageUtility.setImage("Database/showdown.jpg"));
        logger.info("Creating new Showdown from scratch");
        showdownId = ShowdownDAO.setId();
        players = this.setPlayers();
    }

    /**
     * Creates a new showdown from existing showdown in database
     *
     * @param showdownId the id for the existing showdown.
     */
    public Showdown(Integer showdownId) {
        logger.info("Showdown already in progress. Creating new showdown from showdown id: " + showdownId);
        this.showdownId = showdownId;
        this.players = ShowdownDAO.getExistingPlayers(showdownId);
        this.removeDeadPlayers();
        gameOver = this.isGameOver();
    }

    /**
     * Sets the player for this showdown
     *
     * @return The list of players competing in this ultimate showdown
     */
    private List<Player> setPlayers() {

        Random rand = new Random();

        List<String> potentialPlayers = DatabaseManager.getDatabaseManager().getData("SELECT * FROM `potentialPlayers`");
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < MAX_PLAYERS; i++) {

            String randomName;
            Player player;

            // ensuring no duplicates. Big-O is terrible, but there is less code :)
            do {
                players.sort(Comparator.comparing(Player::getName)); // so it's in order.
                randomName = potentialPlayers.get(rand.nextInt(potentialPlayers.size()));
                player = new Player(randomName); // uses different constructor, doesn't add to db or increase id count
            }
            while (Collections.binarySearch(players, player, Comparator.comparing(Player::getName)) >= 0);
            player = new Player(randomName, showdownId, false);
            players.add(player);
        }
        return players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getShowdownId() {
        return showdownId;
    }

    public void removeDeadPlayers() {

        List<Player> deadPlayers = new ArrayList<>();

        for (Player player : players) {
            if (player.getPlayerStatistics().getHealth().getValue() <= 0) {
                deadPlayers.add(player);
            }
        }
        players.removeAll(deadPlayers);
    }

    public boolean isGameOver() {
        if (this.players.size() == 1) {
            ShowdownDAO.updateGameOverDatabase(this.showdownId);
            this.declareWinner();
            return true;
        }
        return false;
    }

    /**
     * Why did I write the code like this LOL
     */
    private void declareWinner() {
        Player winningPlayer = this.players.get(0);
        StringBuilder victoryMessage = new StringBuilder();
        victoryMessage.append("...and the fight raged on for a century\n");
        victoryMessage.append("many lives were claimed, but eventually\n");
        victoryMessage.append("the champion stood, the rest saw their better:\n");
        victoryMessage.append(winningPlayer.getName()).append(" in a bloodstained sweater\n");
        victoryMessage.append("this is the Ultimate Showdown of Ultimate Destiny\n");
        victoryMessage.append("good guys, bad guys, and explosions as far as the eye can see\n");
        victoryMessage.append("and only one will survive, I wonder who it will be\n");
        victoryMessage.append("this is the Ultimate Showdown...\n");
        victoryMessage.append("this is the Ultimate Showdown...\n");
        victoryMessage.append("this is the Ultimate Showdown...\n");
        victoryMessage.append("of Ultimate Destiny\n");
        victoryMessage.append("\n Congratulations " + winningPlayer.getName() + " you have won the Ultimate Showdown!");
        Game.lastPost = true;
        Game.facebookPost.setTextContent(victoryMessage.toString());
        Game.facebookPost.setImageContent(ImageUtility.winningImage(ImageUtility.setImage(winningPlayer.getImageDirectory())));
    }
}
