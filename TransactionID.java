/*State Street Bank transaction ID generator programmed by Group 2 of CSCI 362 
This Java class is to be used by a separate test class to create a random 
24-digit transaction ID for a customer account.*/
import java.io.File;
import java.io.FileReader;//Class used with LineNumberReader.
import java.io.FileNotFoundException;
import java.io.LineNumberReader;
import java.util.Scanner;//Scanner class used for reading csv file.

public class TransactionID
{
    //Initialize char array of 62 elements.
    final private char[] digits = new char[62];

    //Initialize Scanner class object to read csv file.
    private Scanner read;

    //Initialize LineNumberReader class object to count lines in csv file.
    private LineNumberReader lnr;

    //CSV file to read.
    final private String filePath;// = "customers.csv";

    //Nine tokens to collect from csv file. Instance variables(fields).
    private String account = "";
    private String company = "";
    private String firstName = "";
    private String lastName = "";
    private String address1 = "";
    private String address2 = "";
    private String city = "";
    private String state = "";
    private String zipCode = "";

    //A one argument constructor.
    public TransactionID(String filePath)
    {
        this.filePath = filePath;

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
    public char[] getArray()
    {
        return this.digits;
    }

    //Accessor method returns a random digit generated by random() method of Math class.
    public char randomCharDigit()
    {
        return digits[(int)Math.floor(Math.random() * digits.length)];
    }

    //Accessor method to assign a String variable for 24-digit transaction ID.
    public String getTransactionID()
    {
        //Initialize StringBuilder class object.
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 24; i++)//For loop to add 24 char values to String.
        {
            result.append(randomCharDigit());
        }
        return result.toString();//Returns toString method of StringBuilder class object.
    }

    //Accessor method to count lines in csv file and assign transID array element size.
    public int getLineNumber()//Method overloading with LineNumberReader class.
    {
        int lines = 0;
        try{ 		
            lnr = new LineNumberReader(new FileReader(filePath));
            
            lnr.skip(Long.MAX_VALUE);//Instead of using while loop, skip to last line.
            lines = lnr.getLineNumber() + 1;//+1 because line index starts at 0 
            lnr.close();//Close LineNumberReader class object.                   		     
        }
        catch(Exception e)
        {
    		e.printStackTrace();
        }
        return lines;//Includes header line to prevent off-by-one error.
    }//End of getLineNumber method

    //Method to assign all customer records a transaction ID by reading csv file.
    //public void readRecord()
    //public void readRecord(String filePath)
    public String[] readRecord()
    {
        int index = 0;//Count number of customers.

        //String array to store transaction ID's with size determined by number of customers.
        final String[] transID = new String[getLineNumber()];

        try {
            read = new Scanner(new File(filePath));//Opens customers.csv file
            read.useDelimiter("[,\n]");//Delimiter to parse String tokens.
            read.nextLine();//Skip header of csv file.

            while(read.hasNext())//while loop to search customer record.
            {
                index++;
         
                /*For loop that does not store tokens to String variables for a
                zero-knowledge customer account program.*/
                /*for(int token = 0; token < 9; token++)
                {
                    read.next();
                }*/
                
                account = read.next();
                company = read.next();
                firstName = read.next();
                lastName = read.next();
                address1 = read.next();
                address2 = read.next();
                city = read.next();
                state = read.next();
                zipCode = read.next();

                //Stores transaction ID's into String array.
                transID[index] = getTransactionID();

                //Stores one transaction ID per loop.
                //String transID = getTransactionID();

                //Prints transaction ID's with Account information.
                /*System.out.printf("%-3d %-24s %-35s %-15s %-15s %s%n",
                 index, account, company, firstName, lastName, transID);*/

                //Prints transaction ID's without the stored String variables.
                //System.out.printf("%-3d %s%n", index, transID);
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
        //Returns String array containing transaction ID's.
        return transID;
    }//End of readRecord method.
}//End of TransacationID class.