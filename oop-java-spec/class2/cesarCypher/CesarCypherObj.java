
/**
 * Write a description of CesarCypherObj here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CesarCypherObj {
    /*
     * Encapsulation: the idea of putting code and data together in an object
     * Methods: let you act on the data inside of the object and with inputs
     * Fields: which are also called instance variables. They let you declare data that should be inside of objects.
     * Visibility: private and public, restrict access to fields and methods, 
     *      so you can enforce abstractions and provide the interface you want.
     * Constructors: which let you write code specifying how to initialize the objects you create.
     */
    
    // fields AKA "instance variables"
    // private is specific to each INSTANCE of the object
    private String upAlphabet;
    private String lowAlphabet;
    private String combAlphabet;
    private String shiftedAlphabet;
    
    // initialization
    public CesarCypherObj(int key) {
        upAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        lowAlphabet = "abcdefghijklmnopqrstuvwxyz";
        combAlphabet = upAlphabet + lowAlphabet;
        
        String uShifted = upAlphabet.substring(key) + upAlphabet.substring(0, key);
        String lShifted = lowAlphabet.substring(key) + lowAlphabet.substring(0, key);        
        shiftedAlphabet = uShifted + lShifted;
    }
    
    public String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder(input);
        
        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = combAlphabet.indexOf(currChar);
            
            if (idx != -1) {
                char newChar = shiftedAlphabet.charAt(idx);
                encrypted.setCharAt(i, newChar);
            }
        }
        
        return encrypted.toString();
    }
}
