/*State Street Bank transaction ID generator test class programmed by Group 2 of CSCI 362 
This test class is used to create TransactionID class object to call methods to
generate a 24-digit randomized transaction ID.*/
public class TransactionTest
{
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();//Runtime benchmark in milliseconds.

        //Declare TransactionID class object and input csv filename.
        TransactionID t1 = new TransactionID("customers.csv");
        
        //If this method is void.
        //t1.readRecord();
        
        //Enhanced for loop to collect contents of TransID array from TransactionID class.
        int index = 0;
        for(String contents: t1.readRecord())
        {
            index++;
            System.out.printf("%-3d %s%n", index, contents);
        }

        //Enhanced for loop to print contents of digits array from TransactionID class.
        /*for(char contents: t1.getArray())
        {
            System.out.print(contents);
        }
        System.out.println();*/

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime + "ms");
    }//End of main method.
}//End of TransactionTest class.
