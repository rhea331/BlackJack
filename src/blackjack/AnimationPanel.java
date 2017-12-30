/*
 	Ryan Heath
 	Code based of University of Auckland Compsci 230 2015 Assignment 1 AnimationPanel.java 
 */


/*
 * ===========================================================
 * AnimationPanel.java: Contains blackjack game. Contains all
 * actions required to play, including bet, hit and stand and
 * reset button to reset game to default. Main drawing area.
 * ===========================================================
 */

package blackjack;

import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class AnimationPanel extends JPanel{
	private Deck deck; // deck to be used by both the player and dealer
	private Hand PlayerHand; //Where the player cards are stored
	private Hand DealerHand; //Where the dealer cards are stored
	private int GameState = 1; //Used for drawing and disabling buttons
	private int TotalMoney = 1000; //The total money the player has
	private int currentBet = 0; //What the player has bet
	private SwingWorker<Void, Void> worker;
	protected BufferedImage background; 
    public AnimationPanel() {
    	PlayerHand = new Hand(400,350);
    	DealerHand = new Hand(400,100);
    	deck = new Deck();
		try{
			background = ImageIO.read(new FileInputStream("resources/green-velvet-texture-background.jpg"));
		} catch (IOException e) {}
		setForeground(Color.white);
    }
    
    /** Draws the backgrounds, cards, and text
     *  @param g The Graphics control
     */
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(background, 0 , 0, null);
    	if (GameState > 2){ //Whenever it's not initializing
			g.drawString("Dealer total: "+DealerHand.getTotalValue(), 360, 180); //if player decides to stand, it will draw the dealer's total
    		if (GameState == 3){ //Player has won
    			g.drawString("YOU WIN", 370, 220);
    			GameState = 1;
    		}
    		else if (GameState == 4){ //Dealer has won
    			g.drawString("DEALER WINS", 350, 220);
    			GameState = 1;
    		}
    		else if(GameState == 5){//Player and Dealer have the same total
    			g.drawString("STAND-OFF", 350, 220);
    			GameState = 1; //set back to initalize
    		}
    	}
    	PlayerHand.draw(g); //draws both player and dealer's hand
    	DealerHand.draw(g);
    	g.drawString("Total money", 0, 200); //draws money, and total value of player's hand
    	g.drawString(""+TotalMoney, 0, 215);
    	g.drawString("Total: "+PlayerHand.getTotalValue(), 380, 420);
    	if(currentBet !=0){ //draws current bet
    		g.drawString("Current bet is "+currentBet, 0, 230);
    	}
    } 
   	
    /**Draws a card for the player and determines if the player is bust or can continue to draw cards
     */
	public void hit(){
		GameState = 6;//game is in progress
		PlayerHand.addCard(deck.drawCard());
		int total = PlayerHand.getTotalValue();
		if (total > 21){ //Player has bust
			DealerHand.revealSecond();
			currentBet = 0;
			GameState = 4;
		}else{GameState = 2;}
		repaint();
	}
	
	/** Dealer now draws until it either reaches 17 or higher and stops, or goes over 21 and busts
	 */
	public void stand(){
			GameState = 6; //game is in progress
			worker = new SwingWorker<Void, Void>(){ //used to be able to call repaint() between draws without freezing the GUI
			@Override
			protected Void doInBackground() throws Exception{ //reveals second card, then draws new card until above 17, pausing for a second each draw
				DealerHand.revealSecond();
				this.publish();
				Thread.sleep(1000);
				while (DealerHand.getTotalValue() < 17){
					DealerHand.addCard(deck.drawCard());
					this.publish();
					Thread.sleep(1000);
				}
				return null;
			}
			
			@Override
			protected void process(List<Void> chunks){ //drawing
				repaint();
			}
			@Override
			protected void done(){ //when finished, determines if dealer has gone bust, or if it hasn't, check whether dealer or player has a higher total value
				while(DealerHand.getTotalValue() < 17){
					DealerHand.addCard(deck.drawCard());
					repaint();
				}
				int dealervalue = DealerHand.getTotalValue();
				if (dealervalue > 21){ //Dealer went bust
					TotalMoney+=currentBet+currentBet;
					currentBet = 0;
					GameState = 3;
				}else{
					if(dealervalue > PlayerHand.getTotalValue()){ //Dealer has a higher total
						currentBet = 0;
						GameState = 4;
					}else if(dealervalue == PlayerHand.getTotalValue()){//Dealer and Player are tied
						TotalMoney+=currentBet;
						GameState = 5;
					}else{ //Player has a higher total
						TotalMoney+=currentBet+currentBet;
						currentBet = 0;
						GameState = 3;
					}
					
				}
				repaint(); //draws the outcome
			}
			
		};
		worker.execute(); // starts SwingWorker
		
	}
	
	/** Initalizes the game
	 * 
	 * @param bet the amount of money the player has bet
	 */
	public void initalize(int bet){ 
		GameState = 2; //game is initalizing
		if (deck.size() < 15){ //if number of cards in deck is under 15, make a new deck
			deck = new Deck();
		}
		deck.shuffle(); //shuffles
		DealerHand.clearHand(); //clears both Dealer and Player's hand from previous games
		PlayerHand.clearHand();
    	DealerHand.addCard(deck.drawCard());//Dealer draws one and Player draws two
    	PlayerHand.addCard(deck.drawCard());
    	PlayerHand.addCard(deck.drawCard());
    	if(PlayerHand.getTotalValue()==21){ //If Player has a natural blackjack, check to see if Dealer gets blackjack
    		DealerHand.addCard(deck.drawCard());
    		if(DealerHand.getTotalValue()==21){ //Dealer also has one, game set to stand-off
    			GameState = 5;
    		}else{ //Player wins, gets 1.5x the bet
    			TotalMoney +=(bet*0.5);
    			GameState = 3;
    		}
    		repaint();
    		return; //exits out early
    	}
    	Card flippedCard = deck.drawCard();//otherwise dealer gets one card face down
    	flippedCard.flip(true);
    	DealerHand.addCard(flippedCard);
		TotalMoney-=bet; //Total Money decreases and bet is recorded
		currentBet = bet;
    	repaint();
	}
	/** Resets the game to default
	 */
	public void reset(){ //resets game
		TotalMoney = 1000;
	}
	
	/** Gets the current state of the game
	 * @return GameState the current state of the game
	 */
	public int getGameState(){ 
		return GameState;
	}
	
	/** Gets the amount of money the Player has 
	 * @return TotalMoney  the total money
	 */
	public int getTotalMoney(){
		return TotalMoney;
	}
}