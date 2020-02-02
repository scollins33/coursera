
/**
 * Write a description of CaesarBreaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class CaesarBreaker {
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
    
    public String decrypt(String encrypted) {
        CesarCypher cc = new CesarCypher();
        
        int[] freqs = countLetters(encrypted);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex - 4;
        if (maxDex < 4) {
            dkey = 26 - (4 - maxDex);
        }
        System.out.println("Max Dex (key) found: " + maxDex);
        return cc.encrypt(encrypted, 26 - dkey);
    }
    
    public String decryptWithKey(String encrypted, int key) {
        CesarCypher cc = new CesarCypher();
        
        return cc.encrypt(encrypted, 26 - key);
    }
    
    public String decryptTwoKeys(String encrypted) {
        String firstHalf = halfOfString(encrypted, 0);
        String secondHalf = halfOfString(encrypted, 1);
        
        String firstDecrypt = decrypt(firstHalf);
        String secondDecrypt = decrypt(secondHalf);
        System.out.println(firstDecrypt);
        System.out.println(secondDecrypt);
        
        StringBuilder first = new StringBuilder(firstDecrypt);
        StringBuilder second = new StringBuilder(secondDecrypt);
        StringBuilder combined = new StringBuilder(first.toString() + second.toString());
        
        for (int i = 0; i < firstDecrypt.length(); i++) {
            if (i == 0) {
                combined.setCharAt(0, first.charAt(i));
                //System.out.println(i + "|" + combined.toString());
            }
            else {
                combined.setCharAt(i * 2, first.charAt(i));
                //System.out.println(i + "|" + combined.toString());
            }
        }
        
        for (int j = 0; j < secondDecrypt.length(); j++) {
            if (j == 0) {
                combined.setCharAt(1, second.charAt(j));
                //System.out.println(j + "|" + combined.toString());
            }
            else {
                combined.setCharAt(j * 2 + 1, second.charAt(j));
                //System.out.println(j + "|" + combined.toString());
            }
        }
        
        System.out.println("----------------------------------");
        System.out.println("combined:");
        System.out.println(combined.toString());
        System.out.println("----------------------------------");
        return combined.toString();
    }
    
    public String decryptWithTwoKeys(String encrypted, int key1, int key2) {
        String firstHalf = halfOfString(encrypted, 0);
        String secondHalf = halfOfString(encrypted, 1);
        
        String firstDecrypt = decryptWithKey(firstHalf, key1);
        String secondDecrypt = decryptWithKey(secondHalf, key2);
        System.out.println(firstDecrypt);
        System.out.println(secondDecrypt);
        
        StringBuilder first = new StringBuilder(firstDecrypt);
        StringBuilder second = new StringBuilder(secondDecrypt);
        StringBuilder combined = new StringBuilder(first.toString() + second.toString());
        
        for (int i = 0; i < firstDecrypt.length(); i++) {
            if (i == 0) {
                combined.setCharAt(0, first.charAt(i));
                //System.out.println(i + "|" + combined.toString());
            }
            else {
                combined.setCharAt(i * 2, first.charAt(i));
                //System.out.println(i + "|" + combined.toString());
            }
        }
        
        for (int j = 0; j < secondDecrypt.length(); j++) {
            if (j == 0) {
                combined.setCharAt(1, second.charAt(j));
                //System.out.println(j + "|" + combined.toString());
            }
            else {
                combined.setCharAt(j * 2 + 1, second.charAt(j));
                //System.out.println(j + "|" + combined.toString());
            }
        }
        
        System.out.println("combined:" + combined.toString());
        return combined.toString();
    }
    
    public String combineStrings(String firstDecrypt, String secondDecrypt) {
        StringBuilder first = new StringBuilder(firstDecrypt);
        StringBuilder second = new StringBuilder(secondDecrypt);
        StringBuilder combined = new StringBuilder(first.toString() + second.toString());
        
        for (int i = 0; i < firstDecrypt.length(); i++) {
            if (i == 0) {
                combined.setCharAt(0, first.charAt(i));
                //System.out.println(i + "|" + combined.toString());
            }
            else {
                combined.setCharAt(i * 2, first.charAt(i));
                //System.out.println(i + "|" + combined.toString());
            }
        }
        
        for (int j = 0; j < secondDecrypt.length(); j++) {
            if (j == 0) {
                combined.setCharAt(1, second.charAt(j));
                //System.out.println(j + "|" + combined.toString());
            }
            else {
                combined.setCharAt(j * 2 + 1, second.charAt(j));
                //System.out.println(j + "|" + combined.toString());
            }
        }
        
        System.out.println("combined:" + combined.toString());
        return combined.toString();
    }
    
    public String halfOfString(String target, int start) {
        StringBuilder builder = new StringBuilder(target);
        StringBuilder half = new StringBuilder();
        
        for (int i = start; i < target.length(); i = i + 2) {
            half.append(builder.charAt(i));
        }
        
        System.out.println("halfOfString: " + half.toString());
        return half.toString();
    }
    
    public void testBreaker() {
        FileResource resource = new FileResource("data/smallHamlet.txt");
        String fileStr = resource.asString();
        CesarCypher cc = new CesarCypher();
        
        System.out.println(fileStr);
        
        String encryptedText = cc.encrypt(fileStr, 15);
        System.out.println(encryptedText);
        
        String decryptedText = decrypt(encryptedText);
        System.out.println(decryptedText);
    }
    
    public void test2breaker() {
        FileResource resource = new FileResource();
        String fileStr = resource.asString();
        
        //CesarCypher cc = new CesarCypher();
        //System.out.println(fileStr);
        //String encryptedText = cc.encryptTwoKeys(fileStr, 23, 2);
        //System.out.println(encryptedText);
        //String decryptedText = decryptTwoKeys(encryptedText);
        //System.out.println(decryptedText);
        //System.out.println("-----------------------------");
        
        //String dec2 = decryptTwoKeys("Akag tjw Xibhr awoa aoee xakex znxag xwko");
        //System.out.println(dec2);
        
        String dec3 = decryptTwoKeys(fileStr);
        System.out.println(dec3);
    }
    
    public void question8() {
        String str = "Akag tjw Xibhr awoa aoee xakex znxag xwko";
        
        String str1 = halfOfString(str, 0);
        String str2 = halfOfString(str, 1);
        
        String str1a = decryptWithKey(str1, 2);
        String str2a = decryptWithKey(str2, 20);
        String str1b = decryptWithKey(str1, 20);
        String str2b = decryptWithKey(str1, 2);
        
        System.out.println("1:" + str1a);
        System.out.println("2:" + str2a);
        System.out.println("------------------------------");
        System.out.println("1:" + str1b);
        System.out.println("2:" + str2b);
        
        System.out.println(combineStrings(str1a, str2a));
        System.out.println(combineStrings(str1b, str2b));
    }
    
    public void question6() {
        String str = "Hfs cpwewloj loks cd Hoto kyg Cyy.";
        
        String dec = decryptWithTwoKeys(str, 14, 24);
    }
    
    public void question7() {
        String str = "Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";
        
        String dec = decryptTwoKeys(str);
    }
}
