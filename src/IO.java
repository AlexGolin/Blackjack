
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
/**
 * IO class used to manipulate the text from a text file containing all of the information of the decks in use.
 * @author Alex
 * @since 1/08/2015
 */
public class IO
{
	private static PrintWriter fileOut;
	private static BufferedReader fileIn;

	public static void createOutputFile(String fileName)//data//Deck.txt" into here
	{
		try
		{
			fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		}
		catch (IOException e)
		{
			System.out.println("*** Cannot create file: " + fileName + " ***");
		}
	}

	public static void openOutputFile(String fileName)
	{
		try
		{
			fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
		}
		catch (IOException e)
		{
			System.out.println("*** Cannot open file: " + fileName + " ***");
		}
	}

	public static void print(String text)
	{
		fileOut.print(text);
	}

	public static void println(String text)
	{
		fileOut.println(text);
	}

	public static void closeOutputFile()
	{
		fileOut.close();
	}

	public static void openInputFile(String fileName)
	{
		try
		{
			fileIn = new BufferedReader(new FileReader(fileName));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("***Cannot open " + fileName + "***");
		}
	}

	public static String readLine()
			throws IOException
	{
		return fileIn.readLine();
	}

	public static void closeInputFile()
			throws IOException
	{
		fileIn.close();
	}
}

