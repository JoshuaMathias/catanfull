package Testing.CanDos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

public class CanBuildSettlement {

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
		ResourceList joshResources = new ResourceList(0,0,0,0,0);
		ResourceList danielResources = new ResourceList(1,1,1,1,1);
		ResourceList paulResources = new ResourceList(0,0,0,0,0);
		
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
		
		roads.add(road1);
		roads.add(road2);

		map.setRoads(roads);
		
		VertexObject ifeSettlement = new VertexObject();
		ifeSettlement.setOwner(0);
		ifeSettlement.setLocation(new VertexLocation(new HexLocation(1,-1),VertexDirection.NE));
		map.addSettlement(ifeSettlement);
		
		Ife.incrementSettlement();
		
		VertexObject joshSettlement = new VertexObject();
		joshSettlement.setOwner(1);
		joshSettlement.setLocation(new VertexLocation(new HexLocation(0,0),VertexDirection.SW));
		
		turnTracker.setCurrentTurn(2);
		turnTracker.setStatus("Playing");
		
		VertexObject danSettlement = new VertexObject();
		danSettlement.setOwner(2);
		danSettlement.setLocation(new VertexLocation(new HexLocation(1,-1),VertexDirection.E));
		
		assertFalse(clientModel.canBuildSettlement(danSettlement));//Too close to Ifes settlement
		
		turnTracker.setCurrentTurn(0);
		
		ifeSettlement = new VertexObject();
		ifeSettlement.setOwner(0);
		ifeSettlement.setLocation(new VertexLocation(new HexLocation(1,-1),VertexDirection.W));
		
		assertTrue(clientModel.canBuildSettlement(ifeSettlement));//Ife builds next to his own road in the middle of game
		
		turnTracker.setCurrentTurn(1);
		turnTracker.setStatus("FirstRound");
		assertTrue(clientModel.canBuildSettlement(joshSettlement));//Josh builds settlement without resources on his first turn
		
		VertexObject paulSettlement = new VertexObject();
		paulSettlement.setOwner(3);
		paulSettlement.setLocation(new VertexLocation(new HexLocation(-1,0),VertexDirection.W));
		
		turnTracker.setStatus("SecondRound");
		turnTracker.setCurrentTurn(3);
		assertTrue(clientModel.canBuildSettlement(paulSettlement));//Paul has no resources but build settlement on 2nd round
		
		turnTracker.setStatus("Playing");
		assertFalse(clientModel.canBuildSettlement(paulSettlement));//Paul doesn't have resources to build settlement during Playing status
		
		turnTracker.setStatus("Rolling");
		turnTracker.setCurrentTurn(0);
		assertFalse(clientModel.canBuildSettlement(ifeSettlement));//Cant build during Rolling status
		
		turnTracker.setStatus("Playing");
		turnTracker.setCurrentTurn(1);
		assertFalse(clientModel.canBuildSettlement(ifeSettlement));//Not Ifes turn to build settlement
		
		
		
		
	}
}
