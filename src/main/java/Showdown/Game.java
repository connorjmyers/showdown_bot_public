package Showdown;

import Managers.FacebookManager;
import Showdown.Turn.Turn;
import Utility.Database.DAOs.ShowdownDAO;
import Utility.Facebook.FacebookPost;
import Utility.Facebook.FacebookUtility;

import java.io.IOException;

// https://stackoverflow.com/questions/37100082/java-project-with-gradle-and-building-jar-file-in-intellij-idea-how-to
public class Game {

    public static FacebookUtility facebookUtility;
    public static FacebookPost facebookPost;
    public static boolean lastPost = false;

    public static void main(String[] args) throws IOException {
        initialiseFacebook(); // creates Facebook Utility and Managers
        startShowdown(); // Creates a new showdown
        FacebookManager.getFacebookManager().postImageWithText(facebookPost); // posts content
    }

    /**
     * Starts the showdown
     */
    private static void startShowdown() {
        Showdown showdown;
        if (ShowdownDAO.checkExistingGame()) {
            showdown = new Showdown(ShowdownDAO.getLastShowdownId());
            if (!showdown.isGameOver()) {
                new Turn(showdown);
            }
        } else {
            showdown = new Showdown();
        }
        showdown.removeDeadPlayers(); // chance someone died in battle, need to update accordingly
        if (!lastPost) {
            Game.facebookPost.addToText("\n \n" + showdown.getPlayers().size() + " fighters remain...");
        }
    }

    /**
     * Initialises Facebook stuff
     */
    private static void initialiseFacebook() {
        facebookUtility = new FacebookUtility();
        facebookPost = new FacebookPost();
    }
}
