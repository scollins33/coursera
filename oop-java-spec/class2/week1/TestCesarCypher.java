
/**
 * Write a description of TestCesarCypher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class TestCesarCypher {
    public TestCesarCypher() {
    }
    
    public int[] countLetters(String message) {
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        
        for (int k = 0; k < message.length(); k++) {
            char ch = Character.toLowerCase(message.charAt(k));
            int dex = alph.indexOf(ch);
            
            if (dex != -1) {
                counts[dex] += 1;
            }
        }
        
        return counts;
    }
    
    public int maxIndex(int[] vals) {
        int maxDex = 0;
        for (int k = 0; k < vals.length; k++) {
            if (vals[k] > vals[maxDex]) {
                maxDex = k;
            }
        }
        return maxDex;
    }
    
    public String breakCesarCypher(String input) {        
        int[] freqs = countLetters(input);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex - 4;
        
        if (maxDex < 4) {
            dkey = 26 - (4 - maxDex);
        }
        
        System.out.println("Max Dex (key) found: " + maxDex);
        CesarCypherObj cc = new CesarCypherObj(maxDex);
        
        return cc.decrypt(input);
    }
    
    public void simpleTests() {
        FileResource file = new FileResource();
        String fileString = file.asString();
        
        CesarCypherObj cc15 = new CesarCypherObj(15);
        String enc15 = cc15.encrypt("Can you imagine life WITHOUT the internet AND computers in your pocket?");
        System.out.println("enc15 |" + enc15);
        
        String dec15 = cc15.decrypt(enc15);
        System.out.println("dec15 |" + dec15);
        
        //String broken = breakCesarCypher(enc18);
        //System.out.println("broken |" + broken);
    }
}
