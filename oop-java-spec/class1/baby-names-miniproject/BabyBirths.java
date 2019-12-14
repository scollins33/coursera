
import edu.duke.*;
import org.apache.commons.csv.*;

/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyBirths {
    public void printNames() {
        FileResource fr = new FileResource();
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                                " | Gender " + rec.get(1) +
                                " | NumBorn " + rec.get(2)
                                );
            }
        }
    }
    
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        
        for (CSVRecord rec : fr.getCSVParser()) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
        }
        
        System.out.println("Total Births: " + totalBirths);
    }
}
