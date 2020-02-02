
/**
 * Write a description of WordPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordPlay {
    public void test() {
        System.out.println("a is a vowel: " + isVowel('a'));
        //System.out.println("U is a vowel: " + isVowel('U'));
        //System.out.println("b is a vowel: " + isVowel('b'));
        System.out.println("Y is a vowel: " + isVowel('Y'));
        
        System.out.println(replaceVowels("Hello World", '*'));
        
        System.out.println(emphasize("dna ctgaaactga", 'a'));
        System.out.println(emphasize("Mary Bella Abracadabra", 'a'));
    }
    
    public boolean isVowel(char ch) {
        String vowels = "aeiouAEIOU";
        return (vowels.indexOf(ch) != -1);
    }
    
    public String replaceVowels(String phrase, char ch) {
        StringBuilder replaced = new StringBuilder(phrase);
        
        for (int i = 0; i < replaced.length(); i++) {
            if (isVowel(replaced.charAt(i))) {
                replaced.setCharAt(i, ch);
            }
        }
        
        return replaced.toString();
    }
    
    public String emphasize(String phrase, char ch) {
        StringBuilder replaced = new StringBuilder(phrase);
        
        for (int i = 0; i < replaced.length(); i++) {
            if (replaced.charAt(i) == ch) {
                if ((i+1) % 2 == 0) { replaced.setCharAt(i, '+'); } // even
                else { replaced.setCharAt(i, '*'); } // odd
            }
        }
        
        return replaced.toString();
    }
    
    
}
