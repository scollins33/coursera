
/**
 * Write a description of WordLengths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class WordLengths {
    public void countWordLengths(FileResource resource, int[] counts) {
        
        for(String word : resource.words()){
            // find word length
            int trueLength = word.length();
            //System.out.println("word " + word);
            if (!Character.isLetter(word.charAt(trueLength - 1))) {
                System.out.println("last char is not letter");
                trueLength -= 1;
            }
            if (!Character.isLetter(word.charAt(0))) {
                System.out.println("first char is not letter");
                trueLength -= 1;
            }
            
            // increment the counts index (length)
            if (trueLength > counts.length - 1) {
                counts[counts.length - 1] += 1;
            }
            else if (trueLength < 0) {
                System.out.println("length was negative...");
            }
            else {
                //System.out.println("trueLength " + trueLength);
                counts[trueLength] += 1;
            }
        }
        
    }
    
    public int indexOfMax(int[] values) {
        int highestRank = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[highestRank]) {
                highestRank = i;
            }
        }
        return highestRank;
    }
    
    public void testCountWordLengths() {
        FileResource file = new FileResource();
        int[] counts = new int[31];
        
        countWordLengths(file, counts);
        
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > 0) {
                System.out.println("Length " + i + " had " + counts[i] + " words.");
            }
        }
        
        System.out.println(">> Max Index: " + indexOfMax(counts));
    }
}
