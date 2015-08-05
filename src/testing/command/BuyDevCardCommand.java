package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Testing.Proxy.ServerFacadeTest;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.gameModel.GameModel;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;

public class BuyDevCardCommand {

	private IServerFacade serverFacade;
	private GameModel game;
	private Player ife;
	
	@Before 
	public void setUp() {
		
		serverFacade = ServerFacadeTest.getSingleton();
		
		Player paul = new Player();
		Player josh = new Player();
		Player daniel = new Player();
		
		ife = new Player();
		ife.setPlayerIndex(0);
		ife.setName("Ife");
		
		ResourceList resourceList = new ResourceList(8,8,8,8,8);
		ife.setResources(resourceList);
		
		ArrayList<Player> players = new ArrayList<>();
		players.add(ife);
		players.add(paul);
		players.add(josh);
		players.add(daniel);
		
		game = new GameModel();
		game.setGameID(0);
		game.setPlayers(players);
		
		TurnTracker turnTracker = new TurnTracker();
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Playing");
		game.setTurnTracker(turnTracker);
	
		ResourceList bank = new ResourceList(15,15,15,15,15);
		game.setBank(bank);
		
		serverFacade.addGameToList(game);
	}
	
	@After
	public void tearDown() {
		ServerFacadeTest.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
		
		serverFacade.buyDevCard(0, 0);
		
		ResourceList expectedResources = new ResourceList(8,7,7,7,8);
		expectedPlayerResources(ife,expectedResources);
		
		expectedResources = new ResourceList(15,16,16,16,15);
		expectedBankResources(expectedResources);
	
		movingDevCardTest(1,0);
		
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
		
		ResourceList bank = game.getBank();
		
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
	
	private void movingDevCardTest(int newExpectedDevCardAmount, int oldExpectedDevCardAmount) {
		
		int newTotal = ife.getNewDevCards().size();
		int oldTotal = ife.getOldDevCards().size();
		
		assertEquals(newExpectedDevCardAmount, newTotal);
		assertEquals(oldExpectedDevCardAmount, oldTotal);
	}

}
