package shared.gameModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import shared.definitions.HexType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

public class ValidMapLocations {
	
	public static final int numOfPossibleHexLocations = 19;
	@SuppressWarnings("serial")
	public static final ArrayList <HexLocation> hexes = new ArrayList<HexLocation>(){
		{
			add(new HexLocation(-2,0));
			add(new HexLocation(-2,1));
			add(new HexLocation(-2,2));
			add(new HexLocation(-1,-1));
			add(new HexLocation(-1,0));
			add(new HexLocation(-1,1));
			add(new HexLocation(-1,2));
			add(new HexLocation(0,-2));
			add(new HexLocation(0,-1));
			add(new HexLocation(0,0));
			add(new HexLocation(0,1));
			add(new HexLocation(0,2));
			add(new HexLocation(1,-2));
			add(new HexLocation(1,-1));
			add(new HexLocation(1,0));
			add(new HexLocation(1,1));
			add(new HexLocation(2,-2));
			add(new HexLocation(2,-1));
			add(new HexLocation(2,0));
		}
	};
	
	public static final HexType[] hexTypeOrder = 
		{HexType.ore, HexType.wheat, HexType.wood, 
		HexType.brick, HexType.sheep, HexType.sheep, 
		HexType.ore, HexType.desert, HexType.wood, 
		HexType.wheat, HexType.wood, HexType.wheat, 
		HexType.brick, HexType.ore, HexType.brick, 
		HexType.sheep, HexType.wood, HexType.sheep, 
		HexType.wheat };
	
	//Valid Port Locations
	public static final int numOfPossiblePortLocations = 30;//not the actual number
	@SuppressWarnings("serial")
	public static final HashMap<Integer, EdgeLocation> ports = new HashMap<Integer, EdgeLocation>(){
		{
			put(0, new EdgeLocation(new HexLocation(0,-3), EdgeDirection.S));
			put(1, new EdgeLocation(new HexLocation(1,-3), EdgeDirection.SW));
			put(2, new EdgeLocation(new HexLocation(1,-3), EdgeDirection.S));
			put(3, new EdgeLocation(new HexLocation(2,-3), EdgeDirection.SW));
			put(4, new EdgeLocation(new HexLocation(2,-3), EdgeDirection.S));
			put(5, new EdgeLocation(new HexLocation(3,-3), EdgeDirection.SW));
			put(6, new EdgeLocation(new HexLocation(3,-2), EdgeDirection.NW));
			put(7, new EdgeLocation(new HexLocation(3,-2), EdgeDirection.SW));
			put(8, new EdgeLocation(new HexLocation(3,-1), EdgeDirection.NW));
			put(9, new EdgeLocation(new HexLocation(3,-1), EdgeDirection.SW));
			put(10, new EdgeLocation(new HexLocation(3,0), EdgeDirection.NW));
			put(11, new EdgeLocation(new HexLocation(2,1), EdgeDirection.N));
			put(12, new EdgeLocation(new HexLocation(2,1), EdgeDirection.NW));
			put(13, new EdgeLocation(new HexLocation(1,2), EdgeDirection.N));
			put(14, new EdgeLocation(new HexLocation(1,2), EdgeDirection.NW));
			put(15, new EdgeLocation(new HexLocation(0,3), EdgeDirection.N));
			put(16, new EdgeLocation(new HexLocation(-1,3), EdgeDirection.NE));
			put(17, new EdgeLocation(new HexLocation(-1,3), EdgeDirection.N));
			put(18, new EdgeLocation(new HexLocation(-2,3), EdgeDirection.NE));
			put(19, new EdgeLocation(new HexLocation(-2,3), EdgeDirection.N));
			put(20, new EdgeLocation(new HexLocation(-3,3), EdgeDirection.NE));
			put(21, new EdgeLocation(new HexLocation(-3,2), EdgeDirection.SE));
			put(22, new EdgeLocation(new HexLocation(-3,2), EdgeDirection.NE));
			put(23, new EdgeLocation(new HexLocation(-3,1), EdgeDirection.SE));
			put(24, new EdgeLocation(new HexLocation(-3,1), EdgeDirection.NE));
			put(25, new EdgeLocation(new HexLocation(-3,0), EdgeDirection.SE));
			put(26, new EdgeLocation(new HexLocation(-2,-1), EdgeDirection.S));
			put(27, new EdgeLocation(new HexLocation(-2,-1), EdgeDirection.SE));
			put(28, new EdgeLocation(new HexLocation(-1,-2), EdgeDirection.S));
			put(29, new EdgeLocation(new HexLocation(-1,-2), EdgeDirection.SE));
		}
	};
	
	@SuppressWarnings("serial")
	public static final HashMap<Integer, Integer> matchingPortLocations = new HashMap<Integer, Integer>(){
		{
			put(0, -1);
			put(1, 2);
			put(2, 1);
			put(3, 4);
			put(4, 3);
			put(5, -1);
			put(6, 7);
			put(7, 6);
			put(8, 9);
			put(9, 8);
			put(10, -1);
			put(11, 12);
			put(12, 11);
			put(13, 14);
			put(14, 13);
			put(15, -1);
			put(16, 17);
			put(17, 16);
			put(18, 19);
			put(19, 18);
			put(20, -1);
			put(21, 22);
			put(22, 21);
			put(23, 24);
			put(24, 23);
			put(25, -1);
			put(26, 27);
			put(27, 26);
			put(28, 29);
			put(29, 28);
		}
	};
	
	@SuppressWarnings("serial")
	public static final HashMap<Integer, Integer> chitAmounts = new HashMap<Integer, Integer>(){
		{
			put(2, 1);
			put(3, 2);
			put(4, 2);
			put(5, 2);
			put(6, 2);
			put(8, 2);
			put(9, 2);
			put(10, 2);
			put(11, 2);
			put(12, 1);
		}
	};
	
	public static final Integer[] chitOrder = 
			{5, 2, 6, 8, 10, 9, 3, 0, 3, 11, 4, 8, 4, 9, 5, 10, 11, 12, 6};
}
