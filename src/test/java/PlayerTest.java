import Utility.Game.RandomNumberUtility;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void statIncreaseTest() {

        int increase = (int) Math.floor((5 * Math.log10(100) + RandomNumberUtility.randomNumberExclusive(3)));
        System.out.println(increase);
    }

}
