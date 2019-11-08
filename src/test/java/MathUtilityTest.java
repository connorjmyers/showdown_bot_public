import Utility.MathUtility;
import org.junit.Assert;
import org.junit.Test;

public class MathUtilityTest {

    @Test
    public void dodgeChanceTest() {

        for (int i = 1; i < 1000; i++) {
            if (MathUtility.dodgeChance(i) < 0 || MathUtility.dodgeChance(i) > 1) {
                Assert.fail();
            }
        }
    }

    @Test
    public void attackChanceTest() {

        for (int i = 1; i < 1000; i++) {
            if (MathUtility.attackChance(i) < 0.475 || MathUtility.attackChance(i) > 1) {
                Assert.fail();
            }
        }
    }
}
