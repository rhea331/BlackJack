package blackjack;

import java.util.*;

public class Deck {
	
	protected LinkedList<Card> deck;
	
	public Deck(){
		deck = new LinkedList<Card>();
		for (FaceEnum f: FaceEnum.values()){
			for (SuitEnum s: SuitEnum.values()){
				deck.add(new Card(f, s, false));
			}
		}
	}
	
	public LinkedList<Card> getDeck(){
		return deck;
	}
	
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public Card drawCard(){
		if (size()==0){
			return null;
			}
		Card topcard = deck.removeFirst();
		return topcard;
	}
	
	public void addCards(LinkedList <Card> cards){
		deck.addAll(cards);
	}
	
	public int size(){
		return deck.size();
	}
	
	public void printDeck(){
    	for(Card c: deck){
    		System.out.println(c.getString());
    	}
	}
}
