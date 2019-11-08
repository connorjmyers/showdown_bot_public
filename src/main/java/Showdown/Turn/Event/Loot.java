package Showdown.Turn.Event;

import Showdown.Game;
import Showdown.Players.Player;
import Showdown.Players.Weapons.Weapon;
import Utility.Database.DAOs.WeaponDAO;
import Utility.ImageUtility;

public class Loot {

    public Loot(Player player) {

        Weapon weapon = createWeapon(player);
        player.addWeapon(weapon);
        Game.facebookPost.addToText(player.getName() + " has looted the " + weapon.getName() + "!");
        Game.facebookPost.setImageContent(ImageUtility.createDualImage(ImageUtility.setImage(player.getImageDirectory()),
                ImageUtility.setImage(weapon.getImageDirectory())));
    }

    private Weapon createWeapon(Player player) {
        return new Weapon(WeaponDAO.getRandomWeapon(), player.getName(), player.getShowdownId());
    }
}
