package Showdown.Players;

import Showdown.Game;
import Showdown.Players.Stats.PlayerStatistics;
import Showdown.Players.Stats.Stat;
import Showdown.Players.Weapons.Weapon;
import Utility.Database.DAOs.PlayerDAO;
import Utility.Game.RandomNumberUtility;
import Utility.MathUtility;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public static int numberOfPlayers = 0;
    private String name; // the name of the player
    private PlayerStatistics playerStatistics; // statistics for the player
    private String image; // path to their image
    private int showdownId;
    private List<Weapon> weapons;
    private String currentPowerUp = "";

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, int showdownId, boolean inDatabase) {

        numberOfPlayers++;

        this.name = name;
        this.showdownId = showdownId;

        if (!inDatabase) {
            this.playerStatistics = new PlayerStatistics();
            weapons = new ArrayList<>();
            weapons.add(Weapon.getDefaultWeapon());
            PlayerDAO.addPlayerToDatabase(this.name, this.showdownId);
            PlayerDAO.addPlayerStatisticsToDatabase(this.name, this.showdownId, this.playerStatistics);
            Game.facebookPost.addToText(name);
        } else {
            this.playerStatistics = PlayerDAO.getExistingPlayerStatistics(this.name, this.showdownId);
            this.weapons = PlayerDAO.getExistingWeapons(this.name, this.showdownId);
            this.currentPowerUp = PlayerDAO.getCurrentPowerUp(this.name, this.showdownId);
        }
    }

    public void checkLevelUp() {

        int experienceRequired = this.getExperienceRequired();

        if (this.getPlayerStatistics().getExperience().getValue() >= experienceRequired) {
            Game.facebookPost.addToText(String.format("%s has leveled up!", this.getName()));
            this.getPlayerStatistics().getLevel().increaseValue(1);
            this.getPlayerStatistics().getExperience().setValue(0);
            this.increaseStats();
        }
    }

    public String getName() {
        return name;
    }

    public int getShowdownId() {
        return showdownId;
    }

    public PlayerStatistics getPlayerStatistics() {
        return playerStatistics;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public int getBaseDamage() {
        int damage;
        try {
            damage = playerStatistics.getStrength().getValue() + weapons.get(weapons.size() - 1).getBaseDamage();
        } catch (IndexOutOfBoundsException e) {
            damage = playerStatistics.getStrength().getValue();
        }
        return damage;
    }

    public boolean isAlive() {
        return playerStatistics.getHealth().getValue() > 0;
    }

    public double getAccuracy() {
        return MathUtility.dodgeChance(this.getPlayerStatistics().getIntelligence().getValue());
    }

    public double getDodgeChance() {
        return MathUtility.dodgeChance(this.getPlayerStatistics().getDexterity().getValue());
    }

    /**
     * Adds a weapon to the player's weapons
     * @param weapon the weapon to be added.
     */
    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public String getImageDirectory() {
        return String.format("Database/Images/PlayerImages/%s.jpg", this.name);
    }

    public void setCurrentPowerUp(String powerUp) {
        currentPowerUp = powerUp;
    }

    public String getSpecialName() {
        return this.currentPowerUp + " " + this.name;
    }

    private int getExperienceRequired() {
        return (int) Math.floor(10 * Math.pow(2, this.getPlayerStatistics().getLevel().getValue()));
    }

    /**
     * Increases stats after a level up
     */
    private void increaseStats() {
        Game.facebookPost.addToText("New Stats");
        increaseStat(this.getPlayerStatistics().getStrength());
        increaseStat(this.getPlayerStatistics().getDexterity());
        increaseStat(this.getPlayerStatistics().getIntelligence());
        increaseStat(this.getPlayerStatistics().getCharisma());
        increaseStat(this.getPlayerStatistics().getWisdom());
        increaseStat(this.getPlayerStatistics().getConstitution());
        PlayerDAO.updatePlayerStatistics(this);
    }

    private void increaseStat(Stat stat) {
        int increase = (int) Math.floor((5 * Math.log10(this.getPlayerStatistics().getLevel().getValue()) + RandomNumberUtility.randomNumberExclusive(3)));
        Game.facebookPost.addToText(String.format("%s: %d + %d = %d", stat.getName(), stat.getValue(), increase, (stat.getValue() + increase)));
        stat.increaseValue(increase);
    }

    /**
     * Experience earned from killing this player
     * @return
     */
    public int getExperienceEarned() {
        return (int) Math.floor(3 * Math.pow(2, this.getPlayerStatistics().getLevel().getValue()));
    }
}
