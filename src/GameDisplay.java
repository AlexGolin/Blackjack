import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;
/**
 * Class that visually displays the game, contains the AI for the dealer, and uses all the other classes to 
 * create the main functions of the game. 
 * @author Alex
 *@since 1/22/2015
 */
public class GameDisplay extends JFrame implements ActionListener {

	public static void main (String[]Args) throws IOException 
	{
		GameDisplay d = new GameDisplay();		
		d.show();
	}

	/**
	 * JFrame to use
	 */
	JFrame frame;

	/**
	 * Create a JPanel and JLabel to use
	 * 
	 */
	JPanel jp = new JPanel();
	JLabel jl = new JLabel();

	/**
	 * Text Field for displaying messages
	 * 
	 */
	JLabel[] gameTexts = new JLabel[4];

	/**
	 * JLabel to display messages such as "You won!" and "You lose" and so on
	 */
	JLabel messages = new JLabel();

	/**
	 * Array List of deck to display with the images
	 */
	private ArrayList<Card> displayDeck = new ArrayList();


	/**
	 * dealer hand to display
	 */
	private Card[] displayDealerHand = new Card[7];	
	/**
	 * Player hand to display
	 */
	private Card[] displayPlayerHand = new Card[7];
	/**
	 * Menu bar for the program where the options are 
	 */
	JMenuBar menuBar = new JMenuBar();


	/**
	 * The card locations for the player
	 */
	private JButton[] playercards = new JButton[7];

	/**
	 * The card locations of the dealer
	 */
	private JButton[] dealcards = new JButton[7];

	/**
	 * An array of buttons that are used to play the game
	 */
	private JButton[] gameButtons  = new JButton[3];

	/**
	 * Colored buttons that are used to bet for the game, representing chips
	 */
	private JButton[] betButtons = new JButton[6];

	/**The player's bank in the bottom of the screen with the otehr options
	 * 
	 */
	private JButton Bank = new JButton();

	/**
	 * The main box to display the program
	 * @throws IOException 
	 */

	/**
	 * The action preformed listener which is triggered when the user clicks the button.
	 * numdeals is the count of how many cards have been delt, to make suret that they do not go over the limit of 7 cards
	 */
	public int numdeals = 0 ; 

	/**
	 * First time checker to initiate the RunGame method
	 */
	private boolean firstTime = true;

