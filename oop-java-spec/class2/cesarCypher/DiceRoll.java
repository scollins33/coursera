
/**
 * Write a description of DiceRoll here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Random;

public class DiceRoll {
    public void simpleSimulate(int rolls) {
        Random rand = new Random();
        int[]  counts = new int[13]; // so we can ignore 0 and 1
        
        for (int k = 0; k < rolls; k++) {
            int d1 = rand.nextInt(6) + 1;
            int d2 = rand.nextInt(6) + 1;
            
            counts[d1 + d2] += 1;
        }
        
        for (int j = 2; j <= 12; j++) {
            System.out.println(j + "'s =\t" + counts[j] + "\t" + 100.0 * counts[j]/rolls);
        }
    }
}
