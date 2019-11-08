package Utility;

public class MathUtility {

    /**
     * Logistic function for calculating the chance of a player dodging attack.
     * Desmos equation is \frac{0.95}{1+e^{-0.06\left(x-50\right)}}
     */
    public static double dodgeChance(int dexterity) {
        return 0.95 / (1 + Math.exp(-0.06 * (dexterity - 50)));
    }

    /**
     * Logistic function for calculating the chance of an attack landing
     * Desmos equation is \frac{0.95}{1+e^{-0.06\left(x\right)}}
     */
    public static double attackChance(int intelligence) {
        return 0.95 / (1 + Math.exp(-0.06 * (intelligence)));
    }
}
