/*State Street Bank transaction ID generator programmed by Group 2 of CSCI 362 
This Java program is used to create random 24-digit transaction ID's for customer 
accounts in a database read from a csv file.*/
import java.io.FileReader;//Class used with LineNumberReader and Scanner classes.
import java.io.FileNotFoundException;
import java.io.LineNumberReader;
import java.util.Scanner;/*Scanner class used for reading and parsing csv file within
Customer class, and user input for TransactionTest class.*/

public class TransactionTest
{
    public static void main(String[] args)//Enter csv filename before executing program.
    {
        //Initialize Customer class object c1.
        final Customer c1 = new Customer();

        //Initialize Scanner class object for command-line user input.
        Scanner input = new Scanner(System.in);
        System.out.print("Enter 1 for transaction ID's to all customers.\n" +
        "Enter 2 for one transaction ID for specific account ID.\n");
        int option = input.nextInt();

        //Print transaction ID's without customer information.
        if(option == 1)
        {
            String[][] contents = c1.method1();
            for(int i = 0; i < contents.length; i++)
            {
                /*Print transaction ID's for customers.csv file. Index number is
                preconditioned to +1 to prevent off-by-one error.*/
                System.out.printf("%-3d %s%n", i + 1, contents[i][9]);           
            }
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
        }
        else if(option == 2)
        {
            int attempt = 0;
            String transID;
            do{	
                System.out.print("Please enter account ID: ");
                String searchTerm = input.nextLine();

                searchTerm = searchTerm.toLowerCase();

                //Call Method2 to obtain single transaction ID.
                transID = c1.method2(searchTerm);
            
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
        }
        else
        {
            System.out.println("Invalid option. Program exiting...");
            System.exit(0);
        }
    }//End of main method.
}//End of TransactionTest class.

//Class used to generate a 24-digit alphanumeric TransactionID
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
    public String getTransID()
    {
        //Initialize StringBuilder class object.
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 24; i++)//For loop to add 24 char values to String.
        {
            result.append(randomCharDigit());
        }
        return result.toString();//Returns toString method of StringBuilder class object.
    }
}//End of TransactionID class.

class Customer
{
    //Initialize Scanner class object to read csv file.
    private Scanner read;

    //Initialize LineNumberReader class object to count lines in csv file.
    private LineNumberReader lnr;

    //CSV file to read.
    final private String filePath = "customers.csv";

    /*Initialize TransactionID class object to generate transaction ID's for
    methods of Customer class.*/
    final private TransactionID t1 = new TransactionID();

    //A constructor for Customer class.
    public Customer()
    {
        
    }

    //Accessor method to count lines in a csv file and assign customer array row size.
    public int getLineNumber()//Method overloading with LineNumberReader class.
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

    //Method to assign all customer records a transaction ID by reading csv file.
    public String[][] method1()
    {
        int index = 0;//Count number of customers.

        //2D String array to store transaction ID's with size determined by number of customers.
        String[][] customer = new String[getLineNumber()][10];

        try {
            read = new Scanner(new FileReader(filePath));//Opens customers.csv file
            read.useDelimiter("[,\n]");//Delimiter to parse String tokens.
            read.nextLine();//Skip header of csv file.

            while(read.hasNext())//while loop to search customer record.
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

                //Stores transaction ID's into 10th column of 2D String array.
                customer[index][9] = t1.getTransID();             
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

        //Call method to check for duplicate transaction ID's.
        customer = duplicateCheck(customer);
        //Returns 2D String array containing transaction ID's.
        return customer;
    }//End of Method1 method.

    public String[][] readRecord()
    {
        int index = 0;//Count number of customers.

        //2D String array to store transaction ID's with size determined by number of customers.
        String[][] customer = new String[getLineNumber()][9];

        try {
            read = new Scanner(new FileReader(filePath));//Opens customers.csv file
            read.useDelimiter("[,\n]");//Delimiter to parse String tokens.
            read.nextLine();//Skip header of csv file.

            while(read.hasNext())//while loop to search customer record.
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
        return customer;

    }//End of readRecord method.

    public String method2(String accountID)
    {
        String transID = "";
        String[][] account = readRecord();
        for(int index = 1; index < account.length; index++)
        {
            if(account[index][0].equals(accountID))//Checks for matching account ID from query.
            {             
                transID = t1.getTransID();//Obtains transaction ID and ends loop.
                break;
            }
            else
            {
                transID = "Invalid.";
            }
        }
        return transID;
    }//End of Method2 method.

    //Method to check and replace duplicate transaction ID's for Method1.
    public String[][] duplicateCheck(String[][] customer)
    {
        /*Nested-loop algorithm to compare one assigned transaction ID from a customer
        to all transactionID's in the database at each cycle.*/
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