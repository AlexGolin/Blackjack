import java.util.ArrayList;

/**
 * The blackjack player that the user interacts with the dealer and the game in
 * @author Alex
 * @date 1/11/2015
 */
public class Player {

	/**
	 * The Player's name
	 */
	private String name;

	/**
	 * The player's hand is an array of cards.
	 * The player should never have more than 10 cards 
	 */
	private Card[] hand = new Card[7];

	/**
	 * Number of cards in the player's hand
	 */
	private int numCards;

	/**
	 * The player has $100 dollars at the start of the game
	 */
	public static int playerMoney = 500;// The player starts with 500 dollars

	public int betAmount;

	/** The player constructor
	 * 
	 * @param aName the name that the player will have to identify them
	 */
	public Player(String aName)
	{
		name = aName;

		//When a new player is created, they must have a new hand that is clear

		this.emptyHand();
	}	

	/**
	 * Clear the player's hand so they have no cards
	 */
	public void emptyHand()
	{
		for(int i=0;i<7;i++)
		{
			this.hand[i] = null;
		}
		this.numCards=0;
	}

	/**
	 * Add a card from the top of the deck to the player's hand
	 * @param aCard the card from the top of the deck
	 * @return  boolean determining weather the sum of the new hand is below or equal to 21
	 */
	public boolean addCard(Card aCard)
	{
		if(this.numCards == 10)
		{
			System.err.println("This hand allready has the max of 10 cards, cannot add more");
			return false; //This should stop the code from continuing further and adding the card
			//System.exit(1); 
		}

		this.hand[this.numCards] = aCard;
		this.numCards++;

		if(this.getHandSum() <= 21 == true)
		{
			return true;
		}
		else return false;
	}


	/**Get the sum of all of the cards in the hand
	 * 
	 * @return the sum of all the values of each of the cards
	 */
	public int getHandSum()
	{
		int sum = 0;
		int numAces = 0;

		for(int i = 0 ; i <this.numCards ; i ++)//*
		{
			if(hand[i].getValue()==1)//Ace
			{
				numAces++;
				sum+=11;

			}
			else
			{
				sum += hand[i].getValue(); 
			}
		}
		//if we have aces and the sum is over 21, then we can set some or all of them to 1

		while(sum > 21 && numAces > 0 )
		{
			sum -= 10;
			numAces--;
		}
		return sum;
	}


	/**Print out the cards in the player's hand 
	 * 
	 * @param showFirstCard wether the first card is hidden or not (Useful for the dealer to 
	 * hide their cards)
	 */
	public void printHand(boolean showFirstCard)
	{
		System.out.println("Player " + this.name + " cards:");

		for(int i = 0 ; i < this.numCards ; i++)
		{
			if( i ==0 && showFirstCard == false)
			{
				System.out.println("[Hidden]");
			}
			else
			{
				System.out.println(hand[i].printCard());
			}
		}
	}

	/**
	 * Method that checks if the player can make the bet, and then temporarily keeps the bet number
	 * -If they lose their bet, then the money is not returned, so thus they lose their money to the bet
	 * 
	 * @param num The bet amount that the user has given
	 */
	public void placeBet(int num)
	{
		if(num> playerMoney) //Error check to make sure that the 
		{
			System.err.println("This wager is too high");
			return;
		}
		else
		{
			this.betAmount = num;
		}
		playerMoney-=betAmount; // Player loses TEMPORARILY the amount they wagered
	}
	/**
	 * A method that adds the money that the player won if they win the bet 
	 * The player lost  the money when they bet, so there is a double so they get the money that they put out back, and
	 * they receive their winnings
	 */
	public void wonBet()
	{
		playerMoney +=  2*(betAmount);
	}

	/**
	 * Display the amount of player money
	 */
	public void printMoney()
	{
		System.out.println("The player has $" + this.playerMoney);
	}

	public int getMoney(){return playerMoney;}
	public Card[] getHand(){return hand;}
	public String getName(){return name;}




}
