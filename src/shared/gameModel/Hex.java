package shared.gameModel;

import shared.definitions.HexType;
import shared.locations.HexLocation;

/**
 * This class represents a hex in the map.
 * @author Ife's group
 *
 */
public class Hex {

	private HexLocation location;
	private HexType resource;
	private int number;
	
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	public HexType getResource() {
		if(resource == null){
			resource = HexType.desert;
		}
		return resource;
	}
	public void setResource(HexType resource) {
		this.resource = resource;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + number;
		result = prime * result
				+ ((resource == null) ? 0 : resource.hashCode());
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
		Hex other = (Hex) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (number != other.number)
			return false;
		if (resource != other.resource)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Hex [location=" + location + ", resource=" + resource
				+ ", number=" + number + "]";
	}
	
}
