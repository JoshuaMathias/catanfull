package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.gameModel.GameModel;
import shared.gameModel.Map;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class MaritimeTradeCommand {

	private IServerFacade serverFacade;
	private Player paul = new Player();
	private Player daniel = new Player();
	private Player ife = new Player();
	private Player josh = new Player();
	private GameModel game;
	private ResourceList bank;
	ArrayList<VertexObject> settlements;
	
	@Before 
	public void setUp() {
		
		serverFacade = ServerFacade.getSingleton();
	
		paul.setPlayerIndex(0);
		daniel.setPlayerIndex(1);
		ife.setPlayerIndex(2);
		josh.setPlayerIndex(3);
		
		game = new GameModel();
		game.setGameID(0);
		
		ArrayList<Player> players = new ArrayList<>();
		players.add(paul);
		players.add(daniel);
		players.add(ife);
		players.add(josh);
		
		ResourceList paulsResources = new ResourceList(10,10,10,10,10);
		ResourceList danielsResources = new ResourceList(9,4,0,0,1);
		ResourceList ifesResources = new ResourceList(0,0,0,1,2);
		ResourceList joshsResources = new ResourceList(8,4,5,6,7);
		
		paul.setResources(paulsResources);
		daniel.setResources(danielsResources);
		ife.setResources(ifesResources);
		josh.setResources(joshsResources);
		
		paul.setName("paul");
		daniel.setName("daniel");
		ife.setName("ife");
		josh.setName("josh");
		
		TurnTracker turnTracker = new TurnTracker();
		turnTracker.setStatus("Playing");
		turnTracker.setCurrentTurn(0);
		
		serverFacade.createGame(false, false, false, "default game");
		
		game = serverFacade.getGameModel(0);
		game.setPlayers(players);
		game.setTurnTracker(turnTracker);
		
		settlements = new ArrayList<>();
		VertexObject paulsSettlement = new VertexObject();
		paulsSettlement.setOwner(0);
		paulsSettlement.setLocation(new VertexLocation(new HexLocation(1,-3), VertexDirection.SE));
		settlements.add(paulsSettlement);
		
		game.getMap().setSettlements(settlements);
		
		ResourceList bank = new ResourceList(15,15,15,15,15);
		game.setBank(bank);
		
	}
	
	@After
	public void tearDown() {
		ServerFacade.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
		
		serverFacade.maritimeTrade(0,4, "wood", "ore", 0);
		
		ResourceList expectedResources = new ResourceList(10,11,10,10,6);
		expectedPlayerResources(paul,expectedResources);
		
		ResourceList expectedBank = new ResourceList(15,14,15,15,19);
		expectedBankResources(expectedBank);
		
		serverFacade.maritimeTrade(0, 2, "brick", "ore", 0);
		expectedResources = new ResourceList(8,12,10,10,6);
		expectedPlayerResources(paul,expectedResources);
		
		expectedBank = new ResourceList(17,13,15,15,19);
		expectedBankResources(expectedBank);
		
		VertexObject newSettlement = new VertexObject();
		newSettlement.setLocation(new VertexLocation(new HexLocation(3, -3), VertexDirection.SW));
		
		newSettlement.setOwner(1);
		settlements.add(newSettlement);
		
		game.getTurnTracker().setCurrentTurn(1);
		
		serverFacade.maritimeTrade(1, 3, "brick", "ore", 0);
		
		expectedResources = new ResourceList(6,5,0,0,1);
		expectedPlayerResources(daniel,expectedResources);
		
		expectedBank = new ResourceList(20,12,15,15,19);
		expectedBankResources(expectedBank);
	}
	
	private void expectedPlayerResources(Player player, ResourceList expectedResources) {
		
		int playersBrick = player.getResources().getBrick();
		int playersOre = player.getResources().getOre();
		int playersSheep = player.getResources().getSheep();
		int playersWheat = player.getResources().getWheat();
		int playersWood = player.getResources().getWood();
		
		int expectedBrick = expectedResources.getBrick();
		int expectedOre = expectedResources.getOre();
		int expectedSheep = expectedResources.getSheep();
		int expectedWheat = expectedResources.getWheat();
		int expectedWood = expectedResources.getWood();
		
		assertEquals(playersBrick, expectedBrick);
		assertEquals(playersOre, expectedOre);
		assertEquals(playersSheep, expectedSheep);
		assertEquals(playersWheat, expectedWheat);
		assertEquals(playersWood, expectedWood);
	}
	
	private void expectedBankResources(ResourceList expectedResources) {
		
		bank = game.getBank();
		
		int bankBrick = bank.getBrick();
		int bankOre = bank.getOre();
		int bankSheep = bank.getSheep();
		int bankWheat = bank.getWheat();
		int bankWood = bank.getWood();
		
		int expectedBrick = expectedResources.getBrick();
		int expectedOre = expectedResources.getOre();
		int expectedSheep = expectedResources.getSheep();
		int expectedWheat = expectedResources.getWheat();
		int expectedWood = expectedResources.getWood();
		
		System.out.println(bankBrick + "  "+ expectedBrick);
		assertEquals(bankBrick, expectedBrick);
		assertEquals(bankOre, expectedOre);
		assertEquals(bankSheep, expectedSheep);
		assertEquals(bankWheat, expectedWheat);
		assertEquals(bankWood, expectedWood);
	}
	

}
