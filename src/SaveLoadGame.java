/**
 * Class used to save and Load the game using an IO class to crate a text file and manipulate it to get the desired information
 * @author Alex
 *@since 1/21/2015
 */
public class SaveLoadGame {
	/**
	 * Need cards that are dealt in the hands of each player
	 * Need cards in deck
	 * Custom Cards (Whole deck in the specific game) 
	 * Player's money
	 * Players' names
	 * 
	 */
	static String saveFileName = "Save file text";

	/**
	 * Saves the game by storing all of the 
	 * @param p1 The player that is saving the game.
	 */
	public static void saveGame(Player p1)
	{
		//for(int i = 0 ; i < ... there would be an array of players 
		int money = p1.getMoney();
		String name = p1.getName(); //There will be more players in the parameter? 
		//ArrayList<Card> saveDeck = //getdeck

		IO.createOutputFile(saveFileName);
		IO.println(money+"");
		IO.println(name);

		IO.closeOutputFile();


	}
	public static void loadGame()
	{
		
	}
}
