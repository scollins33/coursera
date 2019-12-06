
/**
 * Write a description of debugging1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class debugging1 {
    public void findAbc(String input){
       int index = input.indexOf("abc");
       while (true){
           if (index == -1 || index >= input.length() - 3){
               break;
           }
           //logi(index);
           String found = input.substring(index+1, index+4);
           System.out.println(found);
           index = input.indexOf("abc",index+3);
           //logi(index);
       }
   }

   public void test(){
       //findAbc("abcd");
       findAbc("abcabcabcabca");
   }
   
   public void logs(String s) {
       System.out.println(s);
   }
   
   public void logi(int i) {
       System.out.println(i);
   }
}
