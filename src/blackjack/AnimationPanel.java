package blackjack;


import java.awt.Color;
import java.awt.Dimension;
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
	private Deck deck;
	private Hand PlayerHand;
	private Hand DealerHand;
	private int GameState = 1;
	private int TotalMoney = 1000;
	private int currentBet = 0;
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
 
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
 
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(background, 0 , 0, null);
    	if (GameState > 2){
			g.drawString("Dealer total: "+DealerHand.getTotalValueD(), 360, 180);
    		if (GameState == 3){
    			g.drawString("YOU WIN", 370, 220);
    		}
    		else if (GameState == 4){
    			g.drawString("DEALER WINS", 350, 220);
    		}
    		else if(GameState == 5){
    			g.drawString("STAND-OFF", 350, 220);
    		}
			GameState = 1;
    	}
    	PlayerHand.draw(g);
    	DealerHand.draw(g);
    	g.drawString("Total money", 0, 200);
    	g.drawString(""+TotalMoney, 0, 215);
    	g.drawString("Total: "+PlayerHand.getTotalValueP(), 380, 420);
    	if(currentBet !=0){
    		g.drawString("Current bet is "+currentBet, 0, 230);
    	}
    } 
   	
	public void hit(){
		GameState = 6;
		PlayerHand.addCard(deck.drawCard());
		int total = PlayerHand.getTotalValueP();
		System.out.println("CURRENT TOTAL IS "+total);
		if (total > 21){
			System.out.println("BUST");
			DealerHand.revealSecond();
			currentBet = 0;
			GameState = 4;
		}else{GameState = 2;}
		repaint();
	}
	
	public void stand(){
			GameState = 6;
			worker = new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception{
				DealerHand.revealSecond();
				this.publish();
				Thread.sleep(1000);
				while (DealerHand.getTotalValueD() < 17){
					DealerHand.addCard(deck.drawCard());
					this.publish();
					Thread.sleep(1000);
				}
				return null;
			}
			
			@Override
			protected void process(List<Void> chunks){
				repaint();
			}
			@Override
			protected void done(){
				while(DealerHand.getTotalValueD() < 17){
					DealerHand.addCard(deck.drawCard());
					System.out.println("Dealer's current total is "+DealerHand.getTotalValueD());
					repaint();
				}
				int dealervalue = DealerHand.getTotalValueD();
				if (dealervalue > 21){
					System.out.println("Dealer has been busted");
					TotalMoney+=currentBet+currentBet;
					currentBet = 0;
					GameState = 3;
				}else{
					System.out.println("Dealer stands at "+dealervalue);
					if(dealervalue > PlayerHand.getTotalValueP()){
						System.out.println("Dealer total is higher than players.");
						currentBet = 0;
						GameState = 4;
					}else if(dealervalue == PlayerHand.getTotalValueP()){
						System.out.println("Stand-off");
						TotalMoney+=currentBet;
						GameState = 5;
					}else{
						System.out.println("Player total is higher than dealers.");
						TotalMoney+=currentBet+currentBet;
						currentBet = 0;
						GameState = 3;
					}
					
				}
				repaint();
			}
			
		};
		worker.execute();
		
	}
	
	
	public void initalize(int bet){
		GameState = 2;
		if (deck.size() < 15){
			System.out.println("Shuffling new deck");
			deck = new Deck();
		}
		deck.shuffle();
		DealerHand.clearHand();
		PlayerHand.clearHand();
    	DealerHand.addCard(deck.drawCard());
    	PlayerHand.addCard(deck.drawCard());
    	PlayerHand.addCard(deck.drawCard());
    	if(PlayerHand.getTotalValueP()==21){
    		System.out.println("Natural Blackjack");
    		DealerHand.addCard(deck.drawCard());
    		if(DealerHand.getTotalValueD()==21){
    			System.out.println("Stand-off");
    			GameState = 5;
    		}else{
    			TotalMoney +=(bet*0.5);
    			GameState = 3;
    		}
    		repaint();
    		return;
    	}
    	Card flippedCard = deck.drawCard();
    	flippedCard.flip(true);
    	DealerHand.addCard(flippedCard);
		TotalMoney-=bet;
		currentBet = bet;
    	repaint();
	}
	
	public void reset(){
		TotalMoney = 1000;
	}
	
	public int getGameState(){
		return GameState;
	}
	
	
	public int getTotalMoney(){
		return TotalMoney;
	}
}