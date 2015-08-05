package Testing.CanDos;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.DevCardType;
import shared.gameModel.GameModel;
import shared.gameModel.DevCardList;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TurnTracker;

public class CanPlayDevCard {
	
	private GameModel clientModel;
	private TurnTracker turnTracker;
	private Player Ife;
	private Player Josh;
	private Player Daniel;
	private Player Paul;
	
	
	//sheep,ore,wheat
	@Before 
	public void setUp() {
		
		clientModel = new GameModel();
		turnTracker = new TurnTracker();
		Ife = new Player();
		Josh = new Player();
		Daniel = new Player();
		Paul= new Player();
		
		ResourceList ifeResources = new ResourceList(0,0,0,0,0);
		ResourceList joshResources = new ResourceList(2,2,0,7,0);
		ResourceList danielResources = new ResourceList(0,1,1,1,0);
		ResourceList paulResources = new ResourceList(5,4,3,1,2);
		
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
	public void test() {
		
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Playing");
		Ife.setPlayedDevCard(false);
		DevCardList ifeDevCardList = new DevCardList(2,2,1,1,0);
		Ife.setOldDevCards(ifeDevCardList);
		
		assertTrue(clientModel.canPlayDevCard(0, DevCardType.MONOPOLY));//Ife has everything he needs to play
		Ife.setPlayedDevCard(true);
		assertFalse(clientModel.canPlayDevCard(0, DevCardType.MONOPOLY));//Ife should be able to play Monopoly card. Its his turn and playing status. But Ife has already played a Dev card this turn
	
		assertFalse(clientModel.canPlayDevCard(0, DevCardType.YEAR_OF_PLENTY));//Ife doesnt have a Year Of Plenty Card but tries to play it
	
		Josh.setPlayedDevCard(false);
		new DevCardList(1,1,1,1,1);
		assertFalse(clientModel.canPlayDevCard(1, DevCardType.MONUMENT));//Josh fails because it is not his turn
		
		turnTracker.setStatus("Robbing");
		Ife.setPlayedDevCard(false);
		assertFalse(clientModel.canPlayDevCard(0, DevCardType.MONUMENT));//The status is not set to playing. Ife can not play dev card
		
		
		
	}
}
