/*Programmed by Group 2 of CSCI 362
Bank application that generates a random transaction ID for an account ID entered by
the user. Calls TransactionID class object to recieve random transaction ID.*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class StateStreetBank
{
    private static Scanner read;//Scanner class object to read and parse csv file.
    private static Scanner input;//Scanner class object for command-line input.

    public static void main(String[] args)
    {   
        input = new Scanner(System.in);//Input for command-line interface.
        //System.out.println("Please enter filename of database: ");
        //String filePath = input.nextLine();
        //System.out.println("Please enter account ID: ");
        //String searchTerm = input.nextLine();

        //GUI version of input.
        String filePath = JOptionPane.showInputDialog(null, 
        "Please enter filename of database: ", "State Street Bank", 
        JOptionPane.INFORMATION_MESSAGE);

        String searchTerm = JOptionPane.showInputDialog(null, 
        "Please enter account ID: ", "State Street Bank", 
        JOptionPane.INFORMATION_MESSAGE);
        searchTerm = searchTerm.toLowerCase();

        //Call method to obtain transaction ID.
        String transID = getRecord(searchTerm, filePath);
        JOptionPane.showMessageDialog(null,"Transaction ID: " + transID);

        //System.out.println(transID);
    }

    //Method to read and search records in a csv file using Scanner class object.
    public static String getRecord(String searchTerm, String filePath)
    {
        //Initialize TransactionID class object.
        TransactionID t1 = new TransactionID(filePath);

        boolean found = false;//Boolean value to determine correct account ID.
        String transID = null;

        //Nine attributes of a customer record.
        String account = "";
        String company = "";
        String firstName = "";
        String lastName = "";
        String address1 = "";
        String address2 = "";
        String city = "";
        String state = "";
        String zipCode = "";

        try {
            read = new Scanner(new File(filePath));//Opens customer.csv file.
            read.useDelimiter("[,\n]");//Delimiter to parse String tokens.

            while(read.hasNext() && !found)//while loop to search customer records.
            {
                account = read.next();
                company = read.next();
                firstName = read.next();
                lastName = read.next();
                address1 = read.next();
                address2 = read.next();
                city = read.next();
                state = read.next();
                zipCode = read.next();

                if(account.equals(searchTerm))
                {
                    found = true;
                }
            }
            //Conditional statement where valid account ID recieves transaction ID.
            if(found)
            {
                //Calls method from TransactionID class to obtain transaction ID.
                transID = t1.getTransactionID();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Invalid Account ID.",
                 "State Street Bank", JOptionPane.ERROR_MESSAGE);
            }  
        } 
        catch (Exception e) {
            //Displays if csv file is not found or typed correctly.
            JOptionPane.showMessageDialog(null, "File not found.", "State Street Bank",
            JOptionPane.ERROR_MESSAGE);
            //System.out.println("File not found.");
        }
        return transID;//Send transaction ID to main method.
    }//End of getRecord method.
}//End of StateStreetBank class.