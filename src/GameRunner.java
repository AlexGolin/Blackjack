import java.io.IOException;

/**
 * The basic functions of the game, and the fundamental pieces which are used to separately create the elements needed for the game
 * such as players, decks and winning management. 
 * @author Alex
 *@since 1/21/2015
 */

public class GameRunner {

	static String name= "Obama";

	/**
	 * The user
	 */
	public static Player me= new Player(name); 

	/**
	 * The AI dealer
	 */
	public static Player dealer =  new Player("Dealer");

	/**
	 * Deck used in game
	 */
	public static CardShuffle playDeck = new CardShuffle();

	/**
	 * winAmount is the amount of money that the user has chosen to be the final goal to win the game
	 */
	public static int winAmount = 1000;

	/**
	 * Boolean to wait for the player to press deal
	 */
	public static boolean waitDeal= true;

	/** 
	 * Start the game by building the deck
	 * @throws IOException 
	 */
	public static void StartGame() throws IOException
	{
		CardShuffle.fillArray();
		CardShuffle.PrintArray(); //Print Deck before
		CardShuffle.shuffle();//Shuffle deck 
		CardShuffle.PrintArray();//Print Deck after shuffle	
	}

	/**
	 * Runs the main applications of the game. Used each time the Deal button in GameDisplay is clicked
	 * @throws IOException
	 */
	public static void RunGame() throws IOException
	{
		dealCards();


		System.out.println("Reached the while loop");//Error check

		/*
		 * The program waits for the user to re-click the wait (NOT SURE IF IT WORKS)
		 */
		while(waitDeal== false){
			try {
				Thread.sleep(200);
			} catch(InterruptedException e) {
			}
		}
		
		//Reset the wait each time
		waitDeal = false;

		/**
		 * Deal the cards using the deal cards method
		 */
		dealCards();

		//The first move
		System.out.println();
		System.out.println("The cards have been delt");
		me.printHand(true); //The player wants to see the first card
		dealer.printHand(true); //True for all cards visible, false for the first card hidden
		System.out.println();
	}	

	/**
	 * Method that deletes all of the cards in the player's hand, and then adds to cards to start the player off with a new round
	 * @param name The player 
	 * @param useDeck The Deck being used 
	 */
	public static void fillHands(Player name, CardShuffle useDeck)
	{
		name.emptyHand();

		name.addCard(useDeck.dealNextCard());
		name.addCard(useDeck.dealNextCard());
	}
	/**
	 * Used to deal a fresh new hand of cards to the players 
	 */
	public static void dealCards()
	{
		/*
		 * If the size of the deck  gets too low, refill the deck.
		 */
		if(playDeck.getBlackDeck().size()<=5)
		{
			playDeck.refillDeck();
		}
		//Shuffle the deck
		CardShuffle.shuffle();

		//Start the players off
		fillHands(me,playDeck);
		fillHands(dealer,playDeck);
	}
	
	/** Method for the gameDisplay class to use so that the player can bet.
	 * @param amount the given bet amount by the gameDisplay class based on what button they press. They can bet in amounts of 5, 10, 25, 50, 100, 500
	 * @return the money after the player makes the bet (TEMPORARILY LOST) 
	 */
	public static int bet(int amount)
	{
		me.placeBet(amount);
		return me.getMoney();//get the money

	}
	/**
	 * The player Hits, adding one Card
	 * @param person
	 * @return
	 */
	public static void Hit(Player person)
	{
		person.addCard(playDeck.dealNextCard());
	}

	/**
	 * Checks if the user won the overall BlackJack game by reaching target amount. 
	 * @return 1 if won, -1 if lost, 0 if the player can keep going
	 */
	public static int GameWin()
	{
		if(me.getMoney()>=winAmount)
		{
			return 1;//If the player won, return 1
		}
		if(me.getMoney()<=0)
		{
			return -1;//If the player lost, return -1
		}
		return 0;//Else return 0 for continue
	}

	/**
	 * Determines after the moves of the user and the dealer who won, and displays the appropriate message in GameDisplay.
	 * This method is static so it can be used outside of the class without creating another GameRunner object
	 * @return What message to display in GameDisplay. True for win, false for lose. 
	 * 
	 */
	public static boolean compareCards()
	{
		int mySum = me.getHandSum();
		int dealerSum = dealer.getHandSum();
		if(mySum> dealerSum && mySum <=21 ||dealerSum > 21)
		{
			me.wonBet();
			me.printMoney();
			System.out.println("You win!");
			return true;

		}
		else
		{
			me.printMoney();
			System.out.println("Dealer wins!");	
			return false;
		}
	}
}






