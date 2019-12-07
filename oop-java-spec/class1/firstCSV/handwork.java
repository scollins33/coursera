
/**
 * Write a description of handwork here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class handwork {
    public void listExporters(CSVParser parser, String exportsOfInterest) {
        for (CSVRecord record : parser) {
            String export = record.get("Exports");
            
            if (export.contains(exportsOfInterest)) {
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }
    
    public void whoExportsCoffee() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        
        listExporters(parser, "cocoa");
    }
    
    public void tester() {
        FileResource file = new FileResource();
        CSVParser parser = file.getCSVParser();
        
        String test1 = countryInfo(parser, "Nauru");
        System.out.println(test1);
        
        // have to make new parser since its been consumed
        CSVParser parser2 = file.getCSVParser(); 
        
        String exp1 = "fish";
        String exp2 = "nuts";
        listExportersTwoProducts(parser2, exp1, exp2);
        
        CSVParser parser3 = file.getCSVParser();
        System.out.println("Export count: " + numberOfExporters(parser3, "gold"));
        
        CSVParser parser4 = file.getCSVParser();
        bigExporters(parser4, "$999,999,999");
    }
    
    public void quiz() {
        FileResource file = new FileResource();
        // q1
        CSVParser parser = file.getCSVParser();
        String exp1 = "cotton";
        String exp2 = "flowers";
        listExportersTwoProducts(parser, exp1, exp2);
        
        //q3
        System.out.println("--------------- question 3 -------------");
        CSVParser parser4 = file.getCSVParser();
        bigExporters(parser4, "$999,999,999,999");
    }
    
    public String countryInfo(CSVParser parser, String country) {
        String finalProduct = "NOT FOUND";
        
        for (CSVRecord record : parser) {
            String thisCountry = record.get("Country");
            
            if (thisCountry.equals(country)) {
                finalProduct = thisCountry + " : " + record.get("Exports") + " : " + record.get("Value (dollars)");
            }
        }
        
        return finalProduct;
    }
    
    public void listExportersTwoProducts(CSVParser parser, String export1, String export2) {
        
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            
            if (exports.contains(export1) && exports.contains(export2)) {
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String export) {
        int count = 0;
        
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            
            if (exports.contains(export)) {
                count++;
            }
        }
        
        return count;
    }
    
    public void bigExporters(CSVParser parser, String dollars) {
        for (CSVRecord record : parser) {
            String dolladolla = record.get("Value (dollars)");
            
            if (dolladolla.length() > dollars.length()) {
                String country = record.get("Country");
                System.out.println(country + " " + dolladolla);
            }
        }
    }
}
