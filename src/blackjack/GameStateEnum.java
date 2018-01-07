package blackjack;

public enum GameStateEnum {
	DEFAULT(""),
	INITIALIZING(""),
	PLAYING(""),
	PLAYERWIN("YOU WIN"),
	DEALERWIN("DEALER WINS"),
	STANDOFF("STAND-OFF");
	
	private String message;
	
	/** Sets ending message
	 * @param message   the message to be displayed
	 */
	GameStateEnum(String message){
		this.message = message;
	}
	
	/** Returns the message to be displayed
	 * @return message    the displayed message
	 */
	String returnMessage(){
		return message;
	}
}
