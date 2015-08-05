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
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class CanBuildCity {
	
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
		
		ResourceList ifeResources = new ResourceList(0,3,0,2,0);
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
		
		VertexObject settlement = new VertexObject();
		VertexLocation settlementLocation = new VertexLocation(new HexLocation(0,0), VertexDirection.E);
		settlementLocation = settlementLocation.getNormalizedLocation();
		settlement.setLocation(settlementLocation);
		settlement.setOwner(0);
		
		VertexObject settlement2 = new VertexObject();
		VertexLocation settlementLocation2 = new VertexLocation(new HexLocation(0,0), VertexDirection.E);
		settlementLocation2 = settlementLocation2.getNormalizedLocation();
		settlement2.setLocation(settlementLocation2);
		settlement2.setOwner(1);
		
		
		VertexObject city = new VertexObject();
		VertexLocation cityLocation = new VertexLocation(new HexLocation(0,0), VertexDirection.E);
		cityLocation = cityLocation.getNormalizedLocation();
		city.setLocation(cityLocation);
		city.setOwner(0);
		
		
		VertexObject city2 = new VertexObject();
		VertexLocation cityLocation2 = new VertexLocation(new HexLocation(1,0), VertexDirection.E);
		cityLocation2 = cityLocation2.getNormalizedLocation();
		city2.setLocation(cityLocation2);
		city2.setOwner(0);
		
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Playing");
		
		map.addSettlement(settlement);
		Ife.incrementSettlement();
		map.addSettlement(settlement2);
		Josh.incrementSettlement();
		
		assertFalse(clientModel.canBuildCity(city2));//Ife doesnt have a settlement at city2 spot
		//assertTrue(clientModel.canBuildCity(city));//Ife does have settlement where they want to place city
		
		ResourceList ifeResources = new ResourceList(0,0,0,0,0);
		Ife.setResources(ifeResources);
		assertFalse(clientModel.canBuildCity(city));//Ife doesn't have the resources to build city
		
		ifeResources = new ResourceList(0,3,0,2,0);
		Ife.setResources(ifeResources);
		turnTracker.setCurrentTurn(1);
		assertFalse(clientModel.canBuildCity(city));//Not Ifes turn
		
		turnTracker.setStatus("Rolling");
		turnTracker.setCurrentTurn(0);
		assertFalse(clientModel.canBuildCity(city));//Not in the playing status
		
		turnTracker.setStatus("Playing");
		//assertTrue(clientModel.canBuildCity(city));//Everything is accounted for
	}
}
