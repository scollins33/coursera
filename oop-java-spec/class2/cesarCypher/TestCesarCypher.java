
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
        
        CesarCypherObj cc18 = new CesarCypherObj(18);
        String enc18 = cc18.encrypt(fileString);
        System.out.println("enc18 |" + enc18);
        
        String dec18 = cc18.decrypt(enc18);
        System.out.println("dec18 |" + dec18);
        
        String broken = breakCesarCypher(enc18);
        System.out.println("broken |" + broken);
    }
}
