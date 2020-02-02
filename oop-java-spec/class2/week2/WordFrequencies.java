
/**
 * Find out how many times each word occurs, and
 * in particular the most frequently occurring word.
 * 
 * @author Duke Software Team
 * @version 1.0
 */
import edu.duke.*;
import java.util.ArrayList;

public class WordFrequencies
{
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public WordFrequencies() {
        myWords = new ArrayList<String>();
        // have to use Integer not int
        myFreqs = new ArrayList<Integer>();
    }
    
    public void findUnique(){
        // clear things out so the last file doesnt affect things
        myWords.clear();
        myFreqs.clear();
        
        FileResource resource = new FileResource();
        
        for(String s : resource.words()){
            s = s.toLowerCase(); // all things are equal
            
            int index = myWords.indexOf(s); // find the word in myWords
            
            if (index == -1){ // it doesnt exist 
                myWords.add(s); // add it
                myFreqs.add(1); // set Freqs to 1 since we know of 1 instance
            }
            else {
                int freq = myFreqs.get(index); // fetch the number
                myFreqs.set(index,freq+1); // increment it and set it
            }
        }
    }
    
    public void tester(){
        findUnique();
        System.out.println("-----------------------------------------");
        System.out.println("# unique words: "+myWords.size());
        
        //for (int k = 0; k < myWords.size(); k++) {
          //  System.out.println("\t" + myWords.get(k) + "\t" + myFreqs.get(k));
        //}
        
        int index = findMax();
        System.out.println("max word/freq: "+myWords.get(index)+" "+myFreqs.get(index));
        System.out.println("-----------------------------------------");
    }
    
    public int findMax(){
        int max = myFreqs.get(0);
        int maxIndex = 0;
        for(int k=0; k < myFreqs.size(); k++){
            if (myFreqs.get(k) > max){
                max = myFreqs.get(k);
                maxIndex = k;
            }
        }
        return maxIndex;
    }
}