	GameDisplay() throws IOException 
	{

		/**
		 * Create the Image 
		 */
		jl.setIcon(new ImageIcon("data\\cardback image.jpg"));
		jp.add(jl);
		add(jp);

		validate();

		//Add the menu bar to the frame
		setJMenuBar(menuBar); 

		//Define and add two drop down menu
		JMenu optionsMenu = new JMenu("Options");
		JMenuItem saveItem = new JMenuItem(new AbstractAction("Save")
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("save menu clicked");
				SaveLoadGame.saveGame(GameRunner.me);
			}

		});

		/*
		 *Add the save option in the drop down menu
		 */
		optionsMenu.add(saveItem);
		menuBar.add(optionsMenu);

		//Give it an action listener
		optionsMenu.addActionListener(this);


		//Create the window of the program
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1500,1000);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("BlackJack");
		Container c = getContentPane();
		c.setBackground(new Color(0,170,100));	

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 900, 1500,70);
		buttonPanel.setBackground(Color.black);

		/**
		 * buttonPosition is an int which holds the x value of where the
		 * button should be. Starts with the first button,
		 * and then shifts over each time a button is created
		 * 
		 */
		int buttonPosition = 30;

		/**
		 * The creation of the game buttons
		 */
		for(int i = 0 ; i< gameButtons.length; i ++)
		{
			/**
			 * The label will change to the label for the button when creating the buttons
			 */
			System.out.println(i);
			String label= "Deal";
			if(i == 0){ label= "Deal";} 
			if(i==1)  {label = "Hit"; } 
			if(i == 2){label = "Stand";}

			gameButtons[i] = new JButton(label);
			gameButtons[i].setBounds(buttonPosition,900,100,30);
			gameButtons[i].setOpaque(true);
			gameButtons[i].addActionListener(this);
			//Set the hit/stand buttons to be disabled
			gameButtons[i].setEnabled(false);
			buttonPanel.add(gameButtons[i]);
			buttonPosition += 130;
		}
		//Set the deal button to be enabled 
		gameButtons[0].setEnabled(true);


		/**
		 * Create the betting buttons represented as colored squares
		 *  
		 */
		int xPlace= 700;
		String chipNum="error";
		Color chipColor= Color.pink;
		for(int i = 0; i< betButtons.length; i++)
		{
			if(i==0){chipNum="5";
			chipColor=Color.orange;}
			if(i==1){chipNum="10";
			chipColor=Color.blue;}
			if(i==2){chipNum="25";
			chipColor=Color.GREEN;}
			if(i==3){chipNum="50";
			chipColor=Color.DARK_GRAY;}
			if(i==4){chipNum="100";
			chipColor=Color.orange;
			chipColor=Color.yellow;}
			if(i==5){chipNum="500";
			chipColor=Color.RED;}
			betButtons[i] =  new JButton(chipNum);
			betButtons[i].setBounds(xPlace,900,10,10);
			betButtons[i].setOpaque(true);
			betButtons[i].setBackground(chipColor);
			betButtons[i].addActionListener(this);
			betButtons[i].setEnabled(false);//Bet buttons start disabled so the user cannot bet until it is the time to bet
			buttonPanel.add(betButtons[i]);
			xPlace += 200;
		}

		/**
		 * Add the smaller JPanel to the big panel
		 */
		c.add(buttonPanel);

		/**
		 * Create a card locations so that the cards can have specific places on the gui board
		 * For the dealer's hand 
		 */
		int xLocation = 50;
		for(int i =0; i <dealcards.length;i++)
		{
			dealcards[i] = new JButton(new ImageIcon("data\\cardback image.jpg"));
			dealcards[i].setBounds(xLocation,50,190,270);
			dealcards[i].setVisible(false);//Start with the card slots invisible 
			dealcards[i].setOpaque(false);
			dealcards[i].validate();
			c.add(dealcards[i]);
			xLocation+= 200;
		}

		/**
		 * Create a card locations so that the cards can have specific places on the gui board
		 * For the player's hand 
		 */
		int xLocationP = 50;
		for(int i =0; i <playercards.length;i++)
		{
			playercards[i] = new JButton(new ImageIcon("data\\cardback image.jpg"));
			playercards[i].setBounds(xLocationP,550,190,270);
			playercards[i].setVisible(false);//Start with the card slots invisible
			playercards[i].setOpaque(false);
			playercards[i].validate();
			c.add(playercards[i]);
			xLocationP+= 200;
		}

		/**
		 * Set the JTextFeild dealerText.
		 * In this text field, the name of the dealer will be shown 
		 */

		/*
		 * Create the text boxes using a for loop
		 */
		int yPosition = 0; 
		Font font1 = new Font("SansSerif", Font.BOLD, 20);

		for(int i = 0 ; i <4; i++)
		{
			gameTexts[i]= new JLabel("Dealer");
			gameTexts[i].setBounds(20,yPosition, 1000, 1000);
			gameTexts[i].setBackground(null);
			gameTexts[i].setForeground(null);
			gameTexts[i].setBorder(null);
			gameTexts[i].setFont(font1);
			c.add(gameTexts[i]);
			yPosition +=270;
		}
		/**
		 * Set the text areas to optimal Locations, and the labels of the text areas
		 */
		gameTexts[0].setBounds(20,-25, 1000, 100);//Dealer name
		gameTexts[1].setBounds(20,820, 1000, 100);//Player Hand sum
		gameTexts[2].setBounds(20,450, 1000, 100);//Player Name
		gameTexts[3].setBounds(20,340, 1000, 100);//Dealer hand sum
		gameTexts[0].setText(GameRunner.dealer.getName());//Dealer name
		gameTexts[2].setText(GameRunner.me.getName());//Player Name
		gameTexts[3].setText(GameRunner.dealer.getName()+ " holds "+ GameRunner.dealer.getHandSum());//Dea;er hand sum
		gameTexts[1].setText(GameRunner.me.getName()+ " holds "+ GameRunner.me.getHandSum() );//Player Hand sum

		/**
		 * Add in the message JLabel
		 */
		messages = new JLabel("Press Deal to Start");//Starts of With a message to start
		messages.setBounds(700,-60, 1500, 1000);
		messages.setBackground(null);
		messages.setForeground(null);
		messages.setBorder(null);
		messages.setFont(font1);
		c.add(messages);

		/**
		 * Create the player's bank in the bottom of the screen, keeping track of the user's money
		 * This will go up or down depending on the user's success in teh bets, and the objective of teh game is to reach a set amount of money, and not fall down to 0 dollars
		 */
		Bank = new JButton();
		Bank.setBounds(400,900,100,30);
		Bank.setOpaque(true);
		Bank.setText("$"+ GameRunner.me.getMoney());
		Color Money = Color.GREEN;
		Bank.setBackground(Money);
		buttonPanel.add(Bank);


		//Add the JPane
		c.repaint();	
		buttonPanel.repaint();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.print("Button pressd");//If any button is pressed this is called (error check)

		/**
		 * Deal Button
		 */
		if(e.getSource() == gameButtons[0])//Deal Button
		{
			messages.setText("");//The Message text is set to nothing so it only appears when necessary

			/*
			 * The Deal button starts the game, and if the game is in it's first time, then the gameRunner StartGame method is used to create the deck
			 */
			if(firstTime==true){
				try {
					GameRunner.waitDeal= true;
					GameRunner.StartGame();
					GameRunner.waitDeal= true;
					System.out.println("Game is running...");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.err.println("Error with gameRunner in GameDisplay");
					//e1.printStackTrace();
				}
				GameRunner.waitDeal= true;
				firstTime = false;// Set firstTime to true so it never triggers the if statement again.
			}


			/**
			 * Check each time that the Deal button is pressed to determine if the player won the game of BlackJack
			 */
			if(GameRunner.GameWin()==1)//The player reached their goal and won
			{
				messages.setText("You Won!");
			}
			if(GameRunner.GameWin()==-1)
			{
				messages.setText("Game Over");

			}

			/*
			 * Any other time that Deal is used, it is a restart for the next turn 
			 */
			try {
				GameRunner.RunGame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			GameRunner.waitDeal= true;//Allow the GameRunner method RunGame() to continue

			/*
			 * If the hand is full, no more cards can be added
			 */
			if(numdeals>= dealcards.length)
			{
				return;
			}

			/** DEALER CLEAR
			 * Clear all of the previous Cards
			 */
			for(int i = 0 ; i <GameRunner.dealer.getHand().length;i++)
			{
				dealcards[i].setIcon(new ImageIcon("data\\cardback image.jpg"));
				dealcards[i].setVisible(false);
			}

			/** PLAYER CLEAR
			 * Clear all of the previous Cards
			 */
			for(int i = 0 ; i <GameRunner.me.getHand().length;i++)
			{
				playercards[i].setIcon(new ImageIcon("data\\cardback image.jpg"));
				playercards[i].setVisible(false);
			}

			/**								DEALER CARDS
			 * 
			 * When the card is dealt, the card shows the face of whatever card is drawn from the deck.
			 */
			displayDealerHand = GameRunner.dealer.getHand();

			for(int i = 0;i<2;i++) //Create the appropriate amount of cards (2) to display
			{
				dealcards[i].setIcon(new ImageIcon("cardPics\\"+displayDealerHand[i].getSuit()+"\\"+displayDealerHand[i].getLocation())); //this will be: cardPics\\Diamond\\ace-c.png
				dealcards[i].setVisible(true);


				System.out.println("numdeals is "+ numdeals);//Error check
			}

			/**								Player CARDS
			 * 
			 * When the card is dealt, the card shows the face of whatever card is drawn from the deck.
			 */
			displayPlayerHand = GameRunner.me.getHand();
			for(int i = 0;i< 2;i++) //Create the appropriate amount of cards (2) to display
			{
				playercards[i].setIcon(new ImageIcon("cardPics\\"+ displayPlayerHand[i].getSuit()+"\\"+displayPlayerHand[i].getLocation())); //this will be: cardPics\\Diamond\\ace-c.png
				playercards[i].setVisible(true);

				System.out.println("numdeals is "+ numdeals);//Error check
			}
			/**
			 * Display the card sums of each player
			 */
			gameTexts[3].setText(GameRunner.dealer.getName()+ " holds "+ GameRunner.dealer.getHandSum());//Dea;er hand sum
			gameTexts[1].setText(GameRunner.me.getName()+ " holds "+ GameRunner.me.getHandSum() );//Player Hand sum
			/*
			 * Allow the betButtons to be used so the user can bet
			 * The buttons are only available if the user can afford the bet
			 */

			messages.setText("Please Bet");	
			if(GameRunner.me.getMoney()>=500)
			{
				for(int i = 0 ; i<betButtons.length;i++)
				{
					betButtons[i].setEnabled(true);
				}
			}
			else if(GameRunner.me.getMoney()<500 && GameRunner.me.getMoney()>=100)
			{
				for(int i = 0 ; i<betButtons.length-1;i++)
				{
					betButtons[i].setEnabled(true);
				}
			}
			else if(GameRunner.me.getMoney()<100 && GameRunner.me.getMoney()>=50)
			{
				for(int i = 0 ; i<betButtons.length-2;i++)
				{
					betButtons[i].setEnabled(true);
				}
			}
			else if(GameRunner.me.getMoney()<50 && GameRunner.me.getMoney()>=25)
			{
				for(int i = 0 ; i<betButtons.length-3;i++)
				{
					betButtons[i].setEnabled(true);
				}
			}
			else if(GameRunner.me.getMoney()<25 && GameRunner.me.getMoney()>=10)
			{
				for(int i = 0 ; i<betButtons.length-4;i++)
				{
					betButtons[i].setEnabled(true);
				}
			}
			else
			{
				for(int i = 0 ; i<betButtons.length-5;i++)
				{
					betButtons[i].setEnabled(true);
				}
			}
			System.out.println("Bet buttons enabled...");//Error check
			gameButtons[0].setEnabled(false); //Set the button to disabled after use.
			//this.displayText(this.getGraphics(), "Bet");
		}

		/**										HIT FOR USER 
		 * Hit Button Action Listener
		 * FOR USER
		 */
		if(e.getSource() == gameButtons[1])
		{
			GameRunner.Hit(GameRunner.me);

			for(int i = 0;i< GameRunner.me.getHand().length;i++) //Create the appropriate amount of cards (2) to display
			{
				playercards[i].setIcon(new ImageIcon("cardPics\\"+ displayPlayerHand[i].getSuit()+"\\"+displayPlayerHand[i].getLocation())); //this will be: cardPics\\Diamond\\ace-c.png
				playercards[i].setVisible(true);
				gameTexts[3].setText(GameRunner.dealer.getName()+ " holds "+ GameRunner.dealer.getHandSum());//Dea;er hand sum
				gameTexts[1].setText(GameRunner.me.getName()+ " holds "+ GameRunner.me.getHandSum() );//Player Hand sum

				//Check the win after each move
				if(GameRunner.me.getHandSum()==21)// The play reached 21
				{
					messages.setText("You Got 21!");
					GameRunner.me.wonBet();// Player wins the bet
					Bank.setText("$"+ GameRunner.me.getMoney());
					gameButtons[1].setEnabled(false);//They can no longer keep hitting
					gameButtons[2].setEnabled(false);//Set stand to disabled to prevent error
					gameButtons[0].setEnabled(true);
				}
				if(GameRunner.me.getHandSum()>21) //The player busted
				{
					messages.setText("Busted");
					gameButtons[1].setEnabled(false);//They can no longer keep hitting
					gameButtons[2].setEnabled(false);//Set stand to disabled to prevent error
					gameButtons[0].setEnabled(true);//Set the deal button to restart
				}
			}
		}	

		/**										STAND FOR USER
		 * Stand Button Action Listener
		 * FOR USER
		 */
		if(e.getSource() == gameButtons[2])
		{
			gameButtons[1].setEnabled(false);//They can no longer keep hitting
			gameButtons[2].setEnabled(false);//Set stand to disabled to prevent error
			dealerAI();//Initiate the dealer moves
		}	

		/**
		 * Bet button Action Listeners
		 * 
		 */
		if(e.getSource() == betButtons[0])//5
		{
			GameRunner.bet(5);//The value of the bet.
			for(int i = 0;i<betButtons.length;i++)
			{
				Bank.setText("$"+ GameRunner.me.getMoney());
				messages.setText("");	
				betButtons[i].setEnabled(false);//After bet, set all betButtons to false. 
				//At the end, set the hit/stand buttons to enabled 
				gameButtons[1].setEnabled(true); 
				gameButtons[2].setEnabled(true); 
			}
		}
		if(e.getSource() == betButtons[1])//10
		{
			GameRunner.bet(10);//The value of the bet.
			for(int i = 0;i<betButtons.length;i++)
			{
				Bank.setText("$"+ GameRunner.me.getMoney());
				messages.setText("");	
				betButtons[i].setEnabled(false);//After bet, set all betButtons to false. 
				//At the end, set the hit/stand buttons to enabled 
				gameButtons[1].setEnabled(true); 
				gameButtons[2].setEnabled(true); 
			}
		}
		if(e.getSource() == betButtons[2])//25
		{
			GameRunner.bet(25);//The value of the bet.
			for(int i = 0;i<betButtons.length;i++)
			{
				Bank.setText("$"+ GameRunner.me.getMoney());
				messages.setText("");	
				betButtons[i].setEnabled(false);//After bet, set all betButtons to false. 
				//At the end, set the hit/stand buttons to enabled 
				gameButtons[1].setEnabled(true); 
				gameButtons[2].setEnabled(true); 
			}
		}
		if(e.getSource() == betButtons[3])//50
		{
			GameRunner.bet(50);//The value of the bet.
			for(int i = 0;i<betButtons.length;i++)
			{
				Bank.setText("$"+ GameRunner.me.getMoney());
				messages.setText("");	
				betButtons[i].setEnabled(false);//After bet, set all betButtons to false. 
				//At the end, set the hit/stand buttons to enabled 
				gameButtons[1].setEnabled(true); 
				gameButtons[2].setEnabled(true); 
			}
		}
		if(e.getSource() == betButtons[4])//100
		{
			GameRunner.bet(100);//The value of the bet.
			for(int i = 0;i<betButtons.length;i++)
			{
				Bank.setText("$"+ GameRunner.me.getMoney());
				messages.setText("");	
				betButtons[i].setEnabled(false);//After bet, set all betButtons to false. 
				//At the end, set the hit/stand buttons to enabled 
				gameButtons[1].setEnabled(true); 
				gameButtons[2].setEnabled(true); 
			}
		}
		if(e.getSource() == betButtons[5])//500
		{
			GameRunner.bet(500);//The value of the bet.
			for(int i = 0;i<betButtons.length;i++)
			{
				Bank.setText("$"+ GameRunner.me.getMoney());
				messages.setText("");	
				betButtons[i].setEnabled(false);//After bet, set all betButtons to false. 
				//At the end, set the hit/stand buttons to enabled 
				gameButtons[1].setEnabled(true); 
				gameButtons[2].setEnabled(true); 
			}
		}
	}

	/**
	 * Method containing the AI of the dealer
	 * The dealer hits if handSum is 17 or lower, and the dealer stands if it is higher than 17
	 * The dealer then compares their cards to the player's cards using the CompareCards class
	 */
	public void dealerAI()
	{
		/**
		 * Marker to determine when the dealer is finished making their move
		 */
		boolean dealerdone=false;

		/**
		 * The dealer has a chance of drawing a blackjack tight away to win the round
		 */
		if(GameRunner.dealer.getHandSum()==21)//If dealer drew a 21
		{
			messages.setText("Dealer Reached 21");//Set the center menu to the desired message
			System.out.println("Dealer won");
			gameButtons[0].setEnabled(true);//Enable the Deal button to start another turn 
			return;//Stop the method from continuing

		}
		while(dealerdone==false)
		{

			if(GameRunner.dealer.getHandSum()<=17)
			{
				GameRunner.Hit(GameRunner.dealer);

				for(int i = 0;i< GameRunner.dealer.getHand().length;i++) //Create the appropriate amount of cards (2) to display
				{
					dealcards[i].setIcon(new ImageIcon("cardPics\\"+ displayDealerHand[i].getSuit()+"\\"+displayDealerHand[i].getLocation())); //this will be: cardPics\\Diamond\\ace-c.png
					dealcards[i].setVisible(true);
					gameTexts[3].setText(GameRunner.dealer.getName()+ " holds "+ GameRunner.dealer.getHandSum());//Dealer hand sum
					gameTexts[1].setText(GameRunner.me.getName()+ " holds "+ GameRunner.me.getHandSum() );//Player Hand sum

					if(GameRunner.dealer.getHandSum()==21)//The dealer reached 21
					{
						messages.setText("Dealer Reached 21");
						System.out.println("Dealer won");
						//Display a message on the final outcome of the turn 

						gameButtons[0].setEnabled(true);//Enable the Deal button to start another turn 
					}
					//Check each time if the dealer won
					if(GameRunner.compareCards()==true)
					{
						messages.setText("You won the round");
						GameRunner.me.wonBet();
						Bank.setText("$"+ GameRunner.me.getMoney());
						gameButtons[0].setEnabled(true);//Enable the Deal button to start another turn 
					}
					else messages.setText("Dealer won the round");
					gameButtons[0].setEnabled(true);//Enable the Deal button to start another turn 
				}
			}
			else dealerdone=true;
		}
		dealerdone=true;
		//Display a message on the final outcome of the turn 
		if(GameRunner.compareCards()==true)
		{
			messages.setText("You won the round");
			GameRunner.me.wonBet();
			Bank.setText("$"+ GameRunner.me.getMoney());
		}
		else messages.setText("Dealer won the round");
		gameButtons[0].setEnabled(true);//Enable the Deal button to start another turn 
		return;//Stop the method by returning on a void

	}

}







