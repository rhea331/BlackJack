/*
 	Ryan Heath
 	Code based of University of Auckland Compsci 230 2015 Assignment 1 A1.java 
 */


/*
 * ===========================================================
 * Blackjack.java: Creates JFrame containing a blackjack game
 * and buttons necessary to play it
 * ===========================================================
 */

package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class Blackjack extends JFrame {
	AnimationPanel bjpanel; //blackjack panel
	JButton hitButton, standButton, betButton, resetButton; //buttons for hit, stand, bet and reset
	JTextField betTxt;//text to input bet amount
	
	
	/** main method 
	  */
    public static void main(String[] args){
    	Blackjack BJFrame = new Blackjack();
    }
    
    /**
     Constructor for components
     */
    public Blackjack(){
    	super("Blackjack");
    	bjpanel = new AnimationPanel();
    	add(bjpanel, BorderLayout.CENTER);
    	add(setUpButtons(), BorderLayout.SOUTH);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(800,500);
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    	Dimension frameSize = getSize();
    	setLocation((d.width - frameSize.width)/2, (d.height - frameSize.height) / 2);
    	setVisible(true);
    	setResizable(false);
    }

    
    /** Sets up the button panel
     * @return buttonPanel     the button panel
     */
    public JPanel setUpButtons(){
    	JPanel buttonPanel = new JPanel(new FlowLayout());
    	//Bet Button setup
    	betTxt = new JTextField("100");
    	betButton = new JButton("Bet");
    	betButton.setToolTipText("Insert number between $2 and $500");
    	betButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			if(bjpanel.getGameState() == GameStateEnum.DEFAULT){
    				int betNo=0;
    				try{
    					betNo = Integer.parseInt(betTxt.getText());
    					if(betNo > bjpanel.getTotalMoney()){
    						JOptionPane.showMessageDialog(null, "You don't have enough money!");
    					}
    					if (betNo > 1 && betNo < 501){
    						hitButton.setEnabled(true);
    						standButton.setEnabled(true);
    						bjpanel.initialize(betNo);
    					}else{
    						JOptionPane.showMessageDialog(null, "Please insert a number between 2 and 500.");
    					}
    				}catch(Exception ex){}
    			}

    		}
    	});
    	//Hit Button setup
    	hitButton = new JButton("Hit");
		hitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bjpanel.getGameState() == GameStateEnum.INITIALIZING){
					bjpanel.hit();
				}
			}
    	});
		hitButton.setEnabled(false);
    	//Stand Button setup
		standButton = new JButton("Stand");
		standButton.setEnabled(false);
		standButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(bjpanel.getGameState() == GameStateEnum.INITIALIZING){
					bjpanel.stand();
				}
			 }
		});
    	//Reset Button setup
		resetButton = new JButton("Reset");
		resetButton.setEnabled(true);
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(bjpanel.getGameState() == GameStateEnum.DEFAULT){
					bjpanel.reset();
				}
			}
		});
		
		//Adding the buttons to the panel
		buttonPanel.add(betButton);
		buttonPanel.add(betTxt);
		buttonPanel.add(hitButton);
		buttonPanel.add(standButton);
		buttonPanel.add(resetButton);
		return buttonPanel;
    }
    
    
}