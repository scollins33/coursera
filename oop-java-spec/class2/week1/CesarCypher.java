
/**
 * Write a description of CesarCypher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class CesarCypher {
    // strings are immutable.., have to make a new one
    
    /**
     * strings are immutable.., have to make a new one
     *  -- have to use StringBuilder
     * 
     * Character Class
     *  char is a primitive
     *  single '' is the primitive
     *  double "" is a String
     *  
     *  Character.toLowerCase('G') ==> 'g'
     *  
     */
    public void digitTest() {
        String test = "ABCabc123';#!";
        
        for (int k = 0; k < test.length(); k++) {
            char ch = test.charAt(k);
            
            if (Character.isDigit(ch)) {
                System.out.println(ch + " is a digit");
            }
            
            if (Character.isAlphabetic(ch)) {
                System.out.println(ch + " is alphabetic");
            }
        }
    }
    
    public String getUpperAlph() {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
    
    public String getLowerAlph() {
        return "abcdefghijklmnopqrstuvwxyz";
    }
    
    public String getAlphabet() {
        return getUpperAlph() + getLowerAlph();
    }
    
    public String shiftAlphabet(int key) {
        String uAlph = getUpperAlph();
        String uShifted = uAlph.substring(key) + uAlph.substring(0, key);
        
        String lAlph = getLowerAlph();
        String lShifted = lAlph.substring(key) + lAlph.substring(0, key);
        
        return uShifted + lShifted;
    }
    
    public String encrypt(String input, int key) {
        StringBuilder encrypted = new StringBuilder(input);
        
        String alph = getAlphabet();
        String shifted = shiftAlphabet(key);
        
        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = alph.indexOf(currChar);
            
            if (idx != -1) {
                char newChar = shifted.charAt(idx);
                encrypted.setCharAt(i, newChar);
            }
        }
        
        return encrypted.toString();
    }
    
    public String encryptTwoKeys(String input, int key1, int key2) {
        StringBuilder encrypted = new StringBuilder(input);
        
        String alph = getAlphabet();
        String shifted1 = shiftAlphabet(key1);
        String shifted2 = shiftAlphabet(key2);
        
        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i); // get our current place
            int idx = alph.indexOf(currChar); // find it in the regular alphabet
            
            if (idx != -1) {
                if ((i+2) % 2 == 0) { // plus 2 so there is no error on index 0 and 1
                    encrypted.setCharAt(i, shifted1.charAt(idx)); // same index as 1st shift
                }
                else {
                    encrypted.setCharAt(i, shifted2.charAt(idx)); // same index as 2nd shift
                }
            }
        }
        
        return encrypted.toString();
    }
    
    public void testCesar() {
        int key1 = 23;
        String encrypted1 = encrypt("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 15);
        System.out.println(encrypted1);
        System.out.println(encrypt(encrypted1, 26 - 15));
        
        String encrypted2 = encrypt("First Legion", key1);
        System.out.println(encrypted2);
        System.out.println(encrypt(encrypted2, 26 - key1));
        
        int key3 = 17;
        String encrypted3 = encrypt("First Legion", key3);
        System.out.println(encrypted3);
        System.out.println(encrypt(encrypted3, 26 - key3));
        
        String encrypted4 = encryptTwoKeys("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 8, 21);
        System.out.println(encrypted4);
    }
    
    public void weekQuiz() {        
        String question2 = encryptTwoKeys("Can you imagine life WITHOUT the internet AND computers in your pocket?", 21, 8);
        System.out.println(question2);
    }
}
