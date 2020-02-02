
/**
 * Write a description of CodonCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class CodonCount {
    private HashMap<String, Integer> codons;
    
    public CodonCount() {
        codons = new HashMap<String, Integer>();
    }
    
    private void buildCodonMap(int start, String dna) {
        codons.clear();
        
        for(int k = start; k + 3 <= dna.length(); k = k + 3) {
            String codon = dna.substring(k, k + 3);
            
            //System.out.println("Found codon: " + codon + " @ " + k + " to " + (k+3));
            
            if (codons.containsKey(codon)) {
                //System.out.println("counter " + codons.get(codon));
                codons.put(codon, codons.get(codon) + 1);
                //System.out.println("counter after " + codons.get(codon));
            }
            else {
                codons.put(codon, 1);
                //System.out.println("counter " + codons.get(codon));
            }
        }
    }
    
    private String getMostCommonCodon() {
        String most = "";
        int highest = 0;
        
        for (String s : codons.keySet()) {
            if (codons.get(s) > highest) {
                highest = codons.get(s);
                most = s;
            }
        }
        
        System.out.println("MOST COUNT: " + highest);
        return most;
    }
    
    private void printCodonCounts(int start, int end) {
        for (String s : codons.keySet()) {
            if (codons.get(s) >= start && codons.get(s) <= end) {
                System.out.println(s + " : " + codons.get(s));
            }
        }
    }
    
    public void tester() {
        FileResource fr = new FileResource();
        String dnaStr = fr.asString();
        
        dnaStr = dnaStr.toUpperCase();
        dnaStr = dnaStr.trim();
        
        buildCodonMap(0, dnaStr);
        printCodonCounts(1, 10);
        System.out.println("Most Common: " + getMostCommonCodon());
        System.out.println("Unique: " + codons.size());
        System.out.println("-------- END 0 --------");
        
        buildCodonMap(1, dnaStr);
        printCodonCounts(1, 10);
        System.out.println("Most Common: " + getMostCommonCodon());
        System.out.println("Unique: " + codons.size());
        System.out.println("-------- END 1 --------");
        
        buildCodonMap(2, dnaStr);
        printCodonCounts(1, 10);
        System.out.println("Most Common: " + getMostCommonCodon());
        System.out.println("Unique: " + codons.size());
        System.out.println("-------- END 2 --------");
    }
}
