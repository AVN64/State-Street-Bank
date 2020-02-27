/*State Street Bank transaction ID generator test class programmed by Group 2 of CSCI 362 
This test class is used to create TransactionID class object to call methods to
generate a 24-digit randomized transaction ID for a customer account.*/
public class TransactionTest
{
    public static void main(String[] args)//Enter csv filename before executing program.
    {
        long startTime = System.currentTimeMillis();//Runtime benchmark in milliseconds.

        //Declare TransactionID class object t1.
        TransactionID t1 = new TransactionID();

        //Enhanced for loop to collect contents of customer array from TransactionID class.
        int index = 0;
        /*for(String contents: t1.readRecord())//Loop for 1D Array.
        {
            index++;
            System.out.printf("%-3d %s%n", index, contents);
        }*/
 
        //Print transaction ID's and customer information.
        String divide = "-";
        /*for(String[] contents: t1.readRecord())
        {
            index++;
            System.out.printf("%s%nCustomer #%d%n", divide.repeat(30),index);
            for(String i: contents)
            {
                System.out.printf("%s%n", i);
            }
        }*/

        //Print transaction ID's without customer information.
        String[][] contents = t1.readRecord();
        for(int i = 0; i < contents.length; i++)
        {
            index++;//Includes header line to prevent off-by-one error.
            System.out.printf("%-3d %s%n", index, contents[i][9]);     
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