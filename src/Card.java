/**
 * Card Object class that is used to store specific information like how the card
 *  looks and how it will interact in the game.
 * @author Alex
 *@since 1/20/2015
 */
public class Card extends Object {

	private String suit;//In blackjack the suit means nothing (Only for display)
	private String rank;

	private int value;
	//boolean aceValue;
	/**
	 * Cards 2-10 are valued at the face value of the card.
				Face cards such as the King, Queen, and Jack are valued at 10 each.
				The Ace card, however, is a special card and which be valued either at 11 or 1.
	 */

	/**
	 * The location is the card's location in eclipse when the program is being run
	 */
	private String location;

	public Card()
	{
		this(null,null,0,null);
	}

	public Card(String s, String r, int v, String loc)
	/*
	 * The Card constructor
	 */
	{

		if(v >= 1 && v <=13)
		{
			value = v; //The value is important for adding up to 21
		}
		else if(v==0)
			/**
			 * This if statement is not supposed to be here. It is only here because there 
			 * is an unknown 0 card value which is causing errors
			 */
		{
			System.err.println("The value is 0 and this is an error"); //There is a 0 value in the deck for some reason
			value = v;
		}
		else 
		{

			System.err.println(v + " is an error value"); //If there is an error in the value,the program catches it
			System.exit(1); // Of there is a bad value, the program immediately stops working
		}
		suit = s; //The suit of the card, not necessary for blackjack (Used for display)
		rank = r; //The rank of the card, (Used for display)
		location = loc; // The location of the card in the game
	}

	public String getSuit(){return suit;}
	public String getRank(){return rank;}
	public int getValue(){return value;}
	public String getLocation(){return location;}


	public String printCard()
	{
		return ( getValue() + " is a " +getRank() + " of " + getSuit() + " and is found in "+ getLocation());
	}


}



