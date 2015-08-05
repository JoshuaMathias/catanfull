package shared.gameModel;

import client.facade.ClientFacade;
import shared.definitions.DevCardType;

/**
 * This class represents a list of development cards.
 * @author Ife's group
 *
 */
public class DevCardList {

	private int monopoly = 0;
	private int monument = 0;
	private int roadBuilding = 0;
	private int soldier = 0;
	private int yearOfPlenty = 0;
	
	public DevCardList(boolean full){
		if (full){
			monopoly = 2;
			monument = 5;
			roadBuilding = 2;
			soldier = 14;
			yearOfPlenty = 2;
		}
	}
	
	public DevCardList(int monopoly, int monument, int roadBuilding, int soldier, int yearOfPlenty){
		this.monopoly = monopoly;
		this.monument = monument;
		this.roadBuilding = roadBuilding;
		this.soldier = soldier;
		this.yearOfPlenty = yearOfPlenty;
	}
	
	public int getMonopoly() {
		return monopoly;
	}

	public void setMonopoly(int monopoly) {
		this.monopoly = monopoly;
	}

	public int getMonument() {
		return monument;
	}

	public void setMonument(int monument) {
		this.monument = monument;
	}

	public int getRoadBuilding() {
		return roadBuilding;
	}

	public void setRoadBuilding(int roadBuilding) {
		this.roadBuilding = roadBuilding;
	}

	public int getSoldier() {
		return soldier;
	}

	public void setSoldier(int soldier) {
		this.soldier = soldier;
	}

	public int getYearOfPlenty() {
		return yearOfPlenty;
	}

	public void setYearOfPlenty(int yearOfPlenty) {
		this.yearOfPlenty = yearOfPlenty;
	}

	public boolean canBuyDevCard(){
		int total = monopoly + monument + roadBuilding + soldier + yearOfPlenty;
		if (total > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean canPlayDevCard(ResourceList bank, Player player, DevCardType cardType){
		
		if(cardType == DevCardType.MONOPOLY) {
			
			if(monopoly > 0) {
				return true;
			}
		} else if(cardType == DevCardType.MONUMENT) { 
			
			if(monument > 0) {
				return true;
			}
		} else if(cardType == DevCardType.ROAD_BUILD) {
			
//			ClientFacade clientFacade = ClientFacade.getSingleton();
	
		
			int roadAmount = player.getRoads();
		
			if(roadBuilding > 0 && roadAmount > 1) {
				return true;
			}
		} else if(cardType == DevCardType.SOLDIER) {
			
			if(soldier > 0) {
				return true;
			}
		} else if(cardType == DevCardType.YEAR_OF_PLENTY) {
			
//			ClientFacade clientFacade = ClientFacade.getSingleton();
//			ResourceList bank = clientFacade.getBank();
			
			if(yearOfPlenty > 0 && bank.getTotal() > 1) {
				return true;
			}
		}
		
		return false;
	}
	
	public int size() {
		return monopoly + monument + roadBuilding + soldier + yearOfPlenty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + monopoly;
		result = prime * result + monument;
		result = prime * result + roadBuilding;
		result = prime * result + soldier;
		result = prime * result + yearOfPlenty;
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
		DevCardList other = (DevCardList) obj;
		if (monopoly != other.monopoly)
			return false;
		if (monument != other.monument)
			return false;
		if (roadBuilding != other.roadBuilding)
			return false;
		if (soldier != other.soldier)
			return false;
		if (yearOfPlenty != other.yearOfPlenty)
			return false;
		return true;
	}
	
}
