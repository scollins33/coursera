
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        System.out.println("----------------------");
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        System.out.println("----------------------");
        LogAnalyzer logger = new LogAnalyzer();
        logger.readFile("short-test_log");
        logger.printAll();
    }
    
    public void testIPs() {
        System.out.println("----------------------");
        LogAnalyzer logger = new LogAnalyzer();
        logger.readFile("weblog2_log");
        int visitors = logger.countUniqueIPs();
        
        System.out.println("Unqiue IPs: " + visitors);
    }
    
    public void testIPsRange() {
        System.out.println("----------------------");
        LogAnalyzer logger = new LogAnalyzer();
        logger.readFile("weblog2_log");
        int visitors = logger.countUniqueIPsInRange(200,299);
        System.out.println("Unqiue IPs: " + visitors);
    }
    
    public void testHigherThan() {
        System.out.println("----------------------");
        LogAnalyzer logger = new LogAnalyzer();
        logger.readFile("weblog2_log");
        logger.printAllHigherThan(400);
    }
    
    public void testUniqueIPVisitsOnDay() {
        System.out.println("----------------------");
        LogAnalyzer logger = new LogAnalyzer();
        logger.readFile("weblog2_log");
        
        ArrayList<String> ipsOver = new ArrayList<String>();
        ArrayList<String> ipsUnder = new ArrayList<String>();
        ipsOver = logger.uniqueIPVisitsOnDay("Sep 24");
        
        System.out.println("  Sep 24 ----------");
        for (String s : ipsOver) {
            System.out.println(s);
        }
        
    }
    
    public void testIPcounts() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        
        HashMap<String,Integer> counts = la.countVisitsPerIP();
        
        System.out.println(counts);
    }
    
    public void testHighestCount() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        
        HashMap<String,Integer> counts = la.countVisitsPerIP();
        int highest = la.mostNumberVisitsByIP(counts);
        
        System.out.println(highest);
    }
    
    public void ipMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        
        HashMap<String,Integer> counts = la.countVisitsPerIP();
        ArrayList<String> ips = la.iPsMostVisits(counts);
        
        System.out.println(ips);
    }
    
    public void iPsForDays() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        
        System.out.println("----------");
        HashMap<String, HashSet<String>> days = la.iPsForDays();        
        System.out.println(days);
        HashMap<String, ArrayList<String>> days2 = la.iPsForDaysRepeated();        
        System.out.println(days2);
        
        String biggest = la.dayWithMostIPVisits(days2);
        System.out.println(biggest);
    }
}
