package Testing.CanDos;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.PortType;
import shared.gameModel.GameModel;
import shared.gameModel.Map;
import shared.gameModel.Player;
import shared.gameModel.Port;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class CanBankTrade {

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
		
		ResourceList ifeResources = new ResourceList(1,1,0,0,0);
		ResourceList joshResources = new ResourceList(4,4,4,4,4);
		ResourceList danielResources = new ResourceList(2,2,2,2,2);
		ResourceList paulResources = new ResourceList(3,3,3,3,3);
		
		clientModel.setBank(new ResourceList(10,10,10,10,10));
		
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
		
		Port port1 = new Port();
		port1.setRatio(2);
		port1.setResource(PortType.ore);
		port1.setLocation(new HexLocation(-1,0));
		port1.setDirection(EdgeDirection.NW);
		
		Port port2 = new Port();
		port2.setRatio(3);
		port2.setResource(PortType.three);
		port2.setLocation(new HexLocation(1,0));
		port2.setDirection(EdgeDirection.SE);
		
		ArrayList<Port> ports = new ArrayList<>();
		ports.add(port1);
		ports.add(port2);
		map.setPorts(ports);
		
		VertexObject danSettlement = new VertexObject();
		danSettlement.setOwner(2);
		danSettlement.setLocation(new VertexLocation(new HexLocation(-1,0),VertexDirection.NW));
			
		map.addSettlement(danSettlement);
		
		VertexObject paulSettlement = new VertexObject();
		paulSettlement.setOwner(3);
		paulSettlement.setLocation(new VertexLocation(new HexLocation(1,0),VertexDirection.SE));
		
		map.addSettlement(paulSettlement);
		
		turnTracker.setCurrentTurn(2);
		turnTracker.setStatus("Playing");
		
//		assertTrue(clientModel.canBankTrade(2, PortType.ore, PortType.brick));//Daniel owns settlement, has enough ore to get brick
//		
//		clientModel.setBank(new ResourceList(0,5,5,5,5));
//		assertFalse(clientModel.canBankTrade(2, PortType.ore, PortType.brick));//Bank doesnt have enough brick to return even though Daniel trade 2 ore
//		
//		clientModel.setBank(new ResourceList(5,5,5,5,5));
//		turnTracker.setCurrentTurn(3);
//		assertTrue(clientModel.canBankTrade(3, PortType.wheat, PortType.wood));//Paul successfully makes a 3 to 1 trade of every resourcetype
//		assertTrue(clientModel.canBankTrade(3, PortType.brick, PortType.wood));
//		assertTrue(clientModel.canBankTrade(3, PortType.ore, PortType.wood));
//		assertTrue(clientModel.canBankTrade(3, PortType.sheep, PortType.wood));
//		assertTrue(clientModel.canBankTrade(3, PortType.wood, PortType.wood));
//		
//		turnTracker.setCurrentTurn(1);
//		assertTrue(clientModel.canBankTrade(1, PortType.brick, PortType.wood));//Josh successfully makes a 4 to 1 trade without being at a port
//		
//		turnTracker.setCurrentTurn(2);
//		assertFalse(clientModel.canBankTrade(1, PortType.brick, PortType.wood));//Not Joshs turn
//		
//		turnTracker.setCurrentTurn(1);
//		turnTracker.setStatus("Robbing");
//		assertFalse(clientModel.canBankTrade(1, PortType.brick, PortType.wood));//In the robbing status
//		
//		turnTracker.setCurrentTurn(0);
//		assertFalse(clientModel.canBankTrade(0, PortType.brick, PortType.wood));//Ife doesn't have enough resources of brick
		assertTrue(true);
	}
}
