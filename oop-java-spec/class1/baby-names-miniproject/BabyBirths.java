
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyBirths {
    /*
     *  VIDEO METHODS
     */
    public void printNames() {
        FileResource fr = new FileResource();
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                                " | Gender " + rec.get(1) +
                                " | NumBorn " + rec.get(2)
                                );
            }
        }
    }
    
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalNames = 0;
        int totalBoysNames = 0;
        int totalGirlsNames = 0;
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            totalNames += 1;
            
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                totalBoysNames += 1;
            }
            else {
                totalGirls += numBorn;
                totalGirlsNames += 1;
            }
        }
        
        System.out.println("Total Births: " + totalBirths);
        System.out.println("Total Names: " + totalNames);
        System.out.println("Total Boys: " + totalBoys);
        System.out.println("Total Boys Names: " + totalBoysNames);
        System.out.println("Total Girls: " + totalGirls);
        System.out.println("Total Girls Names: " + totalGirlsNames);
    }
    
    /*
     *  ASSIGNMENT METHODS
     */
    public int getRank(int year, String name, String gender) {
        
        int rank = 0;
        FileResource fr = new FileResource("./us_babynames_by_year/yob" + year + ".csv");
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank += 1; // started at 0 so add first, only add correct gender
                
                if (rec.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        
        return -1;
    }
    
    public int getRankFile(File file, String name, String gender) {
        
        int rank = 0;
        FileResource fr = new FileResource(file);
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank += 1; // started at 0 so add first, only add correct gender
                
                if (rec.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        
        return -1;
    }
    
    public String getName(int year, int queryRank, String gender) {
        
        int rank = 0;
        FileResource fr = new FileResource("./us_babynames_by_year/yob" + year + ".csv");
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank += 1; // started at 0 so add first, only add correct gender
                
                if (rank == queryRank) {
                    return rec.get(0);
                }
            }
        }
        
        return "NO NAME";
    }
    
    public void whatIsNameInYear (String name, int year, int newYear, String gender) {
        
        int ogRank = getRank(year, name, gender);
        
        String newName = getName(newYear, ogRank, gender);
        
        log(name + " born in " + year + " would have been " + newName + " in " + newYear);
    }
    
    public int yearOfHighestRank(String name, String gender) {
        DirectoryResource dir = new DirectoryResource();
        int topRank = 1000000000;
        int topYear = -1;
        
        for (File file : dir.selectedFiles()) {
            int fileRank = getRankFile(file, name, gender);
            
            if (fileRank < topRank) {
                topRank = fileRank;
                
                String filename = file.getName();
                String strYear = filename.substring(3, 7);
                topYear = Integer.parseInt(strYear);
            }
        }

        return topYear;
    }
    
    public double getAverageRank(String name, String gender) {
        DirectoryResource dir = new DirectoryResource();
        int totalRank = 0;
        int denom = 0;
        
        for (File file : dir.selectedFiles()) {
            int fileRank = getRankFile(file, name, gender);
            denom++;
            
            // handle in case rank is -1 for a year
            if (fileRank > 0) {
                totalRank += fileRank;
            }
        }
        
        if (totalRank == 0) {
            return -1.0;
        }
        else {
            return (double) totalRank / denom;
        }
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        
        int totalBirths = 0;
        FileResource fr = new FileResource("./us_babynames_by_year/yob" + year + ".csv");
        //FileResource fr = new FileResource("./us_babynames_test/yob" + year + "short.csv");
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            // make sure we're in the right gender section
            if (rec.get(1).equals(gender)) {
                // if we found the name then eject
                if (rec.get(0).equals(name)) {
                    return totalBirths;
                }
                // otherwise add the births since the name is below us in rank still
                else {
                    totalBirths += Integer.parseInt(rec.get(2));
                }
            }
        }
        
        return totalBirths;
    }
    
    /*
     *  HELPERS
     */    
    public void log(String str) {
        System.out.println(str);
    }
    
    /*
     *  TESTING METHODS
     */
    public void testTotalBirths() {
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    
    public void testGetRank() {
        int rank = getRank(2012, "Mason", "M");
        log("getRank: " + rank);
        int rank1 = getRank(1960, "Emily", "F");
        log("getRank: " + rank1);
        int rank2 = getRank(1971, "Frank", "M");
        log("getRank: " + rank2);
    }
    
    public void testGetName() {
        String name = getName(1980, 350, "F");
        log("getName: " + name);
        String name1 = getName(1982, 450, "M");
        log("getName: " + name1);
    }
    
    public void testWhatNameYear() {
        whatIsNameInYear("Susan", 1972, 2014, "F");
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    public void testYearHighestRank() {
        int highestYear = yearOfHighestRank("Genevieve", "F");
        log("Most popular year was " + highestYear);
        int highestYear1 = yearOfHighestRank("Mich", "M");
        log("Most popular year was " + highestYear1);
    }
    
    public void testGetAverageRank() {
        double avgMason = getAverageRank("Susan", "F");
        log("Susan average is " + avgMason);
        double avgJacob = getAverageRank("Robert", "M");
        log("Robert average is " + avgJacob);
    }
    
    public void testGetTotalBirthsRankedHigher() {
        int totalEthan = getTotalBirthsRankedHigher(1990, "Emily", "F");
        log("Emily TotalBirths is " + totalEthan);
        int totalEthan1 = getTotalBirthsRankedHigher(1990, "Drew", "M");
        log("Drew TotalBirths is " + totalEthan1);
    }
}
