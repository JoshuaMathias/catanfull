package shared.locations;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation
{
	
	private HexLocation hexLoc;
	private int x;//x and y are only here for json conversion compatability
	private int y;
	private VertexDirection direction;
	
	public VertexLocation(HexLocation hexLoc, VertexDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
	}
	
	public VertexLocation(client.serverproxy.VertexLocation location) {
		setHexLoc(new HexLocation(location.getX(), location.getY()));
		VertexDirection dir;
		switch (location.getDirection()) {
		case "E":
			dir = VertexDirection.E;
			break;
		case "SE":
			dir = VertexDirection.SE;
			break;
		case "SW":
			dir = VertexDirection.SW;
			break;
		case "W":
			dir = VertexDirection.W;
			break;
		case "NW":
			dir = VertexDirection.NW;
			break;
		case "NE":
			dir = VertexDirection.NE;
			break;
		default:
			dir = VertexDirection.NE;
		}
		setDir(dir);
	}
	
	public HexLocation getHexLoc()
	{
		setXYHexLoc();
		return hexLoc;
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.hexLoc = hexLoc;
		this.x = hexLoc.getX();
		this.y = hexLoc.getY();
	}
	
	private void setXYHexLoc(){
		HexLocation location = new HexLocation(x, y);
		setHexLoc(location);
	}
	
	public VertexDirection getDir()
	{
		setXYHexLoc();
		return direction;
	}
	
	private void setDir(VertexDirection direction)
	{
		setXYHexLoc();
		this.direction = direction;
	}
	
	@Override
	public String toString()
	{
		setXYHexLoc();
		return "VertexLocation [hexLoc=" + hexLoc + ", dir=" + direction + "]";
	}
	
	@Override
	public int hashCode()
	{
		setXYHexLoc();
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		setXYHexLoc();
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		VertexLocation other = (VertexLocation)obj;
		if(direction != other.direction)
			return false;
		if(hexLoc == null)
		{
			if(other.hexLoc != null)
				return false;
		}
		else if(!hexLoc.equals(other.hexLoc))
			return false;
		return true;
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this vertex location. Since
	 * each vertex has three different locations on a map, this method converts
	 * a vertex location to a single canonical form. This is useful for using
	 * vertex locations as map keys.
	 * 
	 * @return Normalized vertex location
	 */
	public VertexLocation getNormalizedLocation()
	{
		setXYHexLoc();
		
		// Return location that has direction NW or NE
		
		switch (direction)
		{
			case NW:
			case NE:
				return this;
			case W:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SW),
										  VertexDirection.NE);
			case SW:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.S),
										  VertexDirection.NW);
			case SE:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.S),
										  VertexDirection.NE);
			case E:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SE),
										  VertexDirection.NW);
			default:
				assert false;
				return null;
		}
	}
}

