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
import shared.gameModel.Map;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;
import shared.locations.HexLocation;

public class SoldierCommand {

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
		DevCardList oldDevCardList = new DevCardList(1,1,1,1,1);
		paul.setOldDevCards(oldDevCardList);
		
		ResourceList danielsResources = new ResourceList(1,1,1,1,1);
		daniel.setResources(danielsResources);
		
		ResourceList ifesResources = new ResourceList(2,2,2,2,2);
		ife.setResources(ifesResources);
		
		ResourceList joshsResources = new ResourceList(2,2,2,2,2);
		josh.setResources(joshsResources);
		
		Map board = new Map();
		
		game.setMap(board);
		game.setPlayers(players);
		
		turnTracker = new TurnTracker();
		turnTracker.setStatus("Playing");
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
			
		HexLocation hexLocation = new HexLocation(0,2);
		serverFacade.soldier(0, 1,hexLocation, 0);//Paul lays down a soldier card
		assertEquals(true,paul.isPlayedDevCard());//Paul should have isPlayedDevCard() set to true because he laid down a soldier card
		assertEquals(4,paul.getOldDevCards().size());//Pauls OldDevCardList should have the soldier card taken away to equal a size of 4 now
		assertEquals(4,daniel.getResources().getTotal());//daniel should have a resource taken away
		assertEquals(46,paul.getResources().getTotal());//Paul should have a resource add to his resourceList
		
		HexLocation tempLocation = new HexLocation(0,2);
		HexLocation robberLocation = serverFacade.getGameModel(0).getMap().getRobber();
		assertEquals(tempLocation,robberLocation);//Robber should be moved to the correctHex
		
		paul.setPlayedDevCard(false);
		serverFacade.soldier(0, 1,hexLocation, 0);//Paul trys to lay down another soldier card. Should be allowed to though because he already laid one down
		assertEquals(false,paul.isPlayedDevCard());//Paul should have isPlayedDevCard() set to false. Can't lay another soldier card down again. 
		assertEquals(4,paul.getOldDevCards().size());//Pauls OldDevCardList should not be changed
		assertEquals(4,daniel.getResources().getTotal());//daniel should not have his resources changed
		assertEquals(46,paul.getResources().getTotal());//Paul should not have his resources changed
	}

}
