
/**
 * Write a description of whileLoop here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class whileLoop {
    public String findGene(String dna) {
        int startIndex = dna.indexOf("ATG");
        int currIndex = dna.indexOf("TAA", startIndex + 3);
        
        while (currIndex != -1) {
            if ((currIndex - startIndex) % 3 == 0) {
                return dna.substring(startIndex, currIndex + 3);
            }
            else {
                currIndex = dna.indexOf("TAA", currIndex + 1);
            }
        }
        
        return ""; // incase you cant find anything valid
    }
    
    public void testFindGeneSimple() {
        String dna = "AATGCGTAATTAATCG";
        System.out.println("DNA is: " + dna);
        String gene = findGene(dna);
        System.out.println("Gene is: " + gene);
        
        dna = "CGATGGTTGATAAGCCTAAGCTATAA";
        System.out.println("DNA is: " + dna);
        gene = findGene(dna);
        System.out.println("Gene is: " + gene);
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
    
    public String findGeneBetter(String dna) {
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) { return ""; }
        
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        
        int tmp = Math.min(taaIndex,tagIndex);
        int minIndex = Math.min(tmp, tgaIndex);
        
        if (minIndex == dna.length()) { return ""; }
        
        return dna.substring(startIndex, minIndex + 3);
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
    
    public void printAllGenes(String dna) {
       int startIndex = 0;
       
       while (true) {
           String currentGene = findGeneBetterAlt(dna, startIndex);
           
           if (currentGene.isEmpty()) {
               break;
           }
           else {
                System.out.println(currentGene);
                startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
           }
       }
    }
    
    public int howMany(String stra, String strb) {
        int occ = 0;
        int index = 0;
        
        while (true) {
            int loopIndex = strb.indexOf(stra, index);
            
            if (loopIndex == -1) {
                break;
            }
            
            occ++;
            index = loopIndex + stra.length();
        }
        
        return occ;
    }
    
    public int findGeneIndex(String dna, int where) {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1) { return -1; }
        
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
        
        if (minIndex == -1) { return -1; }
        
        return (minIndex + 3);
    }
    
    public int countGene(String dna) {
        int occ = 0;
        int tracker = 0;
        
        while (true) {
            int loopIndex = findGeneIndex(dna, tracker);
            
            if (loopIndex == -1) { break; }
            
            occ++;
            tracker = loopIndex + 1;
        }
        
        return occ;
    }
    
    public void test() {
        String ex1 = "ATGTAAGATGCCCTAGT";
        
        System.out.println(countGene(ex1));
    }
}
