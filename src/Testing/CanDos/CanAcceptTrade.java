package Testing.CanDos;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.gameModel.GameModel;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TradeOffer;
import shared.gameModel.TurnTracker;

public class CanAcceptTrade {

	private GameModel clientModel;
	private TurnTracker turnTracker;
	private TradeOffer tradeOffer;
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
		
		ResourceList ifeResources = new ResourceList(1,4,3,2,1);
		ResourceList joshResources = new ResourceList(0,2,3,0,2);
		ResourceList danielResources = new ResourceList(0,4,0,1,0);
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
		
		tradeOffer = new TradeOffer();
		
		ResourceList offer = new ResourceList(-1,-1,-1,-1,-1);
		tradeOffer.setOffer(offer);
		
		tradeOffer.setReceiver(0);
		tradeOffer.setSender(1);
		
		assertTrue(clientModel.canAcceptTrade(0, tradeOffer));//Ife can accept Joshes trade. Ife has enough resources
		
		tradeOffer.setReceiver(1);
		tradeOffer.setSender(0);
		assertFalse(clientModel.canAcceptTrade(1, tradeOffer));//Josh can not accept Ifes trade. Josh doesn't have the resources
		
		offer = new ResourceList(2,-3,0,0,-1);
		tradeOffer.setOffer(offer);
		
		tradeOffer.setReceiver(3);
		tradeOffer.setSender(2);
		
		assertTrue(clientModel.canAcceptTrade(3, tradeOffer));//Paul can accept Daniels offer. Has required resources.
		
		tradeOffer.setReceiver(2);
		tradeOffer.setSender(3);
		
		assertFalse(clientModel.canAcceptTrade(2, tradeOffer));//Daniel can not accept Pauls offer. Does not have required resources.
		
	}
}
