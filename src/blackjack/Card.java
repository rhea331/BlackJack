package blackjack;

import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Card {
	protected FaceEnum face;
	protected SuitEnum suit;
	protected BufferedImage cardImage;
	protected BufferedImage backImage;
	protected boolean flipped = false;
	protected int x=0;
	protected int y=0;

	public Card(FaceEnum face, SuitEnum suit, boolean flipped){
		this.face = face;
		this.suit = suit; 
		this.flipped = flipped;
		cardImage = null;
		try{
			cardImage = ImageIO.read(new FileInputStream(getImageLocation()));
			backImage = ImageIO.read(new FileInputStream("resources/back.bmp"));
		} catch (IOException e) {}
	}
	
	public Card(int face, SuitEnum suit, boolean flipped){
		this(FaceEnum.values()[face], suit, flipped);
		
	}
	
	public void draw(Graphics g){
		if(flipped){
			g.drawImage(backImage, x, y, null);
		}else{
			g.drawImage(cardImage, x, y, null);
		}
	}
	
	public FaceEnum getFace(){
		return face;
	}
	
	public SuitEnum getSuit(){
		return suit;
	}
	
	public String getString(){
		return (face + " " + suit);
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void flip(boolean flip){
		if (flip){flipped = true;}
		else{flipped = false;}
	}
	
	private String getImageLocation(){
		String location = "resources/"+face+suit+".bmp";
		return location.toLowerCase();
		
	}
}
