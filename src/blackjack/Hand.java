package blackjack;

import java.awt.Graphics;
import java.util.LinkedList;

public class Hand {
	protected LinkedList<Card> hand;
	protected int x;
	protected int y;
	
	public Hand(int x, int y){
		this.x = x;
		this.y = y;
		hand = new LinkedList<Card>();
	}
	
	public Hand(LinkedList<Card> cards, int x, int y){
		this(x, y);
		hand.addAll(cards);
	}
	
	public Hand(Card card, int x, int y){
		this(x, y);
		hand.add(card);
	}
	
	public void addCard(Card card){
		hand.add(card);
	}
	
	public void removeCard(Card card){
		if (hand.remove(card) == false){
			throw new ArrayIndexOutOfBoundsException("card does not exist");
		}
	}
	
	public Card removeCard(FaceEnum face, SuitEnum suit){
		for (Card card: hand){
			if (card.getFace() == face && card.getSuit() == suit){
				removeCard(card);
				return card;
			}
		}
		return null;
	}
	
	public void draw(Graphics g){
		int handX;
		if(hand.size()!= 0){
			if((hand.size() & 1) ==0){
				handX = 76+(hand.size()/2-1)*(81);
			}else{
				handX = 36+((hand.size()-1)/2)*(81);
			}
			for(Card card : hand){
				card.setX(x-handX);
				card.setY(y-48);
				card.draw(g);
				handX-=81;
			}
		}
	}
	
	public int size(){
		return hand.size();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void revealSecond(){
		hand.get(1).flip(false);
	}
	
	public int getTotalValueP(){
		int totalAces = 0;
		int totalValue = 0;
		for (Card card: hand){
			if (card.getFace() == FaceEnum.ACE){
				totalAces +=1;
			}
			totalValue+=card.getFace().returnValue();
			while(totalValue > 21 && totalAces != 0){
				totalValue -=10;
				totalAces-=1;
			}
		}
		return totalValue;
	}
	
	public int getTotalValueD(){
		int totalValue = 0;
		int aces = 0;
		for (Card card: hand){
			if (card.getFace() == FaceEnum.ACE){
				aces+=1;
			}
			totalValue+=card.getFace().returnValue();
			if (totalValue> 21 && aces !=0){
				totalValue -= 10;
				aces-=1;
			}
		}
		return totalValue;
	}
	
	public void flipAll(){
		for (Card card: hand){
			card.flipped = false;
		}
	}
	
	public LinkedList<Card> clearHand(){
		LinkedList<Card> removedlist = hand;
		hand = new LinkedList<Card>();
		return removedlist;
	}
	
}
