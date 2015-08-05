package shared.gameModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
/**
 * This class represents the game map.
 * @author Ife's group
 *
 */
public class Map implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1360744968578963595L;
	private ArrayList<Hex> hexes = new ArrayList<>();
	private ArrayList<Port> ports = new ArrayList<>();
	private ArrayList<Road> roads = new ArrayList<>();
	private ArrayList<VertexObject> settlements = new ArrayList<>();
	private ArrayList<VertexObject> cities = new ArrayList<>();
	private int radius = 0;
	private HexLocation robber; // = new HexLocation(5000, 5000); 
	
	public ArrayList<Hex> getHexes() {
		return hexes;
	}

	public void setHexes(ArrayList<Hex> hexes) {
		this.hexes = hexes;
	}

	public ArrayList<Port> getPorts() {
		return ports;
	}

	public void setPorts(ArrayList<Port> ports) {
		this.ports = ports;
	}

	public ArrayList<Road> getRoads() {
		return roads;
	}

	public void setRoads(ArrayList<Road> roads) {
		this.roads = roads;
	}

	public ArrayList<VertexObject> getSettlements() {
		return settlements;
	}

	public void setSettlements(ArrayList<VertexObject> settlements) {
		this.settlements = settlements;
	}

	public ArrayList<VertexObject> getCities() {
		return cities;
	}

	public void setCities(ArrayList<VertexObject> cities) {
		this.cities = cities;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public HexLocation getRobber() {
		return robber;
	}

	public void setRobber(HexLocation robber) {
		this.robber = robber;
	}
	
	public void addSettlement(VertexObject settlement){
		if (settlements == null){
			settlements = new ArrayList<VertexObject>();
		}
		settlements.add(settlement);
	}
	
	public void removeSettlement(VertexObject settlement){//hopefully, this works
//		for(VertexObject house: settlements){
//			if(house.equals(settlement)){
//				settlements.remove(settlement);
//			}
//		}
		
		LinkedList<VertexObject> settlementsLinked = new LinkedList<>(settlements);
		
		settlementsLinked.remove(settlement);
		this.settlements = new ArrayList<>(settlementsLinked);
	}
	
	public void addCity(VertexObject city){
		if (cities == null){
			cities = new ArrayList<VertexObject>();
		}
		cities.add(city);
	}
	
	public void addRoad(Road road){
		roads.add(road);
	}
	
	public boolean isSpotTaken(VertexLocation spot){
		spot = spot.getNormalizedLocation();
		
		for(int i = 0; i < settlements.size(); i++){
			VertexLocation spotCheck = settlements.get(i).getLocation().getNormalizedLocation();
			
			if(spot.equals(spotCheck)){
				return true;
			}
		}

		
		for(int i = 0; i < cities.size(); i++){
			VertexLocation spotCheck = cities.get(i).getLocation().getNormalizedLocation();
			if(spot.equals(spotCheck)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isSpotNeighbored(VertexLocation spot){
		spot = spot.getNormalizedLocation();
		HexLocation hexLoc = spot.getHexLoc();
		VertexDirection direction = spot.getDir();
		int x = hexLoc.getX();
		int y = hexLoc.getY();
		
		HexLocation hexLoc1;
		VertexLocation spot1;
		HexLocation hexLoc2;
		VertexLocation spot2;
		HexLocation hexLoc3;
		VertexLocation spot3;
		switch(direction){
			case E:
				hexLoc1 = new HexLocation(x, y);
				spot1 = new VertexLocation(hexLoc1, VertexDirection.NE);
				
				hexLoc2 = new HexLocation(x, y);
				spot2 = new VertexLocation(hexLoc2, VertexDirection.SE);
				
				hexLoc3 = new HexLocation(x+1, y);
				spot3 = new VertexLocation(hexLoc3, VertexDirection.NE);
				break;
				
			case NE:
				hexLoc1 = new HexLocation(x, y);
				spot1 = new VertexLocation(hexLoc1, VertexDirection.E);
				
				hexLoc2 = new HexLocation(x, y);
				spot2 = new VertexLocation(hexLoc2, VertexDirection.NW);
				
				hexLoc3 = new HexLocation(x, y-1);
				spot3 = new VertexLocation(hexLoc3, VertexDirection.E);
				break;
				
			case NW:
				hexLoc1 = new HexLocation(x, y);
				spot1 = new VertexLocation(hexLoc1, VertexDirection.W);
				
				hexLoc2 = new HexLocation(x, y);
				spot2 = new VertexLocation(hexLoc2, VertexDirection.NE);
				
				hexLoc3 = new HexLocation(x, y-1);
				spot3 = new VertexLocation(hexLoc3, VertexDirection.W);
				break;
				
			case SE:
				hexLoc1 = new HexLocation(x, y);
				spot1 = new VertexLocation(hexLoc1, VertexDirection.E);
				
				hexLoc2 = new HexLocation(x, y);
				spot2 = new VertexLocation(hexLoc2, VertexDirection.SW);
				
				hexLoc3 = new HexLocation(x, y+1);
				spot3 = new VertexLocation(hexLoc3, VertexDirection.E);
				break;
			case SW:
				hexLoc1 = new HexLocation(x, y);
				spot1 = new VertexLocation(hexLoc1, VertexDirection.W);
				
				hexLoc2 = new HexLocation(x, y);
				spot2 = new VertexLocation(hexLoc2, VertexDirection.SE);
				
				hexLoc3 = new HexLocation(x, y+1);
				spot3 = new VertexLocation(hexLoc3, VertexDirection.W);
				break;
			case W:
				hexLoc1 = new HexLocation(x, y);
				spot1 = new VertexLocation(hexLoc1, VertexDirection.NW);
				
				hexLoc2 = new HexLocation(x, y);
				spot2 = new VertexLocation(hexLoc2, VertexDirection.SW);
				
				hexLoc3 = new HexLocation(x-1, y);
				spot3 = new VertexLocation(hexLoc3, VertexDirection.SW);
				break;
			default: //should never be reached Maybe Throw an exception
				hexLoc1 = new HexLocation(x, y);
				spot1 = new VertexLocation(hexLoc1, VertexDirection.E);
				
				hexLoc2 = new HexLocation(x, y);
				spot2 = new VertexLocation(hexLoc2, VertexDirection.NW);
				
				hexLoc3 = new HexLocation(x, y-1);
				spot3 = new VertexLocation(hexLoc3, VertexDirection.SE);
				break;
		
		}
		
		if(isSpotTaken(spot1) || isSpotTaken(spot2) || isSpotTaken(spot3)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isSpotMySettlement(VertexLocation spot, int playerIndex) {
		
		spot = spot.getNormalizedLocation();
		
		for(int i = 0; i < settlements.size(); i++) {
			
			if(settlements.get(i).getOwner() == playerIndex) {
				
				VertexLocation settlementSpot = settlements.get(i).getLocation();
				settlementSpot = settlementSpot.getNormalizedLocation();
				
				if(settlementSpot.equals(spot)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean haveRoadHere(Road road){
		
		for(Road roadCheck: roads){
			if(roadCheck.equals(road)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasNeighboringOwnRoad(VertexObject settlement){
		int playerIndex = settlement.getOwner();
		VertexLocation settlementSpot = settlement.getLocation();
		settlementSpot = settlementSpot.getNormalizedLocation();
		HexLocation hexLoc = settlementSpot.getHexLoc();
		VertexDirection direction = settlementSpot.getDir();
		
		int x = hexLoc.getX();
		int y = hexLoc.getY();
		
		HexLocation hexLoc1;
		EdgeLocation edge1;
		HexLocation hexLoc2;
		EdgeLocation edge2;
		HexLocation hexLoc3;
		EdgeLocation edge3;
		switch(direction){
		case E:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.NE);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.SE);
			
			hexLoc3 = new HexLocation(x,y-1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.S);
			break;
		case NE:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.N);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.NE);
			
			hexLoc3 = new HexLocation(x,y-1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.SE);
			break;
		case NW:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.N);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.NW);
			
			hexLoc3 = new HexLocation(x,y-1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.SW);
			break;
		case SE:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.S);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.SE);
			
			hexLoc3 = new HexLocation(x,y+1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.NE);
			break;
		case SW:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.SW);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.S);
			
			hexLoc3 = new HexLocation(x,y+1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.NW);
			break;
		case W:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.NW);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.SW);
			
			hexLoc3 = new HexLocation(x-1,y);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.S);
			break;
		default: //Should never get here Throw Exception Maybe
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.NE);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.SE);
			
			hexLoc3 = new HexLocation(x,y-1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.S);
			break;
		
		}
		edge1 = edge1.getNormalizedLocation();
		edge2 = edge2.getNormalizedLocation();
		edge3 = edge3.getNormalizedLocation();
		Road road1 = new Road(playerIndex, edge1);
		Road road2 = new Road(playerIndex, edge2);
		Road road3 = new Road(playerIndex, edge3);
		
		if(haveRoadHere(road1) || haveRoadHere(road2) || haveRoadHere(road3)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean hasNeighboringOwnSettlement(Road road){
		int playerIndex = road.getOwner();
		EdgeLocation side = road.getLocation();
		side = side.getNormalizedLocation();
		
		HexLocation hexLoc = side.getHexLoc();
		EdgeDirection direction = side.getDir();
		
		
		VertexLocation spot1;
		VertexLocation spot2;
		switch(direction){
		case N:
			spot1 = new VertexLocation(hexLoc, VertexDirection.NE);
			spot2 = new VertexLocation(hexLoc, VertexDirection.NW);
			break;
		case NE:
			spot1 = new VertexLocation(hexLoc, VertexDirection.NE);
			spot2 = new VertexLocation(hexLoc, VertexDirection.E);
			break;
		case NW:
			spot1 = new VertexLocation(hexLoc, VertexDirection.W);
			spot2 = new VertexLocation(hexLoc, VertexDirection.NW);
			break;
		case S:
			spot1 = new VertexLocation(hexLoc, VertexDirection.SE);
			spot2 = new VertexLocation(hexLoc, VertexDirection.SW);
			break;
		case SE:
			spot1 = new VertexLocation(hexLoc, VertexDirection.SE);
			spot2 = new VertexLocation(hexLoc, VertexDirection.E);
			break;
		case SW:
			spot1 = new VertexLocation(hexLoc, VertexDirection.SW);
			spot2 = new VertexLocation(hexLoc, VertexDirection.W);
			break;
		default://Should throw exception (should be unreachable)
			spot1 = new VertexLocation(hexLoc, VertexDirection.NE);
			spot2 = new VertexLocation(hexLoc, VertexDirection.E);
			break;
		
		}
		
		spot1 = spot1.getNormalizedLocation();
		spot2 = spot2.getNormalizedLocation();
		
		if(isSpotMySettlement(spot1, playerIndex) || isSpotMySettlement(spot2, playerIndex)){
			return true;
		}
		
		return false;
	}
	
	public boolean hasNeighboringOwnRoad(Road road){
		
		int playerIndex = road.getOwner();
		EdgeLocation edgeSpot = road.getLocation();
		edgeSpot = edgeSpot.getNormalizedLocation();
		
		HexLocation hexLoc = edgeSpot.getHexLoc();
		EdgeDirection direction = edgeSpot.getDir();
		
		int x = hexLoc.getX();
		int y = hexLoc.getY();
		
		HexLocation hexLoc1;
		EdgeLocation edge1;
		HexLocation hexLoc2;
		EdgeLocation edge2;
		HexLocation hexLoc3;
		EdgeLocation edge3;
		HexLocation hexLoc4;
		EdgeLocation edge4;
		
		switch(direction) {
		case N:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.NE);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.NW);
			
			hexLoc3 = new HexLocation(x,y-1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.SW);
			
			hexLoc4 = new HexLocation(x,y-1);
			edge4 = new EdgeLocation(hexLoc4, EdgeDirection.SE);	
			break;
		case NE:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.N);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.SE);
			
			hexLoc3 = new HexLocation(x+1,y-1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.NW);
			
			hexLoc4 = new HexLocation(x+1,y-1);
			edge4 = new EdgeLocation(hexLoc4, EdgeDirection.S);
			break;
		case NW:	
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.N);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.SW);
			
			hexLoc3 = new HexLocation(x-1,y);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.NE);
			
			hexLoc4 = new HexLocation(x-1,y);
			edge4 = new EdgeLocation(hexLoc4, EdgeDirection.S);
			break;
		case SE:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.NE);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.S);
			
			hexLoc3 = new HexLocation(x+1,y);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.N);
			
			hexLoc4 = new HexLocation(x+1,y);
			edge4 = new EdgeLocation(hexLoc4, EdgeDirection.SW);
			break;
		case SW:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.S);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.NW);
			
			hexLoc3 = new HexLocation(x-1,y+1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.N);
			
			hexLoc4 = new HexLocation(x-1,y+1);
			edge4 = new EdgeLocation(hexLoc4, EdgeDirection.SE);
			break;
		case S:
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.SE);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.SW);
			
			hexLoc3 = new HexLocation(x,y+1);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.NE);
			
			hexLoc4 = new HexLocation(x,y+1);
			edge4 = new EdgeLocation(hexLoc4, EdgeDirection.NW);
			break;
		default://May change this to an exception later
			System.out.println("BAD PROBLEM!");
			hexLoc1 = new HexLocation(x,y);
			edge1 = new EdgeLocation(hexLoc1, EdgeDirection.N);
			
			hexLoc2 = new HexLocation(x,y);
			edge2 = new EdgeLocation(hexLoc2, EdgeDirection.SW);
			
			hexLoc3 = new HexLocation(x-1,y);
			edge3 = new EdgeLocation(hexLoc3, EdgeDirection.NE);
			
			hexLoc4 = new HexLocation(x-1,y);
			edge4 = new EdgeLocation(hexLoc4, EdgeDirection.S);
			break;
		}
		edge1 = edge1.getNormalizedLocation();
		edge2 = edge2.getNormalizedLocation();
		edge3 = edge3.getNormalizedLocation();
		edge4 = edge4.getNormalizedLocation();
		
		Road road1 = new Road(playerIndex, edge1);
		Road road2 = new Road(playerIndex, edge2);
		Road road3 = new Road(playerIndex, edge3);
		Road road4 = new Road(playerIndex, edge4);
		
		if(haveRoadHere(road1) || haveRoadHere(road2) || haveRoadHere(road3) || haveRoadHere(road4)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isRoadHere(EdgeLocation edge){
		edge = edge.getNormalizedLocation();
		EdgeLocation edgeCompare;
		for(Road road : roads){
			edgeCompare = road.getLocation();
			edgeCompare = edgeCompare.getNormalizedLocation(); //Might be Unnecessary
			if(edgeCompare.equals(edge)){
				return true;
			}
		}
		return false;
	}
	
	public void setRobberHexLocation(HexLocation newRobber) {
		this.robber = newRobber;
	}
	
	public HexLocation getRobberLocation() {
		return robber;
	}
	
	public ArrayList<VertexObject> getPlayerSettlementsCities(int playerIndex){
		ArrayList<VertexObject> toReturn = new ArrayList<>();
		
		for(VertexObject settlement: settlements){
			if(settlement.getOwner() == playerIndex){
				toReturn.add(settlement);
			}
		}
		
		for(VertexObject city: cities){
			if(city.getOwner() == playerIndex){
				toReturn.add(city);
			}
		}
		
		return toReturn;
	}
	
	public int matchSettlementToPortRatio(VertexLocation spot, PortType resource){
		spot = spot.getNormalizedLocation();
		VertexDirection spotDirection = spot.getDir();
		HexLocation hexLoc = spot.getHexLoc();
		int x = hexLoc.getX();
		int y = hexLoc.getY();
		
		EdgeLocation edge1;
		EdgeLocation edge2;
		HexLocation diffHex;
		EdgeLocation edge3;
		switch(spotDirection){
		case E:
			edge1 = new EdgeLocation(hexLoc, EdgeDirection.NE);
			edge2 = new EdgeLocation(hexLoc, EdgeDirection.SE);
			diffHex = new HexLocation(x+1, y);
			edge3 = new EdgeLocation(diffHex, EdgeDirection.N);
			break;
		case NE:
			edge1 = new EdgeLocation(hexLoc, EdgeDirection.NE);
			edge2 = new EdgeLocation(hexLoc, EdgeDirection.N);
			diffHex = new HexLocation(x, y-1);
			edge3 = new EdgeLocation(diffHex, EdgeDirection.SE);
			break;
		case NW:
			edge1 = new EdgeLocation(hexLoc, EdgeDirection.N);
			edge2 = new EdgeLocation(hexLoc, EdgeDirection.NW);
			diffHex = new HexLocation(x, y-1);
			edge3 = new EdgeLocation(diffHex, EdgeDirection.SW);
			break;
		case SE:
			edge1 = new EdgeLocation(hexLoc, EdgeDirection.S);
			edge2 = new EdgeLocation(hexLoc, EdgeDirection.SE);
			diffHex = new HexLocation(x, y+1);
			edge3 = new EdgeLocation(diffHex, EdgeDirection.NE);
			break;
		case SW:
			edge1 = new EdgeLocation(hexLoc, EdgeDirection.S);
			edge2 = new EdgeLocation(hexLoc, EdgeDirection.SW);
			diffHex = new HexLocation(x, y+1);
			edge3 = new EdgeLocation(diffHex, EdgeDirection.NW);
			break;
		case W:
			edge1 = new EdgeLocation(hexLoc, EdgeDirection.NW);
			edge2 = new EdgeLocation(hexLoc, EdgeDirection.SW);
			diffHex = new HexLocation(x-1, y);
			edge3 = new EdgeLocation(diffHex, EdgeDirection.S);
			break;
		default://Might throw exception instead
			edge1 = new EdgeLocation(hexLoc, EdgeDirection.NE);
			edge2 = new EdgeLocation(hexLoc, EdgeDirection.SE);
			diffHex = new HexLocation(x+1, y);
			edge3 = new EdgeLocation(diffHex, EdgeDirection.N);
			break;
		}
		edge1 = edge1.getNormalizedLocation();
		edge2 = edge2.getNormalizedLocation();
		edge3 = edge3.getNormalizedLocation();
		EdgeLocation portEdgeLocation;
		
		for(Port port: ports){
			portEdgeLocation = new EdgeLocation(port.getLocation(), port.getDirection());
			portEdgeLocation = portEdgeLocation.getNormalizedLocation();
			if(portEdgeLocation.equals(edge1) || portEdgeLocation.equals(edge2) || portEdgeLocation.equals(edge3)){
				if(port.getRatio() == 3){
					return 3;
				}
				else if(port.getResource() == resource){
					return 2;
				}
			}
		}
		return -1;
	}
	
	public boolean isMyBuildingHere(int playerIndex, VertexLocation spot1, VertexLocation spot2){
		
		for(VertexObject settlement: settlements){
			VertexLocation settlementSpot = settlement.getLocation();
			if(settlement.getOwner() == playerIndex){
			}
			if(settlement.getOwner() == playerIndex && (spot1.equals(settlementSpot) || spot2.equals(settlementSpot))){
				return true;
			}
		}
		
		for(VertexObject city: cities){
			VertexLocation citySpot = city.getLocation();
			if(city.getOwner() == playerIndex && (spot1.equals(citySpot) || spot2.equals(citySpot))){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isTouchingRobber(int playerIndex){
		int x = robber.getX();
		int y = robber.getY();
		
		HexLocation hexLoc = new HexLocation(x,y);
		VertexLocation spot1 = new VertexLocation(hexLoc, VertexDirection.SW);
		VertexLocation spot2 = new VertexLocation(hexLoc, VertexDirection.SE);
		VertexLocation spot3 = new VertexLocation(hexLoc, VertexDirection.E);
		VertexLocation spot4 = new VertexLocation(hexLoc, VertexDirection.NE);
		VertexLocation spot5 = new VertexLocation(hexLoc, VertexDirection.NW);
		VertexLocation spot6 = new VertexLocation(hexLoc, VertexDirection.W);
		if(isMyBuildingHere(playerIndex, spot1, spot2) || isMyBuildingHere(playerIndex, spot3, spot4) || isMyBuildingHere(playerIndex, spot5, spot6)){
			return true;
		}
		
		hexLoc = new HexLocation(x,y-1);
		spot1 = new VertexLocation(hexLoc, VertexDirection.SW);
		spot2 = new VertexLocation(hexLoc, VertexDirection.SE);
		if(isMyBuildingHere(playerIndex, spot1, spot2)){
			return true;
		}
		
		hexLoc = new HexLocation(x+1, y-1);
		spot1 = new VertexLocation(hexLoc, VertexDirection.SW);
		spot2 = new VertexLocation(hexLoc, VertexDirection.W);
		if(isMyBuildingHere(playerIndex, spot1, spot2)){
			return true;
		}
		
		hexLoc = new HexLocation(x+1, y);
		spot1 = new VertexLocation(hexLoc, VertexDirection.NW);
		spot2 = new VertexLocation(hexLoc, VertexDirection.W);
		if(isMyBuildingHere(playerIndex, spot1, spot2)){
			return true;
		}
		
		hexLoc = new HexLocation(x, y+1);
		spot1 = new VertexLocation(hexLoc, VertexDirection.NW);
		spot2 = new VertexLocation(hexLoc, VertexDirection.NE);
		if(isMyBuildingHere(playerIndex, spot1, spot2)){
			return true;
		}
		
		hexLoc = new HexLocation(x-1, y+1);
		spot1 = new VertexLocation(hexLoc, VertexDirection.NE);
		spot2 = new VertexLocation(hexLoc, VertexDirection.E);
		if(isMyBuildingHere(playerIndex, spot1, spot2)){
			return true;
		}
		
		hexLoc = new HexLocation(x-1, y);
		spot1 = new VertexLocation(hexLoc, VertexDirection.SE);
		spot2 = new VertexLocation(hexLoc, VertexDirection.E);
		if(isMyBuildingHere(playerIndex, spot1, spot2)){
			return true;
		}
		
		return false;
	}
	
	public boolean canPlaceRobber(HexLocation hexLoc){
		for(Hex hex: hexes){
			if(hex.getLocation().equals(hexLoc)){
				HexType resource = hex.getResource();
				if(resource == HexType.desert || resource == HexType.water){
					return false;
				}
				else{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cities == null) ? 0 : cities.hashCode());
		result = prime * result + ((hexes == null) ? 0 : hexes.hashCode());
		result = prime * result + ((ports == null) ? 0 : ports.hashCode());
		result = prime * result + radius;
		result = prime * result + ((roads == null) ? 0 : roads.hashCode());
		result = prime * result + ((robber == null) ? 0 : robber.hashCode());
		result = prime * result
				+ ((settlements == null) ? 0 : settlements.hashCode());
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
		Map other = (Map) obj;
		if (cities == null) {
			if (other.cities != null)
				return false;
		} else if (!cities.equals(other.cities))
			return false;
		if (hexes == null) {
			if (other.hexes != null)
				return false;
		} else if (!hexes.equals(other.hexes))
			return false;
		if (ports == null) {
			if (other.ports != null)
				return false;
		} else if (!ports.equals(other.ports))
			return false;
		if (radius != other.radius)
			return false;
		if (roads == null) {
			if (other.roads != null)
				return false;
		} else if (!roads.equals(other.roads))
			return false;
		if (robber == null) {
			if (other.robber != null)
				return false;
		} else if (!robber.equals(other.robber))
			return false;
		if (settlements == null) {
			if (other.settlements != null)
				return false;
		} else if (!settlements.equals(other.settlements))
			return false;
		return true;
	}
}
