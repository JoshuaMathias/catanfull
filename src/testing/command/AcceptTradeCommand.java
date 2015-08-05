package testing.command;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.gameModel.GameModel;
import shared.gameModel.Map;
import shared.gameModel.MessageList;
import shared.gameModel.Player;
import shared.gameModel.ResourceList;
import shared.gameModel.TradeOffer;
import shared.gameModel.TurnTracker;

public class AcceptTradeCommand {

	private IServerFacade serverFacade;
	private ArrayList<Player> players;
	
	@Before 
	public void setUp() {
		
		serverFacade = ServerFacade.getSingleton();
		
		Player paul = new Player();
		ResourceList paulsResources = new ResourceList(4,4,4,4,4);
		paul.setResources(paulsResources);
		paul.setPlayerIndex(0);
		paul.setName("Paul");
		paul.setVictoryPoints(3);
		
		Player daniel = new Player();
		ResourceList danielsResources = new ResourceList(4,4,4,4,4);
		daniel.setResources(danielsResources);
		daniel.setPlayerIndex(1);
		daniel.setName("Daniel");
		daniel.setVictoryPoints(4);
		
		Player ife = new Player();
		ResourceList ifesResources = new ResourceList(2,2,2,2,2);
		ife.setResources(ifesResources);
		ife.setPlayerIndex(0);
		ife.setName("Ife");
		ife.setVictoryPoints(3);
		
		Player josh = new Player();
		ResourceList joshsResources = new ResourceList(5,5,5,5,5);
		josh.setResources(joshsResources);
		josh.setPlayerIndex(0);
		josh.setName("josh");
		josh.setVictoryPoints(3);
		
		
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
		ServerFacade.clearSingleton();
		serverFacade = null;
		return;
	}
	
	@Test
	public void test() {
	
		
		
		TradeOffer tradeOffer = new TradeOffer();
		
		ResourceList offer = new ResourceList(-1,1,0,0,-3);
		
		tradeOffer.setOffer(offer);
		tradeOffer.setReceiver(1);
		tradeOffer.setSender(0);
		
		serverFacade.getGameModel(0).setTradeOffer(tradeOffer);
		serverFacade.getGameModel(0).getTurnTracker().setCurrentTurn(0);
		MessageList gameLog = new MessageList();
		
		serverFacade.getGameModel(0).setLog(gameLog);
		serverFacade.acceptTrade(1, true, 0);
	
		/*for(Player player : players) {
			
			System.out.println(player.getName() + " " + player.getPlayerIndex() + ":");
			System.out.println(player.getResources().getBrick());
			System.out.println(player.getResources().getOre());
			System.out.println(player.getResources().getSheep());
			System.out.println(player.getResources().getWheat());
			System.out.println(player.getResources().getWood());
			
		}*/
		int paulsBrick = players.get(0).getResources().getBrick();
		int paulsOre = players.get(0).getResources().getOre();
		int paulsSheep = players.get(0).getResources().getSheep();
		int paulsWheat = players.get(0).getResources().getWheat();
		int paulsWood = players.get(0).getResources().getWood();
		
		int danielsBrick = players.get(1).getResources().getBrick();
		int danielsOre = players.get(1).getResources().getOre();
		int danielsSheep = players.get(1).getResources().getSheep();
		int danielsWheat = players.get(1).getResources().getWheat();
		int danielsWood = players.get(1).getResources().getWood();
		
		assertEquals(paulsBrick,5);
		assertEquals(paulsOre,3);
		assertEquals(paulsSheep,4);
		assertEquals(paulsWheat,4);
		assertEquals(paulsWood,7);
		
		assertEquals(danielsBrick,3);
		assertEquals(danielsOre,5);
		assertEquals(danielsSheep,4);
		assertEquals(danielsWheat,4);
		assertEquals(danielsWood,1);
		
		
		tradeOffer = serverFacade.getGameModel(0).getTradeOffer();
		
		assertEquals(tradeOffer,null);
	}

}
