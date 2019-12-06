
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class Part1 {
    public StorageResource getAllGenes(String dna) {
       int startIndex = 0;
       StorageResource geneList = new StorageResource();
       
       while (true) {
           String currentGene = findGeneBetterAlt(dna, startIndex);
           
           if (currentGene.isEmpty()) {
               break;
           }
           else {
                geneList.add(currentGene);
                startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
           }
       }
       
       return geneList;
    }
    
    public String findGeneBetterAlt(String dna, int where) {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1) { return ""; }
        
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        
        int minIndex = 0;
                
        if (taaIndex == -1 || (tgaIndex != -1 && tgaIndex < taaIndex)) {
            minIndex = tgaIndex;
        }
        else {
            minIndex = taaIndex;
        }
        
        if (minIndex == -1 || (tgaIndex != -1 && tagIndex < minIndex)) {
            minIndex = tagIndex;
        }
        
        if (minIndex == -1) { return ""; }
        
        return dna.substring(startIndex, minIndex + 3);
    }
    
    public int findStopCodon(String dnaStr, int startIndex, String stopCodon) {
        int stopIndex = dnaStr.indexOf(stopCodon, startIndex + 3);
        
        while (stopIndex != -1) {
            int diff = stopIndex - startIndex;
            if (diff % 3 == 0) {
                return stopIndex;
            }
            else {
                stopIndex = dnaStr.indexOf(stopCodon, stopIndex + 1);
            }
        }
    
        return -1;
    }
    
    public float cgRatio(String dna) {
        float count = 0;
        int index = 0;
        
        while (index < dna.length()) {
            char thisChar = dna.charAt(index);
            if (thisChar == 'C' || thisChar == 'G') { count = count + 1; }
            index++;
        }
        return (count / index);
    }
    
    public int countCTG(String dna) {
        int index = 0;
        int count = 0;
        
        while (index <= dna.length() - 3) {            
            if (dna.substring(index, index + 3).equals("CTG")) {
                count++;
            }
            index++;
        }
        
        return count;
    }
    
    public void processGenes(StorageResource sr) {
        int counter9 = 0;
        int counterRatio = 0;
        int longest = 0;
        
        for (String str : sr.data()) {
            if (str.length() > 60) {
                System.out.println(str);
                counter9++;
            }
            
            if (cgRatio(str) > 0.35) {
                counterRatio++;
            }
            
            if (str.length() > longest) {
                longest = str.length();
            }
        }
        System.out.println("60s " + counter9);
        System.out.println("Ratio " + counterRatio);
        System.out.println("Longest " + longest);
    }
    
    public void test() {
        String ex1 = "ATGTAAGATGCCCTAGT";
        StorageResource glist = getAllGenes(ex1);
        
        for (String g : glist.data()) {
            System.out.println(g);
        }
        
        String ex2 = "ATGCCATAG";
        float ratio = cgRatio(ex2);
        
        String ex3 = "CTGCTGAB";
        int ctg = countCTG(ex3);
    }
    
    public void testProcessGenes() {
        // String ex1 = "ATGTAAGATGCCCTAGT";
        // String ex2 = "ATGCCATAG";
        // String ex3 = "CTGCTGAB";
        // String ex4 = "CGATGGTTGATAAGCCTAAGCTATAA";
        
        // StorageResource testing = new StorageResource();
        // testing.add(ex1);
        // testing.add(ex2);
        // testing.add(ex3);
        // testing.add(ex4);
        
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        
        System.out.println("----------------------------");
        System.out.println(dna);
        
        StorageResource allGenes = getAllGenes(dna);
        System.out.println(allGenes.size());
                
        processGenes(allGenes);
    }
}
