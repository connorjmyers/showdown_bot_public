package Showdown.Players.Stats;

import Utility.Game.RandomNumberUtility;

import java.util.List;

public class PlayerStatistics {

    private static final int BASE_HEALTH = 50;
    private static final int HEALTH_FACTOR = 5;

    private Stat health; // The health of the Player.
    private Stat level; // The level of the Player.
    private Stat experience; // The experience points the Player has.
    private Stat kills; // The number of people the Player has killed.
    private Stat strength; // The Strength of the Player.
    private Stat dexterity; // The dexterity of the Player.
    private Stat intelligence; // The intelligence of the Player.
    private Stat charisma; // The charisma of the Player.
    private Stat wisdom; // The wisdom of the Player.
    private Stat constitution; // the constitution of the Player.

    public PlayerStatistics() {

        health = new Stat("Health");
        level = new Stat("Level");
        experience = new Stat("Experience");
        kills = new Stat("Kills");
        strength = new Stat("Strength");
        dexterity = new Stat("Dexterity");
        intelligence = new Stat("Intelligence");
        charisma = new Stat("Charisma");
        wisdom = new Stat("Wisdom");
        constitution = new Stat("Constitution");

        this.setInitialValues();
    }

    /**
     * For when creating playerStatistics from database
     * @param values
     * values from database for the player
     */
    public PlayerStatistics(List<String> values) {

        health = new Stat(Integer.parseInt(values.get(0)), "Health");
        strength = new Stat(Integer.parseInt(values.get(1)), "Strength");
        dexterity = new Stat(Integer.parseInt(values.get(2)), "Dexterity");
        intelligence = new Stat(Integer.parseInt(values.get(3)), "Intelligence");
        charisma = new Stat(Integer.parseInt(values.get(4)), "Charisma");
        wisdom = new Stat(Integer.parseInt(values.get(5)), "Wisdom");
        constitution = new Stat(Integer.parseInt(values.get(6)), "Constitution");
        level = new Stat(Integer.parseInt(values.get(7)), "Level");
        experience = new Stat(Integer.parseInt(values.get(8)), "Experience");
        kills = new Stat(Integer.parseInt(values.get(9)), "Kills");
    }

    private void setInitialValues() {

        strength.setValue(RandomNumberUtility.d18());
        dexterity.setValue(RandomNumberUtility.d18());
        intelligence.setValue(RandomNumberUtility.d18());
        charisma.setValue(RandomNumberUtility.d18());
        wisdom.setValue(RandomNumberUtility.d18());
        constitution.setValue(RandomNumberUtility.d18());
        level.setValue(1);
        experience.setValue(0);
        kills.setValue(0);
        health.setValue(HEALTH_FACTOR * (BASE_HEALTH + (constitution.getValue() * 2)));
    }

    public Stat getHealth() {
        return health;
    }

    public Stat getLevel() {
        return level;
    }

    public Stat getExperience() {
        return experience;
    }

    public Stat getKills() {
        return kills;
    }

    public Stat getStrength() {
        return strength;
    }

    public Stat getDexterity() {
        return dexterity;
    }

    public Stat getIntelligence() {
        return intelligence;
    }

    public Stat getCharisma() {
        return charisma;
    }

    public Stat getWisdom() {
        return wisdom;
    }

    public Stat getConstitution() {
        return constitution;
    }

    /**
     * Heals person up to their max. To be used at the end of a fight.
     */
    public void healUp() {
        health.setValue(HEALTH_FACTOR * (BASE_HEALTH + (constitution.getValue() * 2)));
    }

    @Override
    public String toString() {
        return String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d", health.getValue(), strength.getValue(), dexterity.getValue(),
                intelligence.getValue(), charisma.getValue(), wisdom.getValue(), constitution.getValue(), level.getValue(),
                experience.getValue(), kills.getValue());
    }
}
