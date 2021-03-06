/*
 * TransactionTest.java
 * 
 * Date: 03/20/2020
 * 
 * @author Andy Nguyen
 * 
 * Description: State Street Bank transaction ID generator
 * This Java program is used to create random 24-digit transaction IDs for customer 
 * accounts in a database read from a csv file. Imports FileReader, LineNumberReader, 
 * and Scanner classes from the Java library used for reading and parsing csv file 
 * within Customer class, and user input for TransactionTest class.
 * 
 * Notes:
 * 03/20/2020 - Initial Development.
 * 03/21/2020 - Added oneTransID method for account ID query.
 * 03/24/2020 - Split original TransactionID class into Customer and TransactionID classes.
 * 04/07/2020 - Edited comments for better program description.
 * 04/19/2020 - Renamed readRecord() method to readCSV(). Edited comments.
 * 04/20/2020 - Removed "accountID = accountID.toLowerCase();" line to fix accountID
 *              matching bug.
 */
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.LineNumberReader;
import java.util.Scanner;

/*
 * A Java test class to demonstrate methods of a Customer class object.
 */
public class TransactionTest
{
    public static void main(String[] args)
    {
        //Initialize Customer class object c1.
        final Customer c1 = new Customer();

        //Initialize Scanner class object for command-line user input.
        Scanner input = new Scanner(System.in);
        System.out.print("Enter 1 for transaction IDs to all customers.\n" +
        "Enter 2 for one transaction ID to a specific account ID.\n");
        int option = input.nextInt();
        input.skip("\n");

        /*
         * Print transaction IDs without customer information from 2D array obtained.
         * by calling allTransIDs method of Customer class.
         */
        if(option == 1)
        {
            long startTime = System.currentTimeMillis();//Calculate runtime.

            String[][] contents = c1.allTransIDs();
            for(int i = 0; i < contents.length; i++)
            {
                /*Print transaction IDs for customers.csv file. Index number is
                preconditioned to +1 to prevent off-by-one error.*/
                System.out.printf("%-3d %s%n", i + 1, contents[i][9]);
            }
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println(totalTime + "ms");//Display runtime in milliseconds.
        }
        /*
         * Print one transaction ID for queried account ID by calling oneTransID
         * method of Customer class.
         */
        else if(option == 2)
        {
            int attempt = 0;
            String transID;
            do{	
                System.out.print("Please enter account ID: ");
                String accountID = input.nextLine();

                //Call oneTransID method of Customer class to obtain single transaction ID.
                transID = c1.oneTransID(accountID);
            
                if(transID.equals("Invalid."))//When incorrect account ID is typed.
	            {
                    System.out.println("Invalid account ID.");
                    attempt++;
                }
                if(attempt >= 3)//After three attempts, program shuts down.
                {
                    System.out.println("You made three attempts. Program exiting.");
                    System.exit(0);
                }
            }while(transID.equals("Invalid."));
        
            System.out.println(transID);
            input.close();//Close Scanner class object.
        }
        else
        {
            System.out.println("Invalid option. Program exiting...");
            System.exit(0);
        }
    }//End of main method.
}//End of TransactionTest class.

/*
 * Class used to generate a randomized 24-digit alphanumeric transaction ID by using
 * a char array and store the ID as a String variable for Customer class.
 */
class TransactionID
{
    //Initialize char array of 62 elements.
    final private char[] digits = new char[62];

    //A no argument constructor.
    public TransactionID()
    {
        //Three for loops to populate digits array with alphanumeric characters.
        int index = 0;
        for(char a = '0'; a <= '9'; a++)
        {
            this.digits[index++] = a;
        }
        for(char b = 'A'; b <= 'Z'; b++)
        {
            this.digits[index++] = b;
        }
        for(char c = 'a'; c <= 'z'; c++)
        {
            this.digits[index++] = c;
        }
    }

    //Accessor method used to obtain contents of digits array.
    protected char[] getArray()
    {
        return this.digits;
    }//End of getArray method.

    //Accessor method returns a random digit generated by random() method of Math class.
    private char randomCharDigit()
    {
        return digits[(int)Math.floor(Math.random() * digits.length)];
    }//End of randomCharDigit method.

    //Accessor method to assign a String variable for 24-digit transaction ID.
    protected String getTransID()
    {
        //Initialize StringBuilder class object.
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 24; i++)//For loop to add 24 char values to String.
        {
            result.append(randomCharDigit());
        }
        return result.toString();//Returns toString method of StringBuilder class object.
    }//End of getTransID method.
}//End of TransactionID class.

