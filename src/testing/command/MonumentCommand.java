package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import shared.gameModel.DevCardList;
import shared.gameModel.GameModel;
import shared.gameModel.Map;
import shared.gameModel.Player;
import shared.gameModel.TurnTracker;
import Testing.Proxy.ServerFacadeTest;

public class MonumentCommand {

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
		
		paul.setVictoryPoints(6);
		paul.setMonuments(3);
		
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
	}
	
	@After
	public void tearDown() {
		ServerFacadeTest.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
		
		DevCardList devCardList = new DevCardList(0,3,0,0,0);
		paul.setOldDevCards(devCardList);
		boolean test = paul.canPlayMonument();
		
		assertFalse(test);//Paul shouldnt be able to play monuments yet. 
		paul.setVictoryPoints(paul.getVictoryPoints() + 1);
		
		serverFacade.monument(0, 0);
		int monumentAmount = paul.getMonuments();
		
		assertEquals(2,monumentAmount);
		
		serverFacade.monument(0, 0);
		monumentAmount = paul.getMonuments();
		
		assertEquals(1,monumentAmount);
		
		serverFacade.monument(0, 0);
		monumentAmount = paul.getMonuments();
		
		assertEquals(0,monumentAmount);
		
		test = paul.canPlayMonument();
		
		assertTrue(test);
	}

}
