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
import shared.gameModel.Map;
import shared.gameModel.Player;
import shared.gameModel.TurnTracker;

public class FinishTurnCommand {
	
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
		
		Player paul = new Player();
		Player daniel = new Player();
		Player ife = new Player();
		Player josh = new Player();
		
		paul.setName("paul");
		daniel.setName("daniel");
		ife.setName("ife");
		josh.setName("josh");
		
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
		
		firstRoundTest();
		secondRoundTest();
		rollToPlaying();
	}
	
	private void firstRoundTest() {

		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("FirstRound");
		
		serverFacade.finishTurn(0);
		int turn = turnTracker.getCurrentTurn();
		assertEquals(1, turn);
		
		serverFacade.finishTurn(0);
		turn = turnTracker.getCurrentTurn();
		assertEquals(2, turn);
		
		serverFacade.finishTurn(0);
		turn = turnTracker.getCurrentTurn();
		assertEquals(3, turn);
	}
	
	private void secondRoundTest() {
		
		assertEquals(turnTracker.getStatus(),"FirstRound");
		
		serverFacade.finishTurn(0);
		int turn = turnTracker.getCurrentTurn();
		System.out.println(turn);
		assertEquals(3, turn);
		assertEquals(turnTracker.getStatus(),"SecondRound");
		
		serverFacade.finishTurn(0);
		turn = turnTracker.getCurrentTurn();
		assertEquals(2, turn);
		
		serverFacade.finishTurn(0);
		turn = turnTracker.getCurrentTurn();
		assertEquals(1, turn);
		
		serverFacade.finishTurn(0);
		turn = turnTracker.getCurrentTurn();
		assertEquals(0, turn);
		
		assertEquals(turnTracker.getStatus(),"SecondRound");
		serverFacade.finishTurn(0);
		assertEquals(turnTracker.getStatus(),"Rolling");
	}
	
	private void rollToPlaying() {
		assertEquals(turnTracker.getStatus(),"Rolling");
		serverFacade.rollNumber(0, 0, 0);
		assertEquals(turnTracker.getStatus(),"Playing");
	}

}
