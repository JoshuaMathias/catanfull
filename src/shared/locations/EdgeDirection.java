package shared.locations;

import java.io.Serializable;

public enum EdgeDirection implements Serializable
{
	
	NW, N, NE, SE, S, SW;
	
	private EdgeDirection opposite;
	
	static
	{
		NW.opposite = SE;
		N.opposite = S;
		NE.opposite = SW;
		SE.opposite = NW;
		S.opposite = N;
		SW.opposite = NE;
	}
	
	public EdgeDirection getOppositeDirection()
	{
		return opposite;
	}
}

