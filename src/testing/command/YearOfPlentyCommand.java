package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import shared.gameModel.VertexObject;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class YearOfPlentyCommand {

	private IServerFacade serverFacade;
	private Player paul = new Player();
	private Player daniel = new Player();
	private Player ife = new Player();
	private Player josh = new Player();
	private GameModel game;
	private TurnTracker turnTracker;
	private ResourceList bank;
	
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
		
		ResourceList paulsResources = new ResourceList(5,5,5,5,5);
		ResourceList danielsResources = new ResourceList(9,4,0,0,1);
		ResourceList ifesResources = new ResourceList(0,0,0,1,2);
		ResourceList joshsResources = new ResourceList(8,4,5,6,7);
		
		paul.setResources(paulsResources);
		daniel.setResources(danielsResources);
		ife.setResources(ifesResources);
		josh.setResources(joshsResources);
		
		paul.setName("paul");
		DevCardList paulsOldDevCard = new DevCardList(1,1,1,1,1);
		paul.setOldDevCards(paulsOldDevCard);
		
		turnTracker = new TurnTracker();
		turnTracker.setStatus("Playing");
		turnTracker.setCurrentTurn(0);
		
		serverFacade.createGame(false, false, false, "default game");
		
		game = serverFacade.getGameModel(0);
		game.setPlayers(players);
		game.setTurnTracker(turnTracker);
		
		ArrayList<VertexObject> settlements = new ArrayList<>();
		VertexObject paulsSettlement = new VertexObject();
		paulsSettlement.setOwner(0);
		paulsSettlement.setLocation(new VertexLocation(new HexLocation(3,-3), VertexDirection.NE));
		settlements.add(paulsSettlement);
		
		game.getMap().setSettlements(settlements);
		
		bank = new ResourceList(15,15,15,15,15);
		game.setBank(bank);
		game.setGameID(0);
		
	}
	
	@After
	public void tearDown() {
		ServerFacade.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
		
		serverFacade.yearOfPlenty(0, "brick", "sheep", 0);
		
		ResourceList expectedBankList = new ResourceList(14,15,14,15,15);
		expectedBankResources(expectedBankList);
		
		
		ResourceList expectedResources = new ResourceList(6,5,6,5,5);
		expectedPlayerResources(paul, expectedResources);
		
		boolean discardTest = paul.isPlayedDevCard();
		assertEquals(true,discardTest);
		
		DevCardList expectedDevCardList = new DevCardList(1,1,1,1,0);
		expectedOldDevCardList(paul,expectedDevCardList);
		
		turnTracker.setCurrentTurn(1);
		turnTracker.setStatus("Playing");
		
		bank = new ResourceList(0,0,0,0,0);
		game.setBank(bank);
		
		serverFacade.yearOfPlenty(1, "brick", "wood", 0);
		
		expectedBankList = new ResourceList(0,0,0,0,0);
		expectedBankResources(expectedBankList);
		
		expectedResources = new ResourceList(9,4,0,0,1);
		expectedPlayerResources(daniel, expectedResources);//daniels resources shouldn't change because bank has no cards
		
		discardTest = daniel.isPlayedDevCard();
		assertEquals(false,discardTest);
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
		
		assertEquals(bankBrick, expectedBrick);
		assertEquals(bankOre, expectedOre);
		assertEquals(bankSheep, expectedSheep);
		assertEquals(bankWheat, expectedWheat);
		assertEquals(bankWood, expectedWood);
	}
	
	private void expectedOldDevCardList(Player player,DevCardList oldCardList) {
	
		assertEquals(player.getOldDevCards().size(),oldCardList.size());
		
		int playerMonopoly = player.getOldDevCards().getMonopoly();
		int playerMonument = player.getOldDevCards().getMonument();
		int playerRoadBuilding = player.getOldDevCards().getRoadBuilding();
		int playerSoldier = player.getOldDevCards().getSoldier();
		int playerYearOfPlenty = player.getOldDevCards().getYearOfPlenty();
		
		int expectedMonopoly = oldCardList.getMonopoly();
		int expectedMonument = oldCardList.getMonument();
		int expectedRoadBuilding = oldCardList.getRoadBuilding();
		int expectedSoldier = oldCardList.getSoldier();
		int expectedYearOfPlenty = oldCardList.getYearOfPlenty();
		
		assertEquals(playerMonopoly,expectedMonopoly);
		assertEquals(playerMonument,expectedMonument);
		assertEquals(playerRoadBuilding,expectedRoadBuilding);
		assertEquals(playerSoldier,expectedSoldier);
		assertEquals(playerYearOfPlenty,expectedYearOfPlenty);
	}
}
