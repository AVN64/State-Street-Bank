import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadCustomer
{
	//Delimiters used in the CSV file
	private static final String COMMA_DELIMITER = ",";
	
	public static void main(String args[])
	{		 
		Scanner read = null;
		try {
			//Get the scanner instance
			read = new Scanner(new File("customers.csv"));
			//Use Delimiter as COMMA
			read.useDelimiter(COMMA_DELIMITER);
			while(read.hasNext())
			{
					System.out.print(read.next()+"   ");
			}
		} 
		catch (FileNotFoundException exception) 
		{
			exception.printStackTrace();
		}
		finally
		{
			read.close();
		}
	}
}