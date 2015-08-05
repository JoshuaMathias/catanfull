package shared.gameModel;

import java.io.Serializable;

import shared.definitions.PortType;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

/**
 * This class represents a port.
 * @author Ife's group
 *
 */
public class Port implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7382146039516734914L;
	private PortType resource;
	private HexLocation location;
	private EdgeDirection direction;
	private int ratio;

	public PortType getResource() {
		if(resource == null){
			resource = PortType.three;
		}
		return resource;
	}
	public void setResource(PortType resource) {
		this.resource = resource;
	}
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	public EdgeDirection getDirection() {
		return direction;
	}
	public void setDirection(EdgeDirection direction) {
		this.direction = direction;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ratio;
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
		Port other = (Port) obj;
		if (direction != other.direction)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (ratio != other.ratio)
			return false;
		if (resource != other.resource)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Port [resource=" + resource + ", location=" + location
				+ ", direction=" + direction + ", ratio=" + ratio + "]";
	}

}
