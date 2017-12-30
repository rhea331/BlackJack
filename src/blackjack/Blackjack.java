package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Blackjack extends JFrame {
	AnimationPanel bjpanel;
	JButton hitButton, standButton, betButton, resetButton; 
	JTextField betTxt;

    public static void main(String[] args){
    	Blackjack BJFrame = new Blackjack();

    }
    
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

    
    public JPanel setUpButtons(){
    	betTxt = new JTextField("100");
    	JPanel buttonPanel = new JPanel(new FlowLayout());
    	betButton = new JButton("Bet");
    	betButton.setToolTipText("Insert number between $2 and $500");
    	betButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			if(bjpanel.getGameState() ==1){
    				int betNo=0;
    				try{
    					betNo = Integer.parseInt(betTxt.getText());
    					if (betNo > 1 && betNo < 501){
    						hitButton.setEnabled(true);
    						standButton.setEnabled(true);
    						bjpanel.initalize(betNo);
    					}else{
    						JOptionPane.showMessageDialog(null, "Please insert a number between 2 and 500.");
    					}
    				}catch(Exception ex){}
    			}

    		}
    	});
    	hitButton = new JButton("Hit");
		hitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bjpanel.getGameState() == 2){
					bjpanel.hit();
				}
				if (bjpanel.getTotalMoney() <= 0){
					hitButton.setEnabled(false);
					standButton.setEnabled(false);
					betButton.setEnabled(false);
					resetButton.setEnabled(true);
				}
			}
    	});
		hitButton.setEnabled(false);
		standButton = new JButton("Stand");
		standButton.setEnabled(false);
		standButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(bjpanel.getGameState() == 2){
					bjpanel.stand();
				}
				if (bjpanel.getTotalMoney() <= 0){
					hitButton.setEnabled(false);
					standButton.setEnabled(false);
					betButton.setEnabled(false);
					resetButton.setEnabled(true);
				}
			 }
		});
		resetButton = new JButton("Reset");
		resetButton.setEnabled(false);
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bjpanel.reset();
				hitButton.setEnabled(true);
				standButton.setEnabled(true);
				betButton.setEnabled(true);
				resetButton.setEnabled(false);
			}
		});
		buttonPanel.add(betButton);
		buttonPanel.add(betTxt);
		buttonPanel.add(hitButton);
		buttonPanel.add(standButton);
		buttonPanel.add(resetButton);
		return buttonPanel;
    }
    
    
}