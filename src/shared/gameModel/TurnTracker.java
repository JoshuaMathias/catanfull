package shared.gameModel;

import java.io.Serializable;

/**
 * This class is for keeping track of the turns.
 * @author Ife's group
 *
 */
public class TurnTracker implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3799883634231591208L;
	private int currentTurn = 0; //playerIndex of who's turn it is
	private String status = "FirstRound";
	private int longestRoad = -1;
	private int largestArmy = -1;
	
	public int getCurrentTurn() {
		return currentTurn;
	}
	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getLongestRoad() {
		return longestRoad;
	}
	public void setLongestRoad(int longestRoad) {
		this.longestRoad = longestRoad;
	}
	public int getLargestArmy() {
		return largestArmy;
	}
	public void setLargestArmy(int largestArmy) {
		this.largestArmy = largestArmy;
	}
	
	public void nextTurn(){
		if(currentTurn < 3){
			currentTurn++;
		}
		else{
			currentTurn = 0;
		}
	}
	
	public void previousTurn(){
		currentTurn--;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentTurn;
		result = prime * result + largestArmy;
		result = prime * result + longestRoad;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TurnTracker other = (TurnTracker) obj;
		if (currentTurn != other.currentTurn)
			return false;
		if (largestArmy != other.largestArmy)
			return false;
		if (longestRoad != other.longestRoad)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
}
