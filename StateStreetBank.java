import java.io.File;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class StateStreetBank
{
    private static Scanner read;
    private static Scanner input;

    public static void main(String[] args)
    {
        String filePath = "customers.csv";
        input = new Scanner(System.in);//Input for command-line interface.
        //String searchTerm = "4749464064613eb3feaadc";
        //88042877140228e4355aed

        //GUI version of input.
        String searchTerm = JOptionPane.showInputDialog(null, 
        "Please enter account ID: ", "State Street Bank", 
        JOptionPane.INFORMATION_MESSAGE);
        searchTerm = searchTerm.toLowerCase();

        readRecord(searchTerm, filePath);//Call method to obtain transaction ID.
    }

    public static void readRecord(String searchTerm, String filePath)
    {
        boolean found = false;//Boolean value to determine correct account ID.

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
            read = new Scanner(new File(filePath));//Opens customer.csv file
            read.useDelimiter("[,\n]");//Delimiter to parse String tokens.

            while(read.hasNext() && !found)//while loop to search customer record.
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

            if(found)
            {
                //String transID = transaction();
                String transID = transaction(filePath);
                /*JOptionPane.showMessageDialog(null,"Account: " + account + "\nCompany: " +
                company + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + 
                "\nAddress 1: " + address1 + "\nAddress 2: " + address2 + "\nCity: " +
                city + "\nState: " + state + "\nZIP Code: " + zipCode);*/
                JOptionPane.showMessageDialog(null,"Transaction ID: " + transID);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Invalid Account ID.");
            }  
        } 
        catch (Exception e) {

            System.out.println("Error");
        }
    }

    //public static String transaction()
    public static String transaction(String filePath)
    {
        TransactionID t1 = new TransactionID(filePath);//Declare TransactionID class object.
        String transID = t1.getTransactionID();//Assign transaction ID to String variable.
        return transID;
    }
}