/*
 * Class used to read customers.csv file and assign transaction IDs to customers
 * listed in the database to be stored in a 2D array. Includes a method to search
 * a specified account ID in the csv file and assign a transaction ID.
 */
class Customer
{
    //Initialize Scanner class object to read csv file.
    private Scanner read;

    //Initialize LineNumberReader class object to count lines in csv file.
    private LineNumberReader lnr;

    //CSV file to read.
    final private String filePath = "customers.csv";

    /* 
     * Initialize TransactionID class object to generate transaction IDs for
     * methods of Customer class.
     */
    final private TransactionID t1 = new TransactionID();

    /* 
     * A default constructor for Customer class will be provided by the Java compiler
     * if no constructor is programmed into the class.
     */
    public Customer()
    {
        
    }

    //Accessor method to count lines in a csv file and assign customer 2D array row size.
    private int getLineNumber()
    {
        int lines = 0;
        try{ 		
            lnr = new LineNumberReader(new FileReader(filePath));
            
            lnr.skip(Long.MAX_VALUE);//Instead of using while loop, skip to the last line.
            lines = lnr.getLineNumber() + 1;//+1 because line index starts at 0 
            lnr.close();//Close LineNumberReader class object.                   		     
        }
        catch(Exception e)
        {
            System.err.println("File opening failed.");
    		e.printStackTrace();
        }
        return lines;//Includes header line to prevent off-by-one error.
    }//End of getLineNumber method.

    /*
     * Method to open customers.csv, read and parse the records of the csv file, and
     * store the tokens in a 2D String array.
     */
    private String[][] readCSV()
    {
        int index = 0;//Counter variable for number of customers.

        /*
         * 2D String array to store transaction IDs with size determined by number of
         * customers in the csv file.
         */
        final String[][] customer = new String[getLineNumber()][10];

        try {
            read = new Scanner(new FileReader(filePath));//Opens customers.csv file
            read.useDelimiter("[,\n]");//Delimiter to parse String tokens.
            read.nextLine();//Skip header of csv file.

            while(read.hasNext())//while loop to read lines of customer records.
            {             
                index++;
                                          
                for(int token = 0; token < 9; token++)//For loop of customer attributes.
                {
                    customer[index][token] = read.next();
                    /*token 0 = Account ID.
                      token 1 = Company.
                      token 2 = Firstname.
                      token 3 = Lastname.
                      token 4 = Address 1.
                      token 5 = Address 2.
                      token 6 = City.
                      token 7 = State.
                      token 8 = ZIP Code.*/
                }
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            read.close();//Close Scanner class object.
        }
        //Returns 2D String array containing customer records.
        return customer;
    }//End of readCSV method.

    /*
     * The purpose of this method is to get customer database from readCSV method,
     * store the transaction IDs into 10th column of 2D String array, call duplicateCheck
     * method to check for duplicate transaction IDs in the array, and return a
     * 2D String array containing customer records with transaction IDs.
     */
    protected String[][] allTransIDs()
    {
        String[][] account = readCSV();
        for(int index = 1; index < account.length; index++)
        {
            account[index][9] = t1.getTransID();
        }
        account = duplicateCheck(account);

        return account;
    }//End of allTransIDs method.

    /*
     * The purpose of this method is to get customer database from readCSV method,
     * check for matching account ID from query, and return a single transaction ID
     * to the queried customer account if there is a match.
     */
    protected String oneTransID(String accountID)
    {
        String transID = "";
        String[][] account = readCSV();
        for(int index = 1; index < account.length; index++)
        {
            if(account[index][0].equals(accountID))
            {             
                transID = t1.getTransID();//Obtains transaction ID and ends loop.
                break;
            }
            else//If queried account ID does not match to any account ID in the database.
            {
                transID = "Invalid.";
            }
        }
        return transID;
    }//End of oneTransID method.

    //Method to check and replace duplicate transaction IDs from allTransIDs method.
    private String[][] duplicateCheck(String[][] customer)
    {
        /*Nested-loop algorithm to compare one assigned transaction ID from a customer
        to all transactionIDs in the database at each cycle.*/
        for(int i = 1; i < customer.length; i++)//i set to 1 to skip null header.
        {
            for(int j = i + 1 ; j < customer[i].length; j++)
            {
                //Conditional if statement when a transactionID is identical to another.
                if(customer[i][9].equals(customer[j][9]))
                {
                    //Reassign transaction ID if duplicate is found.
                    System.out.println("Duplicate transaction ID detected. Replacing...");
                    customer[i][9] = t1.getTransID();
                }
            }
        }
        return customer;
    }//End of duplicateCheck method.
}//End of Customer class.