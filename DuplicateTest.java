public class DuplicateTest
{
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();

        TransactionID t1 = new TransactionID("customers.csv");//Declare TransactionID class object.
        final String[] transID = new String[200000];//Initialize String array.

        for(int i = 0; i < transID.length; i++)
        {
            transID[i] = t1.getTransactionID();
        }
        //Enhanced for loop to print contents of transID.
        for(String contents: transID)
        {
            System.out.println(contents);
        }
        
        //Ternary operator to determine duplicate elements.
        System.out.printf("%nThere are %s.%n",(DuplicateCheck(transID) == true ?
         "duplicates" : "no duplicates"));
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime + "ms");
    }//End of main method.

    //Method to check duplicate elements in transID array and return boolean value.
    public static boolean DuplicateCheck(String[] transID)
    {
        boolean duplicate = false;
        for(int i = 0; i < transID.length; i++)
        {
            for(int j = i + 1 ; j < transID.length; j++)
            {
                if(transID[i].equals(transID[j]))
                {
                    duplicate = true;// got the duplicate element
                }
            }
        }
        return duplicate;
    }
}//End of TransactionDemo class.