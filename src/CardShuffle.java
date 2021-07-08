import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
/**
 * Method that directly uses the IO class to manipulate the text to create from raw text, Cards which can be used for 
 * the game. The class creates decks to use, and shuffle methods to use.
 * @author Alex
 *@since 1/15/2015
 */
public class CardShuffle 
{

	private static ArrayList<Card> blackDeck = new ArrayList();//The public may make no difference
	/**
	 * @param blackDeck An array list containing all of the cards is used because the size can change if
	 * the user adds more cards. 
	 * Note: AS THE DECK HAS CARDS DELT FROM IT, THE OVERALL SIZE GOES DOWN
	 * NOTE: Top card is the first index
	 */

	private static ArrayList<Card> dealtDeck = new ArrayList(); //Thre cards that have been dealt and are removed from blackdeck

	/**
	 * Constructor sets the number of cards in the deck (Sets of 52)
	 * - Currently there are 3 sets of 52 cards
	 * - The boolean is to determine if the deck is shuffled or not
	 * @param numDeck the number of sets of 52 cards
	 */
	public CardShuffle()


	{
		IO.openInputFile("data//Deck.txt"); //open IO with the Deck

		//The goal for the constructor is to be able to replicate the deck to 
		//give the user the option to play with 3 or 6 or 9 decks
		//IO.createOutputFile("data//Deck.txt");
		//IO.
	}

	public static void fillArray() throws IOException // I do not know why this is package
	/**
	 * Fills the deck using the IO to interact with the Deck data file
	 * which contains all of the cards in the decks
	 * @param line uses IO.readLine() which is an IO method which takes a line from the 
	 * text file. Each time it is called, it keeps moving down t
	 */
	{
		String line = IO.readLine();
		while(line!= null)
		{
			System.out.println(line);//Error Check
			Card temp = getCard(line);
			if(temp!=null)
			{
				blackDeck.add(temp);
			}
			line = IO.readLine(); 
		}
	}

	private static Card getCard(String text) //Will receive ---> Clubs%King%location
	{
		/**
		 * Used to generate a card object with the card class
		 * @param suit  The suit of the card is used only for display
		 * @param rank  The rank of the card is used only for display
		 * @param value The value is the integer used for adding to 21 
		 */
		String suit;
		String rank;
		int value = 0;
		String location;

		text.trim();
		if(text.length()<1)
		{
			return null;
		}

		suit= text.substring(0,text.indexOf('%'));
		suit = suit.replace("%","");
		text=text.replace(text.substring(0,text.indexOf('%')+1),"");
		rank = text.substring(0,text.indexOf('%'));
		location = text.substring(text.indexOf('%')+1, text.length());
		System.out.println(suit);//Error Check
		System.out.println(rank);//Error Check
		System.out.println(location);//Error Check

		if(rank.length()==1||rank.length()==2)
		{
			if(Integer.parseInt(rank)>=2||Integer.parseInt(rank)<=10)
			{
				value = Integer.parseInt(rank);
			}
			else System.err.println("Card value error");
		}
		if(rank.equals("King")||rank.equals("Queen")||rank.equals("Jack"))
		{
			value = 10; 
		}
		/*
		 * All aces are treated as a 1 value until the total hand sum is calculated
		 */
		if(rank.equals("Ace")) 
		{
			value= 1;	
		}

		System.out.println(value);
		Card newCard = new Card(suit,rank,value,location);
		return newCard;
	}


	public static void PrintArray()
	/**
	 * Print the deck one card by one card
	 */
	{
		for(int i = 0; i <blackDeck.size(); i ++)
		{
			System.out.println(blackDeck.get(i).getValue() + " is a " + blackDeck.get(i).getRank() + " of " + blackDeck.get(i).getSuit()+ " that can be found in "+ blackDeck.get(i).getLocation());
		}
	}

	public static void shuffle()
	/**
	 * This method shuffles the deck using substitution of the current values of 
	 * one selected card in the array list, and swapping it with the values of a 
	 * randomly selected card 
	 */
	{

		Random rng = new Random();

		Card temp;
		int j;
		for(int i = 0; i < blackDeck.size();i++)
		{
			j = rng.nextInt(blackDeck.size());

			//do swap
			temp = blackDeck.get(i);
			blackDeck.set(i,blackDeck.get(j));
			blackDeck.set(j,temp);

		} 
	}

	/**
	 * When a card is dealt, it is the  first card on the top of the deck that is used
	 *  @param top The top of the deck is the card in the array with an index of 0
	 */
	public Card dealNextCard()
	{
		Card top = blackDeck.get(0);
		/**
		 * When a card is removed, the array list automatically moves all of the objects
		 * behind the first card back to the spot, so there is no need to shift all of the
		 *  cards by an index
		 */
		dealtDeck.add(blackDeck.get(0));
		blackDeck.remove(0);
		return top;
	}
	public ArrayList<Card> getBlackDeck(){return blackDeck;}
	public ArrayList<Card> getDealtDeck(){return dealtDeck;}

	/**
	 * Refill the deck by replacing the empty blackdeck with what in the dealtdeck
	 */
	void refillDeck()
	{
		for(int i = 0 ; i<dealtDeck.size(); i ++)
		{
			Card temp = dealtDeck.get(i);
			blackDeck.add(temp);
			dealtDeck.remove(i);
		}
	}

}



