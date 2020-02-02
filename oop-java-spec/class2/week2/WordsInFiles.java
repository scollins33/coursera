
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import java.io.File;

public class WordsInFiles {
    
    private HashMap<String, HashSet<String>> wordFiles;
    
    public WordsInFiles() {
        wordFiles = new HashMap<String, HashSet<String>>();
    }
    
    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        String name = f.getName();
        
        for (String word : fr.words()) {
            if (wordFiles.containsKey(word)) {
                HashSet<String> thisList = wordFiles.get(word);
                thisList.add(name);
                wordFiles.put(word, thisList);
            }
            else {
                HashSet<String> list = new HashSet<String>();
                list.add(name);
                wordFiles.put(word, list);
            }
        }
    }
    
    private void buildWordFileMap() {
        wordFiles.clear();
        
        DirectoryResource dr = new DirectoryResource();
        
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }
    
    private int maxNumber() {
        int max = 0;
        
        for (String word : wordFiles.keySet()) {
            int size = wordFiles.get(word).size();
            
            if (size > max) {
                max = size;
            }
        }
        
        return max;
    }
    
    private ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> wordsList = new ArrayList<String>();
        int count = 0;
        
        for (String word : wordFiles.keySet()) {
            int size = wordFiles.get(word).size();
            
            if (size == number) {
                wordsList.add(word);
                count++;
            }
        }
        
        System.out.println("COUNT @ " + number + " : " + count);
        
        return wordsList;
    }
    
    private void printFilesIn(String word) {
        HashSet<String> thisSet = wordFiles.get(word);
        
        System.out.println(word);
        for (String name : thisSet) {
            System.out.println("\t" + name);
        }
        System.out.println("----------------");
    }
    
    public void tester() {
        buildWordFileMap();
        
        int max = maxNumber();
        ArrayList<String> maxList = wordsInNumFiles(max);
        
        //for (String word : maxList) {
        //    printFilesIn(word);
        //}
        
        ArrayList<String> fiveList = wordsInNumFiles(5);
        ArrayList<String> fourList = wordsInNumFiles(4);
        printFilesIn("sad");
        printFilesIn("red");
        printFilesIn("laid");
        printFilesIn("tree");
    }
}
