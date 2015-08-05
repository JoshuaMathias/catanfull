package Testing.CanDos;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.gameModel.*;

public class MustDiscardHalfCards {

	private GameModel clientModel;
	private TurnTracker turnTracker;
	private Player Ife;
	private Player Josh;
	private Player Daniel;
	private Player Paul;
	
	@Before 
	public void setUp() {
		
		clientModel = new GameModel();
		turnTracker = new TurnTracker();
		Ife = new Player();
		Josh = new Player();
		Daniel = new Player();
		Paul= new Player();
		
		ResourceList ifeResources = new ResourceList(2,4,3,2,0);
		ResourceList joshResources = new ResourceList(0,2,3,0,2);
		ResourceList danielResources = new ResourceList(0,4,0,1,0);
		ResourceList paulResources = new ResourceList(5,4,0,1,0);
		
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
	}
	
	@After
	public void tearDown() {
		clientModel = null;
		return;
	}
	
	@Test
	public void ifeDiscards() {
		
		turnTracker.setStatus("Discarding");
		
		assertTrue(clientModel.mustDiscardHalfCards(7, 0));//Ife has over 7 cards on a roll of 7
		assertFalse(clientModel.mustDiscardHalfCards(7, 1));//Josh has exactly 7 cards on a roll of 7
		assertFalse(clientModel.mustDiscardHalfCards(7, 2));//Daniel has less than 7 cards on a roll of 7
		assertFalse(clientModel.mustDiscardHalfCards(4, 3));//Paul has over 7 cards, on a roll of 4
		
		turnTracker.setStatus("Playing");
		assertFalse(clientModel.mustDiscardHalfCards(7, 0));//Status is not discarding
	}
	
	
	
	
	
}
