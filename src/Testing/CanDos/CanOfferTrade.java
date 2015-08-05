package Testing.CanDos;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import shared.gameModel.*;

public class CanOfferTrade {
	
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
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Playing");
		
		ResourceList offer = new ResourceList(1,1,1,1,1);
		tradeOffer.setOffer(offer);
		tradeOffer.setSender(0);
		tradeOffer.setReceiver(1);
		
		assertTrue(clientModel.canOfferTrade(tradeOffer));//Ife should be able to trade. Has the resources required
		
		
		turnTracker.setCurrentTurn(2);
		tradeOffer.setSender(2);
		assertFalse(clientModel.canOfferTrade(tradeOffer));//Daniel should fail. Doesnt have required brick or Wheat
		
		tradeOffer.setSender(3);
		assertFalse(clientModel.canOfferTrade(tradeOffer));//Paul should fail. Has the required resources but not his turn
		
		turnTracker.setCurrentTurn(3);
		turnTracker.setStatus("Robbing");
		assertFalse(clientModel.canOfferTrade(tradeOffer));//Paul should still fail. This time because turn tracker is set to Robbing instead of playing
		
		
		turnTracker.setStatus("Playing");
		assertTrue(clientModel.canOfferTrade(tradeOffer));//Paul finally passes because its his turn, time to play, and has required resources
	}
	
	
}
