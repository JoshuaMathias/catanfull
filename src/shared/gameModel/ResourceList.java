package shared.gameModel;

import shared.definitions.ResourceType;

/**
 * This class represents a resource list, keeping track of the amount of each resource.
 * @author Ife's group
 *
 */
public class ResourceList {

	private int brick;
	private int ore;
	private int sheep;
	private int wheat;
	private int wood;

	public ResourceList(int brick, int ore, int sheep, int wheat, int wood){
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
	}
	
	public int getBrick() {
		return brick;
	}
	public void setBrick(int brick) {
		this.brick = brick;
	}
	public int getOre() {
		return ore;
	}
	public void setOre(int ore) {
		this.ore = ore;
	}
	public int getSheep() {
		return sheep;
	}
	public void setSheep(int sheep) {
		this.sheep = sheep;
	}
	public int getWheat() {
		return wheat;
	}
	public void setWheat(int wheat) {
		this.wheat = wheat;
	}
	public int getWood() {
		return wood;
	}
	public void setWood(int wood) {
		this.wood = wood;
	}
	
	public boolean isEmpty() {

		int total = brick + wood + sheep + ore + wheat;
		
		if(total > 0) {
			return false;
		} else {
			return true;
		}
	}
	public int getTotal() {
		int total = brick + wood + sheep + ore + wheat;
		return total;
	}
	
	public int getResourceAmount(ResourceType resource)
	{
		switch(resource)
		{
			case brick:
				return brick;
			case wood:
				return wood;
			case sheep:
				return sheep;
			case wheat:
				return wheat;
			case ore:
				return ore;
		}
		return -1;
	}
	
	public boolean hasOne(ResourceType resource){
		switch(resource){
		case brick:
			if(brick > 0){
				return true;
			}
			break;
		case ore:
			if(ore > 0){
				return true;
			}
			break;
		case sheep:
			if(sheep > 0){
				return true;
			}
			break;
		case wheat:
			if(brick > 0){
				return true;
			}
			break;
		case wood:
			if(wood > 0){
				return true;
			}
			break;
		default:
			return false;
		}
		return false;
	}
	
	public boolean isHalf(int halfNum){
		if(halfNum == getTotal()/2){
			return true;
		}
		else return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + brick;
		result = prime * result + ore;
		result = prime * result + sheep;
		result = prime * result + wheat;
		result = prime * result + wood;
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
		ResourceList other = (ResourceList) obj;
		if (brick != other.brick)
			return false;
		if (ore != other.ore)
			return false;
		if (sheep != other.sheep)
			return false;
		if (wheat != other.wheat)
			return false;
		if (wood != other.wood)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResourceList [brick=" + brick + ", ore=" + ore + ", sheep="
				+ sheep + ", wheat=" + wheat + ", wood=" + wood + "]";
	}
}
