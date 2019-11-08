package Showdown.Players.Weapons;

import Showdown.Players.Player;
import Utility.Database.DAOs.WeaponDAO;
import Utility.Game.RandomNumberUtility;

import java.util.logging.Logger;

public class Weapon {

    public static final int BASE_DAMAGE_RANGE = 10;
    private String name;
    private int baseDamage;

    /**
     * Creates a new weapon from existing one in database
     * @param name the name of the weapon
     * @param baseDamage the base damage of the weapon
     */
    public Weapon(String name, int baseDamage) {
        this.name = name;
        this.baseDamage = baseDamage;
    }

    /**
     * Constructor for creating a new weapon
     * @param name the name of the weapon
     */
    public Weapon(String name, String playerName, int showdownId) {
        this.name = name;
        this.baseDamage = RandomNumberUtility.randomNumber(BASE_DAMAGE_RANGE);
        WeaponDAO.addWeaponToDatabase(this.name, this.baseDamage, playerName, showdownId);
    }

    public String getName() {
        return name;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public static Weapon getDefaultWeapon() {
        Weapon fists = new Weapon("fists", RandomNumberUtility.randomNumberExclusive(10));
        return fists;
    }

    public String getImageDirectory() {
        return String.format("Database/Images/WeaponImages/%s.jpg", this.name);
    }
}
