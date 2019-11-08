package Utility.Game;

import java.util.Random;

/**
 * Provides random number generation.
 *
 * @author Connor Myers
 * @version 2.0 25/04/2019
 */
public class RandomNumberUtility {

    private static Random rand = new Random();

    /**
     * Generates a random number from 1-bound (inclusive)
     *
     * @param bound The inclusive roof for your randomly generated number.
     * @return A number between 1 and bound (inclusive)
     */
    public static int randomNumber(int bound) {
        return rand.nextInt(bound) + 1;
    }

    /**
     * Generates a random number from 1-bound (exclusive)
     *
     * @param bound The inclusive roof for your randomly generated number.
     * @return A number between 1 and bound (inclusive)
     */
    public static int randomNumberExclusive(int bound) {
        return rand.nextInt(bound);
    }

    /**
     * Rolls an 18 sided die and returns the result.
     *
     * @return A number between 1 and 18 (inclusive)
     */
    public static int d18() {
        return rand.nextInt(18) + 1;
    }

    /**
     * Calculates and return whether something occurred if it has an x% chance of occurring
     *
     * @param chance The chance something will happen, i.e. if chance is 75%, then chance is 0.75.
     * @return True if event occurred, false otherwise.
     */
    public static boolean checkChance(double chance) {
        return rand.nextDouble() < chance;
    }
}