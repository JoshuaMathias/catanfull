package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.definitions.ResourceType;
import shared.gameModel.GameModel;
import shared.gameModel.Map;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import Testing.Proxy.ServerFacadeTest;

public class MonopolyCommand {
	
	private IServerFacade serverFacade;
	private ArrayList<Player> players = new ArrayList<>();
	private TurnTracker turnTracker;
	private GameModel game = new GameModel();
	private Player paul;
	private Player daniel;
	private Player ife;
	private Player josh;
	private ResourceList bank;
	
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
		
		paul.setPlayerIndex(0);
		daniel.setPlayerIndex(1);
		ife.setPlayerIndex(2);
		josh.setPlayerIndex(3);
		
		
		ResourceList paulsResources = new ResourceList(2,2,2,2,2);
		ResourceList danielsResources = new ResourceList(2,2,2,2,2);
		ResourceList ifesResources = new ResourceList(2,2,2,2,2);
		ResourceList joshsResources = new ResourceList(2,2,2,2,2);
		
		bank = new ResourceList(10,10,10,10,10);
		game.setBank(bank);
		
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
		turnTracker.setStatus("FirstRound");
		
		game = new GameModel();
		game.setGameID(0);
		game.setTurnTracker(turnTracker);
		
		Map newMap = new Map();
		game.setMap(newMap);
		
		ArrayList<GameModel> games = serverFacade.getGamesList();
		game.setPlayers(players);
		
		games.add(game);
	}
	
	@After
	public void tearDown() {
		ServerFacadeTest.clearSingleton();
		serverFacade = null;
		return;
	}
	@Test
	public void test() {
		
		serverFacade.monopoly(0, "brick", 0);
		
		assertEquals(paul.getResources().getBrick(), 8);
		assertEquals(daniel.getResources().getBrick(), 0);
		assertEquals(ife.getResources().getBrick(), 0);
		assertEquals(josh.getResources().getBrick(), 0);
		
		serverFacade.monopoly(1, "ore", 0);
		
		assertEquals(paul.getResources().getOre(), 0);
		assertEquals(daniel.getResources().getOre(), 8);
		assertEquals(ife.getResources().getOre(), 0);
		assertEquals(josh.getResources().getOre(), 0);

		serverFacade.monopoly(2, "sheep", 0);
		
		assertEquals(paul.getResources().getSheep(), 0);
		assertEquals(daniel.getResources().getSheep(), 0);
		assertEquals(ife.getResources().getSheep(), 8);
		assertEquals(josh.getResources().getSheep(), 0);
		
		serverFacade.monopoly(3, "wheat", 0);
		
		assertEquals(paul.getResources().getWheat(), 0);
		assertEquals(daniel.getResources().getWheat(), 0);
		assertEquals(ife.getResources().getWheat(), 0);
		assertEquals(josh.getResources().getWheat(), 8);
		
		serverFacade.monopoly(0, "wood", 0);
		
		assertEquals(paul.getResources().getWood(), 8);
		assertEquals(daniel.getResources().getWood(), 0);
		assertEquals(ife.getResources().getWood(), 0);
		assertEquals(josh.getResources().getWood(), 0);
		
		
	}

}
