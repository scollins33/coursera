
/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.ArrayList;

public class CharactersInPlay {
    
    ArrayList<String> gCharacters;
    ArrayList<Integer> gFreqs;
    
    public CharactersInPlay() {
        gCharacters = new ArrayList<String>();
        gFreqs = new ArrayList<Integer>();
    }
    
    private void update(String person) {
        person = person.toLowerCase();
        
        int index = gCharacters.indexOf(person);
        
        if (index == -1){ // it doesnt exist 
            gCharacters.add(person); // add it
            gFreqs.add(1); // set Freqs to 1 since we know of 1 instance
        }
        else {
            int freq = gFreqs.get(index); // fetch the number
            gFreqs.set(index,freq + 1); // increment it and set it
        }
    }
    
    private void findAllCharacters() {
        gCharacters.clear();
        gFreqs.clear();
        
        FileResource fr = new FileResource();
        
        for (String line : fr.lines()) {
            int period = line.indexOf("."); // find the dot
            
            if (period > -1) { // we got em
                String character = line.substring(0, period);
                
                update(character); // update internal vars
            }
        }
    }
    
    private ArrayList<Integer> filterCharacters(int floor, int ceiling) {
        ArrayList<Integer> pIndexes = new ArrayList<Integer>();
        
        for (int k = 0; k < gCharacters.size(); k++) {
            
            if(gFreqs.get(k) >= floor && gFreqs.get(k) <= ceiling) {
                pIndexes.add(k);
            }
        }
        
        return pIndexes;
    }
    
    public void tester() {
        findAllCharacters();
        
        System.out.println("-----------------------------------------");
        System.out.println("--- RAW");
        
        for (int k = 0; k < gCharacters.size(); k++) {
            
            if(gFreqs.get(k) >= 10) {
                System.out.println("\t" + gCharacters.get(k) + "\t" + gFreqs.get(k));
            }    
        }
                
        System.out.println("-----------------------------------------");
        System.out.println("--- FILTERED");
        
        ArrayList<Integer> filteredIndexes = new ArrayList<Integer>();
        
        filteredIndexes = filterCharacters(10, 15);
        
        for (int k = 0; k < filteredIndexes.size(); k++) {
            
            int thisIndex = filteredIndexes.get(k);
            System.out.println("\t" + gCharacters.get(thisIndex) + "\t" + gFreqs.get(thisIndex));
        }
        
        System.out.println("-----------------------------------------");
    }
}
