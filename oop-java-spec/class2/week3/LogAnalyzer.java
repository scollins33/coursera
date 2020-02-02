
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         FileResource file = new FileResource(filename);
         //WebLogParser parser = new WebLogParser();
         
         for (String line : file.lines()) {
             LogEntry thisEntry = WebLogParser.parseEntry(line);
             records.add(thisEntry);
         }
     }
     
     public int countUniqueIPs() {
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         
         for (LogEntry le : records) {
             String ip = le.getIpAddress();
             
             if (!uniqueIPs.contains(ip)) {
                 uniqueIPs.add(ip);
             }
         }
         
         return uniqueIPs.size();
     }
     
     public int countUniqueIPsHash() {
         HashMap<String,Integer> counts = countVisitsPerIP();
         return counts.size();
     }
     
     public int countUniqueIPsInRange(int low, int high) {
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         
         for (LogEntry le : records) {
             String ip = le.getIpAddress();
             
             if (!uniqueIPs.contains(ip)) {
                if (le.getStatusCode() >= low && le.getStatusCode() <= high) {
                    uniqueIPs.add(ip);
                }
             }
         }
         
         return uniqueIPs.size();
     }
     
     public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         
         for (LogEntry le : records) {
             String date = le.getAccessTime().toString();
             String ip = le.getIpAddress();
             
             if (!uniqueIPs.contains(ip)) {
                //System.out.println(date);
                //System.out.println(someday);
                //System.out.println(date.indexOf(someday));
                if (date.indexOf(someday) > -1) {
                    uniqueIPs.add(ip);
                }
             }
         }
         System.out.println(uniqueIPs.size());
         return uniqueIPs;
     }
     
     public HashMap<String,Integer> countVisitsPerIP() {
         HashMap<String,Integer> ipCounts = new HashMap<String,Integer>();
         
         for (LogEntry le : records) {
             String ip = le.getIpAddress();
             
             if (ipCounts.containsKey(ip)) {
                 int counter = ipCounts.get(ip);
                 ipCounts.put(ip, counter + 1);
             }   
             else {
                 ipCounts.put(ip, 1);
             }
         }
         
         return ipCounts;
     }
     
     public void printAllHigherThan(int num) {
         for (LogEntry le : records) {
             if (le.getStatusCode() > num) {
                 System.out.println(le);
             }
         }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public int mostNumberVisitsByIP(HashMap<String, Integer> ipCounts) {
         int highest = 0;
         
         for (Integer count : ipCounts.values()) {
             if (count > highest) {
                 highest = count;
                }
            }
            
            return highest;
        }
        
     public ArrayList<String> iPsMostVisits(HashMap<String, Integer> ipCounts) {
         ArrayList<String> ips = new ArrayList<String>();
         
         int highest = mostNumberVisitsByIP(ipCounts);
         
         for (String ip : ipCounts.keySet()) {
             if (ipCounts.get(ip) == highest) {
                 ips.add(ip);
             }
         }
         
         return ips;
     }
     
     public HashMap<String, HashSet<String>> iPsForDays() {
         HashMap<String, HashSet<String>> mapped = new HashMap<String,HashSet<String>>();
         
         for (LogEntry le : records) {
            String date = le.getAccessTime().toString();
            String day = getDay(date);
            String ip = le.getIpAddress();
                         
            if (mapped.containsKey(day)) {
                HashSet<String> thisHash = mapped.get(day);
                thisHash.add(ip);
                mapped.put(day, thisHash);
            }
            else {
                HashSet<String> newHash = new HashSet<String>();
                newHash.add(ip);
                mapped.put(day, newHash);
            }
         }
         
         return mapped;
     }
     
     public HashMap<String, ArrayList<String>> iPsForDaysRepeated() {
         HashMap<String, ArrayList<String>> mapped = new HashMap<String,ArrayList<String>>();
         
         for (LogEntry le : records) {
            String date = le.getAccessTime().toString();
            String day = getDay(date);
            String ip = le.getIpAddress();
                         
            if (mapped.containsKey(day)) {
                ArrayList<String> thisHash = mapped.get(day);
                thisHash.add(ip);
                mapped.put(day, thisHash);
            }
            else {
                ArrayList<String> newHash = new ArrayList<String>();
                newHash.add(ip);
                mapped.put(day, newHash);
            }
         }
         
         return mapped;
     }
     
     public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> days) {
        String biggest = "";
        int highest = 0;
        
        for (String day : days.keySet()) {
            int size = days.get(day).size();
            
            if (size > highest) {
                highest = size;
                biggest = day;
            }
        }
        
        return biggest;
     }
     
     private String getDay(String date){
        String day = date.substring(0,10);
        return day;
     }
}
