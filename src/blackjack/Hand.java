/*
 Ryan Heath
 */
/*
 * ===========================================================
 * Hand.java: Used for a hand of a player. Can add or remove
 * cards from the hand, get the x and y coordinates for drawing,
 * Can reveal the second card or flip all cards face up.
 * Can determine total value of the hand with blackjack rules
 * Can clear hand to remove all cards from the hand
 * ===========================================================
 */

package blackjack;

import java.awt.Graphics;
import java.util.LinkedList;

public class Hand {
	protected LinkedList<Card> hand; //the cards in the hand
	protected int x;//the x coordinate
	protected int y;//the y coordinate
	
	/** Constructor to create hand
	 * @param x   the x coordinate of the hand
	 * @param y   the y coordinate of the hand
	 */
	public Hand(int x, int y){
		this.x = x;
		this.y = y;
		hand = new LinkedList<Card>(); //makes new LinkedList to contain cards
	}
	
	/**Constructor to create hand if also given cards to add
	 * @param cards  the cards to be added
	 * @param x  the x coordinate of the hand
	 * @param y  the y coordinate of the hand
	 */
	public Hand(LinkedList<Card> cards, int x, int y){
		this(x, y);
		hand.addAll(cards);
	}
	
	/**Constructor to create hand starting with a card
	 * @param card  the card to be added
	 * @param x  the x coordinate of the hand
	 * @param y  the y coordinate of the hand
	 */
	public Hand(Card card, int x, int y){
		this(x, y);
		hand.add(card);
	}
	
	/** Add a card to the hand
	 * @param card  the card to be added
	 */
	public void addCard(Card card){
		hand.add(card);
	}
	
	/** Remove a card from the hand
	 * @param card   the card to be removed
	 */
	public void removeCard(Card card){
		if (hand.remove(card) == false){
			throw new ArrayIndexOutOfBoundsException("card does not exist"); //TODO: get better exception
		}
	}
	
	/** Remove a card from the hand, given face and suit
	 * @param face  the face of the card
	 * @param suit  the suit of the card
	 * @return card  the card that was removed.
	 */
	public Card removeCard(FaceEnum face, SuitEnum suit){
		for (Card card: hand){
			if (card.getFace() == face && card.getSuit() == suit){
				removeCard(card);
				return card;
			}
		}
		return null;
	}
	
	/** Draws hand
	 * 
	 * @param g the Graphics control
	 */
	public void draw(Graphics g){
		int handX;
		if(hand.size()!= 0){//TODO: find a better way to draw cards evenly apart
			if((hand.size() & 1) ==0){//sets inital point for first card to draw
				handX = 76+(hand.size()/2-1)*(81); //if odd
			}else{
				handX = 36+((hand.size()-1)/2)*(81);//if even
			}
			for(Card card : hand){ //draws card
				card.setX(x-handX);
				card.setY(y-48);
				card.draw(g);
				handX-=81;
			}
		}
	}
	
	/** Returns size of hand
	 * @return int of size of hand
	 */
	public int size(){
		return hand.size();
	}
	
	/** Returns x coordinate
	 * @return x    the x coordinate
	 */
	public int getX(){
		return x;
	}
	
	/** Returns y coordinate
	 * @return y    the y coordinate
	 */
	public int getY(){
		return y;
	}
	
	/** Sets x coordinate
	 * @param x  the new x coordinate
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/** Sets y coordinate
	 * @param y  the new y coordinate
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/** Reveals second card in hand
	 */
	public void revealSecond(){
		hand.get(1).flip(false);
	}
	
	/** Gets total value of hand using blackjack rules
	 * @return totalValue  the value of the hand
	 */
	public int getTotalValue(){
		int totalAces = 0; //total number of aces
		int totalValue = 0; //total value of hand
		for (Card card: hand){
			if (card.getFace() == FaceEnum.ACE){//remember how many aces there are
				totalAces +=1;
			}
			totalValue+=card.getFace().returnValue(); //increases value of hand by card value
			while(totalValue > 21 && totalAces != 0){ //if it goes over 21 and there are aces, set ace value to 1
				totalValue -=10;
				totalAces-=1;
			}
		}
		return totalValue;
	}
	
	/** Flips all cards in the hand face up
	 */
	public void flipAll(){
		for (Card card: hand){
			card.flipped = false;
		}
	}
	
	/** Removes all cards in hand
	 * @return removedList the cards that were removed
	 */
	public LinkedList<Card> clearHand(){
		LinkedList<Card> removedlist = hand;
		hand = new LinkedList<Card>();
		return removedlist;
	}
	
}
