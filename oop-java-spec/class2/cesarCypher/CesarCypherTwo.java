
/**
 * Write a description of CesarCypherTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CesarCypherTwo {
    private String upAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String lowAlphabet = "abcdefghijklmnopqrstuvwxyz";
    private String combAlphabet = upAlphabet + lowAlphabet;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int mainKey1;
    private int mainKey2;
    
    // initialization
    public CesarCypherTwo(int key1, int key2) {
        //upAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //lowAlphabet = "abcdefghijklmnopqrstuvwxyz";
        //combAlphabet = upAlphabet + lowAlphabet;
        
        String uShifted1 = upAlphabet.substring(key1) + upAlphabet.substring(0, key1);
        String lShifted1 = lowAlphabet.substring(key1) + lowAlphabet.substring(0, key1);        
        shiftedAlphabet1 = uShifted1 + lShifted1;
        
        String uShifted2 = upAlphabet.substring(key2) + upAlphabet.substring(0, key2);
        String lShifted2 = lowAlphabet.substring(key2) + lowAlphabet.substring(0, key2);        
        shiftedAlphabet2 = uShifted2 + lShifted2;
        
        mainKey1 = key1;
        mainKey2 = key2;
    }
    
    public String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder(input);
        
        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i); // get our current place
            int idx = combAlphabet.indexOf(currChar); // find it in the regular alphabet
            
            if (idx != -1) {
                if ((i+2) % 2 == 0) { // plus 2 so there is no error on index 0 and 1
                    encrypted.setCharAt(i, shiftedAlphabet1.charAt(idx)); // same index as 1st shift
                }
                else {
                    encrypted.setCharAt(i, shiftedAlphabet2.charAt(idx)); // same index as 2nd shift
                }
            }
        }
        
        return encrypted.toString();
    }
}
