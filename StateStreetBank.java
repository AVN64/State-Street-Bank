/*GUI version of State Street Bank transaction ID generator test class programmed
by Group 2 of CSCI 362. This program displays transaction ID's generated from 
TransactionID class that reads the customers.csv file. Imports six classes used
to display list in a window with scrollbar.*/
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
        //Initialize TransactionID class object t1.
        TransactionID t1 = new TransactionID();

        /*Call readRecord method from TransactionID class to obtain randomly
        generated transaction ID's for all customers in the csv file.*/
        final String[][] customer = t1.readRecord();

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
        JOptionPane.showMessageDialog(null, scrollPane, "State Street Bank", JOptionPane.INFORMATION_MESSAGE);
   }//End of main method.
}//End of StateStreetBank class.