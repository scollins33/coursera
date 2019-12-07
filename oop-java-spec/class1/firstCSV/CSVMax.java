
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
            if (largest == null) {
                largest = record;
            }
            else {
                double temp = Double.parseDouble(record.get("TemperatureF"));
                double largestDouble = Double.parseDouble(largest.get("TemperatureF"));
                
                if (temp > largestDouble) {
                    largest = record;
                }
            }
        }
        
        return largest;
    }
    
    public void testHottest() {
        FileResource file = new FileResource("nc_weather/2015/weather-2015-01-02.csv");
        CSVRecord largest = hottestInFile(file.getCSVParser());
        System.out.println("Hottest with " + largest.get("TemperatureF") + " at " + largest.get("TimeEST"));
    }
    
    public CSVRecord hottestInDir(CSVParser parser) {
        DirectoryResourse dir = new DirectoryResource();
        
        for (File file : dir.selectedFiles()) {
            FileResource file = new FileResource();
            
            
        }
    }
}

