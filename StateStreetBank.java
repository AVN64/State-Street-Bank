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
        String filePath = null;
        try {
            input = new Scanner(System.in);//Input for command-line interface.
            //System.out.println("Please enter filename of database: ");
            //filePath = input.nextLine();

            //GUI version of input.
            filePath = JOptionPane.showInputDialog(null, 
            "Please enter filename of database: ", "State Street Bank", 
            JOptionPane.INFORMATION_MESSAGE);

            read = new Scanner(new File(filePath));
        } 
        catch (Exception e)
        {
            //Displays if csv file is not found or typed correctly.
            JOptionPane.showMessageDialog(null, "File not found.", "State Street Bank",
            JOptionPane.ERROR_MESSAGE);
            //System.out.println("File not found.");
            System.exit(0);
        }
        int attempt = 0;
        String transID;
        do{	
            //System.out.println("Please enter account ID: ");
            //String searchTerm = input.nextLine();

			String searchTerm = JOptionPane.showInputDialog(null, 
            "Please enter account ID: ", "State Street Bank", 
            JOptionPane.INFORMATION_MESSAGE);
            searchTerm = searchTerm.toLowerCase();

            //Call method to obtain transaction ID.
            transID = getRecord(searchTerm, filePath);
            
            if(transID.equals("Invalid."))//When incorrect account ID is typed.
	        {
	       	    JOptionPane.showMessageDialog(null, "Invalid account ID.", "Error", 
                JOptionPane.ERROR_MESSAGE);
                //System.out.println("Invalid account ID.");
                attempt++;
            }
            if(attempt >= 3)//After three attempts, program shuts down.
            {
                JOptionPane.showMessageDialog(null, "You made three attempts. Program exiting.",
                "Error", JOptionPane.ERROR_MESSAGE);
                //System.out.println("You made three attempts. Program exiting.");
                System.exit(0);
            }
		}while(transID.equals("Invalid."));
                
        JOptionPane.showMessageDialog(null,"Transaction ID: " + transID);
        //System.out.println(transID);

    }//End of main method.

    //Method to read and search records in a csv file using Scanner class object.
    public static String getRecord(String searchTerm, String filePath)
    {
        //Initialize TransactionID class object.
        TransactionID t1 = new TransactionID(filePath);

        //Calls method from TransactionID class to obtain String array of transaction ID's.
        String[][] customer = t1.readRecord();
        String transID = "";
        String account = "";

        for(int index = 1; index < customer.length; index++)
        {
            account = customer[index][0];//Reads account ID's from array.
            if(account.equals(searchTerm))//Checks for matching account ID from query.
            {             
                transID = customer[index][9];//Obtains transaction ID and ends loop.
                break;
            }
            else
            {
                transID = "Invalid.";
            }
        }
        return transID;
    }//End of getRecord method.
}//End of StateStreetBank class.