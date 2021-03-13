/*
 * StateStreetBank.java
 *
 * Date: 03/20/20
 *
 * @author Andy Nguyen
 *
 * Description: GUI version of State Street Bank transaction ID generator test class programmed
 * by Group 2 of CSCI 362. This program displays transaction ID's generated from the
 * TransactionID class that reads the customers.csv file. Imports six classes used
 * to implement user input dialog windows and display list in a window with scrollbar.
 *
 * Notes:
 * 03/20/20 - Initial development.
 * 03/13/21 - Added user input for oneTransID method from Customer class.
 *
 */
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class StateStreetBank
{
   public static void main(String[] args)
   {
        //Initialize Customer class object c1.
        Customer c1 = new Customer();

        //Window title for GUI application.
        final String title = "State Street Bank";
        //Buttons for user input.
        final Object[] options = {"Generate All", "Enter Account ID", "Cancel"};
        
        //Display options for obtaining transaction IDs from customer database.
        int choice = JOptionPane.showOptionDialog(null,
        "Select option to obtain transaction IDs.", title,
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
        null, options, options[2]);

        //When user selects "Generate All" option.
        if(choice == JOptionPane.YES_OPTION)
        {
            /*Call allTransIDs method from Customer class to obtain randomly
            generated transaction ID's for all customers in the csv file.*/
            final String[][] customer = c1.allTransIDs();

            DefaultListModel<String> lModel = new DefaultListModel<>();
            JList<String> transactionList = new JList<>(lModel);
            transactionList.setVisibleRowCount(20);// Views 20 transaction ID's in a box.

            /*For loop to append transaction ID's to the toString method of
            Stringbuilder class object sb based on the row length of customer 2D array.*/
            for(int index = 1; index < customer.length; index++)
            {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%-3d %s%n", index, customer[index][9]));
               
                lModel.addElement(sb.toString());
            }

            //Initialize JScrollPane class object with transactionList used as an argument.
            JScrollPane scrollPane = new JScrollPane(transactionList);

            //Adding vertical scrollbar to transactionList.
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            //Setting dimensions of the viewable box of transactionList.
            JViewport viewport = scrollPane.getViewport();
            int w = 250;
            int h = viewport.getPreferredSize().height;
            Dimension preferredSize = new Dimension(w, h);
            viewport.setPreferredSize(preferredSize);

            //Window to display transactionList when StateStreetBank program is executed.
            JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        //When user selects the "Enter Account ID option."
        else if(choice == JOptionPane.NO_OPTION)
        {
            //Prompt user to enter a valid account ID.
            String account = JOptionPane.showInputDialog(null, "Enter Account ID.", title, 
            JOptionPane.QUESTION_MESSAGE);
            String transID = c1.oneTransID(account);

            /*Conditonal if statement based on what the oneTransID method from
            Customer class object returns.*/
            if(transID == "Invalid.")
            {
                JOptionPane.showMessageDialog(null, "Invalid Account ID. Program exiting.",
                 title, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            else
            {
                JOptionPane.showMessageDialog(null, transID, title, JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }              
        }
        //When user selects the "Cancel" option.
        else
        {
            System.exit(0);
        }

   }//End of main method.
}//End of StateStreetBank class.