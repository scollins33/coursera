import java.util.*;
import edu.duke.*;
import java.io.File;

public class VigenereBreaker {
    
    private String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slicedLetters = new StringBuilder();
                
        for (int i = whichSlice; i < message.length(); i = i + totalSlices) {
            //System.out.println("index: " + i);
            slicedLetters.append(message.substring(i, i+1));
        }
        
        return slicedLetters.toString();
    }

    private int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        
        for (int i = 0; i < klength; i++) {
            //System.out.println("looking for key " + i);
            // make a slice
            String thisSlice = sliceString(encrypted, i, klength);
            
            // try to break it
            CaesarCracker cracker = new CaesarCracker(mostCommon);
            key[i] = cracker.getKey(thisSlice);;
        }
        
        return key;
    }
    
    private String runDecrypt(String encrypted, int keyLength, char mostCommon) {
        int[] key = tryKeyLength(encrypted, keyLength, mostCommon);
        
        VigenereCipher vigy = new VigenereCipher(key);
        
        return vigy.decrypt(encrypted);
    }
    
    private HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> set = new HashSet<String>();
        
        for (String line : fr.lines()) {
            set.add(line.toLowerCase());
        }
        
        return set;
    }
            
    private int countWords(String message, HashSet<String> dict) {
        String[] words = message.split("\\W+");
        int counter = 0;
        
        for (String word : words) {
            if (dict.contains(word.toLowerCase())) {
                counter++;
            }
        }
        
        return counter;
    }
    
    private int breakForLanguage(String encrypted, HashSet<String> dict) {
        int matchingHighscore = 0;
        int highscoreKeyLen = 0;
        
        for (int i = 1; i < 101; i++) {
            
            String dec = runDecrypt(encrypted, i, 'e');
            int score = countWords(dec, dict);
            
            if (score > matchingHighscore) {
                matchingHighscore = score;
                highscoreKeyLen = i;
            }
        }
        
        return highscoreKeyLen;
    }
    
    private char mostCommonCharIn(HashSet<String> dict) {
        HashMap<Character, Integer> counters = new HashMap<Character, Integer>();
        
        // loop through each word to build the count of chars
        for (String word : dict) {
            // stringbuilder so we have Chars
            StringBuilder wordBuilder = new StringBuilder(word);
            
            // loop through each character in the word
            for (int i = 0; i < word.length(); i++) {
                Character thisChar = wordBuilder.charAt(i);
                
                if (counters.containsKey(thisChar)) {
                    counters.put(thisChar, counters.get(thisChar) + 1); // increment it by 1
                }
                else {
                    counters.put(thisChar, 1);
                }
            }          
        }
        
        // now loop through the HashMap and find the highest character
        int highscore = 0;
        char highChar = 'a';
        
        for (Character c : counters.keySet()) {
            int score = counters.get(c);
            System.out.println(c + " : " + score);
            
            if (score > highscore) {
                highscore = score;
                highChar = c;
            }
        }
                
        return highChar;
    }
    
    private int breakForLanguageChar(String encrypted, HashSet<String> dict, char common) {
        int matchingHighscore = 0;
        int highscoreKeyLen = 0;
        
        for (int i = 1; i < 101; i++) {
            
            String dec = runDecrypt(encrypted, i, common);
            int score = countWords(dec, dict);
            
            if (score > matchingHighscore) {
                matchingHighscore = score;
                highscoreKeyLen = i;
            }
        }
        
        return highscoreKeyLen;
    }
    
    private String breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        System.out.println("---------- breakForAllLangs ----------");
        String highLang = "";
        int highscore = 0;
        
        for (String lang : languages.keySet()) {
            HashSet<String> thisLang = languages.get(lang);
            char most = mostCommonCharIn(thisLang);
            
            // find the best key for this lang
            int bestKeyLength = breakForLanguageChar(encrypted, thisLang, most);
            
            // now get a matching word count as a score
            String dec = runDecrypt(encrypted, bestKeyLength, most);
            int score = countWords(dec, thisLang);
            
            System.out.println(lang + " : " + score);
            
            // check the score
            if (score > highscore) {
                highscore = score;
                highLang = lang;
            }
        }
        
        System.out.println("Highscore Lang: " + highLang);
        
        // return the language with the best match AKA the KEY for LANGUAGES
        return highLang;
    }
    
    public void breakVigenere() {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        
        FileResource frDict = new FileResource();
        HashSet<String> hashDict = readDictionary(frDict);
        
        int length = breakForLanguage(encrypted, hashDict);
        
        System.out.println("Length: " + length);
        
        String decrypted = runDecrypt(encrypted, length, 'e');
        System.out.println("-----------------------------");
        System.out.println(decrypted);
        System.out.println("Valid words OG: " + countWords(decrypted, hashDict));
    }
    
    public void breakVigenereAll() {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        
        HashMap<String, HashSet<String>> allLangDicts = new HashMap<String, HashSet<String>>();
        
        DirectoryResource drDicts = new DirectoryResource();
        for (File f : drDicts.selectedFiles()) {
            FileResource thisDict = new FileResource(f);
            
            // get the filename and compute the dictionary
            System.out.println("Reading " + f.getName());
            allLangDicts.put(f.getName(), readDictionary(thisDict));
        }
        
        String bestLang = breakForAllLangs(encrypted, allLangDicts);
                
        HashSet<String> thisLang = allLangDicts.get(bestLang);
        
        // do all this again because im lazy
        char most = mostCommonCharIn(thisLang);
        int bestKeyLength = breakForLanguageChar(encrypted, thisLang, most);
        String dec = runDecrypt(encrypted, bestKeyLength, most);
        int score = countWords(dec, thisLang);
        
        System.out.println(dec);
        System.out.println("Valid words OG: " + score);
        System.out.println("bestLang: " + bestLang);
    }
    
    public void testSliceString() {
        System.out.println(sliceString("abcdefghijklm", 0, 3));
        System.out.println(sliceString("abcdefghijklm", 1, 3));
        System.out.println(sliceString("abcdefghijklm", 2, 3));
        System.out.println(sliceString("abcdefghijklm", 0, 4));
        System.out.println(sliceString("abcdefghijklm", 1, 4));
        System.out.println(sliceString("abcdefghijklm", 2, 4));
        System.out.println(sliceString("abcdefghijklm", 3, 4));
        System.out.println(sliceString("abcdefghijklm", 0, 5));
        System.out.println(sliceString("abcdefghijklm", 1, 5));
        System.out.println(sliceString("abcdefghijklm", 2, 5));
        System.out.println(sliceString("abcdefghijklm", 3, 5));
        System.out.println(sliceString("abcdefghijklm", 4, 5));
    }
    
    public void testTryKeyLength() {
        FileResource fr = new FileResource();
        String enc = fr.asString();
        
        int[] keys = tryKeyLength(enc, 38, 'e');
    }
    
    public void testCertainLengthDecrypt() {
        FileResource fr = new FileResource();
        String enc = fr.asString();
        
        FileResource frDict = new FileResource();
        HashSet<String> hashDict = readDictionary(frDict);
                
        String dec = runDecrypt(enc, 57, 'e');
        
        System.out.println("Words count 57: " + countWords(dec, hashDict));
        
        String dec2 = runDecrypt(enc, 38, 'e');
        
        System.out.println("Words count 38: " + countWords(dec2, hashDict));
    }
    
    
    public void testMostCommonChar() {
        FileResource frDict = new FileResource();
        HashSet<String> hashDict = readDictionary(frDict);
        
        char most = mostCommonCharIn(hashDict);
        
        System.out.println("winner: " + most);
    }
}
