
/**
 * Write a description of weatherCSV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
    public CSVRecord hottestInFile(CSVParser parser) {
        CSVRecord largest = null;
        
        for (CSVRecord record : parser) {
            largest = getLargestRecord(largest, record);
        }
        
        return largest;
    }
    
    public void testHottest() {
        FileResource file = new FileResource("nc_weather/2015/weather-2015-01-02.csv");
        CSVRecord largest = hottestInFile(file.getCSVParser());
        System.out.println("Hottest with " + largest.get("TemperatureF") + " at " + largest.get("TimeEST"));
    }
    
    public CSVRecord hottestInDir() {
        CSVRecord largest = null;
        DirectoryResource dir = new DirectoryResource();
        
        for (File file : dir.selectedFiles()) {
            FileResource fr = new FileResource(file);
            
            CSVParser thisParser = fr.getCSVParser();
            CSVRecord currentLargest = hottestInFile(thisParser);
            
            largest = getLargestRecord(largest, currentLargest);
        }
        
        return largest;
    }
    
    public void testManyDays() {
        CSVRecord largest = hottestInDir();
        System.out.println("Hottest with " + largest.get("TemperatureF") + " at " + largest.get("DateUTC"));
    }
    
    public CSVRecord getLargestRecord(CSVRecord largest, CSVRecord currentLargest) {
        if (largest == null) {
            largest = currentLargest;
        }
        else {
            double temp = Double.parseDouble(currentLargest.get("TemperatureF"));
            double largestDouble = Double.parseDouble(largest.get("TemperatureF"));
            
            if (temp > largestDouble) {
                largest = currentLargest;
            }
        }
        
        return largest;
    }
    
    public CSVRecord getSmallestRecord(CSVRecord thisRecord, CSVRecord currentSmallest, String type) {
        if (currentSmallest == null) {
            currentSmallest = thisRecord;
        }
        else if (type == "TemperatureF" && Double.parseDouble(thisRecord.get(type)) == -9999.0) {
            System.out.println("found a -9999.0, ignoring");
            return currentSmallest;
        }
        else if (type == "Humidity" && thisRecord.get(type).equals("N/A")) {
            System.out.println("found a N/A, ignoring");
            return currentSmallest;
        }
        else {
            //System.out.println("Type: " + type + " | this: [" + thisRecord.get(type) + "]");
            double thisTempDouble = Double.parseDouble(thisRecord.get(type));
            double smallestDouble = Double.parseDouble(currentSmallest.get(type));
            
            if (thisTempDouble < smallestDouble) {
                // System.out.println("found new smallest: " + thisTempDouble + " " + thisRecord.get("DateUTC"));
                currentSmallest = thisRecord;
            }
        }
        
        return currentSmallest;
    }
    
    public CSVRecord coldestHourInFile(CSVParser parser, String targetType) {
        CSVRecord smallest = null;
        
        for (CSVRecord record : parser) {
            smallest = getSmallestRecord(record, smallest, targetType);
        }
        
        return smallest;
    }
    
    public void testColdestInFile() {
        
    }
    
    public File fileWithLowest(String desiredType) {
        File smallestFile = null;
        CSVRecord smallestRecord = null;
        DirectoryResource dir = new DirectoryResource();
        
        for (File file : dir.selectedFiles()) {
            FileResource fr = new FileResource(file);
            
            CSVRecord thisFileSmallest = coldestHourInFile(fr.getCSVParser(), desiredType);
            
            if (smallestRecord == null) {
                smallestRecord = thisFileSmallest;
                smallestFile = file;
            }
            else {
                double thisTempDouble = Double.parseDouble(thisFileSmallest.get(desiredType));
                double smallestDouble = Double.parseDouble(smallestRecord.get(desiredType));
                
                if (thisTempDouble < smallestDouble) {
                    System.out.println("FILE found new smallest: " + file.getName() + " " + thisTempDouble + " " + thisFileSmallest.get("DateUTC"));
                    smallestRecord = thisFileSmallest;
                    smallestFile = file;
                }
            }
        }
        
        return smallestFile;
    }
    
    public void testFileWithColdestTemperature() {
        File smallestFile = fileWithLowest("TemperatureF");
        System.out.println("Coldest day was in: " + smallestFile.getName());
        
        FileResource smallestFileR = new FileResource(smallestFile);
        CSVParser coldParser = smallestFileR.getCSVParser();
        CSVRecord coldest = coldestHourInFile(coldParser, "TemperatureF");
        System.out.println("Coldest temp was " + coldest.get("TemperatureF"));
        
        System.out.println("All temps that day:");
        CSVParser coldParser2 = smallestFileR.getCSVParser();
        for (CSVRecord record : coldParser2) {
            System.out.println(record.get("DateUTC") + " " + record.get("TemperatureF"));
        }
    }
    
    public void testFileWithLowestHumidity() {
        File smallestFile = fileWithLowest("Humidity");
        System.out.println("Lowest Humidity day was in: " + smallestFile.getName());
        
        FileResource smallestFileR = new FileResource(smallestFile);
        CSVParser coldParser = smallestFileR.getCSVParser();
        CSVRecord coldest = coldestHourInFile(coldParser, "Humidity");
        System.out.println("Lowest Humidity during that day was " + coldest.get("Humidity") + " at " + coldest.get("DateUTC"));
        
        System.out.println("All Humidity that day:");
        CSVParser coldParser2 = smallestFileR.getCSVParser();
        for (CSVRecord record : coldParser2) {
            System.out.println(record.get("DateUTC") + " " + record.get("Humidity"));
        }
    }
    
    public double giveDouble(String str) {
        return Double.parseDouble(str);
    }
    
    public double averageTemperatureInFile(CSVParser parser) {
        int records = 0;
        double totalTemp = 0.0;
        
        for (CSVRecord record : parser) {
            totalTemp = totalTemp + giveDouble(record.get("TemperatureF"));
            records++;
        }
        
        return (totalTemp / records);
    }
    
    public void testAvg() {
        FileResource fr = new FileResource();
        CSVParser thisParser = fr.getCSVParser();
        
        double avg = averageTemperatureInFile(thisParser);
        System.out.println("Average Temp: " + avg);
    }
    
    public double averageTemperatureWithHighHumidityInFile (CSVParser parser, int threshold) {
        int records = 0;
        double totalTemp = 0.0;
        
        for (CSVRecord record : parser) {
            if (giveDouble(record.get("Humidity")) >= threshold) {
                totalTemp = totalTemp + giveDouble(record.get("TemperatureF"));
                records++;
            }
        }
        
        return (totalTemp / records);
    }
    
    public void testAvgHumid() {
        FileResource fr = new FileResource();
        CSVParser thisParser = fr.getCSVParser();
        int thresh = 80;
        
        double avg = averageTemperatureWithHighHumidityInFile(thisParser, thresh);
        System.out.println("Average Temp of high humidity hours: " + avg);
    }
}

