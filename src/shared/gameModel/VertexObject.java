package shared.gameModel;

import shared.locations.VertexLocation;

/**
 * This class represents a vertex in the map, with an edge and an owner if there is one.
 * @author Ife's group
 *
 */
public class VertexObject {

	private int owner;
	private VertexLocation location;
	
	public VertexObject(int owner, VertexLocation location) {
		this.owner=owner;
		this.location=location;
	}
	
	public VertexObject() {
		
	}
	
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public VertexLocation getLocation() {
		return location;
	}
	public void setLocation(VertexLocation location) {
		this.location = location;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + owner;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		this.location = this.location.getNormalizedLocation();
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VertexObject other = (VertexObject) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (owner != other.owner)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VertexObject [owner=" + owner + ", location=" + location + "]";
	}

}
