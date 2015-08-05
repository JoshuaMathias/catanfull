package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import shared.definitions.HexType;
import shared.gameModel.GameModel;
import shared.gameModel.Hex;
import shared.gameModel.Map;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import Testing.Proxy.ServerFacadeTest;

public class RollNumberCommand {

	private IServerFacade serverFacade;
	private ArrayList<Player> players = new ArrayList<>();
	private TurnTracker turnTracker;
	private GameModel game = new GameModel();
	private Player paul;
	private Player daniel;
	private Player ife;
	private Player josh;
	
	@Before 
	public void setUp() {
		
		serverFacade = ServerFacadeTest.getSingleton();
		
		paul = new Player();
		daniel = new Player();
		ife = new Player();
		josh = new Player();
		
		paul.setName("paul");
		daniel.setName("daniel");
		ife.setName("ife");
		josh.setName("josh");
		
		ResourceList paulsResources = new ResourceList(3,3,3,3,3);
		ResourceList danielsResources = new ResourceList(2,2,2,2,2);
		ResourceList ifesResources = new ResourceList(3,3,3,3,3);
		ResourceList joshsResources = new ResourceList(2,2,2,2,2);
		
		paul.setResources(paulsResources);
		daniel.setResources(danielsResources);
		ife.setResources(ifesResources);
		josh.setResources(joshsResources);
		
		players.add(paul);
		players.add(daniel);
		players.add(ife);
		players.add(josh);
		
		turnTracker = new TurnTracker();
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Playing");
		
		game = new GameModel();
		game.setGameID(0);
		game.setTurnTracker(turnTracker);
		
		Map newMap = new Map();
		game.setMap(newMap);
		
		ArrayList<GameModel> games = serverFacade.getGamesList();
		game.setPlayers(players);
		
		games.add(game);
		
		game.getMap().setRobber(new HexLocation(-2,-2));
		
		VertexObject paulsSettlement = new VertexObject();
		paulsSettlement.setOwner(0);
		paulsSettlement.setLocation(new VertexLocation(new HexLocation(-1,0),VertexDirection.E));
		
		VertexObject danielsSettlement = new VertexObject();
		danielsSettlement.setOwner(1);
		danielsSettlement.setLocation(new VertexLocation(new HexLocation(1,1),VertexDirection.E));
		
		VertexObject ifesSettlement = new VertexObject();
		ifesSettlement.setOwner(2);
		ifesSettlement.setLocation(new VertexLocation(new HexLocation(-1,0),VertexDirection.W));
		
		VertexObject joshsSettlement = new VertexObject();
		joshsSettlement.setOwner(3);
		joshsSettlement.setLocation(new VertexLocation(new HexLocation(0,0),VertexDirection.W));
		
		
		ArrayList<VertexObject> settlements = new ArrayList<>();
		settlements.add(paulsSettlement);
		settlements.add(danielsSettlement);
		settlements.add(ifesSettlement);
		settlements.add(joshsSettlement);
		game.getMap().setSettlements(settlements);
		
		Hex hex1 = new Hex();
		hex1.setLocation(new HexLocation(0,0));
		hex1.setNumber(4);
		hex1.setResource(HexType.brick);
		
		Hex hex2 = new Hex();
		hex2.setLocation(new HexLocation(1,1));
		hex2.setNumber(8);
		hex2.setResource(HexType.sheep);
		
		Hex hex3 = new Hex();
		hex3.setLocation(new HexLocation(-1,0));
		hex3.setNumber(12);
		hex3.setResource(HexType.wheat);
		
		ArrayList<Hex> hexes = new ArrayList<>();
		hexes.add(hex1);
		hexes.add(hex2);
		hexes.add(hex3);
		game.getMap().setHexes(hexes);
	}
	
	@Test
	public void test() {
		
		serverFacade.rollNumber(0, 4, 0);
		
		ResourceList expectedResources = new ResourceList(4,3,3,3,3);
		compareResourceLists(paul,expectedResources);
		
		expectedResources = new ResourceList(2,2,2,2,2);
		compareResourceLists(daniel,expectedResources);
		
		expectedResources = new ResourceList(3,3,3,3,3);
		compareResourceLists(ife,expectedResources);
		
		expectedResources = new ResourceList(3,2,2,2,2);
		compareResourceLists(josh,expectedResources);
		
		expectedResources = new ResourceList(3,3,3,3,3);
		expectedResources = new ResourceList(3,3,3,3,3);
		expectedResources = new ResourceList(3,3,3,3,3);
		
		serverFacade.rollNumber(0, 8, 0);
		
		expectedResources = new ResourceList(4,3,3,3,3);
		compareResourceLists(paul,expectedResources);
		
		expectedResources = new ResourceList(2,2,3,2,2);
		compareResourceLists(daniel,expectedResources);
		
		expectedResources = new ResourceList(3,3,3,3,3);
		compareResourceLists(ife,expectedResources);
		
		expectedResources = new ResourceList(3,2,2,2,2);
		compareResourceLists(josh,expectedResources);
		
		expectedResources = new ResourceList(3,3,3,3,3);
		expectedResources = new ResourceList(3,3,3,3,3);
		expectedResources = new ResourceList(3,3,3,3,3);
		
		serverFacade.rollNumber(0, 12, 0);
		
		expectedResources = new ResourceList(4,3,3,4,3);
		compareResourceLists(paul,expectedResources);
		
		expectedResources = new ResourceList(2,2,3,2,2);
		compareResourceLists(daniel,expectedResources);
		
		expectedResources = new ResourceList(3,3,3,4,3);
		compareResourceLists(ife,expectedResources);
		
		expectedResources = new ResourceList(3,2,2,3,2);
		compareResourceLists(josh,expectedResources);
		
		expectedResources = new ResourceList(3,3,3,3,3);
		expectedResources = new ResourceList(3,3,3,3,3);
		expectedResources = new ResourceList(3,3,3,3,3);
		
		
		
		
	}
	
	private void compareResourceLists(Player player, ResourceList expectedResources) {
		
		int brick = player.getResources().getBrick();
		int ore = player.getResources().getOre();
		int sheep = player.getResources().getSheep();
		int wheat = player.getResources().getWheat();
		int wood = player.getResources().getWood();
		
		int expectedBrick = expectedResources.getBrick();
		int expectedOre = expectedResources.getOre();
		int expectedSheep = expectedResources.getSheep();
		int expectedWheat = expectedResources.getWheat();
		int expectedWood = expectedResources.getWood();
		
		assertEquals(brick,expectedBrick);
		assertEquals(ore,expectedOre);
		assertEquals(sheep,expectedSheep);
		assertEquals(wheat,expectedWheat);
		assertEquals(wood,expectedWood);
	}

}
