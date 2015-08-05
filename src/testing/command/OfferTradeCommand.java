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
import shared.gameModel.MessageList;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TradeOffer;
import shared.gameModel.TurnTracker;

public class OfferTradeCommand {

	private IServerFacade serverFacade;
	private ArrayList<Player> players;
	
	@Before 
	public void setUp() {
		
		serverFacade = ServerFacadeTest.getSingleton();
		
		Player ife = new Player();
		Player josh = new Player();
		
		Player paul = new Player();
		ResourceList paulsResources = new ResourceList(4,4,4,4,4);
		paul.setResources(paulsResources);
		paul.setPlayerIndex(0);
		paul.setName("Paul");
		
		Player daniel = new Player();
		ResourceList danielsResources = new ResourceList(4,4,4,4,4);
		daniel.setResources(danielsResources);
		daniel.setPlayerIndex(1);
		daniel.setName("Daniel");
		
		players = new ArrayList<>();
		players.add(paul);
		players.add(daniel);
		players.add(ife);
		players.add(josh);
		GameModel gameModel = new GameModel();
		gameModel.setPlayers(players);
		gameModel.setGameID(0);
		
		serverFacade.addGameToList(gameModel);
	}
	
	@After
	public void tearDown() {
		ServerFacadeTest.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
	
		
		ResourceList offer = new ResourceList(1,1,0,0,1);
		
		TurnTracker turnTracker = new TurnTracker();
		turnTracker.setCurrentTurn(0);
		turnTracker.setStatus("Robbing");
		serverFacade.getGameModel(0).setTurnTracker(turnTracker);
		
		serverFacade.offerTrade(1, offer,0, 0);
		
		TradeOffer tradeOffer = serverFacade.getGameModel(0).getTradeOffer();
		
		turnTracker.setStatus("Playing");
		turnTracker.setCurrentTurn(2);
		
		serverFacade.offerTrade(1, offer,0, 0);
		tradeOffer = serverFacade.getGameModel(0).getTradeOffer();

		turnTracker.setCurrentTurn(1);
		
		serverFacade.offerTrade(1, offer,0, 0);
		tradeOffer = serverFacade.getGameModel(0).getTradeOffer();
	
		assertEquals(0,tradeOffer.getReceiver());//Trade succesfull. Tradeoff should have correct values at this point
		assertEquals(1,tradeOffer.getSender());
		
		assertEquals(1,tradeOffer.getOffer().getBrick());
		assertEquals(1,tradeOffer.getOffer().getOre());
		assertEquals(0,tradeOffer.getOffer().getSheep());
		assertEquals(0,tradeOffer.getOffer().getWheat());
		assertEquals(1,tradeOffer.getOffer().getWood());
	}

}
