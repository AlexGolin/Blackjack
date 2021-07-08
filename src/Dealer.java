
public class Dealer extends CardShuffle {


	public static int playerMoney = 100;// The player starts with 100 dollars
	
	public boolean[] bets = {false,false,false,false,false,false}; //  (1,5,25,50,100,500 )

	public static void main (String[]args) //Main class for main operations
	{
		CardShuffle.ShuffleDeck();//The dealer shuffles the deck
	}


	private void placeBet()
	{
		for(int i = 0 ; i <bets.length; i++)
		{
			if(bets[i]==true)
			{
				if(i==0)
				{
					playerMoney=playerMoney-1; 
					bets[0]=false; //return the state of the bet array back to false 
				}
				if(i==1)
				{
					playerMoney=playerMoney-5; 
					bets[1]=false;
				}
				if(i==2)
				{
					playerMoney=playerMoney-25; 
					bets[2]=false; 
				}
				if(i==3)
				{
					playerMoney=playerMoney-50; 
					bets[3]=false;
				}
				if(i==4)
				{
					playerMoney=playerMoney-100; 
					bets[4]=false;
				}
				if(i==5)
				{
					playerMoney=playerMoney-500; 
					bets[5]=false;
				}
			}
		}
		//else, this means the player has chose to stand	
	}

}
