package Testing.CanDos;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.gameModel.GameModel;
import shared.gameModel.Map;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.Road;
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class CanBuildRoad {

	private GameModel clientModel;
	private TurnTracker turnTracker;
	private Map map;
	private Player Ife;
	private Player Josh;
	private Player Daniel;
	private Player Paul;
	
	@Before 
	public void setUp() {
		
		map = new Map();
		clientModel = new GameModel();
		turnTracker = new TurnTracker();
		Ife = new Player();
		Josh = new Player();
		Daniel = new Player();
		Paul= new Player();
		
		ResourceList ifeResources = new ResourceList(1,3,1,2,1);
		ResourceList joshResources = new ResourceList(2,2,0,7,0);
		ResourceList danielResources = new ResourceList(0,1,1,1,0);
		ResourceList paulResources = new ResourceList(5,4,3,1,2);
		
		Ife.setResources(ifeResources);
		Josh.setResources(joshResources);
		Daniel.setResources(danielResources);
		Paul.setResources(paulResources);
		
		ArrayList<Player> playerList = new ArrayList<>();

		Ife.setPlayerID(0);
		Josh.setPlayerID(1);
		Daniel.setPlayerID(2);
		Paul.setPlayerID(3);
		
		playerList.add(Ife);
		playerList.add(Josh);
		playerList.add(Daniel);
		playerList.add(Paul);
		
		
		clientModel.setPlayers(playerList);
		clientModel.setTurnTracker(turnTracker);
		clientModel.setMap(map);
	}
	
	@After
	public void tearDown() {
		clientModel = null;
		return;
	}
	
	@Test
	public void test() {
		
		ArrayList<Road> roads = new ArrayList<>();
		map.setRoads(roads);
		
		EdgeLocation side = new EdgeLocation(new HexLocation(0,0),EdgeDirection.N);
		Road road1 = new Road(0, side);
		
		EdgeLocation side2 = new EdgeLocation(new HexLocation(1,0),EdgeDirection.S);
		Road road2 = new Road(1, side2);
		
		EdgeLocation side3 = new EdgeLocation(new HexLocation(1,-1),EdgeDirection.NE);
		Road road3 = new Road(0, side3);
		
		EdgeLocation side4 = new EdgeLocation(new HexLocation(0,-1),EdgeDirection.SW);
		Road road4 = new Road(0, side4);
		
		EdgeLocation side5 = new EdgeLocation(new HexLocation(1,0),EdgeDirection.SE);
		Road road5 = new Road(0, side5);
		
		EdgeLocation side6 = new EdgeLocation(new HexLocation(1,0),EdgeDirection.SE);
		Road road6 = new Road(0, side6);
		
		Road road7 = new Road(2, side);
		Road road8 = new Road(0, side);
		
		roads.add(road1);
		roads.add(road2);

		map.setRoads(roads);
		
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Playing");
		
		assertFalse(clientModel.canBuildRoad(road3));//Ife can't build this road because it is next to nothing
		assertTrue(clientModel.canBuildRoad(road4));//Ife can build this road because it is next to road1
		assertFalse(clientModel.canBuildRoad(road5));//Ife cant build a road relying on Joshs road
		
		VertexObject ifeSettlement = new VertexObject();
		ifeSettlement.setOwner(0);
		ifeSettlement.setLocation(new VertexLocation(new HexLocation(1,-1),VertexDirection.NE));
		map.addSettlement(ifeSettlement);
		
		Ife.incrementSettlement();
		
		assertTrue(clientModel.canBuildRoad(road3));//Ife build settlement, so now can build road next to it
		
		turnTracker.setCurrentTurn(1);
		assertFalse(clientModel.canBuildRoad(road3));//Not Ifes turn
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Robbing");
		assertFalse(clientModel.canBuildRoad(road3));//Not Playing status
		
		VertexObject joshSettlement = new VertexObject();
		joshSettlement.setOwner(1);
		joshSettlement.setLocation(new VertexLocation(new HexLocation(0,0),VertexDirection.SW));
		map.addSettlement(joshSettlement);
		
		turnTracker.setStatus("Playing");
		
		assertFalse(clientModel.canBuildRoad(road6));//Ife doesnt own settlement he is trying to build road next to
		assertFalse(clientModel.canBuildRoad(road7));//Cant build road on top of another players road
		assertFalse(clientModel.canBuildRoad(road8));//Cant build road on top of own road
	}
}
