/*
 Ryan Heath
 */
/*
 * ===========================================================
 * Card.java: Used for cards. Can set and get face and suit of 
 * card, along with x and y coordinates for drawing. Can also
 * flip card.
 * ===========================================================
 */

package blackjack;

import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Card {
	protected FaceEnum face; //the face of the card, from Ace to King
	protected SuitEnum suit; //the suit of the card, from Spades, Hearts, Diamond or Clubs
	protected BufferedImage cardImage;//the image of the card
	protected BufferedImage backImage;//the back image of the card TODO: possibly move to deck for optimization
	protected boolean flipped = false; //if card is flipped
	protected int x=0; //x-coordinate of card
	protected int y=0;//y-coordinate of card

	/** Constructor to create a card using FaceEnum
	 * @param face   the face of the card
	 * @param suit   the suit of the card
	 * @param flipped   if the card is flipped or not
	 */
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
	
	/** Draws card
	 * @param g The Graphics Control
	 */
	public void draw(Graphics g){
		if(flipped){ //if flipped draw back, otherwise draw card
			g.drawImage(backImage, x, y, null);
		}else{
			g.drawImage(cardImage, x, y, null);
		}
	}
	
	/** Gets face of card
	 * @return face     the face
	 */
	public FaceEnum getFace(){
		return face;
	}

	/** Gets suit of card
	 * @return suit    the suit
	 */
	public SuitEnum getSuit(){
		return suit;
	}
	
	/** Gets string showing face and suit of card
	 * @return a string of face and suit
	 */
	public String getString(){
		return (face + " " + suit);
	}
	
	/** Sets x coordinate of card
	 * @param x the x coordinate
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/** Sets y coordinate of card
	 * @param y the y coordinate
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/** Sets if card is flipped or not
	 * @param flip    boolean if card is flipped or not
	 */
	public void flip(boolean flip){
		if (flip){flipped = true;}
		else{flipped = false;}
	}
	
	/** Returns location of image for card
	 * @return the location of the image for card of suit and face
	 */
	private String getImageLocation(){
		String location = "resources/"+face+suit+".bmp";
		return location.toLowerCase();
		
	}
}
