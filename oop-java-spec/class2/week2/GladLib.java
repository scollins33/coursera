import edu.duke.*;
import java.util.*;

public class GladLib {    
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> usedWords;
    private HashSet<String> usedCats;
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLib(){
        myRandom = new Random();
        
        myMap = new HashMap<String, ArrayList<String>>();
        usedWords = new ArrayList<String>();
        usedCats = new HashSet<String>();
        
        initializeFromSource(dataSourceDirectory);
    }
    
    //public GladLib(String source){
    //    initializeFromSource(source);
    //    myRandom = new Random();
    //}
    
    private void initializeFromSource(String source) {        
        String[] labels = {"country", "noun", "animal", "adjective", "name", "color", "timeframe", "fruit", "verb"};
        
        for (String label : labels) {
            ArrayList<String> list = readIt(source+"/" + label + ".txt");
            myMap.put(label, list);
        }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")) {
           return ""+myRandom.nextInt(50)+5;
        }
        return randomFrom(myMap.get(label));
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        
        if (first == -1 || last == -1){
            return w;
        }
        
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        
        if(usedWords.contains(sub)) {
            sub = processWord(w); // regression baby!
        }
        else {
            usedWords.add(sub); // add the new word to the usedWords
            usedCats.add(w.substring(first+1,last)); // add to HashSet, doesnt overlap so we good
        }
        
        //for(String s : usedWords) {
        //    System.out.println("used: " + s);
        //}
        //System.out.println("-------------");
        
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    private int totalWordsInMap() {
        int count = 0;
        
        for (String label : myMap.keySet()) {
            count = count + myMap.get(label).size();
        }
        
        return count;
    }
    
    private int totalWordsConsidered() {
        int count = 0;
        
        for (String label : usedCats) {
            System.out.println(label);
            System.out.println(myMap.get(label));
            count = count + myMap.get(label).size();
        }
        
        return count;
    }
    
    public void makeStory(){
        usedWords.clear();
        
        System.out.println("\n");
        String story = fromTemplate("datalong/madtemplate2.txt");
        printOut(story, 60);
        
        System.out.println("\n");
        System.out.println("Total Words Possible: " + totalWordsInMap());
        //System.out.println("Total Words Considered: " + totalWordsConsidered());
        System.out.println("-------------------------------------------");
    }
}
