package shared.locations;

import java.io.Serializable;

public enum VertexDirection implements Serializable
{
	W, NW, NE, E, SE, SW;
	
	private VertexDirection opposite;
	
	static
	{
		W.opposite = E;
		NW.opposite = SE;
		NE.opposite = SW;
		E.opposite = W;
		SE.opposite = NW;
		SW.opposite = NE;
	}
	
	public VertexDirection getOppositeDirection()
	{
		return opposite;
	}
}

