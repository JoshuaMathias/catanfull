package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.gameModel.GameModel;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;

public class DiscardCardsCommand {

	private IServerFacade serverFacade;
	private Player paul = new Player();
	private Player daniel = new Player();
	private Player ife = new Player();
	private Player josh = new Player();
	private GameModel game;
	private TurnTracker turnTracker;
	
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
		
		paul.setName("paul");
		daniel.setName("daniel");
		ife.setName("ife");
		josh.setName("josh");
		
		daniel.setDiscarded(false);
		ife.setDiscarded(false);
		josh.setDiscarded(false);
		
		ResourceList paulsResources = new ResourceList(9,9,9,9,9);
		paul.setResources(paulsResources);
		
		ResourceList danielsResources = new ResourceList(1,1,1,1,1);
		daniel.setResources(danielsResources);
		
		ResourceList ifesResources = new ResourceList(2,2,2,2,2);
		ife.setResources(ifesResources);
		
		ResourceList joshsResources = new ResourceList(2,2,2,2,2);
		josh.setResources(joshsResources);
		
		game.setPlayers(players);
		
		turnTracker = new TurnTracker();
		turnTracker.setStatus("Discarding");
		turnTracker.setCurrentTurn(0);
		game.setTurnTracker(turnTracker);
		serverFacade.addGameToList(game);
	}
	
	@After
	public void tearDown() {
		ServerFacade.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
		
		ResourceList discardResourceList = new ResourceList(4,4,4,4,4);
		serverFacade.discardCards(0, discardResourceList, 0);
		
		assertEquals(25,paul.getResources().getTotal());//Should take away 20 resources from pauls 45 resources leaving behind 20 left
		
		turnTracker.setCurrentTurn(1);
		turnTracker.setStatus("Discarding");
		discardResourceList = new ResourceList(1,1,1,0,0);
		serverFacade.discardCards(1, discardResourceList, 0);
		assertEquals(5,daniel.getResources().getTotal());//Daniel doesnt have enough resources to discard. So should stay at 5 resources
		
		turnTracker.setCurrentTurn(3);
		turnTracker.setStatus("Discarding");
		discardResourceList = new ResourceList(1,1,1,1,1);
		serverFacade.discardCards(3, discardResourceList, 0);
		assertEquals(5,josh.getResources().getTotal());//Half of Joshs 10 resources were discarded in half 
		
		turnTracker.setCurrentTurn(2);
		discardResourceList = new ResourceList(1,1,1,1,1);
		serverFacade.discardCards(2, discardResourceList, 0);
		assertEquals(10,ife.getResources().getTotal());//The turnTracker is in the robbing status. Should not discard ifes cards
	}

}
